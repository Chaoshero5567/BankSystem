package de.chaos.mc.banksystem.listener;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.utils.*;
import de.chaos.mc.banksystem.utils.menus.menuutils.menu.PinObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {
    BankSystem bankSystem;
    ITransaktionInterface iTransaktionInterface;
    ICoinsInterface coinsInterface;

    public ChatListener(BankSystem bankSystem, ITransaktionInterface iTransaktionInterface, ICoinsInterface coinsInterface) {
        this.bankSystem = bankSystem;
        this.iTransaktionInterface = iTransaktionInterface;
        this.coinsInterface = coinsInterface;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {


        Player player = event.getPlayer();


        // Pin Abfrage
        if (bankSystem.getPinItems().containsKey(player.getUniqueId())) {
            PinObject pinObject = bankSystem.getPinItems().get(player.getUniqueId());
            pinObject.setOutput(event.getMessage());
            pinObject.getConsumer().accept(pinObject);
            bankSystem.getPinItems().remove(player.getUniqueId());
        }


        // Normale Abfragen
        if (bankSystem.getBankPlayers().containsKey(player.getUniqueId())) {
            BankPlayer bankPlayer = bankSystem.getBankPlayers().get(player.getUniqueId());

            Bukkit.getConsoleSender().sendMessage("Debug: ");
            Bukkit.getConsoleSender().sendMessage(bankPlayer.toString());
            Bukkit.getConsoleSender().sendMessage(event.getMessage());

            // Asking if Player wants to change pin
            if (bankPlayer.isPinchangeChat()) {
                // Validating Pin Message and length
                Integer value = Integer.valueOf(event.getMessage());
                if (value != null) {
                    if (event.getMessage().length() == 4) {
                        // Transfering money and setting status to neutral
                        ChangePin.change(player, value);
                    } else player.sendMessage("Pin muss 4 Nummern lang sein");
                } else {
                    player.sendMessage("Bitte gib eine valide zahlt ein");
                }
            }
            // Asking if player wants to get money from his account
            if (bankPlayer.isAbhebenChat()) {
                // Validating amount Message
                Integer amount = Integer.parseInt(event.getMessage());
                if (amount != null) {
                    if (coinsInterface.hasEnoughCoins(player.getUniqueId(), amount)) {
                        // Changing Pin and setting status to neutral
                        coinsInterface.removeCoinsBank(player.getUniqueId(), amount);
                        coinsInterface.addCoins(player.getUniqueId(), amount);
                        bankPlayer.setAbhebenChat(false);
                        bankSystem.getBankPlayers().put(player.getUniqueId(), bankPlayer);
                    } else player.sendMessage("Du hast nicht genug Geld");
                } else {
                    player.sendMessage("Bitte gib eine valide zahlt ein");
                }
            }

            if (bankPlayer.isKontonummerChat()) {
                String Kontonummer = event.getMessage();
                if (coinsInterface.isValidKonto(Kontonummer)) {
                    bankPlayer.setKontonummerChat(false);
                    bankPlayer.setTransaktionAmount(true);
                    bankPlayer.setTargetKontonummer(Kontonummer);
                    player.sendMessage("Wie viel willst du überweisen?");
                    bankSystem.getBankPlayers().put(player.getUniqueId(), bankPlayer);
                } else {
                    player.sendMessage("Bitte gib eine valide Kontonummer ein");
                }
            }
            if (bankPlayer.isTransaktionAmount()) {
                String Kontonummer = event.getMessage();

                String text = event.getMessage();
                if (text.matches("[0-9]+")) {
                    int amount = Integer.parseInt(text);
                    if (coinsInterface.hasEnoughCoins(player.getUniqueId(), amount)) {

                        Transaktion.transferMoney(player, Bukkit.getPlayer(coinsInterface.getUUID(bankPlayer.getTargetKontonummer())), amount);

                        bankPlayer.setTransaktionAmount(false);
                        bankSystem.getBankPlayers().put(player.getUniqueId(), bankPlayer);
                    } else {
                        player.sendMessage("Du hast nicht genug Geld");
                    }
                } else {
                    player.sendMessage("Bitte gib eine valide Zahl ein");
                }
            }
            event.setMessage(null);
        }
    }
}