package de.chaos.mc.banksystem.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TransaktionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final UUID sender;
    private final UUID target;
    private int amount;


    @NotNull
    public UUID getSender() {
        return sender;
    }

    @NotNull
    public UUID getTarget() {
        return target;
    }

    @NotNull
    public int getAmount() {
        return amount;
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
