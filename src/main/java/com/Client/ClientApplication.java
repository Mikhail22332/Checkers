package com.Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientApplication extends Application {
    public static Square[][] squares;

    public static ClientNetwork myNetwork = new ClientNetwork();

    private static int size = 8;
    private static GridPane root;

    public static void setSize(int newSize) {
        size = newSize;
        Platform.runLater(() ->  addSquares(root));
    }

    @Override
    public void start(Stage primaryStage) {
        //ToDo try to find a better way to resize squares and lock min size of grid
        // root.setMinSize(300, 300);
        root = new GridPane();
        root.setPrefSize(600, 600);
        // Add a listener to the width and height properties of the scene
        root.widthProperty().addListener((observable, oldValue, newValue) -> updateSquareSizes(newValue.doubleValue(), root.getHeight()));
        root.heightProperty().addListener((observable, oldValue, newValue) -> updateSquareSizes(root.getWidth(), newValue.doubleValue()));

        Label enterIp = new Label("enter ip");
        GridPane.setHalignment(enterIp, HPos.CENTER);
        root.add(enterIp, size + 1, 0);
        TextField tf1 = new TextField("localhost");
        GridPane.setHalignment(tf1, HPos.CENTER);
        root.add(tf1, size + 1, 1);

        Button connect = new Button("connect");
        GridPane.setHalignment(connect, HPos.CENTER);
        connect.setOnAction(event -> {
            // Connect to the server in a separate thread
            new Thread(() -> {
                try {
                    // Connect to the server
                    String s = tf1.getText();
                    myNetwork.connectToServer(s);
                    myNetwork.getResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
        root.add(connect, size + 1,2);

        Button startGame = new Button("start game");
        GridPane.setHalignment(startGame, HPos.CENTER);
        startGame.setOnAction(event -> {
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
        });
        root.add(startGame,size + 1,3);


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // Fill gridPane with squares
    private static void addSquares(GridPane root) {
        squares = new Square[size][size];

        System.out.println("Try to update field");
        Label enterIp = new Label();
        TextField tf1 = new TextField();
        Button connect = new Button();
        Button startGame = new Button();
        for(Node node : root.getChildren()){
            if(node.getClass() == new Label().getClass()) {
                enterIp = (Label) node;
            }
            if(node.getClass() == new TextField().getClass()){
                tf1 = (TextField) node;
            }
            if(node.getClass() == new Button().getClass()){
                Button temp = (Button) node;
                if(temp.getText() == "connect") {
                    connect = temp;
                }
                if(temp.getText() == "start game") {
                    startGame = temp;
                }
            }
        }
        root.getChildren().clear();
        System.out.println("was cleared");
        root.add(enterIp, size, 0);
        root.add(tf1, size, 1);
        root.add(connect, size, 2);
        root.add(startGame, size, 3);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Create a new square and add it to the grid
                Square square = new Square(row, col);
                root.add(square, col, row);
                squares[row][col] = square;

                // Generate pawns at the default positions
                if (row < (size == 8 ? 3 : 4) && (row + col) % 2 == 1) {
                    square.setPawn(new Pawn(row, col, Color.BLACK));
                } else if (row > (size == 8 ? 4 : 5) && (row + col) % 2 == 1) {
                    square.setPawn(new Pawn(row, col, Color.WHITE));
                }
            }
        }
        updateSquareSizes(root.getWidth(), root.getHeight());
    }
    // Method to update board
    public static void updateSquares(String[] s) {
        for(int i = 0; i < size; i++){
            String[] fields = s[i].split(",");
            for(int j = 0; j < size; j++) {
                int temp = Integer.parseInt(fields[j]);
                // Set null
                if(temp == 0) {
                    getSquare(i,j).setPawn(null);
                    continue;
                }
                Pawn current = new Pawn(i, j);
                if(temp % 2 == 0) {
                    current.makeQueen();
                }
                if(temp < 3) {
                    current.setColor(Color.WHITE);
                }
                else if(temp < 5) {
                    current.setColor(Color.BLACK);
                }
                getSquare(i,j).setPawn(current);
            }
        }
        updateSquareSizes(root.getWidth(), root.getHeight());
    }
    private static void updateSquareSizes(double width, double height) {
        width -= 100;
        double mi = Math.min(width, height);
        for (Node node : root.getChildren()) {
            if (node.getClass() == new Square(0,0).getClass()) {
                // This is the square at the specified row and column
                //System.out.println(node.getClass());
                Square square = (Square) node;
                square.setSize(mi / size);
            }
        }
    }
    public static Square getSquare(int row, int col) {
        return squares[row][col];
    }
    public static void main(String[] args)  {
        launch(args);
    }

}

