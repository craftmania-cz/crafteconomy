package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ProfileSettingsGUISelectionLevelupFireworkType implements InventoryProvider {
    private Pagination pagination;

    @Override
    public void init(Player player, InventoryContents contents) {
        pagination = contents.pagination();
        ClickableItem[] pages = new ClickableItem[1];
        pagination.setItems(pages);
        pagination.setItemsPerPage(1);

        switch (pagination.getPage()) {
            case 0: {
                openPage(player, contents, 0);
                break;
            }
            default: { //Chyba
                player.sendMessage("§cNastala chyba! Přesouvám tě na první stranu.");
                contents.inventory().open(player, pagination.page(0).getPage());
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }

    private void openPage(Player p, InventoryContents contents, int page) {
        switch (page) {
            case 0: {
                ItemStack random = createItem(Material.LEAD, "§e§lNáhodný", Arrays.asList("§f", "§7Effekt ohňostroje při level-upu bude náhodný."));
                ItemStack ball = createItem(Material.SNOWBALL, "§e§lKoule", Arrays.asList("§f", "§7Effekt ohňostroje při level-upu bude koule."));
                ItemStack largeBall = createItem(Material.SLIME_BALL, "§e§lVelká koule", Arrays.asList("§f", "§7Effekt ohňostroje při level-upu bude velká koule."));
                ItemStack burst = createItem(Material.GUNPOWDER, "§e§lVýbuch", Arrays.asList("§f", "§7Effekt ohňostroje při level-upu bude výbuch."));
                ItemStack creeper = createItem(Material.CREEPER_HEAD, "§e§lCreeper", Arrays.asList("§f", "§7Effekt ohňostroje při level-upu bude creeper."));
                ItemStack star = createItem(Material.NETHER_STAR, "§e§lHvězda", Arrays.asList("§f", "§7Effekt ohňostroje při level-upu bude hvězda."));

                contents.set(0, 4, ClickableItem.of(random, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTyp ohňostroje při level-upu byl nastaven na §2§lnáhodný§e.");
                    Main.getInstance().getMySQL().updateSettings(p, "levelup_firework_type", "RANDOM");
                }));
                contents.set(1, 2, ClickableItem.of(ball, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTyp ohňostroje při level-upu byl nastaven na §2§lkouli§e.");
                    Main.getInstance().getMySQL().updateSettings(p, "levelup_firework_type", "BALL");
                }));
                contents.set(1, 3, ClickableItem.of(largeBall, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTyp ohňostroje při level-upu byl nastaven na §2§lvelkou kouli§e.");
                    Main.getInstance().getMySQL().updateSettings(p, "levelup_firework_type", "BALL_LARGE");
                }));
                contents.set(1, 4, ClickableItem.of(burst, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTyp ohňostroje při level-upu byl nastaven na §2§lvýbuch§e.");
                    Main.getInstance().getMySQL().updateSettings(p, "levelup_firework_type", "BURST");
                }));
                contents.set(1, 5, ClickableItem.of(creeper, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTyp ohňostroje při level-upu byl nastaven na §2§lcreeper§e.");
                    Main.getInstance().getMySQL().updateSettings(p, "levelup_firework_type", "CREEPER");
                }));
                contents.set(1, 6, ClickableItem.of(star, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTyp ohňostroje při level-upu byl nastaven na §2§lhvězdu§e.");
                    Main.getInstance().getMySQL().updateSettings(p, "levelup_firework_type", "STAR");
                }));
                break;
            }
        }
    }

    //Utils
    private ItemStack createItem(Material material, String itemName, List<String> itemLore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }
}
