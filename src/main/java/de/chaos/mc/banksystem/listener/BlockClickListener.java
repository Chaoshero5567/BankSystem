package de.chaos.mc.banksystem.listener;

import de.chaos.mc.banksystem.config.ItemsConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockClickListener implements Listener {
    ItemsConfig itemsConfig;
    public BlockClickListener(ItemsConfig config) {
        this.itemsConfig = config;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) return;
            Material block = event.getClickedBlock().getType();
            if (Material.getMaterial(itemsConfig.getBankBlock()) != null) {
                Material confBlock = Material.getMaterial(itemsConfig.getBankBlock());
                if (block == confBlock) {
                    player.performCommand("createAccount");
                }
            }
    }
}