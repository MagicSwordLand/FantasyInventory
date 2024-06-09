package net.brian.fantasyinventory.commands;

import net.brian.fantasyinventory.FantasyInventory;
import net.brian.fantasyinventory.gui.RPGInvGui;
import net.brian.fantasyinventory.services.RPGInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RPGInvCommand implements CommandExecutor {
    RPGInvGui gui;
    FantasyInventory plugin;


    public RPGInvCommand(FantasyInventory plugin, RPGInvGui gui){
        plugin.getCommand("finv").setExecutor(this);
        this.gui  = gui;
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){
            if(sender instanceof Player){
                gui.show((Player) sender);
            }
        }
        else if(sender.hasPermission("finv.admin") && args[0].equalsIgnoreCase("reload")){
            plugin.reload();
        }
        return true;
    }
}
