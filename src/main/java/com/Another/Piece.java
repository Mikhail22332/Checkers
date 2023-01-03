package com.Another;

import java.util.Set;

public interface Piece {

    Set<Move> generateMoves(Position position);
    Color getColor();

    PieceType getType();

}
