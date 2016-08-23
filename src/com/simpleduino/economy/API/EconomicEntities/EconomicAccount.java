package com.simpleduino.economy.API.EconomicEntities;

import com.simpleduino.economy.SQL.EconomicSQL;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class EconomicAccount {

    private int coins;
    private int tokens;
    private Player owner;

    public EconomicAccount(Player p, int c, int t)
    {
        this.owner = p;
        this.coins = c;
        this.tokens = t;
    }

    public Player getOwner()
    {
        return this.owner;
    }

    public int getCoins()
    {
        return this.coins;
    }

    public int getTokens()
    {
        return this.tokens;
    }

    public void setCoins(int value)
    {
        new EconomicSQL().setValue("coins", Integer.toString(value), this.owner.getUniqueId());
    }

    public void setTokens(int value)
    {
        new EconomicSQL().setValue("tokens", Integer.toString(value), this.owner.getUniqueId());
    }
}
