package cz.craftmania.crafteconomy.objects;

import org.bukkit.entity.Player;

import java.util.HashSet;

public class CraftPlayer {

    private Player player;
    private long coins;
    private long tokens;
    private long level;
    private long experience;
    private long karma;
    private HashSet<Multiplier> multipliers;

    public CraftPlayer(){}

    public CraftPlayer(Player player) {
        this.player = player;
    }

    public CraftPlayer(Player player, long coins, long tokens, long level, long experience, long karma){
        this.player = player;
        this.coins = coins;
        this.tokens = tokens;
        this.level = level;
        this.experience = experience;
        this.karma = karma;
        this.multipliers = new HashSet<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public void setKarma(long karma) {
        this.karma = karma;
    }

    public long getKarma() {
        return karma;
    }

    public HashSet<Multiplier> getMultipliers() {
        return multipliers;
    }

    public void setMultipliers(HashSet<Multiplier> multipliers) {
        this.multipliers = multipliers;
    }
}
