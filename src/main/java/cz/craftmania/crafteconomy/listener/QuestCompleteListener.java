package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.QuestManager;
import cz.craftmania.crafteconomy.objects.QuestReward;
import cz.craftmania.crafteconomy.utils.Logger;
import io.github.luxuryquests.api.events.PlayerCompletedQuestEvent;
import io.github.luxuryquests.objects.quest.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuestCompleteListener implements Listener {

    private final BasicManager manager = new BasicManager();

    @EventHandler
    public void onQuestComplete(final PlayerCompletedQuestEvent event) {
        Quest quest = event.getQuest();
        Player player = Bukkit.getPlayer(event.getUser().getUuid());

        Logger.debug("Splněný quest: " + quest.getName() + "(" + quest.getId() + "), type: " + quest.getType());

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            QuestManager.getQuestRewards().forEach(economyQuest -> {
                if (economyQuest.getName().equalsIgnoreCase(quest.getName())) {
                    doAction(economyQuest, player);
                }
            });
        });
    }

    private void doAction(QuestReward quest, Player player) {

        if (quest == null || player == null) {
            return;
        }

        Logger.info("Hrac: " + player.getName() + ", splnil quest: " + quest.getName());

        if (!Main.getInstance().getConfig().getBoolean("disables.quest-log", false)) {
            Main.getInstance().getMySQL().sendPlayerAchievementLog(player, quest);
        }

        StringBuilder finalRewards = new StringBuilder();

        // Items
        /*if (!quest.getItems().isEmpty()) { //TODO: Kontrola inventáře zda je plný nebo ne
            quest.getItems().forEach(itemStack -> {
                player.getInventory().addItem(itemStack);
            });
        }*/

        // Achievement Points
        if (quest.getQuestPointsValue() > 0) { // Default = 0
            if (!Main.getInstance().getConfig().getBoolean("disables.quest-points", false)) {
                EconomyAPI.QUEST_POINTS.give(player, quest.getQuestPointsValue());
                finalRewards.append("§d" + quest.getQuestPointsValue() + " QuestPoints").append("§7, ");
            }
        }

        // Server Experience
        if (quest.getExperienceValue() > 0) {
            if (!Main.getInstance().getConfig().getBoolean("disables.quest-experience", false)) {
                LevelAPI.addExp(player, manager.getExperienceByServer(), quest.getExperienceValue());
                finalRewards.append("§6" + quest.getExperienceValue() + " EXP");
            }
        }

        // Permissions
        if (!quest.getPermissions().isEmpty()) {
            quest.getPermissions().forEach(permission -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + permission + " " + Main.getFixedServerType().toLowerCase());
            });
        }

        // Console commands
        if (!quest.getCommands().isEmpty()) {
            quest.getCommands().forEach(command -> Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()).replace("%server%", Main.getServerType().name().toLowerCase()))));
        }

        // Notify
        player.sendMessage("§d\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        player.sendMessage("");
        player.sendMessage("§c§lSplnil jsi quest: §f" + quest.getName());
        quest.getDescription().forEach(description -> {
            player.sendMessage("§7" + description);
        });
        player.sendMessage("");
        player.sendMessage("§eDostal jsi: " + finalRewards.toString());
        player.sendMessage("");
        player.sendMessage("§d\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
    }
}
