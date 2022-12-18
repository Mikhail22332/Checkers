package com.Server;

import com.Data.AbstractChecker;
import com.Data.AbstractFactory;
import com.Data.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private Socket socket;
    private Scanner input;
    private PrintWriter output;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);


    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 58901);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        //TODO delete code below after tests
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        // Send on enter then clear to prepare for next message
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                output.println(textField.getText());
                textField.setText("");
            }
        });

    }

    public void play() throws Exception {
        try {
            var response = input.nextLine();
            System.out.println(response);
            textField.setEditable(true);
            while (input.hasNextLine()) {
                response = input.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    System.out.println("Valid move, please wait");
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    String[] s = response.split("ss");
                    System.out.println(s.length);
                    for(String a : s){
                        System.out.println(a);
                    }
                    System.out.println("Opponent moved, your turn");
                } else if (response.startsWith("MESSAGE")) {
                    System.out.println(response.substring(8));
                } else if (response.startsWith("VICTORY")) {
                    System.out.println("Winner Winner");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    System.out.println("Sorry you lost");
                    break;
                } else if (response.startsWith("TIE")) {
                    System.out.println("Tie");
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    System.out.println("Other player left");
                    break;
                }
            }
            output.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost");
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.play();
    }
}