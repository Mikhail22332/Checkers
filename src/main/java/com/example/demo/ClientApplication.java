package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientApplication extends Application {
    private static Square[][] squares;

    public static ClientNetwork myNetwork = new ClientNetwork();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        // ToDo editable size for other versions of checkers

        squares = new Square[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a new square and add it to the grid
                Square square = new Square(row, col);
                root.add(square, col, row);
                squares[row][col] = square;

                // Generate pawns at the default positions
                if (row < 3 && (row + col) % 2 == 1) {
                    square.setPawn(new Pawn(row, col, 1, Color.RED));
                } else if (row > 4 && (row + col) % 2 == 1) {
                    square.setPawn(new Pawn(row, col, -1, Color.GREEN));
                }
            }
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Connect to the server in a separate thread
        new Thread(() -> {
            try {
                // Connect to the server
                myNetwork.connectToServer("localhost");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                myNetwork.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }


    public static void main(String[] args)  {
        launch(args);
        //temp.play();
    }

    public static Square getSquare(int row, int col) {
        return squares[row][col];
    }
}

