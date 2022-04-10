package cz.craftmania.crafteconomy.objects;

import cz.craftmania.craftactions.profile.NotificationPriority;
import cz.craftmania.craftactions.profile.NotificationType;
import org.bukkit.entity.Player;

public record NotificationObject(
        Player player,
        int notificationId,
        NotificationType notificationType,
        NotificationPriority notificationPriority,
        String notificationServer,
        String title,
        String message,
        boolean isRead
) {

    @Override
    public Player player() {
        return player;
    }

    @Override
    public int notificationId() {
        return notificationId;
    }

    @Override
    public NotificationType notificationType() {
        return notificationType;
    }

    @Override
    public NotificationPriority notificationPriority() {
        return notificationPriority;
    }

    @Override
    public String notificationServer() {
        return notificationServer;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean isRead() {
        return isRead;
    }
}
