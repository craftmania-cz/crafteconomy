package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.menu.LevelGUI;
import cz.craftmania.crafteconomy.menu.VotePassGUI;
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
            Player player = (Player) sender;
            SmartInventory.builder().size(3, 9).title(":offset_-180::levels_sidebox_info::offset_1::levels_menu:").provider(new LevelGUI()).build().open(player);
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
                    generateServerLevelMessage(player, "Survival", LevelType.SURVIVAL_117_LEVEL, LevelType.SURVIVAL_117_EXPERIENCE);
                    break;
                case "skyblock":
                    generateServerLevelMessage(player, "Skyblock", LevelType.SKYBLOCK_117_LEVEL, LevelType.SKYBLOCK_117_EXPERIENCE);
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
