package cz.craftmania.crafteconomy.migration;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
import org.bukkit.entity.Player;

public class CraftCoins2_migration {

    private Player player;

    public CraftCoins2_migration(final Player player) {
        this.player = player;
    }

    public void migrate() {
        System.out.println("[CraftEconomy] Kontrola migrace CraftCoinsV2 pro: " + this.player.getName());
        if (Main.getInstance().getMySQL().hasCraftCoinsV2Data(player)) {
            System.out.println("[CraftEconomy] Hrac ma data v CraftCoinsV2. Zahajeni migrace...");
            long coins = Main.getInstance().getMySQL().getCraftCoinsV2Data(player);
            System.out.println("[CraftEconomy] Pocet CraftCoins k prevodu: " + coins);
            CraftCoinsAPI.giveCoins(player, coins);
            System.out.println("[CraftEconomy] CraftCoiny byly prevedeny!");
            Main.getInstance().getMySQL().deleteCraftCoinsV2Data(player);
            System.out.println("[CraftEconomy] Stare zaznamy smazany!");
        } else {
            System.out.println("[CraftEconomy] Hrac ma jiz zmigrovane data...");
        }
    }


}
