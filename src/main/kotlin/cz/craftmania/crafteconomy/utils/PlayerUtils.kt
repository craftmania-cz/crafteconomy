package cz.craftmania.crafteconomy.utils

import cz.craftmania.craftlibs.utils.ServerColors
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

class PlayerUtils {

    fun givePermission(player: Player, permission: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${player.name} permission set $permission")
    }

    fun givePermission(name: String, permission: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user $name permission set $permission")
    }

    fun givePermissionForServer(player: Player, permission: String, server: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${player.name} permission set $permission $server")
    }

    fun givePermissionForServer(name: String, permission: String, server: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user $name permission set $permission $server")
    }

    fun removePermission(player: Player, permission: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${player.name} permission unset $permission")
    }

    fun removePermission(name: String, permission: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user $name permission unset $permission")
    }

    fun removePermissionForServer(player: Player, permission: String, server: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${player.name} permission unset $permission $server")
    }

    fun removePermissionForServer(name: String, permission: String, server: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user $name permission unset $permission $server")
    }

    fun isOnline(uuid: UUID): Boolean {
        return Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid)!!.isOnline
    }

    fun isOnline(name: String): Boolean {
        return Bukkit.getPlayer(name) != null && Bukkit.getPlayer(name)!!.isOnline
    }

    fun isOnline(player: Player): Boolean {
        return Bukkit.getPlayer(player.uniqueId) != null && Bukkit.getPlayer(player.uniqueId)!!.isOnline
    }

    fun createDiscriminator(): String {
        val random = Random()
        var id = String.format("%04d", random.nextInt(10000))
        if (id == "0000" || id == "0001" || id == "10000") {
            id = String.format("%04d", random.nextInt(10000))
        }
        return id
    }

    // TODO: MiniMessage
    fun infoNewPlayer(player: Player) {
        player.sendMessage("")
        player.sendMessage(ServerColors.ROLE_BUILDER.get().toString() + "§lVypadá to, že jsi na serveru nový?")
        player.sendMessage(ServerColors.DARK_GRAY.get().toString() + "Koukni na náš návod jak se zorientovat na našem serveru,")
        player.sendMessage(ServerColors.DARK_GRAY.get().toString() + "aby jsi se u nás neztratil.")
        player.sendMessage("§ehttps://wiki.craftmania.cz/tutorial-pro-nove-hrace/") //TODO: Chybí mandarinková barva xD
        player.sendMessage("")
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_FLUTE, 1.0f, 1.0f)
    }
}