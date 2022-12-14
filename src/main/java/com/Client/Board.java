package com.Client;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Board {
    private Color color;
    private Rectangle square;
    private GridPane gridPane;
    private final int size = 8;
    public void boardCreation(Stage stage) {
        gridPane = new GridPane();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                square = new Rectangle();
                if ((row + column) % 2 == 0)
                    color = Color.WHITE;
                else {
                    color = Color.BLACK;
                }
                square.setFill(color);
                gridPane.add(square, column, row);

                square.widthProperty().bind(gridPane.widthProperty().divide(size));
                square.heightProperty().bind(gridPane.heightProperty().divide(size));
            }
        }
        stage.setScene(new Scene(gridPane, 400, 400));

    }
}
