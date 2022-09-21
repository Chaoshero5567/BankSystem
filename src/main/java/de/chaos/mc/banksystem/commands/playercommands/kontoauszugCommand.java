package de.chaos.mc.banksystem.commands.playercommands;

import de.chaos.mc.banksystem.utils.ITransaktionInterface;
import de.chaos.mc.banksystem.utils.ItemBuilder;
import de.chaos.mc.banksystem.utils.TransaktionLogsDAO;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class kontoauszugCommand implements CommandExecutor {
    ITransaktionInterface transaktionInterface;

    public kontoauszugCommand(ITransaktionInterface iTransaktionInterface) {
        this.transaktionInterface = iTransaktionInterface;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            for (TransaktionLogsDAO dao : transaktionInterface.getLastTransaktions(player.getUniqueId())) {
                String LORE = "Kontoauszug: /n" +
                        "Datum: " + dao.getDate() + "/n" +
                        "Amount: " + dao.getAmount() + "/n" +
                        "TransaktionsID: " + dao.getId() + "/n" +
                        "Empfänger: " + dao.getTarget_uuid();

                ItemStack auszug = new ItemBuilder(Material.BOOK).name("Kontauszug vom " + dao.getDate()).setlore(LORE).itemStack();
                player.getInventory().addItem(auszug);
            }
        }
        return false;
    }
}
