package com.Another.Pieces;

import com.Another.*;
import java.util.HashMap;
import java.util.Map;

public class GameBoard implements Board {
    private Map<Piece, Position> whiteLocation;
    private Map<Piece, Position> blackLocation;
    private Piece[][] board;
    private final int size = 8;
    private Piece last;

    public GameBoard(){
        whiteLocation = new HashMap<>();
        blackLocation = new HashMap<>();
        board = new Piece[size][size];

        //placing pieces
        for (int column = 0; column < size; column++) {
            for (int  row= 0;  row< size; row++) {
                if (column < 3 && (row + column) %2 == 1){
                    placePiece(new Pawn(Color.White), new Position(row, column));
                }if (column >= 5 && (row + column) % 2 != 0) {
                    placePiece(new Pawn(Color.Black), new Position(row, column));
                }
            }
        }

    }

    private void placePiece(Piece piece, Position position){
        if (piece.getColor().equals(Color.Black))
            blackLocation.put(piece, position);
        else {
            whiteLocation.put(piece,position);
        }
        board[position.getX()][position.getY()] = piece;
    }
    private void removePiece(Piece piece){
        Position position;
        if(piece.getColor().equals(Color.Black)){
            position = blackLocation.get(piece);
            blackLocation.remove(piece);
        }else{
            position = whiteLocation.get(piece);
            whiteLocation.remove(piece);
        }
        board[position.getX()][position.getY()] = null;
    }

    @Override
    public void movePiece(Piece piece, Move move) {
        //TODO promotion pawn check


        Position destination = move.getDestination();

        //checks whether move or kill
        Piece pieceDestination = getPieceAt(destination.getX(), destination.getY());


        if(move instanceof Capture && ((Capture) move).isCapture()){
            pieceDestination = getPieceAt(((Capture) move).getPositionCapture());
        }
        if (pieceDestination != null){
            removePiece(pieceDestination);
        }

        removePiece(piece);
        placePiece(piece, destination);
    }
    @Override
    public Piece getPieceAt(Position position) {
        return board[position.getX()][position.getY()];
    }

    @Override
    public void replacePieceAt(Position pos, Piece newPiece) {

    }

    @Override
    public boolean pieceCanMove(Move move, Color color) {
        //positions of start->end points
        Position destination = move.getDestination();
        int destX = destination.getX();
        int destY = destination.getY();
        Position start = move.getStart();
        int startX = destination.getX();
        int startY = destination.getY();

        Piece movedPiece = getPieceAt(start);
        Piece pieceAtDest = getPieceAt(destination);
        boolean possibleCapture = false;
        if (movedPiece != null &&
                pieceAtDest != null &&
                Math.abs(destX - startX) == 1 &&
                Math.abs(destY - startY) == 1 ){
            //check cell behind cell where piece is located
        }
        //TODO add move condition and capture condition

        return true;

    }
    private Piece getPieceAt(int x, int y){
        return board[x][y];
    }

}
