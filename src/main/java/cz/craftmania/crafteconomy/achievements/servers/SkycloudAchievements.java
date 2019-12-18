package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.objects.AchievementReward;

import java.util.List;

public class SkycloudAchievements {

    private List<AchievementReward> rewards;

    public SkycloudAchievements(List<AchievementReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {

        // Anvil used
        this.rewards.add(new AchievementReward("skycloud_common_anvilsused_10").setRarity(Rarity.COMMON).setName("Anviled I"));
        this.rewards.add(new AchievementReward("skycloud_rare_anvilsused_30").setRarity(Rarity.RARE).setName("Anviled II"));
        this.rewards.add(new AchievementReward("skycloud_legendary_anvilsused_100").setRarity(Rarity.LEGENDARY).setName("Anviled III"));

        // Beds
        this.rewards.add(new AchievementReward("skycloud_common_beds_1").setRarity(Rarity.COMMON).setName("BedTime I"));
        this.rewards.add(new AchievementReward("skycloud_rare_beds_30").setRarity(Rarity.RARE).setName("BedTime II"));
        this.rewards.add(new AchievementReward("skycloud_epic_beds_100").setRarity(Rarity.EPIC).setName("BedTime III"));

        // Connections
        this.rewards.add(new AchievementReward("skycloud_rare_connections_1").setRarity(Rarity.RARE).setName("Vítej na SkyCloudu!"));
        this.rewards.add(new AchievementReward("skycloud_rare_connections_50").setRarity(Rarity.RARE).setName("Vítej na SkyCloudu II"));
        this.rewards.add(new AchievementReward("skycloud_epic_connections_200").setRarity(Rarity.EPIC).setName("Vítej na SkyCloudu III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_connections_500").setRarity(Rarity.LEGENDARY).setName("Vítej na SkyCloudu IV"));

        // Deaths
        this.rewards.add(new AchievementReward("skycloud_common_deaths_1").setRarity(Rarity.COMMON).setName("Deaths I"));
        this.rewards.add(new AchievementReward("skycloud_epic_deaths_100").setRarity(Rarity.EPIC).setName("Deaths II"));

        // Level
        this.rewards.add(new AchievementReward("skycloud_common_maxlevel_10").setRarity(Rarity.COMMON).setName("Level I"));
        this.rewards.add(new AchievementReward("skycloud_rare_maxlevel_20").setRarity(Rarity.RARE).setName("Level II"));
        this.rewards.add(new AchievementReward("skycloud_epic_maxlevel_40").setRarity(Rarity.EPIC).setName("Level III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_maxlevel_75").setRarity(Rarity.LEGENDARY).setName("Level IV"));
        this.rewards.add(new AchievementReward("skycloud_mythic_maxlevel_120").setRarity(Rarity.MYTHIC).setName("Level V"));

        // Milk
        this.rewards.add(new AchievementReward("skycloud_common_milk_10").setRarity(Rarity.COMMON).setName("Milka I"));
        this.rewards.add(new AchievementReward("skycloud_rare_milk_50").setRarity(Rarity.RARE).setName("Milka II"));

        // Places: Seeds
        this.rewards.add(new AchievementReward("skycloud_common_places_beetroot_seeds_50").setRarity(Rarity.COMMON).setName("Seeder I"));
        this.rewards.add(new AchievementReward("skycloud_common_places_beetroot_seeds_500").setRarity(Rarity.COMMON).setName("Seeder II"));
        this.rewards.add(new AchievementReward("skycloud_rare_places_beetroot_seeds_1000").setRarity(Rarity.RARE).setName("Seeder III"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_beetroot_seeds_2000").setRarity(Rarity.EPIC).setName("Seeder IV"));
        this.rewards.add(new AchievementReward("skycloud_legendary_places_beetroot_seeds_5000").setRarity(Rarity.LEGENDARY).setName("Seeder V"));

        // Places: Sapling
        this.rewards.add(new AchievementReward("skycloud_common_places_birch_sapling_50").setRarity(Rarity.COMMON).setName("Sampler I"));
        this.rewards.add(new AchievementReward("skycloud_rare_places_birch_sapling_200").setRarity(Rarity.RARE).setName("Sampler II"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_birch_sapling_500").setRarity(Rarity.EPIC).setName("Sampler III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_places_birch_sapling_1000").setRarity(Rarity.LEGENDARY).setName("Sampler IV"));
        this.rewards.add(new AchievementReward("skycloud_mythic_places_birch_sapling_3000").setRarity(Rarity.MYTHIC).setName("Sampler V"));

        // Places: Cobblestone
        this.rewards.add(new AchievementReward("skycloud_common_places_cobblestone_50").setRarity(Rarity.COMMON).setName("StoneEra I"));
        this.rewards.add(new AchievementReward("skycloud_common_places_cobblestone_200").setRarity(Rarity.COMMON).setName("StoneEra II"));
        this.rewards.add(new AchievementReward("skycloud_rare_places_cobblestone_750").setRarity(Rarity.RARE).setName("StoneEra III"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_cobblestone_1500").setRarity(Rarity.EPIC).setName("StoneEra IV"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_cobblestone_5000").setRarity(Rarity.EPIC).setName("StoneEra V"));
        this.rewards.add(new AchievementReward("skycloud_legendary_places_cobblestone_10000").setRarity(Rarity.LEGENDARY).setName("StoneEra VI"));
        this.rewards.add(new AchievementReward("skycloud_legendary_places_cobblestone_20000").setRarity(Rarity.LEGENDARY).setName("StoneEra VII"));

        // Places: Dirt
        this.rewards.add(new AchievementReward("skycloud_common_places_dirt_10").setRarity(Rarity.COMMON).setName("Dirtter I"));
        this.rewards.add(new AchievementReward("skycloud_common_places_dirt_50").setRarity(Rarity.COMMON).setName("Dirtter II"));
        this.rewards.add(new AchievementReward("skycloud_rare_places_dirt_200").setRarity(Rarity.RARE).setName("Dirtter III"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_dirt_500").setRarity(Rarity.EPIC).setName("Dirtter IV"));
        this.rewards.add(new AchievementReward("skycloud_epic_places_dirt_1000").setRarity(Rarity.EPIC).setName("Dirtter V"));
        this.rewards.add(new AchievementReward("skycloud_legendary_places_dirt_2000").setRarity(Rarity.LEGENDARY).setName("Dirtter VI"));

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

        // Trades
        this.rewards.add(new AchievementReward("skycloud_common_trades_1").setRarity(Rarity.COMMON).setName("Trader I"));
        this.rewards.add(new AchievementReward("skycloud_rare_trades_30").setRarity(Rarity.RARE).setName("Trader II"));
        this.rewards.add(new AchievementReward("skycloud_epic_trades_100").setRarity(Rarity.EPIC).setName("Trader III"));
        this.rewards.add(new AchievementReward("skycloud_legendary_trades_300").setRarity(Rarity.LEGENDARY).setName("Trader IV"));
        this.rewards.add(new AchievementReward("skycloud_mythic_trades_700").setRarity(Rarity.MYTHIC).setName("Trader V"));

        // Treasures
        this.rewards.add(new AchievementReward("skycloud_rare_treasures_10").setRarity(Rarity.RARE).setName("Treasures I"));
        this.rewards.add(new AchievementReward("skycloud_epic_treasures_30").setRarity(Rarity.EPIC).setName("Treasures II"));

        //this.rewards.add(new AchievementReward(""));
    }
}
