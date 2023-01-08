package com.Client.other;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FXMenu extends HBox {
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem connect;
    private MenuItem startGame;
    private MenuItem restartGame;

    public FXMenu(){
        buildMenu();
    }

    private void buildMenu(){
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        connect = new MenuItem("Connect to Server");
        startGame = new MenuItem("Start the Game");
        restartGame = new MenuItem("Restart the Game");

        menu.getItems().addAll(connect, startGame, restartGame);
        menuBar.getMenus().add(menu);
        getChildren().add(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
    }

}
