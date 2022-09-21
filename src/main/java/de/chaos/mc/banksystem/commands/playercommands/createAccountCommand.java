package de.chaos.mc.banksystem.commands.playercommands;

import de.chaos.mc.banksystem.config.ItemsConfig;
import de.chaos.mc.banksystem.utils.BankKartenFactory;
import de.chaos.mc.banksystem.utils.CoinsDAO;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import de.chaos.mc.banksystem.utils.menus.BankMenus;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class createAccountCommand implements CommandExecutor {
    ItemsConfig itemsConfig;
    BankMenus bankMenus;
    ICoinsInterface iCoinsInterface;

    public createAccountCommand(ItemsConfig itemsConfig, BankMenus bankMenus, ICoinsInterface iCoinsInterface) {
        this.itemsConfig = itemsConfig;
        this.bankMenus = bankMenus;
        this.iCoinsInterface = iCoinsInterface;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Material cardItem = Material.getMaterial(itemsConfig.getBankkartenItem());
            if (player.getItemInHand().getType() == cardItem) {
                ItemStack item = player.getItemInHand();
                if (item.lore().contains(iCoinsInterface.getKontonummer(player.getUniqueId()))) {
                    bankMenus.openMenu(player);
                }
            } else {
                if (player.getItemInHand().getType() == Material.AIR) {
                    if (!iCoinsInterface.hasAccount(player.getUniqueId())) {
                        CoinsDAO coinsDAO = iCoinsInterface.createAccount(player.getUniqueId());
                        BankKartenFactory.createBankKarte(player, coinsDAO.getKontoNummer());
                        player.sendMessage(Component.text("Bank account erstellt"));
                        player.sendMessage(Component.text("Dein Pin ist " + coinsDAO.getPin()) + ". Diesen bitte aufschreiben und nicht verlieren!");
                    }
                }
            }
        }
        return false;
    }
}
