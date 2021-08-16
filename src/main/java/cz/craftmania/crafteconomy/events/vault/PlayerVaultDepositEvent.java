package cz.craftmania.crafteconomy.events.vault;

import cz.craftmania.crafteconomy.annotations.AsynchronousEvent;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Optional;

/**
 * Spustí se, když proběhne uložení přes Vault - Economy#depositPlayer(OfflinePlayer, double)
 */
@AsynchronousEvent
public class PlayerVaultDepositEvent extends Event {

    private final OfflinePlayer player;
    private final double amount;
    private final String world;
    private final EconomyResponse.ResponseType response;

    public PlayerVaultDepositEvent(OfflinePlayer player, double amount, EconomyResponse.ResponseType response) {
        this(player, amount, null, response);
    }

    public PlayerVaultDepositEvent(OfflinePlayer player, double amount, String world, EconomyResponse.ResponseType response) {
        super(true);
        this.player = player;
        this.amount = amount;
        this.world = world;
        this.response = response;
    }

    public OfflinePlayer getOfflinePlayer() {
        return player;
    }

    public double getAmount() {
        return amount;
    }

    public EconomyResponse.ResponseType getResponse() {
        return response;
    }

    public Optional<String> getWorldName() {
        return Optional.ofNullable(world);
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

