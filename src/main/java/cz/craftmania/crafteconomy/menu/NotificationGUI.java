package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.builders.items.ItemBuilder;
import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.content.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.NotificationManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NotificationGUI implements InventoryProvider {

    private final BasicManager manager = new BasicManager();
    private final NotificationManager notificationManager = new NotificationManager();

    @Override
    public void init(Player player, InventoryContents contents) {
        CraftPlayer craftPlayer = manager.getCraftPlayer(player);

        final Pagination pagination = contents.pagination();
        final ArrayList<ClickableItem> items = getClickableItems(craftPlayer);

        ClickableItem[] c = new ClickableItem[items.size()];
        c = items.toArray(c);
        pagination.setItems(c);
        pagination.setItemsPerPage(36);

        if (items.size() > 0 && !pagination.isLast()) {
            contents.set(5, 7, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§f§lDalší stránka").build(), e -> {
                contents.inventory().open(player, pagination.next().getPage());
            }));
        }
        if (!pagination.isFirst()) {
            contents.set(5, 1, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§f§lPředchozí stránka").build(), e -> {
                contents.inventory().open(player, pagination.previous().getPage());
            }));
        }

        SlotIterator slotIterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0);
        slotIterator = slotIterator.allowOverride(false);
        pagination.addToIterator(slotIterator);
    }

    private ArrayList<ClickableItem> getClickableItems(CraftPlayer craftPlayer) {
        final ArrayList<ClickableItem> items = new ArrayList<>();

        craftPlayer.getNotificationList().forEach(((integer, notificationObject) -> {

            if (notificationObject.isRead()) {
                ClickableItem clickableItem = ClickableItem.empty(new ItemBuilder(Material.GREEN_DYE).setName("§a" + notificationObject.getTitle())
                        .setLore("§eTyp: §f" + notificationObject.getNotificationType(),
                                "§ePriorita: §f" + notificationObject.getNotificationPriority(),
                                "§eServer: §f" + notificationObject.getNotificationServer(),
                                "§eDatum: §fxxxx",
                                "§eZpráva:",
                                "§7" + notificationObject.getMessage()
                        ).build());
                items.add(clickableItem);
            } else {
                ItemStack itemStack = new ItemBuilder(Material.RED_DYE)
                        .setName("§c" + notificationObject.getTitle())
                        .setLore("§eTyp: §f" + notificationObject.getNotificationType(),
                                "§ePriorita: §f" + notificationObject.getNotificationPriority(),
                                "§eServer: §f" + notificationObject.getNotificationServer(),
                                "§eDatum: §fxxxx",
                                "§eZpráva:",
                                "§7" + notificationObject.getMessage(),
                                "",
                                "§bKliknutím přečteš upozornění.",
                                "" + notificationObject.getNotificationId())
                        .build();
                ClickableItem clickableItem = ClickableItem.of(itemStack, (inventoryClickEvent -> {
                    List<String> lore = inventoryClickEvent.getCurrentItem().getItemMeta().getLore();
                    int notifycationId = Integer.parseInt(lore.get(lore.size() -1));
                    notificationManager.markNotificationAsRead(craftPlayer.getPlayer().getName(), notifycationId);
                }));
                items.add(clickableItem);
            }
        }));
        return items;
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        CraftPlayer craftPlayer = manager.getCraftPlayer(player);
        final ArrayList<ClickableItem> items = getClickableItems(craftPlayer);
        for (ClickableItem item : items) {
            contents.add(item);
        }

    }
}
