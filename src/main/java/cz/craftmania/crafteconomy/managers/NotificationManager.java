package cz.craftmania.crafteconomy.managers;

import cz.craftmania.craftactions.profile.AsyncPlayerNotificationRecordEvent;
import cz.craftmania.craftactions.profile.NotificationPriority;
import cz.craftmania.craftactions.profile.NotificationType;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.NotificationObject;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Spravuje notifikace hráčů a vše okolo toho.
 */
public class NotificationManager implements Listener {

    private final BasicManager basicManager = new BasicManager();

    public void loadAndInformPlayer(final Player player, boolean informPlayer) {
        CraftPlayer craftPlayer = basicManager.getCraftPlayer(player);
        if (craftPlayer == null) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            List<NotificationObject> sqlNotificationList = Main.getInstance().getMySQL().fetchAllPlayerNotifications(player);
            craftPlayer.getNotificationList().clear();
            sqlNotificationList.forEach(craftPlayer::addNotification);
        }).thenRunAsync(() -> {
            if (informPlayer) {
                AtomicInteger notificationCount = new AtomicInteger();
                AtomicInteger notificationUrgentCount = new AtomicInteger();
                craftPlayer.getNotificationList().forEach((integer, notificationObject) -> {
                    if (notificationObject.notificationPriority() == NotificationPriority.URGENT) {
                        notificationCount.getAndIncrement();
                        notificationUrgentCount.getAndIncrement();
                    }
                    if (notificationObject.notificationPriority() == NotificationPriority.NORMAL
                            || notificationObject.notificationPriority() == NotificationPriority.HIGHER) {
                        notificationCount.getAndIncrement();
                    }
                });
                //TODO: Lepší formát...
                if (notificationCount.get() > 0) {
                    ChatInfo.INFO.send(player, "Máš " + notificationCount.get() + " nepřečtených upozornění.");
                }
                if (notificationUrgentCount.get() > 0) {
                    ChatInfo.INFO.send(player, "Pozor! Máš " + notificationUrgentCount.get() + " urgentních upozornění!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
                }
            }
        });
    }

    /**
     * Vytváří a ukládá notifikace pro hráče, a pokud je online tak mu jí i oznámí.
     * @param playerName Nick hráče, pro kterého je notifikace
     * @param type {@link NotificationType}
     * @param priority {@link NotificationPriority}
     * @param serverScope Kde notifikace platí -> název serveru
     * @param title Název notifikace
     * @param message Popis nebo zpráva notifikace
     */
    public void createNotification(
            final String playerName,
            NotificationType type,
            NotificationPriority priority,
            String serverScope,
            String title,
            String message
            ) {
        Player player = Bukkit.getPlayer(playerName);
        Logger.info("Ukládání notifikace pro: " + playerName + " -> type:" + type + ", priority: " + priority + ", title: " + title);

        Main.getInstance().getMySQL().saveNotification(player, type, priority, serverScope, title, message);

        if (player != null) { // online
            // Reload cache
            this.loadAndInformPlayer(player, false);
            // Odeslat notifikaci do chatu
            player.sendMessage("");
            player.sendMessage("§6Obdržel jsi upozornění:");
            player.sendMessage("§e" + title);
            player.sendMessage("§8Priorita: " + priority + ", Typ: " + type);
            player.sendMessage("§7" + message);
            player.sendMessage("");
        }
    }

    /**
     * Tento listener ukládá eventy do SQL a informuje o nich hráče, pokud je online.
     * Eventy pro tento listener posílají jiné plugiiny než CraftEconomy!
     */
    @EventHandler
    private void onNotificationCreate(AsyncPlayerNotificationRecordEvent event) {
        Logger.info("Notifications - Obdržený request na vytvoření notifikace:");
        this.createNotification(
                event.getPlayer(),
                event.getNotificationType(),
                event.getNotificationPriority(),
                event.getNotificationServer(),
                event.getTitle(),
                event.getDescription()
        );
    }

}
