package com.Controller;

import com.Another.Color;
import com.Another.Move;
import com.Another.Piece;
import com.Another.Position;
import javafx.geometry.Side;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface GameController {
    GameState getState();

    void startGame();

    void startTurn();

    void endTurn();

    Set<Move> getMovesForPieceAt(Position position);
    Map<Piece, Position> getAllActivePositions();
    void makeMove(Move move);

    Color getCurrentPlayer();


}
