package cz.craftmania.crafteconomy.listener;

import cz.craftmania.craftactions.economy.AsyncPlayerLevelUpEvent;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.managers.RewardManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayerLevelUpListener implements Listener {

    static Random r = new Random();
    final BasicManager basicManager = new BasicManager();

    @EventHandler
    public void onLevelGain(AsyncPlayerLevelUpEvent event) {
        final CraftPlayer craftPlayer = basicManager.getCraftPlayer(event.getPlayer());
        final Player player = event.getPlayer();

        BasicManager basicManager = new BasicManager();
        long currentLevel = craftPlayer.getLevelByType(basicManager.getLevelByServer());

        Logger.info("Hrac: " + player.getName() + ", dostal level up na: " + currentLevel);

        RewardManager.getRewards().forEach(level -> {
            if (level.getLevel() == currentLevel) {
                basicManager.givePlayerManualLevelReward(level, player, true);
            }
        });

        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 0F);
        player.sendMessage("§9\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        player.sendMessage("");
        player.sendMessage("§6§l* LEVEL UP! *");
        player.sendMessage("§eNyni jsi level: §f" + currentLevel); //TODO: Odměny boolean
        player.sendMessage("");
        player.sendMessage("§9\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");
        if (Main.getInstance().getMySQL().getSettings(player, "levelup_firework_enabled") == 1) {
            this.randomFireworks(player.getLocation(), Main.getInstance().getMySQL().getSettingsString(player, "levelup_firework_type"));
        }
    }

    private void randomFireworks(final Location location, String levelupFireworkType) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Type fireworkType = null;
                switch(levelupFireworkType) {
                    case "RANDOM":
                        fireworkType = getRandomFireworkType();
                        break;
                    case "BALL":
                        fireworkType = Type.BALL;
                        break;
                    case "BALL_LARGE":
                        fireworkType = Type.BALL_LARGE;
                        break;
                    case "BURST":
                        fireworkType = Type.BURST;
                        break;
                    case "CREEPER":
                        fireworkType = Type.CREEPER;
                        break;
                    case "STAR":
                        fireworkType = Type.STAR;
                        break;
                    default:
                        fireworkType = getRandomFireworkType();
                        break;
                }
                Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                FireworkMeta fireworkMeta = fw.getFireworkMeta();
                fireworkMeta.setPower(0);
                FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(false).trail(false).with(fireworkType).withColor(Color.fromRGB(r.nextInt(255), r.nextInt(255), r.nextInt(255))).withFade(Color.fromRGB(r.nextInt(255), r.nextInt(255), r.nextInt(255))).build();
                fireworkMeta.addEffect(fireworkEffect);
                fw.setFireworkMeta(fireworkMeta);
                fw.setMetadata("nodamage", new FixedMetadataValue(Main.getInstance(), true));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        fw.detonate();
                    }
                }.runTaskLater(Main.getInstance(), 2L);
            }
        }.runTaskLater(Main.getInstance(), 2L);
    }

    private static FireworkEffect.Type getRandomFireworkType() {
        ArrayList<FireworkEffect.Type> types = new ArrayList<>();
        types.add(Type.BALL);
        types.add(Type.BALL_LARGE);
        types.add(Type.BURST);
        types.add(Type.CREEPER);
        types.add(Type.STAR);
        Collections.shuffle(types);
        return types.get(0);
    }

}
