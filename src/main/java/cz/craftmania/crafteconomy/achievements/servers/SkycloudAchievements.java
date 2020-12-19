package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.objects.QuestReward;

import java.util.List;

public class SkycloudAchievements {

    private List<QuestReward> rewards;

    public SkycloudAchievements(List<QuestReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {
/*
        // Anvil used
        this.rewards.add(new AchievementReward("skycloud_common_anvilsused_10").setRarity(Rarity.COMMON).setName("Anviled I"));
        this.rewards.add(new AchievementReward("skycloud_rare_anvilsused_30").setRarity(Rarity.RARE).setName("Anviled II"));
        this.rewards.add(new AchievementReward("skycloud_legendary_anvilsused_50").setRarity(Rarity.LEGENDARY).setName("Anviled III"));

        // Arrows
        this.rewards.add(new AchievementReward("skycloud_common_arrows_10").setRarity(Rarity.COMMON).setName("Shooter I"));
        this.rewards.add(new AchievementReward("skycloud_rare_arrows_75").setRarity(Rarity.RARE).setName("Shooter II"));
        this.rewards.add(new AchievementReward("skycloud_epic_arrows_150").setRarity(Rarity.EPIC).setName("Shooter III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_arrows_300").setRarity(Rarity.LEGENDARY).setName("Shooter IV"));

        // Beds
        this.rewards.add(new AchievementReward("skycloud_common_beds_1").setRarity(Rarity.COMMON).setName("Sleeper I"));
        this.rewards.add(new AchievementReward("skycloud_rare_beds_30").setRarity(Rarity.RARE).setName("Sleeper II"));
        this.rewards.add(new AchievementReward("skycloud_epic_beds_100").setRarity(Rarity.EPIC).setName("Sleeper III"));

        // Connections
        this.rewards.add(new AchievementReward("skycloud_rare_connections_1").setRarity(Rarity.RARE).setName("Vítej na SkyCloudu!"));
        this.rewards.add(new AchievementReward("skycloud_rare_connections_50").setRarity(Rarity.RARE).setName("Vítej na SkyCloudu II"));
        this.rewards.add(new AchievementReward("skycloud_epic_connections_200").setRarity(Rarity.EPIC).setName("Vítej na SkyCloudu III").overrideExperienceValue(10000));
        this.rewards.add(new AchievementReward("skycloud_legendary_connections_500").setRarity(Rarity.LEGENDARY).setName("Vítej na SkyCloudu IV").overrideExperienceValue(30000));

        // Deaths
        this.rewards.add(new AchievementReward("skycloud_common_deaths_1").setRarity(Rarity.COMMON).setName("Deaths I"));
        this.rewards.add(new AchievementReward("skycloud_epic_deaths_100").setRarity(Rarity.EPIC).setName("Deaths II"));

        // Eat
        this.rewards.add(new AchievementReward("skycloud_common_eatenitems_1").setRarity(Rarity.COMMON).setName("EatThis I"));
        this.rewards.add(new AchievementReward("skycloud_rare_eatenitems_50").setRarity(Rarity.RARE).setName("EatThis II"));
        this.rewards.add(new AchievementReward("skycloud_epic_eatenitems_100").setRarity(Rarity.EPIC).setName("EatThis III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_eatenitems_200").setRarity(Rarity.LEGENDARY).setName("EatThis IV"));

        // Enchantments
        this.rewards.add(new AchievementReward("skycloud_common_enchantments_1").setRarity(Rarity.COMMON).setName("Enchanting I"));
        this.rewards.add(new AchievementReward("skycloud_rare_enchantments_20").setRarity(Rarity.RARE).setName("Enchanting II"));
        this.rewards.add(new AchievementReward("skycloud_epic_enchantments_75").setRarity(Rarity.EPIC).setName("Enchanting III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_enchantments_150").setRarity(Rarity.LEGENDARY).setName("Enchanting IV"));

        // Kills
        this.rewards.add(new AchievementReward("skycloud_rare_kills_witch_10").setRarity(Rarity.RARE).setName("Hunter I"));
        this.rewards.add(new AchievementReward("skycloud_mythic_kills_wither_1").setRarity(Rarity.MYTHIC).setName("Hunter II"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_squid_10").setRarity(Rarity.RARE).setName("Hunter III"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_blaze_25").setRarity(Rarity.RARE).setName("Hunter IV"));
        this.rewards.add(new AchievementReward("skycloud_epic_kills_drowned_10").setRarity(Rarity.EPIC).setName("Hunter V"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_endermite_10").setRarity(Rarity.RARE).setName("Hunter VI"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_magma_cube_10").setRarity(Rarity.RARE).setName("Hunter VII"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_pig_50").setRarity(Rarity.RARE).setName("Hunter VIII"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_cow_50").setRarity(Rarity.RARE).setName("Hunter IX"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_sheep_25").setRarity(Rarity.RARE).setName("Hunter X"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_shulker_10").setRarity(Rarity.RARE).setName("Hunter XI"));
        this.rewards.add(new AchievementReward("skycloud_rare_kills_slime_10").setRarity(Rarity.RARE).setName("Hunter XII"));
        this.rewards.add(new AchievementReward("skycloud_epic_kills_rabbit_30").setRarity(Rarity.EPIC).setName("Hunter XIII"));
        this.rewards.add(new AchievementReward("skycloud_epic_kills_creeper_10").setRarity(Rarity.EPIC).setName("Hunter XIV"));
        this.rewards.add(new AchievementReward("skycloud_legendary_kills_creeper_50").setRarity(Rarity.LEGENDARY).setName("Hunter XV"));
        this.rewards.add(new AchievementReward("skycloud_epic_kills_skeleton_10").setRarity(Rarity.EPIC).setName("Hunter XVI"));
        this.rewards.add(new AchievementReward("skycloud_legendary_kills_skeleton_50").setRarity(Rarity.LEGENDARY).setName("Hunter XVII"));
        this.rewards.add(new AchievementReward("skycloud_epic_kills_spider_10").setRarity(Rarity.EPIC).setName("Hunter XIX"));
        this.rewards.add(new AchievementReward("skycloud_legendary_kills_spider_50").setRarity(Rarity.LEGENDARY).setName("Hunter XX"));
        this.rewards.add(new AchievementReward("skycloud_epic_kills_zombie_10").setRarity(Rarity.EPIC).setName("Hunter XXI"));
        this.rewards.add(new AchievementReward("skycloud_legendary_kills_zombie_50").setRarity(Rarity.LEGENDARY).setName("Hunter XXII"));

        // LavaBuckets
        this.rewards.add(new AchievementReward("skycloud_common_lavabuckets_10").setRarity(Rarity.COMMON).setName("Pyroman I"));
        this.rewards.add(new AchievementReward("skycloud_rare_lavabuckets_50").setRarity(Rarity.RARE).setName("Pyroman II"));
        this.rewards.add(new AchievementReward("skycloud_epic_lavabuckets_100").setRarity(Rarity.EPIC).setName("Pyroman III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_lavabuckets_200").setRarity(Rarity.LEGENDARY).setName("Pyroman IV"));

        // Level
        this.rewards.add(new AchievementReward("skycloud_common_maxlevel_10").setRarity(Rarity.COMMON).setName("Level I"));
        this.rewards.add(new AchievementReward("skycloud_rare_maxlevel_20").setRarity(Rarity.RARE).setName("Level II"));
        this.rewards.add(new AchievementReward("skycloud_epic_maxlevel_40").setRarity(Rarity.EPIC).setName("Level III").overrideExperienceValue(10000));
        this.rewards.add(new AchievementReward("skycloud_legendary_maxlevel_75").setRarity(Rarity.LEGENDARY).setName("Level IV").overrideExperienceValue(20000));
        this.rewards.add(new AchievementReward("skycloud_mythic_maxlevel_120").setRarity(Rarity.MYTHIC).setName("Level V").overrideExperienceValue(30000));

        // Milk
        this.rewards.add(new AchievementReward("skycloud_common_milk_1").setRarity(Rarity.COMMON).setName("Cows I"));
        this.rewards.add(new AchievementReward("skycloud_rare_milk_50").setRarity(Rarity.RARE).setName("Cows II"));
        this.rewards.add(new AchievementReward("skycloud_epic_milk_100").setRarity(Rarity.EPIC).setName("Cows III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_milk_200").setRarity(Rarity.LEGENDARY).setName("Cows IV"));

        // Places
        this.rewards.add(new AchievementReward("skycloud_common_places_cobblestone_1000").setRarity(Rarity.COMMON).setName("Builder I"));
        this.rewards.add(new AchievementReward("skycloud_rare_places_cobblestone_5000").setRarity(Rarity.RARE).setName("Builder II"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_cobblestone_10000").setRarity(Rarity.EPIC).setName("Builder III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_places_cobblestone_20000").setRarity(Rarity.LEGENDARY).setName("Builder IV"));

        // PlayedTime
        this.rewards.add(new AchievementReward("skycloud_common_playedtime_1").setRarity(Rarity.COMMON).setName("První hodina"));
        this.rewards.add(new AchievementReward("skycloud_rare_playedtime_12").setRarity(Rarity.RARE).setName("Půl dne online"));
        this.rewards.add(new AchievementReward("skycloud_epic_playedtime_24").setRarity(Rarity.EPIC).setName("Celý den online"));
        this.rewards.add(new AchievementReward("skycloud_legendary_playedtime_168").setRarity(Rarity.LEGENDARY).setName("Týden online"));
        this.rewards.add(new AchievementReward("skycloud_mythic_playedtime_720").setRarity(Rarity.MYTHIC).setName("Měsíc online"));

        // Shear
        this.rewards.add(new AchievementReward("skycloud_common_shear_10").setRarity(Rarity.COMMON).setName("Shear I"));
        this.rewards.add(new AchievementReward("skycloud_rare_shear_50").setRarity(Rarity.RARE).setName("Shear II"));
        this.rewards.add(new AchievementReward("skycloud_epic_shear_100").setRarity(Rarity.EPIC).setName("Shear III"));

        // Smelting
        this.rewards.add(new AchievementReward("skycloud_common_smelting_50").setRarity(Rarity.COMMON).setName("Smelter I"));
        this.rewards.add(new AchievementReward("skycloud_rare_smelting_100").setRarity(Rarity.RARE).setName("Smelter II"));
        this.rewards.add(new AchievementReward("skycloud_epic_smelting_200").setRarity(Rarity.RARE).setName("Smelter III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_smelting_300").setRarity(Rarity.RARE).setName("Smelter IV"));

        // Snowballs
        this.rewards.add(new AchievementReward("skycloud_common_snowballs_100").setRarity(Rarity.EPIC).setName("Milovník koulí"));

        // Tamming
        this.rewards.add(new AchievementReward("skycloud_common_taming_1").setRarity(Rarity.COMMON).setName("Tammer I"));
        this.rewards.add(new AchievementReward("skycloud_rare_taming_10").setRarity(Rarity.RARE).setName("Tammer II"));
        this.rewards.add(new AchievementReward("skycloud_epic_taming_25").setRarity(Rarity.EPIC).setName("Tammer III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_taming_50").setRarity(Rarity.LEGENDARY).setName("Tammer IV"));

        // Trades
        this.rewards.add(new AchievementReward("skycloud_common_trades_1").setRarity(Rarity.COMMON).setName("Trader I"));
        this.rewards.add(new AchievementReward("skycloud_rare_trades_30").setRarity(Rarity.RARE).setName("Trader II"));
        this.rewards.add(new AchievementReward("skycloud_epic_trades_75").setRarity(Rarity.EPIC).setName("Trader III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_trades_150").setRarity(Rarity.LEGENDARY).setName("Trader IV"));
        this.rewards.add(new AchievementReward("skycloud_mythic_trades_300").setRarity(Rarity.MYTHIC).setName("Trader V"));

        // Treasures
        this.rewards.add(new AchievementReward("skycloud_rare_treasures_10").setRarity(Rarity.RARE).setName("Treasures I"));
        this.rewards.add(new AchievementReward("skycloud_epic_treasures_30").setRarity(Rarity.EPIC).setName("Treasures II"));

        // Water
        this.rewards.add(new AchievementReward("skycloud_common_waterbuckets_10").setRarity(Rarity.COMMON).setName("Waterman I"));
        this.rewards.add(new AchievementReward("skycloud_rare_waterbuckets_50").setRarity(Rarity.RARE).setName("Waterman II"));
        this.rewards.add(new AchievementReward("skycloud_epic_waterbuckets_100").setRarity(Rarity.EPIC).setName("Waterman III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_waterbuckets_200").setRarity(Rarity.LEGENDARY).setName("Waterman IV"));

        //this.rewards.add(new AchievementReward(""));*/
    }
}
