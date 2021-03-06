package com.simpleduino.economy.Messaging;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.simpleduino.economy.EconomyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */
public class MessageSender {

    public MessageSender(String cmd, String[] args)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(cmd);
        if(args != null) {
            for (String arg : args) {
                out.writeUTF(arg);
            }
        }

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(EconomyPlugin.getPlugin(EconomyPlugin.class), "BungeeCord", out.toByteArray());
    }
}
