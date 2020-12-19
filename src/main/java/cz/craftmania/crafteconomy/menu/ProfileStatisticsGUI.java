package cz.craftmania.crafteconomy.menu;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.spigot.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.api.*;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.Request;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ProfileStatisticsGUI implements InventoryProvider {

    private Pagination pagination;

    @Override
    public void init(Player player, InventoryContents contents) {
        pagination = contents.pagination();
        ClickableItem[] pages = new ClickableItem[2];
        pagination.setItems(pages);
        pagination.setItemsPerPage(1);

        openPage(player, contents);
    }

    @Override
    public void update(Player player, InventoryContents contents) {}

    //Pages
    private void openPage(Player p, InventoryContents contents) {

        //Preparations
        JsonObject profileData = (JsonObject) new JsonParser().parse(Request.get("https://api.craftmania.cz/player/" + p.getName()));

        if (profileData.get("status").getAsInt() != 200) {
            Logger.danger("CM API vrátilo status code " + profileData.get("status").getAsInt() + " při requestu profilu pro hráče " + p.getName() + "! Data pro hráče se nezobrazí správně.");
            p.sendMessage("§cNastala chyba při získávání dat z API (Kód: " + profileData.get("status").getAsInt() + "). Zkus to prosím později.");
            contents.inventory().close(p);
        }

        ItemStack economy = ProfileGUI.createItem(Material.GOLD_INGOT, "§bEconomy", Arrays.asList(
            "§7CraftCoins: §f" + CraftCoinsAPI.getCoins(p),
            "§7VoteTokens: §f" + VoteTokensAPI.getVoteTokens(p),
            "§7CraftTokens: §f" + CraftTokensAPI.getTokens(p),
            "§7Quest points: §f" + QuestPointsAPI.getQuestPoints(p),
            "§7Karma: §f" + profileData.getAsJsonObject("data").getAsJsonObject("economy").get("karma").getAsInt(),
            "§7Event points: §f" + EventPointsAPI.getEventPoints(p),
            "§7Bug points: §f" + profileData.getAsJsonObject("data").getAsJsonObject("economy").get("bug_points").getAsInt()
        ));
        ItemStack votes = ProfileGUI.createItem(Material.PAPER, "§eVotes", Arrays.asList(
                "§7Týdenní hlasy: §f" + profileData.getAsJsonObject("data").getAsJsonObject("votes").get("week").getAsInt(),
                "§7Měsíční hlasy: §f" + profileData.getAsJsonObject("data").getAsJsonObject("votes").get("month").getAsInt(),
                "§7Celkem hlasů: §f" + profileData.getAsJsonObject("data").getAsJsonObject("votes").get("total").getAsInt(),
                "§7Poslední hlas: §f" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(profileData.getAsJsonObject("data").getAsJsonObject("votes").get("last_vote").getAsLong())
        ));
        ItemStack levels = ProfileGUI.createItem(Material.WRITABLE_BOOK, "§dLevels", Arrays.asList(
                "§7Global: §f" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("global_level").getAsInt() + " §8LVL",
                "",
                "§7Survival: §f" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("survival_level").getAsInt() + " §8LVL §6(" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("survival_experience").getAsInt() + " EXP)",
                "§7Skyblock: §f" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("skyblock_level").getAsInt() + " §8LVL §6(" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("skyblock_experience").getAsInt() + " EXP)",
                "§7Creative: §f" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("creative_level").getAsInt() + " §8LVL §6(" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("creative_experience").getAsInt() + " EXP)",
                "§7Vanilla: §f" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("vanilla_level").getAsInt() + " §8LVL §6(" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("vanilla_experience").getAsInt() + " EXP)",
                "§7Skycloud: §f" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("skycloud_level").getAsInt() + " §8LVL §6(" + profileData.getAsJsonObject("data").getAsJsonObject("ranked").get("skycloud_experience").getAsInt() + " EXP)"
        ));

        ProfileGUI.makeLines(contents);

        //Page making
        contents.set(2, 3, ClickableItem.empty(economy));
        contents.set(2, 4, ClickableItem.empty(votes));
        contents.set(2, 5, ClickableItem.empty(levels));
        contents.set(4, 4, ClickableItem.of(ProfileGUI.backArrow(), e -> {
            SmartInventory.builder().size(5, 9).title("Profile").provider(new ProfileGUI()).build().open(p);
        }));
    }
}
