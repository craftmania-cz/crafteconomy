package cz.craftmania.crafteconomy.utils.hooks;

import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.FormatUtils;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderRegistry extends PlaceholderExpansion {

    private final BasicManager basicManager = new BasicManager();

    public boolean canRegister() {
        return true;
    }

    public @NotNull String getAuthor(){
        return "CraftMania.cz";
    }

    public @NotNull String getRequiredPlugin() {
        return "CraftEconomy";
    }

    public @NotNull String getIdentifier(){
        return "crafteconomy"; // %<identifier>_<value>%
    }

    public @NotNull String getVersion(){
        return "1.0";
    }

    public String onPlaceholderRequest(Player player, @NotNull String identifier){
        if (player == null) {
            return "";
        }

        CraftPlayer craftPlayer = basicManager.getCraftPlayer(player);

        if (craftPlayer == null) {
            return "";
        }

        // %craftceconomy_player_global_level%
        if(identifier.equals("player_global_level")){
            return String.valueOf(craftPlayer.getLevelByType(LevelType.GLOBAL_LEVEL));
        }

        // %crafteconomy_player_craftcoins%
        if (identifier.equals("player_craftcoins")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.CRAFT_COINS));
        }

        // %crafteconomy_player_crafttokens%
        if (identifier.equals("player_crafttokens")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.CRAFT_TOKENS));
        }

        // %crafteconomy_player_votetokens%
        if (identifier.equals("player_votetokens")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.VOTE_TOKENS_2));
        }

        // %crafteconomy_player_karma%
        if (identifier.equals("player_karma")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.KARMA_POINTS));
        }

        // %crafteconomy_player_quest_points%
        if (identifier.equals("player_quest_points")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.QUEST_POINTS));
        }

        // %crafteconomy_player_event_points%
        if (identifier.equals("player_event_points")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.EVENT_POINTS));
        }

        // %crafteconomy_player_season_points%
        if (identifier.equals("player_season_points")) {
            return String.valueOf(craftPlayer.getEconomyByType(EconomyType.SEASON_POINTS));
        }

        // %crafteconomy_player_votepass_votes%
        if (identifier.equals("player_votepass_votes")) {
            return String.valueOf(craftPlayer.getVotePassVotes());
        }

        // %crafteconomy_player_survival_level%
        if (identifier.equals("player_survival_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.SURVIVAL_117_LEVEL));
        }

        // %crafteconomy_player_survival_118_level%
        if (identifier.equals("player_survival_118_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.SURVIVAL_118_LEVEL));
        }

        // %crafteconomy_player_skyblock_118_level%
        if (identifier.equals("player_skyblock_118_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.SKYBLOCK_118_LEVEL));
        }

        // %crafteconomy_player_survival_level_percentage_next%
        if (identifier.equals("player_survival_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.SURVIVAL_117_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.SURVIVAL_117_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_survival_118_level_percentage_next%
        if (identifier.equals("player_survival_118_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.SURVIVAL_118_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.SURVIVAL_118_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_skyblock_118_level_percentage_next%
        if (identifier.equals("player_skyblock_118_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.SKYBLOCK_118_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.SKYBLOCK_118_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_skyblock_level%
        if (identifier.equals("player_skyblock_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.SKYBLOCK_117_LEVEL));
        }

        // %crafteconomy_player_skyblock_level_percentage_next%
        if (identifier.equals("player_skyblock_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.SKYBLOCK_117_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.SKYBLOCK_117_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_creative_level%
        if (identifier.equals("player_creative_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.CREATIVE_LEVEL));
        }

        // %crafteconomy_player_creative_level_percentage_next%
        if (identifier.equals("player_creative_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.CREATIVE_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.CREATIVE_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_vanilla_level%
        if (identifier.equals("player_vanilla_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.VANILLA_LEVEL));
        }

        // %crafteconomy_player_vanilla_level_percentage_next%
        if (identifier.equals("player_vanilla_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.VANILLA_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.VANILLA_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_skycloud_level%
        if (identifier.equals("player_skycloud_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.SKYCLOUD_LEVEL));
        }

        // %crafteconomy_player_skycloud_level_percentage_next%
        if (identifier.equals("player_skycloud_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.SKYCLOUD_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.SKYCLOUD_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_prison_level%
        if (identifier.equals("player_prison_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.PRISON_LEVEL));
        }

        // %crafteconomy_player_prison_level_percentage_next%
        if (identifier.equals("player_prison_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.PRISON_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.PRISON_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_hardcore_vanilla_level%
        if (identifier.equals("player_hardcore_vanilla_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.HARDCORE_VANILLA_LEVEL));
        }

        // %crafteconomy_player_anarchy_level%
        if (identifier.equals("player_anarchy_level")) {
            return String.valueOf(craftPlayer.getLevelByType(LevelType.ANARCHY_LEVEL));
        }

        // %crafteconomy_player_anarchy_level_percentage_next%
        if (identifier.equals("player_anarchy_level_percentage_next")) {
            long totalExperience = craftPlayer.getExperienceByType(LevelType.ANARCHY_EXPERIENCE);
            double totalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.ANARCHY_LEVEL));
            return FormatUtils.roundDouble((totalExperience/totalExperienceForNextLevel)*100, 1) + "%";
        }

        // %crafteconomy_player_global_experience%
        if (identifier.equals("player_global_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.GLOBAL_EXPERIENCE));
        }

        // %crafteconomy_player_survival_experience%
        if (identifier.equals("player_survival_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.SURVIVAL_117_EXPERIENCE));
        }

        // %crafteconomy_player_skyblock_experience%
        if (identifier.equals("player_skyblock_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.SKYBLOCK_117_EXPERIENCE));
        }

        // %crafteconomy_player_survival_118_experience%
        if (identifier.equals("player_survival_118_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.SURVIVAL_118_EXPERIENCE));
        }

        // %crafteconomy_player_skyblock_118_experience%
        if (identifier.equals("player_skyblock_118_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.SKYBLOCK_118_EXPERIENCE));
        }

        // %crafteconomy_player_creative_experience%
        if (identifier.equals("player_creative_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.CREATIVE_EXPERIENCE));
        }

        // %crafteconomy_player_prison_experience%
        if (identifier.equals("player_prison_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.PRISON_EXPERIENCE));
        }

        // %crafteconomy_player_vanilla_experience%
        if (identifier.equals("player_vanilla_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.VANILLA_EXPERIENCE));
        }

        // %crafteconomy_player_skycloud_experience%
        if (identifier.equals("player_skycloud_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.SKYCLOUD_EXPERIENCE));
        }

        // %crafteconomy_player_hardcore_vanilla_experience%
        if (identifier.equals("player_hardcore_vanilla_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.HARDCORE_VANILLA_EXPERIENCE));
        }

        // %crafteconomy_player_anarchy_experience%
        if (identifier.equals("player_anarchy_experience")) {
            return String.valueOf(craftPlayer.getExperienceByType(LevelType.ANARCHY_EXPERIENCE));
        }

        // %crafteconomy_player_week_votes%
        if (identifier.equals("player_week_votes")) {
            return String.valueOf(craftPlayer.getWeekVotes());
        }

        // %crafteconomy_player_month_votes%
        if (identifier.equals("player_month_votes")) {
            return String.valueOf(craftPlayer.getMonthVotes());
        }

        // %crafteconomy_player_total_votes%
        if (identifier.equals("player_total_votes")) {
            return String.valueOf(craftPlayer.getTotalVotes());
        }

        return "";
    }
}
