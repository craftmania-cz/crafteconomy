package cz.craftmania.crafteconomy;

import cz.craftmania.crafteconomy.commands.*;
import cz.craftmania.crafteconomy.commands.vault.*;
import cz.craftmania.crafteconomy.listener.*;
import cz.craftmania.crafteconomy.managers.ProprietaryManager;
import cz.craftmania.crafteconomy.managers.VoteManager;
import cz.craftmania.crafteconomy.managers.vault.DepositGUI;
import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.tasks.AddRandomExpTask;
import cz.craftmania.crafteconomy.utils.AsyncUtils;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.crafteconomy.utils.VaultUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class Main extends JavaPlugin implements PluginMessageListener {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;
    private int minExp, maxExp, time;

    // Vault
    private static Economy vaultEconomy = null;
    private String currency = "$";

    // Server
    private static ServerType serverType = ServerType.UNKNOWN;

    // Enabled properties
    private boolean registerEnabled = false;
    private boolean isAchievementPluginEnabled = false;
    private boolean isCMIPluginEnabled = false;
    private boolean vaultEconomyEnabled = false;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Plugin messages
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "craftbungee:vote", this);

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
            Main.getAsync().runAsync(new AddRandomExpTask(), (long) time);
        }

        // Final boolean values
        isCMIPluginEnabled = Bukkit.getPluginManager().isPluginEnabled("CMI");

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

        // Vault init
        vaultEconomyEnabled = getConfig().getBoolean("vault-economy.enabled", false);
        if (vaultEconomyEnabled && Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Logger.info("Vault economy bude aktivní!");

            this.getServer().getServicesManager().register((Class)Economy.class, (Object)new VaultUtils(), this, ServicePriority.Normal);
            vaultEconomy = new VaultUtils();

            currency = getConfig().getString("vault-economy.name");
            Logger.info("Mena ekonomiky zaevidovana jako: " + currency);

            MoneyCommand.register();
            MoneylogCommand.register();
            PayCommand.register();
            BaltopCommand.register();

            if (getServerType() == ServerType.SKYCLOUD) { // Banky jsou zatím dostupné pouze na Skycloudu
                BankCommand.register();
            }
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

        // Kdyz tabulka neexistuje, vytvoř
        if (!Main.getInstance().getMySQL().tablePlayerProfileExists()) {
            Main.getInstance().getMySQL().createPlayerProfileTable();
        }
    }

    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();

        // Bukkit
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerQuitListener(this), this);

        // CraftEconomy
        pm.registerEvents(new PlayerCreateProfileListener(), this);
        pm.registerEvents(new PlayerExpGainListener(), this);
        pm.registerEvents(new PlayerLevelUpListener(), this);

        pm.registerEvents(new DepositGUI(), this);

        // AdvancedAchievements Events
        if (isAchievementPluginEnabled) {
            pm.registerEvents(new AdvancedAchievementsListener(), this);
        }

        // CMI Events
        if (isCMIPluginEnabled) {
            pm.registerEvents(new PlayerAfkListener(), this);
        }
    }

    private void loadCommands() {
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

    public boolean isVaultEconomyEnabled() {
        return vaultEconomyEnabled;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * Will convert unformatted Long into formatted String.
     * Usually used for formatting money numbers into readable ones.
     *
     * @param number Any (long) number
     * @return (Long)18748724 -> (String)18,748,724
     */
    public String getFormattedNumber(Long number) {
        return NumberFormat.getInstance(Locale.US).format(number);
    }

    private ServerType resolveServerType() {
        String type = getInstance().getConfig().getString("server");
        if (type == null) {
            return ServerType.UNKNOWN;
        }
        if (type.equalsIgnoreCase("survival") || type.equalsIgnoreCase("survival2")) { // survival2 = 1.15
            return ServerType.SURVIVAL;
        } else if (type.equalsIgnoreCase("skyblock")) {
            return ServerType.SKYBLOCK;
        } else if (type.equalsIgnoreCase("creative") || type.equalsIgnoreCase("creative2")) { // creative2 = 1.12
            return ServerType.CREATIVE;
        } else if (type.equalsIgnoreCase("prison")) {
            return ServerType.PRISON;
        } else if (type.equalsIgnoreCase("vanilla")) {
            return ServerType.VANILLA;
        } else if (type.equalsIgnoreCase("skycloud")) {
            return ServerType.SKYCLOUD;
        } else if (type.equalsIgnoreCase("lobby")) {
            return ServerType.LOBBY;
        } else if (type.equalsIgnoreCase("event-server")) {
            return ServerType.EVENT_SERVER;
        } else {
            return ServerType.UNKNOWN;
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        VoteManager voteManager = new VoteManager();
        try {
            String sub = in.readUTF();
            if (sub.equals("vote")) {
                String nick = in.readUTF();
                String coins = in.readUTF();
                voteManager.playerVote(nick, null, coins); //TODO: UUID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
