package cz.craftmania.crafteconomy.utils;

public enum ServerType {

    SURVIVAL_117("survival"),
    SURVIVAL_118("survival_118"),
    SKYBLOCK_117("skyblock"),
    SKYBLOCK_118("skyblock_118"),
    CREATIVE("creative"),
    PRISON("prison"),
    VANILLA("vanilla"),
    SKYCLOUD("skycloud"),
    LOBBY("lobby"),
    EVENT_SERVER("event_server"),
    SKYGRID("skygrid"),
    HARDCORE_VANILLA("hvanilla"),
    ANARCHY("anarchy"),

    /**
     * Unknown type je používaný na servery, které nemají ID
     * nebo na odměny z VotePassu (aktivaci všude).
     */
    UNKNOWN("unknown"),

    // Staré servery
    VANILLA_116("vanilla_116");

    private String tableId;

    ServerType(){};

    ServerType(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
    }
}
