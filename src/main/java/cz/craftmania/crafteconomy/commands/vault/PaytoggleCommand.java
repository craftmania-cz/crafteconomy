package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("paytoggle")
@Description("Umožní ti vypnutí posílání/přijímání peněz")
public class PaytoggleCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lPaytoggle commands:");
        help.showHelp();
    }

    @Default
    public void changePaytoggleSettings(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (Main.getInstance().getMySQL().getSettings(p, "paytoggle")) {
                case 0: {
                    p.sendMessage("§e§l[*] §eAktivoval jsi přijímání peněz!");
                    Main.getInstance().getMySQL().updateSettings(p, "paytoggle", 1);
                    manager.getCraftPlayer(p).setPayToggle(true);
                    break;
                }
                case 1: {
                    p.sendMessage("§e§l[*] §eDeaktivoval jsi přijímání peněz! Nyní ti nikdo nemůže poslat peníze.");
                    Main.getInstance().getMySQL().updateSettings(p, "paytoggle", 0);
                    manager.getCraftPlayer(p).setPayToggle(false);
                    break;
                }
            }
        } else {
            sender.sendMessage("§c§l[!] §cTento příkaz nelze použít v konzoli!");
        }
    }
}
