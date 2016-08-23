package com.simpleduino.economy.Events;

import com.simpleduino.economy.API.EconomicEntities.EconomicAccount;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class newAccountEvent extends Event {

    private Player owner;
    private EconomicAccount economicAccount;

    public newAccountEvent(Player p, EconomicAccount ea)
    {
        this.owner = p;
        this.economicAccount = ea;
    }

    public Player getAccountOwner()
    {
        return this.owner;
    }

    public EconomicAccount getAccount()
    {
        return this.economicAccount;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
