package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.objects.QuestReward;

import java.util.List;

public class VanillaAchievements {

    private List<QuestReward> rewards;

    public VanillaAchievements(List<QuestReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {
/*
        // Anvil Used
        this.rewards.add(new AchievementReward("vanilla_common_anvilsused_10").setRarity(Rarity.COMMON).setName("Anviled I"));
        this.rewards.add(new AchievementReward("vanilla_rare_anvilsused_30").setRarity(Rarity.RARE).setName("Anviled II"));
        this.rewards.add(new AchievementReward("vanilla_legendary_anvilsused_100").setRarity(Rarity.LEGENDARY).setName("Anviled III"));

        // Beds
        this.rewards.add(new AchievementReward("vanilla_common_beds_1").setRarity(Rarity.COMMON).setName("BedTime I"));
        this.rewards.add(new AchievementReward("vanilla_rare_beds_30").setRarity(Rarity.RARE).setName("BedTime II"));

        // Breaks
        this.rewards.add(new AchievementReward("vanilla_common_breaks_coal_ore_30").setRarity(Rarity.COMMON).setName("CoalBreaker I"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_coal_ore_100").setRarity(Rarity.RARE).setName("CoalBreaker II"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_coal_ore_250").setRarity(Rarity.EPIC).setName("CoalBreaker III"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_cobweb_150").setRarity(Rarity.EPIC).setName("Sandstorm II"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_dead_bush_100").setRarity(Rarity.RARE).setName("Sandstorm I"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_diamond_ore_10").setRarity(Rarity.COMMON).setName("DiamondBreaker I"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_diamond_ore_30").setRarity(Rarity.RARE).setName("DiamondBreaker II"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_diamond_ore_90").setRarity(Rarity.EPIC).setName("DiamondBreaker III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_breaks_diamond_ore_200").setRarity(Rarity.LEGENDARY).setName("DiamondBreaker IV"));
        this.rewards.add(new AchievementReward("vanilla_mythic_breaks_diamond_ore_400").setRarity(Rarity.MYTHIC).setName("DiamondBreaker V"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_end_stone_100").setRarity(Rarity.RARE).setName("End I"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_end_stone_1000").setRarity(Rarity.EPIC).setName("End II"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_gold_ore_10").setRarity(Rarity.COMMON).setName("GoldBreaker I"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_gold_ore_30").setRarity(Rarity.RARE).setName("GoldBreaker II"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_gold_ore_75").setRarity(Rarity.EPIC).setName("GoldBreaker III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_breaks_gold_ore_150").setRarity(Rarity.LEGENDARY).setName("GoldBreaker IV"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_grass_block_100").setRarity(Rarity.COMMON).setName("Gardener I"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_grass_block_500").setRarity(Rarity.RARE).setName("Gardener II"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_grass_block_2000").setRarity(Rarity.RARE).setName("Gardener III"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_grass_block_5000").setRarity(Rarity.EPIC).setName("Gardener IV"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_ice_50").setRarity(Rarity.COMMON).setName("IceStorm I"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_ice_150").setRarity(Rarity.RARE).setName("IceStorm II"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_ice_500").setRarity(Rarity.EPIC).setName("IceStoorm III"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_iron_ore_20").setRarity(Rarity.COMMON).setName("IronBreaker I"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_iron_ore_50").setRarity(Rarity.RARE).setName("IronBreaker II"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_iron_ore_100").setRarity(Rarity.EPIC).setName("IronBreaker III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_breaks_iron_ore_250").setRarity(Rarity.LEGENDARY).setName("IronBreaker IV"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_mycelium_75").setRarity(Rarity.RARE).setName("Mycel"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_sand_200").setRarity(Rarity.COMMON).setName("SandBreaker I"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_sand_500").setRarity(Rarity.COMMON).setName("SandBreaker II"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_sand_2000").setRarity(Rarity.RARE).setName("SandBreaker III"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_sand_5000").setRarity(Rarity.EPIC).setName("SandBreaker IV"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_stone_100").setRarity(Rarity.COMMON).setName("StoneBreaker I"));
        this.rewards.add(new AchievementReward("vanilla_common_breaks_stone_500").setRarity(Rarity.COMMON).setName("StoneBreaker II"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_stone_2000").setRarity(Rarity.RARE).setName("StoneBreaker III"));
        this.rewards.add(new AchievementReward("vanilla_rare_breaks_stone_5000").setRarity(Rarity.RARE).setName("StoneBreaker IV"));
        this.rewards.add(new AchievementReward("vanilla_epic_breaks_stone_10000").setRarity(Rarity.EPIC).setName("StoneBreaker V"));
        this.rewards.add(new AchievementReward("vanilla_legendary_breaks_stone_20000").setRarity(Rarity.LEGENDARY).setName("StoneBreaker VI"));
        this.rewards.add(new AchievementReward("vanilla_mythic_breaks_stone_50000").setRarity(Rarity.MYTHIC).setName("StoneBreaker VII"));

        // Connections
        this.rewards.add(new AchievementReward("vanilla_rare_connections_1").setRarity(Rarity.RARE).setName("Vítej na Vanille!"));
        this.rewards.add(new AchievementReward("vanilla_rare_connections_50").setRarity(Rarity.RARE).setName("Vítej na Vanille II"));
        this.rewards.add(new AchievementReward("vanilla_legendary_connections_500").setRarity(Rarity.LEGENDARY).setName("Vítej na Vanille III"));

        // Crafts
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_anvil_1").setRarity(Rarity.LEGENDARY).setName("Crafter III"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_armor_stand_10").setRarity(Rarity.COMMON).setName("Armorer"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_arrow_50").setRarity(Rarity.RARE).setName("Shooter II"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_arrow_300").setRarity(Rarity.EPIC).setName("Shooter III"));
        this.rewards.add(new AchievementReward("vanilla_mythic_crafts_beacon_1").setRarity(Rarity.MYTHIC).setName("Crafter II"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_turtle_helmet_1").setRarity(Rarity.LEGENDARY).setName("Questions IX"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_bookshelf_5").setRarity(Rarity.COMMON).setName("Bookshelf I"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_bow_1").setRarity(Rarity.RARE).setName("Shooter"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_bread_20").setRarity(Rarity.RARE).setName("Farmer I"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_bread_75").setRarity(Rarity.EPIC).setName("Farmer II"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_bread_150").setRarity(Rarity.LEGENDARY).setName("Farmer III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_bread_500").setRarity(Rarity.LEGENDARY).setName("Farmer IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_brewing_stand_1").setRarity(Rarity.LEGENDARY).setName("Breeeew"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_cake_10").setRarity(Rarity.EPIC).setName("Sweets I"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_cake_50").setRarity(Rarity.LEGENDARY).setName("Sweets II"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_clock_1").setRarity(Rarity.LEGENDARY).setName("Questions IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_compass_1").setRarity(Rarity.LEGENDARY).setName("Questions II"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_crafting_table_1").setRarity(Rarity.COMMON).setName("Crafter I"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_dark_prismarine_30").setRarity(Rarity.RARE).setName("Prismarine I"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_diamond_axe_1").setRarity(Rarity.LEGENDARY).setName("DiamondAge"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_diamond_block_1").setRarity(Rarity.COMMON).setName("DiamondAge II"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_diamond_block_10").setRarity(Rarity.RARE).setName("DiamondAge III"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_diamond_block_25").setRarity(Rarity.RARE).setName("DiamondAge IV"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_diamond_block_50").setRarity(Rarity.EPIC).setName("DiamondAge V"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_diamond_block_100").setRarity(Rarity.LEGENDARY).setName("DiamondAge VI"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_emerald_block_1").setRarity(Rarity.COMMON).setName("EmeraldAge I"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_emerald_block_10").setRarity(Rarity.RARE).setName("EmeraldAge II"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_emerald_block_25").setRarity(Rarity.EPIC).setName("EmeraldAge III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_emerald_block_75").setRarity(Rarity.LEGENDARY).setName("EmeraldAge IV"));
        this.rewards.add(new AchievementReward("vanilla_mythic_crafts_emerald_block_150").setRarity(Rarity.MYTHIC).setName("EmeraldAge V"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_end_crystal_4").setRarity(Rarity.LEGENDARY).setName("TwoTimes"));
        this.rewards.add(new AchievementReward("vanilla_mythic_crafts_ender_eye_1").setRarity(Rarity.MYTHIC).setName("Questions VI"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_ender_chest_1").setRarity(Rarity.LEGENDARY).setName("Ender"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_enchanting_table_1").setRarity(Rarity.LEGENDARY).setName("Enchanter"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_golden_apple_1").setRarity(Rarity.EPIC).setName("GoldenApple I"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_golden_apple_10").setRarity(Rarity.EPIC).setName("GoldenApple II"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_golden_apple_30").setRarity(Rarity.LEGENDARY).setName("GoldenApple III"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_golden_axe_1").setRarity(Rarity.RARE).setName("GoldenAge"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_gold_block_1").setRarity(Rarity.COMMON).setName("GoldenAge II"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_gold_block_25").setRarity(Rarity.RARE).setName("GoldenAge III"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_gold_block_75").setRarity(Rarity.EPIC).setName("GoldenAge IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_gold_block_200").setRarity(Rarity.LEGENDARY).setName("GoldenAge V"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_iron_axe_1").setRarity(Rarity.RARE).setName("IronAge"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_iron_block_1").setRarity(Rarity.COMMON).setName("IronAge II"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_iron_block_25").setRarity(Rarity.RARE).setName("IronAge III"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_iron_block_100").setRarity(Rarity.EPIC).setName("IronAge IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_iron_block_250").setRarity(Rarity.LEGENDARY).setName("IronAge V"));
        this.rewards.add(new AchievementReward("vanilla_legendary_crafts_map_1").setRarity(Rarity.LEGENDARY).setName("Questions V"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_piston_30").setRarity(Rarity.COMMON).setName("Redstoner I"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_purple_shulker_box_1").setRarity(Rarity.EPIC).setName("Shulker I"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_quartz_block_10").setRarity(Rarity.RARE).setName("Quartzer I"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_quartz_block_30").setRarity(Rarity.RARE).setName("Quartzer II"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_quartz_block_75").setRarity(Rarity.EPIC).setName("Quartzer III"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_redstone_lamp_20").setRarity(Rarity.RARE).setName("Redstoner II"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_redstone_lamp_50").setRarity(Rarity.EPIC).setName("Redstoner III"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_shield_1").setRarity(Rarity.EPIC).setName("Questions VII"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_slime_block_1").setRarity(Rarity.EPIC).setName("Questions III"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_tnt_1").setRarity(Rarity.COMMON).setName("Explosion I"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_torch_1").setRarity(Rarity.COMMON).setName("Lighter I"));
        this.rewards.add(new AchievementReward("vanilla_common_crafts_torch_50").setRarity(Rarity.COMMON).setName("Lighter II"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_torch_300").setRarity(Rarity.RARE).setName("Lighter III"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_torch_750").setRarity(Rarity.RARE).setName("Lighter IV"));
        this.rewards.add(new AchievementReward("vanilla_epic_crafts_torch_1500").setRarity(Rarity.EPIC).setName("Lighter V"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_white_banner_20").setRarity(Rarity.RARE).setName("Banner"));
        this.rewards.add(new AchievementReward("vanilla_rare_crafts_wooden_axe_1").setRarity(Rarity.RARE).setName("WoodenAge"));

        // Deaths
        this.rewards.add(new AchievementReward("vanilla_common_deaths_1").setRarity(Rarity.COMMON).setName("Deaths I"));
        this.rewards.add(new AchievementReward("vanilla_epic_deaths_100").setRarity(Rarity.EPIC).setName("Deaths II"));

        // Horse distance
        this.rewards.add(new AchievementReward("vanilla_epic_distancehorse_10000").setRarity(Rarity.EPIC).setName("Horse Distance"));

        // Pig distance
        this.rewards.add(new AchievementReward("vanilla_epic_distancepig_1000").setRarity(Rarity.EPIC).setName("Pig Distance"));

        // Ender pearl throw
        this.rewards.add(new AchievementReward("vanilla_common_enderpearls_1").setRarity(Rarity.COMMON).setName("Ender I"));
        this.rewards.add(new AchievementReward("vanilla_rare_enderpearls_30").setRarity(Rarity.RARE).setName("Ender II"));
        this.rewards.add(new AchievementReward("vanilla_epic_enderpearls_75").setRarity(Rarity.EPIC).setName("Ender III"));

        // Item Breaks
        this.rewards.add(new AchievementReward("vanilla_epic_itembreaks_10").setRarity(Rarity.EPIC).setName("LowDamage I"));
        this.rewards.add(new AchievementReward("vanilla_legendary_itembreaks_50").setRarity(Rarity.LEGENDARY).setName("LowDamage II"));

        // Kills
        this.rewards.add(new AchievementReward("vanilla_rare_kills_blaze_50").setRarity(Rarity.RARE).setName("Hunter X"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_cow_100").setRarity(Rarity.RARE).setName("Hunter XIX"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_creeper_100").setRarity(Rarity.RARE).setName("Hunter VI"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_drowned_100").setRarity(Rarity.RARE).setName("Hunter XXII"));
        this.rewards.add(new AchievementReward("vanilla_mythic_kills_ender_dragon_1").setRarity(Rarity.MYTHIC).setName("Hunter XII"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_endermite_50").setRarity(Rarity.RARE).setName("Hunter XV"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_evoker_50").setRarity(Rarity.RARE).setName("Hunter IV"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_husk_100").setRarity(Rarity.RARE).setName("Hunter III"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_magma_cube_50").setRarity(Rarity.RARE).setName("Hunter XI"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_phantom_30").setRarity(Rarity.RARE).setName("Hunter XXI"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_pig_100").setRarity(Rarity.RARE).setName("Hunter XVII"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_pillager_100").setRarity(Rarity.RARE).setName("Hunter XXIV"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_player_1").setRarity(Rarity.RARE).setName("Hunter XXV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_kills_ravenger_5").setRarity(Rarity.LEGENDARY).setName("Hunter XXIII"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_sheep_100").setRarity(Rarity.RARE).setName("Hunter XVIII"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_shulker_50").setRarity(Rarity.RARE).setName("Hunter XVI"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_skeleton_100").setRarity(Rarity.RARE).setName("Hunter VII"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_slime_150").setRarity(Rarity.RARE).setName("Hunter IX"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_squid_50").setRarity(Rarity.RARE).setName("Hunter XX"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_stray_100").setRarity(Rarity.RARE).setName("Hunter II"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_vindicator_100").setRarity(Rarity.RARE).setName("Hunter V"));
        this.rewards.add(new AchievementReward("vanilla_mythic_kills_wither_1").setRarity(Rarity.MYTHIC).setName("Hunter XIII"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_witch_30").setRarity(Rarity.RARE).setName("Hunter XIV"));
        this.rewards.add(new AchievementReward("vanilla_rare_kills_zombie_500").setRarity(Rarity.RARE).setName("Hunter I"));

        // Max level
        this.rewards.add(new AchievementReward("vanilla_common_maxlevel_10").setRarity(Rarity.COMMON).setName("Level I"));
        this.rewards.add(new AchievementReward("vanilla_rare_maxlevel_20").setRarity(Rarity.RARE).setName("Level II"));
        this.rewards.add(new AchievementReward("vanilla_epic_maxlevel_40").setRarity(Rarity.EPIC).setName("Level III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_maxlevel_75").setRarity(Rarity.LEGENDARY).setName("Level IV"));
        this.rewards.add(new AchievementReward("vanilla_mythic_maxlevel_120").setRarity(Rarity.MYTHIC).setName("Level V"));

        // Milk
        this.rewards.add(new AchievementReward("vanilla_common_milk_10").setRarity(Rarity.COMMON).setName("Milka I"));
        this.rewards.add(new AchievementReward("vanilla_rare_milk_50").setRarity(Rarity.RARE).setName("Milka II"));
        this.rewards.add(new AchievementReward("vanilla_epic_milk_200").setRarity(Rarity.EPIC).setName("Milka III"));

        // Place
        this.rewards.add(new AchievementReward("vanilla_common_places_acacia_log_100").setRarity(Rarity.COMMON).setName("Woodcutter I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_acacia_log_500").setRarity(Rarity.COMMON).setName("Woodcutter II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_acacia_log_1000").setRarity(Rarity.RARE).setName("Woodcutter III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_acacia_log_2500").setRarity(Rarity.EPIC).setName("Woodcutter IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_acacia_log_5000").setRarity(Rarity.LEGENDARY).setName("Woodcutter V"));
        this.rewards.add(new AchievementReward("vanilla_common_places_acacia_planks_100").setRarity(Rarity.COMMON).setName("Woodmaster I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_acacia_planks_500").setRarity(Rarity.COMMON).setName("Woodmaster II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_acacia_planks_2000").setRarity(Rarity.RARE).setName("Woodmaster III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_acacia_planks_5000").setRarity(Rarity.EPIC).setName("Woodmaster IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_acacia_planks_10000").setRarity(Rarity.LEGENDARY).setName("Woodmaster V"));
        this.rewards.add(new AchievementReward("vanilla_common_places_beetroot_seeds_50").setRarity(Rarity.COMMON).setName("Seeder I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_beetroot_seeds_500").setRarity(Rarity.COMMON).setName("Seeder II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_beetroot_seeds_1000").setRarity(Rarity.RARE).setName("Seeder III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_beetroot_seeds_2000").setRarity(Rarity.EPIC).setName("Seeder IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_beetroot_seeds_5000").setRarity(Rarity.LEGENDARY).setName("Seeder V"));
        this.rewards.add(new AchievementReward("vanilla_common_places_birch_sapling_50").setRarity(Rarity.COMMON).setName("Sampler I"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_birch_sapling_200").setRarity(Rarity.RARE).setName("Sampler II"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_birch_sapling_500").setRarity(Rarity.EPIC).setName("Sampler III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_birch_sapling_1000").setRarity(Rarity.LEGENDARY).setName("Sampler IV"));
        this.rewards.add(new AchievementReward("vanilla_common_places_cactus_10").setRarity(Rarity.COMMON).setName("Farms I"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_cactus_100").setRarity(Rarity.RARE).setName("Farms II"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_cactus_200").setRarity(Rarity.EPIC).setName("Farms III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_cauldron_1").setRarity(Rarity.EPIC).setName("Questions I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_cobblestone_100").setRarity(Rarity.COMMON).setName("StoneEra I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_cobblestone_1000").setRarity(Rarity.COMMON).setName("StoneEra II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_cobblestone_5000").setRarity(Rarity.RARE).setName("StoneEra III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_cobblestone_10000").setRarity(Rarity.EPIC).setName("StoneEra IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_cobblestone_20000").setRarity(Rarity.LEGENDARY).setName("StoneEra V"));
        this.rewards.add(new AchievementReward("vanilla_common_places_dirt_100").setRarity(Rarity.COMMON).setName("Dirtter I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_dirt_500").setRarity(Rarity.COMMON).setName("Dirtter II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_dirt_2000").setRarity(Rarity.RARE).setName("Dirtter III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_dirt_5000").setRarity(Rarity.EPIC).setName("Dirtter IV"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_dirt_10000").setRarity(Rarity.EPIC).setName("Dirtter V"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_dirt_25000").setRarity(Rarity.LEGENDARY).setName("Dirtter VI"));
        this.rewards.add(new AchievementReward("vanilla_common_places_grass_block_100").setRarity(Rarity.COMMON).setName("GrassLand I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_grass_block_500").setRarity(Rarity.COMMON).setName("GrassLand II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_grass_block_2500").setRarity(Rarity.RARE).setName("GrassLand III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_grass_block_5000").setRarity(Rarity.EPIC).setName("GrassLand IV"));
        this.rewards.add(new AchievementReward("vanilla_common_places_stone_50").setRarity(Rarity.COMMON).setName("StoneMaster I"));
        this.rewards.add(new AchievementReward("vanilla_common_places_stone_500").setRarity(Rarity.COMMON).setName("StoneMaster II"));
        this.rewards.add(new AchievementReward("vanilla_rare_places_stone_2500").setRarity(Rarity.RARE).setName("StoneMaster III"));
        this.rewards.add(new AchievementReward("vanilla_epic_places_stone_8000").setRarity(Rarity.EPIC).setName("StoneMaster IV"));
        this.rewards.add(new AchievementReward("vanilla_legendary_places_stone_15000").setRarity(Rarity.LEGENDARY).setName("StoneMaster V"));

        // Played time
        this.rewards.add(new AchievementReward("vanilla_common_playedtime_1").setRarity(Rarity.COMMON).setName("První hodina"));
        this.rewards.add(new AchievementReward("vanilla_rare_playedtime_12").setRarity(Rarity.RARE).setName("Půl dne online"));
        this.rewards.add(new AchievementReward("vanilla_epic_playedtime_24").setRarity(Rarity.EPIC).setName("Celý den online"));
        this.rewards.add(new AchievementReward("vanilla_legendary_playedtime_168").setRarity(Rarity.LEGENDARY).setName("Týden online"));
        this.rewards.add(new AchievementReward("vanilla_mythic_playedtime_720").setRarity(Rarity.MYTHIC).setName("Měsíc online"));

        // Shear
        this.rewards.add(new AchievementReward("vanilla_common_shear_10").setRarity(Rarity.COMMON).setName("Shear I"));
        this.rewards.add(new AchievementReward("vanilla_rare_shear_50").setRarity(Rarity.RARE).setName("Shear II"));
        this.rewards.add(new AchievementReward("vanilla_epic_shear_200").setRarity(Rarity.EPIC).setName("Shear III"));

        // Trades
        this.rewards.add(new AchievementReward("vanilla_common_trades_1").setRarity(Rarity.COMMON).setName("Trader I"));
        this.rewards.add(new AchievementReward("vanilla_rare_trades_30").setRarity(Rarity.RARE).setName("Trader II"));
        this.rewards.add(new AchievementReward("vanilla_epic_trades_100").setRarity(Rarity.EPIC).setName("Trader III"));
        this.rewards.add(new AchievementReward("vanilla_legendary_trades_300").setRarity(Rarity.LEGENDARY).setName("Trader IV"));

        // Treasures
        this.rewards.add(new AchievementReward("vanilla_rare_treasures_10").setRarity(Rarity.RARE).setName("Treasures I"));
        this.rewards.add(new AchievementReward("vanilla_epic_treasures_30").setRarity(Rarity.EPIC).setName("Treasures II"));*/
    }
}
