package cz.craftmania.crafteconomy.managers.vault;

import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.Main;
import org.bukkit.entity.Player;

import java.util.Map;

public class VaultEconomyManager {

    private static Map<String, Double> baltopCache;

    public VaultEconomyManager() {
        updateBaltopCache();
    }

    public void startDeposit(final Player player) {
        DepositGUI.open(player);
    }

    public void startWithdraw(final Player player) {
        SmartInventory.builder().size(5, 9).title("[B] Vybrání prostředků").provider(new WithdrawGUI()).build().open(player);
    }

    public void updateBaltopCache() {
        baltopCache = Main.getInstance().getMySQL().getVaultAllEcosWithNicks();
    }

    public Map<String, Double> getBaltopCache() {
        return baltopCache;
    }
}
