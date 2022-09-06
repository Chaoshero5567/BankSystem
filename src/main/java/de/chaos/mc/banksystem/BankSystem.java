package de.chaos.mc.banksystem;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.banksystem.config.ItemsConfig;
import de.chaos.mc.banksystem.config.SQLConfig;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class BankSystem extends JavaPlugin {

    public static BankSystem instance;
    private SQLConfig sqlConfig;
    private ItemsConfig itemsConfig;
    private JdbcPooledConnectionSource source;
    private ICoinsInterface iCoinsInterface;



    @Override
    public void onEnable() {

        instance = this;

        // Config stuff
        sqlConfig = new SQLConfig(this);
        itemsConfig = new ItemsConfig(this);


        // Creating Database Connection
        source.setUrl("jdbc:mysql://" +  sqlConfig.getHost() + ":" + sqlConfig.getPort() + "/" + sqlConfig.getDatabase());
        source.setUsername(sqlConfig.getUser());
        source.setPassword(sqlConfig.getPassword());
        try {
            source.initialize();
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("Irgendwas stimmt bei den mysql credentials net");
        }


    }

    @Override
    public void onDisable() {

        // Closing MySQL connection
        try {
            source.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ICoinsInterface getICoinsInterface() {
        return iCoinsInterface;
    }

    public static BankSystem getInstance() {
        return instance;
    }
}