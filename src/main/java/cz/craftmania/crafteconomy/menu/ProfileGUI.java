package cz.craftmania.crafteconomy.menu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.spigot.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.*;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.Request;
import cz.craftmania.crafteconomy.utils.SkullCreator;
import cz.craftmania.crafteconomy.utils.TimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileGUI implements InventoryProvider  {

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

    // Pages

    void openPage(Player p, InventoryContents contents) {
        // Preparations
        ItemStack settings = createItem(Material.COMPARATOR, "§aNastavení", Arrays.asList("§7Díky nastavení si můžeš", "§7přizpůsobit lobby/hry podle sebe.", "", "§eKlikni pro otevření nastavení"));
        ItemStack statistics = createItem(Material.WRITABLE_BOOK, "§aStatistiky", Arrays.asList("§7Ukáže ti všechny tvoje statistiky od A po Z!", "", "§eKlikni pro otevření statistik"));
        ItemStack multipliers = createItem(Material.ENDER_PEARL, "§aMultipliery", Arrays.asList("§8Již brzy."));
        ItemStack achievements = createItem(Material.LECTERN, "§aAchievementy", Arrays.asList("§8Již brzy."));

        makeLines(contents);

        // Page making
        contents.set(0, 4, ClickableItem.empty(getPlayerHead(p, "§cInformace o tobě", getPlayerHeadLore(p))));
        contents.set(2, 2, ClickableItem.of(statistics, e -> {
            SmartInventory.builder().size(5, 9).title("Statistiky").provider(new ProfileStatisticsGUI()).build().open(p);
        }));
        contents.set(2, 6, ClickableItem.empty(achievements));
        contents.set(3, 2, ClickableItem.empty(multipliers));
        contents.set(3, 4, ClickableItem.of(settings, e -> {
            SmartInventory.builder().size(5, 9).title("Nastavení").provider(new ProfileSettingsGUI()).build().open(p);
        }));
    }

    // Utils
    public static ItemStack createItem(Material material, String itemName, List<String> itemLore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack backArrow() {
        return createItem(Material.ARROW, "§eZpět", null);
    }

    public static void makeLines(InventoryContents contents) {
        ItemStack filler = createItem(Material.BLACK_STAINED_GLASS_PANE, " ", null);
        for (int x=0; x<contents.inventory().getColumns(); x++) {
            contents.set(0, x, ClickableItem.empty(filler));
            contents.set(4, x, ClickableItem.empty(filler));
        }
    }

    private List<String> getPlayerHeadLore(Player p) {
        List<String> itemLore = new ArrayList<>();

        JsonObject profileData = (JsonObject) new JsonParser().parse(Request.get("https://api.craftmania.cz/player/" + p.getName()));

        if (profileData.get("status").getAsInt() != 200) {
            Logger.danger("CM API vrátilo status code " + profileData.get("status").getAsInt() + " při requestu profilu pro hráče " + p.getName() + "! Data pro hráče se nezobrazí správně.");
            itemLore.add("§cNastala chyba při získávání dat z API (Kód: " + profileData.get("status").getAsInt() + ").");
            itemLore.add("§c Zkus to prosím později.");
            return itemLore;
        }

        itemLore.add("");
        itemLore.add("§7ID: §f" + profileData.getAsJsonObject("data").get("id").getAsLong() + "§8#" + profileData.getAsJsonObject("data").get("discriminator").getAsInt());
        itemLore.add("§7První připojení: §f" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(profileData.getAsJsonObject("data").get("registred").getAsLong()));
        itemLore.add("§7CraftCoins: §f" + CraftCoinsAPI.getCoins(p));
        itemLore.add("§7VoteTokens: §f" + VoteTokensAPI.getVoteTokens(p));
        itemLore.add("§7CraftTokens: §f" + CraftTokensAPI.getTokens(p));
        itemLore.add("§7Achievement points: §f" + AchievementPointsAPI.getAchievementPoints(p));
        itemLore.add("§7Karma: §f" + profileData.getAsJsonObject("data").getAsJsonObject("economy").get("karma").getAsInt());
        itemLore.add("§7Event points: §f" + EventPointsAPI.getEventPoints(p));
        itemLore.add("§7Bug points: §8Již brzy.");
        itemLore.add("§7Celkem odehraný čas: §f" + TimeUtils.formatTime("%dd, %hh %mm", profileData.getAsJsonObject("data").get("played_time").getAsLong(), false));

        return itemLore;
    }

    private ItemStack getPlayerHead(Player p, String itemName, List<String> itemLore) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(p);
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        head.setItemMeta(meta);
        return head;
    }
}