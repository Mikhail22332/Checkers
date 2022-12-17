package com.example.demo;

import com.Client.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new MainView(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}