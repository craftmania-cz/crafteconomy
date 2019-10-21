package cz.craftmania.crafteconomy.rewards;

import cz.craftmania.crafteconomy.objects.LevelReward;

import java.util.List;

public class VanillaLevelRewards {

    private List<LevelReward> list;

    public VanillaLevelRewards(List<LevelReward> list) {
        this.list = list;
    }

    public void load() {

        // Level 2
        this.list.add(new LevelReward(2).setName("Rozšíření až 2 chunků")
                .setDescription("§7Přístup k zabrání až 2 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 2 chunků!")
                .setPermissions("lands.chunks.2"));

        // Level 4
        this.list.add(new LevelReward(4).setName("Rozšíření až 4 chunků")
                .setDescription("§7Přístup k zabrání až 4 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 4 chunků!")
                .setPermissions("lands.chunks.4"));

        // Level 6
        this.list.add(new LevelReward(6).setName("Rozšíření až 6 chunků")
                .setDescription("§7Přístup k zabrání až 6 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 6 chunků!")
                .setPermissions("lands.chunks.6"));

        // Level 8
        this.list.add(new LevelReward(8).setName("Rozšíření až 7 chunků")
                .setDescription("§7Přístup k zabrání až 7 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 7 chunků!")
                .setPermissions("lands.chunks.7"));

        // Level 9
        this.list.add(new LevelReward(9).setName("Maximální počet členů: 5")
                .setDescription("§7Budeš moct mít v území až 5 členů!")
                .setRewardDescription("§7Nyní můžeš mít v území 5 členů!")
                .setPermissions("lands.members.5"));

        // Level 10
        this.list.add(new LevelReward(10).setName("Rožšíření až 10 chunků + člen 2 území")
                .setDescription("§8- §7Svůj region můžeš rozšířit až na 10 chunků.", "§8- §7Můžeš být členem ve dvou území (svoje a cizí)")
                .setRewardDescription("§7Nyní můžeš mít až 10 chunků + být člen ve 2 území!")
                .setPermissions("lands.chunks.10", "lands.lands.2"));

        // Level 12
        this.list.add(new LevelReward(12).setName("Rozšíření až 12 chunků")
                .setDescription("§7Přístup k zabrání až 12 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 12 chunků!")
                .setPermissions("lands.chunks.12"));

        // Level 14
        this.list.add(new LevelReward(14).setName("Rozšíření až 14 chunků")
                .setDescription("§7Přístup k zabrání až 14 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 14 chunků!")
                .setPermissions("lands.chunks.14"));

        // Level 15
        this.list.add(new LevelReward(15).setName("Maximální počet členů: 6")
                .setDescription("§7Budeš moct mít v území až 6 členů!")
                .setRewardDescription("§7Nyní můžeš mít v území 6 členů!")
                .setPermissions("lands.members.6"));

        // Level 17
        this.list.add(new LevelReward(17).setName("Rozšíření až 17 chunků")
                .setDescription("§7Přístup k zabrání až 17 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 17 chunků!")
                .setPermissions("lands.chunks.17"));

        // Level 19
        this.list.add(new LevelReward(19).setName("Rozšíření až 19 chunků")
                .setDescription("§7Přístup k zabrání až 19 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 19 chunků!")
                .setPermissions("lands.chunks.19"));

        // Level 20
        this.list.add(new LevelReward(20).setName("Maximální počet členů: 8")
                .setDescription("§7Budeš moct mít v území až 8 členů!")
                .setRewardDescription("§7Nyní můžeš mít v území 8 členů!")
                .setPermissions("lands.members.8"));

        // Level 21
        this.list.add(new LevelReward(21).setName("Majitel dvou území")
                .setDescription("§7Budeš moct vytvořit dvě území!")
                .setRewardDescription("§7Nyní můžeš vytvořit dvě území!")
                .setPermissions("lands.ownlands.2"));

        // Level 22
        this.list.add(new LevelReward(22).setName("Člen ve 3 území")
                .setDescription("§7Můžeš být členem ve 3 území")
                .setRewardDescription("§7Nyní můžeš být členem ve 3 území!")
                .setPermissions("lands.lands.3"));

        // Level 23
        this.list.add(new LevelReward(23).setName("Rozšíření až 23 chunků")
                .setDescription("§7Přístup k zabrání až 23 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 23 chunků!")
                .setPermissions("lands.chunks.23"));

        // Level 25
        this.list.add(new LevelReward(25).setName("Rozšíření až 26 chunků")
                .setDescription("§7Přístup k zabrání až 26 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 26 chunků!")
                .setPermissions("lands.chunks.26"));

        // Level 27
        this.list.add(new LevelReward(27).setName("Rozšíření až 30 chunků")
                .setDescription("§7Přístup k zabrání až 30 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 30 chunků!")
                .setPermissions("lands.chunks.30"));

        // Level 29
        this.list.add(new LevelReward(29).setName("Maximální počet členů: 10")
                .setDescription("§7Budeš moct mít v území až 10 členů!")
                .setRewardDescription("§7Nyní můžeš mít v území 10 členů!")
                .setPermissions("lands.members.10"));

        // Level 30
        this.list.add(new LevelReward(30).setName("Rozšíření až 35 chunků")
                .setDescription("§7Přístup k zabrání až 35 chunků!")
                .setRewardDescription("§7Nyní můžeš zabrat 35 chunků!")
                .setPermissions("lands.chunks.35"));
    }
}
