package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.sql.ConnectionPoolManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class BaltopSql {

    public static Map<Integer, List> reqBaltopList() {
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
}

