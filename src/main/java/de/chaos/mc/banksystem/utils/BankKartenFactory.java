package de.chaos.mc.banksystem.utils;

import de.chaos.mc.banksystem.BankSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class BankKartenFactory {
    public static void createBankKarte(Player player, String kontonummer) {
        String name = "Bankkarte von " + player.getName();

        RandomString gen = new RandomString(6, ThreadLocalRandom.current());

        String kartenID = gen.nextString();

        String lore = "Kontonummer: " + kontonummer + "\n" +
                "KartenID: " + kartenID;


        NamespacedKey card = new NamespacedKey(BankSystem.getInstance(), "card");
        ItemStack karte = new ItemBuilder(Material.NAME_TAG).name(name).setlore(lore).itemStack();
        ItemMeta meta = karte.getItemMeta();
        meta.getPersistentDataContainer().set(card, PersistentDataType.STRING, "card");
        karte.setItemMeta(meta);

        player.getInventory().addItem(karte);
    }

}