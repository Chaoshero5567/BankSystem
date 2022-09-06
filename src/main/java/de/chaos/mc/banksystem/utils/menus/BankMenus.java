package de.chaos.mc.banksystem.utils.menus;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.Menu;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.MenuFactory;
import org.bukkit.entity.Player;

public class BankMenus {
    public void mainInventory(Player player) {
        Menu menu = MenuFactory.register(BankSystem.getInstance()).createMenu(5*9, "Bank");
        menu.additem(null);
    }
}