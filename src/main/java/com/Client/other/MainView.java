package com.Client.other;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    private Stage stage;
    private BorderPane root;
    private Scene scene;

    private FXMenu fxMenu;
    private FXBoard board;

    public MainView(Stage stage){
        this.stage = stage;
        buildGui();
    }

    private void buildGui(){
        root = new BorderPane();

        fxMenu = new FXMenu();
        VBox vbox = new VBox(fxMenu);
        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        root.setTop(vbox);

        board = new FXBoard();
        GridPane gridPane = board.getGridPane();
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        root.setCenter(gridPane);

        scene = new Scene(root, board.getWidthSize(), board.getHeightSize());
        stage.setScene(scene);
        stage.setTitle("Checkers");
        stage.show();
    }

}
