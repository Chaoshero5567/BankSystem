package de.chaos.mc.banksystem.config;

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
    private final FileConfiguration configuration;
    private final Map<String, Object> cache;
    private final ExecutorService pool;

    public FileBuilder(String path, String name, HashMap<String, Object> defaults) {
        this.file = new File(path, name.toLowerCase() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                this.saveDefaults(defaults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.cache = new HashMap<>();
        this.pool = Executors.newCachedThreadPool();

        this.load();
    }

    private void load() {
        for (String key : configuration.getKeys(true)) {
            cache.put(key, configuration.get(key));
        }
    }

    public void reload() {
        cache.clear();
        load();
        save();
    }

    public Object get(String key) {
        if (cache.containsKey(key))
            return cache.get(key);

        cache.put(key, configuration.get(key));
        return configuration.get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public int getInteger(String key) {
        return (int) get(key);
    }

    public List<String> getList(String key) {
        return (List<String>) get(key);
    }

    public void set(String key, Object value) {
        pool.execute(() -> {
            configuration.set(key, value);
            cache.put(key, value);
        });
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveDefaults(HashMap<String, Object> map) {
        for (String string : map.keySet()) {
            configuration.set(string, map.get(string));
        }
        save();
    }
}