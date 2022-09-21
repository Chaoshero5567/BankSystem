package de.chaos.mc.banksystem.utils;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
    public long setCoinsBank(UUID uuid, long coins) {
        try {
            CoinsDAO coinsDAO = daoManager.getDAO().queryForId(uuid);
            coinsDAO.setCoins(coins);
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return getCoinsBank(uuid);
    }

    @Override
    public long setWalletCoins(UUID uuid, long coins) {
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
    public long addCoinsBank(UUID uuid, long coins) {
        long l = getCoinsBank(uuid) + coins;
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
    public long removeCoinsBank(UUID uuid, long coins) {
        long l = getCoinsBank(uuid) - coins;
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
    public long getCoinsBank(UUID uuid) {
        CoinsDAO coinsDAO = null;
        try {
            coinsDAO = daoManager.getDAO().queryForId(uuid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        assert coinsDAO != null;
        return coinsDAO.getBankCoins();
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
    public long changePing(UUID uuid, int pin) {
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
    public CoinsDAO createAccount(UUID uuid) {
        RandomString pinGen = new RandomString(4, ThreadLocalRandom.current(), RandomString.digits);
        int pin = Integer.parseInt(pinGen.nextString());

        RandomString kontoGen = new RandomString(8, ThreadLocalRandom.current());

        String kontonummer = kontoGen.nextString();
        while (isValidKonto(kontonummer)) {
            kontonummer = kontoGen.nextString();
        }

        CoinsDAO coinsDAO = CoinsDAO.builder()
                .uuid(uuid)
                .coins(0)
                .bankCoins(1000)
                .pin(pin)
                .kontoNummer(kontonummer)
                .build();

        try {
            daoManager.getDAO().update(coinsDAO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coinsDAO;
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

    @Override
    public int getPin(UUID uuid) {
        int pin = 0;
        try {
            pin = daoManager.getDAO().queryForId(uuid).getPin();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pin;
    }

    @Override
    public boolean isValidKonto(String kontonummer) {
        AtomicBoolean b = new AtomicBoolean(false);
        if (getAllAccounts().isEmpty()) {
            return false;
        }
        getAllAccounts().forEach(coinsDAO -> {
            if (!b.get()) {
                if (coinsDAO.getKontoNummer() == kontonummer) {
                    b.set(true);
                }
            }
        });
        return b.get();
    }

    @Override
    public UUID getUUID(String Kontonummer) {
        AtomicReference<UUID> uuid = null;
        getAllAccounts().forEach(coinsDAO -> {
            if (coinsDAO.kontoNummer == Kontonummer) {
                uuid.set(coinsDAO.getUuid());
            }
        });
        return uuid.get();
    }

    @Override
    public boolean hasAccount(UUID uuid) {
        try {
            if (daoManager.getDAO().queryForId(uuid) != null) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<CoinsDAO> getAllAccounts() {
        List<CoinsDAO> daos = new ArrayList<>();
        try {
            daos = daoManager.getDAO().queryForAll();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return daos;
    }
}