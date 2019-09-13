package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.VoteTokensAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class VoteTokensCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /votetokens | /vt
        CommandAPI.getInstance().register("votetokens", new String[] {"vt"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long tokens = VoteTokensAPI.getVoteTokens(p);
            p.sendMessage("§e§l[*] §eAktualne mas " + tokens + " VoteTokens.");
        });

        // Admin prikaz: /votetokens give [player] [value]
        LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
        arguments.put("prikaz", new StringArgument().overrideSuggestions("add", "remove", "take", "give").withPermission(CommandPermission.fromString("crafteconomy.admin")));
        arguments.put("hrac", new StringArgument());
        arguments.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("votetokens", new String[] {"vt"}, arguments, (sender, args) -> {
            String subCommand = (String)args[0];
            switch (subCommand.toLowerCase()) {
                case "add": case "give":
                    String playerName = (String)args[1];
                    Player p = Bukkit.getPlayer(playerName);
                    long tokensToAdd = Long.valueOf((Integer)args[2]);
                    if (p != null) {
                        VoteTokensAPI.giveVoteTokens(p, tokensToAdd);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §b" + tokensToAdd + " VT.");
                    } else {
                        VoteTokensAPI.giveOfflineVoteTokens(playerName, tokensToAdd);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §b" + tokensToAdd + " VT.");
                    }
                    break;
                case "take": case "remove":
                    String playerName2 = (String)args[1];
                    Player player2 = Bukkit.getPlayer(playerName2);
                    long tokensToTake = Long.valueOf((Integer)args[2]);
                    if (player2 == null) {
                        VoteTokensAPI.takeOfflineVoteTOkens(playerName2, tokensToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName2 + " §7- §d" + tokensToTake + " VT.");
                        break;
                    }
                    if ((manager.getCraftPlayer(player2).getVoteTokens() - tokensToTake) < 0) {
                        sender.sendMessage("§c§l[!] §cHrac nema dostatek VoteTokens! Ma k dispozici: " + manager.getCraftPlayer(player2).getVoteTokens());
                        break;
                    }
                    VoteTokensAPI.takeVoteTokens(player2, tokensToTake);
                    sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName2 + " §7- §d" + tokensToTake + " VT.");
                    break;
            }
        });
    }
}
