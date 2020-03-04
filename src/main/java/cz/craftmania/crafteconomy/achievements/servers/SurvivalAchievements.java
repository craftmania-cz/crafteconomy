package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.objects.AchievementReward;

import java.util.List;

public class SurvivalAchievements {

    private List<AchievementReward> rewards;

    public SurvivalAchievements(List<AchievementReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {

        // Anvil
        this.rewards.add(new AchievementReward("survival_common_anvilsused_1").setRarity(Rarity.COMMON).setName("Anviled I"));
        this.rewards.add(new AchievementReward("survival_rare_anvilsused_50").setRarity(Rarity.RARE).setName("Anviled II"));
        this.rewards.add(new AchievementReward("survival_epic_anvilsused_250").setRarity(Rarity.EPIC).setName("Anviled III"));
        this.rewards.add(new AchievementReward("survival_legendary_anvilsused_500").setRarity(Rarity.LEGENDARY).setName("Anviled IV"));

        // Arrows
        this.rewards.add(new AchievementReward("survival_common_arrows_50").setRarity(Rarity.COMMON).setName("Shooter I"));
        this.rewards.add(new AchievementReward("survival_rare_arrows_250").setRarity(Rarity.RARE).setName("Shooter II"));
        this.rewards.add(new AchievementReward("survival_epic_arrows_1000").setRarity(Rarity.EPIC).setName("Shooter III"));
        this.rewards.add(new AchievementReward("survival_legendary_arrows_5000").setRarity(Rarity.LEGENDARY).setName("Shooter IV"));

        // Beds
        this.rewards.add(new AchievementReward("survival_common_beds_1").setRarity(Rarity.COMMON).setName("Sleeper I"));
        this.rewards.add(new AchievementReward("survival_rare_beds_50").setRarity(Rarity.RARE).setName("Sleeper II"));
        this.rewards.add(new AchievementReward("survival_epic_beds_250").setRarity(Rarity.EPIC).setName("Sleeper III"));
        this.rewards.add(new AchievementReward("survival_legendary_beds_1000").setRarity(Rarity.LEGENDARY).setName("Sleeper IV"));

        // Breaks
        this.rewards.add(new AchievementReward("survival_mythic_breaks_stone_1000000").setRarity(Rarity.MYTHIC).setName("Destroyed I"));

        // Breeding
        this.rewards.add(new AchievementReward("survival_common_breeding_cow_1").setRarity(Rarity.COMMON).setName("Farmer I"));
        this.rewards.add(new AchievementReward("survival_rare_breeding_cow_50").setRarity(Rarity.RARE).setName("Farmer II"));
        this.rewards.add(new AchievementReward("survival_epic_breeding_cow_250").setRarity(Rarity.EPIC).setName("Farmer III"));
        this.rewards.add(new AchievementReward("survival_legendary_breeding_cow_1000").setRarity(Rarity.LEGENDARY).setName("Farmer IV"));
        this.rewards.add(new AchievementReward("survival_common_breeding_chicken_1").setRarity(Rarity.COMMON).setName("Farmer V"));
        this.rewards.add(new AchievementReward("survival_rare_breeding_chicken_50").setRarity(Rarity.RARE).setName("Farmer VI"));
        this.rewards.add(new AchievementReward("survival_epic_breeding_chicken_250").setRarity(Rarity.EPIC).setName("Farmer VII"));
        this.rewards.add(new AchievementReward("survival_legendary_breeding_chicken_1000").setRarity(Rarity.LEGENDARY).setName("Farmer VIII"));
        this.rewards.add(new AchievementReward("survival_common_breeding_pig_1").setRarity(Rarity.COMMON).setName("Farmer IX"));
        this.rewards.add(new AchievementReward("survival_rare_breeding_pig_50").setRarity(Rarity.RARE).setName("Farmer X"));
        this.rewards.add(new AchievementReward("survival_epic_breeding_pig_250").setRarity(Rarity.EPIC).setName("Farmer XI"));
        this.rewards.add(new AchievementReward("survival_legendary_breeding_pig_1000").setRarity(Rarity.LEGENDARY).setName("Farmer XII"));
        this.rewards.add(new AchievementReward("survival_common_breeding_sheep_1").setRarity(Rarity.COMMON).setName("Farmer XIII"));
        this.rewards.add(new AchievementReward("survival_rare_breeding_sheep_50").setRarity(Rarity.RARE).setName("Farmer XIV"));
        this.rewards.add(new AchievementReward("survival_epic_breeding_sheep_250").setRarity(Rarity.EPIC).setName("Farmer XV"));
        this.rewards.add(new AchievementReward("survival_legendary_breeding_sheep_1000").setRarity(Rarity.LEGENDARY).setName("Farmer XVI"));

        // Brewing
        this.rewards.add(new AchievementReward("survival_common_brewing_1").setRarity(Rarity.COMMON).setName("Alchemie I"));
        this.rewards.add(new AchievementReward("survival_rare_brewing_50").setRarity(Rarity.RARE).setName("Alchemie II"));
        this.rewards.add(new AchievementReward("survival_epic_brewing_250").setRarity(Rarity.EPIC).setName("Alchemie III"));
        this.rewards.add(new AchievementReward("survival_legendary_brewing_1000").setRarity(Rarity.LEGENDARY).setName("Alchemie IV"));

        // Connections
        this.rewards.add(new AchievementReward("survival_rare_connections_1").setRarity(Rarity.RARE).setName("Vítej na Survivalu!"));
        this.rewards.add(new AchievementReward("survival_rare_connections_50").setRarity(Rarity.RARE).setName("Vítej na Survivalu II"));
        this.rewards.add(new AchievementReward("survival_legendary_connections_500").setRarity(Rarity.LEGENDARY).setName("Vítej na Survivalu III"));

        // Consumed potions
        this.rewards.add(new AchievementReward("survival_common_consumedpotions_1").setRarity(Rarity.COMMON).setName("DrinkIt I"));
        this.rewards.add(new AchievementReward("survival_rare_consumedpotions_50").setRarity(Rarity.RARE).setName("DrinkIt II"));
        this.rewards.add(new AchievementReward("survival_epic_consumedpotions_250").setRarity(Rarity.EPIC).setName("DrinkIt III"));
        this.rewards.add(new AchievementReward("survival_legendary_consumedpotions_1000").setRarity(Rarity.LEGENDARY).setName("DrinkIt IV"));

        // Eat
        this.rewards.add(new AchievementReward("survival_common_eatenitems_1").setRarity(Rarity.COMMON).setName("EatThis I"));
        this.rewards.add(new AchievementReward("survival_rare_eatenitems_50").setRarity(Rarity.RARE).setName("EatThis II"));
        this.rewards.add(new AchievementReward("survival_epic_eatenitems_500").setRarity(Rarity.EPIC).setName("EatThis III"));
        this.rewards.add(new AchievementReward("survival_legendary_eatenitems_1000").setRarity(Rarity.LEGENDARY).setName("EatThis IV"));

        // Egs
        this.rewards.add(new AchievementReward("survival_common_eggs_100").setRarity(Rarity.COMMON).setName("ChickenSmash I"));
        this.rewards.add(new AchievementReward("survival_rare_eggs_500").setRarity(Rarity.RARE).setName("ChickenSmash II"));
        this.rewards.add(new AchievementReward("survival_epic_eggs_1000").setRarity(Rarity.EPIC).setName("ChickenSmash III"));
        this.rewards.add(new AchievementReward("survival_legendary_eggs_5000").setRarity(Rarity.LEGENDARY).setName("ChickenSmash IV"));

        // Enderpearls
        this.rewards.add(new AchievementReward("survival_common_enderpearls_10").setRarity(Rarity.COMMON).setName("End I"));
        this.rewards.add(new AchievementReward("survival_rare_enderpearls_200").setRarity(Rarity.RARE).setName("End II"));
        this.rewards.add(new AchievementReward("survival_epic_enderpearls_500").setRarity(Rarity.EPIC).setName("End III"));
        this.rewards.add(new AchievementReward("survival_legendary_enderpearls_1000").setRarity(Rarity.LEGENDARY).setName("End IV"));

        // Enchantments
        this.rewards.add(new AchievementReward("survival_common_enchantments_1").setRarity(Rarity.COMMON).setName("Enchanting I"));
        this.rewards.add(new AchievementReward("survival_rare_enchantments_50").setRarity(Rarity.RARE).setName("Enchanting II"));
        this.rewards.add(new AchievementReward("survival_epic_enchantments_500").setRarity(Rarity.EPIC).setName("Enchanting III"));
        this.rewards.add(new AchievementReward("survival_legendary_enchantments_1000").setRarity(Rarity.LEGENDARY).setName("Enchanting IV"));

        // Fertilising
        this.rewards.add(new AchievementReward("survival_common_fertilising_15").setRarity(Rarity.COMMON).setName("Gardener I"));
        this.rewards.add(new AchievementReward("survival_rare_fertilising_100").setRarity(Rarity.RARE).setName("Gardener II"));
        this.rewards.add(new AchievementReward("survival_epic_fertilising_1000").setRarity(Rarity.EPIC).setName("Gardener III"));
        this.rewards.add(new AchievementReward("survival_legendary_fertilising_5000").setRarity(Rarity.LEGENDARY).setName("Gardener IV"));

        // Fireworks
        this.rewards.add(new AchievementReward("survival_common_fireworks_1").setRarity(Rarity.COMMON).setName("Fireworks I"));
        this.rewards.add(new AchievementReward("survival_rare_fireworks_100").setRarity(Rarity.RARE).setName("Fireworks II"));
        this.rewards.add(new AchievementReward("survival_epic_fireworks_500").setRarity(Rarity.EPIC).setName("Fireworks III"));
        this.rewards.add(new AchievementReward("survival_legendary_fireworks_1000").setRarity(Rarity.LEGENDARY).setName("Fireworks IV"));

        // Fish
        this.rewards.add(new AchievementReward("survival_common_fish_1").setRarity(Rarity.COMMON).setName("Fisherman I"));
        this.rewards.add(new AchievementReward("survival_rare_fish_100").setRarity(Rarity.RARE).setName("Fisherman II"));
        this.rewards.add(new AchievementReward("survival_epic_fish_500").setRarity(Rarity.EPIC).setName("Fisherman III"));
        this.rewards.add(new AchievementReward("survival_legendary_fish_1000").setRarity(Rarity.LEGENDARY).setName("Fisherman IV"));

        // Hoe plowings
        this.rewards.add(new AchievementReward("survival_common_hoeplowings_10").setRarity(Rarity.COMMON).setName("Fielder I"));
        this.rewards.add(new AchievementReward("survival_rare_hoeplowings_100").setRarity(Rarity.RARE).setName("Fielder II"));
        this.rewards.add(new AchievementReward("survival_epic_hoeplowings_2000").setRarity(Rarity.EPIC).setName("Fielder III"));
        this.rewards.add(new AchievementReward("survival_legendary_hoeplowings_5000").setRarity(Rarity.LEGENDARY).setName("Fieldder IV"));

        // ItemBreaks
        this.rewards.add(new AchievementReward("survival_common_itembreaks_1").setRarity(Rarity.COMMON).setName("Breaker I"));
        this.rewards.add(new AchievementReward("survival_rare_itembreaks_50").setRarity(Rarity.RARE).setName("Breaker II"));
        this.rewards.add(new AchievementReward("survival_epic_itembreaks_200").setRarity(Rarity.EPIC).setName("Breaker III"));
        this.rewards.add(new AchievementReward("survival_legendary_itembreaks_500").setRarity(Rarity.LEGENDARY).setName("Breaker IV"));

        // ItemDrops
        this.rewards.add(new AchievementReward("survival_rare_itemdrops_64").setRarity(Rarity.RARE).setName("Dropper I"));

        // ItemPickup
        this.rewards.add(new AchievementReward("survival_rare_itempickups_64").setRarity(Rarity.RARE).setName("Pickup I"));

        // Kills
        this.rewards.add(new AchievementReward("survival_common_kills_witch_50").setRarity(Rarity.COMMON).setName("Hunter I"));
        this.rewards.add(new AchievementReward("survival_mythic_kills_wither_1").setRarity(Rarity.MYTHIC).setName("Hunter II"));
        this.rewards.add(new AchievementReward("survival_epic_kills_vindicator_100").setRarity(Rarity.EPIC).setName("Hunter III"));
        this.rewards.add(new AchievementReward("survival_epic_kills_stray_250").setRarity(Rarity.EPIC).setName("Hunver IV"));
        this.rewards.add(new AchievementReward("survival_rare_kills_squid_100").setRarity(Rarity.RARE).setName("Hunter V"));
        this.rewards.add(new AchievementReward("survival_epic_kills_drowned_100").setRarity(Rarity.EPIC).setName("Hunter VII"));
        this.rewards.add(new AchievementReward("survival_rare_kills_endermite_50").setRarity(Rarity.RARE).setName("Hunter VIII"));
        this.rewards.add(new AchievementReward("survival_epic_kills_evoker_50").setRarity(Rarity.EPIC).setName("Hunter IX"));
        this.rewards.add(new AchievementReward("survival_rare_kills_husk_100").setRarity(Rarity.RARE).setName("Hunter X"));
        this.rewards.add(new AchievementReward("survival_rare_kills_magma_cube_50").setRarity(Rarity.RARE).setName("Hunter XI"));
        this.rewards.add(new AchievementReward("survival_rare_kills_pig_300").setRarity(Rarity.RARE).setName("Hunter XII"));
        this.rewards.add(new AchievementReward("survival_epic_kills_pillager_100").setRarity(Rarity.EPIC).setName("Hunter XIII"));
        this.rewards.add(new AchievementReward("survival_common_kills_player_5").setRarity(Rarity.COMMON).setName("Hunter XIV"));
        this.rewards.add(new AchievementReward("survival_epic_kills_ravenger_5").setRarity(Rarity.EPIC).setName("Hunter XV"));
        this.rewards.add(new AchievementReward("survival_rare_kills_sheep_300").setRarity(Rarity.RARE).setName("Hunter XVI"));
        this.rewards.add(new AchievementReward("survival_rare_kills_shulker_100").setRarity(Rarity.RARE).setName("Hunter XVII"));
        this.rewards.add(new AchievementReward("survival_rare_kills_slime_150").setRarity(Rarity.RARE).setName("Hunter XVIII"));
        this.rewards.add(new AchievementReward("survival_rare_kills_cow_300").setRarity(Rarity.RARE).setName("Hunter XIX"));
        this.rewards.add(new AchievementReward("survival_epic_kills_rabbit_300").setRarity(Rarity.EPIC).setName("Hunter XX"));
        this.rewards.add(new AchievementReward("survival_epic_kills_creeper_500").setRarity(Rarity.EPIC).setName("Hunter XXI"));
        this.rewards.add(new AchievementReward("survival_mythic_kills_creeper_5000").setRarity(Rarity.MYTHIC).setName("Hunter XXII"));
        this.rewards.add(new AchievementReward("survival_epic_kills_skeleton_500").setRarity(Rarity.EPIC).setName("Hunter XXIII"));
        this.rewards.add(new AchievementReward("survival_mythic_kills_skeleton_10000").setRarity(Rarity.MYTHIC).setName("Hunter XXIV"));
        this.rewards.add(new AchievementReward("survival_epic_kills_spider_500").setRarity(Rarity.EPIC).setName("Hunter XXV"));
        this.rewards.add(new AchievementReward("survival_mythic_kills_spider_10000").setRarity(Rarity.MYTHIC).setName("Hunter XXVI"));
        this.rewards.add(new AchievementReward("survival_epic_kills_zombie_500").setRarity(Rarity.EPIC).setName("Hunter XXVII"));
        this.rewards.add(new AchievementReward("survival_mythic_kills_zombie_10000").setRarity(Rarity.MYTHIC).setName("Hunter XXVIII"));

        // LavaBuckets
        this.rewards.add(new AchievementReward("survival_common_lavabuckets_10").setRarity(Rarity.COMMON).setName("Pyroman I"));
        this.rewards.add(new AchievementReward("survival_rare_lavabuckets_50").setRarity(Rarity.RARE).setName("Pyroman II"));
        this.rewards.add(new AchievementReward("survival_epic_lavabuckets_200").setRarity(Rarity.EPIC).setName("Pyroman III"));
        this.rewards.add(new AchievementReward("survival_legendary_lavabuckets_1000").setRarity(Rarity.LEGENDARY).setName("Pyroman IV"));

        // Levels
        this.rewards.add(new AchievementReward("survival_common_maxlevel_30").setRarity(Rarity.COMMON).setName("Levels I"));
        this.rewards.add(new AchievementReward("survival_rare_maxlevel_100").setRarity(Rarity.RARE).setName("Levels II"));
        this.rewards.add(new AchievementReward("survival_epic_maxlevel_200").setRarity(Rarity.EPIC).setName("Levels III"));
        this.rewards.add(new AchievementReward("survival_legendary_maxlevel_300").setRarity(Rarity.LEGENDARY).setName("Levels IV"));
        this.rewards.add(new AchievementReward("survival_mythic_maxlevel_500").setRarity(Rarity.MYTHIC).setName("Levels V"));

        // Mcmmo_Skill_Level_Up
        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_akrobacie_1").setRarity(Rarity.COMMON).setName("Acrobatic I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_akrobacie_5").setRarity(Rarity.RARE).setName("Acrobatic II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_akrobacie_10").setRarity(Rarity.RARE).setName("Acrobatic III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_akrobacie_20").setRarity(Rarity.RARE).setName("Acrobatic IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_akrobacie_50").setRarity(Rarity.RARE).setName("Acrobatic V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_akrobacie_100").setRarity(Rarity.EPIC).setName("Acrobatic VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_akrobacie_150").setRarity(Rarity.EPIC).setName("Acrobatic VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_akrobacie_200").setRarity(Rarity.LEGENDARY).setName("Acrobatic VIII"));
        this.rewards.add(new AchievementReward("survival_mythic_mcmmo_skill_level_up_akrobacie_250").setRarity(Rarity.MYTHIC).setName("Acrobatic IX"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_alchemie_1").setRarity(Rarity.COMMON).setName("Alchemy I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_alchemie_5").setRarity(Rarity.RARE).setName("Alchemy II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_alchemie_10").setRarity(Rarity.RARE).setName("Alchemy III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_alchemie_20").setRarity(Rarity.RARE).setName("Alchemy IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_alchemie_50").setRarity(Rarity.RARE).setName("Alchemy V"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_alchemie_100").setRarity(Rarity.RARE).setName("Alchemy VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_alchemie_150").setRarity(Rarity.EPIC).setName("Alchemy VII"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_alchemie_200").setRarity(Rarity.EPIC).setName("Alchemy VIII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_alchemie_250").setRarity(Rarity.LEGENDARY).setName("Alchemy IX"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_bylinkarstvi_1").setRarity(Rarity.COMMON).setName("Herbalism I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_bylinkarstvi_5").setRarity(Rarity.RARE).setName("Herbalism II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_bylinkarstvi_10").setRarity(Rarity.RARE).setName("Herbalism III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_bylinkarstvi_20").setRarity(Rarity.RARE).setName("Herbalism IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_bylinkarstvi_50").setRarity(Rarity.RARE).setName("Herbalism V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_bylinkarstvi_75").setRarity(Rarity.EPIC).setName("Herbalism VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_bylinkarstvi_100").setRarity(Rarity.EPIC).setName("Herbalism VII"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_bylinkarstvi_150").setRarity(Rarity.EPIC).setName("Herbalism VIII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_bylinkarstvi_200").setRarity(Rarity.LEGENDARY).setName("Herbalism IX"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_bylinkarstvi_250").setRarity(Rarity.LEGENDARY).setName("Herbalism X"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_dolovani_1").setRarity(Rarity.COMMON).setName("Mining I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_dolovani_5").setRarity(Rarity.RARE).setName("Mining II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_dolovani_10").setRarity(Rarity.RARE).setName("Mining III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_dolovani_20").setRarity(Rarity.RARE).setName("Mining IV"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_dolovani_30").setRarity(Rarity.EPIC).setName("Mining V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_dolovani_50").setRarity(Rarity.EPIC).setName("Mining VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_dolovani_100").setRarity(Rarity.EPIC).setName("Mining VII"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_dolovani_150").setRarity(Rarity.EPIC).setName("Mining VIII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_dolovani_200").setRarity(Rarity.LEGENDARY).setName("Mining IX"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_dolovani_250").setRarity(Rarity.LEGENDARY).setName("Mining X"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_drevorubectvi_1").setRarity(Rarity.COMMON).setName("Woodcutting I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_drevorubectvi_3").setRarity(Rarity.RARE).setName("Woodcutting II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_drevorubectvi_5").setRarity(Rarity.RARE).setName("Woodcutting III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_drevorubectvi_10").setRarity(Rarity.RARE).setName("Woodcutting IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_drevorubectvi_20").setRarity(Rarity.RARE).setName("Woodcutting V"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_drevorubectvi_50").setRarity(Rarity.RARE).setName("Woodcutting VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_drevorubectvi_75").setRarity(Rarity.EPIC).setName("Woodcutting VII"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_drevorubectvi_100").setRarity(Rarity.EPIC).setName("Woodcutting VIII"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_drevorubectvi_150").setRarity(Rarity.EPIC).setName("Woodcutting IX"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_drevorubectvi_200").setRarity(Rarity.LEGENDARY).setName("Woodcutting X"));
        this.rewards.add(new AchievementReward("survival_mythic_mcmmo_skill_level_up_drevorubectvi_250").setRarity(Rarity.MYTHIC).setName("Woodcutting XI"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_kopani_1").setRarity(Rarity.COMMON).setName("Exkavation I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_kopani_5").setRarity(Rarity.RARE).setName("Exkavation II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_kopani_10").setRarity(Rarity.RARE).setName("Exkavation III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_kopani_20").setRarity(Rarity.RARE).setName("Exkavation IV"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_kopani_50").setRarity(Rarity.EPIC).setName("Exkavation V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_kopani_75").setRarity(Rarity.EPIC).setName("Exkavation VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_kopani_100").setRarity(Rarity.EPIC).setName("Exkavation VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_kopani_150").setRarity(Rarity.LEGENDARY).setName("Exkavation VIII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_kopani_200").setRarity(Rarity.LEGENDARY).setName("Exkavation IX"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_kopani_250").setRarity(Rarity.LEGENDARY).setName("Exkavation X"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_lukostrelba_1").setRarity(Rarity.COMMON).setName("Archery I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_lukostrelba_10").setRarity(Rarity.RARE).setName("Archery II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_lukostrelba_20").setRarity(Rarity.RARE).setName("Archery III"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_lukostrelba_50").setRarity(Rarity.EPIC).setName("Archery IV"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_lukostrelba_100").setRarity(Rarity.LEGENDARY).setName("Archery V"));
        this.rewards.add(new AchievementReward("survival_mythic_mcmmo_skill_level_up_lukostrelba_150").setRarity(Rarity.MYTHIC).setName("Archery VI"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_mece_1").setRarity(Rarity.COMMON).setName("Swords I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_mece_10").setRarity(Rarity.RARE).setName("Swords II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_mece_20").setRarity(Rarity.RARE).setName("Swords III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_mece_50").setRarity(Rarity.RARE).setName("Swords IV"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_mece_100").setRarity(Rarity.EPIC).setName("Swords V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_mece_150").setRarity(Rarity.EPIC).setName("Swords VI"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_mece_200").setRarity(Rarity.LEGENDARY).setName("Swords VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_mece_250").setRarity(Rarity.LEGENDARY).setName("Swords VIII"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_neozbrojeny_1").setRarity(Rarity.COMMON).setName("Unarmed I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_neozbrojeny_5").setRarity(Rarity.RARE).setName("Unarmed II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_neozbrojeny_10").setRarity(Rarity.RARE).setName("Unarmed III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_neozbrojeny_20").setRarity(Rarity.RARE).setName("Unarmed IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_neozbrojeny_30").setRarity(Rarity.RARE).setName("Unamed V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_neozbrojeny_50").setRarity(Rarity.EPIC).setName("Unarmed VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_neozbrojeny_75").setRarity(Rarity.EPIC).setName("Unarmed VII"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_neozbrojeny_100").setRarity(Rarity.EPIC).setName("Unaremd VIII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_neozbrojeny_150").setRarity(Rarity.LEGENDARY).setName("Unarmed IX"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_neozbrojeny_200").setRarity(Rarity.LEGENDARY).setName("Unarmed X"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_ochocovani_1").setRarity(Rarity.COMMON).setName("Tamming I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_ochocovani_10").setRarity(Rarity.RARE).setName("Tamming II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_ochocovani_20").setRarity(Rarity.RARE).setName("Tamming III"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_ochocovani_30").setRarity(Rarity.EPIC).setName("Tamming IV"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_ochocovani_50").setRarity(Rarity.EPIC).setName("Tamming V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_ochocovani_100").setRarity(Rarity.EPIC).setName("Tamming VI"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_ochocovani_150").setRarity(Rarity.LEGENDARY).setName("Tamming VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_ochocovani_200").setRarity(Rarity.LEGENDARY).setName("Tamming VIII"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_opravovani_1").setRarity(Rarity.COMMON).setName("Repairing I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_opravovani_10").setRarity(Rarity.RARE).setName("Repairing II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_opravovani_20").setRarity(Rarity.RARE).setName("Repairing III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_opravovani_50").setRarity(Rarity.RARE).setName("Repairing IV"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_opravovani_100").setRarity(Rarity.EPIC).setName("Repairing V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_opravovani_150").setRarity(Rarity.EPIC).setName("Repairing VI"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_opravovani_200").setRarity(Rarity.LEGENDARY).setName("Repairing VII"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_recyklovani_1").setRarity(Rarity.COMMON).setName("Salvage I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_recyklovani_10").setRarity(Rarity.RARE).setName("Salvage II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_recyklovani_20").setRarity(Rarity.RARE).setName("Salvage III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_recyklovani_30").setRarity(Rarity.RARE).setName("Salvage IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_recyklovani_50").setRarity(Rarity.RARE).setName("Salvage V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_recyklovani_75").setRarity(Rarity.EPIC).setName("Salvage VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_recyklovani_100").setRarity(Rarity.EPIC).setName("Salvage VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_recyklovani_150").setRarity(Rarity.LEGENDARY).setName("Salvage VIII"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_rybareni_1").setRarity(Rarity.COMMON).setName("Fishing I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_rybareni_10").setRarity(Rarity.RARE).setName("Fishing II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_rybareni_20").setRarity(Rarity.RARE).setName("Fishing III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_rybareni_50").setRarity(Rarity.RARE).setName("Fishing IV"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_rybareni_100").setRarity(Rarity.EPIC).setName("Fishing V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_rybareni_150").setRarity(Rarity.EPIC).setName("Fishing VI"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_rybareni_200").setRarity(Rarity.LEGENDARY).setName("Fishing VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_rybareni_250").setRarity(Rarity.LEGENDARY).setName("Fishing VIII"));

        this.rewards.add(new AchievementReward("survival_common_mcmmo_skill_level_up_sekery_1").setRarity(Rarity.COMMON).setName("Axes I"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_sekery_5").setRarity(Rarity.RARE).setName("Axes II"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_sekery_10").setRarity(Rarity.RARE).setName("Axes III"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_sekery_20").setRarity(Rarity.RARE).setName("Axes IV"));
        this.rewards.add(new AchievementReward("survival_rare_mcmmo_skill_level_up_sekery_50").setRarity(Rarity.RARE).setName("Axes V"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_sekery_100").setRarity(Rarity.EPIC).setName("Axes VI"));
        this.rewards.add(new AchievementReward("survival_epic_mcmmo_skill_level_up_sekery_150").setRarity(Rarity.EPIC).setName("Axes VII"));
        this.rewards.add(new AchievementReward("survival_legendary_mcmmo_skill_level_up_sekery_200").setRarity(Rarity.LEGENDARY).setName("Axes VIII"));
        this.rewards.add(new AchievementReward("survival_mythic_mcmmo_skill_level_up_sekery_250").setRarity(Rarity.MYTHIC).setName("Axes IX"));

        // Milk
        this.rewards.add(new AchievementReward("survival_common_milk_1").setRarity(Rarity.COMMON).setName("Cows I"));
        this.rewards.add(new AchievementReward("survival_rare_milk_100").setRarity(Rarity.RARE).setName("Cows II"));
        this.rewards.add(new AchievementReward("survival_epic_milk_1000").setRarity(Rarity.EPIC).setName("Cows III"));
        this.rewards.add(new AchievementReward("survival_legendary_milk_5000").setRarity(Rarity.LEGENDARY).setName("Cows IV"));

        // Discs
        this.rewards.add(new AchievementReward("survival_common_musicdiscs_1").setRarity(Rarity.COMMON).setName("Disco I"));
        this.rewards.add(new AchievementReward("survival_rare_musicdiscs_20").setRarity(Rarity.RARE).setName("Disco II"));

        // Places
        this.rewards.add(new AchievementReward("survival_common_places_cobblestone_1000").setRarity(Rarity.COMMON).setName("Builder I"));
        this.rewards.add(new AchievementReward("survival_rare_places_cobblestone_50000").setRarity(Rarity.RARE).setName("Builder II"));
        this.rewards.add(new AchievementReward("survival_epic_places_cobblestone_250000").setRarity(Rarity.EPIC).setName("Builder III"));
        this.rewards.add(new AchievementReward("survival_legendary_places_cobblestone_1000000").setRarity(Rarity.LEGENDARY).setName("Builder IV"));

        this.rewards.add(new AchievementReward("survival_common_places_chest_5").setRarity(Rarity.COMMON).setName("Builder V"));
        this.rewards.add(new AchievementReward("survival_common_places_chest_50").setRarity(Rarity.COMMON).setName("Builder VI"));

        this.rewards.add(new AchievementReward("survival_common_places_stone_1000").setRarity(Rarity.COMMON).setName("Builder VII"));
        this.rewards.add(new AchievementReward("survival_rare_places_stone_50000").setRarity(Rarity.RARE).setName("Builder VIII"));
        this.rewards.add(new AchievementReward("survival_epic_places_stone_250000").setRarity(Rarity.EPIC).setName("Builder IX"));
        this.rewards.add(new AchievementReward("survival_legendary_places_stone_1000000").setRarity(Rarity.LEGENDARY).setName("Builder X"));

        // Played time
        this.rewards.add(new AchievementReward("survival_common_playedtime_1").setRarity(Rarity.COMMON).setName("První hodina"));
        this.rewards.add(new AchievementReward("survival_rare_playedtime_12").setRarity(Rarity.RARE).setName("Půl dne online"));
        this.rewards.add(new AchievementReward("survival_epic_playedtime_24").setRarity(Rarity.EPIC).setName("Celý den online"));
        this.rewards.add(new AchievementReward("survival_legendary_playedtime_168").setRarity(Rarity.LEGENDARY).setName("Týden online"));
        this.rewards.add(new AchievementReward("survival_mythic_playedtime_720").setRarity(Rarity.MYTHIC).setName("Měsíc online"));

        // Player commands
        this.rewards.add(new AchievementReward("survival_rare_playercommands_rtp_1").setRarity(Rarity.RARE).setName("Commander II"));
        this.rewards.add(new AchievementReward("survival_rare_playercommands_vip_1").setRarity(Rarity.RARE).setName("Commander I"));

        // Shears
        this.rewards.add(new AchievementReward("survival_common_shear_100").setRarity(Rarity.COMMON).setName("Sheeps I"));

        // Smelting
        this.rewards.add(new AchievementReward("survival_common_smelting_50").setRarity(Rarity.COMMON).setName("Smelter I"));
        this.rewards.add(new AchievementReward("survival_rare_smelting_250").setRarity(Rarity.RARE).setName("Smelter II"));

        // Snowballs
        this.rewards.add(new AchievementReward("survival_common_snowballs_1000").setRarity(Rarity.EPIC).setName("Milovník koulí"));

        // Tamming
        this.rewards.add(new AchievementReward("survival_common_taming_1").setRarity(Rarity.COMMON).setName("Tammer I"));
        this.rewards.add(new AchievementReward("survival_rare_taming_10").setRarity(Rarity.RARE).setName("Tammer II"));
        this.rewards.add(new AchievementReward("survival_epic_taming_25").setRarity(Rarity.EPIC).setName("Tammer III"));
        this.rewards.add(new AchievementReward("survival_legendary_taming_50").setRarity(Rarity.LEGENDARY).setName("Tammer IV"));

        // Traders
        this.rewards.add(new AchievementReward("survival_common_trades_1").setRarity(Rarity.COMMON).setName("Trader I"));
        this.rewards.add(new AchievementReward("survival_rare_trades_100").setRarity(Rarity.RARE).setName("Trader II"));
        this.rewards.add(new AchievementReward("survival_epic_trades_500").setRarity(Rarity.EPIC).setName("Trader III"));
        this.rewards.add(new AchievementReward("survival_legendary_trades_1000").setRarity(Rarity.LEGENDARY).setName("Trader IV"));

        // Treasures
        this.rewards.add(new AchievementReward("survival_common_treasures_1").setRarity(Rarity.COMMON).setName("Pirate I"));
        this.rewards.add(new AchievementReward("survival_rare_treasures_100").setRarity(Rarity.RARE).setName("Pirate II"));
        this.rewards.add(new AchievementReward("survival_epic_treasures_500").setRarity(Rarity.EPIC).setName("Pirate III"));
        this.rewards.add(new AchievementReward("survival_legendary_treasures_1000").setRarity(Rarity.LEGENDARY).setName("Pirate IV"));

        // Water
        this.rewards.add(new AchievementReward("survival_common_waterbuckets_10").setRarity(Rarity.COMMON).setName("Waterman I"));
        this.rewards.add(new AchievementReward("survival_rare_waterbuckets_50").setRarity(Rarity.RARE).setName("Waterman II"));
        this.rewards.add(new AchievementReward("survival_epic_waterbuckets_200").setRarity(Rarity.EPIC).setName("Waterman III"));
        this.rewards.add(new AchievementReward("survival_legendary_waterbuckets_1000").setRarity(Rarity.LEGENDARY).setName("Waterman IV"));
    }
}
