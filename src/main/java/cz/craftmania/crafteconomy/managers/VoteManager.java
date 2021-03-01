package cz.craftmania.crafteconomy.managers;

import cz.craftmania.craftcore.spigot.xseries.messages.Titles;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.CraftCoinsAPI;
import cz.craftmania.crafteconomy.api.VoteTokensAPI;
import cz.craftmania.crafteconomy.events.PlayerVoteEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.VotePassReward;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoteManager {

    private BasicManager manager = new BasicManager();
    public static List<VotePassReward> votePassRewards = new ArrayList<>();

    /**
     * Přidělí hráči hlas a oznámí do chatu o info
     * @param nick Hráč, který hlasoval
     * @param uuid UUID hráče
     * @param coins Počet CraftCoins, který hráč dostane v základu
     */
    public void playerVote(final String nick, final String uuid, final String coins) {
        Player player = Bukkit.getServer().getPlayer(nick);
        assert player != null; // Hráč nemůže být null jelikož Bungeecord kontroluje zda je na serveru
        CraftPlayer craftPlayer = this.manager.getCraftPlayer(player);

        VoteTokensAPI.giveVoteTokens(player, 1);
        CraftCoinsAPI.giveCoins(player, Integer.parseInt(coins));
        Main.getInstance().getMySQL().addPlayerVote(nick);
        craftPlayer.addVote();
        craftPlayer.setLastVote(System.currentTimeMillis());

        if (Main.isCraftCoreEnabled) {
            Titles.sendTitle(player, "§a§lDěkujeme!", "§fDostal/a jsi 1x VoteToken.");
        }

        Bukkit.getPluginManager().callEvent(new PlayerVoteEvent(player));
        player.sendMessage(" ");
        player.sendMessage("§bVyber si odměnu jakou chceš!");
        player.sendMessage("§eStačí zajít do §f/cshop §ea zvolit si odměnu za VoteTokeny.");
        player.sendMessage("");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§b" + player.getName() + " §ehlasoval a získal §aodměnu! §c/vote");
        }
    }

    /**
     * Vrací aktuální počet hlasů v VotePassu
     *
     * @param player {@link Player}
     * @return {@link Long}
     */
    public long getVotePassVotes(@NotNull Player player) {
        return this.manager.getCraftPlayer(player).getVotePass();
    }

    public static List<VotePassReward> getVotePassRewards() {
        return votePassRewards;
    }

    public static void loadVotePassRewards() {

        /*
         * Free rewards - strana 1
         */

        // Reward: 1 - 300 CraftCoins
        VotePassReward reward_1 = new VotePassReward(1);
        reward_1.setName("Tier: 1");
        reward_1.setRequiredVotes(10);
        reward_1.setDescription("§8- §f300 CraftCoins");
        reward_1.setCraftCoins(300);
        reward_1.setMaterial(Material.GOLD_NUGGET);
        votePassRewards.add(reward_1);

        // Reward: 2 - 1000 Server XP, Beta přístup: Vanilla
        VotePassReward reward_2 = new VotePassReward(2);
        reward_2.setName("Tier: 2");
        reward_2.setRequiredVotes(20);
        reward_2.setDescription("§8- §f1,000 Server XP", "§8- §fBeta přístup: Vanilla: Anarchy");
        reward_2.setServerExperience(1000);
        reward_2.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.beta_access.vanilla_anarchy");
        reward_2.setMaterial(Material.ENDER_PEARL);
        votePassRewards.add(reward_2);

        // Reward: 3 - 2x Basic Key
        VotePassReward reward_3 = new VotePassReward(3);
        reward_3.setName("Tier: 3");
        reward_3.setRequiredVotes(30);
        reward_3.setDescription("§8- §f2x Basic Key");
        reward_3.setRequiredSlotInInventory();
        reward_3.setServerCommand(ServerType.SKYBLOCK, "crate give physical Basic 2 %player%");
        reward_3.setServerCommand(ServerType.SURVIVAL, "crate give physical Basic 2 %player%");
        reward_3.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_3);

        // Reward: 4 - Skyblock: Coal Minion / Creative WE 12h
        VotePassReward reward_4 = new VotePassReward(4);
        reward_4.setName("Tier: 4");
        reward_4.setRequiredVotes(40);
        reward_4.setDescription("§8- §fSkyblock: Coal Minion §7nebo §fCreative WorldEdit: 12h");
        reward_4.setRequiredSlotInInventory();
        reward_4.setServerCommand(ServerType.SKYBLOCK, "msetup give minion coal %player% 1");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.(center|set|walls|move|overlay) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.(pos|chunk) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 12h creative");
        reward_4.setMaterial(Material.COAL_ORE);
        votePassRewards.add(reward_4);

        // Reward: 5 - SellStick: 50 použití
        VotePassReward reward_5 = new VotePassReward(5);
        reward_5.setName("Tier: 5");
        reward_5.setRequiredVotes(50);
        reward_5.setDescription("§8- §fSellStick: 50 použití");
        reward_5.setRequiredSlotInInventory();
        reward_5.setServerCommand(ServerType.SKYBLOCK, "sellstick give %player% 1 50");
        reward_5.setServerCommand(ServerType.SURVIVAL, "sellstick give %player% 1 50");
        reward_5.setMaterial(Material.STICK);
        votePassRewards.add(reward_5);

        // Reward: 6 - 500 CraftCoins, McMMO 200% - 24h
        VotePassReward reward_6 = new VotePassReward(6);
        reward_6.setName("Tier: 6");
        reward_6.setRequiredVotes(60);
        reward_6.setDescription("§8- §f500 CraftCoins", "§8- §fMcMMO 200% Boost - 24h");
        reward_6.setCraftCoins(500);
        reward_6.setServerCommand(ServerType.SURVIVAL, "lp user %player% permission settemp mcmmo.perks.xp.double.* 24h survival");
        reward_6.setServerCommand(ServerType.SKYBLOCK, "lp user %player% permission settemp mcmmo.perks.xp.double.* 24h skyblock");
        reward_6.setMaterial(Material.SUGAR);
        votePassRewards.add(reward_6);

        // Reward: 7 - Prison Boost → 200% → 48h, 1,000 Server XP
        VotePassReward reward_7 = new VotePassReward(7);
        reward_7.setName("Tier: 7");
        reward_7.setRequiredVotes(70);
        reward_7.setDescription("§8- §fPrison Boost: 200% na 48h", "§8- §f1,000 Server XP");
        reward_7.setServerExperience(1000);
        reward_7.setServerCommand(ServerType.PRISON, "multiplier create %player% personal 172800000 200");
        reward_7.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_7);

        // PAGE 2

        // Reward: 8 - ArmorStandEditor → 7d
        VotePassReward reward_8 = new VotePassReward(15);
        reward_8.setName("Tier: 8");
        reward_8.setRequiredVotes(80);
        reward_8.setDescription("§8- §fArmorStandEditor na 7 dní");
        reward_8.setServerCommand(ServerType.SURVIVAL, "lp user %player% permission settemp asedit.* 7d survival");
        reward_8.setServerCommand(ServerType.SKYBLOCK, "lp user %player% permission settemp asedit.* 7d skyblock");
        reward_8.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp asedit.* 7d creative");
        reward_8.setMaterial(Material.ARMOR_STAND);
        votePassRewards.add(reward_8);

        // Reward: 9 - 5x Basic Key
        VotePassReward reward_9 = new VotePassReward(16);
        reward_9.setName("Tier: 9");
        reward_9.setRequiredVotes(90);
        reward_9.setDescription("§8- §f5x Basic Key");
        reward_9.setRequiredSlotInInventory();
        reward_9.setServerCommand(ServerType.SURVIVAL, "crate give physical Basic 5 %player%");
        reward_9.setServerCommand(ServerType.SKYBLOCK, "crate give physical Basic 5 %player%");
        reward_9.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_9);

        // Reward: 10 - 1 CraftToken, 2000 Server XP
        VotePassReward reward_10 = new VotePassReward(17);
        reward_10.setName("Tier: 10");
        reward_10.setRequiredVotes(100);
        reward_10.setDescription("§8- §f1 CraftToken", "§8- §f2000 Server XP");
        reward_10.setServerExperience(2000);
        reward_10.setCraftTokens(1);
        reward_10.setMaterial(Material.EMERALD);
        votePassRewards.add(reward_10);

        // Reward: 11 - Skyblock: Fly na 7d
        VotePassReward reward_11 = new VotePassReward(18);
        reward_11.setName("Tier: 11");
        reward_11.setRequiredVotes(115);
        reward_11.setDescription("§8- §fSkyblock: Fly na 7 dní");
        reward_11.setServerCommand(ServerType.SKYBLOCK, "lp user %player% permission settemp bskyblock.island.fly 7d skyblock");
        reward_11.setMaterial(Material.CHAINMAIL_BOOTS);
        votePassRewards.add(reward_11);

        // Reward: 12 - SellStick: 150 použití, 500 Server XP
        VotePassReward reward_12 = new VotePassReward(19);
        reward_12.setName("Tier: 12");
        reward_12.setRequiredVotes(130);
        reward_12.setDescription("§8- §fSellStick: 150 použití", "§8- §f500 Server XP");
        reward_12.setServerExperience(500);
        reward_12.setRequiredSlotInInventory();
        reward_12.setServerCommand(ServerType.SURVIVAL, "sellstick give %player% 1 150");
        reward_12.setServerCommand(ServerType.SKYBLOCK, "sellstick give %player% 1 150");
        reward_12.setMaterial(Material.STICK);
        votePassRewards.add(reward_12);

        // Reward: 13 - McMMO: 300% - 48h
        VotePassReward reward_13 = new VotePassReward(20);
        reward_13.setName("Tier: 13");
        reward_13.setRequiredVotes(145);
        reward_13.setDescription("§8- §fMcMMO: 300% - 48h");
        reward_13.setServerCommand(ServerType.SURVIVAL, "lp user %player% permission settemp mcmmo.perks.xp.triple.* 48h survival");
        reward_13.setServerCommand(ServerType.SKYBLOCK, "lp user %player% permission settemp mcmmo.perks.xp.triple.* 48h skyblock");
        reward_13.setMaterial(Material.SUGAR);
        votePassRewards.add(reward_13);

        // Reward: 14 - SB: Collector Minion / Crea WE 5d
        VotePassReward reward_14 = new VotePassReward(21);
        reward_14.setName("Tier: 14");
        reward_14.setRequiredVotes(160);
        reward_14.setRequiredSlotInInventory();
        reward_14.setDescription("§8- §fSkyblock: Collector Minion §fnebo §fCreative: WorldEdit 5 dní");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.(center|set|walls|move|overlay) 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.(pos|chunk) 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 5d creative");
        reward_14.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 5d creative");
        reward_14.setServerCommand(ServerType.SKYBLOCK, "msetup give minion collector %player% 1");
        reward_14.setMaterial(Material.HOPPER);
        votePassRewards.add(reward_14);

        // PAGE 3

        // Reward: 15 - 1,200 CraftCoins
        VotePassReward reward_15 = new VotePassReward(29);
        reward_15.setName("Tier: 15");
        reward_15.setDescription("§8- §f1200 CraftCoins");
        reward_15.setRequiredVotes(175);
        reward_15.setCraftTokens(1200);
        reward_15.setMaterial(Material.GOLD_INGOT);
        votePassRewards.add(reward_15);

        // Reward: 16 - 3,500 Server XP
        VotePassReward reward_16 = new VotePassReward(30);
        reward_16.setName("Tier: 16");
        reward_16.setDescription("§8- §f3,500 Server XP");
        reward_16.setRequiredVotes(190);
        reward_16.setServerExperience(3500);
        reward_16.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_16);

        // 21
        VotePassReward reward_17 = new VotePassReward(31);
        reward_17.setEmpty();
        votePassRewards.add(reward_17);

        VotePassReward reward_18 = new VotePassReward(32);
        reward_18.setEmpty();
        votePassRewards.add(reward_18);

        VotePassReward reward_19 = new VotePassReward(33);
        reward_19.setEmpty();
        votePassRewards.add(reward_19);

        VotePassReward reward_20 = new VotePassReward(34);
        reward_20.setEmpty();
        votePassRewards.add(reward_20);

        VotePassReward reward_21 = new VotePassReward(35);
        reward_21.setEmpty();
        votePassRewards.add(reward_21);


        /*
            Plus Rewards - strana 1
         */

        // Reward+: 1 - 200 CraftCoins
        VotePassReward reward_plus_1 = new VotePassReward(8);
        reward_plus_1.setName("Tier+: 1");
        reward_plus_1.setDescription("§8- §f200 CraftCoins");
        reward_plus_1.setRequiredVotePassPlus();
        reward_plus_1.setRequiredVotes(10);
        reward_plus_1.setCraftCoins(200);
        reward_plus_1.setMaterial(Material.GOLD_INGOT);
        votePassRewards.add(reward_plus_1);

        // Reward+: 2 - 100 CraftCoins, 500 Server XP
        VotePassReward reward_plus_2 = new VotePassReward(9);
        reward_plus_2.setName("Tier+: 2");
        reward_plus_2.setDescription("§8- §f100 CraftCoins", "§8- §f500 Server XP");
        reward_plus_2.setRequiredVotePassPlus();
        reward_plus_2.setRequiredVotes(20);
        reward_plus_2.setCraftCoins(100);
        reward_plus_2.setServerExperience(500);
        reward_plus_2.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_2);

        // Reward+: 3 - 1x Basic Key
        VotePassReward reward_plus_3 = new VotePassReward(10);
        reward_plus_3.setName("Tier+: 3");
        reward_plus_3.setDescription("§8- §f1x Basic Key");
        reward_plus_3.setRequiredVotePassPlus();
        reward_plus_3.setRequiredVotes(30);
        reward_plus_3.setRequiredSlotInInventory();
        reward_plus_3.setServerCommand(ServerType.SURVIVAL, "crate give physical Basic 1 %player%");
        reward_plus_3.setServerCommand(ServerType.SKYBLOCK, "crate give physical Basic 1 %player%");
        reward_plus_3.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_3);

        // Reward+: 4 - SB: Jungle Minion / Creative WE 24h
        VotePassReward reward_plus_4 = new VotePassReward(11);
        reward_plus_4.setName("Tier+: 4");
        reward_plus_4.setDescription("§8- §fSkyblock: Jungle Minion §7nebo §fCreative WorldEdit 24h");
        reward_plus_4.setRequiredVotePassPlus();
        reward_plus_4.setRequiredVotes(40);
        reward_plus_4.setRequiredSlotInInventory();
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.(center|set|walls|move|overlay) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.(pos|chunk) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 24h creative");
        reward_plus_4.setServerCommand(ServerType.SKYBLOCK, "msetup give minion jungle %player% 1");
        reward_plus_4.setMaterial(Material.JUNGLE_WOOD);
        votePassRewards.add(reward_plus_4);

        // Reward+: 5 - SellStick: 100 použití
        VotePassReward reward_plus_5 = new VotePassReward(12);
        reward_plus_5.setName("Tier+: 5");
        reward_plus_5.setDescription("§8- §fSellStick: 100 použití");
        reward_plus_5.setRequiredVotePassPlus();
        reward_plus_5.setRequiredVotes(50);
        reward_plus_5.setRequiredSlotInInventory();
        reward_plus_5.setServerCommand(ServerType.SURVIVAL, "sellstick give %player% 1 100");
        reward_plus_5.setServerCommand(ServerType.SKYBLOCK, "sellstick give %player% 1 100");
        reward_plus_5.setMaterial(Material.STICK);
        votePassRewards.add(reward_plus_5);

        // Reward+: 6 - 200 CraftCoins, 500 Server XP
        VotePassReward reward_plus_6 = new VotePassReward(13);
        reward_plus_6.setName("Tier+: 6");
        reward_plus_6.setDescription("§8- §f200 CraftCoins", "§8- §f500 Server XP");
        reward_plus_6.setRequiredVotePassPlus();
        reward_plus_6.setRequiredVotes(60);
        reward_plus_6.setCraftCoins(200);
        reward_plus_6.setServerExperience(500);
        reward_plus_6.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_6);

        // Reward+: 7 - Prison Boost → 300% → 12h
        VotePassReward reward_plus_7 = new VotePassReward(14);
        reward_plus_7.setName("Tier+: 7");
        reward_plus_7.setDescription("§8- §fPrison Boost: 300% → 12h");
        reward_plus_7.setRequiredVotePassPlus();
        reward_plus_7.setRequiredVotes(70);
        reward_plus_7.setServerCommand(ServerType.PRISON, "multiplier create %player% personal 43200000 300");
        reward_plus_7.setMaterial(Material.IRON_INGOT);
        votePassRewards.add(reward_plus_7);

        // PAGE 2

        // Reward+: 8 - 500 Server XP, SB Minion Boost - 75% - 12h
        VotePassReward reward_plus_8 = new VotePassReward(22);
        reward_plus_8.setName("Tier+: 8");
        reward_plus_8.setDescription("§8- §f500 Server XP", "§8- §fSkyblock: Minion Boost 75% - 12h");
        reward_plus_8.setRequiredVotePassPlus();
        reward_plus_8.setRequiredVotes(80);
        reward_plus_8.setServerExperience(500);
        reward_plus_8.setRequiredSlotInInventory();
        reward_plus_8.setServerCommand(ServerType.SKYBLOCK, ""); //TODO: Dodělat
        reward_plus_8.setMaterial(Material.PRISMARINE_SHARD);
        votePassRewards.add(reward_plus_8);

        // Reward+: 9 - 2x Legendary Key
        VotePassReward reward_plus_9 = new VotePassReward(23);
        reward_plus_9.setName("Tier+: 9");
        reward_plus_9.setDescription("§8- §f2x Legendary Key");
        reward_plus_9.setRequiredVotePassPlus();
        reward_plus_9.setRequiredVotes(90);
        reward_plus_9.setRequiredSlotInInventory();
        reward_plus_9.setServerCommand(ServerType.SURVIVAL, "crate give physical Legendary 2 %player%");
        reward_plus_9.setServerCommand(ServerType.SKYBLOCK, "crate give physical Legendary 2 %player%");
        reward_plus_9.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_9);

        // Reward+: 10 - Skyblock: Iron Minion
        VotePassReward reward_plus_10 = new VotePassReward(24);
        reward_plus_10.setName("Tier+: 10");
        reward_plus_10.setDescription("§8- §fSkyblock: Iron Minion");
        reward_plus_10.setRequiredVotePassPlus();
        reward_plus_10.setRequiredVotes(100);
        reward_plus_10.setRequiredSlotInInventory();
        reward_plus_10.setServerCommand(ServerType.SKYBLOCK, "msetup give minion iron %player% 1");
        reward_plus_10.setMaterial(Material.IRON_ORE);
        votePassRewards.add(reward_plus_10);

        // Reward+: 11 - Skyblock: Fly na 14d
        VotePassReward reward_plus_11 = new VotePassReward(25);
        reward_plus_11.setName("Tier+: 11");
        reward_plus_11.setDescription("§8- §fSkyblock: Fly na 14d", "§8Aktivace pomocí /is fly");
        reward_plus_11.setRequiredVotePassPlus();
        reward_plus_11.setRequiredVotes(115);
        reward_plus_11.setServerCommand(ServerType.SKYBLOCK, "lp user %player% permission settemp bskyblock.island.fly 14d skyblock");
        reward_plus_11.setMaterial(Material.IRON_BOOTS);
        votePassRewards.add(reward_plus_11);

        // Reward+: 12 - 1,000 Server XP
        VotePassReward reward_plus_12 = new VotePassReward(26);
        reward_plus_12.setName("Tier+: 12");
        reward_plus_12.setDescription("§8- §f1,000 Server XP");
        reward_plus_12.setRequiredVotePassPlus();
        reward_plus_12.setRequiredVotes(130);
        reward_plus_12.setServerExperience(1000);
        reward_plus_12.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_12);

        // Reward+: 13 - McMMO: 400% - 48h
        VotePassReward reward_plus_13 = new VotePassReward(27);
        reward_plus_13.setName("Tier+: 13");
        reward_plus_13.setDescription("§8- §fMcMMO: 400% - 48h");
        reward_plus_13.setRequiredVotePassPlus();
        reward_plus_13.setServerCommand(ServerType.SURVIVAL, "lp user %player% permission settemp mcmmo.perks.xp.quadruple.* 48h survival");
        reward_plus_13.setServerCommand(ServerType.SKYBLOCK, "lp user %player% permission settemp mcmmo.perks.xp.quadruple.* 48h skyblock");
        reward_plus_13.setRequiredVotes(145);
        reward_plus_13.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_13);

        // Reward+: 14 - 3x Legendary Key
        VotePassReward reward_plus_14 = new VotePassReward(28);
        reward_plus_14.setName("Tier+: 14");
        reward_plus_14.setDescription("§8- §f3x Legendary Key");
        reward_plus_14.setRequiredVotePassPlus();
        reward_plus_14.setRequiredSlotInInventory();
        reward_plus_14.setServerCommand(ServerType.SURVIVAL, "crate give physical Legendary 3 %player%");
        reward_plus_14.setServerCommand(ServerType.SKYBLOCK, "crate give physical Legendary 3 %player%");
        reward_plus_14.setRequiredVotes(160);
        reward_plus_14.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_14);

        /// ???

        // Reward+: 15 - 3x Basic Key
        VotePassReward reward_plus_15 = new VotePassReward(36);
        reward_plus_15.setName("Tier+: 15");
        reward_plus_15.setDescription("§8- §f3x Basic Key");
        reward_plus_15.setRequiredVotePassPlus();
        reward_plus_15.setRequiredSlotInInventory();
        reward_plus_15.setServerCommand(ServerType.SURVIVAL, "crate give physical Basic 3 %player%");
        reward_plus_15.setServerCommand(ServerType.SKYBLOCK, "crate give physical Basic 3 %player%");
        reward_plus_15.setRequiredVotes(175);
        reward_plus_15.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_15);

        // Reward+: 16 - Skyblock: Minion AutoSell (1x)
        VotePassReward reward_plus_16 = new VotePassReward(37);
        reward_plus_16.setName("Tier+: 16");
        reward_plus_16.setDescription("§8- §fSkyblock: Minion AutoSell");
        reward_plus_16.setRequiredVotePassPlus();
        reward_plus_16.setRequiredSlotInInventory();
        reward_plus_16.setServerCommand(ServerType.SKYBLOCK, ""); //TODO: Dodělat
        reward_plus_16.setRequiredVotes(190);
        reward_plus_16.setMaterial(Material.WRITTEN_BOOK);
        votePassRewards.add(reward_plus_16);

        // Reward+: 17 - Hats: Legendary Lion
        VotePassReward reward_plus_17 = new VotePassReward(38);
        reward_plus_17.setName("Tier+: 17");
        reward_plus_17.setDescription("§8- §fHats: Legendary Lion");
        reward_plus_17.setRequiredVotePassPlus();
        reward_plus_17.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.hats.lion");
        reward_plus_17.setRequiredVotes(200);
        reward_plus_17.setMaterial(Material.NETHER_STAR);
        votePassRewards.add(reward_plus_17);


        Collections.sort(votePassRewards); // LOL collections


    }
}
