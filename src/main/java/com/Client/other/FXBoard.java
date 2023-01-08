package com.Client.other;

import javafx.scene.Group;
import javafx.scene.layout.*;
import java.util.ArrayList;

public class FXBoard extends HBox {
    private Piece piece;
    private GridPane gridPane;
    private Group squareGroup = new Group();
    private Group pieceGroup = new Group();
    private final int size = 8;

    private SquareOfField squareOfField = new SquareOfField();
    private int width = squareOfField.getSquareSize()*size;
    private int height = squareOfField.getSquareSize()*size;
    private SquareOfField[][] myBoard = new SquareOfField[size][size];

    private final Integer blackPawn = 1;
    private final Integer whitePawn = 2;
    private final Integer emptySquare = 0;
    private ArrayList<ArrayList<Integer>> boardArray = new ArrayList<ArrayList<Integer>>();

    public FXBoard(){
       setBoardWithPieces();
    }

    private void setBoardWithPieces(){
        boardCreation();
    }

    private GridPane boardCreation() {

        gridPane = new GridPane();
        gridPane.setHgap(size);
        gridPane.setVgap(size);
        gridPane.getChildren().addAll(squareGroup, pieceGroup);

        for (int i = 0; i < size; i++){
            RowConstraints rowConstraints= new RowConstraints(20, gridPane.getWidth()/size, 150);
            rowConstraints.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConstraints);

            ColumnConstraints columnConstraints = new ColumnConstraints(20, gridPane.getHeight()/size, 150);
            columnConstraints.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int row = 0; row < size; row++) {
            ArrayList<Integer> array = new ArrayList<Integer>();
            for (int column = 0; column < size; column++) {
                SquareOfField square = new SquareOfField(row, column, (row + column) % 2 == 0);
                myBoard[row][column] = square;
                squareGroup.getChildren().add(square);
                gridPane.add(square, row, column);
                piece = null;
                square.widthProperty().bind(gridPane.widthProperty().divide(size));
                square.heightProperty().bind(gridPane.heightProperty().divide(size));
                if (column < 3 && (row + column) %2 !=0){
                    piece = createPiece(PieceType.WhitePawn, row, column);
                    array.add(whitePawn);
                } else if (column >= 5 && (row + column) % 2 != 0) {
                    piece = createPiece(PieceType.BlackPawn, row, column);
                    array.add(blackPawn);
                }
                else {
                    array.add(emptySquare);
                }
                if (piece != null) {
                    squareOfField.setPiece(piece);

                    pieceGroup.getChildren().add(piece);
                    gridPane.add(piece, row, column);
                }

            }

            boardArray.add(array);
        }

        return gridPane;
    }
    public int getHeightSize(){
        return height;
    }
    public int getWidthSize(){
        return width;
    }
    public ArrayList<ArrayList<Integer>> getBoardArray(){
        return boardArray;
    }

    private Piece createPiece(PieceType type, int xCord, int yCord){
        Piece piece = new Piece(type, xCord, yCord, gridPane);
        return piece;
    }
    public GridPane getGridPane(){
        return gridPane;
    }


}
