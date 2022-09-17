package de.chaos.mc.banksystem.utils.menus;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.utils.BankPlayer;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import de.chaos.mc.banksystem.utils.ItemBuilder;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.Menu;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.MenuFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

public class BankMenus {
    MenuFactory menuFactory;


    public void mainInventory(Player player) {
        this.menuFactory = MenuFactory.register(BankSystem.getInstance());
    }

    public void openMenu(Player player) {
        Menu menu = menuFactory.createMenu(5 * 1, "Bank");
        BankPlayer bankPlayer = BankSystem.getInstance().getBankPlayers().get(player.getUniqueId());

        ItemStack abhebenItem = new ItemBuilder(Material.GOLD_INGOT).name("abheben").itemStack();
        ItemStack pinChangeItem = new ItemBuilder(Material.GOLD_NUGGET).name("Pin ändern").itemStack();
        ItemStack transferItem = new ItemBuilder(Material.GOLD_NUGGET).name("Geld überweisen").itemStack();

        menu.additem(0, abhebenItem, player1 -> {
            if (openPinMenu(player1)) {
                player1.closeInventory();
                player1.sendMessage(Component.text("Bitte gib die menge an!"));
                bankPlayer.setAbhebenChat(true);
                BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
            } else {
                player1.sendMessage(Component.text("Bitte einen gültigen pin eingeben"));
            }
        });

        menu.additem(1, abhebenItem, player1 -> {
            if (openPinMenu(player1)) {
                player1.closeInventory();
                player1.sendMessage(Component.text("Bitte gib den neuen pin ein!"));
                bankPlayer.setPinchangeChat(true);
                BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
            } else {
                player1.sendMessage(Component.text("Bitte einen gültigen pin eingeben"));
            }
        });

        menu.additem(2, abhebenItem, player1 -> {
            if (openPinMenu(player1)) {
                player1.closeInventory();
                player1.sendMessage(Component.text("Bitte gib die Banknummer des anderen Spielers ein!"));
                bankPlayer.setKontonummerChat(true);
                BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
            } else {
                player1.sendMessage(Component.text("Bitte einen gültigen pin eingeben"));
            }

            player1.closeInventory();
            player1.sendMessage(Component.text("Bitte gib die Banknummer des anderen Spielers ein!"));
            bankPlayer.setKontonummerChat(true);
            BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
        });

    }

    public boolean openPinMenu(Player player) {
        AtomicBoolean b = new AtomicBoolean(false);
        ICoinsInterface iCoinsInterface = BankSystem.getInstance().getICoinsInterface();
        ItemStack nametag = new ItemBuilder(Material.NAME_TAG).name("pin").itemStack();
        Menu anvil = menuFactory.createInventoryTypeMenu(InventoryType.ANVIL, "Pin eingeben");
        anvil.additem(0, nametag, null);
        anvil.additem(2, null, player1 -> {
            if (nametag.displayName().toString().equals(iCoinsInterface.getCoins(player1.getUniqueId()))) {
                b.set(true);
                player1.closeInventory();
            } else {
                player1.closeInventory();
                b.set(false);
            }
        });
        anvil.openInventory(player);
        return b.get();
    }
}