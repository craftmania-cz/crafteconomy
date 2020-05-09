package cz.craftmania.crafteconomy.menu;

import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import cz.craftmania.craftcore.spigot.inventory.builder.content.Pagination;
import cz.craftmania.crafteconomy.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ProfileGUI implements InventoryProvider {

    private Pagination pagination;

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
        ItemStack pouzeLobby = createItem(Material.BARRIER, "§c§lNastavit lze pouze na lobby", null);
        ItemStack zpet = createItem(Material.ARROW, "§eZpět", null);
        ItemStack nextPage = createItem(Material.ARROW, "§eDalší strana", null);
        ItemStack previousPage = createItem(Material.ARROW, "§ePředchozí strana", null);

        switch (page) {
            case 0: {
                ItemStack fly = createItem(Material.ELYTRA, "§e§lFly", Arrays.asList("§7Nastavuje FLY na lobby serverech.", "§7Fly dostanes pri kazdem",
                        "§7vstupu na lobby", "", "§cVyzaduje MiniGames VIP nebo globalni Obsidian VIP!"));
                ItemStack player = createItem(Material.LEGACY_WATCH, "§e§lViditelnost hracu", Arrays.asList("§7Nastavuje zobrazeni", "§7hracu na lobby."));
                ItemStack pets = createItem(Material.BONE, "§e§lViditelnost pets", Arrays.asList("§7Nastavuje zobrazeni", "§7pets na lobby."));
                ItemStack part = createItem(Material.REDSTONE, "§e§lParticles", Arrays.asList("§7Viditelnost efektu", "", "§cDocasne nefunguje na vsechny!"));
                ItemStack gadgets = createItem(Material.PISTON, "§e§lGadgets", Arrays.asList("§7Nastavuje zda na tebe", "§7budou fungovat gadget lobby."));
                ItemStack speed = createItem(Material.GOLDEN_BOOTS, "§e§lSpeed", Arrays.asList("§7Povoluje rychlost chozeni", "§7na lobby."));
                ItemStack novinky = createItem(Material.MAP, "§e§lReklama", Arrays.asList("§7Nastavuje zobrazovani reklamy", "§7na VIP na MiniGames.", "", "§cVyzaduje MiniGames VIP!"));
                ItemStack deathMessages = createItem(Material.BLAZE_POWDER, "§e§lDeath zpravy", Arrays.asList("§7Nastavuje zobrazeni smrti", "§7hracu.", "", "§cFunguje pouze na Survival serverech", "§e§l[*] §eZměny se projeví až po odpojení a připojení!"));
                ItemStack notify = createItem(Material.JUKEBOX, "§e§lOznameni o oznaceni", Arrays.asList("§7Pokud te nekdo oznaci", "§7v chatu, server te", "§7upozorni cinknutim."));

                //Předchozí / Zpět / Další
                contents.set(4, 4, ClickableItem.of(zpet, e -> {
                    //TODO: Profile menu
                    contents.inventory().close(p);
                }));
                contents.set(4, 5, ClickableItem.of(nextPage, e -> {
                    //TODO: Profile menu
                    contents.inventory().open(p, pagination.next().getPage());
                }));

                //Nastavení
                contents.set(1, 0, ClickableItem.empty(fly));
                contents.set(1, 1, ClickableItem.empty(player));
                contents.set(1, 2, ClickableItem.empty(pets));
                contents.set(1, 3, ClickableItem.empty(part));
                contents.set(1, 4, ClickableItem.empty(gadgets));
                contents.set(1, 5, ClickableItem.empty(speed));
                contents.set(1, 6, ClickableItem.empty(novinky));
                contents.set(1, 7, ClickableItem.empty(deathMessages));
                contents.set(1, 8, ClickableItem.empty(notify)); //TODO: Kliknutí a výběr zvuku

                //Akce po kliknutí na nastavení
                contents.set(2, 0, ClickableItem.empty(pouzeLobby)); //fly
                // Obrácené hodnoty? FIXME
                contents.set(2, 1, ClickableItem.of((getSetting(p, "lobby_players") == 1 ? disabled : enabled), e -> { //Viditelnost hráčů na lobby
                    if (contents.get(2, 1).get().getItem() == disabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_players", 0);
                        p.sendMessage("§e§l[*] §eZobrazování hráčů zapnuto!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_players", 1);
                        p.sendMessage("§c§l[!] §cZobrazovani hracu vypnuto!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 2, ClickableItem.empty(nedostupne));
                contents.set(2, 3, ClickableItem.of((getSetting(p, "lobby_particles") == 1 ? enabled : disabled), e -> { //Viditelnost particlů na lobby
                    if (contents.get(2, 3).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_particles", 0);
                        p.sendMessage("§c§l[!] §cZobrazovani efektu vypnuto!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_particles", 1);
                        p.sendMessage("§e§l[*] §eZobrazovani efektu zapnuto!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 4, ClickableItem.of((getSetting(p, "lobby_gadgets") == 1 ? enabled : disabled), e -> { //Působení gadgetů na hráče
                    if (contents.get(2, 4).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_gadgets", 0);
                        p.sendMessage("§c§l[!] §cGadgety jiz na tebe nebudou reagovat!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_gadgets", 1);
                        p.sendMessage("§e§l[*] §eGadgety nyni na tebe budou reagovat!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 5, ClickableItem.of((getSetting(p, "lobby_speed") == 1 ? enabled : disabled), e -> { //Rychlost na lobby
                    if (contents.get(2, 5).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_speed", 0);
                        p.sendMessage("§c§l[!] §cRychlost byla nastavena na zakladni!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "lobby_speed", 1);
                        p.sendMessage("§e§l[*] §eRychlost byla nastavena na 2x rychlejsi!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 6, ClickableItem.empty(nedostupne));
                contents.set(2, 7, ClickableItem.of((getSetting(p, "death_messages") == 1 ? enabled : disabled), e -> { //Deathzprávy
                    if (contents.get(2, 7).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "death_messages", 0);
                        p.sendMessage("§c§l[!] §cZablokovano zobrazovani zprav o smrti!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "death_messages", 1);
                        p.sendMessage("§e§l[*] §eNyni uvidis v chatu zpravy o smrti hracu!");
                    }
                    contents.inventory().close(p);
                }));
                contents.set(2, 8, ClickableItem.of((getSetting(p, "mention_notify") == 1 ? enabled : disabled), e -> { //Mention notify
                    if (contents.get(2, 8).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "mention_notify", 0);
                        p.sendMessage("§c§l[!] §cNyni ti oznaceni nebude cinkat!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "mention_notify", 1);
                        p.sendMessage("§e§l[*] §eZapnul jsi cinkani pri oznaceni v chatu!");
                    }
                    contents.inventory().close(p);
                }));
                break;
            }
            case 1: {
                //Předchozí / Zpět / Další
                contents.set(4, 3, ClickableItem.of(previousPage, e -> {
                    //TODO: Profile menu
                    contents.inventory().open(p, pagination.previous().getPage());
                }));
                contents.set(4, 4, ClickableItem.of(zpet, e -> {
                    //TODO: Profile menu
                    contents.inventory().close(p);
                }));

                ItemStack disableChat = createItem(Material.LEGACY_BOOK_AND_QUILL, "§e§lVypnuti zprav v chatu", Arrays.asList("§7Nebudes dostavat", "§7zpravy v chatu.", "", "§e§l[*] §eZměny se projeví až po odpojení a připojení!"));
                ItemStack chatSuggestions = createItem(Material.HEART_OF_THE_SEA, "§e§lNapovidani v chatu", Arrays.asList("§7Povolenim se ti budou", "§7zobrazovat v chatu napovedy", "§7pro prikazy §aod MC 1.13."));

                //Nastaveni
                contents.set(1, 0, ClickableItem.empty(disableChat));
                contents.set(1, 1, ClickableItem.empty(chatSuggestions));

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
                contents.set(2, 1, ClickableItem.empty(nedostupne)); //Chat suggestce příkazů
                //FIXME: Nastavení disabled_chat_suggestions neexistuje v SQL?
                /*
                contents.set(2, 1, ClickableItem.of((getSetting(p, "disabled_chat_suggestions") == 1 ? enabled : disabled), e -> {
                    if (contents.get(2, 1).get().getItem() == enabled) {
                        Main.getInstance().getMySQL().updateSettings(p, "disabled_chat_suggestions", 0);
                        p.sendMessage("§e§l[*] §eNyni se ti budou zobrazovat napovedy v chatu!");
                    } else {
                        Main.getInstance().getMySQL().updateSettings(p, "disabled_chat_suggestions", 1);
                        p.sendMessage("§c§l[!] §cNapovedy v chatu se ti jiz nebudou zobrazovat.");
                    }
                    p.sendMessage("§c§l[!] §cK plne deaktivaci jdi do lobby a zpet!");
                    contents.inventory().close(p);
                }));*/
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
}
