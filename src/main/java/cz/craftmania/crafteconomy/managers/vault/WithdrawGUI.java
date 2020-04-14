package cz.craftmania.crafteconomy.managers.vault;

import cz.craftmania.craftcore.spigot.builders.items.ItemBuilder;
import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyBankWithdrawEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WithdrawGUI implements InventoryProvider {

    private static ItemStack cable = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§c ").build();
    private long playerBalance = 0;
    private boolean canWithdrawEmeralds = false, canWithdrawEmeraldBlocks = false, canWithdrawDiamond = false, canWithdrawDiamondBlock = false;

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillRow(0, ClickableItem.of(cable, item -> {}));
        contents.fillRow(4, ClickableItem.of(cable, item -> {}));
        contents.fillColumn(0, ClickableItem.of(cable, item -> {}));
        contents.fillColumn(8, ClickableItem.of(cable, item -> {}));

        this.playerBalance = (long) Main.getVaultEconomy().getBalance(player);
        this.canWithdrawEmeralds = playerBalance >= 1;
        this.canWithdrawEmeraldBlocks = playerBalance >= 9;
        this.canWithdrawDiamond = playerBalance >= 9;
        this.canWithdrawDiamondBlock = playerBalance >= 81;

        if (this.canWithdrawEmeralds) { // 1
            contents.set(2, 2, ClickableItem.of(new ItemBuilder(Material.EMERALD).setName("§aVybrat 1 Emerald").setLore("§71x EM -> 1x EM", "", "§7Klikni pro směnu").build(), item -> {
                if (hasFullInventory(player)) {
                    player.sendMessage("§c§l[!] §cMáš plný inventář, nelze provést výběr!");
                    return;
                }
                Main.getVaultEconomy().withdrawPlayer(player.getName(), 1);
                player.getInventory().addItem(new ItemBuilder(Material.EMERALD).setAmount(1).build());
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyBankWithdrawEvent(player, 1)));
            }));
        } else {
            contents.set(2, 2, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Emerald").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

        if (this.canWithdrawEmeraldBlocks) { // 9
            contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.EMERALD_BLOCK).setName("§aVybrat 1 Emerald Block").setLore("§71x EB -> 9x EM", "", "§7Klikni pro směnu").build(), item -> {
                if (hasFullInventory(player)) {
                    player.sendMessage("§c§l[!] §cMáš plný inventář, nelze provést výběr!");
                    return;
                }
                Main.getVaultEconomy().withdrawPlayer(player.getName(), 9);
                player.getInventory().addItem(new ItemBuilder(Material.EMERALD_BLOCK).setAmount(1).build());
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyBankWithdrawEvent(player, 9)));
            }));
        } else {
            contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Emerald Block").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

        if (this.canWithdrawDiamond) { // 9
            contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.DIAMOND).setName("§bVybrat 1 Diamond").setLore("§71x DM -> 9x EM", "", "§7Klikni pro směnu").build(), item -> {
                if (hasFullInventory(player)) {
                    player.sendMessage("§c§l[!] §cMáš plný inventář, nelze provést výběr!");
                    return;
                }
                Main.getVaultEconomy().withdrawPlayer(player.getName(), 9);
                player.getInventory().addItem(new ItemBuilder(Material.DIAMOND).setAmount(1).build());
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyBankWithdrawEvent(player, 9)));
            }));
        } else {
            contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Diamond").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

        if (this.canWithdrawDiamondBlock) { // 81
            contents.set(2, 6, ClickableItem.of(new ItemBuilder(Material.DIAMOND_BLOCK).setName("§bVybrat 1 Diamond Block").setLore("§71x DB -> 81x EM", "", "§7Klikni pro směnu").build(), item -> {
                if (hasFullInventory(player)) {
                    player.sendMessage("§c§l[!] §cMáš plný inventář, nelze provést výběr!");
                    return;
                }
                Main.getVaultEconomy().withdrawPlayer(player.getName(), 81);
                player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_BLOCK).setAmount(1).build());
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyBankWithdrawEvent(player, 81)));
            }));
        } else {
            contents.set(2, 6, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Diamond Block").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

    }

    @Override
    public void update(Player player, InventoryContents contents) {
        this.playerBalance = (long) Main.getVaultEconomy().getBalance(player);
        this.canWithdrawEmeralds = playerBalance >= 1;
        this.canWithdrawEmeraldBlocks = playerBalance >= 9;
        this.canWithdrawDiamond = playerBalance >= 9;
        this.canWithdrawDiamondBlock = playerBalance >= 81;

        if (!this.canWithdrawEmeralds) {
            contents.set(2, 2, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Emerald").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

        if (!this.canWithdrawEmeraldBlocks) {
            contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Emerald Block").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

        if (!this.canWithdrawDiamond) {
            contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Diamond").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }

        if (!this.canWithdrawDiamondBlock) {
            contents.set(2, 6, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cVybrat 1 Diamond Block").setLore("§7Nemáš dostatek emeraldů.").build(), item -> {}));
        }
    }

    private boolean hasFullInventory(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}
