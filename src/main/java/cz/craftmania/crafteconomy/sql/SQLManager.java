package cz.craftmania.crafteconomy.sql;

import com.zaxxer.hikari.HikariDataSource;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.ChangeActions;
import cz.craftmania.crafteconomy.objects.AchievementReward;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
            e.printStackTrace();
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final boolean hasDataByName(final String p) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE nick = ?;");
            ps.setString(1, p);
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
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
            ps = conn.prepareStatement("SELECT * FROM player_profile WHERE uuid = ?;");
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
                return craftPlayer;
            }
        } catch (Exception e) {
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
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public final void updateCcominutyForceNick(final Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE player_profile SET nick = ? WHERE uuid = '" + p.getUniqueId().toString() + "';");
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

    public final void updateCcominutyForceUUID(final Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("UPDATE player_profile SET uuid = ? WHERE nick = '" + p.getName() + "';");
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
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return 0;
    }

    public static Map<Integer, List> getVaultAllEcosWithNicks() {
        Map<Integer, List> listMap = new HashMap<Integer, List>();
        List<String> nicks = new ArrayList<>();
        List<Long> balances = new ArrayList<>();

        final String server = Main.getServerType().name().toLowerCase();
        ConnectionPoolManager pool = Main.getInstance().getMySQL().getPool();
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            conn = pool.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT `nick`, `balance` FROM `player_economy_" + server + "` WHERE `balance` > 0 ORDER BY `balance` DESC");
            while (rs.next()) {
                nicks.add(rs.getString("nick"));
                balances.add(rs.getLong("balance"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (st != null) st.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }

        listMap.put(1, nicks);
        listMap.put(2, balances);
        return listMap;
    }

    public static List<Long> getVaultAllEcos() {
        List<Long> balances = new ArrayList<>();

        final String server = Main.getServerType().name().toLowerCase();
        ConnectionPoolManager pool = Main.getInstance().getMySQL().getPool();
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            conn = pool.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT `balance` FROM `player_economy_" + server + "` WHERE `balance` > 0");
            while (rs.next()) {
                balances.add(rs.getLong("balance"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (st != null) st.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
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
                    e.printStackTrace();
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
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
            return false;
        } finally {
            pool.close(conn, ps, null);
        }
    }


}
