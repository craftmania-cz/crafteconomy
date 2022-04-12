package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.RewardManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayerJoinListener implements Listener {

    private Main main;
    private BasicManager bm = new BasicManager();
    private PlayerUtils playerUtils = new PlayerUtils();

    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent e) throws ExecutionException, InterruptedException {
        final Player player = e.getPlayer();

        // Zakladni nacteni dat do cache a vytvoření objektu
        bm.loadPlayerData(player).thenApplyAsync((craftPlayer) -> {
            if (craftPlayer == null) {
                Logger.danger("Načítání dat pro " + player.getName() + " selhalo.");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 3.0f, 1.0f);
                ChatInfo.DANGER.send(player, "");
                ChatInfo.DANGER.send(player, "Tvoje data selhaly při načtení! Toto je fatální chyba!");
                ChatInfo.DANGER.send(player, "Odpoj se nebo se zkus znova připojit.");
                ChatInfo.DANGER.send(player, "Pokud stále vidíš tuto zprávu, oznam to neprodleně vedení serveru!");
                ChatInfo.DANGER.send(player, "");
            } else {
                // Rekalkulace legelů z cache
                craftPlayer.recalculateGlobalLevel();

                // Vytvoření a načtení vault money do craftplayer
                if (Main.getInstance().isVaultEconomyEnabled()) {
                    bm.loadVaultPlayerData(player);
                }
            }
            return craftPlayer;
        }).thenAcceptAsync((craftPlayer) -> {
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

            // Informování nových hráčů o návodu na server a wiki
            if (bm.getCraftPlayer(player).getLevelByType(LevelType.GLOBAL_LEVEL) <= 3) {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    if (playerUtils.isOnline(player)) {
                        playerUtils.infoNewPlayer(player);
                    }
                }, 20L * 60 * 5); // 5 minut
            }
        });
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
