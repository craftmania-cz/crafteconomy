package cz.craftmania.crafteconomy.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlaceholderRegister {

    private Main plugin;
    private BasicManager basicManager = new BasicManager();

    public PlaceholderRegister(Main plugin) {
        this.plugin = plugin;
    }

    public void registerPlaceholders() {
        if(Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            PlaceholderAPI.registerPlaceholder(plugin, "crafteconomy_level",
                    e -> {
                        Player p = e.getPlayer();
                        if(p != null) {
                            return String.valueOf(basicManager.getCraftPlayer(p).getLevel());
                        }
                        return "null";
                    });
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftEconomy] " + ChatColor.RED + " Nebyl nalezen MVdWPlaceholderAPI - placeholdery nebudou fungovat.");
        }
    }


}
