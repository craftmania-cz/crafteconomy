package cz.craftmania.crafteconomy.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Spusti se, kdyz se hrac poprvi pripoji na lobby a CraftEconomy ho zaregistruje!
 */
public class PlayerCreateCcomunityProfileEvent extends Event {

    private static final HandlerList handlers;
    private Player player;

    static {
        handlers = new HandlerList();
    }

    public PlayerCreateCcomunityProfileEvent(final Player player) {
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
