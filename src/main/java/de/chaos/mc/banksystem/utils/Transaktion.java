package de.chaos.mc.banksystem.utils;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.events.TransaktionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Transaktion {
    public static ITransaktionInterface transaktionInterface = BankSystem.getInstance().getTransaktionInterface();

    public static void transferMoney(Player player, Player target, int amount) {
        transaktionInterface.newTransaktion(player.getUniqueId(), target.getUniqueId(), amount);
        TransaktionEvent event = new TransaktionEvent(player.getUniqueId(), target.getUniqueId(), amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
