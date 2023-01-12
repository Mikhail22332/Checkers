package com.Data;

/**
 * Class Piece is used to create and modify pieces
 */
public class Piece {
    private PieceType pieceType;
    private Color pieceColor;

    /**
     * Class constructor
     * @param pieceType
     * @param pieceColor
     */
    public Piece(PieceType pieceType, Color pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }
    /**
     * @return piece type
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * @return piece color
     */
    public Color getPieceColor() {
        return pieceColor;
    }

    /**
     * Sets new piece type, used in case of promotion
     * @param type
     */
    public void setPieceType(PieceType type) {
        this.pieceType = type;
    }
}