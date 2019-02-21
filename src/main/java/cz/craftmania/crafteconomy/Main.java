package cz.craftmania.crafteconomy;

import cz.craftmania.crafteconomy.commands.CraftCoins_command;
import cz.craftmania.crafteconomy.commands.CraftTokens_command;
import cz.craftmania.crafteconomy.commands.Level_command;
import cz.craftmania.crafteconomy.commands.VoteTokens_command;
import cz.craftmania.crafteconomy.listener.PlayerCreateProfileListener;
import cz.craftmania.crafteconomy.listener.PlayerExpGainListener;
import cz.craftmania.crafteconomy.listener.PlayerJoinListener;
import cz.craftmania.crafteconomy.listener.PlayerLevelGainListener;
import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.utils.AsyncUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;
    private boolean registerEnabled = false;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Asynchronus tasks
        async = new AsyncUtils(this);

        // HikariCP
        initDatabase();

        // Listeners
        loadListeners();
        loadCommands();

        // Variables
        registerEnabled = getConfig().getBoolean("registerEnabled");

    }

    @Override
    public void onDisable() {

        // Deaktivace MySQL
        sql.onDisable();

        instance = null;

    }

    public static Main getInstance() {
        return instance;
    }

    public static AsyncUtils getAsync() {
        return async;
    }

    public SQLManager getMySQL() {
        return sql;
    }

    private void initDatabase() {
        sql = new SQLManager(this);
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();

        // Bukkit
        pm.registerEvents(new PlayerJoinListener(this), this);

        // Economy
        pm.registerEvents(new PlayerCreateProfileListener(), this);

        pm.registerEvents(new PlayerExpGainListener(), this);
        pm.registerEvents(new PlayerLevelGainListener(), this);
    }

    private void loadCommands() {
        getCommand("craftcoins").setExecutor(new CraftCoins_command());
        getCommand("crafttokens").setExecutor(new CraftTokens_command());
        getCommand("votetokens").setExecutor(new VoteTokens_command());
        getCommand("level").setExecutor(new Level_command());
    }

    public boolean isRegisterEnabled() {
        return registerEnabled;
    }
}
