package cz.craftmania.crafteconomy.listener;

import cz.craftmania.craftactions.economy.PlayerCreateCcomunityProfileEvent;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

//TODO: Přesunout do loggeru
public class PlayerCreateProfileListener implements Listener {

    @EventHandler
    public void onCreate(PlayerCreateCcomunityProfileEvent event) {

        final Player player = event.getPlayer();

        Logger.info("Economy register - " + player.getName() + " (" + player.getUniqueId() + ")");

        // Player's changelog
        Main.getInstance().getMySQL().insertChangeIntoChangelog(event.getPlayer(), "server",
               "ECONOMY_REGISTER" , "0", "0");

    }
}
