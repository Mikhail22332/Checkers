package com.Another.Pieces;

import com.Another.*;
import javafx.geometry.Pos;

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
        Piece oldPiece = getPieceAt(pos);
        removePiece(oldPiece);
        placePiece(newPiece, pos);
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

        int deltaX = destX - startX;
        int deltaY = destY - startY;

        //boolean inBounds = (destX>=0 && destX < 8 && destY >= 0 && destY < 8);

        Piece movedPiece = getPieceAt(start);
        Piece pieceAtDest = getPieceAt(destination);

        if(Math.abs(deltaX) != Math.abs(deltaY) || Math.abs(deltaX) > 0 || deltaX == 0){
            return false;
        }



        return false;

    }
    private Piece getPieceAt(int x, int y){
        return board[x][y];
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
    private Position middle(Position a, Position b){
        if ( a == null || b == null){
            return new Position(-1, -1);
        }
        return middle(a.getX(), a.getY(), b.getX(), b.getY());
    }
    private Position middle(int x1, int y1, int x2, int y2) {

        // Check coordinates
        int x = x2 - x1, y = y2 - y1;
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || // Not in the board
                x1 > 7 || y1 > 7 || x2 > 7 || y2 > 7) {
            return new Position(-1, -1);
        } else if (x1 % 2 == y1 % 2 || x2 % 2 == y2 % 2) { // white tile
            return new Position(-1, -1);
        } else if (Math.abs(x) != Math.abs(y) || Math.abs(x) != 2) {
            return new Position(-1, -1);
        }

        return new Position(x1 + x / 2, y1 + y / 2);
    }

}
