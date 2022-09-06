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
@DatabaseTable(tableName = "TRANSAKTIONEN")
public class TransaktionLogsDAO {
    public static final String TRANSAKTIONEN_ID = "TRANSAKTIONS_ID";
    public static final String PLAYER_UUID_FIELD = "UUID";
    public static final String TARGET_UUID_FIELD = "TARGET_UUID";
    public static final String AMOUNT_FIELD = "AMOUNT";
    public static final String DATE_FIELD = "DATE";
    public static final String ANGEZEIGT_FIELD = "ANGEZEIGT";

    @DatabaseField(id = true, columnName = TRANSAKTIONEN_ID, generatedId = true)
    public long id;

    @DatabaseField(columnName = PLAYER_UUID_FIELD)
    public UUID uuid;

    @DatabaseField(columnName = TARGET_UUID_FIELD)
    public UUID target_uuid;

    @DatabaseField(columnName = AMOUNT_FIELD)
    public long amount;

    @DatabaseField(columnName = DATE_FIELD)
    public String date;

    @DatabaseField(columnName = ANGEZEIGT_FIELD)
    public boolean angezeigt;
}