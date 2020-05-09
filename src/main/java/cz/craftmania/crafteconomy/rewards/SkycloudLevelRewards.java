package cz.craftmania.crafteconomy.rewards;

import cz.craftmania.crafteconomy.objects.LevelReward;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkycloudLevelRewards {

    private List<LevelReward> list;

    public SkycloudLevelRewards(List<LevelReward> list) {
        this.list = list;
    }

    public void load() {

        // Level 2
        this.list.add(new LevelReward(2).setName("2x Melon Seed")
                .setDescription("§7Získáš základní Melon Seed!")
                .setRewardDescription("§7Dostal jsi Melon Seed, které můžeš nyní zasadit!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.MELON_SEEDS, 2)));

        // Level 3
        this.list.add(new LevelReward(3).setName("2x Cow Spawn Egg")
                .setDescription("§7Získáš 2x spawn egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě krávy! BUUUUUUuuuu")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.COW_SPAWN_EGG, 2)));

        // Level 4
        this.list.add(new LevelReward(4).setName("2x Carrot")
                .setDescription("§7Získáš základní surovinu: Mrkev")
                .setRewardDescription("§7Nyní jsi dostal mrkev, zkus jí zasadit!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.CARROT, 2)));

        // Level 5
        this.list.add(new LevelReward(5).setName("2x Chicken Spawn Egg")
                .setDescription("§7Získáš 2x Chicken Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě slepice!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.CHICKEN_SPAWN_EGG, 2)));

        // Level 6
        this.list.add(new LevelReward(6).setName("Možnost přidat na ostrov 6 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 6 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 6 hráčů!")
                .setPermissions("bskyblock.team.maxsize.6"));

        // Level 7
        this.list.add(new LevelReward(7).setName("2x Beetroots")
                .setDescription("§7Získáš základní surovinu: Beetroot")
                .setRewardDescription("§7Nyní jsi dostal beetroot, zkus to zasadit!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.BEETROOT_SEEDS, 2)));

        // Level 8
        this.list.add(new LevelReward(8).setName("3x Sugar Cane")
                .setDescription("§7Získáš základní surovinu: Sugar Cane")
                .setRewardDescription("§7Nyní jsi dostal Sugar cane, zkus ho zasadit!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.SUGAR_CANE, 3)));

        // Level 10
        this.list.add(new LevelReward(10).setName("Rozšíření ostrova: 75x75")
                .setDescription("§7Region ostrova se zvětší na 75x75!")
                .setRewardDescription("§7Nyní máš velikost ostrova 75x75")
                .setPermissions("bskyblock.island.range.75"));

        // Level 11
        this.list.add(new LevelReward(11).setName("3x Nether Warth")
                .setDescription("§7Získáš základní surovinu: Nether Warth")
                .setRewardDescription("§7Nyní jsi dostal Nether Warth, zkus ho zasadit!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.NETHER_WART, 3)));

        // Level 12
        this.list.add(new LevelReward(12).setName("2x Bambus")
                .setDescription("§7Získáš základní surovinu: Bambus")
                .setRewardDescription("§7Nyní jsi dostal Bambus, zasaď hooo!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.BAMBOO, 2)));

        // Level 13
        this.list.add(new LevelReward(13).setName("Možnost přidat na ostrov 8 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 8 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 8 hráčů!")
                .setPermissions("bskyblock.team.maxsize.8"));

        // Level 15
        this.list.add(new LevelReward(15).setName("2x Bee Spawn Egg")
                .setDescription("§7Získáš 2x Bee Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě včelky!", "§7Medové kráslovství začínná!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.BEE_SPAWN_EGG, 2)));

        // Level 16
        this.list.add(new LevelReward(16).setName("Rozšíření ostrova: 150x150")
                .setDescription("§7Region ostrova se zvětší na 150x150!")
                .setRewardDescription("§7Nyní máš velikost ostrova 150x150")
                .setPermissions("bskyblock.island.range.150"));

        // Level 17
        this.list.add(new LevelReward(17).setName("Přístup k /is biomes")
                .setDescription("§7Získáš přístup k změnám biomů", "§7na stvém ostrově!")
                .setRewardDescription("§7Nyní si můžeš změnit biom pomocí /is boime")
                .setPermissions("bskyblock.biomes", "bskyblock.biomes.info", "bskyblock.biomes.set"));

        // Level 18
        this.list.add(new LevelReward(18).setName("Možnost přidat na ostrov 12 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 12 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 12 hráčů!")
                .setPermissions("bskyblock.team.maxsize.12"));

        // Level 20
        this.list.add(new LevelReward(20).setName("2x Turtle Spawn Egg")
                .setDescription("§7Získáš 2x Turtle Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě želvičky!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.TURTLE_SPAWN_EGG, 2)));

        // Level 25
        this.list.add(new LevelReward(25).setName("Pumpin Pie Seed")
                .setDescription("§7Získáš 1x Pumpkin Pie Seed")
                .setRewardDescription("§7Nyní můžeš zasadit dýně!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.PUMPKIN_SEEDS, 1)));

        // Level 30
        this.list.add(new LevelReward(30).setName("Rozšíření ostrova: 200x200")
                .setDescription("§7Region ostrova se zvětší na 200x200!")
                .setRewardDescription("§7Nyní máš velikost ostrova 200x200")
                .setPermissions("bskyblock.island.range.200"));

    }
}
