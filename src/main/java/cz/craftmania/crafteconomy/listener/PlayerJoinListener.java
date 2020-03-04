package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
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
            assert sqlNick != null;
            if (!sqlNick.equals(player.getName())) {
                Main.getInstance().getMySQL().updateNickInTable("player_economy_" + Main.getServerType().toString().toLowerCase(), player);
            }
            
            // Finální nastavení hodnoty peněz na učet
            craftPlayer.setMoney(main.getMySQL().getVaultEcoBalance(player.getUniqueId()));
        }

        // Opravy práv pro achievementy
        if (Main.getServerType() == ServerType.CREATIVE) {
            //this.creativeAchievemenntFixes(craftPlayer, player);
        }

    }

    /**
     * Tato metoda opravuje hráči práva, pokud proběhla nějaká dřívější změna v achievementech a hráč nemusí mít tedy danou výhodu.
     * @param craftPlayer CraftPlayer objekt
     * @param player Player objekt
     */
    private void creativeAchievemenntFixes(CraftPlayer craftPlayer, Player player) {
        if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 4 && !player.hasPermission("rc.bypass.disable.interacting.in-hand.COD_SPAWN_EGG")) {
            bm.givePlayerLevelReward(player, 4);
        }
        if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 7 && !player.hasPermission("rc.bypass.disable.interacting.in-hand.PIG_SPAWN_EGG")) {
            bm.givePlayerLevelReward(player, 7);
        }
        if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 9 && !player.hasPermission("rc.bypass.disable.interacting.in-hand.FOX_SPAWN_EGG")) {
            bm.givePlayerLevelReward(player, 9);
        }
    }
}
