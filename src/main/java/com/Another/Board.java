package com.Another;

import java.util.Map;

public interface Board {

    void movePiece(Piece piece, Move m);

    Piece getPieceAt(Position position);

    void replacePieceAt(Position pos, Piece newPiece);

    boolean pieceCanMove(Move move, Color side);
}
