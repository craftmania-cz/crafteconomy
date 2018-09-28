package cz.craftmania.crafteconomy;

import cz.craftmania.crafteconomy.utils.AsyncUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static AsyncUtils async;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        async = new AsyncUtils(this);
    }

    @Override
    public void onDisable() {

        instance = null;

    }

    public static Main getInstance() {
        return instance;
    }

    public static AsyncUtils getAsync() {
        return async;
    }
}
