package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.builders.items.ItemBuilder;
import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.craftcore.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.inventory.builder.content.SlotPos;
import cz.craftmania.craftlibs.utils.ChatInfo;
import cz.craftmania.craftpack.api.Buttons;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KalendarGUI implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        // 1.12. -> Cosmetic: Rudolph Hat
        CalendarReward day_1 = new CalendarReward(1, "Rudolph Hat", Type.COSMETIC, "craftmanager.hats.rudolph_hat");
        day_1.setUnlockTime(1669849200000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(1, 3), player, day_1);

        // 2.12. -> Furniture: Christmas Chair
        CalendarReward day_2 = new CalendarReward(2, "Christmas Chair", Type.FURNITURE, "craftmanager.furniture.christmas_chair");
        day_2.setUnlockTime(1669935600000L);
        day_2.setTimeRepeatable(true);
        day_2.setItemsAdderId("christmas_chair");
        this.generateCalendarItem(inventoryContents, SlotPos.of(1, 4), player, day_2);

        // 3.12. -> Furniture: Christmas Wreath, Christmas Fireplace
        CalendarReward day_3 = new CalendarReward(3, "Christmas Wreath & Christmas Fireplace", Type.FURNITURE, "craftmanager.furniture.christmas_wreath");
        day_3.setUnlockTime(1670022000000L);
        day_3.setTimeRepeatable(true);
        day_3.setItemsAdderId("christmas_wreath", "christmas_fireplace");
        this.generateCalendarItem(inventoryContents, SlotPos.of(1, 5), player, day_3);

        // 5.12. -> Furniture: Christmas Tree, Christmas Garland
        CalendarReward day_4 = new CalendarReward(5, "Christmas Tree, Christmas Garland", Type.FURNITURE, "craftmanager.furniture.garland");
        day_4.setUnlockTime(1670194800000L);
        day_4.setTimeRepeatable(true);
        day_4.setItemsAdderId("christmas_tree", "christmas_garland");
        this.generateCalendarItem(inventoryContents, SlotPos.of(2, 2), player, day_4);

        // 6.12. -> Cosmetic: Present Hat
        CalendarReward day_6 = new CalendarReward(6, "Present Hat", Type.COSMETIC, "craftmanager.hats.present_hat");
        day_6.setUnlockTime(1670281200000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(2, 3), player, day_6);

        // 7.12. -> Cosmetic: Antlers Hat
        CalendarReward day_7 = new CalendarReward(7, "Antlers Hat", Type.COSMETIC, "craftmanager.hats.antlers_hat");
        day_7.setUnlockTime(1670367600000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(2, 4), player, day_7);

        // 9.12. -> Furniture: Christmas Snowman, Christmas Table
        CalendarReward day_9 = new CalendarReward(9, "Christmas Snowman, Christmas Table", Type.FURNITURE, "craftmanager.furniture.snowman");
        day_9.setUnlockTime(1670540400000L);
        day_9.setTimeRepeatable(true);
        day_9.setItemsAdderId("christmas_snowman", "christmas_table");
        this.generateCalendarItem(inventoryContents, SlotPos.of(2, 5), player, day_9);

        // 10.12. -> Furniture: Christmas Armchair, Christmas Sofa
        CalendarReward day_10 = new CalendarReward(10, "Christmas Armchair, Christmas Sofa", Type.FURNITURE, "craftmanager.furniture.armchair");
        day_10.setUnlockTime(1670626800000L);
        day_10.setTimeRepeatable(true);
        day_10.setItemsAdderId("christmas_armchair", "christmas_sofa");
        this.generateCalendarItem(inventoryContents, SlotPos.of(2, 6), player, day_10);

        // 11.12 -> Cosmetic: Elf Ears
        CalendarReward day_11 = new CalendarReward(11, "Elf Ears", Type.COSMETIC, "craftmanager.hats.elf_ears_hat");
        day_11.setUnlockTime(1670713200000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(3, 2), player, day_11);

        // 12.12. -> Cosmetic: Santa Hat
        CalendarReward day_12 = new CalendarReward(12, "Santa Hat", Type.COSMETIC, "craftmanager.hats.santa_hat_2022");
        day_12.setUnlockTime(1670799600000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(3, 3), player, day_12);

        // 14.12. -> Furniture: Christmas Teddybear, Christmas Dish
        CalendarReward day_14 = new CalendarReward(14, "Christmas Teddybear, Christmas Dish", Type.FURNITURE, "craftmanager.furniture.teddybear");
        day_14.setUnlockTime(1670972400000L);
        day_14.setTimeRepeatable(true);
        day_14.setItemsAdderId("christmas_teddybear", "christmas_dish");
        this.generateCalendarItem(inventoryContents, SlotPos.of(3, 4), player, day_14);

        // 15.12. -> Cosmetic: Snowman Hat
        CalendarReward day_15 = new CalendarReward(15, "Snowman Hat", Type.COSMETIC, "craftmanager.hats.snowman_hat_2022");
        day_15.setUnlockTime(1671058800000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(3, 5), player, day_15);

        // 16.12. -> Furniture: Christmas Present Small, Christmas Present Big
        CalendarReward day_16 = new CalendarReward(16, "Christmas Present Small, Christmas Present Big", Type.FURNITURE, "craftmanager.furniture.presents");
        day_16.setUnlockTime(1671145200000L);
        day_16.setTimeRepeatable(true);
        day_16.setItemsAdderId("christmas_present1", "christmas_present2");
        this.generateCalendarItem(inventoryContents, SlotPos.of(3, 6), player, day_16);

        // 18.12. -> Furniture: Christmas Tea, Christmas Cake
        CalendarReward day_18 = new CalendarReward(18, "Christmas Tea, Christmas Cake", Type.FURNITURE, "craftmanager.furniture.tea");
        day_18.setUnlockTime(1671318000000L);
        day_18.setTimeRepeatable(true);
        day_18.setItemsAdderId("christmas_cocoa", "christmas_cake");
        this.generateCalendarItem(inventoryContents, SlotPos.of(4, 3), player, day_18);

        // 21.12 -> Cosmetic: CandyCane Hat
        CalendarReward day_21 = new CalendarReward(21, "CandyCane Hat", Type.COSMETIC, "craftmanager.hats.candy_cane_hat");
        day_21.setUnlockTime(1671577200000L);
        this.generateCalendarItem(inventoryContents, SlotPos.of(4, 4), player, day_21);

        // 22.12. -> Furniture: Christmas Blocks, Christmas Goose
        CalendarReward day_22 = new CalendarReward(22, "Christmas Blocks, Christmas Goose", Type.FURNITURE, "craftmanager.furniture.blobs");
        day_22.setUnlockTime(1671663600000L);
        day_22.setTimeRepeatable(true);
        day_22.setItemsAdderId("christmas_buildingblocks", "christmas_goose");
        this.generateCalendarItem(inventoryContents, SlotPos.of(4, 5), player, day_22);

        // Info
        inventoryContents.set(0, 4, ClickableItem.empty(new ItemBuilder(Buttons.STAR.getPureItemStack())
                .setName("§6Vánoční kalendář")
                .setLore("§7Tento rok je Vánoční kalendář velmi speciální", "§7až do §f5.1.2023 §7si můžeš vybrat odměny jednorázově", "§7nebo opakovaně v případě nábytku!")
                .build()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }

    private void generateCalendarItem(InventoryContents inventoryContents, SlotPos slotPos, Player player, CalendarReward calendarReward) {
        if (System.currentTimeMillis() <= calendarReward.getUnlockTime()) {
            inventoryContents.set(slotPos, ClickableItem.empty(
                    new ItemBuilder(Buttons.DISABLED.getPureItemStack()).setName("§c[" + calendarReward.getDay() + "] " + calendarReward.getName())
                            .setLore("§7Tato odměna je ještě uzamknuta.").build()));
            return;
        }

        // Hrac ma reward ale odměna je opakovatelna
        if (player.hasPermission(calendarReward.getPermission()) && calendarReward.isTimeRepeatable()) {
            inventoryContents.set(slotPos, ClickableItem.empty(
                    new ItemBuilder(Buttons.DISABLED_OFF.getPureItemStack()).setName("§6[" + calendarReward.getDay() + "] " + calendarReward.getName())
                            .setLore("§7Tuto odměnu jsi si vybral", "§7před méně než 24h. Zkus to později!").build()));
            return;
        }

        // Hrac ma reward ale odměna není opakovatelna
        if (player.hasPermission(calendarReward.getPermission()) && !calendarReward.isTimeRepeatable()) {
            inventoryContents.set(slotPos, ClickableItem.empty(
                    new ItemBuilder(Buttons.ACCEPT_BUTTON.getPureItemStack()).setName("§a[" + calendarReward.getDay() + "] " + calendarReward.getName())
                            .setLore("§7Tuto odměnu jsi si vybral.").build()));
            return;
        }
        if (calendarReward.getType() == Type.COSMETIC) {
            inventoryContents.set(slotPos, ClickableItem.of(new ItemBuilder(Buttons.UNLOCKED.getPureItemStack()).setName("§a" + calendarReward.getName())
                    .setLore("§7Typ: §fCosmetic", "§7Typ odměny: §fJednorázová", "", "§eKlikni pro výběr Cosmetic odměny!").build(), inventoryClickEvent -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + calendarReward.getPermission());
                ChatInfo.SUCCESS.send(player, "Vybral jsi si odměnu z kalendáře: §f" + calendarReward.getName() + " {c}za §e" + calendarReward.getDay() + ". {c}den.");
                player.closeInventory();
            }));
        } else if (calendarReward.getType() == Type.FURNITURE) {
            if (hasFullInventory(player)) {
                ChatInfo.DANGER.send(player, "Nemáš dostatek místa v inventáři k výběru odměny.");
                return;
            }
            inventoryContents.set(slotPos, ClickableItem.of(new ItemBuilder(Buttons.UNLOCKED.getPureItemStack()).setName("§a" + calendarReward.getName())
                    .setLore("§7Typ: §fFurniture", "§7Typ odměny: §fOpakovatelná (1x 24h)", "", "§eKlikni pro výběr Cosmetic odměny!").build(), inventoryClickEvent -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission settemp " + calendarReward.getPermission() + " true 24h");
                calendarReward.getItemsAdderId().forEach(itemId -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "iagive " + player.getName() + " " + itemId + " 1");
                });
                ChatInfo.SUCCESS.send(player, "Vybral jsi si odměnu z kalendáře: §f" + calendarReward.getName() + " {c}za §e" + calendarReward.getDay() + ". {c}den.");
                player.closeInventory();
            }));
        } else {
            inventoryContents.set(slotPos, ClickableItem.empty(new ItemBuilder(Buttons.CANCEL_BUTTON.getPureItemStack()).setName("§c[" + calendarReward.getDay() + "] Chyba!").setLore("§7Při generování odměny nastala chyba.").build()));
        }

    }

    enum Type {
        COSMETIC,
        FURNITURE,
    }

    private static class CalendarReward {

        @Getter int day;
        @Getter String name;
        @Getter Type type;
        @Getter String permission;
        @Getter long unlockTime;
        @Getter boolean isTimeRepeatable = false;
        @Getter List<String> itemsAdderId;

        public CalendarReward(int day, String name, Type type, String permission) {
            this.day = day;
            this.name = name;
            this.type = type;
            this.permission = permission;
        }

        public void setTimeRepeatable(boolean timeRepeatable) {
            isTimeRepeatable = timeRepeatable;
        }

        public void setItemsAdderId(String... itemsAdderId) {
            this.itemsAdderId = List.of(itemsAdderId);
        }

        public void setUnlockTime(long unlockTime) {
            this.unlockTime = unlockTime;
        }
    }

    private boolean hasFullInventory(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}
