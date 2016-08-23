package com.simpleduino.economy.Messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.simpleduino.economy.API.EconomicAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Simple-Duino on 10/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class MessageListener implements PluginMessageListener {

    private EconomicAPI economicAPI = new EconomicAPI();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("EconomyPayPlayer")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String sender = null, receiver = null, amount = null;
            try {
                sender = msgin.readUTF();
                receiver = msgin.readUTF();
                amount = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player rcv = Bukkit.getPlayer(receiver);
                if(economicAPI.hasAccount(rcv))
                {
                    economicAPI.getAccount(rcv).setCoins(economicAPI.getAccount(rcv).getCoins()+Integer.parseInt(amount));
                    rcv.sendMessage(ChatColor.YELLOW + "Vous avez reçu "+amount+" de la part de "+sender);
                    new CustomMessageSender("ALL", "EconomyPayDonePlayer", new String[]{sender, receiver, amount});
                }
                else
                {
                    rcv.sendMessage(ChatColor.RED + "Vous n'avez pas de compte bancaire");
                }
            }
            catch(Exception e)
            {

            }
        }

        else if (subchannel.equals("EconomyPayDonePlayer")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgBytes));
            String sender = null, receiver = null, amount = null;
            try {
                sender = msgin.readUTF();
                receiver = msgin.readUTF();
                amount = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                Player senderPlayer = Bukkit.getPlayer(sender);
                economicAPI.getAccount(senderPlayer).setCoins(economicAPI.getAccount(senderPlayer).getCoins()-Integer.parseInt(amount));
                senderPlayer.sendMessage(ChatColor.YELLOW + "Vous avez envoyé "+amount+" à "+receiver);
            }
            catch(Exception e)
            {

            }
        }
    }

}
