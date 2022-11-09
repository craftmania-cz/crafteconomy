package cz.craftmania.crafteconomy.events.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Calls when:
 * - Player receive level up on server
 * - Player recieved levels manually from API or Economy commands
 */
public class AsyncPlayerLevelUpEvent extends Event {

    private final Player player;
    private final int expReceived;
    private final int expRemaining;
    private final int newLevel;

    public AsyncPlayerLevelUpEvent(final Player player, int expReceived, int expRemaining, int newLevel) {
        super(true);
        this.player = player;
        this.expReceived = expReceived;
        this.expRemaining = expRemaining;
        this.newLevel = newLevel;
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
    public int getExpReceived() {
        return expReceived;
    }

    /**
     * Returns amount of experience which has been moved to next level.
     *
     * @return {@link Integer} Experience amount
     */
    public int getExpRemaining() {
        return expRemaining;
    }

    /**
     * Returns new level that player got.
     *
     * @return {@link Integer} New level
     */
    public int getNewLevel() {
        return newLevel;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

