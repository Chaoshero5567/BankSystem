package de.chaos.mc.banksystem.utils;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.banksystem.BankSystem;
import de.chaos.mc.banksystem.config.ItemsConfig;
import de.chaos.mc.banksystem.events.TransaktionEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransaktionRepository implements ITransaktionInterface {
    public JdbcPooledConnectionSource connectionSource;
    public ICoinsInterface iCoinsInterface;
    public DAOManager<TransaktionLogsDAO, Long> daoManager;
    ItemsConfig itemsConfig;

    public TransaktionRepository(JdbcPooledConnectionSource jdbcPooledConnectionSource, ItemsConfig config) {
        this.connectionSource = jdbcPooledConnectionSource;
        this.daoManager = new DAOManager<TransaktionLogsDAO, Long>(TransaktionLogsDAO.class, connectionSource);
        this.iCoinsInterface = BankSystem.getInstance().getICoinsInterface();
        this.itemsConfig = config;
    }

    @Override
    public TransaktionLogsDAO newTransaktion(UUID uuid, UUID target, int amount) {
        LocalDate localDate = LocalDate.now();
        String dayOfMonth = String.valueOf(localDate.getDayOfMonth());
        String month = localDate.getMonth().toString();
        String year = String.valueOf(localDate.getYear());
        String date = dayOfMonth + " - " + month + " - " + year;

                TransaktionLogsDAO transaktionLogsDAO = TransaktionLogsDAO.builder()
                .uuid(uuid)
                .target_uuid(target)
                .amount(amount)
                .date(date)
                .angezeigt(false)
                .build();
        try {
            daoManager.getDAO().update(transaktionLogsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        iCoinsInterface.addCoinsBank(target, amount);
        iCoinsInterface.removeCoinsBank(uuid, amount);

        Bukkit.getPlayer(uuid).sendMessage(Component.text("-" + amount + " zu " + Bukkit.getPlayer(target).getDisplayName()));
        Bukkit.getPlayer(target).sendMessage(Component.text("+" + amount + "von" + Bukkit.getPlayer(uuid).getDisplayName()));

        TransaktionEvent event = new TransaktionEvent(uuid, target, amount);
        Bukkit.getServer().getPluginManager().callEvent(event);

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
                    if (!dao.angezeigt) {
                        daos.add(dao);
                        dao.setAngezeigt(true);
                        daoManager.getDAO().update(dao);
                        amount++;
                    }
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
