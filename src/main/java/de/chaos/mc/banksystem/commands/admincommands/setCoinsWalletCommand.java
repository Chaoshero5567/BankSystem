package de.chaos.mc.banksystem.commands.admincommands;


import de.chaos.mc.banksystem.utils.ICoinsInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setCoinsWalletCommand implements CommandExecutor {
    ICoinsInterface coinsInterface;

    public setCoinsWalletCommand(ICoinsInterface coinsInterface) {
        this.coinsInterface = coinsInterface;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("Coins.setCoins")) {
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0])!= null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Long amount = Long.valueOf(args[1]);
                        if (amount != null) {
                            coinsInterface.setWalletCoins(target.getUniqueId(), amount);
                        } else {
                            player.sendMessage("Pls give valid amount");
                        }
                    } else {
                        player.sendMessage("Player not online");
                    }
                } else {
                    player.sendMessage("/pay [Player] [Amount]");
                }
            } else {
                player.sendMessage("NoPermission");
            }
        }
        return false;
    }
}
