package cz.craftmania.crafteconomy.achievements;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reward {

    private int level;
    private String id;
    private String name = "[Unknown]";
    private Rarity rarity = Rarity.COMMON;
    private List<String> description = new ArrayList<>();
    private boolean requireSlotInInventory = false;
    private int achievementValue = 0;
    private int experienceValue = 0;
    private List<ItemStack> items = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    public Reward(){};

    public Reward(final int level) {
        this.level = level;
        this.id = "unknown_" + level;
    }

    public Reward(final String id) {
        this.level = 0;
        this.id = id;
    }

    public Reward(final int level, final String id) {
        this.level = level;
        this.id = id;
    }

    public Reward setName(final String name) {
        this.name = name;
        return this;
    }

    public Reward setDescription(final String... description) {
        Collections.addAll(this.description, description);
        return this;
    }

    public Reward setRequiareSlotInInventory() {
        this.requireSlotInInventory = true;
        return this;
    }

    public Reward overrideAchievementValue(final int value) {
        this.achievementValue = value;
        return this;
    }

    public Reward setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public Reward overrideExperienceValue(final int experienceValue) {
        this.experienceValue = experienceValue;
        return this;
    }

    public Reward setItems(final ItemStack... items) {
        Collections.addAll(this.items, items);
        return this;
    }

    public Reward setPermissions(final String... permissions) {
        Collections.addAll(this.permissions, permissions);
        return this;
    }

    public int getLevel() {
        return level;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public boolean isRequireSlotInInventory() {
        return requireSlotInInventory;
    }

    public Rarity getRarity() {
        return rarity;
    }

    /**
     * Metoda, ktera vrací hodnoty AchievementPoints, které dostane hráč za splnění achievementu.
     * @return Hodnota AchievementPoints
     */
    public int getAchievementValue() {

        // Override
        if (achievementValue > 0) {
            return achievementValue;
        }

        // Pokud neni override, tak podle rarity
        if (rarity == Rarity.COMMON) {
            return 5;
        }
        if (rarity == Rarity.RARE) {
            return 7;
        }
        if (rarity == Rarity.EPIC) {
            return 10;
        }
        if (rarity == Rarity.LEGENDARY) {
            return 15;
        }
        if (rarity == Rarity.MYTHIC) {
            return 20;
        }
        if (rarity == Rarity.SECRET) {
            return 30;
        }
        return 0;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public int getExperienceValue() {

        // Override
        if (experienceValue > 0) {
            return experienceValue;
        }

        // Pokud neni override, tak podle rarity
        if (rarity == Rarity.COMMON) {
            return 150;
        }
        if (rarity == Rarity.RARE) {
            return 300;
        }
        if (rarity == Rarity.EPIC) {
            return 600;
        }
        if (rarity == Rarity.LEGENDARY) {
            return 1500;
        }
        if (rarity == Rarity.MYTHIC) {
            return 5000;
        }
        if (rarity == Rarity.SECRET) {
            return 7500;
        }
        return 0;
    }


}
