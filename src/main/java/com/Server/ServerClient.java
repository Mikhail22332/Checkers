package com.Server;

import com.Data.Color;
import com.Data.GameType;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class ServerClient {
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Tic Tac Toe Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game gameExample = new Game(GameType.Standart, 8);
                pool.execute(gameExample.new Player(listener.accept(), Color.White));
                pool.execute(gameExample.new Player(listener.accept(), Color.Black));
            }
        }
    }
}
