package com.Client.other;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends StackPane {
    private PieceType type;
    private SquareOfField squareOfField = new SquareOfField();
    double radius = squareOfField.getSquareSize()*2/10;

    public Piece(PieceType type, int xCord, int yCord, GridPane gridpane){
        this.type = type;
        System.out.println(radius);
        relocate(radius*xCord,radius*yCord);
        Circle piece = new Circle(radius);
        piece.setFill(type == PieceType.BlackPawn ? Color.RED: Color.ANTIQUEWHITE);

        piece.radiusProperty().bind(gridpane.widthProperty().divide(radius));
        piece.radiusProperty().bind(gridpane.heightProperty().divide(radius));
        getChildren().add(piece);
    }

}
