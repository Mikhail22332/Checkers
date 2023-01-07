package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CheckersBoard1 extends Application {
    private static Square[][] squares;

    public static clientNetwork myNetwork = new clientNetwork();

    @Override
    public void start(Stage primaryStage) throws Exception {
        myNetwork.connectToServer("localhost");
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
        myNetwork.waitResponse();
    }



    public static void main(String[] args)  {
        launch(args);
        //temp.play();
    }

    public static Square getSquare(int row, int col) {
        return squares[row][col];
    }
}

