package aceita.moonlogs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Database {

    public static Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        File fileConfig = new File(Main.pluginFolder, "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

        String address = config.getString("MySQL.Address");
        int port = config.getInt("MySQL.Port");
        String username = config.getString("MySQL.Username");
        String password = config.getString("MySQL.Password");
        String database = config.getString("MySQL.Database");

        if (address == null || username == null || password == null || database == null) {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:plugins/MoonLogs/database.db";
            File file = new File("plugins/MoonLogs/database.db");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            try {
                return DriverManager.getConnection(url);
            } catch (SQLException error) {
                return null;
            }
        }

        if (address.isEmpty() || username.isEmpty() || password.isEmpty() || database.isEmpty()) {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:plugins/MoonLogs/database.db";
            File file = new File("plugins/MoonLogs/database.db");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            try {
                return DriverManager.getConnection(url);
            } catch (SQLException error) {
                return null;
            }
        }


        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:mysql://" + address + ":" + port + "/" + database;
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException error) {
            return null;
        }
    }

    public static void checkDatabase() throws IOException, ClassNotFoundException, SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS moonlogs (\n"
                + " uuid VARCHAR(255) PRIMARY KEY,\n"
                + " name VARCHAR(255),\n"
                + " commands VARCHAR(1000),\n"
                + " messages VARCHAR(1000),\n"
                + " joinData VARCHAR(1000),\n"
                + " quitData VARCHAR(1000)\n"
                + ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ignored) {
        }
    }

    public static String getDataDB(String column, String conditionColumn, String conditionValue) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT " + column + " FROM moonlogs WHERE " + conditionColumn + " = ?";
        Connection conn = getConnection();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, conditionValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String result = resultSet.getString(column);
                conn.close();
                return result;
            } else {
                conn.close();
            }
            return null;
        }
    }

    public static boolean insertDB(String uuid, String name, String commands, String messages, String join, String quit) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT INTO moonlogs (uuid, name, commands, messages, joinData, quitData) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = getConnection();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);
            statement.setString(2, name);
            statement.setString(3, commands);
            statement.setString(4, messages);
            statement.setString(5, join);
            statement.setString(6, quit);
            int rowsInserted = statement.executeUpdate();
            conn.close();
            return rowsInserted > 0;
        }
    }

    public static void updateDB(String column, String newValue, String conditionColumn, String conditionValue) throws SQLException, IOException, ClassNotFoundException {
        String sql = "UPDATE moonlogs SET " + column + " = ? WHERE " + conditionColumn + " = ?";
        Connection conn = getConnection();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newValue);
            statement.setString(2, conditionValue);
            statement.executeUpdate();
            conn.close();
        }
    }

    public static void deleteDB(String conditionColumn, String conditionValue) throws SQLException, IOException, ClassNotFoundException {
        String sql = "DELETE FROM moonlogs WHERE " + conditionColumn + " = ?";
        Connection conn = getConnection();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, conditionValue);
            statement.executeUpdate();
            conn.close();
        }
    }

    public static boolean searchDB(String conditionColumn, String conditionValue) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT * FROM moonlogs WHERE " + conditionColumn + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, conditionValue);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

}
