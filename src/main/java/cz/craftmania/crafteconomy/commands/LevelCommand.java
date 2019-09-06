package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.FormatUtils;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.arguments.*;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class LevelCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /level -> globální level
        CommandAPI.getInstance().register("level", new String[] {"lvl"}, null, (sender, args) -> {
            Player p = (Player) sender;
            CraftPlayer craftPlayer = manager.getCraftPlayer(p);

            long totalGlobalLevel = craftPlayer.getLevelByType(LevelType.GLOBAL_LEVEL);

            p.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
            p.sendMessage("");
            p.sendMessage("§6§lGlobal rank"); //TODO: Předělat na menu
            p.sendMessage("§eCelkovy level: §f" + totalGlobalLevel);
            p.sendMessage("§eServer levels: §aSurv §7[" + craftPlayer.getLevelByType(LevelType.SURVIVAL_LEVEL) + "], §bSky §7[" + craftPlayer.getLevelByType(LevelType.SKYBLOCK_LEVEL) + "], §6Crea §7[" + craftPlayer.getLevelByType(LevelType.CREATIVE_LEVEL) + "]§7, §2Vani §7[" + craftPlayer.getLevelByType(LevelType.VANILLA_LEVEL) + "]");
            p.sendMessage("§bKarma: §f0");
            p.sendMessage("§dAchievmentPoints: §f" + craftPlayer.getAchievementPoints());
            p.sendMessage("");
            p.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        });

        // Server type: /level survival|skyblock...
        LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
        arguments.put("server", new StringArgument().overrideSuggestions("survival", "skyblock", "creative", "vanilla", "skycloud"));

        CommandAPI.getInstance().register("level", new String[] {"lvl"}, arguments, (sender, args) -> {
            Player player = (Player) sender;
            String subCommand = (String)args[0];
            switch (subCommand.toLowerCase()) {
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
            }
        });
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
        player.sendMessage("§eLevel: §f" + actualLevel + " §7(dokonceno: " + FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 3) + "%, celkem: " + totalExperience + " XP)");
        player.sendMessage("");
        player.sendMessage("§eExp do level up: §f" + expForLevelUp + " XP");
        player.sendMessage("");
        player.sendMessage("§3\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
    }
}
