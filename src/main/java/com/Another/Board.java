package com.Another;

import java.util.Map;
import java.util.Set;

public interface Board {

    void movePiece(Piece piece, Move m);
    Map<Piece, Set<Move>> generateAllMovesForSide(Color color);
    Map<Piece, Position> getAllActivePiecesPositions();

    Piece getPieceAt(Position position);

    void replacePieceAt(Position pos, Piece newPiece);

    boolean pieceCanMove(Move move, Color side);
}
