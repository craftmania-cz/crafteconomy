package cz.craftmania.crafteconomy.utils;

import cz.craftmania.craftlibs.utils.ServerColors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerUtils {

    public boolean isOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline();
    }

    public boolean isOnline(String name) {
        return Bukkit.getPlayer(name) != null && Bukkit.getPlayer(name).isOnline();
    }

    public boolean isOnline(Player player) {
        return Bukkit.getPlayer(player.getUniqueId()) != null && Bukkit.getPlayer(player.getUniqueId()).isOnline();
    }

    public UUID getUUID(String name) {
        return Bukkit.getPlayer(name) != null ? Bukkit.getPlayer(name).getUniqueId() : null;
    }

    public String getName(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null ? Bukkit.getPlayer(uuid).getName() : null;
    }

    public List<String> getPermissions(UUID uuid) {
        List<String> permissions = new ArrayList<>();
        if (isOnline(uuid)) {
            Bukkit.getPlayer(uuid).getEffectivePermissions().forEach(perm -> permissions.add(perm.getPermission()));
        }
        return permissions;
    }

    public static String createDiscriminator() {
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        if (id.equals("0000") || id.equals("0001") || id.equals("10000")) {
            id = String.format("%04d", random.nextInt(10000));
        }
        return id;
    }

    public void infoNewPlayer(final Player player) {
        player.sendMessage("");
        player.sendMessage(ServerColors.ROLE_BUILDER.get() + "§lVypadá to, že jsi na serveru nový?");
        player.sendMessage(ServerColors.DARK_GRAY.get() + "Koukni na náš návod jak se zorientovat na našem serveru,");
        player.sendMessage(ServerColors.DARK_GRAY.get() + "aby jsi se u nás neztratil.");
        player.sendMessage("§ehttps://wiki.craftmania.cz/tutorial-pro-nove-hrace/"); //TODO: Chybí mandarinková barva xD
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 1.0f, 1.0f);
    }
}
