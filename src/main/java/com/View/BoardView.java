package com.View;

import com.Another.Piece;
import com.Another.Position;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class BoardView {
    private GridPane gridPane;
    private Tile[][] gameBoard;
    private boolean click = false;
    private final int size = 8;

    public BoardView(){
        gameBoard = new Tile[size][size];
        gridPane = new GridPane();
    }

    private void createPieces(){
        gridPane.getChildren().clear();

        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                Tile cell = new Tile(new Position(r, c));
                gridPane.add(cell, r, c);
                GridPane.setHgrow(cell.getPaneNode(), Priority.ALWAYS);
                GridPane.setVgrow(cell.getPaneNode(), Priority.ALWAYS);
                gameBoard[r][c] = cell;
            }
        }


    }

    private Tile getTileAt(Position pos){
        return gameBoard[pos.getX()][pos.getY()];
    }
}
