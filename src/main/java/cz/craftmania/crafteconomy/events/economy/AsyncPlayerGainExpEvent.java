package cz.craftmania.crafteconomy.events.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Calls when:
 * - Player receive experience from playing on server
 * - Player receive experience from API or Economy command
 */
public class AsyncPlayerGainExpEvent extends Event {

    private final Player player;
    private final int amount;

    public AsyncPlayerGainExpEvent(final Player player, int amount) {
        super(true);
        this.player = player;
        this.amount = amount;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns amount of experience that player received
     *
     * @return {@link Integer} Experience amount
     */
    public int getAmount() {
        return amount;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

