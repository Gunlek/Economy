package com.simpleduino.economy;

import com.simpleduino.economy.Commands.EconomicCommands;
import com.simpleduino.economy.Commands.PayCommand;
import com.simpleduino.economy.Listeners.PlayerListener;
import com.simpleduino.economy.Messaging.MessageListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class EconomyPlugin extends JavaPlugin {

    private File cfgFile = new File("plugins/Economy/config.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

    public void onEnable()
    {
        if(!cfgFile.exists())
        {
            cfgFile.getParentFile().mkdirs();
            try {
                cfgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            cfg.set("sql.hostname", "localhost");
            cfg.set("sql.database", "economy");
            cfg.set("sql.username", "root");
            cfg.set("sql.password", "");
            try {
                cfg.save(cfgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getPluginCommand("eco").setExecutor(new EconomicCommands());
        this.getServer().getPluginCommand("pay").setExecutor(new PayCommand());

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageListener());
    }

    public void onDisable()
    {

    }

}
