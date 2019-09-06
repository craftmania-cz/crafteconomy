package cz.craftmania.crafteconomy.rewards;

import cz.craftmania.crafteconomy.objects.LevelReward;

import java.util.List;

public class CreativeLevelReward {

    private List<LevelReward> list;

    public CreativeLevelReward(List<LevelReward> list) {
        this.list = list;
    }

    public void load() {

        // LEVEL 3
        this.list.add(new LevelReward(3).setName("Nastavení hudby na pozemku")
                .setDescription("§7Přístup k příkazu §a/p music")
                .setRewardDescription("§7Pomocí příkazu §b/p music §7si nyní můžeš nastavit hudbu!")
                .setPermissions("plots.music"));

        // LEVEL 5
        this.list.add(new LevelReward(6).setName("Stažení pozemku")
                .setDescription("§7Přístup k příkzu §a/p download")
                .setRewardDescription("§7Pomocí příkazu §b/p download §7nyní můžeš stáhnout pozemek!")
                .setPermissions("plots.download"));

        /*// LEVEL 10
        this.list.add(new LevelReward(10).setName("Disguise přeměny (Zvířata)")
                .setDescription("§7Přístup k přeměnám na zvířata!")
                .setRewardDescription("§7Pomocí příkazu §b/disguise §7se nyní můžeš přeměnit na zvíře!")
                .setPermissions("")); //TODO: Permissions

        // LEVEL 20
        this.list.add(new LevelReward(20).setName("Disguise přeměny (Zvířata)")
                .setDescription("§7Přístup k přeměnám na monstra!")
                .setRewardDescription("§7Pomocí příkazu §b/disguise §7se nyní můžeš přeměnit na monstra!")
                .setPermissions("")); //TODO: Permissions*/
    }

}