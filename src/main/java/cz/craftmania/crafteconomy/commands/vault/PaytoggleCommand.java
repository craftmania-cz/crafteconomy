package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.crafteconomy.Main;
import io.github.jorelali.commandapi.api.CommandAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PaytoggleCommand {

    public static void register() {
        CommandAPI.getInstance().register("paytoggle", new String[]{}, null, (sender, args) -> {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                switch (Main.getInstance().getMySQL().getSettings(p, "paytoggle")) {
                    case 0: {
                        p.sendMessage("§e§l[*] §eAktivoval jsi přijímání peněz!");
                        Main.getInstance().getMySQL().updateSettings(p, "paytoggle", 1);
                        break;
                    }
                    case 1: {
                        p.sendMessage("§e§l[*] §eDeaktivoval jsi přijímání peněz! Nyní ti nikdo nemůže poslat peníze.");
                        Main.getInstance().getMySQL().updateSettings(p, "paytoggle", 0);
                        break;
                    }
                }
            } else {
                sender.sendMessage("§c§l[!] §cTento příkaz nelze použít v konzoli!");
            }
        });
    }
}
