package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.achievements.Rarity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AchievementReward {

    private String id;
    private String name = "[Unknown]";
    private Rarity rarity = Rarity.COMMON;
    private List<String> description = new ArrayList<>();
    private boolean requireSlotInInventory = false;
    private int achievementValue = 0;
    private int experienceValue = 0;
    private List<ItemStack> items = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    private boolean useOverrideExperienceValue = false;
    private boolean useOverrideAchievementValue = false;

    public AchievementReward(){};

    public AchievementReward(final String id) {
        this.id = id;
    }

    public AchievementReward setName(final String name) {
        this.name = name;
        return this;
    }

    public AchievementReward setDescription(final String... description) {
        Collections.addAll(this.description, description);
        return this;
    }

    public AchievementReward setRequiareSlotInInventory() {
        this.requireSlotInInventory = true;
        return this;
    }

    public AchievementReward overrideAchievementValue(final int value) {
        this.useOverrideAchievementValue = true;
        this.achievementValue = value;
        return this;
    }

    public AchievementReward setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public AchievementReward overrideExperienceValue(final int experienceValue) {
        this.useOverrideExperienceValue = true;
        this.experienceValue = experienceValue;
        return this;
    }

    public AchievementReward setItems(final ItemStack... items) {
        Collections.addAll(this.items, items);
        return this;
    }

    public AchievementReward setPermissions(final String... permissions) {
        Collections.addAll(this.permissions, permissions);
        return this;
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
        if (this.useOverrideAchievementValue) {
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

    /**
     * Vrací seznam všech permissions, které se aktivují hráči
     * @return List permissí
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * Metoda která vrací experience value podle Rarity
     * @return Hodnota expů, kterou dostane hráč na serveru
     */
    public int getExperienceValue() {

        // Override
        if (this.useOverrideExperienceValue) {
            return experienceValue;
        }

        // Pokud neni override, tak podle rarity
        if (rarity == Rarity.COMMON) {
            return 500;
        }
        if (rarity == Rarity.RARE) {
            return 750;
        }
        if (rarity == Rarity.EPIC) {
            return 1500;
        }
        if (rarity == Rarity.LEGENDARY) {
            return 3000;
        }
        if (rarity == Rarity.MYTHIC) {
            return 10000;
        }
        if (rarity == Rarity.SECRET) {
            return 15000;
        }
        return 0;
    }


}
