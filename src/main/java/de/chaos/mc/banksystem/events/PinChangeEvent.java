package de.chaos.mc.banksystem.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
@Getter
@Setter
public class PinChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final HumanEntity player;
    private int oldPin;
    private int newPin;


    public HumanEntity getPlayer() {
        return player;
    }

    public int getNewPin() {
        return newPin;
    }

    public int getOldPin() {
        return oldPin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

