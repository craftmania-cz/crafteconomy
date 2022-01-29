package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.objects.economics.*;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum EconomyAPI implements IEconomy<EconomyAPI> {

    CRAFT_COINS("CraftCoins", new CraftCoinsEconomy()),
    CRAFT_TOKENS("CraftTokens", new CraftTokensEconomy()),
    VOTE_TOKENS("VoteTokens", new VoteTokensEconomy()),
    EVENT_POINTS("EventPoints", new EventPointsEconomy()),
    QUEST_POINTS("QuestPoints", new QuestPointsEconomy()),
    SEASON_POINTS("SeasonPoints", new SeasonPointsEconomy());

    private final @Getter String name;
    private final @Getter IEconomy<?> economyType;

    EconomyAPI(String name, IEconomy<?> economyType) {
        this.name = name;
        this.economyType = economyType;
    }

    @Override
    public long get(@NotNull String player) {
        Objects.requireNonNull(player);
        return this.economyType.get(player);
    }

    @Override
    public long get(@NotNull Player player) {
        Objects.requireNonNull(player);
        return this.economyType.get(player);
    }

    @Override
    public void give(@NotNull String player, long amountToAdd) {
        Objects.requireNonNull(player);
        this.economyType.give(player, amountToAdd);
    }

    @Override
    public void give(@NotNull Player player, long amountToAdd) {
        Objects.requireNonNull(player);
        this.economyType.give(player, amountToAdd);
    }

    @Override
    public void giveOffline(@NotNull String player, long amountToAdd) {
        Objects.requireNonNull(player);
        this.economyType.giveOffline(player, amountToAdd);
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {
        Objects.requireNonNull(player);
        this.economyType.take(player, amountToTake);
    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Objects.requireNonNull(player);
        this.economyType.take(player, amountToTake);
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        Objects.requireNonNull(player);
        this.economyType.takeOffline(player, amountToTake);
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {
        Objects.requireNonNull(sender);
        Objects.requireNonNull(target);
        this.economyType.payBetween(sender, target, amountToPay);
    }


}
