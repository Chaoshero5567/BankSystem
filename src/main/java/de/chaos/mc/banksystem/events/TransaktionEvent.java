package de.chaos.mc.banksystem.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TransaktionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final UUID sender;
    private final UUID target;
    private int amount;


    
    public UUID getSender() {
        return sender;
    }

    
    public UUID getTarget() {
        return target;
    }

    
    public int getAmount() {
        return amount;
    }

    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
