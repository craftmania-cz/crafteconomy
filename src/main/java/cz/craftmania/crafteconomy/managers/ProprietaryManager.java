package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.achievements.servers.CreativeAchievements;
import cz.craftmania.crafteconomy.achievements.servers.SkycloudAchievements;
import cz.craftmania.crafteconomy.achievements.servers.SurvivalAchievements;
import cz.craftmania.crafteconomy.achievements.servers.VanillaAchievements;
import cz.craftmania.crafteconomy.objects.AchievementReward;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.rewards.CreativeLevelReward;
import cz.craftmania.crafteconomy.rewards.VanillaLevelRewards;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;

import java.util.ArrayList;
import java.util.List;

public class ProprietaryManager {

    public static List<AchievementReward> serverAchievementList = new ArrayList<>();
    public static List<LevelReward> serverLevelRewardsList = new ArrayList<>();

    public static void loadServerAchievements() {
        Logger.info("Priprava nacteni achievementu.");
        //new GlobalAchievements(serverAchievementList).load(); //TODO: Přesunout do speciální kategorie
        if (Main.getServerType() == ServerType.SURVIVAL) {
            new SurvivalAchievements(serverAchievementList).load();
        } else if (Main.getServerType() == ServerType.SKYBLOCK) {
            Logger.danger("Skyblock nema nastavene zadne achievementy!");
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
            new VanillaLevelRewards(serverLevelRewardsList).load();
        } else {
            Logger.danger("Nenalezeny zadne odmeny k nacteni!");
        }
        Logger.success("Celkove nacteno: " + serverLevelRewardsList.size() + " server odmen!");
    }

    public static List<LevelReward> getServerLevelRewardsList() {
        return serverLevelRewardsList;
    }
}
