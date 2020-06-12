package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.objects.AchievementReward;

import java.util.List;

public class SkyblockAchievements {

    private List<AchievementReward> rewards;

    public SkyblockAchievements(List<AchievementReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {

        // Anvil used
        this.rewards.add(new AchievementReward("skyblock_common_anvilsused_10").setRarity(Rarity.COMMON).setName("Anviled I"));
        this.rewards.add(new AchievementReward("skyblock_rare_anvilsused_30").setRarity(Rarity.RARE).setName("Anviled II"));
        this.rewards.add(new AchievementReward("skyblock_legendary_anvilsused_50").setRarity(Rarity.LEGENDARY).setName("Anviled III"));

        // Arrows
        this.rewards.add(new AchievementReward("skyblock_common_arrows_10").setRarity(Rarity.COMMON).setName("Shooter I"));
        this.rewards.add(new AchievementReward("skyblock_rare_arrows_75").setRarity(Rarity.RARE).setName("Shooter II"));
        this.rewards.add(new AchievementReward("skyblock_epic_arrows_150").setRarity(Rarity.EPIC).setName("Shooter III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_arrows_300").setRarity(Rarity.LEGENDARY).setName("Shooter IV"));

        // Beds
        this.rewards.add(new AchievementReward("skyblock_common_beds_1").setRarity(Rarity.COMMON).setName("Sleeper I"));
        this.rewards.add(new AchievementReward("skyblock_rare_beds_30").setRarity(Rarity.RARE).setName("Sleeper II"));
        this.rewards.add(new AchievementReward("skyblock_epic_beds_100").setRarity(Rarity.EPIC).setName("Sleeper III"));

        // Connections
        this.rewards.add(new AchievementReward("skyblock_rare_connections_1").setRarity(Rarity.RARE).setName("Vítej na Skyblocku!"));
        this.rewards.add(new AchievementReward("skyblock_rare_connections_50").setRarity(Rarity.RARE).setName("Vítej na Skyblocku II"));
        this.rewards.add(new AchievementReward("skyblock_epic_connections_200").setRarity(Rarity.EPIC).setName("Vítej na Skyblocku III").overrideExperienceValue(10000));
        this.rewards.add(new AchievementReward("skyblock_legendary_connections_500").setRarity(Rarity.LEGENDARY).setName("Vítej na Skyblocku IV").overrideExperienceValue(30000));

        // Deaths
        this.rewards.add(new AchievementReward("skyblock_common_deaths_1").setRarity(Rarity.COMMON).setName("Deaths I"));
        this.rewards.add(new AchievementReward("skyblock_epic_deaths_100").setRarity(Rarity.EPIC).setName("Deaths II"));

        // Eat
        this.rewards.add(new AchievementReward("skyblock_common_eatenitems_1").setRarity(Rarity.COMMON).setName("EatThis I"));
        this.rewards.add(new AchievementReward("skyblock_rare_eatenitems_50").setRarity(Rarity.RARE).setName("EatThis II"));
        this.rewards.add(new AchievementReward("skyblock_epic_eatenitems_100").setRarity(Rarity.EPIC).setName("EatThis III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_eatenitems_200").setRarity(Rarity.LEGENDARY).setName("EatThis IV"));

        // Enchantments
        this.rewards.add(new AchievementReward("skyblock_common_enchantments_1").setRarity(Rarity.COMMON).setName("Enchanting I"));
        this.rewards.add(new AchievementReward("skyblock_rare_enchantments_20").setRarity(Rarity.RARE).setName("Enchanting II"));
        this.rewards.add(new AchievementReward("skyblock_epic_enchantments_75").setRarity(Rarity.EPIC).setName("Enchanting III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_enchantments_150").setRarity(Rarity.LEGENDARY).setName("Enchanting IV"));

        // Kills
        this.rewards.add(new AchievementReward("skyblock_rare_kills_blaze_50").setRarity(Rarity.RARE).setName("Blaze Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_blaze_200").setRarity(Rarity.EPIC).setName("Blaze Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_cow_25").setRarity(Rarity.RARE).setName("Cow Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_cow_75").setRarity(Rarity.EPIC).setName("Cow Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_cow_150").setRarity(Rarity.LEGENDARY).setName("Cow Hunter III"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_creeper_10").setRarity(Rarity.RARE).setName("Creeper Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_creeper_50").setRarity(Rarity.EPIC).setName("Creeper Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_creeper_200").setRarity(Rarity.LEGENDARY).setName("Creeper Hunter III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_drowned_25").setRarity(Rarity.LEGENDARY).setName("Drowned Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_enderman_15").setRarity(Rarity.LEGENDARY).setName("Enderman Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_mythic_kills_enderman_50").setRarity(Rarity.MYTHIC).setName("Enderman Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_endermite_10").setRarity(Rarity.RARE).setName("Enderminte Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_magma_cube_10").setRarity(Rarity.RARE).setName("Magma Cube Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_pig_25").setRarity(Rarity.RARE).setName("Pig Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_pig_75").setRarity(Rarity.EPIC).setName("Pig Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_pig_150").setRarity(Rarity.LEGENDARY).setName("Pig Hunter III"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_rabbit_30").setRarity(Rarity.EPIC).setName("Rabbit Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_sheep_25").setRarity(Rarity.RARE).setName("Sheep Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_shulker_25").setRarity(Rarity.LEGENDARY).setName("Shulker Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_skeleton_10").setRarity(Rarity.EPIC).setName("Skeleton Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_skeleton_50").setRarity(Rarity.LEGENDARY).setName("Skeleton Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_mythic_kills_skeleton_200").setRarity(Rarity.MYTHIC).setName("Skeleton Hunter III"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_slime_10").setRarity(Rarity.RARE).setName("Slime Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_spider_10").setRarity(Rarity.EPIC).setName("Spider Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_spider_50").setRarity(Rarity.LEGENDARY).setName("Spider Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_mythic_kills_spider_100").setRarity(Rarity.MYTHIC).setName("Spider Hunter III"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_squid_10").setRarity(Rarity.RARE).setName("Squid Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_mythic_kills_wither_1").setRarity(Rarity.MYTHIC).setName("Wither Hunter"));
        this.rewards.add(new AchievementReward("skyblock_rare_kills_witch_10").setRarity(Rarity.RARE).setName("Witch Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_epic_kills_zombie_10").setRarity(Rarity.EPIC).setName("Zombie Hunter I"));
        this.rewards.add(new AchievementReward("skyblock_legendary_kills_zombie_50").setRarity(Rarity.LEGENDARY).setName("Zombie Hunter II"));
        this.rewards.add(new AchievementReward("skyblock_mythic_kills_zombie_100").setRarity(Rarity.MYTHIC).setName("Zombie Hunter III"));

        // LavaBuckets
        this.rewards.add(new AchievementReward("skyblock_common_lavabuckets_10").setRarity(Rarity.COMMON).setName("Pyroman I"));
        this.rewards.add(new AchievementReward("skyblock_rare_lavabuckets_50").setRarity(Rarity.RARE).setName("Pyroman II"));
        this.rewards.add(new AchievementReward("skyblock_epic_lavabuckets_100").setRarity(Rarity.EPIC).setName("Pyroman III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_lavabuckets_200").setRarity(Rarity.LEGENDARY).setName("Pyroman IV"));

        // Level
        this.rewards.add(new AchievementReward("skyblock_common_maxlevel_10").setRarity(Rarity.COMMON).setName("Level I"));
        this.rewards.add(new AchievementReward("skyblock_rare_maxlevel_20").setRarity(Rarity.RARE).setName("Level II"));
        this.rewards.add(new AchievementReward("skyblock_epic_maxlevel_40").setRarity(Rarity.EPIC).setName("Level III").overrideExperienceValue(10000));
        this.rewards.add(new AchievementReward("skyblock_legendary_maxlevel_75").setRarity(Rarity.LEGENDARY).setName("Level IV").overrideExperienceValue(20000));
        this.rewards.add(new AchievementReward("skyblock_mythic_maxlevel_120").setRarity(Rarity.MYTHIC).setName("Level V").overrideExperienceValue(30000));

        // Milk
        this.rewards.add(new AchievementReward("skyblock_common_milk_1").setRarity(Rarity.COMMON).setName("Cows I"));
        this.rewards.add(new AchievementReward("skyblock_rare_milk_50").setRarity(Rarity.RARE).setName("Cows II"));
        this.rewards.add(new AchievementReward("skyblock_epic_milk_100").setRarity(Rarity.EPIC).setName("Cows III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_milk_200").setRarity(Rarity.LEGENDARY).setName("Cows IV"));

        // Places
        this.rewards.add(new AchievementReward("skyblock_common_places_cobblestone_1000").setRarity(Rarity.COMMON).setName("Builder I"));
        this.rewards.add(new AchievementReward("skyblock_rare_places_cobblestone_5000").setRarity(Rarity.RARE).setName("Builder II"));
        this.rewards.add(new AchievementReward("skyblock_epic_places_cobblestone_10000").setRarity(Rarity.EPIC).setName("Builder III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_places_cobblestone_20000").setRarity(Rarity.LEGENDARY).setName("Builder IV"));

        // PlayedTime
        this.rewards.add(new AchievementReward("skyblock_common_playedtime_1").setRarity(Rarity.COMMON).setName("První hodina"));
        this.rewards.add(new AchievementReward("skyblock_rare_playedtime_12").setRarity(Rarity.RARE).setName("Půl dne online"));
        this.rewards.add(new AchievementReward("skyblock_epic_playedtime_24").setRarity(Rarity.EPIC).setName("Celý den online"));
        this.rewards.add(new AchievementReward("skyblock_legendary_playedtime_168").setRarity(Rarity.LEGENDARY).setName("Týden online"));
        this.rewards.add(new AchievementReward("skyblock_mythic_playedtime_720").setRarity(Rarity.MYTHIC).setName("Měsíc online"));

        // Shear
        this.rewards.add(new AchievementReward("skyblock_common_shear_10").setRarity(Rarity.COMMON).setName("Shear I"));
        this.rewards.add(new AchievementReward("skyblock_rare_shear_50").setRarity(Rarity.RARE).setName("Shear II"));
        this.rewards.add(new AchievementReward("skyblock_epic_shear_100").setRarity(Rarity.EPIC).setName("Shear III"));

        // Smelting
        this.rewards.add(new AchievementReward("skyblock_common_smelting_50").setRarity(Rarity.COMMON).setName("Smelter I"));
        this.rewards.add(new AchievementReward("skyblock_rare_smelting_100").setRarity(Rarity.RARE).setName("Smelter II"));
        this.rewards.add(new AchievementReward("skyblock_epic_smelting_200").setRarity(Rarity.RARE).setName("Smelter III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_smelting_300").setRarity(Rarity.RARE).setName("Smelter IV"));

        // Snowballs
        this.rewards.add(new AchievementReward("skyblock_common_snowballs_100").setRarity(Rarity.EPIC).setName("Milovník koulí"));

        // Tamming
        this.rewards.add(new AchievementReward("skyblock_common_taming_1").setRarity(Rarity.COMMON).setName("Tammer I"));
        this.rewards.add(new AchievementReward("skyblock_rare_taming_10").setRarity(Rarity.RARE).setName("Tammer II"));
        this.rewards.add(new AchievementReward("skyblock_epic_taming_25").setRarity(Rarity.EPIC).setName("Tammer III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_taming_50").setRarity(Rarity.LEGENDARY).setName("Tammer IV"));

        // Trades
        this.rewards.add(new AchievementReward("skyblock_common_trades_1").setRarity(Rarity.COMMON).setName("Trader I"));
        this.rewards.add(new AchievementReward("skyblock_rare_trades_30").setRarity(Rarity.RARE).setName("Trader II"));
        this.rewards.add(new AchievementReward("skyblock_epic_trades_75").setRarity(Rarity.EPIC).setName("Trader III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_trades_150").setRarity(Rarity.LEGENDARY).setName("Trader IV"));
        this.rewards.add(new AchievementReward("skyblock_mythic_trades_300").setRarity(Rarity.MYTHIC).setName("Trader V"));

        // Treasures
        this.rewards.add(new AchievementReward("skyblock_rare_treasures_10").setRarity(Rarity.RARE).setName("Treasures I"));
        this.rewards.add(new AchievementReward("skyblock_epic_treasures_30").setRarity(Rarity.EPIC).setName("Treasures II"));

        // Water
        this.rewards.add(new AchievementReward("skyblock_common_waterbuckets_10").setRarity(Rarity.COMMON).setName("Waterman I"));
        this.rewards.add(new AchievementReward("skyblock_rare_waterbuckets_50").setRarity(Rarity.RARE).setName("Waterman II"));
        this.rewards.add(new AchievementReward("skyblock_epic_waterbuckets_100").setRarity(Rarity.EPIC).setName("Waterman III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_waterbuckets_200").setRarity(Rarity.LEGENDARY).setName("Waterman IV"));

        // Brewing
        this.rewards.add(new AchievementReward("skyblock_common_brewing_1").setRarity(Rarity.COMMON).setName("Alchemie I"));
        this.rewards.add(new AchievementReward("skyblock_rare_brewing_50").setRarity(Rarity.RARE).setName("Alchemie II"));
        this.rewards.add(new AchievementReward("skyblock_epic_brewing_100").setRarity(Rarity.EPIC).setName("Alchemie III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_brewing_200").setRarity(Rarity.LEGENDARY).setName("Alchemie IV"));

        // Consumed potions
        this.rewards.add(new AchievementReward("skyblock_common_consumedpotions_1").setRarity(Rarity.COMMON).setName("DrinkIt I"));
        this.rewards.add(new AchievementReward("skyblock_rare_consumedpotions_50").setRarity(Rarity.RARE).setName("DrinkIt II"));
        this.rewards.add(new AchievementReward("skyblock_epic_consumedpotions_100").setRarity(Rarity.EPIC).setName("DrinkIt III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_consumedpotions_200").setRarity(Rarity.LEGENDARY).setName("DrinkIt IV"));

        // Fertilising
        this.rewards.add(new AchievementReward("skyblock_common_fertilising_15").setRarity(Rarity.COMMON).setName("Gardener I"));
        this.rewards.add(new AchievementReward("skyblock_rare_fertilising_100").setRarity(Rarity.RARE).setName("Gardener II"));
        this.rewards.add(new AchievementReward("skyblock_epic_fertilising_200").setRarity(Rarity.EPIC).setName("Gardener III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_fertilising_350").setRarity(Rarity.LEGENDARY).setName("Gardener IV"));

        // Fireworks
        this.rewards.add(new AchievementReward("skyblock_common_fireworks_1").setRarity(Rarity.COMMON).setName("Fireworks I"));
        this.rewards.add(new AchievementReward("skyblock_rare_fireworks_50").setRarity(Rarity.RARE).setName("Fireworks II"));
        this.rewards.add(new AchievementReward("skyblock_epic_fireworks_100").setRarity(Rarity.EPIC).setName("Fireworks III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_fireworks_200").setRarity(Rarity.LEGENDARY).setName("Fireworks IV"));

        // Discs
        this.rewards.add(new AchievementReward("skyblock_common_musicdiscs_1").setRarity(Rarity.COMMON).setName("Disco I"));
        this.rewards.add(new AchievementReward("skyblock_rare_musicdiscs_20").setRarity(Rarity.RARE).setName("Disco II"));

        // Hoe plowings
        this.rewards.add(new AchievementReward("skyblock_common_hoeplowings_10").setRarity(Rarity.COMMON).setName("Fielder I"));
        this.rewards.add(new AchievementReward("skyblock_rare_hoeplowings_50").setRarity(Rarity.RARE).setName("Fielder II"));
        this.rewards.add(new AchievementReward("skyblock_epic_hoeplowings_100").setRarity(Rarity.EPIC).setName("Fielder III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_hoeplowings_200").setRarity(Rarity.LEGENDARY).setName("Fieldder IV"));

        // ItemBreaks
        this.rewards.add(new AchievementReward("skyblock_common_itembreaks_1").setRarity(Rarity.COMMON).setName("Breaker I"));
        this.rewards.add(new AchievementReward("skyblock_rare_itembreaks_10").setRarity(Rarity.RARE).setName("Breaker II"));
        this.rewards.add(new AchievementReward("skyblock_epic_itembreaks_25").setRarity(Rarity.EPIC).setName("Breaker III"));
        this.rewards.add(new AchievementReward("skyblock_legendary_itembreaks_50").setRarity(Rarity.LEGENDARY).setName("Breaker IV"));

        // ItemDrops
        this.rewards.add(new AchievementReward("skyblock_rare_itemdrops_64").setRarity(Rarity.RARE).setName("Dropper I"));

        // ItemPickup
        this.rewards.add(new AchievementReward("skyblock_rare_itempickups_64").setRarity(Rarity.RARE).setName("Pickup I"));
    }
}
