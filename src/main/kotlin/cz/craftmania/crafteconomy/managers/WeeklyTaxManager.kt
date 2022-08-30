package cz.craftmania.crafteconomy.managers

import cz.craftmania.craftactions.profile.NotificationPriority
import cz.craftmania.craftactions.profile.NotificationType
import cz.craftmania.crafteconomy.Main
import cz.craftmania.crafteconomy.utils.Logger
import cz.craftmania.crafteconomy.utils.Triple
import cz.craftmania.crafteconomy.utils.VaultUtils
import cz.craftmania.craftnotifications.api.NotificationsAPI
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Consumer

class WeeklyTaxManager {

    private val vaultUtils = VaultUtils()

    fun doPlayerPayTax(minBalance: Long, percentage: Int) {

        Logger.info("Start automatického placení poplatků hráčů.")
        Logger.info("Minimální hodnota v bance: $$minBalance")
        Logger.info("Procentuální hodnota placení: $percentage%")

        // Nick, UUID, Balance
        val listToTakeTax = AtomicReference<List<Triple<String, UUID, Double>>>()
        CompletableFuture.runAsync {
            listToTakeTax.set(Main.getInstance().mySQL.fetchAllEconomyTaxPaymentPlayers(minBalance))
            Logger.info("Nalezeno celkem (" + listToTakeTax.get().size + ") hráčů, kteří budou platit poplatek.")
            if (listToTakeTax.get().isEmpty()) {
                Logger.success("Seznam hráčů je prázný, ukončiji task.")
                CompletableFuture.completedFuture(null)
            }
        }.thenRunAsync {
            listToTakeTax.get()
                .forEach(Consumer { consumer: Triple<String, UUID, Double> ->
                    val balanceToTake = (consumer.third * percentage / 100).toInt()
                    Logger.info("Hráč " + consumer.first + "(" + consumer.second + ") zaplatí: $" + balanceToTake)
                    vaultUtils.withdrawPlayer(consumer.first, balanceToTake.toDouble())
                    NotificationsAPI.createNotificationByName(
                        consumer.first,
                        NotificationType.ECONOMY,
                        NotificationPriority.NORMAL,
                        Main.getFixedServerType(),
                        "Týdenní stržení poplatků",
                        "Jelikož jsi dosáhl(a) částky k stržení poplatků, bylo ti z účtu automaticky strženo: §f§$balanceToTake"
                    )
                })
        }.thenRunAsync {
            Logger.success("Automatické placení poplatků dokončeno.")
        }
    }
}