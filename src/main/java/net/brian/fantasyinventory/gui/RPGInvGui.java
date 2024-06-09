package net.brian.fantasyinventory.gui;


import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.brian.fantasyinventory.FantasyInventory;
import net.brian.fantasyinventory.configs.Icon;
import net.brian.fantasyinventory.configs.InventoryConfig;
import net.brian.fantasyinventory.players.PlayerRpgInvData;
import net.brian.fantasyinventory.services.RPGInventory;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class RPGInvGui implements Listener {

    InventoryConfig config;
    FantasyInventory plugin;

    HashMap<HumanEntity,Inventory> inventories = new HashMap<>();

    public RPGInvGui(FantasyInventory plugin, InventoryConfig config){
        this.config = config;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }


    public void show(Player player){
        PlayerRpgInvData data = PlayerRpgInvData.get(player.getUniqueId());
        if(data != null){
            Inventory inv = config.generateDefault(player);
            data.itemMap.forEach(inv::setItem);
            player.openInventory(inv);
            inventories.put(player,inv);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(inventories.containsKey(event.getPlayer())){
            Inventory inv = inventories.get(event.getPlayer());
            PlayerRpgInvData data = PlayerRpgInvData.get(event.getPlayer().getUniqueId());
            if(data != null){
                Integer[] arr = config.slotMap.keySet().toArray(new Integer[0]);
                data.setItemSlotMap(inv,arr);
            }
            inventories.remove(event.getPlayer());
            PlayerData.get(event.getPlayer().getUniqueId()).updateInventory();
            if(plugin.getCompatibleManager().hasProtocolLib()){
                plugin.getDecoration().apply((Player) event.getPlayer(),false);
            }
        }

    }

    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event){
        if(inventories.containsKey(event.getWhoClicked()) && event.getClickedInventory() != null){
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            Inventory topInventory = event.getView().getTopInventory();
            Inventory clickedInventory = event.getClickedInventory();

            if(clickedInventory.getType().equals(InventoryType.PLAYER)){
                if(!PlayerData.get(player.getUniqueId()).getRPG().canUse(NBTItem.get(event.getCurrentItem()),true)){
                    return;
                }
                int slot = config.findAvailable(topInventory,event.getCurrentItem());
                if(slot != -1){
                    topInventory.setItem(slot,event.getCurrentItem());
                    clickedInventory.setItem(event.getSlot(),new ItemStack(Material.AIR));
                }
            }
            else if(clickedInventory.getType().equals(InventoryType.CHEST)){
                ItemStack clickedItem = event.getCurrentItem();

                if(!Icon.isIcon(clickedItem)){
                    if(hasEmptySlots(player)){
                        player.getInventory().addItem(clickedItem);
                        topInventory.setItem(event.getSlot(),config.getItem(event.getSlot(),player));
                    }
                    else player.sendMessage("你的包包滿了,請清空一些位置來取下配備");
                }
            }
        }
    }


    boolean hasEmptySlots(Player player){
        Inventory inv = player.getInventory();
        for(int i=9;i<=44;i++){
            ItemStack itemStack = inv.getItem(i);
            if(itemStack == null || itemStack.getType().equals(Material.AIR)){
                return true;
            }
        }
        return false;
    }

}
