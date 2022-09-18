package de.chaos.mc.banksystem.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BankKartenFactory {
    public static void createBankKarte(Player player, String kontonummer) {
        String name = "Bankkarte von " + player.getName();

        RandomString gen = new RandomString(6, ThreadLocalRandom.current());

        String kartenID = gen.nextString();

        String lore = "Kontonummer: " + kontonummer + "/n" +
                      "KartenID: " + kartenID;

        ItemStack karte = new ItemBuilder(Material.NAME_TAG).name(name).setlore(lore).itemStack();
    }

}