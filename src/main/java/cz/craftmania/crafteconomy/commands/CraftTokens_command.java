package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
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
                p.sendMessage("§dAktualne mas " + tokens + " CraftTokens.");
            }
        } else {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "add":
                case "give":
                    if (sender.hasPermission("crafteconomy.tokens.give")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long tokens = Long.valueOf(args[2]);
                            CraftTokensAPI.giveTokens(p, tokens);
                            sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §d" + tokens + " CT.");
                            break;
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu give! Spravne: /tokens give [nick] [castka]");
                        }
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "take":
                case "remove":
                    if (sender.hasPermission("crafteconomy.tokens.take")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long tokens = Long.valueOf(args[2]);
                            if ((manager.getCraftPlayer(p).getTokens() - tokens) <= 0) {
                                sender.sendMessage("cHrac nema dostatek CraftTokens! Ma k dispozici: " + manager.getCraftPlayer(p).getTokens());
                                break;
                            }
                            CraftTokensAPI.takeTokens(p, tokens);
                            sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §d" + tokens + " CT.");
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu take! Spravne: /tokens take [nick] [castka]");
                        }
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                    }
                    break;
                default:
                    sender.sendMessage("§cNeznamy subprikaz. Zkus /tokens help");
            }
        }
        return true;
    }
}
