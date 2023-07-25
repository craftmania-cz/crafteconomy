package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.CleanUpManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandAlias("economy")
@Description("Spravuje ekonomiku non-vault věcí")
public class EconomyCommand extends BaseCommand {

    private static final BasicManager manager = new BasicManager();
    private CleanUpManager cleanUpManager = new CleanUpManager();

    @HelpCommand
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lEconomy commands:");
        help.showHelp();
    }

    @Subcommand("add|give")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@economyType @players [pocet]")
    @Syntax("[economyType] [nick] [pocet]")
    public void adminGiveEconomy(CommandSender sender, EconomyType economyType, String editedPlayer, long valueToAdd) {
        Player player = Bukkit.getPlayer(editedPlayer);
        if (player != null) { // Online
            switch (economyType) {
                case QUEST_POINTS -> {
                    EconomyAPI.QUEST_POINTS.give(player, valueToAdd);
                    ChatInfo.INFO.send(sender, "Přidal jsi hráči §f" + editedPlayer + " §7- §d" + valueToAdd + " QuestPoints.");
                }
                case EVENT_POINTS -> {
                    EconomyAPI.EVENT_POINTS.give(player, valueToAdd);
                    ChatInfo.INFO.send(sender, "Přidal jsi hráči §f" + editedPlayer + " §7- §d" + valueToAdd + " EventPoints.");
                }
                default -> {
                    sender.sendMessage("§cNepodporovaný typ ekonomiky pro přidání.");
                }
            }
        } else { // offline
            switch (economyType) {
                case QUEST_POINTS -> {
                    EconomyAPI.QUEST_POINTS.giveOffline(editedPlayer, valueToAdd);
                    ChatInfo.INFO.send(sender, "Přidal jsi hráči §f" + editedPlayer + " §7- §d" + valueToAdd + " QuestPoints.");
                }
                case EVENT_POINTS -> {
                    EconomyAPI.EVENT_POINTS.give(editedPlayer, valueToAdd);
                    ChatInfo.INFO.send(sender, "Přidal jsi hráči §f" + editedPlayer + " §7- §d" + valueToAdd + " EventPoints.");
                }
                default -> {
                    sender.sendMessage("§cNepodporovaný typ ekonomiky pro přidání.");
                }
            }
        }
    }

    @Subcommand("add|give exp")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminGiveExperience(CommandSender sender, String editedPlayer, long valueToAdd) {
        Player player = Bukkit.getPlayer(editedPlayer);
        if (player != null) {
            LevelAPI.addExp(player, Objects.requireNonNull(manager.getExperienceByServer()), (int) valueToAdd);
            ChatInfo.INFO.send(sender, "Přidal jsi hráči §f" + editedPlayer + " §7- §b" + valueToAdd + " XP.");
        } else {
            ChatInfo.DANGER.send(sender, "Hráč je offline, nelze mu přidat expy.");
        }
    }

    @Subcommand("cleanup")
    @CommandPermission("crafteconomy.admin.cleanup")
    @CommandCompletion("[pocetDni] confirm")
    @Syntax("[pocetDni] confirm")
    public void adminCleanup(CommandSender sender, int days, String confirm) {
        if (sender instanceof Player) {
            ChatInfo.DANGER.send(sender, "Tento příkaz můžeš použít pouze z konzole.");
            return;
        }
        if (confirm == null) {
            ChatInfo.DANGER.send(sender, "Musíš potvrdit smazání databáze.");
            return;
        }
        if (days < 180) {
            ChatInfo.DANGER.send(sender, "Nelze smazat ekonomiku starou méně než 180 dní.");
            return;
        }
        cleanUpManager.cleanUpDatabase(days);
    }
}
