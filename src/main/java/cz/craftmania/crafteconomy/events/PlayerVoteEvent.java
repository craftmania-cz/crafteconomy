package cz.craftmania.crafteconomy.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Spustí se, když hráč je online na serveru a provede úspěšné zahlasování pro server.
 */
public class PlayerVoteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    //TODO: Až budou všechny servery na 1.14+ přidat počet hlasů i sem
    public PlayerVoteEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
