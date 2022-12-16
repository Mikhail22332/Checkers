package com.example.demo;

import com.Client.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Board board = new Board();
        board.boardCreation();
        board.addPiece();
        GridPane gridPane = board.getGridPane();
        stage.setScene(new Scene(gridPane, 400, 400));
        stage.setTitle("Checkers");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}