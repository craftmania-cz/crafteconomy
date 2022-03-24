package cz.craftmania.crafteconomy.objects;

public enum LevelType {

    // Levels and experience
    GLOBAL_LEVEL("global_level"),
    GLOBAL_EXPERIENCE("global_experience"),

    SURVIVAL_117_LEVEL("survival_level"),
    SURVIVAL_117_EXPERIENCE("survival_experience"),

    SURVIVAL_118_LEVEL("survival_118_level"),
    SURVIVAL_118_EXPERIENCE("survival_118_experience"),

    SKYBLOCK_117_LEVEL("skyblock_level"),
    SKYBLOCK_117_EXPERIENCE("skyblock_experience"),

    SKYBLOCK_118_LEVEL("skyblock_118_level"),
    SKYBLOCK_118_EXPERIENCE("skyblock_118_experience"),

    CREATIVE_LEVEL("creative_level"),
    CREATIVE_EXPERIENCE("creative_experience"),

    PRISON_LEVEL("prison_level"),
    PRISON_EXPERIENCE("prison_experience"),

    VANILLA_LEVEL("vanilla_level"),
    VANILLA_EXPERIENCE("vanilla_experience"),

    @Deprecated
    SKYCLOUD_LEVEL("skycloud_level"),
    @Deprecated
    SKYCLOUD_EXPERIENCE("skycloud_experience"),

    @Deprecated
    HARDCORE_VANILLA_LEVEL("hardcore_vanilla_level"),
    @Deprecated
    HARDCORE_VANILLA_EXPERIENCE("hardcore_vanilla_experience"),

    ANARCHY_LEVEL("anarchy_level"),
    ANARCHY_EXPERIENCE("anarchy_experience"),

    @Deprecated
    VANILLA_116_LEVEL("vanilla_116_level"),
    @Deprecated
    VANILLA_116_EXPERIENCE("vanilla_116_experience")
    ;

    private String columnId;

    LevelType() {}

    LevelType(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnId() {
        return columnId;
    }
}
