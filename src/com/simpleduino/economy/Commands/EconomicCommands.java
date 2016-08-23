package com.simpleduino.economy.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class EconomicCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player)
        {
            Player p = (Player)sender;
            if(args.length >= 1)
            {
                switch(args[0])
                {
                    case "give":
                        if(p.hasPermission("Economy.admin.give"))
                        {

                        }
                        break;

                    case "reset":
                        if(p.hasPermission("Economy.admin.reset"))
                        {

                        }
                        break;

                    case "get":
                        if(p.hasPermission("Economy.admin.get"))
                        {

                        }
                        break;
                }
            }
        }
        return false;
    }
}
