package cz.craftmania.crafteconomy.objects;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelReward {

    private int level;
    private String name = "[Unknown]";
    private List<String> description = new ArrayList<>();
    private List<String> rewardDescription = new ArrayList<>();
    private boolean requireSlotInInventory = false;
    private List<ItemStack> items = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    public LevelReward(final int level) {
        this.level = level;
        this.name = "[Unknown_" + level + "]";
    }

    public LevelReward setName(@NonNull final String name) {
        this.name = name;
        return this;
    }

    public LevelReward setDescription(final @NotNull List<String> description) {
        this.description = description;
        return this;
    }

    public LevelReward setRewardDescription(final @NotNull List<String> description) {
        this.rewardDescription = description;
        return this;
    }

    public LevelReward setRequiareSlotInInventory() {
        this.requireSlotInInventory = true;
        return this;
    }

    public LevelReward setItems(@NonNull final ItemStack... items) {
        this.requireSlotInInventory = true;
        Collections.addAll(this.items, items);
        return this;
    }

    public LevelReward addItem(@NotNull final ItemStack itemStack) {
        this.requireSlotInInventory = true;
        this.items.add(itemStack);
        return this;
    }

    public LevelReward setPermissions(final @NotNull List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public List<String> getRewardDescription() {
        return rewardDescription;
    }

    public boolean isRequireSlotInInventory() {
        return requireSlotInInventory;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
