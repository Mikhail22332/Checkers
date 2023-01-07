package com.Data;

public class Piece {
    private PieceType pieceType;
    private Color pieceColor;

    public Piece(PieceType pieceType, Color pieceColor){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public PieceType getPieceType(){
        return pieceType;
    }

    public Color getPieceColor(){
        return pieceColor;
    }
    public void setPieceType(PieceType type){
        this.pieceType = type;
    }
    public void setPieceColor(Color color){
        this.pieceColor = color;
    }

}