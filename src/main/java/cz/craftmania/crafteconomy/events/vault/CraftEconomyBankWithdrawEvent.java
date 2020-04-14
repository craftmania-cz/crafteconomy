package cz.craftmania.crafteconomy.events.vault;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
    Spustí se, když hráč uloží do své banky určitý počet emeraldů.
 */
public class CraftEconomyBankWithdrawEvent extends Event {

    private final Player player;
    private final int amount;

    public CraftEconomyBankWithdrawEvent(Player player, int amount) {
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
     * Vrací hodnotu vybranou z banky.
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
