package de.chaos.mc.banksystem.utils.menus;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.utils.BankPlayer;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import de.chaos.mc.banksystem.utils.ItemBuilder;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.AnvilOutput;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.Menu;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.MenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class BankMenus {
    MenuFactory menuFactory;
    ICoinsInterface coinsInterface;


    public BankMenus(MenuFactory menuFactory, ICoinsInterface iCoinsInterface) {
        this.menuFactory = menuFactory;
        this.coinsInterface = iCoinsInterface;
    }

    public void openMenu(Player player) {
        Menu menu = menuFactory.createMenu(9*1, "Bank");
        BankPlayer bankPlayer = BankSystem.getInstance().getBankPlayers().get(player.getUniqueId());

        ItemStack abhebenItem = new ItemBuilder(Material.GOLD_INGOT).name("abheben").itemStack();
        ItemStack pinChangeItem = new ItemBuilder(Material.GOLD_NUGGET).name("Pin ändern").itemStack();
        ItemStack transferItem = new ItemBuilder(Material.GOLD_NUGGET).name("Geld überweisen").itemStack();

        menu.additem(0, abhebenItem, player1 -> {
            openPinMenu(player1, anvilOutput -> {
                if (rightPin(anvilOutput)) {
                player1.closeInventory();
                player1.sendMessage("Bitte gib die menge an!");
                bankPlayer.setAbhebenChat(true);
                BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
            } else {
                player1.sendMessage("Bitte einen gültigen pin eingeben");
                player1.closeInventory();
            }});
        });

        menu.additem(1, pinChangeItem, player1 -> {
            openPinMenu(player1, anvilOutput -> {
                if (this.rightPin(anvilOutput)) {
                    player1.closeInventory();
                    player1.sendMessage("Bitte gib den neuen pin ein!");
                    bankPlayer.setPinchangeChat(true);
                    BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
                } else {
                    player1.sendMessage("Bitte einen gültigen pin eingeben");
                    player1.closeInventory();
                }
            });
        });

        menu.additem(2, transferItem, player1 -> {
            openPinMenu(player1, anvilOutput -> {
                if (rightPin(anvilOutput)) {
                    player1.closeInventory();
                    player1.sendMessage("Bitte gib die Banknummer des anderen Spielers ein!");
                    bankPlayer.setKontonummerChat(true);
                    BankSystem.getInstance().getBankPlayers().put(player1.getUniqueId(), bankPlayer);
                } else {
                    player1.sendMessage("Bitte einen gültigen pin eingeben");
                    player1.closeInventory();
                }
            });

        });
        menu.openInventory(player);
    }

    public void openPinMenu(Player player, Consumer<AnvilOutput> callback) {
        Bukkit.getConsoleSender().sendMessage("Debug02");
        ItemStack nametag = new ItemBuilder(Material.NAME_TAG).name("pin").itemStack();
        Menu anvil = menuFactory.createCallbackMenu(InventoryType.ANVIL, "Pin eingeben", callback);
        anvil.additem(0, nametag, null);
        anvil.openInventory(player);
    }

    private boolean rightPin(AnvilOutput anvilOutput) {
        Bukkit.getConsoleSender().sendMessage(anvilOutput.getOutput().getItemMeta().getDisplayName());
        Bukkit.getConsoleSender().sendMessage("Debug03");
        String name = anvilOutput.getOutput().getItemMeta().getDisplayName();
        String pin = String.valueOf(coinsInterface.getPin(anvilOutput.getUuid()));

        if (name.equals(pin)) {
            return true;
        }
        return false;
    }
}