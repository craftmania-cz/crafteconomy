package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.utils.ServerType;
import lombok.NonNull;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VotePassReward implements Comparable<VotePassReward> {

    private int id;
    private String name = "[Unknown]";
    private List<String> description = new ArrayList<>();
    private int requiredVotes = 0;
    private boolean requireVotepassPlus = false; // Placený VotePass
    private boolean requireSlotInInventory = false;
    private List<String> permissions = new ArrayList<>();
    private List<ServerCommand> commands = new ArrayList<>();
    private Material itemMaterial = Material.PAPER;
    private long craftcoins = 0;
    private long crafttokens = 0;
    private long serverExperience = 0;
    private ServerType requiredServer = ServerType.UNKNOWN;
    private boolean isEmpty = false;

    public VotePassReward() {};

    public VotePassReward(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public VotePassReward setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    /**
     * Nastavení počtu hlasů - 10, 20, 30 atd.
     * @param votes Počet hlasů
     * @return {@link VotePassReward}
     */
    public VotePassReward setRequiredVotes(int votes) {
        this.requiredVotes = votes;
        return this;
    }

    /**
     * Nastaví, že k získání odměny je vyžadování členství v VotePass Plus
     * @return {@link VotePassReward}
     */
    public VotePassReward setRequiredVotePassPlus() {
        this.requireVotepassPlus = true;
        return this;
    }

    public VotePassReward setDescription(@NonNull final String... description) {
        Collections.addAll(this.description, description);
        return this;
    }

    public VotePassReward setRequiredSlotInInventory() {
        this.requireSlotInInventory = true;
        return this;
    }

    public VotePassReward setPermissions(@NotNull final String... permissions) {
        Collections.addAll(this.permissions, permissions);
        return this;
    }

    public VotePassReward setServerCommand(final ServerType server, final String command) {
        this.commands.add(new ServerCommand(server, command));
        return this;
    }

    public VotePassReward setCraftCoins(long craftcoins) {
        this.craftcoins = craftcoins;
        return this;
    }

    public VotePassReward setCraftTokens(long crafttokens) {
        this.crafttokens = crafttokens;
        return this;
    }

    public VotePassReward setRequiredServer(ServerType server) {
        this.requiredServer = server;
        return this;
    }

    public VotePassReward setServerExperience(long serverExperience) {
        this.serverExperience = serverExperience;
        return this;
    }

    public VotePassReward setMaterial(@NotNull Material material) {
        this.itemMaterial = material;
        return this;
    }

    /**
     * Pokud je nastavený jako empty, ukáže se v inv s křížkem.
     * Slouží pouze jako výplň na prázdný pole.
     * @return
     */
    public VotePassReward setEmpty() {
        this.isEmpty = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public int getRequiredVotes() {
        return requiredVotes;
    }

    public boolean isRequireVotepassPlus() {
        return requireVotepassPlus;
    }

    public boolean isRequireSlotInInventory() {
        return requireSlotInInventory;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<ServerCommand> getCommands() {
        return commands;
    }

    public long getCraftCoins() {
        return craftcoins;
    }

    public long getCraftTokens() {
        return crafttokens;
    }

    public long getServerExperience() {
        return serverExperience;
    }

    public ServerType getRequiredServer() {
        return requiredServer;
    }

    public Material getItemMaterial() {
        return itemMaterial;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public int compareTo(VotePassReward reward) {
        return this.getId().compareTo(reward.getId());
    }
}
