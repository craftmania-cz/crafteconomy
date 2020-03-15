package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.Bukkit;

public class VoteReminderTask implements Runnable {

    private BasicManager bm = new BasicManager();

    @Override
    public void run() {
        //TODO: Napojit pozdeji na CraftEconomy API
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (bm.getCraftPlayer(player) != null) {
                CraftPlayer craftPlayer = bm.getCraftPlayer(player);
                player.sendMessage("§cNezapomen hlasovat! Tento mesic mas §f" + craftPlayer.getMonthVotes() + " §chlasu! §8(Celkem: " + craftPlayer.getTotalVotes() + ")");
            }
        });
    }
}
