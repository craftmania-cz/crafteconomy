package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.economy.CraftEconomyVaultCleanUpEvent;
import cz.craftmania.crafteconomy.utils.Constants;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.crafteconomy.utils.Triple;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Tento manager spravuje data v databázi a v určitý
 * nastavený čas promaže všechny data.
 */
public class CleanUpManager {

    public void cleanUpDatabase(int days) {
        // Když je čas menší jak tento, smazat.
        long cleanTime = System.currentTimeMillis() - (Constants.DAY_IN_MS * days);

        Logger.info("Start mazání hráčů z vault databáze.");
        Logger.info("Aktuální čas je nastaven na: " + days + " dní.");
        Logger.info("Vypočítaný čas zlomu: <= " + formatDate(cleanTime));

        // List všech -> Nick / Balance / Last Updated
        AtomicReference<List<Triple<String, Double, Long>>> listToRemove = new AtomicReference<>();
        CompletableFuture.runAsync(() -> {
            listToRemove.set(Main.getInstance().getMySQL().fetchAllEconomyRowsToRemove(cleanTime));
            Logger.info("Nalezeno celkem (" + listToRemove.get().size() + ") záznamů k smazání.");
            if (listToRemove.get().size() == 0) {
                Logger.success("Nebylo nalezeno nic k mazání, proces ukončen.");
                CompletableFuture.completedFuture(null);
            }
        }).thenRunAsync(() -> {
            listToRemove.get().forEach((consumer) -> {
                Logger.info("Mazání hráče: " + consumer.getFirst() + " - " + consumer.getSecond() + "$ (last update: " + formatDate(consumer.getThird()) + ")");
                Main.getInstance().getMySQL().purgeFromVaultDatabase(consumer.getFirst(), consumer.getThird());
                final CraftEconomyVaultCleanUpEvent event = new CraftEconomyVaultCleanUpEvent(consumer.first(), consumer.getSecond(), formatDate(consumer.getThird()));
                Bukkit.getPluginManager().callEvent(event);
            });
        }).thenRunAsync(() -> {
            Logger.success("Automatické mazání vault databáze dokončeno.");
        });

    }

    private String formatDate(long date) {
        Date newDate = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(newDate);
    }
}
