package de.chaos.mc.banksystem.utils;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.banksystem.BankSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransaktionRepository implements ITransaktionInterface{
    public JdbcPooledConnectionSource connectionSource;
    public ICoinsInterface iCoinsInterface;
    public DAOManager<TransaktionLogsDAO, Long> daoManager;
    public TransaktionRepository(JdbcPooledConnectionSource jdbcPooledConnectionSource) {
        this.connectionSource = jdbcPooledConnectionSource;
        this.daoManager = new DAOManager<TransaktionLogsDAO, Long>(TransaktionLogsDAO.class, connectionSource);
        this.iCoinsInterface = BankSystem.getInstance().getICoinsInterface();
    }

    @Override
    public TransaktionLogsDAO newTransaktion(UUID uuid, UUID target, long amount) {
        LocalDate localDate = LocalDate.now();
        String date = localDate.getDayOfMonth() + "-" + localDate.getMonth() + "-" +  localDate.getYear();

        TransaktionLogsDAO transaktionLogsDAO = TransaktionLogsDAO.builder()
                .uuid(uuid)
                .target_uuid(target)
                .amount(amount)
                .date(date)
                .build();
        try {
            daoManager.getDAO().update(transaktionLogsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        iCoinsInterface.addCoins(target, amount);
        iCoinsInterface.removeCoins(uuid, amount);

        Bukkit.getPlayer(uuid).sendMessage(Component.text("-" + amount + " zu " + Bukkit.getPlayer(target).getDisplayName()));
        Bukkit.getPlayer(target).sendMessage(Component.text("+" + amount + "von" + Bukkit.getPlayer(uuid).getDisplayName()));

        return transaktionLogsDAO;
    }

    @Override
    public List<TransaktionLogsDAO> getLastTransaktions(UUID uuid) {
        List<TransaktionLogsDAO> TransaktionList = new ArrayList<>();

        try {
            List<TransaktionLogsDAO> daos = daoManager.getDAO().queryForAll();
            int amount = 0;

            for (TransaktionLogsDAO dao : daos) {
                if (amount >= 5) {
                    daos.add(dao);
                    amount++;
                } else {
                    break;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return TransaktionList;
    }
}
