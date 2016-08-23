package com.simpleduino.economy.Listeners;

import com.simpleduino.economy.API.EconomicAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class PlayerListener implements Listener {

    private EconomicAPI economicAPI = new EconomicAPI();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if(!economicAPI.hasAccount(p))
        {
            economicAPI.createAccount(p);
        }
    }
}
