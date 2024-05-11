package aceita.moonlogs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.SQLException;

public class LogsMenu implements Listener {

    @EventHandler
    public void onLogsMenu(InventoryClickEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (event.getInventory().getName().contains("Logs: ")) {
            event.setCancelled(true);
            Player playerArg = Bukkit.getPlayer(event.getInventory().getName().replace("Logs: ", ""));
            Player player = (Player) event.getWhoClicked();

            if (event.getCurrentItem().getType() != Material.AIR) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aEntrada")) {
                    Inventory menu = Bukkit.createInventory(player, 54, "Logs de Entrada: " + playerArg.getName());
                    String result = Database.getDataDB("joinData", "uuid", playerArg.getUniqueId().toString());
                    if (result != null) {
                        String[] logs = result.split(";");
                        int slot = 10;
                        for (int i = logs.length - 1; i >= 0; i--) {
                            String[] data = logs[i].split(",");
                            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                                slot++;
                                slot++;
                            }
                            if (slot <= 43) {
                                menu.setItem(slot, Util.createItem(Material.ITEM_FRAME, "§a" + playerArg.getName(), "§eData: §b" + data[0] + ";§eServidor: §b" + data[1]));
                            }
                            slot++;
                        }
                    } else {
                        menu.setItem(22, Util.createItem(Material.BARRIER, "§cNenhum registro encontrado.", null));
                    }
                    menu.setItem(49, Util.createItem(Material.ARROW, "§aVoltar", "§eAo menu anterior"));
                    player.openInventory(menu);
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cSaída")) {
                    Inventory menu = Bukkit.createInventory(player, 54, "Logs de Saída: " + playerArg.getName());
                    String result = Database.getDataDB("quitData", "uuid", playerArg.getUniqueId().toString());
                    if (result != null) {
                        String[] logs = result.split(";");
                        int slot = 10;
                        for (int i = logs.length - 1; i >= 0; i--) {
                            String[] data = logs[i].split(",");
                            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                                slot++;
                                slot++;
                            }
                            if (slot <= 43) {
                                menu.setItem(slot, Util.createItem(Material.ITEM_FRAME, "§a" + playerArg.getName(), "§eData: §b" + data[0] + ";§eServidor: §b" + data[1]));
                            }
                            slot++;
                        }
                    } else {
                        menu.setItem(22, Util.createItem(Material.BARRIER, "§cNenhum registro encontrado.", null));
                    }
                    menu.setItem(49, Util.createItem(Material.ARROW, "§aVoltar", "§eAo menu anterior"));
                    player.openInventory(menu);
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eMensagens")) {
                    Inventory menu = Bukkit.createInventory(player, 54, "Logs de Mensagens: " + playerArg.getName());
                    String result = Database.getDataDB("messages", "uuid", playerArg.getUniqueId().toString());
                    if (result != null) {
                        String[] logs = result.split(";");
                        int slot = 10;
                        for (int i = logs.length - 1; i >= 0; i--) {
                            String[] data = logs[i].split(",");
                            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                                slot++;
                                slot++;
                            }
                            if (slot <= 43) {
                            menu.setItem(slot, Util.createItem(Material.PAPER, "§a" + playerArg.getName(), "§eData: §b" + data[0] + ";§eServidor: §b" + data[2] + ";§a;§eMensagem:;§b" + data[1]));
                            }
                            slot++;
                        }
                    } else {
                        menu.setItem(22, Util.createItem(Material.BARRIER, "§cNenhum registro encontrado.", null));
                    }
                    menu.setItem(49, Util.createItem(Material.ARROW, "§aVoltar", "§eAo menu anterior"));
                    player.openInventory(menu);
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bComandos")) {
                    Inventory menu = Bukkit.createInventory(player, 54, "Logs de Comandos: " + playerArg.getName());
                    String result = Database.getDataDB("commands", "uuid", playerArg.getUniqueId().toString());
                    if (result != null) {
                        String[] logs = result.split(";");
                        int slot = 10;
                        for (int i = logs.length - 1; i >= 0; i--) {
                            String[] data = logs[i].split(",");
                            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                                slot++;
                                slot++;
                            }
                            if (slot <= 43) {
                                menu.setItem(slot, Util.createItem(Material.PAPER, "§a" + playerArg.getName(), "§eData: §b" + data[0] + ";§eServidor: §b" + data[2] + ";§a;§eMensagem:;§b" + data[1]));
                            }
                            slot++;
                        }
                    } else {
                        menu.setItem(22, Util.createItem(Material.BARRIER, "§cNenhum registro encontrado.", null));
                    }
                    menu.setItem(49, Util.createItem(Material.ARROW, "§aVoltar", "§eAo menu anterior"));
                    player.openInventory(menu);
                }
            }
        } else if (event.getInventory().getName().contains("Logs")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.ARROW) {
                Player player = (Player) event.getWhoClicked();
                String playerArg = event.getInventory().getName();
                playerArg = playerArg.replace("Logs de Comandos: ", "");
                playerArg = playerArg.replace("Logs de Mensagens: ", "");
                playerArg = playerArg.replace("Logs de Entrada: ", "");
                playerArg = playerArg.replace("Logs de Saída: ", "");
                Inventory menu = Bukkit.createInventory(player, 27, "Logs: " + playerArg);
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
            }
        }
    }
}
