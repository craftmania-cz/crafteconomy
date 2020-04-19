package cz.craftmania.crafteconomy.events.vault;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CraftEconomyMoneySetEvent extends Event {

    private String sender;
    private String reciever;
    private long oldvalue;
    private long newValue;

    public CraftEconomyMoneySetEvent(String sender, String reciever, long oldValue, long newValue) {
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

    public long getOldvalue() {
        return oldvalue;
    }

    public long getNewValue() {
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
