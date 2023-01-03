package com.Another.Pieces;

import com.Another.*;

import java.util.Set;

public abstract class AbstractPiece implements Piece {
    private Color color;
    private PieceType type;

    public AbstractPiece(PieceType type, Color color){
        this.color = color;
        this.type = type;
    }

    @Override
    public abstract Set<Move> generateMoves(Position position);

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public PieceType getType() {
        return type;
    }

}
