package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.crafteconomy.managers.vault.VaultEconomyManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import org.bukkit.entity.Player;

public class BankCommand {

    private static VaultEconomyManager vaultEconomyManager = new VaultEconomyManager();

    public static void register() {

        // Default: /deposit
        CommandAPI.getInstance().register("deposit", new String[]{"vlozit"}, null, (sender, args) -> {
            Player player = (Player) sender;
            vaultEconomyManager.startDeposit(player);
        });

        // Default: /withdraw
        CommandAPI.getInstance().register("withdraw", new String[]{"vybrat"}, null, (sender, args) -> {
            Player player = (Player) sender;
            vaultEconomyManager.startWithdraw(player);
        });
    }
}
