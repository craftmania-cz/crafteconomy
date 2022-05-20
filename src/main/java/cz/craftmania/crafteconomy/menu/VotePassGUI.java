package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.builders.items.ItemBuilder;
import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.inventory.builder.content.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.VoteManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.Constants;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class VotePassGUI implements InventoryProvider {

    private final BasicManager manager = new BasicManager();

    @Override
    public void init(Player player, InventoryContents contents) {

        CraftPlayer craftPlayer = manager.getCraftPlayer(player);

        // Statistiky o hlasování
        contents.set(2, 4, ClickableItem.empty(new ItemBuilder(Material.PAPER).setName("§dTvoje statistiky")
                .setLore("§7Zde jsou zobrazené tvoje statistiky", "§7v aktuálním VotePassu a měsíci.", "",
                        "§eVotePass hlasy: §f" + craftPlayer.getVotePassVotes(), "§eTýdenní hlasy: §f" + craftPlayer.getWeekVotes(), "§eMěsíční hlasy: §f" + craftPlayer.getMonthVotes()).build()));

        // VotePass+ zakoupení
        contents.set(1, 0, ClickableItem.of(
                new ItemBuilder(Material.IRON_NUGGET).setName("§aVotePass+").setCustomModelData(100013).hideAllFlags()
                        .setLore("§7Nákupem VotePass+ si zpřístupníš lepší", "§7odměny napříč hlasováním na serveru.",
                                "§7", "§eCena: §f5 Euro / 120kč", "", "§bKliknutím zobrazíš odkaz na nákup v chatu.").build(), click -> {
                    player.closeInventory();
                    player.sendMessage(ChatColor.AQUA + Constants.CHAT_BOXES);
                    player.sendMessage("§f");
                    player.sendMessage("§aOdkaz na nákup VotePassu+:");
                    player.sendMessage("§fhttps://store.craftmania.cz/category/252215");
                    player.sendMessage("§f");
                    player.sendMessage(ChatColor.AQUA + Constants.CHAT_BOXES);
                }));

        final Pagination pagination = contents.pagination();
        final ArrayList<ClickableItem> items = new ArrayList<>();

        VoteManager.getVotePassRewards().forEach(votePassReward -> {
            if (votePassReward.isEmpty()) {
                items.add(ClickableItem.empty(new ItemBuilder(Material.AIR).build()));
                return;
            }
            if (player.hasPermission("crafteconomy.votepass.reward." + votePassReward.getId())) { // Již vybraná odměna
                final ArrayList<String> lore = new ArrayList<>();
                lore.add("§7Tuto odměnu jsi si již vybral:");
                lore.addAll(votePassReward.getDescription());
                items.add(ClickableItem.empty(new ItemBuilder(Material.IRON_NUGGET).setCustomModelData(100001)
                        .setName("§a" + votePassReward.getName() + " (" + votePassReward.getRequiredVotes() + " hlasů)").setLore(lore).build()));
                return;
            }
            if (votePassReward.isRequireVotepassPlus() && !player.hasPermission("crafteconomy.votepass.access")) { // Nemá zakoupený VotePass+
                final ArrayList<String> lore = new ArrayList<>();
                lore.add("§7Při zakoupení VotePass+ by jsi získal:");
                lore.addAll(votePassReward.getDescription());
                lore.add("§f ");
                lore.add("§dK zpřístupnění musíš vlastnit VotePass+");
                items.add(ClickableItem.empty(new ItemBuilder(Material.IRON_NUGGET).setCustomModelData(100005)
                        .setName("§c" + votePassReward.getName() + " (" + votePassReward.getRequiredVotes() + " hlasů)").setLore(lore).build()));
                return;
            }
            if (craftPlayer.getVotePassVotes() < votePassReward.getRequiredVotes()) { // Malá počet hlasů
                final ArrayList<String> lore = new ArrayList<>();
                lore.add("§7Získáš:");
                lore.addAll(votePassReward.getDescription());
                lore.add("§f ");
                lore.add("§eNemáš dostatečný počet hlasů.");
                items.add(ClickableItem.empty(new ItemBuilder(Material.IRON_NUGGET).setCustomModelData(100008)
                        .setName("§6" + votePassReward.getName() + " (" + votePassReward.getRequiredVotes() + " hlasů)").setLore(lore).build()));
                return;
            }
            if (votePassReward.getCommands().size() > 0 && votePassReward.getCommands().stream().noneMatch(serverCommand -> serverCommand.getServerType() == Main.getServerType())
                    && votePassReward.getCommands().stream().noneMatch(serverCommand -> serverCommand.getServerType() == ServerType.UNKNOWN)) { // Je na serveru, kde se odměna nadí vybrat
                final ArrayList<String> lore = new ArrayList<>();
                lore.add("§7Získáš:");
                lore.addAll(votePassReward.getDescription());
                lore.add("§f ");
                lore.add("§eOdměnu nelze vybrat na tomto serveru.");
                items.add(ClickableItem.empty(new ItemBuilder(Material.IRON_NUGGET).setCustomModelData(100011)
                        .setName("§a" + votePassReward.getName() + " (" + votePassReward.getRequiredVotes() + " hlasů)").setLore(lore).build()));
                return;
            }
            if (votePassReward.isTemporaryDisabled()) {
                final ArrayList<String> lore = new ArrayList<>();
                lore.add("§7Item, je dočasně deaktivovaný.");
                lore.add("§7Pravděpodobně z důvodu bugu nebo uveřejnění.");
                lore.add("§cNelze jej vybrat.");
                items.add(ClickableItem.empty(new ItemBuilder(Material.BARRIER)
                        .setName("§c" + votePassReward.getName() + " (" + votePassReward.getRequiredVotes() + " hlasů)").setLore(lore).build()));
                return;
            }
            final ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Získáš:");
            lore.addAll(votePassReward.getDescription());
            ItemStack item = new ItemBuilder(votePassReward.getItemMaterial()).setName("§a" + votePassReward.getName() + " (" + votePassReward.getRequiredVotes() + " hlasů)")
                    .setLore(lore).build();
            items.add(ClickableItem.of(item, inventoryClickEvent -> {
                if (votePassReward.isRequireSlotInInventory()) {
                    if (hasFullInventory(player)) {
                        player.sendMessage("§c§l[!] §cMáš plný inventář, nelze si vyzvednout odměnu.");
                        return;
                    }
                }

                if (votePassReward.getCraftCoins() > 0) {
                    EconomyAPI.CRAFT_COINS.give(player, votePassReward.getCraftCoins());
                }

                if (votePassReward.getCraftTokens() > 0) {
                    EconomyAPI.CRAFT_TOKENS.give(player, votePassReward.getCraftTokens());
                }

                if (votePassReward.getServerExperience() > 0) {
                    LevelAPI.addExp(player, manager.getExperienceByServer(), (int) votePassReward.getServerExperience());
                }

                votePassReward.getCommands().forEach((serverCommand -> {
                    if (serverCommand.getServerType() == Main.getServerType() || serverCommand.getServerType() == ServerType.UNKNOWN) {
                        Logger.info("VotePass příkaz: " + serverCommand.getCommand());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), serverCommand.getCommand().replace("%player%", player.getName()).replace("%server%", Main.getServerType().name().toLowerCase()));
                    }
                }));

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set crafteconomy.votepass.reward." + votePassReward.getId());
                player.sendMessage("§e§l[!] §eVybral jsi si odměnu z VotePassu §f" + votePassReward.getName());
                player.closeInventory();
            }));
        });

        ClickableItem[] c = new ClickableItem[items.size()];
        c = items.toArray(c);
        pagination.setItems(c);
        pagination.setItemsPerPage(14);

        if (items.size() > 0 && !pagination.isLast()) {
            contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.IRON_NUGGET).setCustomModelData(100010).setName("§6Další stránka").build(), e -> {
                contents.inventory().open(player, pagination.next().getPage());
            }));
        }
        if (!pagination.isFirst()) {
            contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.IRON_NUGGET).setCustomModelData(100003).setName("§6Předchozí stránka").build(), e -> {
                contents.inventory().open(player, pagination.previous().getPage());
            }));
        }

        SlotIterator slotIterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 1);
        slotIterator = slotIterator.blacklist(SlotPos.of(0, 8)).allowOverride(false);
        pagination.addToIterator(slotIterator);



    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private boolean hasFullInventory(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}
