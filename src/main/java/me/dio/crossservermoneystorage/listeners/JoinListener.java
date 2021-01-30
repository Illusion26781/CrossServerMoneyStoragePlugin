package me.dio.crossservermoneystorage.listeners;

import me.dio.crossservermoneystorage.StoragePlugin;
import me.dio.crossservermoneystorage.utilities.storage.sqlutils.SQLManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class JoinListener implements Listener {

    private final StoragePlugin main;


    public JoinListener(StoragePlugin main) {
        this.main = main;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {

        SQLManager manager = new SQLManager(main);
        try {

            main.getEconomy().depositPlayer(e.getPlayer(), manager.getBalance(e.getPlayer().getUniqueId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
