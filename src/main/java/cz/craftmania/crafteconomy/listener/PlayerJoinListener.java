package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.RewardManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.craftnotifications.api.NotificationsAPI;
import cz.craftmania.craftnotifications.objects.NotificationPriority;
import cz.craftmania.craftnotifications.objects.NotificationType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Main main;
    private final BasicManager bm = new BasicManager();
    private final PlayerUtils playerUtils = new PlayerUtils();

    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        // Zakladni nacteni dat do cache a vytvoření objektu
        CraftPlayer craftPlayer = BasicManager.loadPlayerData(player);
        craftPlayer.recalculateGlobalLevel();

        // Vytvoření a načtení vault money do craftplayer
        if (Main.getInstance().isVaultEconomyEnabled()) {

            if (main.getMySQL().hasVaultEcoProfile(player.getUniqueId())) {

                // Originálka si změnila nick -> kontrola -> update -> load podle UUID
                String sqlNick = Main.getInstance().getMySQL().getNickFromTable("player_economy_" + Main.getServerType().toString().toLowerCase(), player);
                assert sqlNick != null;
                if (!sqlNick.equals(player.getName())) {
                    Main.getInstance().getMySQL().updateNickInTable("player_economy_" + Main.getServerType().toString().toLowerCase(), player);
                }
                craftPlayer.setMoney(main.getMySQL().getVaultEcoBalance(player.getUniqueId()));

            } else if (main.getMySQL().hasVaultEcoProfile(player.getName())) {

                // Chyba UUID (Autologin) -> fix UUID -> load podle nicku
                Main.getInstance().getMySQL().updateUUIDInTable("player_economy_" + Main.getServerType().toString().toLowerCase(), player);
                craftPlayer.setMoney(main.getMySQL().getVaultEcoBalance(player.getName()));

            } else {

                // Hráč nemá vytvořený profil -> create -> load
                main.getMySQL().createVaultEcoProfile(player);
                craftPlayer.setMoney(main.getMySQL().getVaultEcoBalance(player.getUniqueId()));

            }

            // Limit jenom na 1.18 eco servery
            // TODO: Odebrat s odebráním 1.17 serverů
            if (Main.getServerType() == ServerType.SURVIVAL_118) {
                Main.getInstance().getMySQL().setHideInBaltop(player.getUniqueId().toString(), player.hasPermission("craftmania.at"));
            }
        }

        // Opravy práv pro achievementy
        // EXTRA: Toto právo se dává pouze, pokud hráč požádá o převod
        /*if (player.hasPermission("crafteconomy.levels.past-fix")) {
            Logger.info("Hrac " + player.getName() + " ma pravo na opravu Levels. Spustim...");
            this.fixLevelRewardsForPlayer(craftPlayer);
        }*/

        // Global Rewards: Redstone Limits
        // TODO: Config??
        if (Main.getServerType() != ServerType.LOBBY) {
            if (bm.getCraftPlayer(player).getLevelByType(LevelType.GLOBAL_LEVEL) >= 25
                    && !player.hasPermission("insights.group.redstone.25")) { // Redstone limit pro LEVEL > 25
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set insights.group.redstone.25");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set insights.group.observer.25");
            }

            if (bm.getCraftPlayer(player).getLevelByType(LevelType.GLOBAL_LEVEL) >= 50
                    && !player.hasPermission("insights.group.redstone.50")) { // Redstone limit pro LEVEL > 50
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set insights.group.redstone.50");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set insights.group.observer.50");
            }

            if (bm.getCraftPlayer(player).getLevelByType(LevelType.GLOBAL_LEVEL) >= 75
                    && !player.hasPermission("insights.group.redstone.75")) { // Redstone limit pro LEVEL > 75
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set insights.group.redstone.75");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set insights.group.observer.75");
            }
        }

        if (Main.getServerType() == ServerType.SKYBLOCK_117) {
            if (bm.getCraftPlayer(player).getLevelByType(LevelType.SKYBLOCK_117_LEVEL) >= 25 && !player.hasPermission("cmi.command.flightcharge")) {
                bm.givePlayerManualLevelReward(player, 25, true);
            }

            if (bm.getCraftPlayer(player).getLevelByType(LevelType.SKYBLOCK_117_LEVEL) >= 47 && !player.hasPermission("shopguiplus.item.spawners.12")) {
                bm.givePlayerManualLevelReward(player, 47, true);
            }

            if (bm.getCraftPlayer(player).getLevelByType(LevelType.SKYBLOCK_117_LEVEL) >= 50 && !player.hasPermission("shopguiplus.item.spawners.13")) {
                bm.givePlayerManualLevelReward(player, 50, true);
            }
        }

        if (Main.getServerType() == ServerType.CREATIVE) {
            if (bm.getCraftPlayer(player).getLevelByType(LevelType.CREATIVE_LEVEL) >= 15 && !player.hasPermission("plots.set.biome")) {
                bm.givePlayerManualLevelReward(player, 15, true);
            }
        }

        if (Main.getServerType() == ServerType.SURVIVAL_118) {
            if (bm.getCraftPlayer(player).getLevelByType(LevelType.SURVIVAL_118_LEVEL) >= 21 && !player.hasPermission("cmi.command.fly")) {
                bm.givePlayerManualLevelReward(player, 21, false);
                NotificationsAPI.Companion.createNotificationByUUID(
                        player.getUniqueId(),
                        NotificationType.SERVER,
                        NotificationPriority.NORMAL,
                        "SURVIVAL",
                        "Oprava: Odměna za 21. survival level",
                        "§7Nyní můžeš použít příkaz §e/fly §7k neomezenému lítání."
                );
            }
        }

        // Informování nových hráčů o návodu na server a wiki
        if (bm.getCraftPlayer(player).getLevelByType(LevelType.GLOBAL_LEVEL) <= 2) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (playerUtils.isOnline(player)) {
                    playerUtils.infoNewPlayer(player);
                }
            }, 20L * 60 * 30); // 30 minut
        }
    }

    /**
     * Tato metoda projede všechny level rewards co jsou načteny v cache a opraví je hráči.
     * <strong>Tato metoda se používá pouze při převodech účtu.</strong>
     * @param craftPlayer {@link CraftPlayer}
     */
    private void fixLevelRewardsForPlayer(CraftPlayer craftPlayer) {
        RewardManager.getRewards().forEach((levelReward -> {
            bm.givePlayerManualLevelReward(levelReward, craftPlayer.getPlayer(), false);
        }));
    }


}
