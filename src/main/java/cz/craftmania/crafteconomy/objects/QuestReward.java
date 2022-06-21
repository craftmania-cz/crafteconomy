package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.managers.Rarity;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestReward {

    private String id;
    private String name = "[Unknown]";
    private Rarity rarity = Rarity.COMMON;
    private List<String> description = new ArrayList<>();
    private boolean requireSlotInInventory = false;
    private int questPointsValue = 0;
    private int experienceValue = 0;
    private List<String> permissions = new ArrayList<>();
    private List<String> commands = new ArrayList<>();

    private boolean useOverrideExperienceValue = false;
    private boolean useOverrideAchievementValue = false;

    public QuestReward(){};

    public QuestReward(@NonNull final String id) {
        this.id = id;
    }

    public QuestReward setName(@NonNull final String name) {
        this.name = name;
        return this;
    }

    public QuestReward setDescription(@NonNull final String... description) {
        Collections.addAll(this.description, description);
        return this;
    }

    public QuestReward setRequiareSlotInInventory() {
        this.requireSlotInInventory = true;
        return this;
    }

    public QuestReward overrideQuestValue(final int value) {
        this.useOverrideAchievementValue = true;
        this.questPointsValue = value;
        return this;
    }

    public QuestReward setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public QuestReward overrideExperienceValue(final int experienceValue) {
        this.useOverrideExperienceValue = true;
        this.experienceValue = experienceValue;
        return this;
    }

    public QuestReward setPermissions(final @NotNull List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public QuestReward setCommands(final @NotNull List<String> commands) {
        this.commands = commands;
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

    public List<String> getCommands() {
        return commands;
    }

    /**
     * Metoda, ktera vrací hodnoty AchievementPoints, které dostane hráč za splnění achievementu.
     * @return Hodnota AchievementPoints
     */
    public int getQuestPointsValue() {

        // Override
        if (this.useOverrideAchievementValue) {
            return questPointsValue;
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
            return 300;
        }
        if (rarity == Rarity.RARE) {
            return 500;
        }
        if (rarity == Rarity.EPIC) {
            return 1000;
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
