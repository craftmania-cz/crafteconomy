package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ProfileSettingsGUISelectionLobbySound implements InventoryProvider {
    
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
                ItemStack experience = createItem(Material.EXPERIENCE_BOTTLE, "§e§lEXP ORB PICKUP", Arrays.asList("§f", "§7Tento zvuk slysis, kdyz", "§7seberes EXP orb."));
                ItemStack anvil = createItem(Material.ANVIL, "§e§lANVIL FALL", Arrays.asList("§f", "§7Tento zvuk slysis, kdyz", "§7kdyz anvilka dopadne na zem."));
                ItemStack glass = createItem(Material.GLASS, "§e§lGLASS BREAK", Arrays.asList("§f", "§7Tento zvuk slysis, kdyz", "§7rozbijes sklo."));
                ItemStack itempickup = createItem(Material.IRON_PICKAXE, "§e§lITEM PICKUP", Arrays.asList("§f", "§7Tento zvuk slysis, kdyz", "§7seberes nejaky item."));
                ItemStack zombie = createItem(Material.ROTTEN_FLESH, "§e§lZOMBIE HURT", Arrays.asList("§f", "§7Tento zvuk slysis, kdyz", "§7ublizis zombie."));

                contents.set(1, 2, ClickableItem.of(experience, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZvuk oznacovani byl nastaven na §2§lEXP ORB PICKUP§e.");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
                }));
                contents.set(1, 3, ClickableItem.of(anvil, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZvuk oznacovani byl nastaven na §2§lANVIL FALL§e.");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound", "BLOCK_ANVIL_FALL");
                }));
                contents.set(1, 4, ClickableItem.of(glass, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZvuk oznacovani byl nastaven na §2§lGLASS BREAK§e.");
                    p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound", "BLOCK_GLASS_BREAK");
                }));
                contents.set(1, 5, ClickableItem.of(itempickup, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZvuk oznacovani byl nastaven na §2§lITEM PICKUP§e.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound", "ENTITY_ITEM_PICKUP");
                }));
                contents.set(1, 6, ClickableItem.of(zombie, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZvuk oznacovani byl nastaven na §2§lZOMBIE HURT§e.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_HURT, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound", "ENTITY_ZOMBIE_HURT");
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
