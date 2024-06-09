package net.brian.fantasyinventory.compatible;

import net.brian.fantasyinventory.FantasyInventory;

public class CompatibleManager {

    FantasyInventory plugin;
    private final boolean ProtocolLib,PlaceholderAPI;

    public CompatibleManager(FantasyInventory plugin){
        this.plugin = plugin;
        ProtocolLib = plugin.getServer().getPluginManager().getPlugin("ProtocolLib") != null;
        PlaceholderAPI = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public boolean hasPlaceholderAPI() {
        return PlaceholderAPI;
    }

    public boolean hasProtocolLib() {
        return ProtocolLib;
    }

}
