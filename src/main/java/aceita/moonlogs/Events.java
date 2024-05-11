package aceita.moonlogs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.sql.SQLException;

public class Events implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (Main.logsJoin) {
            Player player = event.getPlayer();
            Boolean exist = Database.searchDB("uuid", player.getUniqueId().toString());
            if (exist) {
                String last = Database.getDataDB("joinData", "uuid", player.getUniqueId().toString());
                if (last == null) {
                    Database.updateDB("joinData", Util.dateNow() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                } else {
                    Database.updateDB("joinData", last + ";" + Util.dateNow() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                }
            } else {
                Database.insertDB(player.getUniqueId().toString(), player.getName(), null, null, Util.dateNow() + "," + Bukkit.getServerName(), null);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (Main.logsQuit) {
            Player player = event.getPlayer();
            boolean exist = Database.searchDB("uuid", player.getUniqueId().toString());
            if (exist) {
                String last = Database.getDataDB("quitData", "uuid", player.getUniqueId().toString());
                if (last == null) {
                    Database.updateDB("quitData", Util.dateNow() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                } else {
                    Database.updateDB("quitData", last + ";" + Util.dateNow() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                }
            } else {
                Database.insertDB(player.getUniqueId().toString(), player.getName(), null, null, null, Util.dateNow() + "," + Bukkit.getServerName());
            }
        }
    }

    @EventHandler
    public void onMessage(PlayerChatEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (Main.logsMessage) {
            Player player = event.getPlayer();
            boolean exist = Database.searchDB("uuid", player.getUniqueId().toString());
            if (exist) {
                String last = Database.getDataDB("messages", "uuid", player.getUniqueId().toString());
                if (last == null) {
                    Database.updateDB("messages", Util.dateNow() + "," + event.getMessage() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                } else {
                    Database.updateDB("messages", last + ";" + Util.dateNow() + "," + event.getMessage() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                }
            } else {
                Database.insertDB(player.getUniqueId().toString(), player.getName(), null, Util.dateNow() + "," + event.getMessage() + "," + Bukkit.getServerName(), null, null);
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (Main.logsCommand) {
            Player player = event.getPlayer();
            boolean exist = Database.searchDB("uuid", player.getUniqueId().toString());
            if (exist) {
                String last = Database.getDataDB("commands", "uuid", player.getUniqueId().toString());
                if (last == null) {
                    Database.updateDB("commands", Util.dateNow() + "," + event.getMessage() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                } else {
                    Database.updateDB("commands", last + ";" + Util.dateNow() + "," + event.getMessage() + "," + Bukkit.getServerName(), "uuid", player.getUniqueId().toString());
                }
            } else {
                Database.insertDB(player.getUniqueId().toString(), player.getName(), Util.dateNow() + "," + event.getMessage() + "," + Bukkit.getServerName(), null, null, null);
            }
        }
    }
}
