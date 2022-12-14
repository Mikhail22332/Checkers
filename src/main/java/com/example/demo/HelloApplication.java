package com.example.demo;

import com.Client.Board;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Board board = new Board();
        board.boardCreation(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}