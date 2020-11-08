package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.Bukkit;

public class EconomySaveTask implements Runnable {

    private final BasicManager bm = new BasicManager();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (bm.getCraftPlayer(player) != null) {
                long balance = bm.getCraftPlayer(player).getMoney();
                Main.getInstance().getMySQL().setVaultEcoBalance(player.getName(), balance);
            }
        });
        Logger.info("Economy update v MySQL dokončen.");
    }
}
