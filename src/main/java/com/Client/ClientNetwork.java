package com.Client;

import javafx.application.Platform;

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

    /**
     * Method for player, Is it his turn?
     * @return boolean
     */
    public static boolean IsYourMove() {return isYourMove;}

    /**
     * Method for make connection with server
     * @param ip
     * @throws IOException
     */
    public void connectToServer(String ip) throws IOException {
        socket = new Socket(ip, 58901);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connected");
    }

    /**
     * Method to disconnect from server
     * @throws IOException
     */
    public static void disconnectFromServer() throws IOException {
        System.out.println("Disconnected");
        output.println("QUIT");
        socket.close();
    }

    /**
     * Method to get response from server
     * @throws Exception
     */
    public void getResponse() throws Exception {
        try {
            if(input == null)
                return;
            if (input.hasNextLine()) {
                var response = input.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    response = response.substring(11);
                    if(response.startsWith("YOUR_TURN")) {
                        response = response.substring(10);
                        updateBoard(response);
                        System.out.println("Your turn one more");
                        sendAlert("Your turn, one more");
                        isYourMove = true;
                    }
                    if(response.startsWith("WAIT")) {
                        response = response.substring(5);
                        updateBoard(response);
                        System.out.println("Valid move, please wait");
                        sendAlert("Valid move, please wait");
                        isYourMove = false;
                    }
                }
                else if (response.startsWith("NOT_VALID_MOVE")){
                    response = response.substring(15);
                    updateBoard(response);
                    System.out.println("That is not valid move, please make new move");
                    isYourMove = true;
                }
                else if (response.startsWith("OPPONENT_MOVED")) {
                    response = response.substring(15);
                    updateBoard(response);
                    System.out.println("Opponent moved, your turn");
                    isYourMove = true;
                }
                else if (response.startsWith("MESSAGE")) {
                    response = response.substring(8);
                    sendAlert(response);
                    if(response.startsWith("YOUR MOVE"))
                    {
                        isYourMove = true;
                    }
                    if(response.startsWith("WELCOME"))
                    {
                        response = response.substring(14);
                        int size = Integer.parseInt(response);
                        Platform.runLater(() -> ClientApplication.setSize(size));
                    }
                }
                else if (response.startsWith("VICTORY")) {
                    sendAlert(response);
                }
                else if (response.startsWith("DEFEAT")) {
                    sendAlert(response);
                } else if (response.startsWith("TIE")) {
                    sendAlert(response);
                }
                else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    sendAlert(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that call updating of board at application
     * @param board
     */
    private void updateBoard(String board) {
        String[] s = board.split("newline");
        Platform.runLater(() -> ClientApplication.updateSquares(s));
    }

    /**
     * Method to send a completed move to server
     * @param move
     * @throws Exception
     */
    public static void sendMove(String move) throws Exception {
        System.out.println(move);
        output.println(move);
        System.out.println("Sending move");
    }

    /**
     * Method to call Alert with message at application
     * @param message
     */
    private void sendAlert(String message) {
        String finalResponse = message;
        Platform.runLater(() -> ClientApplication.printAlert(finalResponse));
    }
}
