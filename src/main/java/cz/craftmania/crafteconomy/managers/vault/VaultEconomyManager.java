package cz.craftmania.crafteconomy.managers.vault;

import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import org.bukkit.entity.Player;

public class VaultEconomyManager {

    public void startDeposit(final Player player) {
        DepositGUI.open(player);
    }

    public void startWithdraw(final Player player) {
        SmartInventory.builder().size(5, 9).title("[B] Vybrání prostředků").provider(new WithdrawGUI()).build().open(player);
    }
}
