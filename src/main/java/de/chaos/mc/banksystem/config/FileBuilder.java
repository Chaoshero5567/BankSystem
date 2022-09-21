package de.chaos.mc.banksystem.config;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileBuilder {
    private final File file;
    private  FileConfiguration configuration;
    private final Map<String, Object> cache;
    private final ExecutorService pool;

    public FileBuilder(String path, String name, HashMap<String, Object> defaults) {
        this.file = new File(path, name.toLowerCase() + ".yml");
        this.pool = Executors.newCachedThreadPool();
        this.cache = new HashMap<>();


        // Checks if file exist
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Sets YamlConf as file
                this.configuration = YamlConfiguration.loadConfiguration(file);
                this.saveDefaults(defaults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Checks if file is empty
            if (file.length() == 0) {
                this.saveDefaults(defaults);
            }
            // Sets file as the yaml conf
            this.configuration = YamlConfiguration.loadConfiguration(file);
        }
        // Loads conf from file
        this.load();
    }

    // Loads the Strings into the cash
    private void load() {
        for (String key : configuration.getKeys(true)) {
            cache.put(key, configuration.get(key));
        }
        Bukkit.getConsoleSender().sendMessage(Component.text(cache.toString()));
    }

    // Reloads config from file
    public void reload() {
        cache.clear();
        load();
        save();
    }


    // Gets a object from the config
    public Object get(String key) {
        // Checks if object in cash and reutrns that
        if (cache.containsKey(key)) {return cache.get(key);}

        // Puts object in cash and returns the object
        cache.put(key, configuration.get(key));
        return configuration.get(key);
    }

    // Gets a String
    public String getString(String key) {
        return (String) get(key);
    }

    // Gets a int
    public int getInteger(String key) {
        return (int) get(key);
    }

    // Gets a boolean
    public boolean getBoolean(String key) {return (boolean) get(key);}

    // Get all objects
    public List<String> getList(String key) {
        return (List<String>) get(key);
    }

    // Puts things into the cach and file
    public void set(String key, Object value) {
        pool.execute(() -> {
            configuration.set(key, value);
            cache.put(key, value);
            save();
        });
    }

    // Save option for writing things into the file
    public void save() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // saves default strings
    public void saveDefaults(HashMap<String, Object> map) {
        for (String string : map.keySet()) {
            this.set(string, map.get(string));
        }
    }
}