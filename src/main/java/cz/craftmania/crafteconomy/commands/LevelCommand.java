package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.FormatUtils;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("level|lvl")
@Description("Zobrazuje tvůj aktuální level")
public class LevelCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lLevel commands:");
        help.showHelp();
    }

    @Default
    public void showLevel(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            CraftPlayer craftPlayer = manager.getCraftPlayer(p);

            long totalGlobalLevel = craftPlayer.getLevelByType(LevelType.GLOBAL_LEVEL);

            p.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
            p.sendMessage("");
            p.sendMessage("§6§lGlobální rank"); //TODO: Předělat na menu
            p.sendMessage("§eCelkový level: §f" + totalGlobalLevel);
            p.sendMessage("§eServer levels: ");
            p.sendMessage("§8- §aSurv §7[" + craftPlayer.getLevelByType(LevelType.SURVIVAL_LEVEL) + "], §bSky §7[" + craftPlayer.getLevelByType(LevelType.SKYBLOCK_LEVEL) + "], §6Crea §7[" + craftPlayer.getLevelByType(LevelType.CREATIVE_LEVEL) + "]§7, §2Vani §7[" + craftPlayer.getLevelByType(LevelType.VANILLA_LEVEL) + "§7]");
            p.sendMessage("§8- §9Pris §7[" + craftPlayer.getLevelByType(LevelType.PRISON_LEVEL) + "], §cVan-Anar §7[" + craftPlayer.getLevelByType(LevelType.ANARCHY_LEVEL) + "]");
            p.sendMessage("§bKarma: §f0");
            p.sendMessage("§dQuest Points: §f" + craftPlayer.getEconomyByType(EconomyType.QUEST_POINTS));
            p.sendMessage("");
            p.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        }
    }

    @Default
    @CommandCompletion("survival|skyblock|vanilla|creative|skycloud|hardcore-vanilla|prison|anarchy")
    @Syntax("[server]")
    public void showLevelByServer(CommandSender sender, String server) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (server.toLowerCase()) {
                case "survival":
                    generateServerLevelMessage(player, "Survival", LevelType.SURVIVAL_LEVEL, LevelType.SURVIVAL_EXPERIENCE);
                    break;
                case "skyblock":
                    generateServerLevelMessage(player, "Skyblock", LevelType.SKYBLOCK_LEVEL, LevelType.SKYBLOCK_EXPERIENCE);
                    break;
                case "creative":
                    generateServerLevelMessage(player, "Creative", LevelType.CREATIVE_LEVEL, LevelType.CREATIVE_EXPERIENCE);
                    break;
                case "vanilla":
                    generateServerLevelMessage(player, "Vanilla", LevelType.VANILLA_LEVEL, LevelType.VANILLA_EXPERIENCE);
                    break;
                case "skycloud":
                    generateServerLevelMessage(player, "Skycloud", LevelType.SKYCLOUD_LEVEL, LevelType.SKYCLOUD_EXPERIENCE);
                    break;
                case "hardcore-vanilla":
                    generateServerLevelMessage(player, "Hardcore Vanilla", LevelType.HARDCORE_VANILLA_LEVEL, LevelType.HARDCORE_VANILLA_EXPERIENCE);
                    break;
                case "prison":
                    generateServerLevelMessage(player, "Prison", LevelType.PRISON_LEVEL, LevelType.PRISON_EXPERIENCE);
                    break;
                case "anarchy":
                    generateServerLevelMessage(player, "Vanilla: Anarchy", LevelType.ANARCHY_LEVEL, LevelType.ANARCHY_EXPERIENCE);
                    break;
            }
        }
    }

    private static void generateServerLevelMessage(Player player, String server, LevelType levelType, LevelType experienceType) {
        CraftPlayer craftPlayer = manager.getCraftPlayer(player);
        long actualLevel = craftPlayer.getLevelByType(levelType);
        long totalExperience = craftPlayer.getExperienceByType(experienceType);
        double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(levelType));
        double expForLevelUp = totalExperienceForNextLevel - craftPlayer.getExperienceByType(experienceType);
        player.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        player.sendMessage("");
        player.sendMessage("§6§lServer rank: §f" + server);
        player.sendMessage("§eLevel: §f" + actualLevel + " §7(dokončeno: " + FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 3) + "%, celkem: " + totalExperience + " XP)");
        player.sendMessage("");
        player.sendMessage("§eExp do level up: §f" + expForLevelUp + " XP");
        player.sendMessage("");
        player.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
    }
}
