package cz.craftmania.crafteconomy.listener;

import io.github.luxuryquests.api.events.PlayerCompletedQuestEvent;
import io.github.luxuryquests.objects.quest.Quest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuestCompleteListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onQuestComplete(PlayerCompletedQuestEvent event) {
        Quest quest = event.getQuest();
        System.out.println("--- KOMPLENÍ QUEST ---");
        System.out.println("ID: " + quest.getId());
        System.out.println("NAME: " + quest.getName());
        System.out.println("TYPE: " + quest.getType());
    }
}
