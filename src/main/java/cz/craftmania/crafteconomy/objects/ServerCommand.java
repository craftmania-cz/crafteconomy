package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.utils.ServerType;

public class ServerCommand {

    private ServerType serverType;
    private String command;

    public ServerCommand(ServerType serverType, String command) {
        this.serverType = serverType;
        this.command = command;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public String getCommand() {
        return command;
    }
}
