package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.achievements.Reward;

import java.util.List;

public class CreativeAchievements {

    private List<Reward> rewards;

    public CreativeAchievements(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public void load() {
        this.rewards.add(new Reward("common_breaks_bedrock_1").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("rare_breaks_grass_500").setRarity(Rarity.RARE));
        this.rewards.add(new Reward("rare_connections_1").setRarity(Rarity.RARE).overrideExperienceValue(1000));
        this.rewards.add(new Reward("common_custom_playedtime_creative_1").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("rare_custom_playedtime_creative_12").setRarity(Rarity.RARE));
        this.rewards.add(new Reward("epic_custom_playedtime_creative_24").setRarity(Rarity.EPIC));
        this.rewards.add(new Reward("legendary_custom_playedtime_creative_168").setRarity(Rarity.LEGENDARY));
        this.rewards.add(new Reward("legendary_custom_playedtime_creative_720").setRarity(Rarity.LEGENDARY));
        this.rewards.add(new Reward("common_deaths_1").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("epic_deaths_100").setRarity(Rarity.EPIC));
        this.rewards.add(new Reward("epic_distancehorse_10000").setRarity(Rarity.EPIC));
        this.rewards.add(new Reward("epic_distancepig_1000").setRarity(Rarity.EPIC));
        this.rewards.add(new Reward("common_fireworks_50").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("common_musicdiscs_1").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("common_places_grass_500").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("common_places_stone_50").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("common_places_stone_500").setRarity(Rarity.COMMON));
        this.rewards.add(new Reward("rare_places_stone_5000").setRarity(Rarity.RARE));
        this.rewards.add(new Reward("rare_places_tnt_1").setRarity(Rarity.RARE));

    }
}
