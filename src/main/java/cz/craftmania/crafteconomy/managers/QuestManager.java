package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.QuestReward;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.configs.Config;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {

    public static List<QuestReward> questRewards = new ArrayList<>();

    public static void loadQuests() {
        Logger.info("Příprava načtení questů.");
        Config questConfig = Main.getInstance().getConfigAPI().getConfig("quests");
        if (questConfig == null) {
            Logger.danger("Quest config se nepodařilo načíst.");
            return;
        }

        ConfigurationSection questSection = questConfig.getConfig().getConfigurationSection("quests");
        for (String key : questSection.getKeys(false)) {
            ConfigurationSection quest = questSection.getConfigurationSection(key);
            QuestReward questReward = new QuestReward(key);
            questReward.setName(quest.getString("name"));
            questReward.setDescription(quest.getString("goal"));
            questReward.setRarity(Rarity.valueOf(quest.getString("rarity")));
            if (quest.contains("points")) {
                questReward.overrideQuestValue(quest.getInt("points"));
            }
            if (quest.contains("experience")) {
                questReward.overrideExperienceValue(quest.getInt("experience"));
            }
            if (quest.contains("permissions")) {
                questReward.setPermissions(quest.getStringList("permissions"));
            }

            questRewards.add(questReward);
            Logger.debug("Quest zaregistrovan: " + questReward.getName() + ", id: " + questReward.getId() + ", desc: " + questReward.getDescription() + ", rarity: "
                    + questReward.getRarity().name() + ", qp: " + questReward.getQuestPointsValue() + ", exp: " + questReward.getExperienceValue() + ", perms: "
                    + questReward.getPermissions());
        }
        Logger.success("Celkově načteno (" + questRewards.size() + ") questů.");
    }

    public static List<QuestReward> getQuestRewards() {
        return questRewards;
    }
}
