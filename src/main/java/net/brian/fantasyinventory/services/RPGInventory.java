package net.brian.fantasyinventory.services;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import net.Indyuce.mmoitems.comp.inventory.PlayerInventory;
import net.brian.fantasyinventory.configs.InventoryConfig;
import net.brian.fantasyinventory.players.PlayerRpgInvData;
import net.brian.playerdatasync.PlayerDataSync;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RPGInventory implements PlayerInventory {

    private final InventoryConfig config;

    
    public RPGInventory(InventoryConfig config){
        this.config = config;
        MMOItems.plugin.registerPlayerInventory(this);
    }


    @Override
    public List<EquippedItem> getInventory(Player player) {
        List<EquippedItem> items = new ArrayList<>();
        PlayerRpgInvData data =  PlayerRpgInvData.get(player.getUniqueId());
        if(data != null && data.itemMap != null){
            for (Integer slot : config.slotMap.keySet()) {
                items.add(new EquippedItem(data.itemMap.get(slot), EquipmentSlot.ANY));
            }
        }
        return items;
    }

}
