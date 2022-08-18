package de.chaos.mc.banksystem.utils;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class CoinsRepository implements ICoinsInterface {
    public JdbcPooledConnectionSource connectionSource;
    public DAOManager<CoinsDAO, UUID> daoManager;
    public CoinsRepository(JdbcPooledConnectionSource jdbcPooledConnectionSource) {
        this.connectionSource = jdbcPooledConnectionSource;
        this.daoManager = new DAOManager<CoinsDAO, UUID>(CoinsDAO.class, connectionSource);
    }



    @Override
    public long getCoins(UUID uuid) {
        CoinsDAO coinsDAO = null;
        try {
            coinsDAO = daoManager.getDAO().queryForId(uuid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        assert coinsDAO != null;
        return coinsDAO.getCoins();
    }

    @Override
    public long setCoins(UUID uuid, long coins) {
        try {
            CoinsDAO coinsDAO = daoManager.getDAO().queryForId(uuid);
            coinsDAO.setCoins(coins);
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return getCoins(uuid);
    }

    @Override
    public long addCoins(UUID uuid, long coins) {
        long l = Math.addExact(getCoins(uuid), coins);
        try {
            CoinsDAO coinsDAO = daoManager.getDAO().queryForId(uuid);
            coinsDAO.setCoins(l);
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return getCoins(uuid);
    }

    @Override
    public long removeCoins(UUID uuid, long coins) {
        long l = Math.subtractExact(getCoins(uuid), coins);
        try {
            CoinsDAO coinsDAO = daoManager.getDAO().queryForId(uuid);
            coinsDAO.setCoins(l);
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return getCoins(uuid);
    }

    @Override
    public boolean hasEnoughCoins(UUID uuid, long amount) {
        if (getCoins(uuid) >= amount) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long changePing(UUID uuid, String pin) {
        try {
            CoinsDAO coinsDAO = daoManager.getDAO().queryForId(uuid);
            coinsDAO.setPin(pin);
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


        return 0;
    }

    @Override
    public long createAccount(UUID uuid) {
        String pin = String.format("%04d", new Random(10000));
        String Kontonummer = new RandomString(8).toString();


        CoinsDAO coinsDAO = CoinsDAO.builder()
                .uuid(uuid)
                .coins(1000)
                .pin(pin)
                .kontoNummer(Kontonummer)
                .build();

        return 0;
    }

    @Override
    public String getKontonummer(UUID uuid) {
        String kontonummer = null;
        try {
            CoinsDAO coinsDAO = daoManager.getDAO().queryForId(uuid);
            kontonummer = coinsDAO.getKontoNummer();
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return kontonummer;
    }
}
