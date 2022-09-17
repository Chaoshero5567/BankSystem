package de.chaos.mc.banksystem.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BankPlayer {
    public boolean abhebenChat;
    public boolean kontonummerChat;
    public boolean transaktionAmount;
    public boolean pinchangeChat;
    protected UUID uuid;

    public BankPlayer(UUID uuid, boolean b1, boolean b2, boolean b3, boolean b4) {
        this.uuid = uuid;
        this.abhebenChat = b1;
        this.kontonummerChat = b2;
        this.kontonummerChat = b3;
        this.pinchangeChat = b4;
    }
}
