package com.View;

import com.Another.Piece;
import com.Another.Position;
import com.Controller.Game8x8Controller;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Map;

public class BoardView8x8 {
    private GridPane gridPane;
    private Game8x8Controller controller;
    private Tile[][] gameBoard;
    private boolean click = false;
    private final int size = 8;

    public BoardView8x8(Game8x8Controller controller){
        this.controller = controller;
        gameBoard = new Tile[size][size];
        gridPane = new GridPane();

    }

    private void createPieces(){
        gridPane.getChildren().clear();
        Map<Piece, Position> pieces = controller.getAllActivePositions();

        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                Tile cell = new Tile(new Position(r, c));
                gridPane.add(cell, r, c);

                GridPane.setHgrow(cell.getPaneNode(), Priority.ALWAYS);
                GridPane.setVgrow(cell.getPaneNode(), Priority.ALWAYS);

                gameBoard[r][c] = cell;
            }
        }
        //adding pieces
        for(Piece piece : pieces.keySet()){
            Position place = pieces.get(piece);
            getTileAt(place).setSymbol(piece.getType().getSymbol(piece.getColor()));
        }

    }
    private Tile getTileAt(Position pos){
        return gameBoard[pos.getX()][pos.getY()];
    }
}
