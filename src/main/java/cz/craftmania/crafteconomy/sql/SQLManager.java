package cz.craftmania.crafteconomy.sql;

import com.zaxxer.hikari.HikariDataSource;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.*;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SQLManager {

    private final Main plugin;
    private final ConnectionPoolManager pool;
    private HikariDataSource dataSource;

    public SQLManager(Main plugin) {
        this.plugin = plugin;
        pool = new ConnectionPoolManager(plugin);
    }

    public void onDisable() {
        pool.closePool();
    }

    public ConnectionPoolManager getPool() {
        return pool;
    }

    public final boolean hasData(final Player p) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE uuid = ?;");
            ps.setString(1, p.getUniqueId().toString());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final boolean hasDataByNick(final String p) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE nick = ?;");
            ps.setString(1, p);
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final boolean hasDataByUUID(final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE uuid = ?;");
            ps.setString(1, uuid.toString());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    //TODO: Rewrite
    public final CraftPlayer getCraftPlayerFromSQL(final Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT player_profile.*, player_settings.paytoggle FROM player_profile, player_settings WHERE player_profile.uuid = ? AND player_profile.nick = player_settings.Nick;");
            ps.setString(1, player.getUniqueId().toString());
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                CraftPlayer craftPlayer = new CraftPlayer(player);
                craftPlayer.setEconomyByType(EconomyType.CRAFT_COINS, ps.getResultSet().getLong("craft_coins"));
                craftPlayer.setEconomyByType(EconomyType.CRAFT_TOKENS, ps.getResultSet().getLong("craft_tokens"));
                // Zde je jedno jaký mají VoteTokens klíč, jelikož se všechny nastavují stejně
                craftPlayer.setEconomyByType(EconomyType.VOTE_TOKENS, ps.getResultSet().getLong(Main.getInstance().getVoteTokensVersion().name().toLowerCase()));
                craftPlayer.setLevelByType(LevelType.SURVIVAL_117_LEVEL, ps.getResultSet().getLong(LevelType.SURVIVAL_117_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SURVIVAL_117_EXPERIENCE, ps.getResultSet().getLong(LevelType.SURVIVAL_117_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SKYBLOCK_117_LEVEL, ps.getResultSet().getLong(LevelType.SKYBLOCK_117_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SKYBLOCK_117_EXPERIENCE, ps.getResultSet().getLong(LevelType.SKYBLOCK_117_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SURVIVAL_118_LEVEL, ps.getResultSet().getLong(LevelType.SURVIVAL_118_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SURVIVAL_118_EXPERIENCE, ps.getResultSet().getLong(LevelType.SURVIVAL_118_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SKYBLOCK_118_LEVEL, ps.getResultSet().getLong(LevelType.SKYBLOCK_118_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SKYBLOCK_118_EXPERIENCE, ps.getResultSet().getLong(LevelType.SKYBLOCK_118_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.CREATIVE_LEVEL, ps.getResultSet().getLong(LevelType.CREATIVE_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.CREATIVE_EXPERIENCE, ps.getResultSet().getLong(LevelType.CREATIVE_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.PRISON_LEVEL, ps.getResultSet().getLong(LevelType.PRISON_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.PRISON_EXPERIENCE, ps.getResultSet().getLong(LevelType.PRISON_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.VANILLA_LEVEL, ps.getResultSet().getLong(LevelType.VANILLA_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.VANILLA_EXPERIENCE, ps.getResultSet().getLong(LevelType.VANILLA_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.ANARCHY_LEVEL, ps.getResultSet().getLong(LevelType.ANARCHY_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.ANARCHY_EXPERIENCE, ps.getResultSet().getLong(LevelType.ANARCHY_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SKYCLOUD_LEVEL, ps.getResultSet().getLong(LevelType.SKYCLOUD_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SKYCLOUD_EXPERIENCE, ps.getResultSet().getLong(LevelType.SKYCLOUD_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.VANILLA_116_LEVEL, ps.getResultSet().getLong(LevelType.VANILLA_116_LEVEL.getColumnId()));
                craftPlayer.setLevelByType(LevelType.HARDCORE_VANILLA_LEVEL, ps.getResultSet().getLong(LevelType.HARDCORE_VANILLA_LEVEL.getColumnId()));
                craftPlayer.setEconomyByType(EconomyType.QUEST_POINTS, ps.getResultSet().getLong("quest_points"));
                craftPlayer.setEconomyByType(EconomyType.SEASON_POINTS, ps.getResultSet().getLong("season_points"));
                craftPlayer.setEconomyByType(EconomyType.EVENT_POINTS, ps.getResultSet().getLong("event_points"));
                craftPlayer.setEconomyByType(EconomyType.PARKOUR_POINTS, ps.getResultSet().getLong("parkour_points"));
                craftPlayer.setEconomyByType(EconomyType.KARMA_POINTS, ps.getResultSet().getLong("karma_points"));
                craftPlayer.setVotePassVotes(ps.getResultSet().getLong("vote_pass"));
                craftPlayer.setPayToggle(ps.getResultSet().getBoolean("paytoggle"));
                craftPlayer.setVoteStatistics(
                        ps.getResultSet().getLong(EconomyType.WEEK_VOTES.name().toLowerCase()),
                        ps.getResultSet().getLong(EconomyType.MONTH_VOTES.name().toLowerCase()),
                        ps.getResultSet().getLong(EconomyType.TOTAL_VOTES.name().toLowerCase()),
                        ps.getResultSet().getLong("last_vote")
                );
                return craftPlayer;
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return null;
    }

    public void setEconomy(final LevelType type, final Player p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + finalType + " = ? WHERE uuid = ?");
            ps.setLong(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void setEconomy(final EconomyType type, final Player p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + finalType + " = ? WHERE uuid = ?");
            ps.setLong(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void addEconomy(final LevelType type, final String p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + finalType + " = " + finalType + " + ? WHERE nick = ?");
            ps.setLong(1, value);
            ps.setString(2, p);
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void addEconomy(final EconomyType type, final String p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + finalType + " = " + finalType + " + ? WHERE nick = ?");
            ps.setLong(1, value);
            ps.setString(2, p);
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void takeEconomy(final LevelType type, final String p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + finalType + " = " + finalType + " - ? WHERE nick = ?");
            ps.setLong(1, value);
            ps.setString(2, p);
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void takeEconomy(final EconomyType type, final String p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + finalType + " = " + finalType + " - ? WHERE nick = ?");
            ps.setLong(1, value);
            ps.setString(2, p);
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final long getPlayerEconomy(final LevelType type, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE nick = '" + player + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final long getPlayerEconomy(final EconomyType type, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE nick = '" + player + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final long getPlayerEconomy(final EconomyType type, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final long getPlayerEconomy(final LevelType type, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final long getPlayerEconomy(final String column, final Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + column + " FROM player_profile WHERE uuid = '" + player.getUniqueId().toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong(column);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final void createCcominutyProfile(final Player p) {
        String discriminator = PlayerUtils.createDiscriminator();
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("INSERT INTO player_profile (discriminator, nick ,uuid, registred, last_online, last_server) VALUES (?,?,?,?,?,?);");
                    ps.setString(1, discriminator);
                    ps.setString(2, p.getName());
                    ps.setString(3, p.getUniqueId().toString());
                    ps.setLong(4, System.currentTimeMillis());
                    ps.setLong(5, System.currentTimeMillis());
                    ps.setString(6, "lobby");
                    ps.executeUpdate();
                } catch (Exception e) {
                    //e.printStackTrace(); // Schvalne ignorovani, kazdy hrac pohybujici se po CM *musi* mít profil.
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void insertChangeIntoChangelog(final Player player, final String sender, final String action,
                                                final String oldValue, final String newValue) {
        long currentTime = System.currentTimeMillis();
        String serverName = Main.getServerType().name().toLowerCase();
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("INSERT INTO player_changelog (uuid, nick, sender, server, action, old_value, new_value, time) VALUES (?,?,?,?,?,?,?,?);");
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, player.getName());
                    ps.setString(3, sender);
                    ps.setString(4, serverName);
                    ps.setString(5, action);
                    ps.setString(6, oldValue);
                    ps.setString(7, newValue);
                    ps.setLong(8, currentTime);
                    ps.executeUpdate();
                } catch (Exception e) {
                    Main.getInstance().sendSentryException(e);
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void updateNickInTable(final String table, final Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE " + table + " SET nick = ? WHERE uuid = '" + p.getUniqueId().toString() + "';");
                    ps.setString(1, p.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    //e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void updateUUIDInTable(final String table, final Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE " + table + " SET uuid = ? WHERE nick = '" + p.getName() + "';");
                    ps.setString(1, p.getUniqueId().toString());
                    ps.executeUpdate();
                } catch (Exception e) {
                    //e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void sendPlayerAchievementLog(final Player p, final QuestReward achievement) {
        final String server = Main.getServerType().name().toLowerCase();
        final long currentTime = System.currentTimeMillis();
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("INSERT INTO player_achievement_log (nick,uuid,ach_name,ach_value,ach_server,date) VALUES (?,?,?,?,?,?);");
                    ps.setString(1, p.getName());
                    ps.setString(2, p.getUniqueId().toString());
                    ps.setString(3, achievement.getName());
                    ps.setInt(4, achievement.getQuestPointsValue());
                    ps.setString(5, server);
                    ps.setLong(6, currentTime);
                    ps.executeUpdate();
                } catch (Exception e) {
                    Main.getInstance().sendSentryException(e);
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void addPlayerVote(final String p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE player_profile SET total_votes = total_votes + 1, week_votes = week_votes + 1, month_votes = month_votes + 1, vote_pass = vote_pass + 1, last_vote = '" + System.currentTimeMillis() + "' WHERE nick = '" + p + "';");
                    ps.executeUpdate();
                } catch (Exception e) {
                    Main.getInstance().sendSentryException(e);
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final boolean hasVaultEcoProfile(final UUID uuid) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_economy_" + server + " WHERE uuid = ?;");
            ps.setString(1, uuid.toString());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final boolean hasVaultEcoProfile(final String name) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_economy_" + server + " WHERE nick = ?;");
            ps.setString(1, name);
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    @Deprecated
    public final void createPlayerProfileTable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement(
                            "CREATE TABLE `player_profile` (" +
                                    " `id` int(255) NOT NULL AUTO_INCREMENT," +
                                    " `discriminator` varchar(64) COLLATE latin2_czech_cs NOT NULL," +
                                    " `nick` varchar(64) COLLATE latin2_czech_cs NOT NULL," +
                                    " `uuid` varchar(64) COLLATE latin2_czech_cs NOT NULL," +
                                    " `web_group` int(32) NOT NULL DEFAULT '0'," +
                                    " `registred` bigint(64) NOT NULL," +
                                    " `last_online` bigint(64) NOT NULL," +
                                    " `last_server` varchar(32) COLLATE latin2_czech_cs NOT NULL," +
                                    " `is_online` int(12) NOT NULL DEFAULT '0'," +
                                    " `played_time` int(64) NOT NULL DEFAULT '0'," +
                                    " `craftcoins` bigint(32) NOT NULL DEFAULT '0'," +
                                    " `crafttokens` bigint(32) NOT NULL DEFAULT '0'," +
                                    " `votetokens` bigint(32) NOT NULL DEFAULT '0'," +
                                    " `votetokens_2` bigint(32) NOT NULL DEFAULT '0'," +
                                    " `level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `global_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `global_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `survival_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `survival_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `skyblock_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `skyblock_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `creative_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `creative_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `prison_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `prison_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `vanilla_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `vanilla_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `skycloud_level` bigint(64) NOT NULL DEFAULT '1'," +
                                    " `skycloud_experience` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `karma` bigint(32) NOT NULL DEFAULT '0'," +
                                    " `achievement_points` int(64) NOT NULL DEFAULT '0'," +
                                    " `event_points` bigint(64) NOT NULL DEFAULT '0'," +
                                    " `total_votes` int(64) NOT NULL DEFAULT '0'," +
                                    " `month_votes` int(64) NOT NULL DEFAULT '0'," +
                                    " `week_votes` int(64) NOT NULL DEFAULT '0'," +
                                    " `last_vote` bigint(255) NOT NULL DEFAULT '0'," +
                                    " `status` varchar(100) COLLATE latin2_czech_cs NOT NULL DEFAULT 'Tento hráč nemá nastavený status...'," +
                                    " `soc_facebook` varchar(128) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `soc_twitter` varchar(128) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `soc_ytb` varchar(128) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `soc_steam` varchar(128) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `soc_twitch` varchar(128) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `soc_web` varchar(256) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `mc_version` varchar(32) COLLATE latin2_czech_cs NOT NULL DEFAULT '0'," +
                                    " `discord_user_id` varchar(64) COLLATE latin2_czech_cs DEFAULT NULL," +
                                    " `discord_reward` int(12) NOT NULL DEFAULT '0'," +
                                    " `lobby_daily_bonus` int(12) NOT NULL DEFAULT '0'," +
                                    " `lobby_vip_bonus` int(12) NOT NULL DEFAULT '0'," +
                                    " `seen_latest_news` int(2) NOT NULL DEFAULT '0'," +
                                    " PRIMARY KEY (`id`)," +
                                    " UNIQUE KEY `uuid` (`uuid`)," +
                                    " UNIQUE KEY `nick` (`nick`)" +
                                    ") ENGINE=InnoDB AUTO_INCREMENT=131544 DEFAULT CHARSET=latin2 COLLATE=latin2_czech_cs");
                    ps.executeUpdate();
                } catch (Exception e) {
                    //e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final long getVaultEcoBalance(final UUID uuid) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT balance FROM player_economy_" + server + " WHERE uuid = '" + uuid + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong("balance");
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final long getVaultEcoBalance(final String nick) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT balance FROM player_economy_" + server + " WHERE nick = '" + nick + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong("balance");
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public Map<String, Long> getVaultAllEcosWithNicks() {
        Map<String, Long> balanceMap = new HashMap<String, Long>();

        final String server = Main.getServerType().getTableId();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `nick`, `balance` FROM `player_economy_" + server + "` WHERE `balance` > 0 ORDER BY `balance` DESC");
            ps.executeQuery();
            while (ps.getResultSet().next()) {
                balanceMap.put(ps.getResultSet().getString("nick"), ps.getResultSet().getLong("balance"));
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        return balanceMap;
    }

    public CompletableFuture<List<EconomyLog>> getVaultAllLogsByUUID(UUID uuid) {
        CompletableFuture<List<EconomyLog>> completableFuture = new CompletableFuture<>();
        Main.getAsync().runAsync(() -> {
            List<EconomyLog> list = new ArrayList<>();

            final String server = Main.getServerType().name().toLowerCase();
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("SELECT * FROM logs.economy_" + server + "_log WHERE `r_uuid` = ? OR `s_uuid` = ? ORDER BY `time` DESC;");
                ps.setString(1, uuid.toString());
                ps.setString(2, uuid.toString());

                final ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    final String reciever = resultSet.getString("reciever");
                    final UUID recieverUUID = UUID.fromString(resultSet.getString("r_uuid"));
                    final String sender = resultSet.getString("sender");
                    UUID senderUUID;
                    if (resultSet.getString("s_uuid") == null) senderUUID = null;
                    else senderUUID = UUID.fromString(resultSet.getString("s_uuid"));
                    final EconomyLog.EconomyAction action = EconomyLog.EconomyAction.valueOf(resultSet.getString("action"));
                    final Long amount = resultSet.getLong("amount");
                    final Long time = resultSet.getLong("time");

                    EconomyLog economyLog = new EconomyLog(reciever, recieverUUID, sender, senderUUID, action, amount, time);
                    list.add(economyLog);
                }

                completableFuture.complete(list);
            } catch (Exception e) {
                Main.getInstance().sendSentryException(e);
                e.printStackTrace();

                completableFuture.completeExceptionally(e);
            } finally {
                pool.close(conn, ps, null);
            }
        });

        return completableFuture;
    }

    public CompletableFuture<List<EconomyLog>> getVaultAllLogsByNickname(String nickname) {
        CompletableFuture<List<EconomyLog>> completableFuture = new CompletableFuture<>();
        Main.getAsync().runAsync(() -> {
            List<EconomyLog> list = new ArrayList<>();

            final String server = Main.getServerType().name().toLowerCase();
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("SELECT * FROM logs.economy_" + server + "_log WHERE `reciever` = ? OR `sender` = ? ORDER BY `time` DESC;");
                ps.setString(1, nickname);
                ps.setString(2, nickname);

                final ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    final String reciever = resultSet.getString("reciever");
                    final UUID recieverUUID = UUID.fromString(resultSet.getString("r_uuid"));
                    final String sender = resultSet.getString("sender");
                    UUID senderUUID;
                    if (resultSet.getString("s_uuid") == null) senderUUID = null;
                    else senderUUID = UUID.fromString(resultSet.getString("s_uuid"));
                    final EconomyLog.EconomyAction action = EconomyLog.EconomyAction.valueOf(resultSet.getString("action"));
                    final Long amount = resultSet.getLong("amount");
                    final Long time = resultSet.getLong("time");

                    EconomyLog economyLog = new EconomyLog(reciever, recieverUUID, sender, senderUUID, action, amount, time);
                    list.add(economyLog);
                }

                completableFuture.complete(list);
            } catch (Exception e) {
                Main.getInstance().sendSentryException(e);
                e.printStackTrace();

                completableFuture.completeExceptionally(e);
            } finally {
                pool.close(conn, ps, null);
            }
        });

        return completableFuture;
    }

    public final UUID fetchUUIDbyName(final String name) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT uuid FROM player_economy_" + server + " WHERE nick = '" + name + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return UUID.fromString(ps.getResultSet().getString("uuid"));
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        throw new NullPointerException("UUID pro hrace (" + name + ") nebylo nalezeno!");
    }

    public void setVaultEcoBalance(final String player, final long amount) {
        Main.getAsync().runAsync(() -> {
            long milis = System.currentTimeMillis();
            final String server = Main.getServerType().name().toLowerCase();
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("UPDATE player_economy_" + server + " SET balance = ?, last_update = ? WHERE nick = ?");
                ps.setLong(1, amount);
                ps.setLong(2, System.currentTimeMillis());
                ps.setString(3, player);
                ps.executeUpdate();
            } catch (Exception e) {
                Main.getInstance().sendSentryException(e);
                e.printStackTrace();
            } finally {
                pool.close(conn, ps, null);
            }
            Logger.debug("Method: setVaultEcoBalance(): " + (System.currentTimeMillis() - milis) + "ms");
        });
    }

    public final void createVaultEcoProfile(final Player player) {
        long currentTime = System.currentTimeMillis();
        final String server = Main.getServerType().name().toLowerCase();
        int startValue = Main.getInstance().getConfig().getInt("vault-economy.start-value", 0);
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("INSERT INTO player_economy_" + server + " (nick, uuid, balance, last_update) VALUES (?,?,?);");
                    ps.setString(1, player.getName());
                    ps.setString(2, player.getUniqueId().toString());
                    ps.setLong(3, startValue);
                    ps.setLong(4, currentTime);
                    ps.executeUpdate();
                } catch (Exception e) {
                    // Hráč má špatný UUID!
                    Logger.danger("POZOR! Hráč " + player.getName() + " má špatné UUID! Nefungoval mu správně AutoLogin.");
                    udpateUUIDInEconomyTable(player);
                    //e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public void udpateUUIDInEconomyTable(final Player player) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_economy_" + server + " SET uuid = ? WHERE nick = ?");
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        Logger.success("Automatická oprava UUID dokončena.");
        player.sendMessage(" ");
        player.sendMessage("§e§l[*] §eNa tvém účtu proběhla interní změna dat. Prosíme jdi do lobby a zpět!");
        player.sendMessage(" ");
    }

    public final boolean tablePlayerProfileExists() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT uuid FROM player_profile");
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    /**
     * Kontroluje nick v DB podle UUID
     * @param table Tabulka k kontrole
     * @param player Hráč k kontrole
     * @return null když hráč neexistuje? jinak nick
     */
    public final String getNickFromTable(final String table, final Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT nick FROM " + table + " WHERE uuid = '" + player.getUniqueId().toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getString("nick");
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return null;
    }

    /**
     * Will return selected player settings.
     *
     * @param p        Player object
     * @param settings Settings name
     * @return Selected settings value
     */
    public final int getSettings(final Player p, final String settings) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + settings + " FROM player_settings WHERE nick = '" + p.getName() + "'");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt(settings);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return -1;
    }


    public final void updateGender(final Player p, final int value) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE player_profile SET gender = " + value + " WHERE nick = ?;");
                    ps.setString(1, p.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    Main.getInstance().sendSentryException(e);
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final int getGender(final Player p) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT gender FROM player_profile WHERE nick = '" + p.getName() + "'");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt("gender");
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return -1;
    }

    public final String getSettingsString(final Player p, final String settings) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + settings + " FROM player_settings WHERE nick = '" + p.getName() + "'");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getString(settings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return null;
    }

    /**
     * Will update selected player settings.
     *
     * @param p        Player object
     * @param settings Settings name
     * @param value    New value for selected settings
     */
    public final void updateSettings(final Player p, final String settings, final int value) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE player_settings SET " + settings + " = " + value + " WHERE nick = ?;");
                    ps.setString(1, p.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    Main.getInstance().sendSentryException(e);
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void updateSettings(final Player p, final String settings, final String value) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE player_settings SET " + settings + "=? WHERE nick = ?;");
                    //ps.setString(1, p.getName());
                    ps.setString(1, value);
                    ps.setString(2, p.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }
}
