package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyPlayerPayEvent;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyPlayerPrePayEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.craftlibs.utils.ChatInfo;
import cz.craftmania.craftpack.api.TextureItems;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("pay")
@Description("Umožňuje posílat jiným hráčům peníze")
public class PayCommand extends BaseCommand {

    private static final BasicManager manager = new BasicManager();
    private final PendingPayments pendingPayments = new PendingPayments();

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
                ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                         .send(sender, "Nelze odesílat nulovou nebo zápornou hodnotu!");
                return;
            }
            if (sender.getName().equals(receiverPlayer)) {
                ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                        .send(sender, "Sám sobě nelze zasílat částky, bankovní podvody nevedeme!");
                return;
            }
            if (moneyToSend > Main.getVaultEconomy().getBalance(sender.getName())) {
                ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                        .send(sender, "Nemáš dostatek peněz k odeslání zadané částky.");
                return;
            }
            Player playerReceiver = Bukkit.getPlayer(receiverPlayer);
            Player playerSender = (Player) sender;
            if (playerReceiver != null) {

                CraftEconomyPlayerPrePayEvent craftEconomyPlayerPrePayEvent = new CraftEconomyPlayerPrePayEvent(playerSender, playerReceiver);
                Bukkit.getPluginManager().callEvent(craftEconomyPlayerPrePayEvent);
                if (craftEconomyPlayerPrePayEvent.isCancelled()) {
                    ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                            .send(sender, "Proces odesílání peněz byl interně zastaven.");
                    return;
                }

                if (manager.getCraftPlayer(playerReceiver).getPayToggle()) {
                    if (moneyToSend >= confirmThreshold) {
                        if (!pendingPayments.hasPendingPayment(playerSender)){
                            ChatInfo.INFO.overridePrefix(TextureItems.BANK_INFO.getRender())
                                            .send(sender, "Pro potvrzení platby s částkou §f" + moneyToSend + Main.getInstance().getCurrency() + "{c} pro hráče §f" + playerReceiver.getName() + "{c} napiš §7/pay confirm{c}! Pokud sis platbu rozmyslel, můžeš napsat §7/pay cancel{c}.");
                            pendingPayments.addPendingPayment(new PendingPayment(playerSender, playerReceiver, moneyToSend));
                        } else {
                            PendingPayment pendingPayment = pendingPayments.getPendingPayment(playerSender);
                            ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                                            .send(sender, "Již máš probíhající platbu! §ePro potvrzení platby s částkou §f" + pendingPayment.moneyToSend + Main.getInstance().getCurrency() + "{c} pro hráče §f" + pendingPayment.receiver.getName() + "{c} napiš §7/pay confirm{c}! Pokud sis platbu rozmyslel, můžeš napsat §7/pay cancel{c}.");
                        }
                        return;
                    }
                    Main.getVaultEconomy().withdrawPlayer(playerSender, moneyToSend);
                    Main.getVaultEconomy().depositPlayer(playerReceiver, moneyToSend);
                    ChatInfo.INFO.overridePrefix(TextureItems.BANK_INFO.getRender())
                                    .send(sender, "Odeslal jsi hráči: §f" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                    ChatInfo.INFO.overridePrefix(TextureItems.BANK_INFO.getRender())
                                    .send(playerReceiver, "Obdržel jsi peníze od §f" + playerSender.getName() + " §7- {c}" + Main.getInstance().getFormattedNumber(moneyToSend) + Main.getInstance().getCurrency());
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyPlayerPayEvent(playerSender, playerReceiver, moneyToSend)));
                } else {
                    ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                            .send(playerSender, "Tento hráč má vypnuté přijímání peněz!");
                    ChatInfo.INFO.overridePrefix(TextureItems.BANK_INFO.getRender())
                            .send(playerReceiver, "Hráč " + playerSender.getName() + " se ti snaží poslat peníze, ale máš vypnutý /paytoggle!");
                }
            } else {
                ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                        .send(sender, "Hráč není online, nelze mu zaslat peníze!");
            }
        }
    }

    @Subcommand("confirm")
    @CommandCompletion("confirm")
    private void payConfirm(CommandSender sender) {
        if (sender instanceof Player player) {
            PendingPayment pendingPayment = pendingPayments.getPendingPayment(player);
            if (pendingPayment != null) {
                if (pendingPayment.moneyToSend > Main.getVaultEconomy().getBalance(pendingPayment.sender)) {
                    ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                                    .send(sender, "Nemáš dostatek peněz k odeslání zadané částky.");
                    return;
                }
                pendingPayments.removePendingPayment(pendingPayment);
                Main.getVaultEconomy().withdrawPlayer(pendingPayment.sender, pendingPayment.moneyToSend);
                Main.getVaultEconomy().depositPlayer(pendingPayment.receiver, pendingPayment.moneyToSend);
                ChatInfo.INFO.overridePrefix(TextureItems.BANK_INFO.getRender())
                                .send(sender, "Odeslal jsi hráči: §f" + Main.getInstance().getFormattedNumber(pendingPayment.moneyToSend) + Main.getInstance().getCurrency());
                ChatInfo.ECONOMY.overridePrefix(TextureItems.BANK_SUCCESS.getRender())
                                .send(pendingPayment.receiver, "Obdržel jsi peníze od §f" + pendingPayment.sender.getName() + " §7- §a" + Main.getInstance().getFormattedNumber(pendingPayment.moneyToSend) + Main.getInstance().getCurrency());
                Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyPlayerPayEvent(pendingPayment.sender, pendingPayment.receiver, pendingPayment.moneyToSend)));
            } else {
                ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                                .send(sender, "Nemáš žádnou probíhající platbu!");
            }
        }
    }

    @Subcommand("cancel")
    @CommandCompletion("cancel")
    private void payCancel(CommandSender sender) {
        if (sender instanceof Player player) {
            if (pendingPayments.removePendingPayment(player)) {
                ChatInfo.INFO.overridePrefix(TextureItems.BANK_INFO.getRender())
                                .send(sender, "Tvoje probíhající platba byla úspěšně zrušena.");
            } else {
                ChatInfo.DANGER.overridePrefix(TextureItems.BANK_ERROR.getRender())
                                .send(sender, "Nemáš žádnou probíhající platbu!");
            }
        }
    }

    private class PendingPayments {

        private @Getter List<PendingPayment> PendingPayments = new ArrayList<>();

        public void addPendingPayment(PendingPayment pendingPayment) {
            PendingPayments.add(pendingPayment);
        }

        public PendingPayment getPendingPayment(Player player) {
            for (PendingPayment pendingPayment : PendingPayments) {
                if (pendingPayment.sender == player) {
                    return pendingPayment;
                }
            }
            return null;
        }

        public boolean removePendingPayment(Player player) {
            int counter = 0;
            for (PendingPayment pendingPayment : PendingPayments) {
                if (pendingPayment.sender == player) {
                    PendingPayments.remove(counter);
                    return true;
                }
                counter++;
            }
            return false;
        }

        public boolean removePendingPayment(PendingPayment pendingPayment) {
            return PendingPayments.remove(pendingPayment);
        }


        public boolean hasPendingPayment(Player player) {
            for (PendingPayment pendingPayment : PendingPayments) {
                if (pendingPayment.sender == player) {
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
