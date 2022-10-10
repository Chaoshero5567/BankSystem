package de.chaos.mc.banksystem.listener;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.config.ItemsConfig;
import de.chaos.mc.banksystem.utils.BankKartenFactory;
import de.chaos.mc.banksystem.utils.CoinsDAO;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import de.chaos.mc.banksystem.utils.menus.BankMenus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockClickListener implements Listener {
    ItemsConfig itemsConfig;

    public BlockClickListener(ItemsConfig config) {
        this.itemsConfig = config;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        BankMenus bankMenus = BankSystem.getInstance().getBankMenus();
        ICoinsInterface iCoinsInterface = BankSystem.getInstance().getICoinsInterface();
        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) return;
        Material block = event.getClickedBlock().getType();
        if (Material.getMaterial(itemsConfig.getBankBlock()) != null) {
            Material confBlock = Material.getMaterial(itemsConfig.getBankBlock());
            if (block == confBlock) {
                event.setCancelled(true);
                Material cardItem = Material.getMaterial(itemsConfig.getBankkartenItem());
                if (player.getItemInHand().getType() == cardItem) {
                    ItemStack item = player.getItemInHand();
                    NamespacedKey card = new NamespacedKey(BankSystem.getInstance(), "card");
                    if (item.getItemMeta().getPersistentDataContainer().getKeys().contains(card)) {
                        bankMenus.openMenu(player);
                    }
                } else {
                    if (player.getItemInHand().getType() == Material.AIR) {
                        if (!iCoinsInterface.hasAccount(player.getUniqueId())) {
                            CoinsDAO coinsDAO = iCoinsInterface.createAccount(player.getUniqueId());
                            BankKartenFactory.createBankKarte(player, coinsDAO.getKontoNummer());
                            player.sendMessage("Bank account erstellt");
                            player.sendMessage("Dein Pin ist " + coinsDAO.getPin() + ". Diesen bitte aufschreiben und nicht verlieren!");
                        }
                    }
                }
            }
        }
    }
}