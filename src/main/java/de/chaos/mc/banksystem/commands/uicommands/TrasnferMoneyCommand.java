package de.chaos.mc.banksystem.commands.uicommands;

import de.chaos.mc.banksystem.utils.ITransaktionInterface;
import de.chaos.mc.banksystem.utils.Transaktion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrasnferMoneyCommand implements CommandExecutor {
    ITransaktionInterface transaktionInterface;

    public TrasnferMoneyCommand(ITransaktionInterface transaktionInterface) {
        this.transaktionInterface = transaktionInterface;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            int amount = Integer.parseInt(args[1]);

            Transaktion.transferMoney(player, target, amount);

        } else {
            sender.sendMessage("This is a GuiLocked command");
        }
        return false;
    }
}