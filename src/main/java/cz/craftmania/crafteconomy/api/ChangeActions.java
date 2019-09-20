package cz.craftmania.crafteconomy.api;

/**
 * Basic change actions for requesting MySQL, internal events and player logs
 */
public enum ChangeActions {

    ECONOMY_REGISTER,

    COINS_ADD,
    COINS_REMOVE,
    COINS_SET,
    COINS_RESET,

    TOKENS_ADD,
    TOKENS_REMOVE,
    TOKENS_SET,
    TOKENS_RESET,

    VOTE_ADD,
    VOTE_REMOVE,
    VOTE_SET,
    VOTE_RESET,

    ChangeActions(){};

    public static ChangeActions[] getTypes() {
        return ChangeActions.values();
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
