package cz.craftmania.crafteconomy.events;

import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
Spustí se, když hráč získa exp
 */
public class PlayerExpGainEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private CraftPlayer player;
    private long amount;

    public PlayerExpGainEvent(CraftPlayer p, long amount) {
        this.player = p;
        this.amount = amount;
    }

    public CraftPlayer getPlayer() {
        return player;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
