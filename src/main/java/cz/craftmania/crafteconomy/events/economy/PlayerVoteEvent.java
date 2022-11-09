package cz.craftmania.crafteconomy.events.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Calls when:<br>
 * - Player vote and is online on some server<br>
 * - Player vote as offline player<br>
 */
public class PlayerVoteEvent extends Event {

    private final Player player;
    private final int voteAmount;
    private final int voteTokensReceived;

    public PlayerVoteEvent(final Player player, int voteAmount, int voteTokensReceived) {
        this.player = player;
        this.voteAmount = voteAmount;
        this.voteTokensReceived = voteTokensReceived;
    }

    /**
     * Returns player as Bubkkit object which voted.
     * @return {@link Player} Player Bukkit object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns amount of votes which has been recorded for player.<br>
     * This value can be dynamic based by multipler on Bungeecord.
     * @return {@link Integer}
     */
    public int getVoteAmount() {
        return voteAmount;
    }

    /**
     * Returns amount of VoteTokens which player received during vote events.
     * This value can be dynamic based by multipler on Bungeecord.
     * @return {@link Integer}
     */
    public int getVoteTokensReceived() {
        return voteTokensReceived;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

