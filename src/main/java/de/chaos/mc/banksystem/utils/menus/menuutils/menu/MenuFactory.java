package de.chaos.mc.banksystem.utils.menus.menuutils.menu;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

public class MenuFactory {

    private MenuListener listener;

    public MenuFactory(MenuListener listener) {
        this.listener = listener;
    }

    public static MenuFactory register(Plugin plugin) {
        MenuListener l = new MenuListener();
        Bukkit.getPluginManager().registerEvents(l, plugin);
        return new MenuFactory(l);
    }

    public Menu createMenu(int size, String title) {
        return new Menu(size, title, listener);
    }

    public Menu createInventoryTypeMenu(InventoryType inventoryType, String title) {
        return new Menu(inventoryType, title, listener);
    }

    public PagedMenu createPagedMenu(int size, String title) {
        return new PagedMenu(size, title, listener);
    }
}
