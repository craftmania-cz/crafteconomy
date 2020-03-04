package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.PlayerCreateCcomunityProfileEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

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

    public CraftPlayer getCraftPlayer(Player p) {
        return players.values().stream().filter(cp -> cp.getPlayer() == p).findFirst().orElse(null);
    }

    public CraftPlayer getCraftPlayer(String name) {
        return players.values().stream().filter(cp -> cp.getPlayer().getName().equals(name)).findFirst().orElse(null);
    }

    public CraftPlayer getCraftPlayer(UUID uuid) {
        return players.values().stream().filter(cp -> cp.getPlayer().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }


    private static CraftPlayer getOrRegisterPlayer(final Player player) {
        CraftPlayer cp = null;
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

        // Prevence proti NPE z SQL
        if (cp == null) {
            cp = new CraftPlayer(player);
        }
        return cp;
    }

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
        }
        return null;
    }

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
        }
        return null;
    }

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
        }
        return null;
    }

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
        }
        return null;
    }

    /**
     * Dá hráči reward podle zadaného levelu!
     * @param player Zvolený hráč
     * @param level Požadovaná odměna dle levelu
     */
    public void givePlayerLevelReward(final Player player, final int level) {
        ProprietaryManager.getServerLevelRewardsList().forEach(levelReward -> {
            if (levelReward.getLevel() == level) {
                this.givePlayerLevelReward(levelReward, player);
            }
        });
    }

    public void removePlayerReward(final Player player, final int level) {
        ProprietaryManager.getServerLevelRewardsList().forEach(levelReward -> {
            if (levelReward.getLevel() == level) {
                this.removePlayerLevelReward(levelReward, player);
            }
        });
    }

    /**
     * Dá hráči reward podle zadaného LevelReward
     * @param level LevelReward objekt
     * @param player Zvolený hráč
     */
    public void givePlayerLevelReward(LevelReward level, Player player) {
        if (level == null || player == null) {
            return;
        }
        if (!level.getPermissions().isEmpty()) {
            level.getPermissions().forEach(permission -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + permission + " " + Main.getServerType().name().toLowerCase());
            });
        }

        // Notify
        player.sendMessage("§b\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        player.sendMessage("");
        player.sendMessage("§9§lOdmena za level: §f" + level.getLevel());
        level.getRewardDescription().forEach(description -> {
            player.sendMessage("§7" + description);
        });
        player.sendMessage("");
        player.sendMessage("§b\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
    }

    public void removePlayerLevelReward(LevelReward level, Player player) {
        if (level == null || player == null) {
            return;
        }
        if (!level.getPermissions().isEmpty()) {
            level.getPermissions().forEach(permission -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset " + permission + " " + Main.getServerType().name().toLowerCase());
            });
        }

        // Notify
        player.sendMessage("§c\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        player.sendMessage("");
        player.sendMessage("§9§lOdmena za level: §f" + level.getLevel());
        player.sendMessage("§7Ti byla odebrana z duvodu bugu!");
        player.sendMessage("");
        player.sendMessage("§c\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
    }


}
