package cz.craftmania.crafteconomy;

import cz.craftmania.crafteconomy.commands.CraftCoins_command;
import cz.craftmania.crafteconomy.commands.CraftTokens_command;
import cz.craftmania.crafteconomy.commands.Level_command;
import cz.craftmania.crafteconomy.commands.VoteTokens_command;
import cz.craftmania.crafteconomy.listener.PlayerCreateProfileListener;
import cz.craftmania.crafteconomy.listener.PlayerExpGainListener;
import cz.craftmania.crafteconomy.listener.PlayerJoinListener;
import cz.craftmania.crafteconomy.listener.PlayerLevelUpListener;
import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.tasks.AddRandomExpTask;
import cz.craftmania.crafteconomy.utils.AsyncUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;
    private boolean registerEnabled = false;
    private int minExp, maxExp, time;
    private static String server = null;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Values
        minExp = getConfig().getInt("random-exp.settings.min", 30);
        maxExp = getConfig().getInt("random-exp.settings.max", 60);
        time = getConfig().getInt("random-exp.settings.every", 6000);
        server = getConfig().getString("server", "survival");

        // Asynchronus tasks
        async = new AsyncUtils(this);

        // HikariCP
        initDatabase();

        // Listeners
        loadListeners();
        loadCommands();

        // Variables
        registerEnabled = getConfig().getBoolean("registerEnabled");

        // Tasks
        if (getConfig().getBoolean("random-exp.enabled", false)) {
            Main.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(this, new AddRandomExpTask(), 0, time);
        }

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
        pm.registerEvents(new PlayerLevelUpListener(), this);
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

    public int getMinExp() {
        return minExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public String getServerName() {
        return server;
    }
}
