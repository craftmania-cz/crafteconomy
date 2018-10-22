package cz.craftmania.crafteconomy.objects;

public class Multiplier {

    private CraftPlayer owner;
    private String ownerName;
    private MultiplierType type;
    private double multiply;
    private long startTime;
    private long endTime;

    public Multiplier(String ownerName, MultiplierType type, double multiplier) {
        this.ownerName = ownerName; // Global multiplier nema CraftPlayer
        this.type = type;
        this.multiply = multiplier;
        this.startTime = 0L;
        this.endTime = 0L;
    }

    public Multiplier(CraftPlayer player, MultiplierType type, double multiplier) {
        this.owner = player;
        this.type = type;
        this.multiply = multiplier;
        this.startTime = 0L;
        this.endTime = 0L;
    }

    public Multiplier(CraftPlayer player, MultiplierType type, double multiplier, long startTime, long endTime) {
        this.owner = player;
        this.type = type;
        this.multiply = multiplier;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CraftPlayer getOwner() {
        return owner;
    }

    public void setOwner(CraftPlayer owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public MultiplierType getType() {
        return type;
    }

    public void setType(MultiplierType type) {
        this.type = type;
    }

    public double getMultiply() {
        return multiply;
    }

    public void setMultiply(double multiply) {
        this.multiply = multiply;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
