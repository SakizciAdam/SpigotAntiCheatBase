package com.github.sakizciadam.spigotanticheatbase.commands;

import com.github.sakizciadam.spigotanticheatbase.SpigotAntiCheat;
import com.github.sakizciadam.spigotanticheatbase.users.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AntiCheatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args[0].equalsIgnoreCase("alerts")){
            if(sender instanceof Player){
                final Player player = (Player) sender;
                if(player.hasPermission("anticheat.alerts")){
                    final User user = SpigotAntiCheat.getSpigotAntiCheat().getUserManager().get(player);
                    user.setAlert(!user.getAlert());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eAlerts Status: &r"+user.getAlert()));
                }
            }
        }

        return false;
    }
}
