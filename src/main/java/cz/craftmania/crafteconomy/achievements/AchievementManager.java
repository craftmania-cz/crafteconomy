package cz.craftmania.crafteconomy.achievements;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.achievements.servers.CreativeAchievements;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {

    public static List<Reward> serverAchievementList = new ArrayList<>();

    public static void loadServerAchievements() {
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
    }

    public static List<Reward> getServerAchievementList() {
        return serverAchievementList;
    }
}
