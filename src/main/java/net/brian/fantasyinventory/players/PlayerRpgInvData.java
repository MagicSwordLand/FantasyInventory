package net.brian.fantasyinventory.players;

import net.brian.fantasyinventory.configs.Icon;
import net.brian.fantasyinventory.util.Item64;
import net.brian.playerdatasync.PlayerDataSync;
import net.brian.playerdatasync.data.PlayerData;
import net.brian.playerdatasync.data.gson.PostProcessable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRpgInvData extends PlayerData implements PostProcessable {

    public static final String key = "rpginvdata";

    private HashMap<Integer,String> itemStringMap = new HashMap<>();

    public transient HashMap<Integer,ItemStack> itemMap = new HashMap<>();

    public PlayerRpgInvData(UUID uuid) {
        super(uuid);
    }



    @Override
    public void gsonPostSerialize() {
        itemStringMap.clear();
        itemMap.forEach((slot,item)-> itemStringMap.put(slot,Item64.serialize(item)));
    }

    @Override
    public void gsonPostDeserialize() {
        itemMap = new HashMap<>();
        itemStringMap.forEach((slot,string)-> itemMap.put(slot, Item64.deserialize(string)));
    }

    public void setItemSlotMap(Inventory inv,Integer... slots) {
        itemMap.clear();
        for (int slot : slots) {
            ItemStack itemStack = inv.getItem(slot);
            if(!Icon.isIcon(itemStack)){
                itemMap.put(slot,inv.getItem(slot));
            }
        }
    }

    public static PlayerRpgInvData get(UUID uuid){
        return PlayerDataSync.getInstance().getData(uuid,PlayerRpgInvData.class).orElse(null);
    }

}
