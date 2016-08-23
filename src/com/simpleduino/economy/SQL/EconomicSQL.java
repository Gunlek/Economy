package com.simpleduino.economy.SQL;

import com.simpleduino.economy.API.EconomicEntities.EconomicAccount;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.UUID;

/**
 * Created by Simple-Duino on 26/06/2016.
 * Copyrights Simple-Duino, all rights reserved
 */

public class EconomicSQL {

    private File cfgFile = new File("plugins/Economy/config.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
    private Connection con;

    public EconomicSQL()
    {
        String hostname = cfg.get("sql.hostname").toString();
        String database = cfg.get("sql.database").toString();
        String username = cfg.get("sql.username").toString();
        String password = cfg.get("sql.password").toString();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.con = DriverManager.getConnection("jdbc:mysql://"+hostname+":3306/"+database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!this.isInit())
        {
            this.initDb();
        }
    }

    private void initDb()
    {
        try {
            Statement statement = this.con.createStatement();
            statement.execute("CREATE TABLE `"+cfg.get("sql.database")+"`.`economy` ( `id` INT NOT NULL AUTO_INCREMENT , `name` VARCHAR(255) NOT NULL , `uuid` TEXT NOT NULL , `coins` VARCHAR(255) NOT NULL , `tokens` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isInit()
    {
        try {
            Statement statement = this.con.createStatement();
            statement.execute("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE `TABLE_NAME` = \"economy\"");
            ResultSet result = statement.getResultSet();
            if(result.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCoins(UUID uuid)
    {
        try {
            Statement statement = this.con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM economy WHERE uuid=\""+uuid.toString()+"\"");
            if(result.next())
                return Integer.parseInt(result.getString("coins"));
            else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getTokens(UUID uuid)
    {
        try {
            Statement statement = this.con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM economy WHERE uuid=\""+uuid.toString()+"\"");
            if(result.next())
                return Integer.parseInt(result.getString("tokens"));
            else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public EconomicAccount addAccount(Player p)
    {
        try {
            Statement statement = this.con.createStatement();
            boolean state = statement.execute("INSERT INTO economy(name, uuid, coins, tokens) VALUES(\""+p.getName()+"\", \""+p.getUniqueId().toString()+"\", \"0\", \"0\")");
            if(state)
            {
                return new EconomicAccount(p, 0, 0);
            }
            else
            {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setValue(String field, String value, UUID uuid)
    {
        try {
            Statement statement = this.con.createStatement();
            statement.execute("UPDATE economy SET "+field+"=\""+value+"\" WHERE uuid=\""+uuid.toString()+"\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean accountExist(UUID uuid)
    {
        try {
            Statement statement = this.con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM economy WHERE uuid=\""+uuid.toString()+"\"");
            if(result.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
