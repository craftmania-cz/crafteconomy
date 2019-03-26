package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCoins_command implements CommandExecutor {

    private BasicManager manager = new BasicManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                long coins = CraftCoinsAPI.getCoins(p);
                p.sendMessage("§6Aktualne mas " + coins + " CraftCoins.");
            }
        } else {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "add":
                case "give":
                    if (sender.hasPermission("crafteconomy.coins.give")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long coins = Long.valueOf(args[2]);
                            CraftCoinsAPI.giveCoins(p, coins);
                            sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §6" + coins + " CC.");
                            break;
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu give! Spravne: /coins give [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                        break;
                    }
                case "take":
                case "remove":
                    if (sender.hasPermission("crafteconomy.coins.take")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long coins = Long.valueOf(args[2]);
                            if ((manager.getCraftPlayer(p).getCoins() - coins) < 0) {
                                sender.sendMessage("cHrac nema dostatek CraftCoins! Ma k dispozici: " + manager.getCraftPlayer(p).getCoins());
                                break;
                            }
                            CraftCoinsAPI.takeCoins(p, coins);
                            sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §6" + coins + " CC.");
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu take! Spravne: /coins take [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "help":
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    sender.sendMessage("");
                    sender.sendMessage("§a§lCraftCoins prikazy:");
                    sender.sendMessage("§7/coins §8- §fZobrazeni tvych dostupnych CraftCoins");
                    sender.sendMessage("§7/coins help §8- §fZobrazeni teto napovedy");
                    sender.sendMessage("");
                    sender.sendMessage("§b§lTIP! §7Alternativni prikazy k /coins jsou take /cc nebo /craftcoins");
                    sender.sendMessage("");
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    break;
                default:
                    sender.sendMessage("§cNeznamy subprikaz. Zkus /coins help");
            }
        }
        return true;
    }
}
