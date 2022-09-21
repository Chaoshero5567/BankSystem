package de.chaos.mc.banksystem.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ItemsConfig {
    HashMap<String, Object> defaults;
    private FileBuilder fileBuilder;
    @Getter
    public final String bankarte;
    @Getter
    public final String Kontoauszug;
    @Getter
    public final String bankBlock;
    @Getter
    public final String bankkartenItem;
    public ItemsConfig(Plugin plugin) {
        defaults = new HashMap<>();
        defaults.put("Bankkarte", "Bankkarte");
        defaults.put("Kontoauszug", "Kontoauszug");
        defaults.put("bankblock", "ITEM_FRAME");
        defaults.put("bankkartenItem", "NAME_TAG");
        fileBuilder = new FileBuilder(plugin.getDataFolder().getPath(), "ItemConfig", defaults);

        bankarte = fileBuilder.getString("Bankkarte");
        Kontoauszug = fileBuilder.getString("Kontoauszug");
        bankBlock = fileBuilder.getString("bankblock");
        bankkartenItem = fileBuilder.getString("bankkartenItem");

    }

    public FileBuilder getFileBuilder() {
        return fileBuilder;
    }
}