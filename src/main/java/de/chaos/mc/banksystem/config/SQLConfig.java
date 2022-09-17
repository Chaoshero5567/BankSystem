package de.chaos.mc.banksystem.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class SQLConfig {
    HashMap<String, Object> defaults;
    private FileBuilder fileBuilder;
    @Getter
    public final String host = fileBuilder.getString("host");
    @Getter
    public final int port = fileBuilder.getInteger("port");
    @Getter
    public final String database = fileBuilder.getString("database");
    @Getter
    public final String user = fileBuilder.getString("user");
    @Getter
    public final String password = fileBuilder.getString("password");
    public SQLConfig(Plugin plugin) {
        defaults = new HashMap<>();
        defaults.put("host", "localhost");
        defaults.put("port", 3306);
        defaults.put("database", "coins");
        defaults.put("user", "coins");
        defaults.put("password", "coins");
        this.fileBuilder = new FileBuilder(plugin.getDataFolder().getPath(), "SQLConfig", defaults);
    }
}