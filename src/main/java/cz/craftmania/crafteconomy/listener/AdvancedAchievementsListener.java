package cz.craftmania.crafteconomy.listener;

import com.hm.achievement.utils.PlayerAdvancedAchievementEvent;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.AchievementPointsAPI;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.ProprietaryManager;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.AchievementReward;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AdvancedAchievementsListener implements Listener {

    private BasicManager manager = new BasicManager();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerAdvancedAchievementReception(PlayerAdvancedAchievementEvent event) {
        String name = event.getName();
        Player player = event.getPlayer();

        ProprietaryManager.getServerAchievementList().forEach(achievement -> {
            if (achievement.getId().equalsIgnoreCase(name)) {
                doAction(achievement, player);
            }
        });

    }

    private void doAction(AchievementReward achievement, Player player) {

        if (achievement == null || player == null) {
            return;
        }

        Logger.info("Hrac: " + player.getName() + ", splnil achievement: " + achievement.getName());
        Main.getInstance().getMySQL().sendPlayerAchievementLog(player, achievement);

        StringBuilder finalRewards = new StringBuilder();

        // Items
        if (!achievement.getItems().isEmpty()) { //TODO: Kontrola inventáře zda je plný nebo ne
            achievement.getItems().forEach(itemStack -> {
                player.getInventory().addItem(itemStack);
            });
        }

        // Achievement Points
        if (achievement.getAchievementValue() > 0) { // Default = 0
            AchievementPointsAPI.giveAchievementPoints(player, achievement.getAchievementValue());
            finalRewards.append("§d" + achievement.getAchievementValue() + " AchPoints").append("§7, ");
        }

        // Server Experience
        if (achievement.getExperienceValue() > 0) {
            LevelAPI.addExp(player, manager.getExperienceByServer(), achievement.getExperienceValue());
            finalRewards.append("§6" + achievement.getExperienceValue() + " EXP");
        }

        // Permissions
        if (!achievement.getPermissions().isEmpty()) {
            achievement.getPermissions().forEach(permission -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + permission + " " + Main.getServerType().name().toLowerCase());
            });
        }

        // Notify
        player.sendMessage("§d\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        player.sendMessage("");
        player.sendMessage("§c§lSplnil jsi achievement: §f" + achievement.getName());
        achievement.getDescription().forEach(description -> {
            player.sendMessage("§7" + description);
        });
        player.sendMessage("");
        player.sendMessage("§eDostal jsi: " + finalRewards.toString());
        player.sendMessage("");
        player.sendMessage("§d\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
    }
}
