package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyPlayerPayEvent;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyPlayerPrePayEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("pay")
@Description("Umožňuje posílat jiným hráčům peníze")
public class PayCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();
    private PendingPayments pendingPayments = new PendingPayments();

    int confirmThreshold = Main.getInstance().getConfig().getInt("vault-economy.min-confirm", 1000);

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lPay commands:");
        help.showHelp();
    }

    @Default
    @CommandCompletion("@players [castka]")
    @Syntax("[hrac] [castka]")
    public void sendMoney(CommandSender sender, String receiverPlayer,  double moneyToSend) {
        if (sender instanceof Player) {
            if (moneyToSend <= 0) {
                sender.sendMessage("§c§l[!] §cNelze odesílat nulovou nebo zápornou hodnotu!");
                return;
            }
            if (sender.getName().equals(receiverPlayer)) {
                sender.sendMessage("§c§l[!] §cSám sobě nelze zasílat částky, bankovní podvody nevedeme!");
                return;
            }
            if (moneyToSend > Main.getVaultEconomy().getBalance(sender.getName())) {
                sender.sendMessage("§c§l[!] §cNemáš dostatek peněz k odeslání zadané částky.");
                return;
            }
            Player playerReceiver = Bukkit.getPlayer(receiverPlayer);
            Player playerSender = (Player) sender;
            if (playerReceiver != null) {

                CraftEconomyPlayerPrePayEvent craftEconomyPlayerPrePayEvent = new CraftEconomyPlayerPrePayEvent(playerSender, playerReceiver);
                Bukkit.getPluginManager().callEvent(craftEconomyPlayerPrePayEvent);
                if (craftEconomyPlayerPrePayEvent.isCancelled()) {
                    playerSender.sendMessage("§c§l[!] §cProces odesílání peněz byl interně zastaven.");
                    return;
                }

                if (manager.getCraftPlayer(playerReceiver).getPayToggle()) {
                    if (moneyToSend >= confirmThreshold) {
                        if (!pendingPayments.hasPendingPayment(playerSender)){
                            sender.sendMessage("§e§l[*] §ePro potvrzení platby s částkou §a" + moneyToSend + Main.getInstance().getCurrency() + "§e pro hráče §f" + playerReceiver.getName() + "§e napiš §7/pay confirm§e! Pokud sis platbu rozmyslel, můžeš napsat §7/pay cancel§e.");
                            pendingPayments.addPendingPayment(new PendingPayment(playerSender, playerReceiver, moneyToSend));
                        } else {
                            PendingPayment pp = pendingPayments.getPendingPayment(playerSender);
                            sender.sendMessage("§c§l[!] §cUž máš probíhající platbu! §ePro potvrzení platby s částkou §a" + pp.moneyToSend + Main.getInstance().getCurrency() + "§e pro hráče §f" + pp.receiver.getName() + "§e napiš §7/pay confirm§e! Pokud sis platbu rozmyslel, můžeš napsat §7/pay cancel§e.");
                        }
                        return;
                    }
                    Main.getVaultEconomy().withdrawPlayer(playerSender, moneyToSend);
                    Main.getVaultEconomy().depositPlayer(playerReceiver, moneyToSend);
                    sender.sendMessage("§e§l[*] §eOdeslal jsi hráči: §f" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                    playerReceiver.sendMessage("§e§l[*] §eObdržel jsi peníze od §f" + playerSender.getName() + " §7- §a" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyPlayerPayEvent(playerSender, playerReceiver, moneyToSend)));
                } else {
                    playerSender.sendMessage("§c§l[!] §cTento hráč má vypnuté přijímání peněz!");
                    playerReceiver.sendMessage("§e§l[*] §eHráč " + playerSender.getName() + " se ti snaží poslat peníze, ale máš vypnutý /paytoggle!");
                }
            } else {
                sender.sendMessage("§c§l[!] §cHráč není online, nelze mu zaslat peníze!");
            }
        }
    }

    @Subcommand("confirm")
    @CommandCompletion("confirm")
    private void payConfirm(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PendingPayment pp = pendingPayments.getPendingPayment(p);
            if (pp != null) {
                if (pp.moneyToSend > Main.getVaultEconomy().getBalance(pp.sender)) {
                    sender.sendMessage("§c§l[!] §cNemáš dostatek peněz k odeslání zadané částky.");
                    return;
                }
                pendingPayments.removePendingPayment(pp);
                Main.getVaultEconomy().withdrawPlayer(pp.sender, pp.moneyToSend);
                Main.getVaultEconomy().depositPlayer(pp.receiver, pp.moneyToSend);
                sender.sendMessage("§e§l[*] §eOdeslal jsi hráči: §f" + Main.getInstance().getFormattedNumber(pp.moneyToSend) + Main.getInstance().getCurrency());
                pp.receiver.sendMessage("§e§l[*] §eObdržel jsi peníze od §f" + pp.sender.getName() + " §7- §a" + Main.getInstance().getFormattedNumber(pp.moneyToSend) + Main.getInstance().getCurrency());
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyPlayerPayEvent(pp.sender, pp.receiver, pp.moneyToSend)));
            } else {
                sender.sendMessage("§c§l[!] §cNemáš žádnou probíhající platbu!");
            }
        }
    }

    @Subcommand("cancel")
    @CommandCompletion("cancel")
    private void payCancel(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (pendingPayments.removePendingPayment(p)) {
                sender.sendMessage("§e§l[*] §eTvoje probíhající platba byla úspěšně zrušena.");
            } else {
                sender.sendMessage("§c§l[!] §cNemáš žádnou probíhající platbu!");
            }
        }
    }

    private class PendingPayments {

        private @Getter List<PendingPayment> PendingPayments = new ArrayList<>();

        public void addPendingPayment(PendingPayment pendingPayment) {
            PendingPayments.add(pendingPayment);
        }

        public PendingPayment getPendingPayment(Player player) {
            for (PendingPayment pp : PendingPayments) {
                if (pp.sender == player) {
                    return pp;
                }
            }
            return null;
        }

        public boolean removePendingPayment(Player player) {
            int counter = 0;
            for (PendingPayment pp : PendingPayments) {
                if (pp.sender == player) {
                    PendingPayments.remove(counter);
                    return true;
                }
                counter++;
            }
            return false;
        }

        public boolean removePendingPayment(PendingPayment pp) {
            return PendingPayments.remove(pp);
        }


        public boolean hasPendingPayment(Player player) {
            for (PendingPayment pp : PendingPayments) {
                if (pp.sender == player) {
                    return true;
                }
            }
            return false;
        }
    }

    private class PendingPayment {

        private final Player sender;
        private final Player receiver;
        private final double moneyToSend;

        PendingPayment(Player sender, Player receiver, double moneyToSend) {
            this.sender = sender;
            this.receiver = receiver;
            this.moneyToSend = moneyToSend;
        }
    }
}
