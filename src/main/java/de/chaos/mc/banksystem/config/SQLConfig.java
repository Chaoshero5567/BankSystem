package de.chaos.mc.banksystem.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class SQLConfig {
    HashMap<String, Object> defaults;
    private FileBuilder fileBuilder;
    @Getter
    public final String host;
    @Getter
    public final Integer port;
    @Getter
    public final String database;
    @Getter
    public final String user;
    @Getter
    public final String password;

    public SQLConfig(Plugin plugin) {
        defaults = new HashMap<>();
        defaults.put("host", "localhost");
        defaults.put("port", 3306);
        defaults.put("database", "coins");
        defaults.put("user", "coins");
        defaults.put("password", "coins");

        this.fileBuilder = new FileBuilder(plugin.getDataFolder().getPath(), "SQLConfig", defaults);
        host = fileBuilder.getString("host");
        port = fileBuilder.getInteger("port");
        database = fileBuilder.getString("database");
        user = fileBuilder.getString("user");
        password = fileBuilder.getString("password");
    }

    public FileBuilder getFileBuilder() {
        return fileBuilder;
    }
}