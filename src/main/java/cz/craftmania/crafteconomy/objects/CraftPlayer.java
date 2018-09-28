package cz.craftmania.crafteconomy.objects;

import org.bukkit.entity.Player;

public class CraftPlayer {

    private Player player;
    private long coins;
    private long tokens;
    private long level;
    private long experience;

    public CraftPlayer(Player player) {
        this.player = player;
    }

}
