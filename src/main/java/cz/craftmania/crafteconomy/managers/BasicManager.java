package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.PlayerCreateCcomunityProfileEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Constants;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * BasicManager spravuje základní data hráčů v CraftEconomy
 */
public class BasicManager {

    public static HashMap<Player, CraftPlayer> players = new HashMap<>();

    public static CraftPlayer loadPlayerData(final Player player) {
        CraftPlayer cp = getOrRegisterPlayer(player);
        players.put(player, cp);
        return cp;
    }

    public static HashMap<Player, CraftPlayer> getCraftPlayersCache() {
        return players;
    }

    /**
     * Vrací {@link CraftPlayer} dle zadaného objektu hráče
     * @param p {@link Player} objekct hráče
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    public CraftPlayer getCraftPlayer(@NonNull Player p) {
        return players.values().stream().filter(cp -> cp.getPlayer() == p).findFirst().orElse(null);
    }

    /**
     * Vrací {@link CraftPlayer} dle zadaného stringu
     * @param name Nick hráče
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    public CraftPlayer getCraftPlayer(@NonNull String name) {
        return players.values().stream().filter(cp -> cp.getPlayer().getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Vrací {@link CraftPlayer} dle zadaného {@link UUID}
     * @param uuid UUID hráče
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    public CraftPlayer getCraftPlayer(@NonNull UUID uuid) {
        return players.values().stream().filter(cp -> cp.getPlayer().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * Registruje hráče do SQL, základní kontroly změny nicku
     * @param player Hráč
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    private static CraftPlayer getOrRegisterPlayer(@NonNull final Player player) {
        CraftPlayer cp = null;
        try {
            if (!Main.getInstance().getMySQL().hasData(player)) {
                // Pokud hrac neni vubec v SQL, tak se provede register
                if (Main.getInstance().isRegisterEnabled()) {

                    // Vytvoreni
                    Main.getInstance().getMySQL().createCcominutyProfile(player);

                    // Event
                    final PlayerCreateCcomunityProfileEvent event = new PlayerCreateCcomunityProfileEvent(player);
                    Bukkit.getPluginManager().callEvent(event);
                }
            } else {
                // Kontrola zda si originalka nezměnila nick
                String sqlNick = Main.getInstance().getMySQL().getNickFromTable("player_profile", player);
                assert sqlNick != null;
                if (!sqlNick.equals(player.getName())) {
                    Main.getInstance().getMySQL().updateNickInTable("player_profile", player);
                }
                cp = Main.getInstance().getMySQL().getCraftPlayerFromSQL(player);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        }
        // Prevence proti NPE z SQL
        if (cp == null) {
            cp = new CraftPlayer(player);
        }
        return cp;
    }

    /**
     * Vrací aktuální {@link LevelType} dle nastaveného ID serveru v configu
     * @return {@link LevelType}
     */
    public LevelType getExperienceByServer(){
        ServerType server = Main.getServerType();
        switch (server) {
            case SURVIVAL:
                return LevelType.SURVIVAL_EXPERIENCE;
            case SKYBLOCK:
                return LevelType.SKYBLOCK_EXPERIENCE;
            case CREATIVE:
                return LevelType.CREATIVE_EXPERIENCE;
            case VANILLA:
                return LevelType.VANILLA_EXPERIENCE;
            case SKYCLOUD:
                return LevelType.SKYCLOUD_EXPERIENCE;
            case PRISON:
                return LevelType.PRISON_EXPERIENCE;
            case HARDCORE_VANILLA:
                return LevelType.HARDCORE_VANILLA_EXPERIENCE;
        }
        return null;
    }

    /**
     * Vrací aktuální {@link LevelType} dle nastaveného ID serveru v configu
     * @return {@link LevelType}
     */
    public LevelType getLevelByServer(){
        ServerType server = Main.getServerType();
        switch (server) {
            case SURVIVAL:
                return LevelType.SURVIVAL_LEVEL;
            case SKYBLOCK:
                return LevelType.SKYBLOCK_LEVEL;
            case CREATIVE:
                return LevelType.CREATIVE_LEVEL;
            case VANILLA:
                return LevelType.VANILLA_LEVEL;
            case SKYCLOUD:
                return LevelType.SKYCLOUD_LEVEL;
            case PRISON:
                return LevelType.PRISON_LEVEL;
            case HARDCORE_VANILLA:
                return LevelType.HARDCORE_VANILLA_LEVEL;
        }
        return null;
    }

    /**
     * Vrací typ {@link LevelType} dle stringu.
     * @param server ID serveru
     * @return {@link LevelType} když existuje, jinak null
     */
    public LevelType resolveLevelTypeByString(String server) {
        switch (server.toLowerCase()) {
            case "survival":
                return LevelType.SURVIVAL_LEVEL;
            case "skyblock":
                return LevelType.SKYBLOCK_LEVEL;
            case "creative":
                return LevelType.CREATIVE_LEVEL;
            case "vanilla":
                return LevelType.VANILLA_LEVEL;
            case "skycloud":
                return LevelType.SKYCLOUD_LEVEL;
            case "prison":
                return LevelType.PRISON_LEVEL;
            case "hardcore-vanilla":
                return LevelType.HARDCORE_VANILLA_LEVEL;
        }
        return null;
    }

    /**
     * Vrací typ {@link LevelType} dle stringu.
     * @param server ID serveru
     * @return {@link LevelType} když existuje, jinak null
     */
    public LevelType resolveExperienceTypeByString(String server) {
        switch (server.toLowerCase()) {
            case "survival":
                return LevelType.SURVIVAL_EXPERIENCE;
            case "skyblock":
                return LevelType.SKYBLOCK_EXPERIENCE;
            case "creative":
                return LevelType.CREATIVE_EXPERIENCE;
            case "vanilla":
                return LevelType.VANILLA_EXPERIENCE;
            case "skycloud":
                return LevelType.SKYCLOUD_EXPERIENCE;
            case "prison":
                return LevelType.PRISON_EXPERIENCE;
            case "hardcore-vanilla":
                return LevelType.HARDCORE_VANILLA_EXPERIENCE;
        }
        return null;
    }

    /**
     * Zkontroluje všechny rewardy podle nastaveného serveru a dá je hráči, pokud nemá.
     * @param player Zvolený hráč
     * @param level Požadovaná odměna dle levelu
     * @param announce Oznámení
     */
    public void givePlayerManualLevelReward(final Player player, final int level, final boolean announce) {
        RewardManager.getRewards().forEach(levelReward -> {
            if (levelReward.getLevel() == level) {
                this.givePlayerManualLevelReward(levelReward, player, announce);
            }
        });
    }

    /**
     * Zkontruje zda hráč vlastní daný reward, pokud ano odebere mu jej
     * @param player Zvolený hráč
     * @param level Požadovaná odměna dle levelu
     */
    public void removePlayerManualReward(final Player player, final int level) {
        RewardManager.getRewards().forEach(levelReward -> {
            if (levelReward.getLevel() == level) {
                this.removePlayerLevelReward(levelReward, player, true);
            }
        });
    }

    /**
     * Dá hráči reward podle zadaného LevelReward
     * @param level LevelReward objekt
     * @param player Zvolený hráč
     * @param announce Zda se má ukázat v chatu oznámení
     */
    public void givePlayerManualLevelReward(@NonNull LevelReward level, @NonNull Player player, boolean announce) {
        Logger.info("Davam manualni reward: Level (" + level.getLevel() + ") -> " + level.getName() + ", hrac: " + player.getName());
        if (!level.getPermissions().isEmpty()) {
            level.getPermissions().forEach(permission -> {
                Main.getAsync().runSync(() -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + permission + " " + Main.getServerType().name().toLowerCase());
                });
            });
        }

        if (level.getItems().size() >= 1) {
            AtomicBoolean announceDrop = new AtomicBoolean(false);
            level.getItems().forEach(itemStack -> {
                if (level.isRequireSlotInInventory()) {
                    if (hasFullInventory(player)) { // Když full tak drop na zem
                        player.getWorld().dropItem(player.getLocation(), itemStack);
                        announceDrop.set(true);
                    } else {
                        player.getInventory().addItem(itemStack);
                    }
                } else {
                    player.getInventory().addItem(itemStack);
                }
            });
            if (announceDrop.get()) {
                player.sendMessage("§c§l[!] §cMáš plný inventář! Itemy leží na zemi.");
            }
        }

        // Notify
        if (announce) {
            player.sendMessage("§b" + Constants.CHAT_BOXES);
            player.sendMessage("");
            player.sendMessage("§9§lOdmena za level: §f" + level.getLevel());
            level.getRewardDescription().forEach(description -> {
                player.sendMessage("§7" + description);
            });
            player.sendMessage("");
            player.sendMessage("§b" + Constants.CHAT_BOXES);
        }
    }

    /**
     * Odebere reward hráči podle zadaného LevelReward
     * @param level LevelReward objekt
     * @param player Zvolený hráč
     * @param announce Zda se má ukázat v chatu oznámení
     */
    public void removePlayerLevelReward(@NonNull LevelReward level, @NonNull Player player, boolean announce) {
        if (!level.getPermissions().isEmpty()) {
            level.getPermissions().forEach(permission -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset " + permission + " " + Main.getServerType().name().toLowerCase());
            });
        }

        // Notify
        if (announce) {
            player.sendMessage("§c" + Constants.CHAT_BOXES);player.sendMessage("");
            player.sendMessage("§9§lOdmena za level: §f" + level.getLevel());
            player.sendMessage("§7Ti byla odebrana z duvodu bugu!");
            player.sendMessage("");
            player.sendMessage("§c" + Constants.CHAT_BOXES);
        }
    }

    private boolean hasFullInventory(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}
