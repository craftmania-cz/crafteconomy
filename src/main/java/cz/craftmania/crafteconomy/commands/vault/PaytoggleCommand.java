package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import io.github.jorelali.commandapi.api.CommandAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PaytoggleCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {
        CommandAPI.getInstance().register("paytoggle", new String[]{}, null, (sender, args) -> {
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
        });
    }
}
