package de.chaos.mc.banksystem.commands.uicommands;

import de.chaos.mc.banksystem.utils.ChangePin;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePinCommand implements CommandExecutor {
    public ICoinsInterface coinsInterface;

    public ChangePinCommand(ICoinsInterface coinsInterface) {
        this.coinsInterface = coinsInterface;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                int pin = Integer.parseInt(args[0]);
                ChangePin.change(player, pin);
            }
        } else {
            return false;
        }
        return false;
    }
}
