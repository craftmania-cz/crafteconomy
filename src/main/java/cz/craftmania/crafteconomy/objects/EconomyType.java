package cz.craftmania.crafteconomy.objects;

public enum EconomyType {

    // Basic economy
    CRAFTCOINS,
    CRAFTTOKENS,
    VOTETOKENS_2, // "votetokens_2" je kvůli 1.14!
    KARMA,

    WEEK_VOTES,
    MONTH_VOTES,
    TOTAL_VOTES,
    VOTE_PASS,

    ACHIEVEMENT_POINTS,
    QUEST_POINTS, //TODO: Přepnout ve finále
    SEASON_POINTS,
    EVENT_POINTS;

    EconomyType() {
    }
}
