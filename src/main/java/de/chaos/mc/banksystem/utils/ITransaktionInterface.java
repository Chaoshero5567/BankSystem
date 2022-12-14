package de.chaos.mc.banksystem.utils;

import java.util.List;
import java.util.UUID;

public interface ITransaktionInterface {

    TransaktionLogsDAO newTransaktion(UUID uuid, UUID target, int amount);

    List<TransaktionLogsDAO> getLastTransaktions(UUID uuid);
}
