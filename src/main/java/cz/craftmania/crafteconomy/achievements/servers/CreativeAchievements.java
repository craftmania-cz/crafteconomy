package cz.craftmania.crafteconomy.achievements.servers;

import cz.craftmania.crafteconomy.achievements.Reward;
import cz.craftmania.crafteconomy.utils.Logger;

import java.util.List;

public class CreativeAchievements {

    private List<Reward> rewards;

    public CreativeAchievements(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public void load() {

        Logger.info("Priprava nacteni achievementu.");

        Reward break_10_ice = new Reward("break_10_ice").setAchievementValue(20).setDescription("Výborně rozbil jsi 10 Ice Bloků!").setName("Cold Drink Supplier");
        rewards.add(break_10_ice);

        Logger.success("Celkove nacteno: " + this.rewards.size() + " achievementu!");

    }
}
