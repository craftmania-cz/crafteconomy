package cz.craftmania.crafteconomy;

import cz.craftmania.crafteconomy.commands.*;
import cz.craftmania.crafteconomy.managers.ProprietaryManager;
import cz.craftmania.crafteconomy.listener.AdvancedAchievementsListener;
import cz.craftmania.crafteconomy.listener.PlayerCreateProfileListener;
import cz.craftmania.crafteconomy.listener.PlayerExpGainListener;
import cz.craftmania.crafteconomy.listener.PlayerJoinListener;
import cz.craftmania.crafteconomy.listener.PlayerLevelUpListener;
import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.tasks.AddRandomExpTask;
import cz.craftmania.crafteconomy.utils.AsyncUtils;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;
    private boolean registerEnabled = false;
    private int minExp, maxExp, time;
    private static ServerType serverType = ServerType.UNKNOWN;

    private boolean isAchievementPluginEnabled = false;

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

        // ID serveru a typ
        serverType = resolveServerType();
        Logger.info("Server zaevidovany jako: " + serverType.name());

        // Asynchronus tasks
        async = new AsyncUtils(this);

        // HikariCP
        initDatabase();

        // AdvancedAchievements API
        if (Bukkit.getPluginManager().isPluginEnabled("AdvancedAchievements")) {
            isAchievementPluginEnabled = true;
            Logger.info("Detekovan plugin: AdvancedAchievements");
            ProprietaryManager.loadServerAchievements();
            ProprietaryManager.loadServerLevelRewards();
        }

        // Variables
        registerEnabled = getConfig().getBoolean("registerEnabled");
        if (registerEnabled) {
            Logger.info("Aktivace novych hracu do SQL aktivovano.");
        }

        // Tasks
        if (getConfig().getBoolean("random-exp.enabled", false)) {
            Logger.info("Aktivace nahodneho davani expu na serveru!");
            Main.getInstance().getServer().getScheduler().runTaskTimer(this, new AddRandomExpTask(), 0, time);
        }

        // Listeners
        loadListeners();

        // Commands
        if (Bukkit.getPluginManager().isPluginEnabled("CommandAPI")) {
            Logger.info("CommandsAPI detekovano, prikazy budou registrovany!");
            loadCommands();

            // Prikazy zavisly na CraftCore
            if (Bukkit.getPluginManager().isPluginEnabled("CraftCore")) {
                RewardsCommand.register();
            }
        } else {
            Logger.danger("CommandsAPI nebylo nalezeno, plugin bude fungovat pouze jako knihovna!");
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

        // AdvancedAchievements Events
        if (isAchievementPluginEnabled) {
            pm.registerEvents(new AdvancedAchievementsListener(), this);
        }
    }

    private void loadCommands() { //TODO: Nenačítat, když nebude CommandAPI na serveru
        CraftCoinsCommand.register();
        CraftTokensCommand.register();
        VoteTokensCommand.register();
        LevelCommand.register();
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

    public static ServerType getServerType() {
        return serverType;
    }

    private ServerType resolveServerType() {
        String type = getInstance().getConfig().getString("server");
        if (type == null) {
            return ServerType.UNKNOWN;
        }
        if (type.equalsIgnoreCase("survival")) {
            return ServerType.SURVIVAL;
        } else if (type.equalsIgnoreCase("skyblock")) {
            return ServerType.SKYBLOCK;
        } else if (type.equalsIgnoreCase("creative") || type.equalsIgnoreCase("creative2")) { // creative2 = 1.14
            return ServerType.CREATIVE;
        } else if (type.equalsIgnoreCase("prison")) {
            return ServerType.PRISON;
        } else if (type.equalsIgnoreCase("vanilla")) {
            return ServerType.VANILLA;
        } else if (type.equalsIgnoreCase("skycloud")) {
            return ServerType.SKYCLOUD;
        } else {
            return ServerType.UNKNOWN;
        }
    }
}
