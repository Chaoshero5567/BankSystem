package de.chaos.mc.banksystem.commands.uicommands;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.events.PinChangeEvent;
import de.chaos.mc.banksystem.utils.BankPlayer;
import de.chaos.mc.banksystem.utils.ICoinsInterface;
import org.bukkit.Bukkit;
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
    public boolean onCommand( CommandSender sender,  Command command,  String arg,  String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                int pin = Integer.parseInt(args[0]);
                coinsInterface.changePing(player.getUniqueId(), pin);

                int oldPin = coinsInterface.getPin(player.getUniqueId());
                coinsInterface.changePing(player.getUniqueId(),pin);

                BankPlayer bankPlayer = BankSystem.getInstance().getBankPlayers().get(player.getUniqueId());
                bankPlayer.setPinchangeChat(false);
                BankSystem.getInstance().getBankPlayers().put(player.getUniqueId(), bankPlayer);

                // Creating PinChangeEvent
                PinChangeEvent pinChangeEvent = new PinChangeEvent(player, oldPin, coinsInterface.getPin(player.getUniqueId()));
                // Calling PinChangeEvent
                Bukkit.getServer().getPluginManager().callEvent(pinChangeEvent);
            }
        } else {
            return false;
        }
        return false;
    }
}
