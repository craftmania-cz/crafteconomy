package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.configs.Config;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class RewardManager {

    public static List<LevelReward> rewards = new ArrayList<>();

    public static void loadRewards() {

        Logger.info("Příprava načtení server rewards.");
        Config questConfig = Main.getInstance().getConfigAPI().getConfig("rewards");
        if (questConfig == null) {
            Logger.danger("Rewards config se nepodařilo načíst.");
            return;
        }

        ConfigurationSection rewardsSection = questConfig.getConfig().getConfigurationSection("rewards");
        for (String key : rewardsSection.getKeys(false)) {
            ConfigurationSection rewardKey = rewardsSection.getConfigurationSection(key);
            LevelReward levelReward = new LevelReward(Integer.parseInt(key));
            levelReward.setName(rewardKey.getString("name"));
            levelReward.setDescription(rewardKey.getStringList("description"));
            levelReward.setRewardDescription(rewardKey.getStringList("reward_description"));
            levelReward.setPermissions(rewardKey.getStringList("permissions"));

            rewards.add(levelReward);
            Logger.debug("Server reward zaregistrovan: " + levelReward.getName() + ", level: " + levelReward.getLevel() +
                    ", desc: " + levelReward.getDescription() + ", perms: " + levelReward.getPermissions());
        }

        Logger.success("Celkově načteno (" + rewards.size() + ") server odměn.");
    }

    public static List<LevelReward> getRewards() {
        return rewards;
    }
}
