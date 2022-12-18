package com.Server;

public class Command {
    public static final String COMMAND_STARTGAME = "STARTGAME";
    public static final String COMMAND_UPDATE = "UPDATE";
    public static final String COMMAND_CONNECT = "CONNECT";
    public static final String COMMAND_DISCONNECT = "DISCONNECT";

    private String command;
    private String[] data;

    public Command(String command, String... data) {
        this.command = command;
        this.data = data;
    }

    public String Send(String host, int port){
        String response = "";
        return response;
    }
}
