package cz.craftmania.crafteconomy.events;

import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Spustí se, když hráč získa nový level
 */
public class PlayerLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private CraftPlayer player;
    private long amount;
    private long newLevel;

    public PlayerLevelUpEvent(CraftPlayer player, long amount, long newLevel) {
        this.player = player;
        this.amount = amount;
        this.newLevel = newLevel;
    }

    public CraftPlayer getPlayer() {
        return player;
    }

    /**
     * Vrací hodnotu kolik levelů hráč dostal
     * @return počet levelů
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Vrací hodnotu levelu po level-upu hráče.
     * Příklad: hráč má level up z 5 -> 6 => vrátí 6
     * @return počet levelů
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
