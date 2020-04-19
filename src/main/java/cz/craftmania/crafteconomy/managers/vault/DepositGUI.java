package cz.craftmania.crafteconomy.managers.vault;

import cz.craftmania.craftcore.spigot.builders.items.ItemBuilder;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyBankDepositEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DepositGUI implements Listener {

    private static ItemStack cable = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§c ").build();
    private static ItemStack trezor = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("§aUmísti Emeraldy do sředu").build();
    private static ItemStack addItem = new ItemBuilder(Material.GOLD_NUGGET).setName("§6Vložit do banky").setLore("§7Po kliknutí se ti přidá", "§7daný počet Emeraldů na účet.").build();
    private static ItemStack filler = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("§c ").build();

    public static void open(final Player player) {
        Inventory inv = Bukkit.createInventory(null,45, "[WB] Uložení prostředků");

        inv.setItem(0, cable);
        inv.setItem(1, cable);
        inv.setItem(2, cable);
        inv.setItem(11, cable);

        inv.setItem(6, cable);
        inv.setItem(7, cable);
        inv.setItem(8, cable);
        inv.setItem(15, cable);

        inv.setItem(29, cable);
        inv.setItem(36, cable);
        inv.setItem(37, cable);
        inv.setItem(38, cable);

        inv.setItem(33, cable);
        inv.setItem(42, cable);
        inv.setItem(43, cable);
        inv.setItem(44, cable);

        inv.setItem(12, trezor);
        inv.setItem(13, trezor);
        inv.setItem(14, trezor);
        inv.setItem(21, trezor);
        inv.setItem(23, trezor);
        inv.setItem(30, trezor);
        inv.setItem(31, trezor);
        inv.setItem(32, trezor);

        inv.setItem(25, addItem);

         // ou šit fill sračky
        inv.setItem(3, filler);
        inv.setItem(4, filler);
        inv.setItem(5, filler);
        inv.setItem(9, filler);
        inv.setItem(10, filler);
        inv.setItem(16, filler);
        inv.setItem(17, filler);
        inv.setItem(18, filler);
        inv.setItem(19, filler);
        inv.setItem(20, filler);
        inv.setItem(24, filler);
        inv.setItem(26, filler);
        inv.setItem(27, filler);
        inv.setItem(28, filler);
        inv.setItem(34, filler);
        inv.setItem(35, filler);
        inv.setItem(39, filler);
        inv.setItem(40, filler);
        inv.setItem(41, filler);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (event.getView().getTitle().equals("[WB] Uložení prostředků")) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta().equals(cable.getItemMeta())
                    || event.getCurrentItem().getItemMeta().equals(trezor.getItemMeta())
                    || event.getCurrentItem().getItemMeta().equals(filler.getItemMeta())) {
                event.setCancelled(true);
                return;
            }
            if (event.getSlot() == 25) {
                event.setCancelled(true);

                //Count emeralds
                ItemStack itemStack = event.getView().getItem(22);
                if (itemStack == null) {
                    player.sendMessage("§c§l[!] §cMusíš umístit emeraldy do středu!");
                    return;
                }
                if (itemStack.hasItemMeta()) {
                    player.sendMessage("§c§l[!] §cNelze vkládat upravené Emeraldy!");
                    return;
                }
                if (!checkIfCanStore(itemStack)) {
                    player.sendMessage("§c§l[!] §cLze vkládat pouze Emeraldy, Emerald Blocky, Diamands nebo Diamond Blocky!");
                    return;
                }
                int count = countEmeralds(event.getView().getTopInventory(), player);
                Main.getVaultEconomy().depositPlayer(player.getName(), count);
                event.getView().setItem(22, null);
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyBankDepositEvent(player, count)));
            }
        }
    }

    @EventHandler
    public void onDragDeposit(final InventoryDragEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (event.getView().getTitle().equals("[WB] Uložení prostředků")) {
            if (event.getCursor() == null) {
                return;
            }
            if (event.getCursor().getItemMeta().equals(cable.getItemMeta())
                    || event.getCursor().getItemMeta().equals(trezor.getItemMeta())
                    || event.getCursor().getItemMeta().equals(filler.getItemMeta())
                    || event.getCursor().getItemMeta().equals(addItem.getItemMeta())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCloseDeposit(final InventoryCloseEvent event) {
        final Player player = (Player)event.getPlayer();
        if (event.getView().getTitle().equals("[WB] Uložení prostředků")) {
            ItemStack itemStack = event.getView().getItem(22);
            if (itemStack == null) {
                return;
            }
            player.getInventory().addItem(itemStack);
            player.sendMessage("§e§l[*] §ePoložka v deposit menu ti byla vrácena do inventáře.");
        }
    }

    private int countEmeralds(Inventory inventory, Player player) {
        int count = 0; // Slot 22
        ItemStack emeralds = inventory.getItem(22);
        if (emeralds.hasItemMeta()) {
            player.sendMessage("§c§l[!] §cLze umisťovat pouze emeraldy bez názvů.");
            return 0;
        }
        if (emeralds.getType() == Material.EMERALD) {
            return emeralds.getAmount();
        } else if (emeralds.getType() == Material.EMERALD_BLOCK) {
            return emeralds.getAmount() * 9;
        } else if (emeralds.getType() == Material.DIAMOND) {
            return (emeralds.getAmount() * 9) * 9;
        } else if (emeralds.getType() == Material.DIAMOND_BLOCK) {
            return ((emeralds.getAmount() * 9) * 9) * 9;
        }
        return 0; // HUH?
    }

    private boolean checkIfCanStore(final ItemStack itemStack) {
        boolean canStore = false;
        if (itemStack.getType() == Material.EMERALD) {
            canStore = true;
        }
        if (itemStack.getType() == Material.EMERALD_BLOCK) {
            canStore = true;
        }
        if (itemStack.getType() == Material.DIAMOND) {
            canStore = true;
        }
        if (itemStack.getType() == Material.DIAMOND_BLOCK) {
            canStore = true;
        }
        return canStore;
    }
}
