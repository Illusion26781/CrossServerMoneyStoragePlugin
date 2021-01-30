package me.dio.crossservermoneystorage;

import lombok.Getter;
import me.dio.crossservermoneystorage.listeners.JoinListener;
import me.dio.crossservermoneystorage.listeners.LeaveListener;
import me.dio.crossservermoneystorage.utilities.storage.sqlutils.SQLUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

@Getter
public class StoragePlugin extends JavaPlugin
{
    private Connection mySQLConnection;
    private Economy economy;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LeaveListener(this), this);
        setupSQL();
        setupEconomy();
    }

    private void setupSQL() {
        saveDefaultConfig();


        String host = getConfig().getString("database.host", "");
        String database = getConfig().getString("database.database", "");
        String username = getConfig().getString("database.username", "");
        String password = getConfig().getString("database.password", "");
        int port = getConfig().getInt("database.port");

        CompletableFuture.runAsync(() -> {
            SQLUtil sql = new SQLUtil(host, database, username, password, port);


            if (!sql.openConnection()) {
                getLogger().warning("Could not load SQL Database.");
                getLogger().warning("This plugin requires a valid SQL database to work.");
                setEnabled(false);
                return;
            }

            sql.createTable();
            mySQLConnection = sql.getConnection();
        });
    }

    private void setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

    }
}
