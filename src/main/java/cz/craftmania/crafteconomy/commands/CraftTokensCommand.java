package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.CraftTokensAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class CraftTokensCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /crafttokens
        CommandAPI.getInstance().register("crafttokens", new String[] {"ct"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long tokens = CraftTokensAPI.getTokens(p); //TODO: Cache?
            p.sendMessage("§6Aktualne mas " + tokens + " CraftTokens.");
        });

        // Admin prikaz: /crafttokens give [player] [value]
        LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
        arguments.put("prikaz", new StringArgument().overrideSuggestions("add", "remove", "take", "give").withPermission(CommandPermission.fromString("crafteconomy.admin")));
        arguments.put("hrac", new StringArgument());
        arguments.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("crafttokens", new String[] {"ct"}, arguments, (sender, args) -> {
            String subCommand = (String)args[0];
            switch (subCommand.toLowerCase()) {
                case "add": case "give":
                    String playerName = (String)args[1];
                    Player p = Bukkit.getPlayer(playerName);
                    long tokensToAdd = Long.valueOf((Integer)args[2]);
                    if (p != null) {
                        CraftTokensAPI.giveTokens(p, tokensToAdd);
                        sender.sendMessage("§aPridal jsi hraci §f" + playerName + " §7- §d" + tokensToAdd + " CT.");
                    } else {
                        CraftTokensAPI.giveOfflineTokens(playerName, tokensToAdd);
                        sender.sendMessage("§aPridal jsi hraci §f" + playerName + " §7- §d" + tokensToAdd + " CT.");
                    }
                    break;
                case "take": case "remove":
                    String playerName2 = (String)args[1];
                    Player player2 = Bukkit.getPlayer(playerName2);
                    long tokensToTake = Long.valueOf((Integer)args[2]);
                    if (player2 == null) { //TODO: Chybi offline kontrola, lze jit do minusu
                        CraftTokensAPI.takeOfflineTokens(playerName2, tokensToTake);
                        sender.sendMessage("§cOdebral jsi hraci §f" + playerName2 + " §7- §d" + tokensToTake + " CT.");
                        break;
                    }
                    if ((manager.getCraftPlayer(player2).getTokens() - tokensToTake) < 0) {
                        sender.sendMessage("§c§l[!] §cHrac nema dostatek CraftTokens! Ma k dispozici: " + manager.getCraftPlayer(player2).getTokens());
                        break;
                    }
                    CraftTokensAPI.takeTokens(player2, tokensToTake);
                    sender.sendMessage("§cOdebral jsi hraci §f" + playerName2 + " §7- §d" + tokensToTake + " CT.");
                    break;
            }
        });

    }
}
