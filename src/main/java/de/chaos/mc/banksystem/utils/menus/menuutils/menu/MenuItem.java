package de.chaos.mc.banksystem.utils.menus.menuutils.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {

    private Consumer<Player> callback;
    private final int slot;
    private Menu menu;
    private final ItemStack itemStack;

    public MenuItem(int slot, ItemStack itemStack, Consumer<Player> callback) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.callback = callback;
    }

    public MenuItem(int slot, ItemStack itemStack, Menu menu) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.menu = menu;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public void click(Player p) {
        if (callback != null) callback.accept(p);

        if (menu != null) menu.openInventory(p);
    }
}
