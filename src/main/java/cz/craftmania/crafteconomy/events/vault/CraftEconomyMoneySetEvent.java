package cz.craftmania.crafteconomy.events.vault;

import cz.craftmania.crafteconomy.annotations.AsynchronousEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AsynchronousEvent
public class CraftEconomyMoneySetEvent extends Event {

    private String sender;
    private String reciever;
    private double oldvalue;
    private double newValue;

    public CraftEconomyMoneySetEvent(String sender, String reciever, double oldValue, double newValue) {
        super(true);
        this.sender = sender;
        this.reciever = reciever;
        this.oldvalue = oldValue;
        this.newValue = newValue;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public double getOldvalue() {
        return oldvalue;
    }

    public double getNewValue() {
        return newValue;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
