package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.CraftTokensAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftTokens_command implements CommandExecutor {

    private BasicManager manager = new BasicManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                long tokens = CraftTokensAPI.getTokens(p);
                p.sendMessage("§6Aktualne mas " + tokens + " CraftTokens.");
            }
        } else {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "add":
                case "give":
                    if (sender.hasPermission("crafteconomy.tokens.give")) {
                        if (args.length < 2) {
                            sender.sendMessage("§c§l(!) §cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long tokens = Long.valueOf(args[2]);
                            if (p != null) {
                                CraftTokensAPI.giveTokens(p, tokens);
                                sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §d" + tokens + " CT.");
                            } else {
                                CraftTokensAPI.giveOfflineTokens(args[1], tokens);
                                sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §d" + tokens + " CT.");
                            }
                            break;
                        } catch (Exception e) {
                            sender.sendMessage("§c§l(!) §cChyba pri zpracovani prikazu give! Spravne: §f/tokens give [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§c§l(!) §cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "take":
                case "remove":
                    if (sender.hasPermission("crafteconomy.tokens.take")) {
                        if (args.length < 2) {
                            sender.sendMessage("§c§l(!) §cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long tokens = Long.valueOf(args[2]);
                            if (p == null) {
                                CraftTokensAPI.takeOfflineTokens(args[1], tokens);
                                sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §d" + tokens + " CT.");
                                break;
                            }
                            if ((manager.getCraftPlayer(p).getTokens() - tokens) < 0) {
                                sender.sendMessage("§c§l(!) §cHrac nema dostatek CraftTokens! Ma k dispozici: " + manager.getCraftPlayer(p).getTokens());
                                break;
                            }
                            CraftTokensAPI.takeTokens(p, tokens);
                            sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §d" + tokens + " CT.");
                        } catch (Exception e) {
                            sender.sendMessage("§c§l(!) §cChyba pri zpracovani prikazu take! Spravne: §f/tokens take [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§c§l(!) §cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "help":
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    sender.sendMessage("");
                    sender.sendMessage("§9§lCraftTokens prikazy:");
                    sender.sendMessage("§7/tokens §8- §fZobrazeni tvych dostupnych CraftTokens");
                    sender.sendMessage("§7/tokens help §8- §fZobrazeni teto napovedy");
                    sender.sendMessage("");
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    break;
                default:
                    sender.sendMessage("§c§l(!) §cNeznamy subprikaz. Zkus §f/tokens help");
            }
        }
        return true;
    }
}
