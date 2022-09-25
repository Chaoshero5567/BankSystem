package de.chaos.mc.banksystem.utils.menus.menuutils.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class AnvilOutput {
    public UUID uuid;
    public ItemStack output;
}