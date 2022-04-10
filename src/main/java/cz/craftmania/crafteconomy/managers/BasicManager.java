package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.PlayerCreateCcomunityProfileEvent;
import cz.craftmania.crafteconomy.exceptions.CraftEconomyLoadPlayerDataFailed;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.LevelReward;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Constants;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.craftlibs.utils.ChatInfo;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * BasicManager spravuje základní data hráčů v CraftEconomy
 */
public class BasicManager {

    //TODO: Private?
    public static HashMap<Player, CraftPlayer> players = new HashMap<>();

    /**
     * Načtení dat do interní cache.
     * @param player Bukkit objekt hráče
     */
    @Nullable
    public static CraftPlayer loadPlayerData(final Player player) {
        try {
            CraftPlayer cp = getOrRegisterPlayer(player).get();
            players.put(player, cp);
            return cp;
        } catch (ExecutionException | InterruptedException loadError) {
            loadError.printStackTrace();
        }
        return null;
    }

    /**
     * Načte Vault data do cache.
     * @param player Bukkit objekt hráče
     */
    public void loadVaultPlayerData(final Player player) {
        try {
            this.getOrRegisterVaultData(player).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vrací všechny nahrané hráče v cache.
     * Vždy to jsou ti, co jsou online na serveru.
     * @return list hráčů v {@link HashMap}
     */
    public static HashMap<Player, CraftPlayer> getCraftPlayersCache() {
        return players;
    }

    /**
     * Vrací {@link CraftPlayer} dle zadaného objektu hráče
     * @param player {@link Player} objekct hráče
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    @Nullable
    public CraftPlayer getCraftPlayer(@NonNull Player player) {
        return players.values().stream().filter(cp -> cp.getPlayer() == player).findFirst().orElse(null);
    }

    /**
     * Vrací {@link CraftPlayer} dle zadaného stringu
     * @param name Nick hráče
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    @Nullable
    public CraftPlayer getCraftPlayer(@NonNull String name) {
        return players.values().stream().filter(cp -> cp.getPlayer().getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Vrací {@link CraftPlayer} dle zadaného {@link UUID}
     * @param uuid UUID hráče
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    @Nullable
    public CraftPlayer getCraftPlayer(@NonNull UUID uuid) {
        return players.values().stream().filter(cp -> cp.getPlayer().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * Registruje hráče do SQL, základní kontroly změny nicku
     * @param player Hráč
     * @return {@link CraftPlayer}
     * @see CraftPlayer
     */
    @NotNull
    private static CompletableFuture<CraftPlayer> getOrRegisterPlayer(@NonNull final Player player) {
        CompletableFuture<CraftPlayer> completableFuture = new CompletableFuture<>();
        try {
            // Načítání dat dle UUID, pokud si změnil nick dojde k převodu a znovu načtení
            if (Main.getInstance().getMySQL().hasDataByUuid(player, "player_profile").get()) {

                // Player profile: Kontrola změny nicku -> update nicku -> load podle UUID
                Main.getInstance().getMySQL().getNickFromTable("player_profile", player).thenAcceptAsync((sqlNick) -> {
                    if (!sqlNick.equals(player.getName())) {
                        Main.getInstance().getMySQL().updateNickInTable("player_profile", player);
                    }
                }).thenRunAsync(() -> {
                    Main.getInstance().getMySQL().getCraftPlayerFromSQL(player).thenAcceptAsync((completableFuture::complete));
                });

            } else if (Main.getInstance().getMySQL().hasDataByNick(player.getName(), "player_profile").get()) {
                // Chyba AutoLoginu -> update UUID -> load podle nicku
                Main.getInstance().getMySQL().updateUUIDInTable("player_profile", player).thenRunAsync(() -> {
                    Main.getInstance().getMySQL().getCraftPlayerFromSQL(player).thenAcceptAsync((completableFuture::complete));
                });
            } else {
                // Pokud hrac neni vubec v SQL, tak se provede register
                if (Main.getInstance().isRegisterEnabled()) {
                    CompletableFuture<Boolean> createProfile = Main.getInstance().getMySQL().createCcominutyProfile(player);
                    createProfile.thenRun(() -> {
                        final PlayerCreateCcomunityProfileEvent event = new PlayerCreateCcomunityProfileEvent(player);
                        Bukkit.getPluginManager().callEvent(event);
                    }).thenRunAsync(() -> {
                        Main.getInstance().getMySQL().getCraftPlayerFromSQL(player).thenAcceptAsync((completableFuture::complete));
                    });
                }
            }
        } catch (Exception exception) {
            Main.getInstance().sendSentryException(exception);
            exception.printStackTrace();
            completableFuture.completeExceptionally(exception);
        }
        return completableFuture;
    }

    private CompletableFuture<CraftPlayer> getOrRegisterVaultData(@NonNull final Player player) {
        CompletableFuture<CraftPlayer> completableFuture = new CompletableFuture<>();
        String sqlTableName = "player_economy_" + Main.getServerType().toString().toLowerCase();
        CraftPlayer craftPlayer = getCraftPlayer(player);
        assert craftPlayer != null;
        try {
            // Načítání dat dle UUID, pokud si změnil nick dojde k převodu a znovu načtení
            if (Main.getInstance().getMySQL().hasDataByUuid(player, sqlTableName).get()) {
                Main.getInstance().getMySQL().getNickFromTable(sqlTableName, player).thenAcceptAsync((sqlNick) -> {
                    if (!sqlNick.equals(player.getName())) {
                        Main.getInstance().getMySQL().updateNickInTable(sqlTableName, player);
                    }
                }).thenRunAsync(() -> {
                    Main.getInstance().getMySQL().getVaultEcoBalance(player.getUniqueId())
                            .thenAcceptAsync(craftPlayer::setMoney).thenRunAsync(() -> completableFuture.complete(craftPlayer));
                });
            // Chyba autologinu (špatný UUID)
            } else if (Main.getInstance().getMySQL().hasDataByNick(player.getName(), sqlTableName).get()) {
                Main.getInstance().getMySQL().updateUUIDInTable(sqlTableName, player).thenRunAsync(() -> {
                    Main.getInstance().getMySQL().getVaultEcoBalance(player.getUniqueId())
                            .thenAcceptAsync(craftPlayer::setMoney).thenRunAsync(() -> completableFuture.complete(craftPlayer));;
                });
            } else {
                Main.getInstance().getMySQL().createVaultEcoProfile(player)
                        .thenAcceptAsync(craftPlayer::setMoney)
                        .thenRunAsync(() -> completableFuture.complete(craftPlayer));
            }
        } catch (ExecutionException | InterruptedException exception) {
            Main.getInstance().sendSentryException(exception);
            exception.printStackTrace();
            completableFuture.completeExceptionally(exception);
        }
        return completableFuture;
    }

    /**
     * Vrací aktuální {@link LevelType} dle nastaveného ID serveru v configu
     * @return {@link LevelType}
     */
    @Nullable
    public LevelType getExperienceByServer(){
        ServerType server = Main.getServerType();
        return switch (server) {
            case SURVIVAL_117 -> LevelType.SURVIVAL_117_EXPERIENCE;
            case SKYBLOCK_117 -> LevelType.SKYBLOCK_117_EXPERIENCE;
            case SURVIVAL_118 -> LevelType.SURVIVAL_118_EXPERIENCE;
            case SKYBLOCK_118 -> LevelType.SKYBLOCK_118_EXPERIENCE;
            case CREATIVE -> LevelType.CREATIVE_EXPERIENCE;
            case VANILLA -> LevelType.VANILLA_EXPERIENCE;
            case SKYCLOUD -> LevelType.SKYCLOUD_EXPERIENCE;
            case PRISON -> LevelType.PRISON_EXPERIENCE;
            case HARDCORE_VANILLA -> LevelType.HARDCORE_VANILLA_EXPERIENCE;
            case ANARCHY -> LevelType.ANARCHY_EXPERIENCE;
            default -> null;
        };
    }

    /**
     * Vrací aktuální {@link LevelType} dle nastaveného ID serveru v configu
     * @return {@link LevelType}
     */
    @Nullable
    public LevelType getLevelByServer(){
        ServerType server = Main.getServerType();
        return switch (server) {
            case SURVIVAL_117 -> LevelType.SURVIVAL_117_LEVEL;
            case SKYBLOCK_117 -> LevelType.SKYBLOCK_117_LEVEL;
            case SURVIVAL_118 -> LevelType.SURVIVAL_118_LEVEL;
            case SKYBLOCK_118 -> LevelType.SKYBLOCK_118_LEVEL;
            case CREATIVE -> LevelType.CREATIVE_LEVEL;
            case VANILLA -> LevelType.VANILLA_LEVEL;
            case SKYCLOUD -> LevelType.SKYCLOUD_LEVEL;
            case PRISON -> LevelType.PRISON_LEVEL;
            case HARDCORE_VANILLA -> LevelType.HARDCORE_VANILLA_LEVEL;
            case ANARCHY -> LevelType.ANARCHY_LEVEL;
            default -> null;
        };
    }

    /**
     * Vrací typ {@link LevelType} dle stringu.
     * @param server ID serveru
     * @return {@link LevelType} když existuje, jinak null
     */
    @Nullable
    public LevelType resolveLevelTypeByString(String server) {
        return switch (server.toLowerCase()) {
            case "survival" -> LevelType.SURVIVAL_117_LEVEL;
            case "skyblock" -> LevelType.SKYBLOCK_117_LEVEL;
            case "survival_118" -> LevelType.SURVIVAL_118_LEVEL;
            case "skyblock_118" -> LevelType.SKYBLOCK_118_LEVEL;
            case "creative" -> LevelType.CREATIVE_LEVEL;
            case "vanilla" -> LevelType.VANILLA_LEVEL;
            case "skycloud" -> LevelType.SKYCLOUD_LEVEL;
            case "prison" -> LevelType.PRISON_LEVEL;
            case "hardcore-vanilla" -> LevelType.HARDCORE_VANILLA_LEVEL;
            case "anarchy" -> LevelType.ANARCHY_LEVEL;
            default -> null;
        };
    }

    /**
     * Vrací typ {@link LevelType} dle stringu.
     * @param server ID serveru
     * @return {@link LevelType} když existuje, jinak null
     */
    @Nullable
    public LevelType resolveExperienceTypeByString(String server) {
        return switch (server.toLowerCase()) {
            case "survival" -> LevelType.SURVIVAL_117_EXPERIENCE;
            case "skyblock" -> LevelType.SKYBLOCK_117_EXPERIENCE;
            case "survival_118" -> LevelType.SURVIVAL_118_EXPERIENCE;
            case "skyblock_118" -> LevelType.SKYBLOCK_118_EXPERIENCE;
            case "creative" -> LevelType.CREATIVE_EXPERIENCE;
            case "vanilla" -> LevelType.VANILLA_EXPERIENCE;
            case "skycloud" -> LevelType.SKYCLOUD_EXPERIENCE;
            case "prison" -> LevelType.PRISON_EXPERIENCE;
            case "hardcore-vanilla" -> LevelType.HARDCORE_VANILLA_EXPERIENCE;
            case "anarchy" -> LevelType.ANARCHY_EXPERIENCE;
            default -> null;
        };
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
                        Bukkit.getScheduler().runTask(Main.getInstance(),() ->  player.getWorld().dropItem(player.getLocation(), itemStack));
                        announceDrop.set(true);
                    } else {
                        player.getInventory().addItem(itemStack);
                    }
                } else {
                    player.getInventory().addItem(itemStack);
                }
            });
            if (announceDrop.get()) {
                ChatInfo.DANGER.send(player, "Máš plný inventář! Itemy leží na zemi.");
            }
        }

        // Notify
        if (announce) {
            player.sendMessage("§b" + Constants.CHAT_BOXES);
            player.sendMessage("");
            player.sendMessage("§9§lOdmena za level: §f" + level.getLevel());
            if (level.getRewardDescription() != null) {
                player.sendMessage(level.getRewardDescription());
            }
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
