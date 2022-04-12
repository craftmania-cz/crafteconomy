package cz.craftmania.crafteconomy.objects;

public enum EconomyType {

    // Basic economy
    CRAFT_COINS,
    CRAFT_TOKENS,
    VOTE_TOKENS, // 1.9+
    VOTE_TOKENS_2, // 1.14+
    VOTE_TOKENS_3, // 1.18+
    KARMA_POINTS,
    QUEST_POINTS,
    SEASON_POINTS,
    EVENT_POINTS,
    PARKOUR_POINTS,

    WEEK_VOTES,
    MONTH_VOTES,
    TOTAL_VOTES,
    VOTE_PASS;

    EconomyType() {
    }
}
