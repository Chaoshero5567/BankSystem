package de.chaos.mc.banksystem.commands;

import de.chaos.mc.banksystem.utils.ICoinsInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChangePinCommand implements CommandExecutor {
    public ICoinsInterface coinsInterface;

    public ChangePinCommand(ICoinsInterface coinsInterface) {
        this.coinsInterface = coinsInterface;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String arg, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                int pin = Integer.parseInt(args[0]);
                coinsInterface.changePing(player.getUniqueId(), pin);
            }
        } else {
            return false;
        }
        return false;
    }
}
