package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.objects.AchievementReward;

import java.util.List;

public class HalloweenAchievements {

    private List<AchievementReward> rewards;

    public HalloweenAchievements(List<AchievementReward> rewards) {
        this.rewards = rewards;
    }

    /*
        Všechny achievementy mají nastavený overrideExperienceValue(0) - jedná se o speciální achievementy, expy by neměly kam jít.
     */

    public void load() {

        // Connect
        this.rewards.add(new AchievementReward("halloween_19_rare_global_connect_1").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("Halloween je tu!"));

        // Monsters
        this.rewards.add(new AchievementReward("halloween_19_rare_global_monsters_10").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("MonsterHunter I"));
        this.rewards.add(new AchievementReward("halloween_19_rare_global_monsters_50").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("MonsterHunter II"));
        this.rewards.add(new AchievementReward("halloween_19_epic_global_monsters_100").setRarity(Rarity.EPIC).overrideExperienceValue(0).setName("MonsterHunter III"));
        this.rewards.add(new AchievementReward("halloween_19_epic_global_monsters_250").setRarity(Rarity.EPIC).overrideExperienceValue(0).setName("MonsterHunter IV"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_monsters_500").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("MonsterHunter V"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_monsters_1000").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("MonsterHunter VI"));

        // Golds
        this.rewards.add(new AchievementReward("halloween_19_rare_global_golds_500").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("GoldDigger I"));
        this.rewards.add(new AchievementReward("halloween_19_epic_global_golds_2000").setRarity(Rarity.EPIC).overrideExperienceValue(0).setName("GoldDigger II"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_golds_5000").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("GoldDigger III"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_golds_10000").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("GoldDigger IV"));

        // Played
        this.rewards.add(new AchievementReward("halloween_19_rare_global_played_5").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("Play this game I"));
        this.rewards.add(new AchievementReward("halloween_19_rare_global_played_10").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("Play this game II"));
        this.rewards.add(new AchievementReward("halloween_19_epic_global_played_20").setRarity(Rarity.EPIC).overrideExperienceValue(0).setName("Play this game III"));
        this.rewards.add(new AchievementReward("halloween_19_epic_global_played_40").setRarity(Rarity.EPIC).overrideExperienceValue(0).setName("Play this game IV"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_played_80").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("Play this game V"));

        // Waves
        this.rewards.add(new AchievementReward("halloween_19_common_global_wave_5").setRarity(Rarity.COMMON).overrideExperienceValue(0).setName("Survival I"));
        this.rewards.add(new AchievementReward("halloween_19_common_global_wave_10").setRarity(Rarity.COMMON).overrideExperienceValue(0).setName("Survival II"));
        this.rewards.add(new AchievementReward("halloween_19_rare_global_wave_15").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("Survival III"));
        this.rewards.add(new AchievementReward("halloween_19_rare_global_wave_20").setRarity(Rarity.RARE).overrideExperienceValue(0).setName("Survival IV"));
        this.rewards.add(new AchievementReward("halloween_19_epic_global_wave_25").setRarity(Rarity.EPIC).overrideExperienceValue(0).setName("Survival V"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_wave_30").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("Survival VI"));
        this.rewards.add(new AchievementReward("halloween_19_legendary_global_wave_35").setRarity(Rarity.LEGENDARY).overrideExperienceValue(0).setName("Survival VII"));
        this.rewards.add(new AchievementReward("halloween_19_mythic_global_wave_40").setRarity(Rarity.MYTHIC).overrideExperienceValue(0).setName("Survival VIII"));
        this.rewards.add(new AchievementReward("halloween_19_mythic_global_wave_45").setRarity(Rarity.MYTHIC).overrideExperienceValue(0).setName("Survival IX"));
        this.rewards.add(new AchievementReward("halloween_19_mythic_global_wave_50").setRarity(Rarity.MYTHIC).overrideExperienceValue(0).setName("Survival X"));

    }
}
