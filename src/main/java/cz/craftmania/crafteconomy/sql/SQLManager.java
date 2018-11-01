package cz.craftmania.crafteconomy.sql;

import com.zaxxer.hikari.HikariDataSource;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
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
                return new CraftPlayer(p,
                        ps.getResultSet().getLong("craftcoins"),
                        ps.getResultSet().getLong("crafttokens"),
                        ps.getResultSet().getLong("votetokens"),
                        ps.getResultSet().getLong("level"),
                        ps.getResultSet().getLong("experience"),
                        ps.getResultSet().getLong("karma"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        return null;
    }

    public void setEconomy(final String column, final Player p, final long value) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE player_profile SET " + column + " = ? WHERE uuid = ?");
            ps.setLong(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public final int getPlayerEconomy(final String column, final String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + column + " FROM player_profile WHERE nick = '" + player + "';");
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

    public final int getPlayerEconomy(final String column, final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT " + column + " FROM player_profile WHERE uuid = '" + uuid.toString() + "';");
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
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = pool.getConnection();
                    ps = conn.prepareStatement("INSERT INTO player_profile (discriminator, nick ,uuid, registred, last_online, last_server) VALUES (?,?,?,?,?,?);");
                    ps.setString(1, PlayerUtils.createDiscriminator());
                    ps.setString(2, p.getName());
                    ps.setString(3, p.getUniqueId().toString());
                    ps.setLong(4, System.currentTimeMillis());
                    ps.setLong(5, System.currentTimeMillis());
                    ps.setString(6, "lobby");
                    ps.executeUpdate();
                } catch (Exception e) {
                    //e.printStackTrace(); Schvalne duplikace
                } finally {
                    pool.close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }




}
