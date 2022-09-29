package de.chaos.mc.banksystem.listener;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.utils.BankPlayer;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import de.chaos.mc.banksystem.utils.ITransaktionInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
    public void onPlayerChat(AsyncPlayerChatEvent event) {


        Player player = event.getPlayer();
        if (bankSystem.getBankPlayers().containsKey(player.getUniqueId())) {
            BankPlayer bankPlayer = bankSystem.getBankPlayers().get(player.getUniqueId());

            Bukkit.getConsoleSender().sendMessage("Debug: ");
            Bukkit.getConsoleSender().sendMessage(bankPlayer.toString());

            // Asking if Player wants to change pin
            if (bankPlayer.isPinchangeChat()) {
                // Canceling event so no message appears in chat
                event.setCancelled(true);
                // Validating Pin format and length
                String text = event.getMessage();
                if (text.matches("[0-9]+") && text.length() > 2) {
                    if (text.length() == 4) {
                        // Transfering money and setting status to neutral
                        event.setCancelled(true);
                        player.performCommand("changePin " + Integer.parseInt(text));
                    } else player.sendMessage("Pin muss 4 Nummern lang sein");
                } else {
                    player.sendMessage("Bitte gib eine valide zahlt ein");
                }
            }
            // Asking if player wants to get money from his account
            if (bankPlayer.isAbhebenChat()) {
                // Canceling event so no message appears in chat
                event.setCancelled(true);
                // Validating amount format
                String text = event.getMessage();
                if (text.matches("[0-9]+")) {
                    int amount = Integer.parseInt(text);
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
                // Canceling event so no message appears in chat
                event.setCancelled(true);
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
                // Canceling event so no message appears in chat
                event.setCancelled(true);
                String Kontonummer = event.getMessage();

                String text = event.getMessage();
                if (text.matches("[0-9]+")) {
                  int amount = Integer.parseInt(text);
                  if (coinsInterface.hasEnoughCoins(player.getUniqueId(), amount)) {

                      player.performCommand("transferMoney " + coinsInterface.getUUID(bankPlayer.getTargetKontonummer()) + " " + amount + " " + true);

                      bankPlayer.setTransaktionAmount(false);
                      bankSystem.getBankPlayers().put(player.getUniqueId(), bankPlayer);
                  } else {
                      player.sendMessage("Du hast nicht genug Geld");
                  }
                } else {
                    player.sendMessage("Bitte gib eine valide Zahl ein");
                }
            }
        }
    }
}