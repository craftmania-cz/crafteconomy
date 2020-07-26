package cz.craftmania.crafteconomy.rewards;

import cz.craftmania.crafteconomy.objects.LevelReward;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkyblockLevelRewards {

    private List<LevelReward> list;

    public SkyblockLevelRewards(List<LevelReward> list) {
        this.list = list;
    }

    public void load() {

        // Level 2
        this.list.add(new LevelReward(2).setName("2x Pig & Cow Spawn Egg")
                .setDescription("§7Získáš 2x Pig & Cow Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě prasátka a kravičky!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.COW_SPAWN_EGG, 2), new ItemStack(Material.PIG_SPAWN_EGG, 2)));

        // Level 3
        this.list.add(new LevelReward(3).setName("2x Sheep Spawn Egg")
                .setDescription("§7Získáš 2x Sheep Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě ovečky!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.SHEEP_SPAWN_EGG, 2)));

        // Level 4
        this.list.add(new LevelReward(4).setName("Možnost přidat na ostrov 5 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 5 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 5 hráčů!")
                .setPermissions("bskyblock.team.maxsize.5"));

        // Level 5
        this.list.add(new LevelReward(5).setName("2x Chicken Spawn Egg")
                .setDescription("§7Získáš 2x Chicken Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě kuřátka!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.CHICKEN_SPAWN_EGG, 2)));

        // Level 7
        this.list.add(new LevelReward(7).setName("Možnost přidat na ostrov 6 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 6 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 6 hráčů!")
                .setPermissions("bskyblock.team.maxsize.6"));

        // Level 8
        this.list.add(new LevelReward(8).setName("2x Turtle Spawn Egg")
                .setDescription("§7Získáš 2x Turtle Spawn Egg")
                .setRewardDescription("§7Nyní si můžeš spawnout dvě želvičky!")
                .setRequiareSlotInInventory()
                .setItems(new ItemStack(Material.TURTLE_SPAWN_EGG, 2)));

        // Level 9
        this.list.add(new LevelReward(9).setName("Rozšíření ostrova: 75x75")
                .setDescription("§7Region ostrova se zvětší na 75x75!")
                .setRewardDescription("§7Nyní máš velikost ostrova 75x75")
                .setPermissions("bskyblock.island.range.75"));

        // Level 10
        this.list.add(new LevelReward(10).setName("Možnost nákupu: Pig Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Pig Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Pig Spawner")
                .setPermissions("shopguiplus.item.spawners.1", "shopguiplus.shops.spawners")); // ID: 1

        // Level 11
        this.list.add(new LevelReward(11).setName("Možnost přidat na ostrov 8 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 8 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 8 hráčů!")
                .setPermissions("bskyblock.team.maxsize.8"));

        // Level 13
        this.list.add(new LevelReward(13).setName("Možnost nákupu: Cow Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Cow Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Cow Spawner")
                .setPermissions("shopguiplus.item.spawners.2")); // ID: 2

        // Level 15
        this.list.add(new LevelReward(15).setName("Možnost nákupu: Rabbit Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Rabbit Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Rabbit Spawner")
                .setPermissions("shopguiplus.item.spawners.3")); // ID: 3

        // Level 17
        this.list.add(new LevelReward(17).setName("Rozšíření ostrova: 100x100")
                .setDescription("§7Region ostrova se zvětší na 100x100!")
                .setRewardDescription("§7Nyní máš velikost ostrova 100x100")
                .setPermissions("bskyblock.island.range.100"));

        // Level 18
        this.list.add(new LevelReward(18).setName("Možnost přidat na ostrov 10 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 10 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 10 hráčů!")
                .setPermissions("bskyblock.team.maxsize.10"));

        // Level 20
        this.list.add(new LevelReward(20).setName("Přístup do Special Shop")
                .setDescription("§7Přístup do speciální sekce", "§7v /shop k zakoupení těch nejvíc OP věcí!")
                .setRewardDescription("§7Nyní máš přístup do Special Shopu v §b/shop")
                .setPermissions("shopguiplus.shops.special"));

        // Level 21
        this.list.add(new LevelReward(21).setName("Možnost nákupu: Zombie Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Zombie Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Zombie Spawner")
                .setPermissions("shopguiplus.item.spawners.4")); // ID: 4

        // Level 22
        this.list.add(new LevelReward(22).setName("Možnost nákupu: Creeper Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Creeper Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Creeper Spawner")
                .setPermissions("shopguiplus.item.spawners.5"));

        // Level 23
        this.list.add(new LevelReward(23).setName("Možnost nákupu: Chicken Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Chicken Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Chicken Spawner")
                .setPermissions("shopguiplus.item.spawners.6"));

        // Level 24
        this.list.add(new LevelReward(24).setName("Rozšíření ostrova: 150x150")
                .setDescription("§7Region ostrova se zvětší na 150x150!")
                .setRewardDescription("§7Nyní máš velikost ostrova 150x150")
                .setPermissions("bskyblock.island.range.150"));

        // Level 26
        this.list.add(new LevelReward(26).setName("Možnost nákupu: Sheep Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Sheep Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Sheep Spawner")
                .setPermissions("shopguiplus.item.spawners.7"));

        // Level 28
        this.list.add(new LevelReward(28).setName("Možnost přidat na ostrov 12 hráčů")
                .setDescription("§7Budeš si moct přidat na ostrov", "§7až 12 hráčů!")
                .setRewardDescription("§7Nyní můžeš pozvat na ostrov až 12 hráčů!")
                .setPermissions("bskyblock.team.maxsize.12"));

        // Level 31
        this.list.add(new LevelReward(31).setName("Možnost nákupu: Spider Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Spider Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Spider Spawner")
                .setPermissions("shopguiplus.item.spawners.8"));

        // Level 33
        this.list.add(new LevelReward(33).setName("Rozšíření ostrova: 200x200")
                .setDescription("§7Region ostrova se zvětší na 200x200!")
                .setRewardDescription("§7Nyní máš velikost ostrova 200x200")
                .setPermissions("bskyblock.island.range.200"));

        // Level 35
        this.list.add(new LevelReward(35).setName("Možnost nákupu: Skeleton Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Skeleton Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Skeleton Spawner")
                .setPermissions("shopguiplus.item.spawners.9"));

        // Level 38
        this.list.add(new LevelReward(38).setName("Rozšíření ostrova: 250x250")
                .setDescription("§7Region ostrova se zvětší na 250x250!")
                .setRewardDescription("§7Nyní máš velikost ostrova 250x250")
                .setPermissions("bskyblock.island.range.250"));

        // Level 40
        this.list.add(new LevelReward(40).setName("Možnost nákupu: Iron Golem Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Iron Golem Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Iron Golem Spawner")
                .setPermissions("shopguiplus.item.spawners.10"));

        // Level 45
        this.list.add(new LevelReward(45).setName("Možnost nákupu: Witch Spawner")
                .setDescription("§7Odemkne se ti možnost si zakoupit", "§7Witch Spawner v §b/shop")
                .setRewardDescription("§7Nyní si můžeš zakoupit v /shop - Witch Spawner")
                .setPermissions("shopguiplus.item.spawners.11"));

    }
}
