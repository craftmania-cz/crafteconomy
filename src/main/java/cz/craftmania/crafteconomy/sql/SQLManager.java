package cz.craftmania.crafteconomy.sql;

import com.zaxxer.hikari.HikariDataSource;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.ChangeActions;
import cz.craftmania.crafteconomy.objects.AchievementReward;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

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

    public final CraftPlayer getCraftPlayerFromSQL(final Player p) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT player_profile.*, player_settings.paytoggle FROM player_profile, player_settings WHERE player_profile.uuid = ? AND player_profile.nick = player_settings.Nick;");
            ps.setString(1, p.getUniqueId().toString());
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                CraftPlayer craftPlayer = new CraftPlayer(p,
                        ps.getResultSet().getLong("craftcoins"),
                        ps.getResultSet().getLong("crafttokens"),
                        ps.getResultSet().getLong("votetokens_2"));
                craftPlayer.setLevelByType(LevelType.SURVIVAL_LEVEL, ps.getResultSet().getLong("survival_level"));
                craftPlayer.setLevelByType(LevelType.SKYBLOCK_LEVEL, ps.getResultSet().getLong("skyblock_level"));
                craftPlayer.setLevelByType(LevelType.CREATIVE_LEVEL, ps.getResultSet().getLong("creative_level"));
                craftPlayer.setLevelByType(LevelType.PRISON_LEVEL, ps.getResultSet().getLong("prison_level"));
                craftPlayer.setLevelByType(LevelType.VANILLA_LEVEL, ps.getResultSet().getLong("vanilla_level"));
                craftPlayer.setLevelByType(LevelType.SKYCLOUD_LEVEL, ps.getResultSet().getLong("skycloud_level"));
                craftPlayer.setExperienceByType(LevelType.SURVIVAL_EXPERIENCE, ps.getResultSet().getLong("survival_experience"));
                craftPlayer.setExperienceByType(LevelType.SKYBLOCK_EXPERIENCE, ps.getResultSet().getLong("skyblock_experience"));
                craftPlayer.setExperienceByType(LevelType.CREATIVE_EXPERIENCE, ps.getResultSet().getLong("creative_experience"));
                craftPlayer.setExperienceByType(LevelType.PRISON_EXPERIENCE, ps.getResultSet().getLong("prison_experience"));
                craftPlayer.setExperienceByType(LevelType.VANILLA_EXPERIENCE, ps.getResultSet().getLong("vanilla_experience"));
                craftPlayer.setExperienceByType(LevelType.SKYCLOUD_EXPERIENCE, ps.getResultSet().getLong("skycloud_experience"));
                craftPlayer.setAchievementPoints(ps.getResultSet().getLong("achievement_points"));
                craftPlayer.setPayToggle(ps.getResultSet().getBoolean("paytoggle"));
                craftPlayer.setEventPoints(ps.getResultSet().getLong("event_points"));
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

    public final int getPlayerEconomy(final LevelType type, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE nick = '" + player + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final int getPlayerEconomy(final EconomyType type, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE nick = '" + player + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final int getPlayerEconomy(final EconomyType type, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final int getPlayerEconomy(final LevelType type, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        String finalType = type.name().toLowerCase();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + finalType + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt(finalType);
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final int getPlayerEconomy(final String column, final Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + column + " FROM player_profile WHERE uuid = '" + player.getUniqueId().toString() + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt(column);
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

    public final void insertChangeIntoChangelog(final Player player, final String sender, final ChangeActions action,
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
                    ps.setString(5, action.toString());
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

    public final void sendPlayerAchievementLog(final Player p, final AchievementReward achievement) {
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
                    ps.setInt(4, achievement.getAchievementValue());
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
                    ps = conn.prepareStatement("UPDATE player_profile SET total_votes = total_votes + 1, week_votes = week_votes + 1, month_votes = month_votes + 1, last_vote = '" + System.currentTimeMillis() + "' WHERE nick = '" + p + "';");
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

    public final int getVaultEcoBalance(final UUID uuid) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT balance FROM player_economy_" + server + " WHERE uuid = '" + uuid + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt("balance");
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public final int getVaultEcoBalance(final String nick) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT balance FROM player_economy_" + server + " WHERE nick = '" + nick + "';");
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getInt("balance");
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

        final String server = Main.getServerType().name().toLowerCase();
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
    public Map<Integer, List> getVaultAllLogsByUUID(String UUIDstring) {
        Map<Integer, List> listMap = new HashMap<Integer, List>();
        List<String> recieverNick = new ArrayList<>();
        List<String> recieverUUID = new ArrayList<>();
        List<String> senderNick = new ArrayList<>();
        List<String> senderUUID = new ArrayList<>();
        List<String> action = new ArrayList<>();
        List<Long> amount = new ArrayList<>();
        List<Long> time = new ArrayList<>();

        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `reciever`, `r_uuid`, `sender`, `s_uuid`, `action`, `amount`, `time` FROM `economy_" + server + "_log` WHERE `action` <> \"PAY_COMMAND\" AND `r_uuid` = \"" + UUIDstring + "\" ORDER BY `economy_" + server + "_log`.`time` DESC");
            ps.executeQuery();
            while (ps.getResultSet().next()) {
                recieverNick.add(ps.getResultSet().getString("reciever"));
                recieverUUID.add(ps.getResultSet().getString("r_uuid"));
                senderNick.add(ps.getResultSet().getString("sender"));
                senderUUID.add(ps.getResultSet().getString("s_uuid"));
                action.add(ps.getResultSet().getString("action"));
                amount.add(ps.getResultSet().getLong("amount"));
                time.add(ps.getResultSet().getLong("time"));
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        listMap.put(1, recieverNick);
        listMap.put(2, recieverUUID);
        listMap.put(3, senderNick);
        listMap.put(4, senderUUID);
        listMap.put(5, action);
        listMap.put(6, amount);
        listMap.put(7, time);
        return listMap;
    }

    public Map<Integer, List> getVaultAllLogsByNickname(String playerNickname) {
        Map<Integer, List> listMap = new HashMap<Integer, List>();
        List<String> recieverNick = new ArrayList<>();
        List<String> recieverUUID = new ArrayList<>();
        List<String> senderNick = new ArrayList<>();
        List<String> senderUUID = new ArrayList<>();
        List<String> action = new ArrayList<>();
        List<Long> amount = new ArrayList<>();
        List<Long> time = new ArrayList<>();

        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `reciever`, `r_uuid`, `sender`, `s_uuid`, `action`, `amount`, `time` FROM `economy_" + server + "_log` WHERE `action` <> \"PAY_COMMAND\" AND `reciever` = \"" + playerNickname+ "\" ORDER BY `economy_" + server + "_log`.`time` DESC");
            ps.executeQuery();
            while (ps.getResultSet().next()) {
                recieverNick.add(ps.getResultSet().getString("reciever"));
                recieverUUID.add(ps.getResultSet().getString("r_uuid"));
                senderNick.add(ps.getResultSet().getString("sender"));
                senderUUID.add(ps.getResultSet().getString("s_uuid"));
                action.add(ps.getResultSet().getString("action"));
                amount.add(ps.getResultSet().getLong("amount"));
                time.add(ps.getResultSet().getLong("time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        listMap.put(1, recieverNick);
        listMap.put(2, recieverUUID);
        listMap.put(3, senderNick);
        listMap.put(4, senderUUID);
        listMap.put(5, action);
        listMap.put(6, amount);
        listMap.put(7, time);
        return listMap;
    }

    public List<Long> getVaultAllEcos() {
        List<Long> balances = new ArrayList<>();

        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT `balance` FROM `player_economy_" + server + "` WHERE `balance` > 0");
            ps.executeQuery();
            while (ps.getResultSet().next()) {
                balances.add(ps.getResultSet().getLong("balance"));
            }
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return balances;
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
        throw new NullPointerException("UUID pro hrace (" + name + ") nebylo nalezeno!") ;
    }

    public void setVaultEcoBalance(final String player, final long amount) {
        final String server = Main.getServerType().name().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_economy_" + server + " SET balance = ? WHERE nick = ?");
            ps.setLong(1, amount);
            ps.setString(2, player);
            ps.executeUpdate();
        } catch (Exception e) {
            Main.getInstance().sendSentryException(e);
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final void createVaultEcoProfile(final Player player) {
        long currentTime = System.currentTimeMillis();
        final String server = Main.getServerType().name().toLowerCase();
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("INSERT INTO player_economy_" + server + " (nick, uuid, last_update) VALUES (?,?,?);");
                    ps.setString(1, player.getName());
                    ps.setString(2, player.getUniqueId().toString());
                    ps.setLong(3, currentTime);
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
     * @param p Player object
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
     * @param p Player object
     * @param settings Settings name
     * @param value New value for selected settings
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
