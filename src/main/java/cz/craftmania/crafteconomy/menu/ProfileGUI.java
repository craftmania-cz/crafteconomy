package cz.craftmania.crafteconomy.menu;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.craftcore.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.Request;
import cz.craftmania.crafteconomy.utils.TimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileGUI implements InventoryProvider {

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
        ItemStack settings = createItem(Material.COMPARATOR, "§aNastavení", Arrays.asList("§7Díky nastavení si můžeš", "§7přizpůsobit lobby/hry podle sebe.", "", "§eKlikni pro otevření"));
        ItemStack statistics = createItem(Material.WRITABLE_BOOK, "§aStatistiky", Arrays.asList("§7Ukáže ti všechny tvoje statistiky od A po Z!", "", "§eKlikni pro zobrazení statistik"));
        ItemStack multipliers = createItem(Material.ENDER_PEARL, "§aMultipliery", Arrays.asList("§8Již brzy."));
        ItemStack achievements = createItem(Material.LECTERN, "§aAchievementy", Arrays.asList("§7Plň achievementy na serveru", "§7a získávej odměny za hraní!", "", "§eKlikni pro zobrazení"));

        makeLines(contents);

        // Page making
        contents.set(0, 4, ClickableItem.empty(getPlayerHead(p, "§cInformace o tobě", getPlayerHeadLore(p))));
        contents.set(2, 2, ClickableItem.of(statistics, e -> {
            SmartInventory.builder().size(5, 9).title("Statistiky").provider(new ProfileStatisticsGUI()).build().open(p);
        }));
        contents.set(2, 3, ClickableItem.of(achievements, e -> {
            p.performCommand("aach list");
        }));
        contents.set(2, 5, ClickableItem.empty(multipliers));
        contents.set(2, 4, ClickableItem.of(settings, e -> {
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
        return createItem(Material.RED_BED, "§cZpět", null);
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

        itemLore.add("§7ID: §f" + profileData.getAsJsonObject("data").get("id").getAsLong() + "§8#" + profileData.getAsJsonObject("data").get("discriminator").getAsString());
        itemLore.add("§7Tvůj nick: §f" + p.getName());
        itemLore.add("§7První připojení: §f" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(profileData.getAsJsonObject("data").get("registred").getAsLong()));
        itemLore.add("§7Celkem odehraný čas: §f" + TimeUtils.formatTime("%dd, %hh %mm", profileData.getAsJsonObject("data").get("played_time").getAsLong(), false));
        itemLore.add("");
        itemLore.add("§ePro více statistik klikni na menu s statistiky");
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