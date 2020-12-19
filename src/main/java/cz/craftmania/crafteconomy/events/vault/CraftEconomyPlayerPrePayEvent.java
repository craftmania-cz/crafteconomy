package cz.craftmania.crafteconomy.events.vault;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CraftEconomyPlayerPrePayEvent extends Event implements Cancellable {

    private Player sender;
    private Player reciever;
    private boolean isCancelled;

    public CraftEconomyPlayerPrePayEvent(Player sender, Player reciever) {
        this.sender = sender;
        this.reciever = reciever;
        this.isCancelled = false;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReciever() {
        return reciever;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
