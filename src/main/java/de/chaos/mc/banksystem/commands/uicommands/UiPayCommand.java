package de.chaos.mc.banksystem.commands.uicommands;

import de.chaos.mc.serverapi.ServerAPIBukkitMain;
import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.PlayerLanguage;
import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UiPayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerLanguage playerLanguage = ServerAPIBukkitMain.getOnlinePlayers().get(player.getUniqueId());
            if (args.length == 2) {
                if (CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(args[0]).isOnline()) {
                    ICloudPlayer target = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(args[0]);
                    Long amount = Long.valueOf(args[1]);
                    if (amount != null) {
                        if (ServerAPIBukkitMain.getApi().getCoinsInterface().hasEnoughCoins(player.getUniqueId(), amount)) {
                            PlayerLanguage targetLanguage = ServerAPIBukkitMain.getOnlinePlayers().get(target.getUniqueId());
                            ServerAPIBukkitMain.getApi().getCoinsInterface().removeCoins(player.getUniqueId(), amount);
                            ServerAPIBukkitMain.getApi().getCoinsInterface().addCoins(target.getUniqueId(), amount);
                            target.sendMessage(targetLanguage.recievedMoney(player, amount));
                        } else {
                            player.sendMessage(playerLanguage.getNotEnoughCoins());
                        }
                    } else {
                        player.sendMessage(playerLanguage.getPlsGiveValidCoinsAmount());
                    }
                } else {
                    player.sendMessage(playerLanguage.getPlayerNotOnline());
                }
            } else {
                player.sendMessage(AbstractMessages.wrongSyntax("/pay [Spieler] [Summe]"));
            }
        } else {
            sender.sendMessage(AbstractMessages.BEAPLAYER);
        }
        return true;
    }
}
