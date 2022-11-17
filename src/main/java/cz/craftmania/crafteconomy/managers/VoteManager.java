package cz.craftmania.crafteconomy.managers;

import cz.craftmania.craftcore.xseries.messages.Titles;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.events.economy.PlayerVoteEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.objects.VotePassReward;
import cz.craftmania.crafteconomy.utils.PlayerUtils;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.craftnotifications.api.NotificationsAPI;
import cz.craftmania.craftnotifications.objects.NotificationPriority;
import cz.craftmania.craftnotifications.objects.NotificationType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoteManager {

    private BasicManager manager = new BasicManager();
    private PlayerUtils playerUtils = new PlayerUtils();
    public static List<VotePassReward> votePassRewards = new ArrayList<>();

    /**
     * Přidělí hráči hlas a oznámí do chatu o info
     * @param nick Hráč, který hlasoval
     * @param uuid UUID hráče
     * @param coins Počet CraftCoins, který hráč dostane v základu
     * @param votetokens Počet VoteTokens, které hráč dostane
     */
    public void playerVote(final String nick, final String uuid, final String coins, final String votetokens) {
        Player player = Bukkit.getServer().getPlayer(nick);
        assert player != null; // Hráč nemůže být null jelikož Bungeecord kontroluje zda je na serveru
        CraftPlayer craftPlayer = this.manager.getCraftPlayer(player);

        EconomyAPI.VOTE_TOKENS.give(player, Integer.parseInt(votetokens));
        EconomyAPI.CRAFT_COINS.give(player, Integer.parseInt(coins));
        Main.getInstance().getMySQL().addPlayerVote(nick);
        craftPlayer.addVote();
        craftPlayer.setLastVote(System.currentTimeMillis());

        Titles.sendTitle(player, "§a§lDěkujeme!", "§fDostal(a) jsi " + votetokens + "x VoteToken.");

        Bukkit.getPluginManager().callEvent(new PlayerVoteEvent(player, 1, 1));
        player.sendMessage(" ");
        player.sendMessage("§bVyber si odměnu jakou chceš!");
        player.sendMessage("§eStačí zajít do §f/cshop §ea zvolit si odměnu za VoteTokeny.");
        player.sendMessage("§6Nezapomeň si vybírat odměny v §f/votepass");
        player.sendMessage("");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§b" + player.getName() + " §ehlasoval a získal §aodměnu! §c/vote");
        }
        // Reward: Heart Cape - 1500 Votes
        if (craftPlayer.getTotalVotes() >= 1500) {
            playerUtils.givePermission(player, "craftmanager.backpack.heart_cape");
            NotificationsAPI.Companion.createNotificationByUUID(
                    player.getUniqueId(),
                    NotificationType.INFO,
                    NotificationPriority.NORMAL,
                    "ALL",
                    "Aktivace cosmetics: 1500 hlasů",
                    "Na tvém účtu byla aktivována cosmetic odměna za 1500 hlasů §7- §cHeart Cape."
            );
        }
    }

    public long getVotePassVotes(@NotNull Player player) {
        return this.manager.getCraftPlayer(player).getVotePassVotes();
    }

    public static List<VotePassReward> getVotePassRewards() {
        return votePassRewards;
    }

    public static void loadVotePassRewards() {

        /*
         * Free rewards - strana 1
         */

        // Reward: 1 - 100 CraftCoins
        VotePassReward reward_1 = new VotePassReward(1);
        reward_1.setName("Tier: 1");
        reward_1.setRequiredVotes(20);
        reward_1.setDescription("§8- §f100 CraftCoins");
        reward_1.setCraftCoins(100);
        reward_1.setMaterial(Material.GOLD_NUGGET);
        votePassRewards.add(reward_1);

        // Reward: 2 - 1,000 Server XP
        VotePassReward reward_2 = new VotePassReward(2);
        reward_2.setName("Tier: 2");
        reward_2.setRequiredVotes(40);
        reward_2.setDescription("§8- §f1,000 Server XP");
        reward_2.setServerExperience(1000);
        reward_2.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_2);

        // Reward: 3 - 2x Basic Key
        VotePassReward reward_3 = new VotePassReward(3);
        reward_3.setName("Tier: 3");
        reward_3.setRequiredVotes(60);
        reward_3.setDescription("§8- §f2x Basic Key");
        reward_3.setRequiredSlotInInventory();
        reward_3.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Basic 2 %player%");
        reward_3.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Basic 2 %player%");
        reward_3.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Basic 2 %player%");
        reward_3.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_3);

        // Reward: 4 - Skyblock: Fly (2,000B) / Creative WE 12h
        VotePassReward reward_4 = new VotePassReward(4);
        reward_4.setName("Tier: 4");
        reward_4.setRequiredVotes(80);
        reward_4.setDescription("", "§eVolba:", "§8- §fSkyblock: Fly (2,000B)", "§8- §fSurvival: Fly (2,000B)", "§8- §fCreative WorldEdit: 12h", "", "§cVýhoda se aktivuje, tam kde jí otevřeš.");
        reward_4.setRequiredSlotInInventory();
        reward_4.setServerCommand(ServerType.SKYBLOCK_117, "cmi flightcharge add %player% 2000");
        reward_4.setServerCommand(ServerType.SURVIVAL_118, "cmi flightcharge add %player% 2000");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.* 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.tool.* 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.* 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.(drain|replacenear|snow|thaw|regen) 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.generation.* 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 12h creative");
        reward_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 12h creative");
        reward_4.setMaterial(Material.WOODEN_AXE);
        votePassRewards.add(reward_4);

        // Reward: 5 - Boxovací rukavice
        VotePassReward reward_5 = new VotePassReward(5);
        reward_5.setName("Tier: 5");
        reward_5.setRequiredVotes(100);
        reward_5.setDescription("§8- §fCosmetic: Boxovací rukavice");
        reward_5.setRequiredSlotInInventory();
        reward_5.setServerCommand(ServerType.UNKNOWN, "cosadmin %player% boxing_glove %player%");
        //reward_5.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.hats.scuba_goggles");
        reward_5.setMaterial(Material.STONE_SWORD);
        votePassRewards.add(reward_5);

        // Reward: 6 - 500 CraftCoins, 3x Basic Key
        VotePassReward reward_6 = new VotePassReward(6);
        reward_6.setName("Tier: 6");
        reward_6.setRequiredVotes(120);
        reward_6.setDescription("§8- §f500 CraftCoins", "§8- §f3x Basic Key");
        reward_6.setCraftCoins(500);
        reward_6.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Basic 3 %player%");
        reward_6.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Basic 3 %player%");
        reward_6.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Basic 3 %player%");
        reward_6.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_6);

        // Reward: 7 - 1,000 Server XP
        VotePassReward reward_7 = new VotePassReward(7);
        reward_7.setName("Tier: 7");
        reward_7.setRequiredVotes(140);
        reward_7.setDescription("§8- §f200 CraftCoins", "§8- §f1,000 Server XP");
        reward_7.setCraftCoins(200);
        reward_7.setServerExperience(1000);
        reward_7.setMaterial(Material.HORN_CORAL);
        votePassRewards.add(reward_7);

        // PAGE 2

        // Reward: 8 - 1,500 Server XP, 1x Legendary Key
        VotePassReward reward_8 = new VotePassReward(15);
        reward_8.setName("Tier: 8");
        reward_8.setRequiredVotes(160);
        reward_8.setDescription("§8- §f1,500 Server XP", "§8- §f1x Legendary Key");
        reward_8.setServerExperience(1500);
        reward_8.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Legendary 1 %player%");
        reward_8.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Legendary 1 %player%");
        reward_8.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Legendary 1 %player%");
        reward_8.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_8);

        // Reward: 9 - ArmorStandEditor 7 dní
        VotePassReward reward_9 = new VotePassReward(16);
        reward_9.setName("Tier: 9");
        reward_9.setRequiredVotes(180);
        reward_9.setDescription("§8- §fArmorStandEditor na 7 dní");
        reward_9.setServerCommand(ServerType.SURVIVAL_117, "lp user %player% permission settemp asedit.* 7d survival");
        reward_9.setServerCommand(ServerType.SKYBLOCK_117, "lp user %player% permission settemp asedit.* 7d skyblock");
        reward_9.setServerCommand(ServerType.SURVIVAL_118, "lp user %player% permission settemp asedit.* 7d survival");
        reward_9.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp asedit.* 7d creative");
        reward_9.setServerCommand(ServerType.VANILLA, "lp user %player% permission settemp asedit.* 7d vanilla");
        reward_8.setMaterial(Material.ARMOR_STAND);
        votePassRewards.add(reward_9);

        // Reward: 10 - 1 CraftToken, 2000 Server XP
        VotePassReward reward_10 = new VotePassReward(17);
        reward_10.setName("Tier: 10");
        reward_10.setRequiredVotes(200);
        reward_10.setDescription("§8- §f1x CraftToken", "§8- §f2000 Server XP");
        reward_10.setServerExperience(2000);
        reward_10.setCraftTokens(1);
        reward_10.setMaterial(Material.EMERALD);
        votePassRewards.add(reward_10);

        // Reward: 11 - Skyblock: Fly (6,000B) / Creative WE 24h
        VotePassReward reward_11 = new VotePassReward(18);
        reward_11.setName("Tier: 11");
        reward_11.setRequiredVotes(220);
        reward_11.setDescription("", "§eVolba:", "§8- §fSkyblock: Fly (3,000B)", "§8- §fSurvival: Fly (3,000B)", "§8- §fCreative WE 24h", "", "§cVýhoda se aktivuje, tam kde jí otevřeš.");
        reward_11.setServerCommand(ServerType.SKYBLOCK_117, "cmi flightcharge add %player% 3000");
        reward_11.setServerCommand(ServerType.SURVIVAL_118, "cmi flightcharge add %player% 3000");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.* 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.tool.* 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.* 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.(drain|replacenear|snow|thaw|regen) 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.generation.* 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 24h creative");
        reward_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 24h creative");
        reward_11.setMaterial(Material.FEATHER);
        votePassRewards.add(reward_11);

        // Reward: 12 - původne: SellStick: 50 použití, 500 Server XP
        // Reward: 12 - 1500 Server XP
        VotePassReward reward_12 = new VotePassReward(19);
        reward_12.setName("Tier: 12");
        reward_12.setRequiredVotes(240);
        reward_12.setDescription("§8- §f1500 Server XP");
        reward_12.setServerExperience(1500);
        //reward_12.setRequiredSlotInInventory();
        //reward_12.setServerCommand(ServerType.SURVIVAL_117, "sellstick give %player% 1 50");
        //reward_12.setServerCommand(ServerType.SKYBLOCK_117, "sellstick give %player% 1 50");
        reward_12.setMaterial(Material.STICK);
        votePassRewards.add(reward_12);

        // Reward: 13 - 500 CraftCoins
        VotePassReward reward_13 = new VotePassReward(20);
        reward_13.setName("Tier: 13");
        reward_13.setRequiredVotes(260);
        reward_13.setDescription("§8- §fSurvival 1.18: Jobs: +20% Boost - 3 dny");
        reward_13.setServerCommand(ServerType.SURVIVAL_118, "lp user %player% permission settemp jobs.boost.all.money.0.20 48h survival");
        //reward_13.setCraftCoins(500);
        reward_13.setMaterial(Material.GOLD_ORE);
        votePassRewards.add(reward_13);

        // Reward: 14 - 500 CraftCoins, 5x Basic Key
        VotePassReward reward_14 = new VotePassReward(21);
        reward_14.setName("Tier: 14");
        reward_14.setDescription("§8- §f500 CraftCoins", "§8- §f5x Basic Key");
        reward_14.setRequiredVotes(280);
        reward_14.setCraftCoins(500);
        reward_14.setRequiredSlotInInventory();
        reward_14.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Basic 5 %player%");
        reward_14.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Basic 5 %player%");
        reward_14.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Basic 5 %player%");
        reward_14.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_14);

        // PAGE 3

        // Reward: 15 - McMMO: 200% Boost - 3d
        VotePassReward reward_15 = new VotePassReward(29);
        reward_15.setName("Tier: 15");
        reward_15.setDescription("", "§eVolba:", "§8- §fSurvival 1.18: Jobs: +25% Boost - 3 dny", "§8- §fSkyblock 1.17: McMMO 200% boost 2 dny", "§8- §fSurvival 1.17: McMMO 200% boost 2 dny");
        reward_15.setRequiredVotes(300);
        reward_15.setServerCommand(ServerType.SURVIVAL_118, "lp user %player% permission settemp jobs.boost.all.money.0.25 72h survival");
        reward_15.setServerCommand(ServerType.SKYBLOCK_117, "lp user %player% permission settemp mcmmo.perks.xp.double.* 48h skyblock");
        reward_15.setServerCommand(ServerType.SURVIVAL_117, "lp user %player% permission settemp mcmmo.perks.xp.double.* 48h survival");
        reward_15.setMaterial(Material.GOLD_INGOT);
        votePassRewards.add(reward_15);

        // Reward: 16 - Cosmetic: Chicken Helmet
        VotePassReward reward_16 = new VotePassReward(30);
        reward_16.setName("Tier: 16");
        reward_16.setDescription("§8- §fCosmetic: Chicken Helmet");
        reward_16.setRequiredVotes(325);
        reward_16.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.hats.chicken_helmet");
        reward_16.setMaterial(Material.BLAZE_ROD);
        votePassRewards.add(reward_16);

        // Reward: 17 -  2,000 Server XP
        VotePassReward reward_17 = new VotePassReward(31);
        reward_17.setName("Tier: 17");
        reward_17.setDescription("§8- §f2,000 Server XP");
        reward_17.setRequiredVotes(350);
        reward_17.setServerExperience(2000);
        //reward_17.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.disguise.sheep.yellow");
        votePassRewards.add(reward_17);

        // Reward: 18 - Sellstick: 200 použití
        VotePassReward reward_18 = new VotePassReward(32);
        reward_18.setName("Tier: 18");
        reward_18.setDescription("§8- §fSellStick: 50 použití");
        reward_18.setRequiredVotes(375);
        reward_18.setRequiredSlotInInventory();
        reward_18.setServerCommand(ServerType.SURVIVAL_117, "sellstick give %player% 1 50");
        reward_18.setServerCommand(ServerType.SKYBLOCK_117, "sellstick give %player% 1 50");
        //TODO: Survival 1.18?
        votePassRewards.add(reward_18);

        // Reward: 19 - Skyblock: Fly (4,000B)
        VotePassReward reward_19 = new VotePassReward(33);
        reward_19.setName("Tier: 19");
        reward_19.setDescription("", "§eVolba:", "§8- §fSkyblock 1.17: Fly (3,000B)", "§8- §fSurvival 1.18: Fly (3,000B)");
        reward_19.setRequiredVotes(400);
        reward_19.setServerCommand(ServerType.SKYBLOCK_117, "cmi flightcharge add %player% 3000");
        reward_19.setServerCommand(ServerType.SURVIVAL_118, "cmi flightcharge add %player% 3000");
        votePassRewards.add(reward_19);

        // Reward: 20 - Backpack: Gold Crystal Wings
        VotePassReward reward_20 = new VotePassReward(34);
        reward_20.setName("Tier: 20");
        reward_20.setDescription("§8- §fBackpack: Gold Crystal Wings [Survival 1.18]");
        reward_20.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.backpack.wings_gold_crystal");
        reward_20.setRequiredVotes(425);
        votePassRewards.add(reward_20);

        VotePassReward reward_21 = new VotePassReward(35);
        reward_21.setName("Tier: 21");
        reward_21.setDescription("§8- §fSword: Skyward (Limited Bonus Item)");
        reward_21.setServerCommand(ServerType.SURVIVAL_117, "cosadmin %player% skyward_sword %player%");
        reward_21.setServerCommand(ServerType.SURVIVAL_118, "cosadmin %player% skyward_sword %player%");
        reward_21.setServerCommand(ServerType.SKYBLOCK_117, "cosadmin %player% skyward_sword %player%");
        reward_21.setRequiredVotes(800);
        votePassRewards.add(reward_21);


        /*
            Plus Rewards - strana 1
         */

        // Reward+: 1 - 200 CraftCoins
        VotePassReward reward_plus_1 = new VotePassReward(8);
        reward_plus_1.setName("Tier+: 1");
        reward_plus_1.setDescription("§8- §f200 CraftCoins");
        reward_plus_1.setRequiredVotePassPlus();
        reward_plus_1.setRequiredVotes(20);
        reward_plus_1.setCraftCoins(200);
        reward_plus_1.setMaterial(Material.GOLD_NUGGET);
        votePassRewards.add(reward_plus_1);

        // Reward+: 2 - 100 CraftCoins, 500 Server XP
        VotePassReward reward_plus_2 = new VotePassReward(9);
        reward_plus_2.setName("Tier+: 2");
        reward_plus_2.setDescription("§8- §f100 CraftCoins", "§8- §f500 Server XP");
        reward_plus_2.setRequiredVotePassPlus();
        reward_plus_2.setRequiredVotes(40);
        reward_plus_2.setCraftCoins(100);
        reward_plus_2.setServerExperience(500);
        reward_plus_2.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_2);

        // Reward+: 3 - 1x Basic Key
        VotePassReward reward_plus_3 = new VotePassReward(10);
        reward_plus_3.setName("Tier+: 3");
        reward_plus_3.setDescription("§8- §f1x Basic Key");
        reward_plus_3.setRequiredVotePassPlus();
        reward_plus_3.setRequiredVotes(60);
        reward_plus_3.setRequiredSlotInInventory();
        reward_plus_3.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Basic 1 %player%");
        reward_plus_3.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Basic 1 %player%");
        reward_plus_3.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Basic 1 %player%");
        reward_plus_3.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_3);

        // Reward+: 4 - Skyblock: Fly (2,500B) / Creative WE 24h
        VotePassReward reward_plus_4 = new VotePassReward(11);
        reward_plus_4.setName("Tier+: 4");
        reward_plus_4.setDescription("", "§eVolba:", "§8- §fSkyblock: Fly (1,500B)", "§8- §fSurvival: Fly (1,500B)", "§8- §fCreative WorldEdit 24h", "", "§cVýhoda se aktivuje, tam kde jí otevřeš.");
        reward_plus_4.setRequiredVotePassPlus();
        reward_plus_4.setRequiredVotes(80);
        reward_plus_4.setRequiredSlotInInventory();
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.* 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.tool.* 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.* 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.(drain|replacenear|snow|thaw|regen) 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.generation.* 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 24h creative");
        reward_plus_4.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 24h creative");
        reward_plus_4.setServerCommand(ServerType.SKYBLOCK_117, "cmi flightcharge add %player% 1500");
        reward_plus_4.setServerCommand(ServerType.SURVIVAL_118, "cmi flightcharge add %player% 1500");
        reward_plus_4.setMaterial(Material.GOLDEN_AXE);
        votePassRewards.add(reward_plus_4);

        // Reward+: 5 - SellStick: 100 použití
        VotePassReward reward_plus_5 = new VotePassReward(12);
        reward_plus_5.setName("Tier+: 5");
        reward_plus_5.setDescription("§8- §fSellStick: 100 použití");
        reward_plus_5.setRequiredVotePassPlus();
        reward_plus_5.setRequiredVotes(100);
        reward_plus_5.setRequiredSlotInInventory();
        reward_plus_5.setServerCommand(ServerType.SURVIVAL_117, "sellstick give %player% 1 100");
        reward_plus_5.setServerCommand(ServerType.SKYBLOCK_117, "sellstick give %player% 1 100");
        //TODO: Survival 1:18
        reward_plus_5.setMaterial(Material.STICK);
        votePassRewards.add(reward_plus_5);

        // Reward+: 6 - 500 CraftCoins, 1x Legendary Key
        VotePassReward reward_plus_6 = new VotePassReward(13);
        reward_plus_6.setName("Tier+: 6");
        reward_plus_6.setDescription("§8- §f500 CraftCoins", "§8- §f1x Legendary Key");
        reward_plus_6.setRequiredVotePassPlus();
        reward_plus_6.setRequiredVotes(120);
        reward_plus_6.setCraftCoins(500);
        reward_plus_6.setRequiredSlotInInventory();
        reward_plus_6.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Legendary 1 %player%");
        reward_plus_6.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Legendary 1 %player%");
        reward_plus_6.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Legendary 1 %player%");
        reward_plus_6.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_6);

        // Reward+: 7 - Cosmetic: Golem Head
        VotePassReward reward_plus_7 = new VotePassReward(14);
        reward_plus_7.setName("Tier+: 7");
        reward_plus_7.setDescription("§8- §fCosmetic: Golem Head");
        reward_plus_7.setRequiredVotePassPlus();
        reward_plus_7.setRequiredVotes(140);
        reward_plus_7.setRequiredSlotInInventory();
        reward_plus_7.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.hats.golem_head");
        reward_plus_7.setMaterial(Material.NETHER_STAR);
        votePassRewards.add(reward_plus_7);

        // PAGE 2

        // Reward+: 8 - 750 Server XP, 2x Basic Key
        VotePassReward reward_plus_8 = new VotePassReward(22);
        reward_plus_8.setName("Tier+: 8");
        reward_plus_8.setDescription("§8- §f750 Server XP", "§8- §f2x Basic Key");
        reward_plus_8.setRequiredVotePassPlus();
        reward_plus_8.setRequiredVotes(160);
        reward_plus_8.setServerExperience(750);
        reward_plus_8.setRequiredSlotInInventory();
        reward_plus_8.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Basic 2 %player%");
        reward_plus_8.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Basic 2 %player%");
        reward_plus_8.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Basic 2 %player%");
        reward_plus_8.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_8);

        // Reward+: 9 - 2x Legendary Key
        VotePassReward reward_plus_9 = new VotePassReward(23);
        reward_plus_9.setName("Tier+: 9");
        reward_plus_9.setDescription("§8- §f2x Legendary Key");
        reward_plus_9.setRequiredVotePassPlus();
        reward_plus_9.setRequiredVotes(180);
        reward_plus_9.setRequiredSlotInInventory();
        reward_plus_9.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Legendary 2 %player%");
        reward_plus_9.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Legendary 2 %player%");
        reward_plus_9.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Legendary 2 %player%");
        reward_plus_9.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_9);

        // Reward+: 10 - 1x VoidChest
        VotePassReward reward_plus_10 = new VotePassReward(24);
        reward_plus_10.setName("Tier+: 10");
        reward_plus_10.setDescription("§8- §fSkyblock: 1x Coal Minion", "§8- §fSkyblock: 1x Birch Minion");
        reward_plus_10.setRequiredVotePassPlus();
        reward_plus_10.setRequiredVotes(200);
        reward_plus_10.setRequiredSlotInInventory();
        reward_plus_10.setServerCommand(ServerType.SKYBLOCK_117, "msetup give minion coal %player% 1");
        reward_plus_10.setServerCommand(ServerType.SKYBLOCK_117, "msetup give minion birch %player% 1");
        reward_plus_10.setMaterial(Material.CHEST);
        votePassRewards.add(reward_plus_10);

        // Reward+: 11 - Skyblock: Fly (8,000B) / Creative WE 48h
        VotePassReward reward_plus_11 = new VotePassReward(25);
        reward_plus_11.setName("Tier+: 11");
        reward_plus_11.setDescription("", "§eVolba:", "§8- §fSkyblock: Fly (8,000B)", "§8- §fCreative WE 48h", "", "§cVýhoda se aktivuje, tam kde jí otevřeš.");
        reward_plus_11.setRequiredVotePassPlus();
        reward_plus_11.setRequiredVotes(220);
        reward_plus_11.setServerCommand(ServerType.SKYBLOCK_117, "cmi flightcharge add %player% 8000");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.brush.* 48h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.clipboard.(copy|cut|flip|paste|rotate) 48h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.fill 48h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.wand 48h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.history.(redo|undo) 48h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.selection.* 24h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.tool.* 24h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.region.* 24h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.(drain|replacenear|snow|thaw|regen) 24h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.generation.* 24h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp worldedit.navigation.up 48h creative");
        reward_plus_11.setServerCommand(ServerType.CREATIVE, "lp user %player% permission settemp fawe.worldguard 48h creative");
        reward_plus_11.setMaterial(Material.IRON_AXE);
        votePassRewards.add(reward_plus_11);

        // Reward+: 12 - 1,000 Server XP
        VotePassReward reward_plus_12 = new VotePassReward(26);
        reward_plus_12.setName("Tier+: 12");
        reward_plus_12.setDescription("§8- §f3,000 Server XP", "§8- §f500 CC");
        reward_plus_12.setRequiredVotePassPlus();
        reward_plus_12.setRequiredVotes(240);
        reward_plus_12.setServerExperience(3000);
        reward_plus_12.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_12);

        // Reward+: 13 - Skyblock: Iron Ore Minion
        VotePassReward reward_plus_13 = new VotePassReward(27);
        reward_plus_13.setName("Tier+: 13");
        reward_plus_13.setDescription("§8- §fSkyblock: Iron Ore Minion");
        reward_plus_13.setRequiredVotePassPlus();
        reward_plus_13.setServerCommand(ServerType.SKYBLOCK_117, "msetup give minion iron %player% 1");
        reward_plus_13.setRequiredVotes(260);
        reward_plus_13.setMaterial(Material.IRON_ORE);
        votePassRewards.add(reward_plus_13);

        // Reward+: 14 - 1x Legendary Key, 750 CraftCoins
        VotePassReward reward_plus_14 = new VotePassReward(28);
        reward_plus_14.setName("Tier+: 14");
        reward_plus_14.setDescription("§8- §f1x Legendary Key", "§8- §f750 CraftCoins");
        reward_plus_14.setRequiredVotePassPlus();
        reward_plus_14.setRequiredSlotInInventory();
        reward_plus_14.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Legendary 1 %player%");
        reward_plus_14.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Legendary 1 %player%");
        reward_plus_14.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Legendary 1 %player%");
        reward_plus_14.setCraftCoins(750);
        reward_plus_14.setRequiredVotes(280);
        reward_plus_14.setMaterial(Material.TRIPWIRE_HOOK);
        votePassRewards.add(reward_plus_14);

        ///

        // Reward+: 15 - McMMO: 300% Boost
        VotePassReward reward_plus_15 = new VotePassReward(36);
        reward_plus_15.setName("Tier+: 15");
        reward_plus_15.setDescription("", "§eVolba:", "§8- §fSurvival 1.18: Jobs: +40% Boost - 3 dny", "§8- §fSkyblock: 1.17 McMMO: 300% Boost - 72h", "§8- §fSurvival: 1.17 McMMO: 300% Boost - 72h");
        reward_plus_15.setRequiredVotePassPlus();
        reward_plus_15.setServerCommand(ServerType.SURVIVAL_117, "lp user %player% permission settemp mcmmo.perks.xp.triple.* 72h survival");
        reward_plus_15.setServerCommand(ServerType.SKYBLOCK_117, "lp user %player% permission settemp mcmmo.perks.xp.triple.* 72h skyblock");
        reward_plus_15.setServerCommand(ServerType.SURVIVAL_118, "lp user %player% permission settemp jobs.boost.all.money.0.40 72h survival");
        reward_plus_15.setRequiredVotes(300);
        reward_plus_15.setMaterial(Material.IRON_INGOT);
        votePassRewards.add(reward_plus_15);

        // Reward+: 16 - 2,500 Server XP, 2x Legendary Key, 300 CraftCoins
        VotePassReward reward_plus_16 = new VotePassReward(37);
        reward_plus_16.setName("Tier+: 16");
        reward_plus_16.setDescription("§8- §f2,500 Server XP", "§8- §f2x Legendary Key");
        reward_plus_16.setRequiredVotePassPlus();
        reward_plus_16.setRequiredSlotInInventory();
        reward_plus_16.setServerCommand(ServerType.SURVIVAL_117, "crate give physical Legendary 2 %player%");
        reward_plus_16.setServerCommand(ServerType.SKYBLOCK_117, "crate give physical Legendary 2 %player%");
        reward_plus_16.setServerCommand(ServerType.SURVIVAL_118, "crate give physical Legendary 2 %player%");
        reward_plus_16.setServerExperience(2500);
        reward_plus_16.setRequiredVotes(325);
        reward_plus_16.setMaterial(Material.EXPERIENCE_BOTTLE);
        votePassRewards.add(reward_plus_16);

        // Reward+: 17 - Balloon: Shark
        VotePassReward reward_plus_17 = new VotePassReward(38);
        reward_plus_17.setName("Tier+: 17");
        reward_plus_17.setDescription("§8- §fBalloon: Shark [Survival 1.18]");
        reward_plus_17.setRequiredVotePassPlus();
        reward_plus_17.setRequiredSlotInInventory();
        reward_plus_17.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.balloons.shark");
        reward_plus_17.setRequiredVotes(350);
        reward_plus_17.setMaterial(Material.NETHER_STAR);
        votePassRewards.add(reward_plus_17);

        // Reward+: 18 - 1x CraftToken
        VotePassReward reward_plus_18 = new VotePassReward(39);
        reward_plus_18.setName("Tier+: 18");
        reward_plus_18.setDescription("§8- §f1x CraftToken");
        reward_plus_18.setRequiredVotePassPlus();
        reward_plus_18.setCraftTokens(1);
        reward_plus_18.setRequiredVotes(375);
        reward_plus_18.setMaterial(Material.EMERALD);
        votePassRewards.add(reward_plus_18);

        // Reward+: 19 - Skyblock: Fly (10,000B)
        VotePassReward reward_plus_19 = new VotePassReward(40);
        reward_plus_19.setName("Tier+: 19");
        reward_plus_19.setDescription("", "§eVolba:", "§8- §fSkyblock: Fly (5,000B)", "§8- §fSurvival: Fly (5,000B)");
        reward_plus_19.setRequiredVotePassPlus();
        reward_plus_19.setServerCommand(ServerType.SKYBLOCK_117, "cmi flightcharge add %player% 5000");
        reward_plus_19.setServerCommand(ServerType.SURVIVAL_118, "cmi flightcharge add %player% 5000");
        reward_plus_19.setRequiredVotes(400);
        reward_plus_19.setMaterial(Material.FEATHER);
        votePassRewards.add(reward_plus_19);

        // Reward+: 20 - Cosmetics: Dark Gold Crystal Wings
        VotePassReward reward_plus_20 = new VotePassReward(41);
        reward_plus_20.setName("Tier+: 20");
        reward_plus_20.setDescription("§8- §fCosmetics: Dark Gold Crystal Wings");
        reward_plus_20.setRequiredVotePassPlus();
        reward_plus_20.setRequiredSlotInInventory();
        reward_plus_20.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.backpack.wings_dark_gold_crystal");
        reward_plus_20.setRequiredVotes(425);
        reward_plus_20.setMaterial(Material.NETHER_STAR);
        votePassRewards.add(reward_plus_20);

        // Reward+: 21 - Backpack: Kraken Arms
        //VotePassReward reward_plus_21 = new VotePassReward(42);
        //reward_plus_21.setName("Tier+: 21");
        //reward_plus_21.setDescription("§8- §fBackpack: Kraken Arms");
        //reward_plus_21.setRequiredVotePassPlus();
        //reward_plus_21.setServerCommand(ServerType.UNKNOWN, "lp user %player% permission set craftmanager.backpack.kraken_arms"); //TODO: Zkontrolovat
        //reward_plus_21.setRequiredVotes(500);
        //reward_plus_21.setMaterial(Material.NETHER_STAR);
        //votePassRewards.add(reward_plus_21);




        Collections.sort(votePassRewards); // LOL collections


    }
}
