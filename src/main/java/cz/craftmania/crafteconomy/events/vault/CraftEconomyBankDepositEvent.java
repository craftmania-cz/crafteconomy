package cz.craftmania.crafteconomy.events.vault;

import cz.craftmania.crafteconomy.annotations.AsynchronousEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Spustí se, pokud hráč uloží určité prostředky do banky.
 */
@AsynchronousEvent
public class CraftEconomyBankDepositEvent extends Event {

    private final Player player;
    private final int amount;

    public CraftEconomyBankDepositEvent(Player player, int amount) {
        super(true);
        this.player = player;
        this.amount = amount;
    }

    /**
     * Vrátí hráče, který si vybral prostředky.
     * @return {@link Player}
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Vrací hodnotu uloženou do banky.
     * @return {@link Player}
     */
    public int getAmount() {
        return amount;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
