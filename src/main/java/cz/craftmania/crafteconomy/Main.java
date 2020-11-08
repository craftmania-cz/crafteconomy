package cz.craftmania.crafteconomy;

import co.aikar.commands.PaperCommandManager;
import cz.craftmania.crafteconomy.commands.*;
import cz.craftmania.crafteconomy.commands.vault.*;
import cz.craftmania.crafteconomy.commands.vault.BankCommands.DepositCommand;
import cz.craftmania.crafteconomy.commands.vault.BankCommands.WithdrawCommand;
import cz.craftmania.crafteconomy.listener.*;
import cz.craftmania.crafteconomy.managers.ProprietaryManager;
import cz.craftmania.crafteconomy.managers.VoteManager;
import cz.craftmania.crafteconomy.managers.vault.DepositGUI;
import cz.craftmania.crafteconomy.managers.vault.VaultEconomyManager;
import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.tasks.AddRandomExpTask;
import cz.craftmania.crafteconomy.tasks.EconomySaveTask;
import cz.craftmania.crafteconomy.tasks.PlayerUpdateGlobalLevelTask;
import cz.craftmania.crafteconomy.utils.AsyncUtils;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.crafteconomy.utils.VaultUtils;
import cz.craftmania.craftlibs.sentry.CraftSentry;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Main extends JavaPlugin implements PluginMessageListener {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;
    private int minExp, maxExp, time;
    private boolean debug;

    // Vault
    private static Economy vaultEconomy = null;
    private String currency = "$";
    private static VaultEconomyManager vaultEconomyManager;

    // Server
    private static ServerType serverType = ServerType.UNKNOWN;

    // Enabled properties
    private boolean registerEnabled = false;
    private boolean isAchievementPluginEnabled = false;
    private boolean isCMIPluginEnabled = false;
    private boolean vaultEconomyEnabled = false;
    private List<String> disabledExperienceInWorlds = new ArrayList<>();

    // Sentry
    private CraftSentry sentry = null;

    // Commands manager
    private PaperCommandManager manager;

    // CraftCore
    public static boolean isCraftCoreEnabled = false;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        debug = false; // Debug zprávy

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("CraftCore")) isCraftCoreEnabled = true;

        // Plugin messages
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "craftbungee:vote", this);

        // Values
        minExp = getConfig().getInt("random-exp.settings.min", 30);
        maxExp = getConfig().getInt("random-exp.settings.max", 60);
        time = getConfig().getInt("random-exp.settings.every", 6000);

        // ID serveru a typ
        serverType = resolveServerType();
        Logger.info("Server zaevidovany jako: " + serverType.name());

        // Sentry integration
        if (!(Objects.requireNonNull(getConfig().getString("sentry-dsn")).length() == 0) && Bukkit.getPluginManager().isPluginEnabled("CraftLibs")) {
            String dsn = getConfig().getString("sentry-dsn");
            Logger.info("Sentry integration je aktivní: §7" + dsn);
            sentry = new CraftSentry(dsn);
        } else {
            Logger.danger("Sentry integration neni aktivovana!");
        }

        // Asynchronus tasks
        async = new AsyncUtils(this);

        // HikariCP
        initDatabase();

        // AdvancedAchievements API
        if (Bukkit.getPluginManager().isPluginEnabled("AdvancedAchievements")) {
            isAchievementPluginEnabled = true;
            Logger.info("Detekovan plugin: AdvancedAchievements");
            ProprietaryManager.loadServerAchievements();
        } else {
            Logger.danger("AdvancedAchievements nejsou na serveru!");
        }

        // Variables
        registerEnabled = getConfig().getBoolean("registerEnabled");
        if (registerEnabled) {
            Logger.info("Aktivace novych hracu do SQL aktivovano.");
        }

        // Tasks
        if (getConfig().getBoolean("random-exp.enabled", false)) {
            Logger.info("Aktivace nahodneho davani expu na serveru!");
            ProprietaryManager.loadServerLevelRewards();
            Main.getAsync().runAsync(new AddRandomExpTask(), (long) time);
            this.disabledExperienceInWorlds = Main.getInstance().getConfig().getStringList("random-exp.not-in-world");
        }

        // Final boolean values
        isCMIPluginEnabled = Bukkit.getPluginManager().isPluginEnabled("CMI");

        // Aikar command manager
        manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");
        
        // Register příkazů
        Logger.info("Probíhá registrace příkazů pomocí Aikar commands!");
        loadCommands(manager);

        // Listeners
        loadListeners();

        // Vault init
        vaultEconomyEnabled = getConfig().getBoolean("vault-economy.enabled", false);
        if (vaultEconomyEnabled && Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Logger.info("Vault economy bude aktivní!");

            this.getServer().getServicesManager().register((Class)Economy.class, (Object)new VaultUtils(), this, ServicePriority.Normal);
            vaultEconomy = new VaultUtils();

            currency = getConfig().getString("vault-economy.name");
            Logger.info("Mena ekonomiky zaevidovana jako: " + currency);

            Main.getAsync().runAsync(new EconomySaveTask(), 1200L);

            manager.registerCommand(new MoneyCommand());
            manager.registerCommand(new MoneylogCommand());
            manager.registerCommand(new BaltopCommand());
            manager.registerCommand(new PayCommand());
            manager.registerCommand(new PaytoggleCommand());
            if (isCraftCoreEnabled) {
                vaultEconomyManager = new VaultEconomyManager();
                Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> getVaultEconomyManager().updateBaltopCache(), 0L, 2400);
            }

            if (getServerType() == ServerType.SKYCLOUD && isCraftCoreEnabled) { // Banky jsou zatím dostupné pouze na Skycloudu
                //manager.registerCommand(new BankCommand());
                manager.registerCommand(new DepositCommand());
                manager.registerCommand(new WithdrawCommand());
            }
        }

        if (getConfig().getBoolean("disables.global-level-updates", true)) {
            Logger.info("Aktivace updatu global levels pro hrace!");
            Main.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(this, new PlayerUpdateGlobalLevelTask(), 100L, 18000L); // 15 minut
        } else {
            Logger.info("Server nebude updatovat hracum global level!");
        }

        if (!isCraftCoreEnabled) {
            Logger.danger("CraftCore není na serveru! Hodně funkcí je deaktivováných.");
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

        if (isCraftCoreEnabled)
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

    private void loadCommands(PaperCommandManager manager) {
        manager.registerCommand(new CraftCoinsCommand());
        manager.registerCommand(new CraftTokensCommand());
        manager.registerCommand(new LevelCommand());
        manager.registerCommand(new EventPointsCommand());
        manager.registerCommand(new VoteTokensCommand());
        manager.registerCommand(new KarmaCommand());
        if (isCraftCoreEnabled) {
            manager.registerCommand(new RewardsCommand());
            manager.registerCommand(new ProfileCommand());
        }
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

    public boolean isDebugActive() {
        return debug;
    }

    /**
     * Vrací název aktuální měny
     * @return {@link String}
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Vrací list, kde je deaktivované random expy podle configu
     */
    public List<String> getDisabledExperienceInWorlds() {
        return disabledExperienceInWorlds;
    }

    /**
     * Převede long říslo na zformátovaný String
     *
     * @param number Jakékoliv číslo (long)
     * @return (Long)18748724 -> (String)18,748,724
     */
    public String getFormattedNumber(Long number) {
        return NumberFormat.getInstance(Locale.US).format(number);
    }

    public VaultEconomyManager getVaultEconomyManager() {
        return vaultEconomyManager;
    }

    /**
     * Rozdělí server dle ID z configu na {@link ServerType}
     * Vrací ServerType.UNKNOWN, když se id neshodují.
     * @return {@link ServerType}
     */
    private ServerType resolveServerType() {
        String type = getInstance().getConfig().getString("server");
        if (type == null) {
            return ServerType.UNKNOWN;
        }
        if (type.equalsIgnoreCase("survival") || type.equalsIgnoreCase("survival2")) { // survival2 = 1.15
            return ServerType.SURVIVAL;
        } else if (type.equalsIgnoreCase("skyblock") || type.equalsIgnoreCase("skyblock2")) { // skyblock2 = 1.15
            return ServerType.SKYBLOCK;
        } else if (type.equalsIgnoreCase("creative")) {
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
        } else if (type.equalsIgnoreCase("hardcore-vanilla")) {
            return ServerType.HARDCORE_VANILLA;
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

    /**
     * Odesilá exception na Sentry
     */
    public void sendSentryException(Exception exception) {
        if (sentry == null) {
            Logger.danger("Sentry neni aktivovany, error nebude zaslan!");
            return;
        }
        sentry.sendException(exception);
    }
}
