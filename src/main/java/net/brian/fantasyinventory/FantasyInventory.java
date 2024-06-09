package net.brian.fantasyinventory;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.brian.fantasyinventory.commands.RPGInvCommand;
import net.brian.fantasyinventory.compatible.CompatibleManager;
import net.brian.fantasyinventory.configs.DecorationConfig;
import net.brian.fantasyinventory.configs.InventoryConfig;
import net.brian.fantasyinventory.gui.RPGInvGui;
import net.brian.fantasyinventory.players.PlayerRpgInvData;
import net.brian.fantasyinventory.services.DecorationService;
import net.brian.fantasyinventory.services.RPGInventory;
import net.brian.playerdatasync.PlayerDataSync;
import org.bukkit.plugin.java.JavaPlugin;

public final class FantasyInventory extends JavaPlugin {

    private static FantasyInventory instance;
    private CompatibleManager compatibleManager;
    private RPGInvGui gui;
    private RPGInventory rpgInventory;
    private InventoryConfig config;
    private DecorationConfig decorationConfig;
    private DecorationService decorationService;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PlayerDataSync.getInstance().register(PlayerRpgInvData.key,PlayerRpgInvData.class);
        compatibleManager = new CompatibleManager(this);
        config = new InventoryConfig(this);

        rpgInventory = new RPGInventory(config);
        gui = new RPGInvGui(this,config);
        new RPGInvCommand(this,gui);

        decorationConfig = new DecorationConfig(this);
        if(compatibleManager.hasProtocolLib()){
            decorationService = new DecorationService(this,decorationConfig);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FantasyInventory getInstance() {
        return instance;
    }

    public void reload(){
        config.reload();
    }

    public CompatibleManager getCompatibleManager() {
        return compatibleManager;
    }

    public DecorationService getDecoration(){
        return decorationService;
    }
}
