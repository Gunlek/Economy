package com.simpleduino.economy.API;

import com.simpleduino.economy.API.EconomicEntities.EconomicAccount;
import com.simpleduino.economy.Events.newAccountEvent;
import com.simpleduino.economy.SQL.EconomicSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class EconomicAPI {

    EconomicSQL economicSQL = new EconomicSQL();

    public EconomicAPI()
    {

    }

    public EconomicAccount getAccount(Player p)
    {
        int coins = economicSQL.getCoins(p.getUniqueId());
        int tokens = economicSQL.getTokens(p.getUniqueId());
        return new EconomicAccount(p, coins, tokens);
    }

    public boolean createAccount(Player p)
    {
        EconomicAccount ea = new EconomicSQL().addAccount(p);
        if(ea!=null) {
            Bukkit.getServer().getPluginManager().callEvent(new newAccountEvent(p, ea));
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean hasAccount(Player p)
    {
        return economicSQL.accountExist(p.getUniqueId());
    }
}
