package cz.craftmania.crafteconomy.events.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Calls when:<br>
 * - New player is created in player_profile table.
 */
public class PlayerCreateCcomunityProfileEvent extends Event {

    private final Player player;

    public PlayerCreateCcomunityProfileEvent(final Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}