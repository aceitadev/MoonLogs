package aceita.moonlogs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Comando implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (sender instanceof Player) {
            File fileConfig = new File(Main.pluginFolder, "config.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
            Player player = (Player) sender;
            if (!player.hasPermission(config.getString("Comando.Permission"))) {
                player.sendMessage(config.getString("Comando.Permission-Message").replace("&", "§"));
                return true;
            }
            if (args.length != 1) {
                player.sendMessage(config.getString("Comando.Usage").replace("&", "§"));
                return true;
            }

            boolean exist = false;
            Player playerArg = Bukkit.getPlayer(args[0]);
            if (playerArg == null) {
                player.sendMessage("§cJogador não encontrado.");
                return true;
            }
            if (!playerArg.isOnline()) {
                player.sendMessage("§aO jogador precisa estar online.");
                return true;
            }
            try {
                exist = Database.searchDB("uuid", playerArg.getUniqueId().toString());
            } catch (SQLException | ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
            if (exist) {
                Inventory menu = Bukkit.createInventory(player, 27, "Logs: " + playerArg.getName());
                if (Main.logsJoin) {
                    menu.setItem(10, Util.createItem(Material.SLIME_BALL, "§aEntrada", null));
                }
                if (Main.logsQuit) {
                    menu.setItem(12, Util.createItem(Material.REDSTONE, "§cSaída", null));
                }
                if (Main.logsMessage) {
                    menu.setItem(14, Util.createItem(Material.PAPER, "§eMensagens", null));
                }
                if (Main.logsCommand) {
                    menu.setItem(16, Util.createItem(Material.ITEM_FRAME, "§bComandos", null));
                }
                player.openInventory(menu);
                return true;
            } else {
                player.sendMessage("§cEsse jogador não possui nenhum registro.");
                return true;
            }
        }
        sender.sendMessage("Comando disponível apenas para os jogadores.");
        return true;
    }
}
