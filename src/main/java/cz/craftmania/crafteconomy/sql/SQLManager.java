package cz.craftmania.crafteconomy.sql;

import com.zaxxer.hikari.HikariDataSource;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.*;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import cz.craftmania.crafteconomy.utils.Triple;
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
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE uuid = ?;");
            ps.setString(1, p.getUniqueId().toString());
            ps.executeQuery();
            rs = ps.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public final boolean hasDataByNick(final String p) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE nick = ?;");
            ps.setString(1, p);
            ps.executeQuery();
            rs = ps.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public final boolean hasDataByUUID(final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE uuid = ?;");
            ps.setString(1, uuid.toString());
            ps.executeQuery();
            rs = ps.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public final CraftPlayer getCraftPlayerFromSQL(final Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT player_profile.*, player_settings.paytoggle FROM player_profile, player_settings WHERE player_profile.uuid = ? AND player_profile.nick = player_settings.Nick;");
            ps.setString(1, player.getUniqueId().toString());
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                CraftPlayer craftPlayer = new CraftPlayer(player);
                craftPlayer.setEconomyByType(EconomyType.CRAFT_COINS, rs.getLong("craft_coins"));
                craftPlayer.setEconomyByType(EconomyType.CRAFT_TOKENS, rs.getLong("craft_tokens"));
                // Zde je jedno jaký mají VoteTokens klíč, jelikož se všechny nastavují stejně
                craftPlayer.setEconomyByType(EconomyType.VOTE_TOKENS, rs.getLong(Main.getInstance().getVoteTokensVersion().name().toLowerCase()));
                craftPlayer.setLevelByType(LevelType.SURVIVAL_117_LEVEL, rs.getLong(LevelType.SURVIVAL_117_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SURVIVAL_117_EXPERIENCE, rs.getLong(LevelType.SURVIVAL_117_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SKYBLOCK_117_LEVEL, rs.getLong(LevelType.SKYBLOCK_117_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SKYBLOCK_117_EXPERIENCE, rs.getLong(LevelType.SKYBLOCK_117_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SURVIVAL_118_LEVEL, rs.getLong(LevelType.SURVIVAL_118_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SURVIVAL_118_EXPERIENCE, rs.getLong(LevelType.SURVIVAL_118_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.ONEBLOCK_LEVEL, rs.getLong(LevelType.ONEBLOCK_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.ONEBLOCK_EXPERIENCE, rs.getLong(LevelType.ONEBLOCK_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.CREATIVE_LEVEL, rs.getLong(LevelType.CREATIVE_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.CREATIVE_EXPERIENCE, rs.getLong(LevelType.CREATIVE_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.PRISON_LEVEL, rs.getLong(LevelType.PRISON_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.PRISON_EXPERIENCE, rs.getLong(LevelType.PRISON_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.VANILLA_LEVEL, rs.getLong(LevelType.VANILLA_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.VANILLA_EXPERIENCE, rs.getLong(LevelType.VANILLA_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.ANARCHY_LEVEL, rs.getLong(LevelType.ANARCHY_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.ANARCHY_EXPERIENCE, rs.getLong(LevelType.ANARCHY_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.SKYCLOUD_LEVEL, rs.getLong(LevelType.SKYCLOUD_LEVEL.getColumnId()));
                craftPlayer.setExperienceByType(LevelType.SKYCLOUD_EXPERIENCE, rs.getLong(LevelType.SKYCLOUD_EXPERIENCE.getColumnId()));
                craftPlayer.setLevelByType(LevelType.VANILLA_116_LEVEL, rs.getLong(LevelType.VANILLA_116_LEVEL.getColumnId()));
                craftPlayer.setLevelByType(LevelType.HARDCORE_VANILLA_LEVEL, rs.getLong(LevelType.HARDCORE_VANILLA_LEVEL.getColumnId()));
                craftPlayer.setEconomyByType(EconomyType.QUEST_POINTS, rs.getLong("quest_points"));
                craftPlayer.setEconomyByType(EconomyType.SEASON_POINTS, rs.getLong("season_points"));
                craftPlayer.setPayToggle(rs.getBoolean("paytoggle"));
                craftPlayer.setEconomyByType(EconomyType.EVENT_POINTS, rs.getLong("event_points"));
                craftPlayer.setVotePassVotes(rs.getLong("vote_pass"));
                return craftPlayer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
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
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final long getPlayerEconomy(final LevelType type, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE nick = '" + player + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getLong(finalType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return 0;
    }

    public final long getPlayerEconomy(final EconomyType type, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE nick = '" + player + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getLong(finalType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return 0;
    }

    public final long getPlayerEconomy(final EconomyType type, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getLong(finalType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return 0;
    }

    public final long getPlayerEconomy(final LevelType type, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String finalType = type.getColumnId();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getLong(finalType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return 0;
    }

    public final long getPlayerEconomy(final String column, final Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + column + " FROM player_profile WHERE uuid = '" + player.getUniqueId().toString() + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getLong(column);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
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
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_economy_" + server + " WHERE uuid = ?;");
            ps.setString(1, uuid.toString());
            ps.executeQuery();
            rs = ps.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public final boolean hasVaultEcoProfile(final String name) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_economy_" + server + " WHERE nick = ?;");
            ps.setString(1, name);
            ps.executeQuery();
            rs = ps.getResultSet();
            return rs.next();
        } catch (Exception e) {
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

    public final double getVaultEcoBalance(final UUID uuid) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT balance FROM player_economy_" + server + " WHERE uuid = '" + uuid + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return 0;
    }

    public final double getVaultEcoBalance(final String nick) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT balance FROM player_economy_" + server + " WHERE nick = '" + nick + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return 0;
    }

    public Map<String, Double> getVaultAllEcosWithNicks() {
        Map<String, Double> balanceMap = new HashMap<String, Double>();

        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `nick`, `balance` FROM `player_economy_" + server + "` WHERE `balance` > 0 AND `hide_in_baltop` = 0 ORDER BY `balance` DESC");
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                balanceMap.put(rs.getString("nick"), rs.getDouble("balance"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }

        return balanceMap;
    }

    public boolean isHiddenInBaltop(String uuid) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `hide_in_baltop` FROM `player_economy_" + server + "` WHERE `uuid` = ?;");
            ps.setString(1, uuid);
            ps.executeQuery();
            rs = ps.getResultSet();
            rs.next();
            return rs.getBoolean("hide_in_baltop");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return false;
    }

    public void setHideInBaltop(String uuid, boolean hideInBaltop) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player_economy_" + server + "` SET `hide_in_baltop` = ? WHERE `uuid` = ?;");
            ps.setBoolean(1, hideInBaltop);
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public CompletableFuture<List<EconomyLog>> getVaultAllLogsByUUID(UUID uuid) {
        CompletableFuture<List<EconomyLog>> completableFuture = new CompletableFuture<>();
        Main.getAsync().runAsync(() -> {
            List<EconomyLog> list = new ArrayList<>();

            final String server = Main.getServerType().name().toLowerCase();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("SELECT * FROM logs.economy_" + server + "_log WHERE `r_uuid` = ? OR `s_uuid` = ? ORDER BY `time` DESC;");
                ps.setString(1, uuid.toString());
                ps.setString(2, uuid.toString());

                rs = ps.executeQuery();
                while (rs.next()) {
                    final String reciever = rs.getString("reciever");
                    final UUID recieverUUID = UUID.fromString(rs.getString("r_uuid"));
                    final String sender = rs.getString("sender");
                    UUID senderUUID;
                    if (rs.getString("s_uuid") == null) senderUUID = null;
                    else senderUUID = UUID.fromString(rs.getString("s_uuid"));
                    final EconomyLog.EconomyAction action = EconomyLog.EconomyAction.valueOf(rs.getString("action"));
                    final double amount = rs.getLong("amount");
                    final Long time = rs.getLong("time");

                    EconomyLog economyLog = new EconomyLog(reciever, recieverUUID, sender, senderUUID, action, amount, time);
                    list.add(economyLog);
                }

                completableFuture.complete(list);
            } catch (Exception e) {
                e.printStackTrace();

                completableFuture.completeExceptionally(e);
            } finally {
                pool.close(conn, ps, rs);
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
            ResultSet rs = null;

            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("SELECT * FROM logs.economy_" + server + "_log WHERE `reciever` = ? OR `sender` = ? ORDER BY `time` DESC;");
                ps.setString(1, nickname);
                ps.setString(2, nickname);

                rs = ps.executeQuery();
                while (rs.next()) {
                    final String reciever = rs.getString("reciever");
                    final UUID recieverUUID = UUID.fromString(rs.getString("r_uuid"));
                    final String sender = rs.getString("sender");
                    UUID senderUUID;
                    if (rs.getString("s_uuid") == null) senderUUID = null;
                    else senderUUID = UUID.fromString(rs.getString("s_uuid"));
                    final EconomyLog.EconomyAction action = EconomyLog.EconomyAction.valueOf(rs.getString("action"));
                    final double amount = rs.getLong("amount");
                    final Long time = rs.getLong("time");

                    EconomyLog economyLog = new EconomyLog(reciever, recieverUUID, sender, senderUUID, action, amount, time);
                    list.add(economyLog);
                }

                completableFuture.complete(list);
            } catch (Exception e) {
                e.printStackTrace();

                completableFuture.completeExceptionally(e);
            } finally {
                pool.close(conn, ps, rs);
            }
        });

        return completableFuture;
    }

    public final UUID fetchUUIDbyName(final String name) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT uuid FROM player_economy_" + server + " WHERE nick = '" + name + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return UUID.fromString(rs.getString("uuid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        throw new NullPointerException("UUID pro hrace (" + name + ") nebylo nalezeno!");
    }

    public void setVaultEcoBalance(final String player, final double amount) {
        Main.getAsync().runAsync(() -> {
            long milis = System.currentTimeMillis();
            final String server = Main.getServerType().name().toLowerCase();
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("UPDATE player_economy_" + server + " SET balance = ?, last_update = ? WHERE nick = ?");
                ps.setDouble(1, amount);
                ps.setLong(2, System.currentTimeMillis());
                ps.setString(3, player);
                ps.executeUpdate();
            } catch (Exception e) {
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
                    ps = conn.prepareStatement("INSERT INTO player_economy_" + server + " (nick, uuid, balance, last_update) VALUES (?,?,?,?);");
                    ps.setString(1, player.getName());
                    ps.setString(2, player.getUniqueId().toString());
                    ps.setDouble(3, startValue);
                    ps.setLong(4, currentTime);
                    ps.executeUpdate();
                } catch (Exception e) {
                    // Hráč má špatný UUID!
                    Logger.danger("POZOR! Hráč " + player.getName() + " má špatné UUID! Nefungoval mu správně AutoLogin.");
                    udpateUUIDInEconomyTable(player);
                    e.printStackTrace();
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
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT uuid FROM player_profile");
            ps.executeQuery();
            rs = ps.getResultSet();
            return rs.next();
        } catch (Exception e) {
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final List<Triple<String, Double, Long>> fetchAllEconomyRowsToRemove(long time) {
        List<Triple<String, Double, Long>> balanceList = new ArrayList<>();
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `nick`, `balance`, `last_update` FROM `player_economy_" + server + "` WHERE `last_update` <= " + time + " ORDER BY `balance` DESC");
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                balanceList.add(
                        new Triple<>(
                                rs.getString("nick"),
                                rs.getDouble("balance"),
                                rs.getLong("last_update"))
                        );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return balanceList;
    }

    public void purgeFromVaultDatabase(String nick, long lastUpdate) {
        Main.getAsync().runAsync(() -> {
            final String server = Main.getServerType().name().toLowerCase();
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = pool.getConnection();
                ps = conn.prepareStatement("DELETE FROM player_economy_" + server + " WHERE nick = ?");
                ps.setString(1, nick);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pool.close(conn, ps, null);
            }
        });
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
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT nick FROM " + table + " WHERE uuid = '" + player.getUniqueId().toString() + "';");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getString("nick");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
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
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + settings + " FROM player_settings WHERE nick = '" + p.getName() + "'");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getInt(settings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
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
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT gender FROM player_profile WHERE nick = '" + p.getName() + "'");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getInt("gender");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return -1;
    }

    public final String getSettingsString(final Player p, final String settings) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + settings + " FROM player_settings WHERE nick = '" + p.getName() + "'");
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                return rs.getString(settings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
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

    public final List<Triple<String, UUID, Double>> fetchAllEconomyTaxPaymentPlayers(long minBalance) {
        List<Triple<String, UUID, Double>> balanceList = new ArrayList<>();
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `nick`, `uuid`, `balance` FROM `player_economy_" + server + "` WHERE `balance` >= " + minBalance);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                balanceList.add(
                        new Triple<>(
                                rs.getString("nick"),
                                UUID.fromString(rs.getString("uuid")),
                                rs.getDouble("balance"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return balanceList;
    }
}
