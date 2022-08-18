package de.chaos.mc.banksystem;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.banksystem.config.SQLConfig;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class BankSystem extends JavaPlugin {

    private ICoinsInterface iCoinsInterface;
    private SQLConfig sqlConfig;
    public static BankSystem instance;
    private JdbcPooledConnectionSource source;


    @Override
    public void onEnable() {
        sqlConfig = new SQLConfig(this);
        source.setUrl("jdbc:mysql://" +  sqlConfig.getHost() + ":" + sqlConfig.getPort() + "/" + sqlConfig.getDatabase());
        source.setUsername(sqlConfig.getUser());
        source.setPassword(sqlConfig.getPassword());
        try {
            source.initialize();
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("Irgendwas stimmt bei den mysql credentials net");
        }

        instance = this;
    }

    @Override
    public void onDisable() {
    }


    public ICoinsInterface getICoinsInterface() {
        return iCoinsInterface;
    }

    public static BankSystem getInstance() {
        return instance;
    }
}