package com.View;

import com.Another.Move;
import com.Another.Piece;
import com.Another.PieceType;
import com.Another.Position;
import com.Controller.Game8x8Controller;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Map;

public class BoardView8x8 {
    private GridPane gridPane;
    private Game8x8Controller controller;
    private Tile[][] gameBoard;
    private final int size = 8;

    public BoardView8x8(Game8x8Controller controller){
        this.controller = controller;
        gameBoard = new Tile[size][size];
        gridPane = new GridPane();
        reset(controller);
    }



    public void updateView(Move move, List<Position> capturedPositions){
        Color color = Color.ANTIQUEWHITE;
        Position start = move.getStart();
        Position end = move.getDestination();

        for(Tile[] tileRow: gameBoard){
            for (Tile tile: tileRow){
                Position current = tile.getPosition();
                getTileAt(start).highlight(color);
                getTileAt(end).highlight(color);
                tile.setSymbol(controller.getSymbolForPieceAt(current));
            }
        }
    }
    public void reset(Game8x8Controller newController){
        controller = newController;



        createPieces();
        controller.startGame();
    }
    private PieceType handlePromotionToQuene(){
        PieceType quene = PieceType.Quene;
        return quene;
    }
    private void createPieces(){
        gridPane.getChildren().clear();
        Map<Piece, Position> pieces = controller.getAllActivePositions();

        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){

                Tile tile = new Tile(new Position(r, c));
                gridPane.add(tile.getPaneNode(), tile.getPosition().getX(), tile.getPosition().getY());

                gridPane.setHgrow(tile.getPaneNode(), Priority.ALWAYS);
                gridPane.setVgrow(tile.getPaneNode(), Priority.ALWAYS);

                gameBoard[r][c] = tile;
                tile.clear();
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



    public Pane getView(){
        return gridPane;
    }

}
