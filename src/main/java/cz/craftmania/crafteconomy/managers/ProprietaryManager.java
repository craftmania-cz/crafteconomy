package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.achievements.servers.CreativeAchievements;
import cz.craftmania.crafteconomy.achievements.servers.GlobalAchievements;
import cz.craftmania.crafteconomy.objects.AchievementReward;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.rewards.CreativeLevelReward;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;

import java.util.ArrayList;
import java.util.List;

public class ProprietaryManager {

    public static List<AchievementReward> serverAchievementList = new ArrayList<>();
    public static List<LevelReward> serverLevelRewardsList = new ArrayList<>();

    public static void loadServerAchievements() {
        Logger.info("Priprava nacteni achievementu.");
        new GlobalAchievements(serverAchievementList).load();
        if (Main.getServerType() == ServerType.SURVIVAL) {
            Logger.danger("Survival nema nastavene zadne achievementy!");
        } else if (Main.getServerType() == ServerType.SKYBLOCK) {
            Logger.danger("Skyblock nema nastavene zadne achievementy!");
        } else if (Main.getServerType() == ServerType.CREATIVE) {
            new CreativeAchievements(serverAchievementList).load();
        } else if (Main.getServerType() == ServerType.PRISON) {
            Logger.danger("Prison nema nastavene zadne achievementy!");
        } else if (Main.getServerType() == ServerType.SKYCLOUD) {
            Logger.danger("Skycloud nema nastavene zadne achievementy!");
        } else if (Main.getServerType() == ServerType.VANILLA) {
            Logger.danger("Vanilla nema nastavene zadne achievementy!");
        } else {
            Logger.danger("Nenalezeny zadne achievementy k nacteni!");
        }
        Logger.success("Celkove nacteno: " + serverAchievementList.size() + " achievementu!");
    }

    public static List<AchievementReward> getServerAchievementList() {
        return serverAchievementList;
    }

    public static void loadServerLevelRewards() {
        Logger.info("Priprava nacteni level odmen.");
        if (Main.getServerType() == ServerType.SURVIVAL) {
            Logger.danger("Survival nema nastavene zadne server odmeny!");
        } else if (Main.getServerType() == ServerType.SKYBLOCK) {
            Logger.danger("Skyblock nema nastavene zadne server odmeny!");
        } else if (Main.getServerType() == ServerType.CREATIVE) {
            new CreativeLevelReward(serverLevelRewardsList).load();
        } else if (Main.getServerType() == ServerType.PRISON) {
            Logger.danger("Prison nema nastavene zadne server odmeny!");
        } else if (Main.getServerType() == ServerType.SKYCLOUD) {
            Logger.danger("Skycloud nema nastavene zadne server odmeny!");
        } else if (Main.getServerType() == ServerType.VANILLA) {
            Logger.danger("Vanilla nema nastavene zadne server odmeny!");
        } else {
            Logger.danger("Nenalezeny zadne achievementy k nacteni!");
        }
        Logger.success("Celkove nacteno: " + serverLevelRewardsList.size() + " server odmen!");
    }

    public static List<LevelReward> getServerLevelRewardsList() {
        return serverLevelRewardsList;
    }
}
