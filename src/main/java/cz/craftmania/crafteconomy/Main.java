package cz.craftmania.crafteconomy;

import co.aikar.commands.PaperCommandManager;
import cz.craftmania.craftactions.profile.NotificationPriority;
import cz.craftmania.craftactions.profile.NotificationType;
import cz.craftmania.craftcore.quartz.CronScheduleBuilder;
import cz.craftmania.craftcore.quartz.SchedulerException;
import cz.craftmania.craftcore.quartz.SchedulerFactory;
import cz.craftmania.craftcore.quartz.SimpleScheduleBuilder;
import cz.craftmania.craftcore.quartz.impl.StdSchedulerFactory;
import cz.craftmania.crafteconomy.commands.*;
import cz.craftmania.crafteconomy.commands.vault.*;
import cz.craftmania.crafteconomy.commands.vault.BankCommands.DepositCommand;
import cz.craftmania.crafteconomy.commands.vault.BankCommands.WithdrawCommand;
import cz.craftmania.crafteconomy.listener.*;
import cz.craftmania.crafteconomy.managers.*;
import cz.craftmania.crafteconomy.managers.vault.DepositGUI;
import cz.craftmania.crafteconomy.managers.vault.VaultEconomyManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.tasks.AddRandomExpTask;
import cz.craftmania.crafteconomy.tasks.EconomySaveTask;
import cz.craftmania.crafteconomy.tasks.PlayerUpdateGlobalLevelTask;
import cz.craftmania.crafteconomy.tasks.VaultCleanTask;
import cz.craftmania.crafteconomy.utils.*;
import cz.craftmania.crafteconomy.utils.configs.Config;
import cz.craftmania.crafteconomy.utils.configs.ConfigAPI;
import cz.craftmania.crafteconomy.utils.hooks.PlaceholderRegistry;
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
import java.time.ZoneId;
import java.util.*;
import java.util.Calendar;
import java.util.stream.Collectors;

public class Main extends JavaPlugin implements PluginMessageListener {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;
    private ConfigAPI configAPI;
    private SchedulerFactory schedulerFactory;
    private JobScheduler jobScheduler;
    private int minExp, maxExp, timeInMinutes;
    private boolean debug;

    // Vault
    private static Economy vaultEconomy = null;
    private String currency = "$";
    private static VaultEconomyManager vaultEconomyManager;
    private final BasicManager basicManager = new BasicManager();

    // Server
    private static ServerType serverType = ServerType.UNKNOWN;

    // Economy
    private EconomyType voteTokensVersion = null;

    // Enabled properties
    private boolean registerEnabled = false;
    private boolean isCMIPluginEnabled = false;
    private boolean vaultEconomyEnabled = false;
    private boolean vaultEconomyCleanUp = false;
    private boolean notificationListenerEnabled = false;
    private boolean notificationLoadingEnabled = false;
    private List<String> disabledExperienceInWorlds = new ArrayList<>();

    // Sentry
    private CraftSentry sentry = null;

    // Commands manager
    private PaperCommandManager manager;

    // Plugins
    public static boolean isCraftCoreEnabled = false;
    public static boolean isLuxuryQuestEnabled = false;

    @Override
    public void onLoad() {

        // Instance
        instance = this;

        debug = false; // Debug zprávy

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Nacteni config souboru
        configAPI = new ConfigAPI(this);
        loadConfiguration();

        // Vault init
        vaultEconomyEnabled = getConfig().getBoolean("vault-economy.enabled", false);
        vaultEconomyCleanUp = getConfig().getBoolean("vault-economy.cleanup.enabled", false);
        if (vaultEconomyEnabled) {
            Logger.info("Injectovani Vault Economy.");

            this.getServer().getServicesManager().register((Class)Economy.class, (Object)new VaultUtils(), this, ServicePriority.Normal);
            vaultEconomy = new VaultUtils();

            currency = getConfig().getString("vault-economy.name");
            Logger.info("Měna ekonomiky zaevidovana jako: " + currency);
        }
    }

    @Override
    public void onEnable() {

        // Tasks & schedules
        try {
            Properties schedulerProperties = new Properties();
            schedulerProperties.put("cz.craftmania.craftcore.quartz.threadPool.threadCount", "10");
            this.schedulerFactory = new StdSchedulerFactory(schedulerProperties);
            this.schedulerFactory.getScheduler().start();
            this.jobScheduler = new JobScheduler(this.schedulerFactory.getScheduler());
            Logger.info("Inicializace interního scheduleru dokončena.");
        } catch (SchedulerException e) {
            Logger.danger("Selhalo spuštění interního scheduleru.");
            e.printStackTrace();
        }
        async = new AsyncUtils(this);

        if (Bukkit.getPluginManager().isPluginEnabled("CraftCore")) isCraftCoreEnabled = true;

        // Plugin messages
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "craftbungee:vote", this);

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

        // HikariCP
        initDatabase();

        // Variables
        registerEnabled = getConfig().getBoolean("registerEnabled");
        if (registerEnabled) {
            Logger.info("Aktivace novych hracu do SQL aktivovano.");
        }

        // Tasks
        if (getConfig().getBoolean("random-exp.enabled", false)) {
            Logger.info("Aktivace nahodneho davani expu na serveru!");
            minExp = getConfig().getInt("random-exp.settings.min", 30);
            maxExp = getConfig().getInt("random-exp.settings.max", 60);
            timeInMinutes = getConfig().getInt("random-exp.settings.every", 5);
            RewardManager.loadRewards();
            this.disabledExperienceInWorlds = Main.getInstance().getConfig().getStringList("random-exp.not-in-world");
            try {
                this.jobScheduler.scheduleWithBuilder(AddRandomExpTask.class, "random-exp-task", SimpleScheduleBuilder.repeatMinutelyForever(timeInMinutes));
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        // Final boolean values
        isCMIPluginEnabled = Bukkit.getPluginManager().isPluginEnabled("CMI");
        isLuxuryQuestEnabled = Bukkit.getPluginManager().isPluginEnabled("LuxuryQuests");
        notificationListenerEnabled = getConfig().getBoolean("notifications.listener", false);
        notificationLoadingEnabled = getConfig().getBoolean("notifications.enabled", false);

        if (isLuxuryQuestEnabled) {
            Logger.info("LuxuryQuests detekováno, rewardy za questy jsou aktivní.");
            QuestManager.loadQuests();
        }

        // Aikar command manager
        manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");
        
        // Register příkazů
        Logger.info("Probíhá registrace příkazů pomocí Aikar commands!");
        loadCommands(manager);

        // Economy settings
        this.setupVoteTokensVersion();

        // Listeners
        loadListeners();

        // Vault init
        vaultEconomyEnabled = getConfig().getBoolean("vault-economy.enabled", false);
        if (vaultEconomyEnabled && Bukkit.getPluginManager().isPluginEnabled("Vault")) {

            try {
                this.jobScheduler.scheduleWithBuilder(EconomySaveTask.class, "economy-save", SimpleScheduleBuilder.repeatMinutelyForever());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

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
            try {
                this.jobScheduler.scheduleWithBuilder(PlayerUpdateGlobalLevelTask.class, "update-global-levels", SimpleScheduleBuilder.repeatMinutelyForever(15));
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        } else {
            Logger.info("Server nebude updatovat hracum global level!");
        }

        if (!isCraftCoreEnabled) {
            Logger.danger("CraftCore není na serveru! Hodně funkcí je deaktivováných.");
        }

        // VotePass
        Logger.info("Načítání odměn pro VotePass.");
        VoteManager.loadVotePassRewards();

        // Placeholders
        Logger.info("Registrace economy placeholderů do PlaceholderAPI");
        new PlaceholderRegistry().register();
    }

    @Override
    public void onDisable() {

        // Finální save dat, pokud by byl server vypnutý před cyklem uložení
        try {
            Logger.info("Příprava a uložení dat před vypnutím pluginu.");
            if (Bukkit.getOnlinePlayers().size() > 0) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (this.basicManager.getCraftPlayer(player) != null) {
                        double balance = this.basicManager.getCraftPlayer(player).getMoney();
                        Main.getInstance().getMySQL().setVaultEcoBalance(player.getName(), balance);
                    }
                });
                Logger.success("Data hráčů uložena do SQL.");
            }
        } catch (Exception ignored) {}

        // Deaktivace MySQL
        sql.onDisable();

        instance = null;
    }

    /**
     * Napojení na MySQL + vytvoření tabulky (může být outdated).
     */
    private void initDatabase() {
        sql = new SQLManager(this);

        // Kdyz tabulka neexistuje, vytvoř
        if (!Main.getInstance().getMySQL().tablePlayerProfileExists()) {
            Main.getInstance().getMySQL().createPlayerProfileTable();
        }
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();

        // Bukkit
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerQuitListener(this), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);

        // CraftEconomy
        pm.registerEvents(new PlayerCreateProfileListener(), this);
        pm.registerEvents(new PlayerExpGainListener(), this);
        pm.registerEvents(new PlayerLevelUpListener(), this);

        if (isCraftCoreEnabled) {
            pm.registerEvents(new DepositGUI(), this);
        }

        // CMI Events
        if (isCMIPluginEnabled) {
            Logger.info("Detekce CMI, plugin bude detekovat AFK stav.");
            pm.registerEvents(new PlayerAfkListener(), this);
        }

        if (isLuxuryQuestEnabled) {
            Logger.info("Detekce LuxuryQuests, odměny za questy jsou aktivní.");
            pm.registerEvents(new QuestCompleteListener(), this);
        }

        if (isCraftCoreEnabled && vaultEconomyEnabled && vaultEconomyCleanUp) {
            int cleanUpDays = getConfig().getInt("vault-economy.cleanup.days", 150);
            Logger.info("Server bude mazat automaticky každý týden hráče z databáze.");
            Logger.info("Aktuální čas je nastaven na: " + cleanUpDays  + " dní.");
            try {
                this.jobScheduler.scheduleWithBuilder(
                        VaultCleanTask.class,
                        "vault-clean-task",
                        CronScheduleBuilder.atHourAndMinuteOnGivenDaysOfWeek(1, 0, Calendar.MONDAY)
                                .inTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Prague")))
                );
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        if (notificationListenerEnabled) {
            Logger.info("Aktivace listeneru na ukládání notifikací.");
            pm.registerEvents(new NotificationManager(), this);
        }
    }

    private void loadCommands(PaperCommandManager manager) {
        manager.registerCommand(new CraftCoinsCommand());
        manager.registerCommand(new CraftTokensCommand());
        manager.registerCommand(new LevelCommand());
        manager.registerCommand(new EventPointsCommand());
        manager.registerCommand(new VoteTokensCommand());
        manager.registerCommand(new KarmaCommand());
        manager.registerCommand(new SeasonPointsCommand());
        if (isCraftCoreEnabled) {
            manager.registerCommand(new RewardsCommand());
            manager.registerCommand(new ProfileCommand());
            manager.registerCommand(new VotePassCommand());
        }
        manager.registerCommand(new EconomyCommand());
        manager.getCommandCompletions().registerCompletion("economyType", context
                -> Arrays.stream(EconomyType.values()).map(Enum::name).collect(Collectors.toList()));
        if (this.notificationLoadingEnabled) {
            manager.registerCommand(new NotificationCommand());
            manager.getCommandCompletions().registerCompletion("notificationType", context -> {
                return Arrays.stream(NotificationType.values()).map(Enum::name).collect(Collectors.toList());
            });
            manager.getCommandCompletions().registerCompletion("notificationPriority", context -> {
               return Arrays.stream(NotificationPriority.values()).map(Enum::name).collect(Collectors.toList());
            });
        }
    }

    private void loadConfiguration() {
        Config questFile = new Config(this.configAPI, "quests");
        configAPI.registerConfig(questFile);

        Config rewardsFile = new Config(this.configAPI, "rewards");
        configAPI.registerConfig(rewardsFile);
    }

    /**
     * Config pro server questy, vyžaduje LuxuryQuest/CraftQuests.
     * @return {@link Config}
     */
    public Config getQuestConfig() {
        return this.configAPI.getConfig("quests");
    }

    /**
     * Config pro server rewards.
     * @return {@link Config}
     */
    public Config getRewardsConfig() {
        return this.configAPI.getConfig("rewards");
    }

    /**
     * Vrací instanci pluginu
     * @return {@link Main}
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Vrací metody pro snadnější spuštění tasků
     * @return {@link AsyncUtils}
     */
    public static AsyncUtils getAsync() {
        return async;
    }

    /**
     * Vrací MySQL metody skrz HikariCP
     * @return {@link SQLManager}
     */
    public SQLManager getMySQL() {
        return sql;
    }

    /**
     * Vrací object z Vaultu {@link Economy}
     * @return {@link Economy}
     */
    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    /**
     * Vraci boolean hodnotu, zda je na serveru aktivní registrace
     * hráčů do MySQL. Tato hodna by měla být true pouze na lobby.
     * @return {@link Boolean}
     */
    public boolean isRegisterEnabled() {
        return registerEnabled;
    }

    public int getMinExp() {
        return minExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    /**
     * Vrací zadaný {@link ServerType} dle configu
     * @return {@link ServerType}
     */
    public static ServerType getServerType() {
        return serverType;
    }

    /**
     * Vrací fixně nastavený hodnoty servertypu
     * př. survival 1.17, survival 1.18 jako survival
     * @return {@link String}
     */
    public static String getFixedServerType() {
        switch (serverType) {
            case SURVIVAL_117, SURVIVAL_118 -> {
                return "survival";
            }
            case SKYBLOCK_117, SKYBLOCK_118 -> {
                return "skyblock";
            }
            case CREATIVE -> {
                return "creative";
            }
            case VANILLA, VANILLA_116 -> {
                return "vanilla";
            }
            case SKYCLOUD -> {
                return "skycloud";
            }
            case PRISON -> {
                return "prison";
            }
        }
        return "unknown";
    }

    /**
     * Vrací true, pokud je na serveru aktivní Vault ekonomika
     * @return
     */
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
     * @return (Long)18748724.9 -> (String)18,748,724.2
     */
    public String getFormattedNumber(Double number) {
        return String.format("%1$,.1f", number);
    }

    public VaultEconomyManager getVaultEconomyManager() {
        return vaultEconomyManager;
    }

    public ConfigAPI getConfigAPI() {
        return configAPI;
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
        switch (type) {
            case "survival" -> {
                return ServerType.SURVIVAL_117;
            }
            case "skyblock" -> {
                return ServerType.SKYBLOCK_117;
            }
            case "survival_118" -> {
                return ServerType.SURVIVAL_118;
            }
            case "skyblock_118" -> {
                return ServerType.SKYBLOCK_118;
            }
            case "creative" -> {
                return ServerType.CREATIVE;
            }
            case "prison" -> {
                return ServerType.PRISON;
            }
            case "vanilla" -> {
                return ServerType.VANILLA;
            }
            case "skycloud" -> {
                return ServerType.SKYCLOUD;
            }
            case "skygrid" -> {
                return ServerType.SKYGRID;
            }
            case "lobby" -> {
                return ServerType.LOBBY;
            }
            case "event-server" -> {
                return ServerType.EVENT_SERVER;
            }
            case "hardcore-vanilla" -> {
                return ServerType.HARDCORE_VANILLA;
            }
            case "anarchy" -> {
                return ServerType.ANARCHY;
            }
            default -> {
                return ServerType.UNKNOWN;
            }
        }
    }

    /**
     * Nastavení verze VoteTokenů dle configu.
     * Pokud je uvedená špatná verze, plugin se nezapne.
     */
    private void setupVoteTokensVersion() {
        int version = this.getConfig().getInt("economy.votetokens-version");
        switch (version) {
            case 1 -> {
                Logger.info("Nastavena verze VoteTokens: 1 -> 1.9 => 1.13");
                this.voteTokensVersion = EconomyType.VOTE_TOKENS;
            }
            case 2 -> {
                Logger.info("Nastavena verze VoteTokens: 2 -> 1.4 => 1.17");
                this.voteTokensVersion = EconomyType.VOTE_TOKENS_2;
            }
            case 3 -> {
                Logger.info("Nastavena verze VoteTokens: 3 -> 1.18+");
                this.voteTokensVersion = EconomyType.VOTE_TOKENS_3;
            }
            default -> {
                Logger.danger("Nespravna verze VoteTokens. Nastaveno: " + version);
                Logger.danger("Plugin se kvuli bezpecnosti vypne.");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    public EconomyType getVoteTokensVersion() {
        return this.voteTokensVersion;
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
                String votetokens = in.readUTF();
                voteManager.playerVote(nick, null, coins, votetokens); //TODO: UUID
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

    public SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    public boolean isNotificationLoadingEnabled() {
        return notificationLoadingEnabled;
    }
}
