package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static co.aikar.commands.ACFBukkitUtil.sendMsg;

@CommandAlias("craftcoins|cc")
@Description("Zobrazuje tvůj aktuální počet CraftCoins")
public class CraftCoinsCommand extends BaseCommand {

    private static final BasicManager manager = new BasicManager();

    @HelpCommand // Automatický generovaný subpříkaz /cc help [subcommand]
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sendMsg(sender, "§e§lCraftCoins commands:"); // Nastavení textu v headu helpu
        help.showHelp(); // Zobrazí basic nápovědu
    }

    @Default // Deafult = Přkíaz bez argumentů -> /cc
    public void showCraftCoins(CommandSender sender) {
        if (sender instanceof Player player) {
            long coins = EconomyAPI.CRAFT_COINS.get(player);
            ChatInfo.INFO.send(player, "Aktuálně máš §6" + coins + " {c}CraftCoins.");
        }
    }

    @Subcommand("give") // Subpříkaz
    @CommandPermission("crafteconomy.admin.craftcoins") // Požadovaný právo
    @CommandCompletion("@players [pocet]") // Doplňování v chatu
    @Syntax("[nick] [pocet]") // Nápověda pro /cc help
    public void adminGiveCraftCoins(CommandSender sender, String editedPlayer, long coinsToAdd) { // 1. je vždy sender, a pak zbytek podle CommandCompletion
        Player targetPlayer = Bukkit.getPlayer(editedPlayer);
        if (coinsToAdd <= 0) {
            ChatInfo.DANGER.send(sender, "Hodnota nesmí být menší nebo rovna 0!");
            return;
        }
        if (targetPlayer != null) {
            EconomyAPI.CRAFT_COINS.give(targetPlayer, coinsToAdd);
            ChatInfo.INFO.send(sender, "Přidal jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToAdd + " {c}CC.");
        } else {
            EconomyAPI.CRAFT_COINS.giveOffline(editedPlayer, coinsToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §6" + coinsToAdd + " CC.");
        }
    }

    @Subcommand("take")
    @CommandPermission("crafteconomy.admin.craftcoins")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminTakeCraftCoins(CommandSender sender, String editedPlayer, long coinsToTake) {
        Player targetPlayer = Bukkit.getPlayer(editedPlayer);
        if (coinsToTake <= 0) {
            sender.sendMessage("§c§l[!!] §cHodnota nesmí být menší než 0!");
            return;
        }
        if (targetPlayer != null) {
            if ((manager.getCraftPlayer(targetPlayer).getEconomyByType(EconomyType.CRAFT_COINS) - coinsToTake) < 0) {
                sender.sendMessage("§c§l[!] §cHráč nemá dostatek CraftCoins! Má k dispozici: " + manager.getCraftPlayer(targetPlayer).getEconomyByType(EconomyType.CRAFT_COINS));
                return;
            }
            EconomyAPI.CRAFT_COINS.take(targetPlayer, coinsToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToTake + " CC.");
        } else {
            EconomyAPI.CRAFT_COINS.takeOffline(editedPlayer, coinsToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §6" + coinsToTake + " CC.");
        }
    }

    @Subcommand("pay")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void playerPay(CommandSender sender, String selectedPlayer, long coinsToPay) {
        if (!(sender instanceof Player senderAsPlayer)) {
            ChatInfo.INFO.send(sender, "Posílat částky může pouze hráč!");
            return;
        }
        if(sender.getName().equalsIgnoreCase(selectedPlayer)) {
            ChatInfo.INFO.send(sender, "Nemůžeš poslat CraftCoiny sobě.");
            return;
        }
        if (coinsToPay <= 0) {
            ChatInfo.INFO.send(sender, "Hodnota nesmí být menší než 0!");
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(selectedPlayer);
        if (targetPlayer == sender) {
            ChatInfo.INFO.send(sender, "Nemůžeš poslat CraftCoiny sobě.");
            return;
        }
        long senderCoins = EconomyAPI.CRAFT_COINS.get(senderAsPlayer);
        if(senderCoins < coinsToPay) {
            ChatInfo.INFO.send(sender, "Nemáš nedostatek CraftCoinů pro tuto platbu!");
            return;
        }
        if (targetPlayer != null) {
            EconomyAPI.CRAFT_COINS.payBetween(senderAsPlayer, targetPlayer, coinsToPay);
            ChatInfo.INFO.send(sender, "Poslal jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToPay + " {c}CC.");
        } else {
            ChatInfo.DANGER.send(sender, "Nemůžeš posílat CraftCoiny hráči, který není online!");
        }
    }

}
