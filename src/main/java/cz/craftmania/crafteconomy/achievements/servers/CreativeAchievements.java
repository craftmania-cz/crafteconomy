package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.objects.AchievementReward;

import java.util.List;

public class CreativeAchievements {

    private List<AchievementReward> rewards;

    public CreativeAchievements(List<AchievementReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {
        this.rewards.add(new AchievementReward("common_breaks_bedrock_1").setRarity(Rarity.COMMON).setName("Bedrocker"));
        this.rewards.add(new AchievementReward("rare_breaks_grass_500").setRarity(Rarity.RARE).setName("Survival"));
        this.rewards.add(new AchievementReward("rare_connections_1").setRarity(Rarity.RARE).overrideExperienceValue(1000).setName("Vítej na Creativu!"));
        this.rewards.add(new AchievementReward("common_custom_playedtime_creative_1").setRarity(Rarity.COMMON).setName("První hodina"));
        this.rewards.add(new AchievementReward("rare_custom_playedtime_creative_12").setRarity(Rarity.RARE).setName("Půl dne online"));
        this.rewards.add(new AchievementReward("epic_custom_playedtime_creative_24").setRarity(Rarity.EPIC).setName("Celý den online"));
        this.rewards.add(new AchievementReward("legendary_custom_playedtime_creative_168").setRarity(Rarity.LEGENDARY).setName("Týden online"));
        this.rewards.add(new AchievementReward("legendary_custom_playedtime_creative_720").setRarity(Rarity.LEGENDARY).setName("Měsíc online"));
        this.rewards.add(new AchievementReward("common_deaths_1").setRarity(Rarity.COMMON).setName("Undead I"));
        this.rewards.add(new AchievementReward("epic_deaths_100").setRarity(Rarity.EPIC).setName("Undead II"));
        this.rewards.add(new AchievementReward("epic_distancehorse_10000").setRarity(Rarity.EPIC).setName("Horse Distance"));
        this.rewards.add(new AchievementReward("epic_distancepig_1000").setRarity(Rarity.EPIC).setName("Pig Distance"));
        this.rewards.add(new AchievementReward("common_fireworks_50").setRarity(Rarity.COMMON).setName("Pyrotechnic"));
        this.rewards.add(new AchievementReward("common_musicdiscs_1").setRarity(Rarity.COMMON).setName("Jukeboxer"));
        this.rewards.add(new AchievementReward("common_places_grass_500").setRarity(Rarity.COMMON).setName("Grass Master"));
        this.rewards.add(new AchievementReward("common_places_stone_50").setRarity(Rarity.COMMON).setName("Place Stone"));
        this.rewards.add(new AchievementReward("common_places_stone_500").setRarity(Rarity.COMMON).setName("Více Stonuuu"));
        this.rewards.add(new AchievementReward("rare_places_stone_5000").setRarity(Rarity.RARE).setName("Stone Master"));
        this.rewards.add(new AchievementReward("rare_places_tnt_1").setRarity(Rarity.RARE).setName("Silent Explosion"));

    }
}
