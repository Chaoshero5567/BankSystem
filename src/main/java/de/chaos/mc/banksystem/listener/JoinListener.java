package de.chaos.mc.banksystem.listener;

import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.utils.BankPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BankPlayer bankPlayer = new BankPlayer(player.getUniqueId(), false, false, false, false);
        BankSystem.getInstance().getBankPlayers().put(player.getUniqueId(), bankPlayer);
    }
}
