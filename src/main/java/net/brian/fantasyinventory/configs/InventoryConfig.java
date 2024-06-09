package net.brian.fantasyinventory.configs;

import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.utils.nbt.NBT;
import me.clip.placeholderapi.PlaceholderAPI;
import net.brian.fantasyinventory.FantasyInventory;
import net.brian.fantasyinventory.compatible.CompatibleManager;
import net.brian.playerdatasync.config.SpigotConfig;
import net.brian.playerdatasync.util.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryConfig extends SpigotConfig {

    FantasyInventory plugin;
    private final CompatibleManager compatible;

    String title;
    public final HashMap<Integer,SlotConfig> slotMap = new HashMap<>();
    List<String> uniqueTypes = new ArrayList<>();
    boolean allUnique = false;

    public InventoryConfig(FantasyInventory plugin){
        super(plugin,"inventory.yml");
        this.plugin = plugin;
        compatible = plugin.getCompatibleManager();
        reload();
    }

    @Override
    public void reload(){
        super.reload();
        uniqueTypes.clear();
        slotMap.clear();
        allUnique = configuration.getBoolean("AllUnique",false);
        title = IridiumColorAPI.process(configuration.getString("title"));
        uniqueTypes = configuration.getStringList("UniqueTypes");
        ConfigurationSection slotSection = configuration.getConfigurationSection("slots");
        for(String key:slotSection.getKeys(false)){
            String[] slots = key.split(";");
            for (String slot : slots) {
                slotMap.put(Integer.parseInt(slot),new SlotConfig(plugin,slotSection.getConfigurationSection(key)));
            }

        }
    }

   public static class SlotConfig{

        final Icon icon;
        List<String> availableTypes;

        public SlotConfig(FantasyInventory plugin,ConfigurationSection section){
            icon = new Icon(plugin.getCompatibleManager(),section.getConfigurationSection("icon"));
            availableTypes = section.getStringList("types");
        }

        public boolean canPut(String type){
            return type != null && !type.equals("") && availableTypes.contains(type);
        }

    }


    public Inventory generateDefault(Player player){
        String title = this.title;
        if(compatible.hasPlaceholderAPI()){
            title = PlaceholderAPI.setPlaceholders(player,this.title);
        }
        Inventory inv = Bukkit.createInventory(null,54,title);
        slotMap.forEach((slot,slotConfig)->{
            inv.setItem(slot,slotConfig.icon.getItem(player));
        });
        return inv;
    }

    public int findAvailable(Inventory inv,ItemStack itemStack){
        NBTItem item = NBTItem.get(itemStack);
        String itemType = item.getType();
        String itemID= item.getString("MMOITEMS_ITEM_ID");
        if(itemType == null || itemType.equals("")){
            return -1;
        }
        int[] availableSlot = {-1};
        boolean isUniqueType = allUnique || uniqueTypes.contains(itemType);

        if(isUniqueType){
            for (Integer integer : slotMap.keySet()) {
                ItemStack slotItem = inv.getItem(integer);
                if(slotItem != null && !Icon.isIcon(slotItem)){
                    NBTItem nbtItem = NBTItem.get(slotItem);
                    String slotItemID = nbtItem.getString("MMOITEMS_ITEM_ID");
                    if(nbtItem.getType().equals(itemType) && slotItemID.equals(itemID)){
                        return -1;
                    }
                }
            }
        }

        slotMap.forEach((slot,slotConfig)->{
            if(Icon.isIcon(inv.getItem(slot))){
                if(slotConfig.canPut(itemType)){
                    availableSlot[0] = slot;
                }
            }
        });
        return availableSlot[0];
    }

    public ItemStack getItem(int slot,Player player){
        SlotConfig config = slotMap.get(slot);
        if(config != null){
            if(config.icon != null){
                return config.icon.getItem(player);
            }
        }
        return new ItemStack(Material.AIR);
    }

}
