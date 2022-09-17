package de.chaos.mc.banksystem.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class PinChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final HumanEntity player;
    private int oldPin;
    private int newPin;


    @NotNull
    public HumanEntity getPlayer() {
        return player;
    }

    @NotNull
    public int getNewPin() {
        return newPin;
    }

    @NotNull
    public int getOldPin() {
        return oldPin;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

