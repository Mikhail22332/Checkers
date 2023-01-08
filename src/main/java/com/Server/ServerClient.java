package com.Server;

import com.Data.Color;
import com.Data.GameType;

import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ServerClient {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        /*System.out.println("Enter server port: ");
        int port = 00000;
        port = sc.nextInt();*/
        System.out.println("""
                        Choose one type of game (write a number):
                        1 - Russian checkers / Szaszki
                        2 - English checkers
                        3 - Some type
                        """);
        int type = 0;
        while (type <= 0 || 4 <= type) {
            try {
                type = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Not correct type");
            }
        }
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Checkers Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game gameExample = new Game();
                if(type == 1)
                     gameExample = new Game(GameType.Russian, 8);
                if(type == 2)
                    gameExample = new Game(GameType.English, 8);
                if(type == 3)
                    gameExample = new Game(GameType.SomeType2, 10);
                pool.execute(gameExample.new Player(listener.accept(), Color.White));
                pool.execute(gameExample.new Player(listener.accept(), Color.Black));
            }
        }
    }
}
