package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.objects.QuestReward;

import java.util.List;

public class CreativeAchievements {

    private List<QuestReward> rewards;

    public CreativeAchievements(List<QuestReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {
/*
        // Breaks
        this.rewards.add(new AchievementReward("creative_common_breaks_bedrock_1").setRarity(Rarity.COMMON).setName("Bedrocker"));
        this.rewards.add(new AchievementReward("creative_rare_breaks_grass_block_500").setRarity(Rarity.RARE).setName("Survival"));
        this.rewards.add(new AchievementReward("creative_epic_breaks_grass_block_5000").setRarity(Rarity.EPIC).setName("Survival II"));

        // Connections
        this.rewards.add(new AchievementReward("creative_rare_connections_1").setRarity(Rarity.RARE).overrideExperienceValue(1000).setName("Vítej na Creativu!"));
        this.rewards.add(new AchievementReward("creative_rare_connections_50").setRarity(Rarity.RARE).overrideExperienceValue(2000).setName("Vítej na Creativu! II"));
        this.rewards.add(new AchievementReward("creative_legendary_connections_500").setRarity(Rarity.LEGENDARY).overrideExperienceValue(10000).setName("Vítej na Creativu! III"));

        // Anvils Used
        this.rewards.add(new AchievementReward("creative_common_anvilsused_10").setRarity(Rarity.COMMON).setName("Anviled I"));
        this.rewards.add(new AchievementReward("creative_rare_anvilsused_30").setRarity(Rarity.RARE).setName("Anviled II"));
        this.rewards.add(new AchievementReward("creative_legendary_anvilsused_100").setRarity(Rarity.LEGENDARY).setName("Anviled III"));

        // Crafts
        this.rewards.add(new AchievementReward("creative_legendary_crafts_cake_100").setRarity(Rarity.LEGENDARY).setName("Baker"));
        this.rewards.add(new AchievementReward("creative_rare_crafts_diamond_sword_1").setRarity(Rarity.RARE).setName("DiamondCrafts I"));

        // Deaths
        this.rewards.add(new AchievementReward("creative_common_deaths_1").setRarity(Rarity.COMMON).setName("Undead I"));
        this.rewards.add(new AchievementReward("creative_epic_deaths_100").setRarity(Rarity.EPIC).setName("Undead II"));

        // DistanceHorse
        this.rewards.add(new AchievementReward("creative_epic_distancehorse_10000").setRarity(Rarity.EPIC).setName("Horse Distance"));

        // DistancePig
        this.rewards.add(new AchievementReward("creative_epic_distancepig_1000").setRarity(Rarity.EPIC).setName("Pig Distance"));

        // Fireworks
        this.rewards.add(new AchievementReward("creative_common_fireworks_50").setRarity(Rarity.COMMON).setName("Pyrotechnic"));

        // MusicDics
        this.rewards.add(new AchievementReward("creative_common_musicdiscs_1").setRarity(Rarity.COMMON).setName("Jukeboxer"));

        // Place blocks
        this.rewards.add(new AchievementReward("creative_rare_places_acacia_door_100").setRarity(Rarity.RARE).setName("Door Breaker"));
        this.rewards.add(new AchievementReward("creative_epic_places_acacia_door_500").setRarity(Rarity.EPIC).setName("Door Breaker II"));
        this.rewards.add(new AchievementReward("creative_common_places_acacia_leaves_50").setRarity(Rarity.COMMON).setName("Nature I"));
        this.rewards.add(new AchievementReward("creative_rare_places_acacia_leaves_200").setRarity(Rarity.RARE).setName("Nature II"));
        this.rewards.add(new AchievementReward("creative_rare_places_acacia_leaves_400").setRarity(Rarity.RARE).setName("Nature III"));
        this.rewards.add(new AchievementReward("creative_epic_places_acacia_leaves_800").setRarity(Rarity.EPIC).setName("Nature IV"));
        this.rewards.add(new AchievementReward("creative_epic_places_acacia_leaves_1500").setRarity(Rarity.EPIC).setName("Nature V"));
        this.rewards.add(new AchievementReward("creative_legendary_places_acacia_leaves_3000").setRarity(Rarity.LEGENDARY).setName("Nature VI"));
        this.rewards.add(new AchievementReward("creative_common_places_acacia_log_100").setRarity(Rarity.COMMON).setName("Woodcutter I"));
        this.rewards.add(new AchievementReward("creative_common_places_acacia_log_500").setRarity(Rarity.COMMON).setName("Woodcutter II"));
        this.rewards.add(new AchievementReward("creative_rare_places_acacia_log_2000").setRarity(Rarity.RARE).setName("Woodcutter III"));
        this.rewards.add(new AchievementReward("creative_epic_places_acacia_log_5000").setRarity(Rarity.EPIC).setName("Woodcutter IV"));
        this.rewards.add(new AchievementReward("creative_legendary_places_acacia_log_10000").setRarity(Rarity.LEGENDARY).setName("Woodcutter V"));
        this.rewards.add(new AchievementReward("creative_common_places_acacia_planks_100").setRarity(Rarity.COMMON).setName("Woodmaster I"));
        this.rewards.add(new AchievementReward("creative_common_places_acacia_planks_500").setRarity(Rarity.COMMON).setName("Woodmaster II"));
        this.rewards.add(new AchievementReward("creative_rare_places_acacia_planks_2000").setRarity(Rarity.RARE).setName("Woodmaster III"));
        this.rewards.add(new AchievementReward("creative_rare_places_acacia_planks_6000").setRarity(Rarity.RARE).setName("Woodmaster IV"));
        this.rewards.add(new AchievementReward("creative_epic_places_acacia_planks_12000").setRarity(Rarity.EPIC).setName("Woodmaster V"));
        this.rewards.add(new AchievementReward("creative_legendary_places_acacia_planks_25000").setRarity(Rarity.LEGENDARY).setName("Woodmaster VI"));
        this.rewards.add(new AchievementReward("creative_rare_places_armor_stand_200").setRarity(Rarity.RARE).setName("ArmorStand I"));
        this.rewards.add(new AchievementReward("creative_epic_places_armor_stand_1000").setRarity(Rarity.EPIC).setName("ArmorStand II"));
        this.rewards.add(new AchievementReward("creative_rare_places_beacon_1").setRarity(Rarity.RARE).setName("Beacon"));
        this.rewards.add(new AchievementReward("creative_common_places_bookshelf_50").setRarity(Rarity.COMMON).setName("Librarian I"));
        this.rewards.add(new AchievementReward("creative_rare_places_bookshelf_500").setRarity(Rarity.RARE).setName("Librarian II"));
        this.rewards.add(new AchievementReward("creative_common_places_crafting_table_1").setRarity(Rarity.COMMON).setName("Crafter"));
        this.rewards.add(new AchievementReward("creative_common_places_flower_pot_30").setRarity(Rarity.COMMON).setName("Flowers I"));
        this.rewards.add(new AchievementReward("creative_rare_places_flower_pot_100").setRarity(Rarity.RARE).setName("Flowers II"));
        this.rewards.add(new AchievementReward("creative_rare_places_flower_pot_200").setRarity(Rarity.RARE).setName("Flowers III"));
        this.rewards.add(new AchievementReward("creative_common_places_grass_block_50").setRarity(Rarity.COMMON).setName("Grass Master"));
        this.rewards.add(new AchievementReward("creative_common_places_grass_block_500").setRarity(Rarity.COMMON).setName("Grass Master II"));
        this.rewards.add(new AchievementReward("creative_rare_places_grass_block_2000").setRarity(Rarity.RARE).setName("Grass Master III"));
        this.rewards.add(new AchievementReward("creative_epic_places_grass_block_7500").setRarity(Rarity.EPIC).setName("Grass Master IV"));
        this.rewards.add(new AchievementReward("creative_rare_places_note_block_1").setRarity(Rarity.RARE).setName("NoteBlocker"));
        this.rewards.add(new AchievementReward("creative_common_places_player_head_5").setRarity(Rarity.COMMON).setName("Head I"));
        this.rewards.add(new AchievementReward("creative_rare_places_player_head_50").setRarity(Rarity.RARE).setName("Head II"));
        this.rewards.add(new AchievementReward("creative_rare_places_player_head_200").setRarity(Rarity.RARE).setName("Head III"));
        this.rewards.add(new AchievementReward("creative_epic_places_player_head_500").setRarity(Rarity.EPIC).setName("Head IV"));
        this.rewards.add(new AchievementReward("creative_legendary_places_player_head_1000").setRarity(Rarity.LEGENDARY).setName("Head V"));
        this.rewards.add(new AchievementReward("creative_common_places_sand_50").setRarity(Rarity.COMMON).setName("Sandstorm I"));
        this.rewards.add(new AchievementReward("creative_rare_places_sand_300").setRarity(Rarity.RARE).setName("Snadstorm II"));
        this.rewards.add(new AchievementReward("creative_epic_places_sand_1000").setRarity(Rarity.EPIC).setName("Sandstorm III"));
        this.rewards.add(new AchievementReward("creative_legendary_places_sand_4000").setRarity(Rarity.LEGENDARY).setName("Sandstorm IV"));
        this.rewards.add(new AchievementReward("creative_mythic_places_sand_10000").setRarity(Rarity.MYTHIC).setName("Sandstorm V"));
        this.rewards.add(new AchievementReward("creative_rare_places_scaffolding_500").setRarity(Rarity.RARE).setName("Scaffolder"));
        this.rewards.add(new AchievementReward("creative_common_places_snow_block_100").setRarity(Rarity.COMMON).setName("Icestorm I"));
        this.rewards.add(new AchievementReward("creative_rare_places_snow_block_300").setRarity(Rarity.RARE).setName("Icestorm II"));
        this.rewards.add(new AchievementReward("creative_rare_places_snow_block_600").setRarity(Rarity.RARE).setName("Icestorm III"));
        this.rewards.add(new AchievementReward("creative_epic_places_snow_block_1500").setRarity(Rarity.EPIC).setName("Icestorm IV"));
        this.rewards.add(new AchievementReward("creative_legendary_places_snow_block_3000").setRarity(Rarity.LEGENDARY).setName("Icestorm V"));
        this.rewards.add(new AchievementReward("creative_common_places_stone_50").setRarity(Rarity.COMMON).setName("Place Stone"));
        this.rewards.add(new AchievementReward("creative_common_places_stone_500").setRarity(Rarity.COMMON).setName("Více Stonuuu"));
        this.rewards.add(new AchievementReward("creative_rare_places_stone_5000").setRarity(Rarity.RARE).setName("Stone Master"));
        this.rewards.add(new AchievementReward("creative_rare_places_tnt_1").setRarity(Rarity.RARE).setName("Silent Explosion"));

        // Played time
        this.rewards.add(new AchievementReward("creative_common_playedtime_1").setRarity(Rarity.COMMON).setName("První hodina"));
        this.rewards.add(new AchievementReward("creative_rare_playedtime_12").setRarity(Rarity.RARE).setName("Půl dne online"));
        this.rewards.add(new AchievementReward("creative_epic_playedtime_24").setRarity(Rarity.EPIC).setName("Celý den online"));
        this.rewards.add(new AchievementReward("creative_legendary_playedtime_168").setRarity(Rarity.LEGENDARY).setName("Týden online"));
        this.rewards.add(new AchievementReward("creative_legendary_playedtime_720").setRarity(Rarity.LEGENDARY).setName("Měsíc online"));

        // Commands
        this.rewards.add(new AchievementReward("creative_common_playercommands_aach_list_10").setRarity(Rarity.COMMON).setName("Commander I"));
        this.rewards.add(new AchievementReward("creative_common_playercommands_aach_stats_10").setRarity(Rarity.COMMON).setName("Commander II"));
        this.rewards.add(new AchievementReward("creative_common_playercommands_rewards_1").setRarity(Rarity.COMMON).setName("Commander III"));
        this.rewards.add(new AchievementReward("creative_rare_playercommands_home_100").setRarity(Rarity.RARE).setName("Commander IV"));
        this.rewards.add(new AchievementReward("creative_rare_playercommands_spawn_100").setRarity(Rarity.RARE).setName("Commander V"));
        this.rewards.add(new AchievementReward("creative_rare_playercommands_plot_100").setRarity(Rarity.RARE).setName("Commander VI"));

        // Snowballs
        this.rewards.add(new AchievementReward("creative_common_snowballs_50").setRarity(Rarity.COMMON).setName("Snowballs I"));
        this.rewards.add(new AchievementReward("creative_common_snowballs_300").setRarity(Rarity.COMMON).setName("Snowballs II"));
        this.rewards.add(new AchievementReward("creative_rare_snowballs_1000").setRarity(Rarity.RARE).setName("Snowballs III"));

        // TargetShots
        this.rewards.add(new AchievementReward("creative_epic_targetsshot_glass_100").setRarity(Rarity.EPIC).setName("GlassShooter"));
        this.rewards.add(new AchievementReward("creative_epic_targetsshot_player_100").setRarity(Rarity.EPIC).setName("PlayerShooter"));

        // Treasures
        this.rewards.add(new AchievementReward("creative_rare_treasures_10").setRarity(Rarity.RARE).setName("Treasures I"));
        this.rewards.add(new AchievementReward("creative_epic_treasures_30").setRarity(Rarity.EPIC).setName("Treasures II"));*/
    }
}
