package de.chaos.mc.banksystem.commands.playercommands;

import de.chaos.mc.banksystem.utils.ICoinsInterface;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class walletCommand implements CommandExecutor {
    ICoinsInterface iCoinsInterface;

    public walletCommand(ICoinsInterface iCoinsInterface) {
        this.iCoinsInterface = iCoinsInterface;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0 || args.length == 1) {
                if (args.length == 0) {
                    player.sendMessage(Component.text(iCoinsInterface.getCoins(player.getUniqueId())));
                } else {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        player.sendMessage(Component.text(iCoinsInterface.getCoins(Bukkit.getPlayer(args[0]).getUniqueId())));
                    } else {
                        player.sendMessage("Player not online");
                    }
                }
            } else {
                player.sendMessage("Not a valid amount of args");
            }
        }
        return false;
    }
}