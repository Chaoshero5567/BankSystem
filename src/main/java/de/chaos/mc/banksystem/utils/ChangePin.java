package de.chaos.mc.banksystem.utils;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.events.PinChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChangePin {
    public static ICoinsInterface coinsInterface = BankSystem.getInstance().getICoinsInterface();

    public static void change(Player player, int newpin) {
        int oldPin = coinsInterface.getPin(player.getUniqueId());

        BankPlayer bankPlayer = BankSystem.getInstance().getBankPlayers().get(player.getUniqueId());
        bankPlayer.setPinchangeChat(false);
        BankSystem.getInstance().getBankPlayers().put(player.getUniqueId(), bankPlayer);
        coinsInterface.changePing(player.getUniqueId(), newpin);

        // Creating PinChangeEvent
        PinChangeEvent pinChangeEvent = new PinChangeEvent(player, oldPin, coinsInterface.getPin(player.getUniqueId()));
        // Calling PinChangeEvent
        Bukkit.getServer().getPluginManager().callEvent(pinChangeEvent);
    }
}
