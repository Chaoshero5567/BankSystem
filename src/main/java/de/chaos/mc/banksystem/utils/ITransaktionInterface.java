package de.chaos.mc.banksystem.utils;

import java.util.List;
import java.util.UUID;

public interface ITransaktionInterface {

    public TransaktionLogsDAO newTransaktion(UUID uuid, UUID target, int amount);

    public List<TransaktionLogsDAO> getLastTransaktions(UUID uuid);
}
