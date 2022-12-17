package com.Client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareOfField extends Rectangle {
    private Piece piece;

    private int squareSize = 100;

    public Piece getPiece(){
        return piece;
    }
    public SquareOfField(){}
    public SquareOfField(int xCord, int yCord, boolean black){
        setWidth(squareSize);
        setHeight(squareSize);
        relocate(xCord*squareSize, yCord*squareSize);

        if (black){
            setFill(Color.WHITE);
        }
        else{
            setFill(Color.BLACK);
        }
    }
    public int getSquareSize(){
        return squareSize;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
}
