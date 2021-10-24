package cz.craftmania.crafteconomy.listener;

import cz.craftmania.craftcore.events.time.WeekChangeEvent;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.craftlibs.utils.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WeekChangeListener implements Listener {

    @EventHandler
    public void onWeekChange(WeekChangeEvent event) {
        int cleanDays = Main.getInstance().getConfig().getInt("vault-economy.cleanup.days", 150);

        Log.info("Start týdenního mazání hráčů z databáze.");
        Log.info("Aktuální čas je nastaven na: " + cleanDays + " dní.");


    }

    private void cleanUpDatabase(int days) {

        // GET ALL
        // SAVE IN ARRAY
        // DO CLEAN UP ONE BY ONE
        // PRINT ALL STATS
        // END


    }
}
