package de.chaos.mc.banksystem;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.banksystem.commands.admincommands.setCoinsBankCommand;
import de.chaos.mc.banksystem.commands.admincommands.setCoinsWalletCommand;
import de.chaos.mc.banksystem.commands.playercommands.createAccountCommand;
import de.chaos.mc.banksystem.commands.playercommands.kontoauszugCommand;
import de.chaos.mc.banksystem.commands.playercommands.walletCommand;
import de.chaos.mc.banksystem.commands.uicommands.ChangePinCommand;
import de.chaos.mc.banksystem.commands.uicommands.TrasnferMoneyCommand;
import de.chaos.mc.banksystem.config.ItemsConfig;
import de.chaos.mc.banksystem.config.SQLConfig;
import de.chaos.mc.banksystem.listener.BlockClickListener;
import de.chaos.mc.banksystem.listener.ChatListener;
import de.chaos.mc.banksystem.utils.*;
import de.chaos.mc.banksystem.utils.menus.BankMenus;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.MenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class BankSystem extends JavaPlugin {

    public static BankSystem instance;
    private SQLConfig sqlConfig;
    private ItemsConfig itemsConfig;
    private JdbcPooledConnectionSource source;
    private ICoinsInterface iCoinsInterface;
    private HashMap<UUID, BankPlayer> bankPlayers;
    private BankMenus bankMenus;
    private MenuFactory menuFactory;
    private ITransaktionInterface transaktionInterface;

    public static BankSystem getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        bankPlayers = new HashMap<>();
        menuFactory = MenuFactory.register(this);


        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Config stuff
        sqlConfig = new SQLConfig(this);
        itemsConfig = new ItemsConfig(this);


        // Creating Database Connection
        source = new JdbcPooledConnectionSource();
        source.setUrl("jdbc:mysql://" + sqlConfig.getHost() + ":" + sqlConfig.getPort() + "/" + sqlConfig.getDatabase());
        source.setUsername(sqlConfig.getUser());
        source.setPassword(sqlConfig.getPassword());
        try {
            source.initialize();
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage("Irgendwas stimmt bei den mysql credentials net");
        }
        iCoinsInterface = new CoinsRepository(source);
        transaktionInterface = new TransaktionRepository(source, itemsConfig);

        bankMenus = new BankMenus(menuFactory, iCoinsInterface);

        init();
        registerCommands();
    }

    public void init() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BlockClickListener(itemsConfig), this);
        manager.registerEvents(new ChatListener(this, transaktionInterface, iCoinsInterface), this);
    }

    public void registerCommands() {
        getCommand("wallet").setExecutor(new walletCommand(iCoinsInterface));
        getCommand("kontoauszug").setExecutor(new kontoauszugCommand(transaktionInterface));
        getCommand("changePin").setExecutor(new ChangePinCommand(iCoinsInterface));
        getCommand("transferMoney").setExecutor(new TrasnferMoneyCommand(transaktionInterface));
        getCommand("setCoins").setExecutor(new setCoinsWalletCommand(iCoinsInterface));
        getCommand("setCoinsBank").setExecutor(new setCoinsBankCommand(iCoinsInterface));
        getCommand("createAccount").setExecutor(new createAccountCommand(itemsConfig, bankMenus, iCoinsInterface));
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

    public HashMap<UUID, BankPlayer> getBankPlayers() {
        return bankPlayers;
    }
}