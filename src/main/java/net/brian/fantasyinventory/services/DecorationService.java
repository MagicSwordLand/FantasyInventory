package net.brian.fantasyinventory.services;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.brian.fantasyinventory.FantasyInventory;
import net.brian.fantasyinventory.compatible.protocollib.WrapperPlayServerEntityEquipment;
import net.brian.fantasyinventory.configs.DecorationConfig;
import net.brian.fantasyinventory.configs.Icon;
import net.brian.fantasyinventory.players.PlayerRpgInvData;
import net.brian.playerdatasync.PlayerDataSync;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DecorationService implements Listener {

    final FantasyInventory plugin;
    DecorationConfig config;

    public DecorationService(FantasyInventory plugin, DecorationConfig config){
        this.config = config;
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Entity entity = event.getPacket().getEntityModifier(event).read(0);
                if(entity instanceof Player player){
                    Bukkit.getScheduler().runTaskLater(plugin,()->{
                        WrapperPlayServerEntityEquipment packet = getPacket(player);
                        if(packet != null){
                            packet.sendPacket(event.getPlayer());
                        }
                    },1L);
                }
            }
        });

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin,PacketType.Play.Server.ENTITY_EQUIPMENT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Entity source = packet.getEntityModifier(event).read(0);
                if(source instanceof Player player){
                    PlayerRpgInvData data = PlayerRpgInvData.get(player.getUniqueId());
                    if(data != null){
                        packet.getSlotStackPairLists().write(0,getSlotPairList(player,data));
                    }
                }
                event.setPacket(packet);
            }
        });

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin,PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedChatComponent w = event.getPacket().getChatComponents().read(0);

                if(w != null){
                    String json = w.getJson();
                    if(json.contains("1joiawrWJRAwaorja")) event.setCancelled(true);
                }

            }
        });

        Bukkit.getScheduler().runTaskTimer(FantasyInventory.getInstance(),()->{
            Bukkit.getOnlinePlayers().forEach(player -> apply(player,true));
        },0,120000);

    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inv = event.getClickedInventory();
        if(inv != null && inv.getType().equals(InventoryType.PLAYER)){
            if( 5<= event.getRawSlot() && event.getRawSlot() <= 8){
                apply((Player) event.getWhoClicked(),true);
            }
        }
    }

    public void apply(Player player,boolean selfOnly){
        WrapperPlayServerEntityEquipment packet = getPacket(player);
        if(packet != null){
            if(selfOnly){
                packet.sendPacket(player);
            }
            else player.getWorld().getPlayers().forEach(packet::sendPacket);
        }
    }

    public WrapperPlayServerEntityEquipment getPacket(Player player){

        PlayerRpgInvData data = PlayerRpgInvData.get(player.getUniqueId());
        if(data != null){
            WrapperPlayServerEntityEquipment packet = new WrapperPlayServerEntityEquipment();

            packet.setEntityID(player.getEntityId());
            packet.setSlots(getSlotPairList(player,data));

            return packet;
        }
        else return null;
    }

    public List<Pair<EnumWrappers.ItemSlot,ItemStack>> getSlotPairList(Player player,PlayerRpgInvData data){
        List<Pair<EnumWrappers.ItemSlot,ItemStack>> slotPairList = new ArrayList<>();

        ItemStack headItem = getDecoration(player,data,EquipmentSlot.HEAD);
        slotPairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD,headItem));

        ItemStack chestItem = getDecoration(player,data,EquipmentSlot.CHEST);
        slotPairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST,chestItem));

        ItemStack legItem = getDecoration(player,data,EquipmentSlot.LEGS);
        slotPairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS,legItem));

        ItemStack feetItem = getDecoration(player,data,EquipmentSlot.FEET);
        slotPairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET,feetItem));

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        slotPairList.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND,mainHand));
        ItemStack offHand = player.getInventory().getItemInOffHand();
        slotPairList.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND,offHand));
        return slotPairList;
    }

    public ItemStack getDecoration(Player player,PlayerRpgInvData data,EquipmentSlot slot){
        ItemStack decItem = data.itemMap.get(config.getSlot(slot));
        ItemStack originItem = player.getInventory().getItem(slot);
        if(isNull(decItem) && isNull(originItem)){
            return new ItemStack(Material.AIR);
        }

        if(isNull(originItem)) return decItem;
        if(isNull(decItem)) return originItem;

        decItem = decItem.clone();
        originItem = originItem.clone();

        if(originItem.hasItemMeta() && originItem.getItemMeta().hasLore()){
            ItemMeta meta = decItem.getItemMeta();
            meta.setLore(originItem.getItemMeta().getLore());
            decItem.setItemMeta(meta);
        }

        return decItem;
    }

    boolean isNull(ItemStack itemStack){
        if(itemStack == null || itemStack.getType().equals(Material.AIR)){
            return true;
        }
        return Icon.isIcon(itemStack);
    }

}
