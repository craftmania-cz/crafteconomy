package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class CraftCoinsCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /craftcoins
        CommandAPI.getInstance().register("craftcoins", new String[] {"cc"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long coins = CraftCoinsAPI.getCoins(p);
            p.sendMessage("§e§l[*] §eAktualne mas " + coins + " CraftCoins.");
        });

        // Admin prikaz: /craftcoins give [player] [value]
        LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
        arguments.put("prikaz", new StringArgument().overrideSuggestions("add", "remove", "take", "give").withPermission(CommandPermission.fromString("crafteconomy.admin")));
        arguments.put("hrac", new StringArgument());
        arguments.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("craftcoins", new String[] {"cc"}, arguments, (sender, args) -> {
            String subCommand = (String)args[0];
            switch (subCommand.toLowerCase()) {
                case "add": case "give":
                    String playerName = (String)args[1];
                    Player p = Bukkit.getPlayer(playerName);
                    long coinsToAdd = Long.valueOf((Integer)args[2]);
                    if (p != null) {
                        CraftCoinsAPI.giveCoins(p, coinsToAdd);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + coinsToAdd + " CC.");
                    } else {
                        CraftCoinsAPI.giveOfflineCoins(playerName, coinsToAdd);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + coinsToAdd + " CC.");
                    }
                    break;
                case "take": case "remove":
                    String playerName2 = (String)args[1];
                    Player player2 = Bukkit.getPlayer(playerName2);
                    long coinsToTake = Long.valueOf((Integer)args[2]);
                    if (player2 == null) {
                        CraftCoinsAPI.takeOfflineCoins(playerName2, coinsToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + args[1] + " §7- §6" + coinsToTake + " CC.");
                        break;
                    }
                    if ((manager.getCraftPlayer(player2).getCoins() - coinsToTake) < 0) {
                        sender.sendMessage("§c§l[!] §cHrac nema dostatek CraftCoins! Ma k dispozici: " + manager.getCraftPlayer(player2).getCoins());
                        break;
                    }
                    CraftCoinsAPI.takeCoins(player2, coinsToTake);
                    sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName2 + " §7- §6" + coinsToTake + " CC.");
                    break;
                case "pay":
                    if(!sender.hasPermission("crafteconomy.pay")) {
                        sender.sendMessage("§e§l[*] §cNa toto nemáš práva.");
                        return;
                    }

                    if(!(sender instanceof Player)) {
                        sender.sendMessage("§c§l[!] §cTento příkaz nelze použít v konzoli!");
                        return;
                    }

                    Player senderPlayer = (Player) sender;
                    String targetName = (String)args[1];
                    Player targetPlayer = Bukkit.getPlayer(targetName);
                    long coinsToPay = Long.valueOf((Integer)args[2]);
                    long senderCoins = CraftCoinsAPI.getCoins(senderPlayer);

                    if(senderPlayer.getName().equalsIgnoreCase(targetName)) {
                        sender.sendMessage("§e§l[*] §eNemůžeš poslat CraftCoiny sobě.");
                        return;
                    }

                    if(senderCoins < coinsToPay) {
                        sender.sendMessage("§e§l[*] §eNemáš nedostatek CraftCoinů pro tuto platbu!");;
                        return;
                    }

                    if (targetPlayer != null) {
                        CraftCoinsAPI.payCoins(senderPlayer, targetPlayer, coinsToPay);
                        sender.sendMessage("§e§l[*] §ePoslal jsi hráči §f" + targetPlayer.getName() + " §7- §6" + coinsToPay + " CC.");
                    } else {
                        sender.sendMessage("§c§l[!] §cNemůžeš posílat CraftCoiny hráči, který není online!");
                    }
                    break;
            }
        });
    }
}
