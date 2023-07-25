package cz.craftmania.crafteconomy.events.economy;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Calls when:<br>
 * - CraftEconomy plugin run task for deleting old players from vault database.
 */
public class AsyncCraftEconomyVaultCleanUpEvent extends Event {

    private final String playerName;
    private final double moneyAmount;
    private final String lastUpdated;

    public AsyncCraftEconomyVaultCleanUpEvent(final String playerName, double moneyAmount, final String lastUpdated) {
        super(true);
        this.playerName = playerName;
        this.moneyAmount = moneyAmount;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Returns name of player which has been deleted.
     *
     * @return {@link String} Player name
     */
    @NotNull
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns amount of money, which has been deleted.
     *
     * @return {@link Long} Deleted money
     */
    public double getMoneyAmount() {
        return moneyAmount;
    }

    /**
     * Returns last update date of economy profile.
     * ex. 10.2.2022 19:30 (Czech date)
     *
     * @return {@link String} Date as string
     */
    @NotNull
    public String getLastUpdated() {
        return lastUpdated;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
