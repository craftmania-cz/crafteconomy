package cz.craftmania.crafteconomy.events;

import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Runs when player recieve level up
 */
public class PlayerLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private CraftPlayer player;
    private long amount;
    private long newLevel;

    public PlayerLevelUpEvent(CraftPlayer player, long amount, long newLevel) {
        super(true);
        this.player = player;
        this.amount = amount;
        this.newLevel = newLevel;
    }

    public CraftPlayer getPlayer() {
        return player;
    }

    /**
     * Returns value what player recieved
     * @return amount of levels
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Returns new level of player
     * Example: player level up from 5 to 6 returns 6
     * @return final value of level
     */
    public long getNewLevel() {
        return newLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
