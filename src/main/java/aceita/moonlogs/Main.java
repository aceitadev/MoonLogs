package aceita.moonlogs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public final class Main extends JavaPlugin {

    public static File pluginFolder = null;
    public static Boolean logsJoin;
    public static Boolean logsQuit;
    public static Boolean logsMessage;
    public static Boolean logsCommand;

    @Override
    public void onEnable() {
        pluginFolder = getDataFolder();
        saveDefaultConfig();
        File fileConfig = new File(Main.pluginFolder, "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

        logsJoin = config.getBoolean("Logs.Entrada");
        logsQuit = config.getBoolean("Logs.Saida");
        logsMessage = config.getBoolean("Logs.Mensagem");
        logsCommand = config.getBoolean("Logs.Comando");

        String permission = config.getString("Comando.Permission");
        String permissionMessage = config.getString("Comando.Permission-Message");
        List<String> aliases = config.getStringList("Comando.Aliases");
        String usage = config.getString("Comando.Usage");

        getCommand("moonlogs").setExecutor(new Comando());
        if (!permission.isEmpty()) {
            getCommand("moonlogs").setPermission(permission);
        }
        if (!permissionMessage.isEmpty()) {
            getCommand("moonlogs").setPermissionMessage(permissionMessage.replace("&", "§"));
        }
        if (!aliases.isEmpty()) {
            getCommand("moonlogs").setAliases(aliases);
        }
        if (!usage.isEmpty()) {
            getCommand("moonlogs").setUsage(usage.replace("&", "§"));
        }

        Bukkit.getPluginManager().registerEvents(new LogsMenu(), this);
        Bukkit.getPluginManager().registerEvents(new Events(), this);

        Connection conn;
        try {
            conn = Database.getConnection();
        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getConsoleSender().sendMessage("§aMoonLogs iniciado!");
        if (conn != null) {
            Bukkit.getConsoleSender().sendMessage("§a ⤿ Conexão com database estabelecida com sucesso!");
        } else {
            Bukkit.getConsoleSender().sendMessage("§c ⤿ Erro ao estabelecer conexão com database.");
            Plugin moonlogs = Bukkit.getPluginManager().getPlugin("MoonLogs");
            Bukkit.getPluginManager().disablePlugin(moonlogs);
        }
        try {
            Database.checkDatabase();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§cMoonLogs desligado!");
        Bukkit.getConsoleSender().sendMessage("§c ⤿ Conexão com database finalizada com sucesso!");
    }
}
