package cz.craftmania.crafteconomy.commands.vault.BankCommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import cz.craftmania.crafteconomy.managers.vault.VaultEconomyManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("deposit|vlozit")
@Description("Umožní ti vložit svoje finance do banky")
public class DepositCommand extends BaseCommand {

    private static VaultEconomyManager vaultEconomyManager = new VaultEconomyManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lDeposit commands:");
        help.showHelp();
    }

    @Default
    public void deposit(CommandSender sender) {
        if (sender instanceof Player) {
            vaultEconomyManager.startDeposit((Player) sender);
        }
    }
}
