package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.spigot.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.crafteconomy.utils.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ProfileSettingsGUI implements InventoryProvider {

    private Pagination pagination;
    public static HashMap<Integer, String> joinMessages = new HashMap<Integer, String>(){{
        put(1, "{player} se pripojil na lobby");
        put(2, "Pozor! {player} prave pristal na lobby");
        put(3, "Tri! Dva! Jedna! {player} je tu!");
        put(4, "{player} je tu! Podrzte mi pivo.");
        put(5, "{player} prave prisel. Party zacala!");
        put(6, "{player} je tu a ma s sebou pizzu!");
        put(7, "{player} vas prisel znicit!");
    }};

    @Override
    public void init(Player player, InventoryContents contents) {
        pagination = contents.pagination();
        ClickableItem[] pages = new ClickableItem[2];
        pagination.setItems(pages);
        pagination.setItemsPerPage(1);

        switch (pagination.getPage()) {
            case 0: {
                openPage(player, contents, 0);
                break;
            }
            case 1: {
                openPage(player, contents, 1);
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

    // Pages

    private void openPage(Player p, InventoryContents contents, int page) {
        ItemStack enabled = createItem(Material.GREEN_STAINED_GLASS_PANE, "§a§lZapnuto", null);
        ItemStack disabled = createItem(Material.RED_STAINED_GLASS_PANE, "§c§lVypnuto", null);
        ItemStack nedostupne = createItem(Material.BARRIER, "§c§lNedostupné", null);
        ItemStack zpet = createItem(Material.RED_BED, "§cZpět do menu", null);
        ItemStack nextPage = createItem(Material.ARROW, "§eDalší strana", null);
        ItemStack previousPage = createItem(Material.ARROW, "§ePředchozí strana", null);

        ProfileGUI.makeLines(contents);

        switch (page) {
            case 0: {
                ItemStack fly = createItem(Material.ELYTRA, "§e§lFly", Arrays.asList("§7Nastavuje FLY na lobby serverech.", "§7Fly dostaneš při každém",
                        "§7vstupu na lobby", "", "§cVyžaduje Global VIP!"));
                ItemStack player = createItem(Material.LEGACY_WATCH, "§e§lViditelnost hráčů", Arrays.asList("§7Nastavuje zobrazení", "§7hráčů na lobby."));
                ItemStack part = createItem(Material.REDSTONE, "§e§lParticles", Arrays.asList("§7Viditelnost efektů", "", "§cDočasně nefunkční!"));
                ItemStack gadgets = createItem(Material.PISTON, "§e§lGadgets", Arrays.asList("§7Nastavuje zda na tebe", "§7budou fungovat gadget lobby."));
                ItemStack speed = createItem(Material.GOLDEN_BOOTS, "§e§lSpeed", Arrays.asList("§7Povoluje rychlost chození", "§7na lobby."));
                ItemStack novinky = createItem(Material.MAP, "§e§lReklama", Arrays.asList("§7Nastavuje zobrazovaní reklamy", "§7na VIP na MiniGames.", "", "§cVyžaduje Global VIP!"));
                ItemStack deathMessages = createItem(Material.BLAZE_POWDER, "§e§lDeath zprávy", Arrays.asList("§7Nastavuje zobrazení smrtí", "§7hráčů.", "", "§cFunguje pouze na Survival serverech", "§e§l[*] §eZměny se projeví až po odpojení a připojení!"));
                ItemStack joinMessage = createItem(Material.BOOK, "§e§lZpráva při připojení", Arrays.asList("§7Pokud se připojíš", "§7ostatní o tom budou vědět.",
                        "", "§bVybrana zprava:", "§f" + formatJoinMessageWithoutColors(Main.getInstance().getMySQL().getSettings(p, "lobby_joinbroadcast_message"), p),
                        "§7", "§eKliknutim si vyberes zpravu"));
                ItemStack joinSound = null;
                try {
                    joinSound = createItem(Material.NOTE_BLOCK, "§e§lZvuk při připojení", Arrays.asList("§7Pokud se připojíš", "§7zazní zvuk.",
                            "§7Vybraný zvuk: " + Main.getInstance().getMySQL().getSettingsString(p, "lobby_joinbroadcast_sound")
                                    .replace("ENTITY_EXPERIENCE_ORB_PICKUP", "EXP ORB PICKUP")
                                    .replace("BLOCK_ANVIL_FALL", "ANVIL FALL")
                                    .replace("BLOCK_GLASS_BREAK", "GLASS BREAK")
                                    .replace("ENTITY_ITEM_PICKUP", "ITEM PICKUP")
                                    .replace("ENTITY_ZOMBIE_HURT", "ZOMBIE HURT")
                            , "", "§eKliknutím si vybereš zvuk"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Main.getInstance().sendSentryException(e);
                    Logger.danger("getSettingsString() z MySQL navrátilo null při požadavku na \"lobby_joinbroadcast_sound\"?!");
                }

                //Předchozí / Zpět / Další
                contents.set(4, 4, ClickableItem.of(zpet, e -> {
                    SmartInventory.builder().size(5, 9).title("Profile").provider(new ProfileGUI()).build().open(p);
                }));
                contents.set(4, 5, ClickableItem.of(nextPage, e -> {
                    contents.inventory().open(p, pagination.next().getPage());
                }));

                //Nastavení
                contents.set(1, 0, ClickableItem.empty(fly));
                contents.set(1, 1, ClickableItem.empty(player));
                contents.set(1, 2, ClickableItem.empty(part));
                contents.set(1, 3, ClickableItem.empty(gadgets));
                contents.set(1, 4, ClickableItem.empty(speed));
                contents.set(1, 5, ClickableItem.empty(novinky));
                contents.set(1, 6, ClickableItem.empty(deathMessages));
                contents.set(1, 7, ClickableItem.of(joinMessage, e -> {
                    SmartInventory.builder().size(3, 9).title("Profile settings - Join message").provider(new ProfileSettingsGUISelectionLobbyMessage()).build().open(p);
                }));
                contents.set(1, 8, ClickableItem.of(joinSound, e -> {
                    SmartInventory.builder().size(3, 9).title("Profile settings - Join sound").provider(new ProfileSettingsGUISelectionLobbySound()).build().open(p);
                }));

                //Akce po kliknutí na nastavení
                contents.set(2, 0, ClickableItem.of((getSetting(p, "lobby_fly") == 1 ? enabled : disabled), e -> { //Fly
                    if (p.hasPermission("craftlobby.vip.fly")) {
                        if (contents.get(2, 0).get().getItem() == enabled) {
                            Main.getInstance().getMySQL().updateSettings(p, "lobby_fly", 0);
                            if (Main.getServerType() == ServerType.LOBBY) {
                                p.setAllowFlight(false);
                                p.setFlying(false);
                            }
                            p.sendMessage("§c§l[!] §cFly na lobby bylo deaktivováno!");
                        } else {
                            Main.getInstance().getMySQL().updateSettings(p, "lobby_fly", 1);
                            if (Main.getServerType() == ServerType.LOBBY) {
                                p.setAllowFlight(true);
                                p.setFlying(true);
                            }
                            p.sendMessage("§e§l[*] §eFly na lobby bylo aktivováno!");
                        }
                    } else {
                        p.sendMessage("§c§l[!] §cK použití této funkce potřebuješ §fGlobal VIP");
                    }
                    contents.inventory().close(p);
                }));
                // Obrácené hodnoty? FIXME
                contents.set(2, 1, ClickableItem.of((getSetting(p, "lobby_players") == 1 ? enabled : disabled), e -> { //Viditelnost hráčů na lobby
                    if (contents.get(2, 1).get().getItem() == disabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_players", 1);
                        p.sendMessage("§e§l[*] §eZobrazování hráčů zapnuto!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_players", 0);
                        p.sendMessage("§c§l[!] §cZobrazovani hracu vypnuto!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 2, ClickableItem.of((getSetting(p, "lobby_particles") == 1 ? enabled : disabled), e -> { //Viditelnost particlů na lobby
                    if (contents.get(2, 2).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_particles", 0);
                        p.sendMessage("§c§l[!] §cZobrazovaní efektu vypnuto!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_particles", 1);
                        p.sendMessage("§e§l[*] §eZobrazovaní efektu zapnuto!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 3, ClickableItem.of((getSetting(p, "lobby_gadgets") == 1 ? enabled : disabled), e -> { //Působení gadgetů na hráče
                    if (contents.get(2, 3).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_gadgets", 0);
                        p.sendMessage("§c§l[!] §cGadgety již na tebe nebudou reagovat!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_gadgets", 1);
                        p.sendMessage("§e§l[*] §eGadgety nyní na tebe budou reagovat!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 4, ClickableItem.of((getSetting(p, "lobby_speed") == 1 ? enabled : disabled), e -> { //Rychlost na lobby
                    if (contents.get(2, 4).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_speed", 0);
                        p.sendMessage("§c§l[!] §cRychlost byla nastavena na základní!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_speed", 1);
                        p.sendMessage("§e§l[*] §eRychlost byla nastavena na 2x rychlejší!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 5, ClickableItem.empty(nedostupne));
                contents.set(2, 6, ClickableItem.of((getSetting(p, "death_messages") == 1 ? enabled : disabled), e -> { //Deathzprávy
                    if (contents.get(2, 6).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "death_messages", 0);
                        p.sendMessage("§c§l[!] §cZablokováno zobrazování zpráv o smrti!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "death_messages", 1);
                        p.sendMessage("§e§l[*] §eNyni uvidíš v chatu zprávy o smrti hráčů!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 7, ClickableItem.of((getSetting(p, "lobby_joinbroadcast_enabled") == 1 ? enabled : disabled), e -> { //Zpráva po připojení
                    if (p.hasPermission("craftlobby.vip.joinbroadcast-message")) {
                        if (contents.get(2, 7).get().getItem() == enabled) {
                            Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_enabled", 0);
                            p.sendMessage("§c§l[!] §cZpráva při připojení byla deaktivována!");
                        } else {
                            Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_enabled", 1);
                            p.sendMessage("§e§l[*] §eZpráva při připojení byla aktivována!");
                        }
                    } else {
                        p.sendMessage("§c§l[!] §cK použití této funkce potřebuješ §fGlobal Emerald VIP");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 8, ClickableItem.of((getSetting(p, "lobby_joinbroadcast_sound_enabled") == 1 ? enabled : disabled), e -> { //Zvuk připojení na lobby
                    if (p.hasPermission("craftlobby.vip.joinbroadcast-change-sound")) {
                        if (contents.get(2, 8).get().getItem() == enabled) {
                            Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound_enabled", 0);
                            p.sendMessage("§c§l[!] §cZvuk při připojení byl deaktivovan!");
                        } else {
                            Main.getInstance().getMySQL().updateSettings(p, "lobby_joinbroadcast_sound_enabled", 1);
                            p.sendMessage("§e§l[*] §eZvuk při připojení byl aktivovan!");
                        }
                    } else {
                        p.sendMessage("§c§l[!] §cK použití této funkce potřebuješ §fGlobal Emerald VIP");
                    }
                    contents.inventory().close(p);
                }));
                break;
            }
            case 1: {
                //Předchozí / Zpět / Další
                contents.set(4, 3, ClickableItem.of(previousPage, e -> {
                    contents.inventory().open(p, pagination.previous().getPage());
                }));
                contents.set(4, 4, ClickableItem.of(zpet, e -> {
                    SmartInventory.builder().size(5, 9).title("Profile").provider(new ProfileGUI()).build().open(p);
                }));

                ItemStack disableChat = createItem(Material.WRITABLE_BOOK , "§e§lVypnutí zpráv v chatu", Arrays.asList("§7Nebudeš dostávat", "§7zprávy v chatu.", "", "§e§l[*] §eZměny se projeví až po odpojení a připojení!"));
                ItemStack disableScoreboard = createItem(Material.GOLD_INGOT, "§e§lZobrazení scoreboardu", Arrays.asList("§7Budeš vidět", "§7tabulku vpravo.", "", "§e§l[*] §eZměna se projeví až po odpojení a připojení!"));
                ItemStack sellBackpack = SkullCreator.createHeadFromData("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRjYzZlYjQwZjNiYWRhNDFlNDMzOTg4OGQ2ZDIwNzQzNzU5OGJkYmQxNzVjMmU3MzExOTFkNWE5YTQyZDNjOCJ9fX0=",
                        "§e§lProdávání věcí v backpacku",
                        Arrays.asList(
                                "§7Dovoluje ti vypnout / zapnout prodávání v backpacku.",
                                "",
                                "§cFunguje pouze na Prisonu",
                                "§e§l[*] §eZměny se projeví až po odpojení a připojení!"
                        ));
                ItemStack sellOnPickaxe = createItem(Material.DIAMOND_PICKAXE, "§e§lProdávání pomocí krumpáče",
                        Arrays.asList(
                                "§7Dovoluje ti vypnout / zapnout prodávání pravým",
                                "§7kliknutím s krumpáčem",
                                "",
                                "§cFunguje pouze na Prisonu",
                                "§e§l[*] §eZměny se projeví až po odpojení a připojení!"
                        ));
                ItemStack gender = createItem(Material.PLAYER_HEAD, "§e§lPohlaví",
                        Arrays.asList(
                                "§7Dovoluje ti nastavit svoje pohlaví",
                                "",
                                "§e§l[*] §eZměny se projeví až po odpojení a připojení!"
                        ));
                ItemStack currentGender = getCurrentGenderItemStack(p);
                // Deprecated
                //ItemStack chatSuggestions = createItem(Material.HEART_OF_THE_SEA, "§e§lNapovidani v chatu", Arrays.asList("§7Povolenim se ti budou", "§7zobrazovat v chatu napovedy", "§7pro prikazy §aod MC 1.13."));

                //Nastaveni
                contents.set(1, 0, ClickableItem.empty(disableChat));
                //contents.set(1, 1, ClickableItem.empty(chatSuggestions));

                //Akce po kliknuti na nastaveni
                contents.set(2, 0, ClickableItem.of((getSetting(p, "disabled_chat") == 1 ? enabled : disabled), e -> { //Vypnutí chatu
                    if (p.hasPermission("craftmanager.vip.disablechat") && !p.hasPermission("craftmania.at")) {
                        if (contents.get(2, 0).get().getItem() == enabled) {
                            Main.getInstance().getMySQL().updateSettings(p, "disabled_chat", 0);
                            p.sendMessage("§e§l[*] §eZpavy v chatu zapnuty!");
                        } else {
                            Main.getInstance().getMySQL().updateSettings(p, "disabled_chat", 1);
                            p.sendMessage("§c§l[!] §cZpravy v chatu vypnuty!");
                        }
                        contents.inventory().close(p);
                    } else {
                        p.sendMessage("§c§l[!] §cNa toto nemas dostatecna prava!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(1, 1, ClickableItem.empty(disableScoreboard));
                contents.set(2, 1, ClickableItem.of((getSetting(p, "show_scoreboard") == 1 ? enabled : disabled), e -> {
                    if (contents.get(2, 1).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "show_scoreboard", 0);
                        p.sendMessage("§c§l[!] §cZobrazovaní scoreboardu vypnuto!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "show_scoreboard", 1);
                        p.sendMessage("§e§l[*] §eZobrazovaní scoreboardu zapnuto!");
                    }
                    contents.inventory().close(p);
                }));

                contents.set(1, 2, ClickableItem.empty(gender));
                contents.set(2, 2, ClickableItem.of(currentGender, e -> {
                    SmartInventory.builder().size(3, 9).title("Profile settings - Pohlaví").provider(new ProfileSettingsGUIGender()).build().open(p);
                }));

                contents.set(1, 3, ClickableItem.empty(sellBackpack));
                contents.set(2, 3, ClickableItem.of((getSetting(p, "prison_sell_backpack") == 1 ? enabled : disabled), e -> {
                   if (contents.get(2, 3).get().getItem() == enabled) {
                       Main.getInstance().getMySQL().updateSettings(p, "prison_sell_backpack", 0);
                       p.sendMessage("§c§l[!] §cPorádávní backpacku vypnuto!");
                   } else {
                       Main.getInstance().getMySQL().updateSettings(p, "prison_sell_backpack", 1);
                       p.sendMessage("§e§l[*] §ePorádávní backpacku zapnuto!");
                   }
                   contents.inventory().close(p);
                }));

                contents.set(1, 4, ClickableItem.empty(sellOnPickaxe));
                contents.set(2, 4, ClickableItem.of((getSetting(p, "prison_sell_on_pickaxe") == 1 ? enabled : disabled), e -> {
                    if (contents.get(2, 4).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "prison_sell_on_pickaxe", 0);
                        p.sendMessage("§c§l[!] §cPorádávní pravým kliknutím s krumpáčem vypnuto!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "prison_sell_on_pickaxe", 1);
                        p.sendMessage("§e§l[*] §ePorádávní pravým kliknutím s krumpáčem zapnuto!");
                    }
                    contents.inventory().close(p);
                }));
                break;
            }
        }
    }

    // Utils
    private ItemStack createItem(Material material, String itemName, List<String> itemLore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }

    private int getSetting(Player p, String setting) {
        return Main.getInstance().getMySQL().getSettings(p, setting);
    }

    private ItemStack getCurrentGenderItemStack(Player p) {
        int genderNumber = Main.getInstance().getMySQL().getGender(p);
        String message = "§7Tvé momentální pohlaví je: ";
        Material material = null;
        switch (genderNumber) {
            case 0:
                message += "nechci uvádět";
                material = Material.LIGHT_GRAY_WOOL;
                break;
            case 1:
                message += "muž";
                material = Material.BLUE_WOOL;
                break;
            case 2:
                message += "žena";
                material = Material.PINK_WOOL;
                break;
        }
        return createItem(material, "§e§lNastavit pohlaví",
                Arrays.asList(
                        message,
                        "",
                        "§7Kliknutím si nastavíš pohlaví"
                ));
    }

    public static String formatJoinMessageWithoutColors(Integer i, Player p) {
        String entry = joinMessages.get(i);
        String message;
        message = entry.replace("{player}", ChatColor.YELLOW + "" + ChatColor.BOLD + p.getName() + ChatColor.GRAY);
        return ChatColor.GRAY + message;
    }
}
