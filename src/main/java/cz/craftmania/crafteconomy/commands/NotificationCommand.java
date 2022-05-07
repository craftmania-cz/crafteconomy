package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftactions.profile.NotificationPriority;
import cz.craftmania.craftactions.profile.NotificationType;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.managers.NotificationManager;
import cz.craftmania.crafteconomy.menu.NotificationGUI;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("notification")
@Description("Zobrazuje tvůj aktuální počet CraftCoins")
public class NotificationCommand extends BaseCommand {

    private final NotificationManager notificationManager = new NotificationManager();

    @Default
    public void showNotifications(CommandSender sender) {
        if (sender instanceof Player) {
            SmartInventory.builder().size(6, 9).title("Notifications").provider(new NotificationGUI()).build().open((Player) sender);
        }
    }

    @Subcommand("create")
    @CommandPermission("crafteconomy.admin.notifications.create")
    @CommandCompletion("@players @notificationType @notificationPriority [serverScope] [title] [message]")
    @Syntax("[nick] [notificationType] [notificationPriority] [serverScope] [title] [message]")
    // /notification create @player @type @priority @serverScope [title] [message]
    public void createNotifications(CommandSender sender,
                                    String notifiedPlayer,
                                    NotificationType type,
                                    NotificationPriority priority,
                                    String serverScope,
                                    String title,
                                    String message
    ) {
        notificationManager.createNotification(
                notifiedPlayer, type, priority, serverScope, title, message);

        sender.sendMessage("§eNotifikace pro " + notifiedPlayer + " byla úspěšně vytvořena.");
    }
}
