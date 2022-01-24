package cz.craftmania.crafteconomy.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IEconomy<T extends Enum<T>> {

    default @Nullable String name() {
        return null;
    }

    default @Nullable String databaseColumn() {
        return null;
    }

    /**
     * Vrací počet {@link T} měny.
     *
     * @param player Zvolený hráč jako String
     * @return Počet {@link T} měny, 0 když hráč neexistuje
     */
    long get(@NotNull final String player);
    long get(@NotNull final Player player);

    void give(@NotNull final String player, final long amountToAdd);
    void give(@NotNull final Player player, final long amountToAdd);
    void giveOffline(@NotNull final String player, final long amountToAdd);

    void take(@NotNull final String player, final long amountToTake);
    void take(@NotNull final Player player, final long amountToTake);
    void takeOffline(@NotNull final String player, final long amountToTake);

    void payBetween(@NotNull final Player sender, @NotNull final Player target, long amountToPay);


}
