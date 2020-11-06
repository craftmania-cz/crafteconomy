package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.achievements.servers.*;
import cz.craftmania.crafteconomy.objects.QuestReward;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.rewards.*;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;

import java.util.ArrayList;
import java.util.List;

/**
 * ProprietaryManager spravuje načtení achievementů a servers rewards
 */
public class ProprietaryManager {

    public static List<QuestReward> serverAchievementList = new ArrayList<>();
    public static List<LevelReward> serverLevelRewardsList = new ArrayList<>();

    /**
     * Načte všechny achievementy dle {@link ServerType}
     */
    public static void loadServerAchievements() {
        Logger.info("Priprava nacteni achievementu.");
        //new GlobalAchievements(serverAchievementList).load(); //TODO: Přesunout do speciální kategorie
        if (Main.getServerType() == ServerType.SURVIVAL) {
            new SurvivalAchievements(serverAchievementList).load();
        } else if (Main.getServerType() == ServerType.SKYBLOCK) {
            new SkyblockAchievements(serverAchievementList).load();
        } else if (Main.getServerType() == ServerType.CREATIVE) {
            new CreativeAchievements(serverAchievementList).load();
        } else if (Main.getServerType() == ServerType.PRISON) {
            Logger.danger("Prison nema nastavene zadne achievementy!");
        } else if (Main.getServerType() == ServerType.SKYCLOUD) {
            new SkycloudAchievements(serverAchievementList).load();
        } else if (Main.getServerType() == ServerType.VANILLA) {
            new VanillaAchievements(serverAchievementList).load();
        } else {
            Logger.danger("Nenalezeny zadne achievementy k nacteni pro konkrétní server!");
            Logger.info("Mohou byt ale pridany globalni nebo specialni achievementy!");
        }
        Logger.success("Celkove nacteno: " + serverAchievementList.size() + " achievementu!");
    }

    /**
     * Vrací list všech achievementů načtených v cache
     * @return List {@link QuestReward}
     */
    public static List<QuestReward> getServerAchievementList() {
        return serverAchievementList;
    }

    /**
     * Načte všechny server rewards dle {@link ServerType}
     */
    public static void loadServerLevelRewards() {
        Logger.info("Priprava nacteni level odmen.");
        if (Main.getServerType() == ServerType.SURVIVAL) {
            new SurvivalLevelRewards(serverLevelRewardsList).load();
        } else if (Main.getServerType() == ServerType.SKYBLOCK) {
            new SkyblockLevelRewards(serverLevelRewardsList).load();
        } else if (Main.getServerType() == ServerType.CREATIVE) {
            new CreativeLevelReward(serverLevelRewardsList).load();
        } else if (Main.getServerType() == ServerType.PRISON) {
            Logger.danger("Prison nema nastavene zadne server odmeny!");
        } else if (Main.getServerType() == ServerType.SKYCLOUD) {
            new SkycloudLevelRewards(serverLevelRewardsList).load();
        } else if (Main.getServerType() == ServerType.VANILLA) {
            new VanillaLevelRewards(serverLevelRewardsList).load();
        } else {
            Logger.danger("Nenalezeny zadne odmeny k nacteni!");
        }
        Logger.success("Celkove nacteno: " + serverLevelRewardsList.size() + " server odmen!");
    }

    /**
     * Vrací list všech rewards načtených v cache
     * @return List {@link LevelReward}
     */
    public static List<LevelReward> getServerLevelRewardsList() {
        return serverLevelRewardsList;
    }
}
