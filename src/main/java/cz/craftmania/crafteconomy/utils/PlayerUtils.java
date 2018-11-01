package cz.craftmania.crafteconomy.utils;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerUtils {

    public Boolean isOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline();
    }

    public Boolean isOnline(String name) {
        return Bukkit.getPlayer(name) != null && Bukkit.getPlayer(name).isOnline();
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
}
