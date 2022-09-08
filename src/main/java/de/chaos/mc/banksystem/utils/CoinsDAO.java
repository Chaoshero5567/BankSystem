package de.chaos.mc.banksystem.utils;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "Coins")
public class CoinsDAO {
    public static final String PLAYER_UUID_FIELD = "UUID";
    public static final String COINS_FIELD = "COINS";
    public static final String BANK_COINS_FIELD = "BANK_COINS";
    public static final String KONTO_NUMMER = "KONTO_NUMMER";
    public static final String PIN_FIELD = "PIN";

    @DatabaseField(id = true, columnName = PLAYER_UUID_FIELD)
    public UUID uuid;

    @DatabaseField(columnName = COINS_FIELD)
    public long coins;

    @DatabaseField(id = true, columnName = KONTO_NUMMER)
    public String kontoNummer;

    @DatabaseField(columnName = PIN_FIELD)
    public int pin;

    @DatabaseField(columnName = BANK_COINS_FIELD)
    public int bankCoins;
}