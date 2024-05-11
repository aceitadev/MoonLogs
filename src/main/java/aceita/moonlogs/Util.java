package aceita.moonlogs;
import com.mojang.util.UUIDTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Material;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
    public static ItemStack createItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setDisplayName(name);
        if (lore != null) {
            String[] lines = lore.split(";");
            List<String> loreLines = new ArrayList<>(Arrays.asList(lines));
            meta.setLore(loreLines);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static String removeChars(String string, int numero) {
        if (string != null && !string.trim().isEmpty()) {
            return string.substring(0, string.length() - numero);
        }
        return "";
    }

    public static String dateNow() {
        ZonedDateTime now = ZonedDateTime.now();
        File fileConfig = new File(Main.pluginFolder, "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

        ZoneId fuse = ZoneId.of(config.getString("FusoHorario"));
        ZonedDateTime gmtMinus3 = now.withZoneSameInstant(fuse);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return gmtMinus3.format(formatter);
    }
}