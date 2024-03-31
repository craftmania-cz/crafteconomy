package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.builders.items.ItemBuilder;
import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.craftcore.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.inventory.builder.content.InventoryProvider;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.FormatUtils;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.craftlibs.utils.ServerColors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LevelGUI implements InventoryProvider {

    private static BasicManager manager = new BasicManager();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        CraftPlayer craftPlayer = manager.getCraftPlayer(player);

        assert craftPlayer != null;
        ItemStack playerHead = new ItemBuilder(Material.PLAYER_HEAD)
                .setCustomModelData(500)
                .setSkullOwner(player)
                .setName(ServerColors.SERVER_CREATIVE.getChatColor() + "Tvůj profil")
                .setLore(
                        "§7Globální level: §f" + craftPlayer.getLevelByType(LevelType.GLOBAL_LEVEL),
                        "§7Karma: §f" + craftPlayer.getEconomyByType(EconomyType.KARMA_POINTS),
                        "§7Quest Points: §f" + craftPlayer.getEconomyByType(EconomyType.QUEST_POINTS),
                        "",
                        "§e[LTM] Získání odkazu na profil"
                ).build();
        inventoryContents.set(0, 4, ClickableItem.of(playerHead, (inventoryClickEvent -> {
            player.sendMessage("");
            player.sendMessage("§b§lOdkaz na tvůj profil:");
            player.sendMessage("§fhttps://stats.craftmania.cz/player/" + player.getName());
            player.sendMessage("");
            player.closeInventory();
        })));

        /**
         * Survival Icon
         */
        LevelType preferredSurvivalLevelType = LevelType.SURVIVAL_117_LEVEL;
        LevelType preferredSurvivalExpType = LevelType.SURVIVAL_117_EXPERIENCE;;

        long survLevel = craftPlayer.getLevelByType(preferredSurvivalLevelType);
        long survivalTotalExperience = craftPlayer.getExperienceByType(preferredSurvivalExpType);
        double survivalTotalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(preferredSurvivalLevelType));
        double percentage = FormatUtils.roundDouble((survivalTotalExperience/survivalTotalExperienceForNextLevel)*100, 3);

        ItemStack survival = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .setName(ServerColors.SERVER_SURVIVAL.getChatColor() + "Survival")
                .hideAllFlags()
                .setLore(
                        "§7Level: §f" + survLevel,
                        "§7Další level: §f" + survivalTotalExperience + "/" + Math.round(survivalTotalExperienceForNextLevel) + " EXP",
                        "§7Procentuálně: ",
                        getPercentageBar(percentage) + " §7[" + percentage + "%]"

                ).build();
        if (Main.getServerType() == ServerType.SURVIVAL_117 || Main.getServerType() == ServerType.SURVIVAL_118) {
            ItemStack modifiedSurvivalItem = new ItemBuilder(survival).addLoreLine("").addLoreLine("§e[LTM] Zobrazení odměn za level").build();
            inventoryContents.set(2, 3, ClickableItem.of(modifiedSurvivalItem, inventoryClickEvent -> {
                SmartInventory.builder().size(6, 9).title("Level rewards").provider(new RewardsGUI()).build().open(player);
            }));
        } else {
            inventoryContents.set(2, 3, ClickableItem.empty(survival));
        }

        /**
         * Skyblock Icon
         */
        LevelType preferredSkyblockLevelType = LevelType.ONEBLOCK_LEVEL;
        LevelType preferredSkyblockExpType = LevelType.ONEBLOCK_EXPERIENCE;

        long skyblockLevel = craftPlayer.getLevelByType(preferredSkyblockLevelType);
        long skyblockTotalExperience = craftPlayer.getExperienceByType(preferredSkyblockExpType);
        double skyblockTotalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(preferredSkyblockLevelType));
        double skyblockPercentage = FormatUtils.roundDouble((skyblockTotalExperience/skyblockTotalExperienceForNextLevel)*100, 3);

        ItemStack skyblock = new ItemBuilder(Material.NAUTILUS_SHELL)
                .setName(ServerColors.SERVER_SKYBLOCK.getChatColor() + "Oneblock")
                .hideAllFlags()
                .setLore(
                        "§7Level: §f" + skyblockLevel,
                        "§7Další level: §f" + skyblockTotalExperience + "/" + Math.round(skyblockTotalExperienceForNextLevel) + " EXP",
                        "§7Procentuálně: ",
                        getPercentageBar(skyblockPercentage) + " §7[" + skyblockPercentage + "%]"

                ).build();
        if (Main.getServerType() == ServerType.SKYBLOCK_117 || Main.getServerType() == ServerType.SKYBLOCK_118) {
            ItemStack modifiedSkyblockItem = new ItemBuilder(skyblock).addLoreLine("").addLoreLine("§e[LTM] Zobrazení odměn za level").build();
            inventoryContents.set(2, 4, ClickableItem.of(modifiedSkyblockItem, inventoryClickEvent -> {
                SmartInventory.builder().size(6, 9).title("Level rewards").provider(new RewardsGUI()).build().open(player);
            }));
        } else {
            inventoryContents.set(2, 4, ClickableItem.empty(skyblock));
        }

        /**
         * Creative Icon
         */
        long creativeLevel = craftPlayer.getLevelByType(LevelType.CREATIVE_LEVEL);
        long creativeTotalExperience = craftPlayer.getExperienceByType(LevelType.CREATIVE_EXPERIENCE);
        double creativeTotalExperienceForNextLevel = LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(LevelType.CREATIVE_LEVEL));
        double creativePercentage = FormatUtils.roundDouble((creativeTotalExperience/creativeTotalExperienceForNextLevel)*100, 3);

        ItemStack creative = new ItemBuilder(Material.LAVA_BUCKET)
                .setName(ServerColors.SERVER_CREATIVE.getChatColor() + "Creative")
                .hideAllFlags()
                .setLore(
                        "§7Level: §f" + creativeLevel,
                        "§7Další level: §f" + creativeTotalExperience + "/" + Math.round(creativeTotalExperienceForNextLevel) + " EXP",
                        "§7Procentuálně: ",
                        getPercentageBar(creativePercentage) + " §7[" + creativePercentage + "%]"

                ).build();
        if (Main.getServerType() == ServerType.CREATIVE) {
            ItemStack modifiedCreativeItem = new ItemBuilder(creative).addLoreLine("").addLoreLine("§e[LTM] Zobrazení odměn za level").build();
            inventoryContents.set(2, 5, ClickableItem.of(modifiedCreativeItem, inventoryClickEvent -> {
                SmartInventory.builder().size(6, 9).title("Level rewards").provider(new RewardsGUI()).build().open(player);
            }));
        } else {
            inventoryContents.set(2, 5, ClickableItem.empty(creative));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }

    private String getPercentageBar(double percentage) {
        if (percentage <= 10) {
            return "§8▀▀▀▀▀▀▀▀▀▀";
        } else if (percentage <= 20) {
            return "§a▀§8▀▀▀▀▀▀▀▀▀";
        } else if (percentage <= 30) {
            return "§a▀▀§8▀▀▀▀▀▀▀▀";
        } else if (percentage <= 40) {
            return "§a▀▀▀§8▀▀▀▀▀▀▀";
        } else if (percentage <= 50) {
            return "§a▀▀▀▀§8▀▀▀▀▀▀";
        } else if (percentage <= 60) {
            return "§a▀▀▀▀▀§8▀▀▀▀▀";
        } else if (percentage <= 70) {
            return "§a▀▀▀▀▀▀§8▀▀▀▀";
        } else if (percentage <= 80) {
            return "§a▀▀▀▀▀▀▀§8▀▀▀";
        } else if (percentage <= 90) {
            return "§a▀▀▀▀▀▀▀▀§8▀▀";
        } else if (percentage <= 100) {
            return "§a▀▀▀▀▀▀▀▀▀§8▀";
        } else {
            return "§a▀▀▀▀▀▀▀▀▀▀";
        }
    }
}
