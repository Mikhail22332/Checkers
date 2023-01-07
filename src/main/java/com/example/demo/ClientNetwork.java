package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientNetwork {
    private static Socket socket;
    public static Scanner input;
    private static PrintWriter output;

    private static boolean isYourMove = false;

    public ClientNetwork() {}

    public static boolean IsYourMove() {return isYourMove;}

    public void connectToServer(String ip) throws IOException {
        socket = new Socket(ip, 58901);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connected");
    }
    public static void disconnectFromServer() throws IOException {
        System.out.println("Disconnected");
        output.println("QUIT");
        socket.close();
    }

    public static void getResponse() throws Exception {
        try {
            if(input == null)
                return;
            if (input.hasNextLine()) {
                var response = input.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    System.out.println("Valid move, please wait");
                    isYourMove = false;
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    String[] s = response.split("ss");
                    System.out.println(s.length);
                    for(String a : s){
                        System.out.println(a);
                    }
                    System.out.println("Opponent moved, your turn");
                    isYourMove = true;
                } else if (response.startsWith("MESSAGE")) {
                    String s = response.substring(8);
                    System.out.println(s);
                    if(s.startsWith("YOUR MOVE"))
                    {
                        isYourMove = true;
                    }
                } else if (response.startsWith("VICTORY")) {
                    System.out.println("Winner Winner");
                    //ToDo some gui to show that info to player
                } else if (response.startsWith("DEFEAT")) {
                    System.out.println("Sorry you lost");
                    //ToDo some gui to show that info to player
                } else if (response.startsWith("TIE")) {
                    System.out.println("Tie");
                    //ToDo some gui to show that info to player
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    System.out.println("Other player left");
                    //ToDo some gui to show that info to player
                }
                System.out.println("TryToGetResponse");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendMove(String move) throws Exception {
        System.out.println(move);
        output.println(move);
        System.out.println("Sending move");
    }
}
