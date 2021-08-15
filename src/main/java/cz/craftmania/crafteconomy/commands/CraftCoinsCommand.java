package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.messages.chat.ChatInfo;
import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static co.aikar.commands.ACFBukkitUtil.sendMsg;

@CommandAlias("craftcoins|cc")
@Description("Zobrazuje tvůj aktuální počet CraftCoins")
public class CraftCoinsCommand extends BaseCommand {

    private static final BasicManager manager = new BasicManager();

    @HelpCommand // Automatický generovaný subpříkaz /cc help [subcommand]
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sendMsg(sender, "§e§lCraftCoins commands:"); // Nastavení textu v headu helpu
        help.showHelp(); // Zobrazí basic nápovědu
    }

    @Default // Deafult = Přkíaz bez argumentů -> /cc
    public void showCraftCoins(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            long coins = CraftCoinsAPI.getCoins(player);
            player.sendMessage("§e§l[*] §eAktuálně máš " + coins + " CraftCoins.");
        }
    }

    @Subcommand("give") // Subpříkaz
    @CommandPermission("crafteconomy.admin.craftcoins") // Požadovaný právo
    @CommandCompletion("@players [pocet]") // Doplňování v chatu
    @Syntax("[nick] [pocet]") // Nápověda pro /cc help
    public void adminGiveCraftCoins(CommandSender sender, String editedPlayer, long coinsToAdd) { // 1. je vždy sender, a pak zbytek podle CommandCompletion
        Player targetPlayer = Bukkit.getPlayer(editedPlayer);
        if (coinsToAdd <= 0) {
            sender.sendMessage("§c§l[!!] §cHodnota nesmí být menší než 0!");
            return;
        }
        if (targetPlayer != null) {
            CraftCoinsAPI.giveCoins(targetPlayer, coinsToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToAdd + " CC.");
        } else {
            CraftCoinsAPI.giveOfflineCoins(editedPlayer, coinsToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §6" + coinsToAdd + " CC.");
        }
    }

    @Subcommand("take")
    @CommandPermission("crafteconomy.admin.craftcoins")
    @CommandCompletion("@players [pocet]")
    public void adminTakeCraftCoins(CommandSender sender, String editedPlayer, long coinsToTake) {
        Player targetPlayer = Bukkit.getPlayer(editedPlayer);
        if (coinsToTake <= 0) {
            sender.sendMessage("§c§l[!!] §cHodnota nesmí být menší než 0!");
            return;
        }
        if (targetPlayer != null) {
            if ((manager.getCraftPlayer(targetPlayer).getCoins() - coinsToTake) < 0) {
                sender.sendMessage("§c§l[!] §cHráč nemá dostatek CraftCoins! Má k dispozici: " + manager.getCraftPlayer(targetPlayer).getCoins());
                return;
            }
            CraftCoinsAPI.takeCoins(targetPlayer, coinsToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToTake + " CC.");
        } else {
            CraftCoinsAPI.takeOfflineCoins(editedPlayer, coinsToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §6" + coinsToTake + " CC.");
        }
    }

    @Subcommand("pay")
    @CommandCompletion("@players [pocet]")
    public void playerPay(CommandSender sender, String selectedPlayer, long coinsToPay) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§l[!] §cPosílat částky může pouze hráč!");
            return;
        }
        if(sender.getName().equalsIgnoreCase(selectedPlayer)) {
            sender.sendMessage("§e§l[*] §eNemůžeš poslat CraftCoiny sobě.");
            return;
        }
        if (coinsToPay <= 0) {
            ChatInfo.error((Player) sender, "Hodnota nesmí být menší než 0!");
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(selectedPlayer);
        Player senderAsPlayer = (Player)sender;
        long senderCoins = CraftCoinsAPI.getCoins(senderAsPlayer);
        if(senderCoins < coinsToPay) {
            sender.sendMessage("§e§l[*] §eNemáš nedostatek CraftCoinů pro tuto platbu!");;
            return;
        }
        if (targetPlayer != null) {
            CraftCoinsAPI.payCoins(senderAsPlayer, targetPlayer, coinsToPay);
            sender.sendMessage("§e§l[*] §ePoslal jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToPay + " CC.");
        } else {
            sender.sendMessage("§c§l[!] §cNemůžeš posílat CraftCoiny hráči, který není online!");
        }
    }

}
