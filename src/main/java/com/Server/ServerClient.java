package com.Server;

import com.Data.Color;
import com.Data.GameType;

import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ServerClient {
    /**
     * Main server client class
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                        Choose one type of game (write a number):
                        1 - Russian checkers / Szaszki
                        2 - English checkers
                        3 - Polish checkers""");
        int type = 0;
        // Get type from host
        while (type <= 0 || 4 <= type) {
            try {
                type = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Not correct type");
            }
        }
        // Set connection and make a pair of players
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Checkers Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            Game gameExample = new Game();
            while (true) {
                if(type == 1) {
                    System.out.println("Russian checkers was chosen");
                    gameExample = new Game(GameType.Russian, 8);
                }
                if(type == 2) {
                    System.out.println("English checkers was chosen");
                    gameExample = new Game(GameType.English, 8);
                }
                if(type == 3) {
                    System.out.println("Polish checkers was chosen");
                    gameExample = new Game(GameType.Polish, 10);
                }
                pool.execute(gameExample.new Player(listener.accept(), Color.White));
                pool.execute(gameExample.new Player(listener.accept(), Color.Black));
            }
        }
    }
}
