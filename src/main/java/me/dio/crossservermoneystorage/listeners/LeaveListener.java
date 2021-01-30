package me.dio.crossservermoneystorage.listeners;

import me.dio.crossservermoneystorage.StoragePlugin;
import me.dio.crossservermoneystorage.utilities.storage.sqlutils.SQLManager;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class LeaveListener implements Listener {

    private final StoragePlugin main;


    public LeaveListener(StoragePlugin main) {
        this.main = main;
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {

        SQLManager manager = new SQLManager(main);
        try {
            double balance = main.getEconomy().getBalance(e.getPlayer());

            main.getEconomy().withdrawPlayer(e.getPlayer(), balance);
            manager.storeBalance(e.getPlayer().getUniqueId(), balance);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
