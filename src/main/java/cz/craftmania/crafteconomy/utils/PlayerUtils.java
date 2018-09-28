package cz.craftmania.crafteconomy.utils;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
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
}
