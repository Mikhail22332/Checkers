package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class clientNetwork {
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private boolean isYourMove = false;

    public clientNetwork() {}

    public boolean IsYourMove() {return isYourMove;}

    public void connectToServer(String ip) throws IOException {
        socket = new Socket(ip, 58901);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
    }
    public void disconnectFromServer() throws IOException {
        output.println("QUIT");
        socket.close();
    }

    public void waitResponse() throws Exception {
        try {
            var response = input.nextLine();
            System.out.println(response);
            while (input.hasNextLine()) {
                response = input.nextLine();
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
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    String s = response.substring(8);
                    System.out.println(s);
                    if(s.startsWith("YOUR MOVE"))
                    {
                        isYourMove = true;
                        break;
                    }
                } else if (response.startsWith("VICTORY")) {
                    System.out.println("Winner Winner");
                    //ToDo some gui to show that info to player
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    System.out.println("Sorry you lost");
                    //ToDo some gui to show that info to player
                    break;
                } else if (response.startsWith("TIE")) {
                    System.out.println("Tie");
                    //ToDo some gui to show that info to player
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    System.out.println("Other player left");
                    //ToDo some gui to show that info to player
                    break;
                }
                System.out.println("inWhile");
            }
            //output.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendMove(String move) throws Exception {
        output.println(move);
        this.waitResponse();
    }
}
