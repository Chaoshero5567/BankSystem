package de.chaos.mc.banksystem.utils;

import java.util.List;
import java.util.UUID;

public interface ICoinsInterface {
    long getCoins(UUID uuid);

    long setCoinsBank(UUID uuid, long coins);

    long setWalletCoins(UUID uuid, long coins);

    long addCoins(UUID uuid, long coins);

    long removeCoins(UUID uuid, long coins);

    long addCoinsBank(UUID uuid, long coins);

    long removeCoinsBank(UUID uuid, long coins);

    long getCoinsBank(UUID uuid);

    boolean hasEnoughCoins(UUID uuid, long amount);

    long changePing(UUID uuid, int pin);

    CoinsDAO createAccount(UUID uuid);

    String getKontonummer(UUID uuid);

    int getPin(UUID uuid);

    boolean isValidKonto(String kontonummer);

    UUID getUUID(String Kontonummer);

    boolean hasAccount(UUID uuid);

    List<CoinsDAO> getAllAccounts();
}
