package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.SkullCreator;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static cz.craftmania.crafteconomy.menu.ProfileSettingsGUI.formatJoinMessageWithoutColors;

public class ProfileSettingsGUISelectionLobbyMessage implements InventoryProvider {
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
                ItemStack jedna = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=",
                        "§e§lPrvni zprava", Arrays.asList("§bVolba 1", "§7Text zpravy:", formatJoinMessageWithoutColors(1, p), "", "§aK nastaveni klikni"));
                ItemStack dva = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19",
                        "§e§lDruha zprava", Arrays.asList("§bVolba 2", "§7Text zpravy:", formatJoinMessageWithoutColors(2, p), "", "§aK nastaveni klikni"));
                ItemStack tri = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19",
                        "§e§lTRETI ZPRAVA", Arrays.asList("§bVolba 3", "§7Text zpravy:", formatJoinMessageWithoutColors(3, p), "", "§aK nastaveni klikni"));
                ItemStack ctyri = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlNzhmYjIyNDI0MjMyZGMyN2I4MWZiY2I0N2ZkMjRjMWFjZjc2MDk4NzUzZjJkOWMyODU5ODI4N2RiNSJ9fX0=",
                        "§e§lCTVRTA ZPRAVA", Arrays.asList("§bVolba 4", "§7Text zpravy:", formatJoinMessageWithoutColors(4, p), "", "§aK nastaveni klikni"));
                ItemStack pet = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19",
                        "§e§lPATA ZPRAVA", Arrays.asList("§bVolba 5", "§7Text zpravy:", formatJoinMessageWithoutColors(5, p), "", "§aK nastaveni klikni"));
                ItemStack sest = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM0YjM2ZGU3ZDY3OWI4YmJjNzI1NDk5YWRhZWYyNGRjNTE4ZjVhZTIzZTcxNjk4MWUxZGNjNmIyNzIwYWIifX19=",
                        "§e§lSESTA ZPRAVA", Arrays.asList("§bVolba 6", "§7Text zpravy:", formatJoinMessageWithoutColors(6, p), "", "§aK nastaveni klikni"));
                ItemStack sedm = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiNmViMjVkMWZhYWJlMzBjZjQ0NGRjNjMzYjU4MzI0NzVlMzgwOTZiN2UyNDAyYTNlYzQ3NmRkN2I5In19fQ==",
                        "§e§lSEDMA ZPRAVA", Arrays.asList("§bVolba 7", "§7Text zpravy:", formatJoinMessageWithoutColors(7, p), "", "§aK nastaveni klikni"));

                contents.set(1, 1, ClickableItem.of(jedna, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(1, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 1);
                }));
                contents.set(1, 2, ClickableItem.of(dva, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(2, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 2);
                }));
                contents.set(1, 3, ClickableItem.of(tri, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(3, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 3);
                }));
                contents.set(1, 4, ClickableItem.of(ctyri, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(4, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 4);
                }));
                contents.set(1, 5, ClickableItem.of(pet, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(5, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 5);
                }));
                contents.set(1, 6, ClickableItem.of(sest, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(6, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 6);
                }));
                contents.set(1, 7, ClickableItem.of(sedm, e -> {
                    contents.inventory().close(p);
                    p.sendMessage("§e§l[*] §eZprava pri pripojeni nastavena na §e'" + formatJoinMessageWithoutColors(7, p) + "'.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_message", 7);
                }));

                break;
            }
        }
    }
}
