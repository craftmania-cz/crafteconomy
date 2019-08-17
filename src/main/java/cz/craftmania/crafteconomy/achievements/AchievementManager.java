package cz.craftmania.crafteconomy.achievements;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.achievements.servers.CreativeAchievements;
import cz.craftmania.crafteconomy.achievements.servers.GlobalAchievements;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {

    public static List<Reward> serverAchievementList = new ArrayList<>();

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

    public static List<Reward> getServerAchievementList() {
        return serverAchievementList;
    }
}
