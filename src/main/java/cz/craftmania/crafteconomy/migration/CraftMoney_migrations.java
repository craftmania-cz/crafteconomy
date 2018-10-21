package cz.craftmania.crafteconomy.migration;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.CraftTokensAPI;
import cz.craftmania.crafteconomy.api.VoteTokensAPI;
import org.bukkit.entity.Player;

public class CraftMoney_migrations {

    private Player player;

    public CraftMoney_migrations(final Player player) {
        this.player = player;
    }

    public void migrate() {
        System.out.println("[CraftEconomy] Kontrola migrace CraftMoneyV1 pro: " + this.player.getName());
        if (Main.getInstance().getMySQL().hasCraftMoneyData(player)) {
            System.out.println("[CraftEconomy] Hrac ma data v CraftMoneyV1. Zahajeni migrace...");
            int crafttokens = Main.getInstance().getMySQL().getCraftMoneyV1DataCraftTokens(player);
            int votetokens = Main.getInstance().getMySQL().getCraftMoneyV1DataVoteTokens(player);
            System.out.println("[CraftEconomy] Pocet CraftTokens k prevodu: " + crafttokens);
            System.out.println("[CraftEconomy] Pocet VoteTokens k prevodu: " + votetokens);
            CraftTokensAPI.giveTokens(player, crafttokens);
            VoteTokensAPI.giveVoteTokens(player, votetokens);
            System.out.println("[CraftEconomy] CraftTokens a VoteTokens byly prevedeny!");
            Main.getInstance().getMySQL().deleteCraftMoneyV1Data(player);
            System.out.println("[CraftEconomy] Stare zaznamy smazany!");
        } else {
            System.out.println("[CraftEconomy] Hrac ma jiz zmigrovane data...");
        }
    }
}
