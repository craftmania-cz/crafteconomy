package cz.craftmania.crafteconomy.rewards;

import cz.craftmania.craftcore.spigot.builders.items.ItemBuilder;
import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.spigot.inventory.builder.content.Pagination;
import cz.craftmania.craftcore.spigot.inventory.builder.content.SlotIterator;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RewardsGUI implements InventoryProvider {

    private final BasicManager manager = new BasicManager();

    @Override
    public void init(Player player, InventoryContents contents) {

        final Pagination pagination = contents.pagination();
        final ArrayList<ClickableItem> items = new ArrayList<>();

        CraftPlayer craftPlayer = manager.getCraftPlayer(player);

        ProprietaryManager.getServerLevelRewardsList().forEach(levelReward -> {
            final ArrayList<String> lore = new ArrayList<>();
            lore.add("§aOdměna:");
            lore.addAll(levelReward.getDescription());
            if (levelReward.getLevel() > craftPlayer.getLevelByType(manager.getLevelByServer())) {
                ItemStack item = new ItemBuilder(Material.KNOWLEDGE_BOOK).setName("§e§lLevel: §6" + levelReward.getLevel())
                        .setLore(lore).build();
                items.add(ClickableItem.of(item, e -> {}));
            } else {
                lore.add("§c");
                lore.add("§cJiž splněno!");
                ItemStack item = new ItemBuilder(Material.BOOK).setName("§e§lLevel: §6" + levelReward.getLevel())
                        .setLore(lore).build();
                items.add(ClickableItem.of(item, e -> {}));
            }
        });

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

        contents.fillRow(0, ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§c ").build()));
        contents.fillRow(5, ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§c ").build()));

        contents.set(5, 4, ClickableItem.of(new ItemBuilder(Material.ANVIL).setGlowing().setName("§c§lDůležité upozornění")
                .setLore("§7Aktuálně pracujeme na opravě,", "§7přidávání dalších odměn a achievementů!", "§7Toto tedy není finální seznam.").build(), e -> {}));

        SlotIterator slotIterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 0);
        slotIterator = slotIterator.allowOverride(false);
        pagination.addToIterator(slotIterator);
    }

    @Override
    public void update(Player player, InventoryContents contents) {}

}
