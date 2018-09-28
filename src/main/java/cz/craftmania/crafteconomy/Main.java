package cz.craftmania.crafteconomy;

import cz.craftmania.crafteconomy.sql.SQLManager;
import cz.craftmania.crafteconomy.utils.AsyncUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AsyncUtils async;
    private SQLManager sql;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Asynchronus tasks
        async = new AsyncUtils(this);

        // HikariCP
        initDatabase();
    }

    @Override
    public void onDisable() {

        // Deaktivace MySQL
        sql.onDisable();

        instance = null;

    }

    public static Main getInstance() {
        return instance;
    }

    public static AsyncUtils getAsync() {
        return async;
    }

    public SQLManager getMySQL() {
        return sql;
    }

    private void initDatabase() {
        sql = new SQLManager(this);
    }

}
