package de.chaos.mc.banksystem.commands.uicommands;

import de.chaos.mc.banksystem.events.TransaktionEvent;
import de.chaos.mc.banksystem.utils.ITransaktionInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TrasnferMoneyCommand implements CommandExecutor {
    ITransaktionInterface transaktionInterface;

    public TrasnferMoneyCommand(ITransaktionInterface transaktionInterface) {
        this.transaktionInterface = transaktionInterface;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 3) {
            Player player = (Player) sender;
            UUID target = UUID.fromString(args[0]);
            int amount = Integer.parseInt(args[1]);
            Boolean gui = Boolean.valueOf(args[2]);
            if (gui) {
                transaktionInterface.newTransaktion(player.getUniqueId(), target, amount);
                TransaktionEvent event = new TransaktionEvent(player.getUniqueId(), target, amount);
                Bukkit.getServer().getPluginManager().callEvent(event);
            } else {
                player.sendMessage("This is a GuiLocked command");
            }
        } else {
            sender.sendMessage("This is a GuiLocked command");
        }
        return false;
    }
}