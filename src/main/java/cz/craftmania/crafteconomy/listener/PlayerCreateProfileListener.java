package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.ChangeActions;
import cz.craftmania.crafteconomy.events.PlayerCreateCcomunityProfileEvent;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerCreateProfileListener implements Listener {

    @EventHandler
    public void onCreate(PlayerCreateCcomunityProfileEvent e) {

        Logger.info("Economy register - " + e.getPlayer().getName());

        // Player's changelog
        Main.getInstance().getMySQL().insertChangeIntoChangelog(e.getPlayer(), "server",
                ChangeActions.ECONOMY_REGISTER, "0", "0", "lobby");

    }
}
