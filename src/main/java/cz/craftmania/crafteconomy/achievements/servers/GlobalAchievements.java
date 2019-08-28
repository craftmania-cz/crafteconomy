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
        this.rewards.add(new AchievementReward("common_custom_bugs_1").setRarity(Rarity.COMMON));
        this.rewards.add(new AchievementReward("rare_custom_bugs_2").setRarity(Rarity.RARE));
        this.rewards.add(new AchievementReward("epic_custom_bugs_5").setRarity(Rarity.EPIC));
        this.rewards.add(new AchievementReward("legendary_custom_bugs_10").setRarity(Rarity.LEGENDARY));
        this.rewards.add(new AchievementReward("mythic_custom_bugs_20").setRarity(Rarity.MYTHIC));
        this.rewards.add(new AchievementReward("common_custom_votes_1").setRarity(Rarity.COMMON));
        this.rewards.add(new AchievementReward("common_custom_votes_50").setRarity(Rarity.COMMON));
        this.rewards.add(new AchievementReward("rare_custom_votes_250").setRarity(Rarity.RARE));
        this.rewards.add(new AchievementReward("epic_custom_votes_1000").setRarity(Rarity.EPIC));
        this.rewards.add(new AchievementReward("legendary_custom_votes_10000").setRarity(Rarity.LEGENDARY));
        this.rewards.add(new AchievementReward("mythic_custom_votes_100000").setRarity(Rarity.MYTHIC));
    }
}
