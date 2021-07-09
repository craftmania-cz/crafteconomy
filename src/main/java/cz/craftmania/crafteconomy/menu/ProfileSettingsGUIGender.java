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

import java.util.List;

public class ProfileSettingsGUIGender implements InventoryProvider {

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
                ItemStack genderMan = createItem(Material.BLUE_WOOL, "§e§lMuž", null);
                ItemStack genderWoman = createItem(Material.PINK_WOOL, "§e§lŽena", null);
                ItemStack genderOther = createItem(Material.LIGHT_GRAY_WOOL, "§e§lNechci uvádět", null);

                contents.set(1, 3, ClickableItem.of(genderOther, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTvoje pohlaví bylo změněno na: Nechci uvádět");
                    Main.getInstance().getMySQL().updateGender(p, 0);
                }));
                contents.set(1, 4, ClickableItem.of(genderMan, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTvoje pohlaví bylo změněno na: Muž");
                    Main.getInstance().getMySQL().updateGender(p, 1);
                }));
                contents.set(1, 5, ClickableItem.of(genderWoman, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eTvoje pohlaví bylo změněno na: Žena");
                    Main.getInstance().getMySQL().updateGender(p, 2);
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
