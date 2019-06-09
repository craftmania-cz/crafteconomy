package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.FormatUtils;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Level_command implements CommandExecutor {

    private BasicManager manager = new BasicManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                CraftPlayer craftPlayer = manager.getCraftPlayer(p);
                p.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                p.sendMessage("");
                p.sendMessage("§6§lGlobal rank");
                p.sendMessage("§eLevel: §f" + craftPlayer.getLevel() + " §7(dokonceno: " + FormatUtils.roundDouble((LevelUtils.getPercentageToNextLevel(craftPlayer.getExperience()) * 100), 3) + "%, celkem: " + craftPlayer.getExperience() + " XP)");
                p.sendMessage("§bKarma: §f0 §8| §aAchievmentPoints: §f0");
                p.sendMessage("");
                p.sendMessage("§eExp do level up: §f" + (int) (LevelUtils.getExpFromLevelToNext(craftPlayer.getLevel() + 1) - craftPlayer.getExperience()) + " XP");
                p.sendMessage("");
                p.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
            }
        } else {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "add":
                case "give":
                    if (sender.hasPermission("crafteconomy.level.give")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long levels = Long.valueOf(args[2]);
                            if (p != null) {
                                LevelAPI.addLevel(p, (int) levels);
                                sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §6" + levels + " LVL.");
                            } else {
                                LevelAPI.addOfflineLevel(args[1], (int) levels);
                                sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §6" + levels + " LVL.");
                            }
                            break;
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu give! Spravne: /level give [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                        break;
                    }
                case "take":
                case "remove":
                    if (sender.hasPermission("crafteconomy.level.take")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long levels = Long.valueOf(args[2]);
                            if (p == null) {
                                LevelAPI.takeOfflineLevel(args[1], (int) levels);
                                sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §6" + levels + " LVL.");
                                break;
                            }
                            if ((manager.getCraftPlayer(p).getLevel() - levels) < 0) {
                                sender.sendMessage("§cHrac nema dostatek Levels! Ma k dispozici: " + manager.getCraftPlayer(p).getLevel());
                                break;
                            }
                            LevelAPI.takeLevel(p, (int) levels);
                            sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §6" + levels + " LVL.");
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu take! Spravne: /level take [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                    }
                    break;
                case "addxp":
                case "givexp":
                    if (sender.hasPermission("crafteconomy.exp.give")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long exp = Long.valueOf(args[2]);
                            LevelAPI.addExp(p, (int) exp);
                            sender.sendMessage("§aPridal jsi hraci §f" + args[1] + " §7- §6" + exp + " EXP.");
                            break;
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu give! Spravne: /level givexp [nick] [castka]");
                        }
                        break;
                    } else {
                        sender.sendMessage("§cNa toto nemas dostatecna prava!");
                        break;
                    }
                case "takexp":
                case "removexp":
                    if (sender.hasPermission("crafteconomy.level.take")) {
                        if (args.length < 2) {
                            sender.sendMessage("§cSpatne zadany prikaz! Chybi hrac nebo castka!");
                            break;
                        }
                        try {
                            Player p = Bukkit.getPlayer(args[1]);
                            long exp = Long.valueOf(args[2]);
                            if (p == null) {
                                LevelAPI.takeOfflineLevel(args[1], (int) exp);
                                sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §6" + exp + " LVL.");
                                break;
                            }
                            if ((manager.getCraftPlayer(p).getExperience() - exp) < 0) {
                                sender.sendMessage("§cHrac nema dostatek EXP! Ma k dispozici: " + manager.getCraftPlayer(p).getExperience());
                                break;
                            }
                            LevelAPI.takeExp(p, (int) exp);
                            sender.sendMessage("§cOdebral jsi hraci §f" + args[1] + " §7- §6" + exp + " LVL.");
                        } catch (Exception e) {
                            sender.sendMessage("§cChyba pri zpracovani prikazu take! Spravne: /level takexp [nick] [castka]");
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
                    sender.sendMessage("§7/level §8- §fZobrazeni tvych dostupnych CraftCoins");
                    sender.sendMessage("§7/level help §8- §fZobrazeni teto napovedy");
                    sender.sendMessage("");
                    sender.sendMessage("§b§lTIP! §7Alternativni prikaz k /level je /lvl");
                    sender.sendMessage("");
                    sender.sendMessage("§e\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
                    break;
                default:
                    sender.sendMessage("§cNeznamy subprikaz. Zkus /level help");
            }
        }
        return true;
    }
}
