package cz.craftmania.crafteconomy.events.vault;

import cz.craftmania.crafteconomy.annotations.AsynchronousEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

/**
 * Spustí se, když hráč provede úspěšně /pay příkaz - převede částku hráči.
 */
@AsynchronousEvent
public class CraftEconomyPlayerPayEvent extends Event {

    private Player sender;
    private Player reciever;
    private double amount;
    private Date date;

    public CraftEconomyPlayerPayEvent(Player sender, Player reciever, double amount) {
        super(true);
        this.sender = sender;
        this.reciever = reciever;
        this.amount = amount;
        this.date = new Date();
    }

    /**
     * Vrací hráče, který zasílá požadovanou částku.
     * @return {@link Player}
     */
    public Player getSender() {
        return sender;
    }

    /**
     * Vrací hráče, který obdržel zasílanou částku.
     * @return {@link Player}
     */
    public Player getReciever() {
        return reciever;
    }

    /**
     * Převáděná částka
     * @return {@link Long}
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Vrací datum provedené transakce
     * @return {@link Date}
     */
    public Date getDate() {
        return date;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
