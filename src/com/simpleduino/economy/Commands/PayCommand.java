package com.simpleduino.economy.Commands;

import com.simpleduino.economy.API.EconomicAPI;
import com.simpleduino.economy.Messaging.CustomMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class PayCommand implements CommandExecutor {

    private EconomicAPI economicAPI = new EconomicAPI();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player)
        {
            if(args.length >= 2)
            {
                Player p = (Player) sender;
                int amount = Integer.parseInt(args[1]);
                if(p.hasPermission("Economy.pay"))
                {
                    if(economicAPI.hasAccount(p)) {
                        try {
                            Player receiver = Bukkit.getPlayer(args[0]);
                            if(economicAPI.hasAccount(receiver))
                            {
                                if(economicAPI.getAccount(p).getCoins() >= amount) {
                                    economicAPI.getAccount(receiver).setCoins(economicAPI.getAccount(receiver).getCoins() + amount);
                                    economicAPI.getAccount(p).setCoins(economicAPI.getAccount(p).getCoins() - amount);
                                    receiver.sendMessage(ChatColor.YELLOW + "Vous avez reçu "+Integer.toString(amount)+" de la part de "+p.getName());
                                    p.sendMessage(ChatColor.YELLOW + "Vous avez envoyé "+Integer.toString(amount)+" à "+receiver.getName());
                                }
                                else
                                {
                                    p.sendMessage(Integer.toString(economicAPI.getAccount(p).getCoins()));
                                    p.sendMessage(Integer.toString(amount));
                                    p.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent sur votre compte");
                                }
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "Le destinataire n'a pas de compte bancaire");
                            }
                        } catch (Exception e) {
                            new CustomMessageSender("ALL", "EconomyPayPlayer", new String[]{p.getName(), args[0], Integer.toString(amount)});
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas de compte bancaire");
                    }
                }
                else
                {
                    p.sendMessage("Vous n'avez pas la permission d'executer cette commande");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Erreur: Syntaxe incorrecte, utilisez /pay <destinataire> <montant>");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Vous devez être une joueur pour executer cette commande");
        }
        return false;
    }
}
