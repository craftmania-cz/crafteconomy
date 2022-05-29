package cz.craftmania.crafteconomy.events.vault;

import cz.craftmania.crafteconomy.annotations.AsynchronousEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Spustí se, když konzole nebo admin provede /money give [nick] [částka]
 */
@AsynchronousEvent
public class CraftEconomyMoneyGiveEvent extends Event {

    private String sender;
    private String reciever;
    private double value;

    public CraftEconomyMoneyGiveEvent(String sender, String reciever, double value) {
        super(true);
        this.sender = sender;
        this.reciever = reciever;
        this.value = value;
    }

    /**
     * Kdo givuje hodnotu peněz, může to být i CONSOLE.
     * @return {@link String}
     */
    public String getSender() {
        return sender;
    }

    /**
     * Nick hráče, který obdrží částku
     * @return {@link String}
     */
    public String getReciever() {
        return reciever;
    }

    /**
     * Hodnota peněz, kterou hráč obdržel
     * @return {@link Double}
     */
    public double getValue() {
        return value;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
