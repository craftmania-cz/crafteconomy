package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyPlayerPayEvent;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.DynamicSuggestedStringArgument;
import io.github.jorelali.commandapi.api.arguments.IntegerArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class PayCommand {

    public static void register() {

        // Default: /pay
        CommandAPI.getInstance().register("pay", new String[]{}, null, (sender, args) -> {
            sender.sendMessage("§c§l[!] §cŠpatné použití příkazu: §f/pay [nick] [částka]");
        });

        // Default: /pay [nick] [castka]
        LinkedHashMap<String, Argument> payArguments = new LinkedHashMap<>();
        payArguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> p1.getName()).toArray(String[]::new)));
        payArguments.put("money", new IntegerArgument());
        CommandAPI.getInstance().register("pay", new String[]{}, payArguments, (sender, args) -> {
            String reciever = (String) args[0];
            long moneyToSend = Integer.parseInt(args[1].toString());
            if (moneyToSend <= 0) {
                sender.sendMessage("§c§l[!] §cNelze odesílat nulovou nebo zápornou hodnotu!");
                return;
            }
            if (sender.getName().equals(reciever)) {
                sender.sendMessage("§c§l[!] §cSám sobě nelze zasílat částky, bankovní podvody nevedeme!");
                return;
            }
            if (moneyToSend > Main.getVaultEconomy().getBalance(sender.getName())) {
                sender.sendMessage("§c§l[!] §cNemáš dostatek peněz k odeslání zadané částky.");
                return;
            }
            Player playerReciever = Bukkit.getPlayer(reciever);
            Player playerSender = Bukkit.getPlayer(String.valueOf(sender.getName()));
            if (playerReciever != null) {
                Main.getVaultEconomy().withdrawPlayer(playerSender, moneyToSend);
                Main.getVaultEconomy().depositPlayer(playerReciever, moneyToSend);
                sender.sendMessage("§e§l[*] §eOdeslal jsi hráči: §f" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                playerReciever.sendMessage("§e§l[*] §eObdržel jsi peníze od §f" + playerSender.getName() + " §7- §a" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                Bukkit.getPluginManager().callEvent(new CraftEconomyPlayerPayEvent(playerSender, playerReciever, moneyToSend));
            } else {
                sender.sendMessage("§c§l[!] §cHráč není online, nelze mu zaslat peníze!");
            }
        });
    }
}
