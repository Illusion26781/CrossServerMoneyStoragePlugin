package me.dio.crossservermoneystorage.utilities.storage.sqlutils;


import me.dio.crossservermoneystorage.StoragePlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SQLManager {
    private final StoragePlugin plugin;

    public SQLManager(StoragePlugin plugin) {
        this.plugin = plugin;
    }

    public void storeBalance(UUID id, double balance) throws SQLException {
        CompletableFuture.runAsync(() -> {
            PreparedStatement statement = null;
            try {
                statement = plugin.getMySQLConnection().prepareStatement(SQLOperations.SAVE);
                statement.setString(1, id.toString());
                statement.setDouble(2, balance);

                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        });
    }

    public Double getBalance(UUID id) throws SQLException {
        PreparedStatement statement = plugin.getMySQLConnection().prepareStatement(SQLOperations.OBTAIN);

        statement.setString(1, id.toString());

        ResultSet result = statement.executeQuery();

        result.first();

        double money = result.getDouble("balance");

        statement.close();
        result.close();

        return money;
    }

}
