package de.chaos.mc.banksystem.utils;

import java.util.UUID;

public interface ICoinsInterface {
    public long getCoins(UUID uuid);
    public long setCoins(UUID uuid, long coins);
    public long addCoins(UUID uuid, long coins);
    public long removeCoins(UUID uuid, long coins);
    public boolean hasEnoughCoins(UUID uuid, long amount);

    public long changePing(UUID uuid, int pin);

    public long createAccount(UUID uuid);

    public String getKontonummer(UUID uuid);
}
