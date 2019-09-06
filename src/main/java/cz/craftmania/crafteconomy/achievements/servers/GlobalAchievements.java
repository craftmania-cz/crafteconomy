package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Rarity;
import cz.craftmania.crafteconomy.objects.AchievementReward;

import java.util.List;

public class GlobalAchievements {

    private List<AchievementReward> rewards;

    public GlobalAchievements(List<AchievementReward> rewards) {
        this.rewards = rewards;
    }

    public void load() {
        // Bug achievementy
        this.rewards.add(new AchievementReward("common_custom_bugs_1").setRarity(Rarity.COMMON).setName("Debugger I"));
        this.rewards.add(new AchievementReward("rare_custom_bugs_2").setRarity(Rarity.RARE).setName("Debugger II"));
        this.rewards.add(new AchievementReward("epic_custom_bugs_5").setRarity(Rarity.EPIC).setName("Debugger III"));
        this.rewards.add(new AchievementReward("legendary_custom_bugs_10").setRarity(Rarity.LEGENDARY).setName("Debugger IV"));
        this.rewards.add(new AchievementReward("mythic_custom_bugs_20").setRarity(Rarity.MYTHIC).setName("Master Debugger"));

        // Vote achievementy
        //TODO: Funkčnost
        this.rewards.add(new AchievementReward("common_custom_votes_1").setRarity(Rarity.COMMON).setName("Fanoušek serveru"));
        this.rewards.add(new AchievementReward("common_custom_votes_50").setRarity(Rarity.COMMON).setName("Velký fanda"));
        this.rewards.add(new AchievementReward("rare_custom_votes_250").setRarity(Rarity.RARE).setName("Větší fanda"));
        this.rewards.add(new AchievementReward("epic_custom_votes_1000").setRarity(Rarity.EPIC).setName("Epický fanoušek"));
        this.rewards.add(new AchievementReward("legendary_custom_votes_10000").setRarity(Rarity.LEGENDARY).setName("Legendární fanoušek"));
        this.rewards.add(new AchievementReward("mythic_custom_votes_100000").setRarity(Rarity.MYTHIC).setName("Pravá legenda!"));
    }
}
