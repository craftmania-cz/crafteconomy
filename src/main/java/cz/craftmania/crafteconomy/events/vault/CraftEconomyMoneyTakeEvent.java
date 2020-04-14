package cz.craftmania.crafteconomy.events.vault;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CraftEconomyMoneyTakeEvent extends Event {

    private String sender;
    private String reciever;
    private long value;

    public CraftEconomyMoneyTakeEvent(String sender, String reciever, long value) {
        super(true);
        this.sender = sender;
        this.reciever = reciever;
        this.value = value;
    }

    /**
     * Kdo bere hodnotu peněz, může to být i CONSOLE.
     * @return {@link String}
     */
    public String getSender() {
        return sender;
    }

    /**
     * Nick hráče, kterému bude částka odebrána
     * @return {@link String}
     */
    public String getReciever() {
        return reciever;
    }

    /**
     * Hodnota peněz, kterému je částka odebrána
     * @return {@link Long}
     */
    public long getValue() {
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
