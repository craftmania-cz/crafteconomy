package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Main main;
    private BasicManager bm = new BasicManager();

    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        // Zakladni nacteni dat do cache a vytvoření objektu
        CraftPlayer craftPlayer = BasicManager.loadPlayerData(player);

        // Vytvoření a načtení vault money do craftplayer
        if (Main.getInstance().isVaultEconomyEnabled()) {

            if (!main.getMySQL().hasVaultEcoProfile(player.getUniqueId())) {
                main.getMySQL().createVaultEcoProfile(player);
            }

            // Update nicku v DB kvůli změně nicku
            String sqlNick = Main.getInstance().getMySQL().getNickFromTable("player_economy_" + Main.getServerType().toString().toLowerCase(), player);
            if (sqlNick != null) { //TODO: Why null?
                if (!sqlNick.equals(player.getName())) {
                    Main.getInstance().getMySQL().updateNickInTable("player_economy_" + Main.getServerType().toString().toLowerCase(), player);
                }
            }
            
            // Finální nastavení hodnoty peněz na učet
            craftPlayer.setMoney(main.getMySQL().getVaultEcoBalance(player.getUniqueId()));
        }

        // Opravy práv pro achievementy
        // EXTRA: Toto právo se dává pouze, pokud hráč požádá o převod
        /*if (player.hasPermission("crafteconomy.levels.past-fix")) {
            Logger.info("Hrac " + player.getName() + " ma pravo na opravu Levels. Spustim...");
            this.fixLevelRewardsForPlayer(craftPlayer);
        }*/

        if (Main.getServerType() == ServerType.SKYBLOCK) {
            if (bm.getCraftPlayer(player).getLevelByType(LevelType.SKYBLOCK_LEVEL) >= 20 && !player.hasPermission("shopguiplus.shops.special")) {
                bm.givePlayerManualLevelReward(player, 20, true);
            }
        }

    }

    /**
     * Tato metoda projede všechny level rewards co jsou načteny v cache a opraví je hráči.
     * <strong>Tato metoda se používá pouze při převodech účtu.</strong>
     * @param craftPlayer {@link CraftPlayer}
     */
    private void fixLevelRewardsForPlayer(CraftPlayer craftPlayer) {
        ProprietaryManager.getServerLevelRewardsList().forEach((levelReward -> {
            bm.givePlayerManualLevelReward(levelReward, craftPlayer.getPlayer(), false);
        }));
    }


}
