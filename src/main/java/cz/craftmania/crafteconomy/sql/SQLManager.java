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
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

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
                                                final String oldValue, final String newValue, final String server) {
        long currentTime = System.currentTimeMillis();
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
                    ps.setString(4, "no-server"); //TODO: CraftCore server
                    ps.setString(5, action.toString());
                    ps.setString(6, oldValue);
                    ps.setString(7, newValue);
                    ps.setLong(8, currentTime);
                    ps.setString(9, player.getName());
                    ps.setLong(10, currentTime);
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


}
