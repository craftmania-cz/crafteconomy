package cz.craftmania.crafteconomy.exceptions;

public class CraftEconomyLoadPlayerDataFailed extends Exception {

    public CraftEconomyLoadPlayerDataFailed(String playerName) {
        super(playerName);
    }
}
