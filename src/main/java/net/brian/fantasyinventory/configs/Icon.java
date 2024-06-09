package net.brian.fantasyinventory.configs;

import me.clip.placeholderapi.PlaceholderAPI;
import net.brian.fantasyinventory.FantasyInventory;
import net.brian.fantasyinventory.compatible.CompatibleManager;
import net.brian.playerdatasync.util.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Icon {
    static NamespacedKey key = new NamespacedKey(FantasyInventory.getInstance(),"icon");

    CompatibleManager compatible;

    final String material;
    final String display;
    final List<String> lores;
    final int modelData;

    public Icon(CompatibleManager compatible, ConfigurationSection section){
        this.compatible = compatible;
        material = section.getString("material","STONE").toUpperCase();
        display = section.getString("display","");
        modelData = section.getInt("model",0);
        lores = section.getStringList("lores");
    }

    public ItemStack getItem(Player player){
        ItemStack itemStack = new ItemStack(Material.valueOf(material));
        ItemMeta meta = itemStack.getItemMeta();
        if(compatible.hasPlaceholderAPI()){
            meta.setDisplayName(PlaceholderAPI.setPlaceholders(player,display));
            meta.setLore(PlaceholderAPI.setPlaceholders(player, lores));
        }
        else {
            meta.setDisplayName(IridiumColorAPI.process(display));
            meta.setLore(IridiumColorAPI.process(lores));
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(key,PersistentDataType.INTEGER,1);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static boolean isIcon(ItemStack itemStack){
        if(itemStack == null){
            return true;
        }
        if(itemStack.hasItemMeta()){
            return itemStack.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER);
        }
        return false;
    }
}
