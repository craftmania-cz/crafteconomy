package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.utils.ServerType;

/**
 * Classa používaný pro VotePass příkazy, které se spouští
 * na určitých serverech. Př. Crate Key pro Skyblock a Survival
 */
public class ServerCommand {

    private ServerType serverType;
    private String command;

    public ServerCommand(ServerType serverType, String command) {
        this.serverType = serverType;
        this.command = command;
    }

    /**
     * Vrací nastavený ServerType, tedy kde se spustí příkaz
     * @return {@link ServerType}
     */
    public ServerType getServerType() {
        return serverType;
    }

    /**
     * Příkaz co se spustí v konzoli
     * @return {@link String}
     */
    public String getCommand() {
        return command;
    }
}
