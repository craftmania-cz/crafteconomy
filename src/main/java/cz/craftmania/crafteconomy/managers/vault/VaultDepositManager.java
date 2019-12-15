package cz.craftmania.crafteconomy.managers.vault;

import org.bukkit.entity.Player;

public class VaultDepositManager {

    public void startDeposit(final Player player) {
        //TODO: check pokud má víc jak 1 atd.
        DepositGUI.open(player);
    }
}
