package com.Client;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Piece extends StackPane {
    private PieceType type;
    private SquareOfField squareOfField = new SquareOfField();

    public Piece(PieceType type, int xCord, int yCord, GridPane gridpane){
        this.type = type;
        double radius = squareOfField.getSquareSize()*2/10;
        System.out.println(radius);
        relocate(radius*xCord,radius*yCord);
        Circle piece = new Circle(radius);
        piece.setFill(type == PieceType.BlackPawn ? Color.RED: Color.ANTIQUEWHITE);

        piece.radiusProperty().bind(gridpane.widthProperty().divide(radius));
        piece.radiusProperty().bind(gridpane.heightProperty().divide(radius));
        getChildren().add(piece);
    }

}
