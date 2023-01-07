package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.Triple;
import cz.craftmania.crafteconomy.utils.VaultUtils;
import cz.craftmania.craftnotifications.api.NotificationsAPI;
import cz.craftmania.craftnotifications.objects.NotificationPriority;
import cz.craftmania.craftnotifications.objects.NotificationType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class WeeklyTaxManager {

    private VaultUtils vaultUtils = new VaultUtils();

    public void doPlayerPayTax(long minBalance, int percentage) {

        Logger.info("Start automatického placení poplatků hráčů.");
        Logger.info("Minimální hodnota v bance: $" + minBalance);
        Logger.info("Procentuální hodnota placení: " + percentage + "%");

        // Nick, UUID, Balance
        AtomicReference<List<Triple<String, UUID, Double>>> listToTakeTax = new AtomicReference<>();
        CompletableFuture.runAsync(() -> {
            listToTakeTax.set(Main.getInstance().getMySQL().fetchAllEconomyTaxPaymentPlayers(minBalance));
            Logger.info("Nalezeno celkem (" + listToTakeTax.get().size() + ") hráčů, kteří budou platit poplatek.");
            if (listToTakeTax.get().size() == 0) {
                Logger.success("Seznam hráčů je prázný, ukončiji task.");
                CompletableFuture.completedFuture(null);
            }
        }).thenRunAsync(() -> {
            listToTakeTax.get().forEach((consumer) -> {
                int balanceToTake = (int) ((consumer.getThird() * percentage) / 100);
                Logger.info("Hráč " + consumer.getFirst() + "(" + consumer.getSecond() + ") zaplatí: $" + balanceToTake);
                vaultUtils.withdrawPlayer(consumer.getFirst(), balanceToTake);
                NotificationsAPI.Companion.createNotificationByUUID(
                        consumer.getSecond(),
                        NotificationType.ECONOMY,
                        NotificationPriority.NORMAL,
                        "Survival 1.18",
                        "Týdenní stržení poplatků",
                        "Jelikož jsi dosáhl(a) částky k stržení poplatků, bylo ti z účtu automaticky strženo: §f§" + balanceToTake);
            });
        }).thenRunAsync(() -> {
            Logger.success("Automatické placení poplatků dokončeno.");
        });
    }
}
