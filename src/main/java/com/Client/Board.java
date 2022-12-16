package com.Client;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board {
    private Color color;
    private Rectangle square;

    private Circle piece;
    private GridPane gridPane;
    private final int size = 8;
    private ArrayList<ArrayList<Integer>> boardArray = new ArrayList<ArrayList<Integer>>();
    private final Integer blackPawn = 1;
    private final Integer whitePawn = 2;
    private final Integer emptySquare = 0;
    public void boardCreation() {
        //setGridPane(gridPane);
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
    }

    public ArrayList<ArrayList<Integer>> getBoardArray(){
        return boardArray;
    }
    public void addPiece(){

        for(int row = 0; row < 8; row++){
            ArrayList<Integer> array = new ArrayList<Integer>();
            for(int column = 0; column < 8; column ++){

                if(column < 3 && (row + column) % 2 != 0){
                    array.add(blackPawn);
                    color = Color.WHEAT;
                    piece = new Circle();
                    piece.setFill(color);
                    gridPane.add(piece, row, column);
                    piece.radiusProperty().bind(gridPane.widthProperty().divide(size*2));
                    piece.radiusProperty().bind(gridPane.heightProperty().divide(size*2));
                } else if (column >= 5 && (row + column) % 2 != 0) {
                    array.add(whitePawn);
                    color = Color.GREY;
                    piece = new Circle();
                    piece.setFill(color);
                    gridPane.add(piece, row, column);
                    piece.radiusProperty().bind(gridPane.widthProperty().divide(size*2));
                    piece.radiusProperty().bind(gridPane.heightProperty().divide(size*2));
                }
                else {
                    array.add(emptySquare);
                }
            }
            boardArray.add(array);
        }
    }
    public GridPane getGridPane(){
        return gridPane;
    }
}
