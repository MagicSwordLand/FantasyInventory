package net.brian.fantasyinventory.configs;

import net.brian.playerdatasync.config.SpigotConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public class DecorationConfig extends SpigotConfig {

    HashMap<EquipmentSlot,Integer> map = new HashMap<>();

    public DecorationConfig(JavaPlugin plugin) {
        super(plugin, "decoration.yml");
        reload();
    }


    @Override
    public void reload(){
        super.reload();
        try {
            for (String key : configuration.getKeys(false)) {
                map.put(EquipmentSlot.valueOf(key),configuration.getInt(key));
            }
        }catch (Exception e){
            Bukkit.getLogger().log(Level.INFO, ChatColor.RED+"[FantasyInventory] Failed to load config");
        }
    }


    public int getSlot(EquipmentSlot slot){
        return map.get(slot);
    }

}
