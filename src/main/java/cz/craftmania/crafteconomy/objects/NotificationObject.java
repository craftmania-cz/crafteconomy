package cz.craftmania.crafteconomy.objects;

import cz.craftmania.craftactions.profile.NotificationPriority;
import cz.craftmania.craftactions.profile.NotificationType;
import org.bukkit.entity.Player;

public class NotificationObject {

    private int notificationId;
    private Player player;
    private NotificationType notificationType;
    private NotificationPriority notificationPriority;
    private String notificationServer;
    private String title;
    private String message;
    private boolean isRead;

    public NotificationObject(
            Player player,
            int notificationId,
            NotificationType notificationType,
            NotificationPriority notificationPriority,
            String notificationServer,
            String title,
            String message,
            boolean isRead
    ) {
        this.player = player;
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.notificationPriority = notificationPriority;
        this.notificationServer = notificationServer;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public NotificationPriority getNotificationPriority() {
        return notificationPriority;
    }

    public String getNotificationServer() {
        return notificationServer;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
