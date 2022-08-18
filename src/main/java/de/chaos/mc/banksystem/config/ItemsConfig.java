package de.chaos.mc.banksystem.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ItemsConfig {
    private FileBuilder fileBuilder;
    HashMap<String, Object> defaults;
    public ItemsConfig(Plugin plugin) {
        defaults = new HashMap<>();
        defaults.put("Bankkarte", "Bankkarte");
        defaults.put("Kontoauszug", "Kontoauszug");
        defaults.put("Kontoauszug-Format", "%date% - %Kontonummer%");
        fileBuilder = new FileBuilder(plugin.getDataFolder().getPath(), "SQLConfig", defaults);
    }
    @Getter
    public final String host = fileBuilder.getString("Bankkarte").replaceAll("&", "§");
    @Getter
    public final String database = fileBuilder.getString("Kontoauszug").replaceAll("&", "§");
    @Getter
    public final String user = fileBuilder.getString("Kontoauszug-Format").replaceAll("&", "§");
}