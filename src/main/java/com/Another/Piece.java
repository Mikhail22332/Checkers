package com.Another;

import java.util.Set;

public interface Piece {

    Set<Move> generMoves(Position position);
    Color getColor();

    PieceType getType();
}
