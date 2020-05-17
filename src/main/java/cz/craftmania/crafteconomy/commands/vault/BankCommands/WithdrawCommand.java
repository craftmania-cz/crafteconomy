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

@CommandAlias("withdraw|vybrat")
@Description("Umožní ti vybrat svoje finance z banky")
public class WithdrawCommand extends BaseCommand {

    private static VaultEconomyManager vaultEconomyManager = new VaultEconomyManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lWithdraw commands:");
        help.showHelp();
    }

    @Default
    public void withdraw(CommandSender sender) {
        if (sender instanceof Player) {
            vaultEconomyManager.startWithdraw((Player) sender);
        }
    }
}
