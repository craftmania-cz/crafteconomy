package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.VoteTokensAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class    VoteTokens_command implements CommandExecutor {

    private BasicManager manager = new BasicManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                long tokens = VoteTokensAPI.getVoteTokens(p);
                p.sendMessage("§6Aktualne mas " + tokens + " VoteTokens.");
            }
        } else {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "add":
                case "give":
                    if (sender.hasPermission("crafteconomy.votetokens.give")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long tokens = Long.valueOf(args[2]);
                            VoteTokensAPI.giveVoteTokens(p, tokens);
                            sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §b" + tokens + " VT.");
                            break;
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu give! Spravne: /vt give [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "take":
                case "remove":
                    if (sender.hasPermission("crafteconomy.votetokens.take")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long tokens = Long.valueOf(args[2]);
                            if ((manager.getCraftPlayer(p).getVoteTokens() - tokens) < 0) {
                                sender.sendMessage("cHrac nema dostatek VoteTokens! Ma k dispozici: " + manager.getCraftPlayer(p).getVoteTokens());
                                break;
                            }
                            VoteTokensAPI.takeVoteTokens(p, tokens);
                            sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §d" + tokens + " VT.");
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu take! Spravne: /vt take [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "help":
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    sender.sendMessage("");
                    sender.sendMessage("§d§lVoteTokens:");
                    sender.sendMessage("§7/vtokens §8- §fZobrazeni tvych dostupnych VoteTokens");
                    sender.sendMessage("§7/vtokens help §8- §fZobrazeni teto napovedy");
                    sender.sendMessage("");
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    break;
                default:
                    sender.sendMessage("§cNeznamy subprikaz. Zkus /vt help");
            }
        }
        return true;
    }
}
