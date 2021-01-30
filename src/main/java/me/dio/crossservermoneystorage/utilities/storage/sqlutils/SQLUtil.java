package me.dio.crossservermoneystorage.utilities.storage.sqlutils;

import lombok.Getter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Getter
public class SQLUtil {


    private Connection connection;

    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    private File file;

    public SQLUtil(File file) {
        this.file = file;
    }

    public SQLUtil(String host, String database, String username, String password, int port) {

        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public boolean openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return true;
            }
            Class.forName("com.mysql.jdbc.Driver");
            if (file == null)
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            else
                connection = DriverManager.getConnection("jdbc:sqlite:" + file);

            connection.setAutoCommit(true);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void createTable() {
        CompletableFuture.runAsync(() -> {
            try {
                connection.createStatement().execute(SQLOperations.CREATE_TABLE);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

}