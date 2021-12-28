package cz.craftmania.crafteconomy.objects;

public enum LevelType {

    // Levels and experience
    GLOBAL_LEVEL,
    GLOBAL_EXPERIENCE,

    SURVIVAL_LEVEL,
    SURVIVAL_EXPERIENCE,

    SKYBLOCK_LEVEL,
    SKYBLOCK_EXPERIENCE,

    CREATIVE_LEVEL,
    CREATIVE_EXPERIENCE,

    PRISON_LEVEL,
    PRISON_EXPERIENCE,

    VANILLA_LEVEL,
    VANILLA_EXPERIENCE,

    @Deprecated
    SKYCLOUD_LEVEL,
    @Deprecated
    SKYCLOUD_EXPERIENCE,

    @Deprecated
    HARDCORE_VANILLA_LEVEL,
    @Deprecated
    HARDCORE_VANILLA_EXPERIENCE,

    ANARCHY_LEVEL,
    ANARCHY_EXPERIENCE,

    @Deprecated
    VANILLA_116_LEVEL,
    @Deprecated
    VANILLA_116_EXPERIENCE
    ;

    LevelType() {
    }
}
