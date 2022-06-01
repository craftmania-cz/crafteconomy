package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.craftlibs.utils.ChatInfo;
import cz.craftmania.craftpack.api.TextureItems;
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
        if (sender instanceof Player player) {
            switch (Main.getInstance().getMySQL().getSettings(player, "paytoggle")) {
                case 0 -> {
                    ChatInfo.INFO.overridePrefix(TextureItems.BANK_WARNING.getRender())
                            .send(player, "Aktivoval jsi přijímání peněz!");
                    Main.getInstance().getMySQL().updateSettings(player, "paytoggle", 1);
                    manager.getCraftPlayer(player).setPayToggle(true);
                }
                case 1 -> {
                    ChatInfo.INFO.overridePrefix(TextureItems.BANK_WARNING.getRender())
                                    .send(player, "Deaktivoval jsi přijímání peněz! Nyní ti nikdo nemůže poslat peníze.");
                    Main.getInstance().getMySQL().updateSettings(player, "paytoggle", 0);
                    manager.getCraftPlayer(player).setPayToggle(false);
                }
            }
        } else {
            sender.sendMessage("§cTento příkaz nelze použít v konzoli!");
        }
    }
}
