package de.chaos.mc.banksystem.utils.menus.menuutils.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

class MenuListener implements Listener {

    private HashMap<Player, Menu> openedMenus = new HashMap<>();

    public static MenuListener register(Plugin plugin) {
        MenuListener listener = new MenuListener();
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return listener;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        HumanEntity who = e.getWhoClicked();

        if (who instanceof Player) {
            Player p = (Player) who;

            if (openedMenus.containsKey(p)) {
                Menu m = openedMenus.get(p);
                m.click(p, e.getSlot());
                if (m.getCallback() != null) {
                    Bukkit.getConsoleSender().sendMessage("Debug01");
                    if (e.getSlot() == 2) {
                            Bukkit.getConsoleSender().sendMessage("Debug");
                            AnvilOutput anvilOutput = new AnvilOutput(p.getUniqueId(), e.getCursor());
                            m.getCallback().accept(anvilOutput);
                        p.closeInventory();
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (openedMenus.containsKey(p)) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (openedMenus.containsKey(p)) {
                openedMenus.remove(p);
            }
        }
    }

    public void openMenu(Player p, Menu menu) {
        openedMenus.put(p, menu);
    }
}
