package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyPlayerPayEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

@CommandAlias("pay")
@Description("Umožňuje posílat jiným hráčům peníze")
public class PayCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lPay commands:");
        help.showHelp();
    }

    @Default
    @CommandCompletion("@players [castka]")
    @Syntax("[hrac] [castka]")
    public void sendMoney(CommandSender sender, String receiverPlayer,  long moneyToSend) {
        if (sender instanceof Player) {
            if (moneyToSend <= 0) {
                sender.sendMessage("§c§l[!] §cNelze odesílat nulovou nebo zápornou hodnotu!");
                return;
            }
            if (sender.getName().equals(receiverPlayer)) {
                sender.sendMessage("§c§l[!] §cSám sobě nelze zasílat částky, bankovní podvody nevedeme!");
                return;
            }
            if (moneyToSend > Main.getVaultEconomy().getBalance(sender.getName())) {
                sender.sendMessage("§c§l[!] §cNemáš dostatek peněz k odeslání zadané částky.");
                return;
            }
            Player playerReceiver = Bukkit.getPlayer(receiverPlayer);
            Player playerSender = (Player) sender;
            if (playerReceiver != null) {
                if (manager.getCraftPlayer(playerReceiver).getPayToggle()) {
                    Main.getVaultEconomy().withdrawPlayer(playerSender, moneyToSend);
                    Main.getVaultEconomy().depositPlayer(playerReceiver, moneyToSend);
                    sender.sendMessage("§e§l[*] §eOdeslal jsi hráči: §f" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                    playerReceiver.sendMessage("§e§l[*] §eObdržel jsi peníze od §f" + playerSender.getName() + " §7- §a" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyPlayerPayEvent(playerSender, playerReceiver, moneyToSend)));
                } else {
                    playerSender.sendMessage("§c§l[!] §cTento hráč má vypnuté přijímání peněz!");
                    playerReceiver.sendMessage("§e§l[*] §eHráč " + playerSender.getName() + " se ti snaží poslat peníze, ale máš vypnutý /paytoggle!");
                }
            } else {
                sender.sendMessage("§c§l[!] §cHráč není online, nelze mu zaslat peníze!");
            }
        }
    }
}
