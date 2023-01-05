package com.Another.Pieces;

import com.Another.*;
import javafx.geometry.Side;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard8x8Classic implements Board {
    private Map<Piece, Position> whiteLocation;
    private Map<Piece, Position> blackLocation;
    private Piece[][] board;
    private final int size = 8;
    private Piece last;


    public GameBoard8x8Classic(){
        whiteLocation = new HashMap<>();
        blackLocation = new HashMap<>();
        board = new Piece[size][size];

        for (Color sideColor :Color.values()) {

            int row1 = -1;
            int row2 = -1;
            int row3 = -1;

            if(sideColor.equals(Color.Black)){
                row1 = 0;
                row2 = 1;
                row3 = 3;
            }
            else{
                row1 = 5;
                row2 = 6;
                row3 = 7;
            }
            //placing pieces
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {

                    if (column < 3 && (row + column) % 2 == 1) {
                        placePiece(new Pawn(Color.Black), new Position(row, column));
                    }
                    if (column >= 5 && (row + column) % 2 != 0) {
                        placePiece(new Pawn(Color.White), new Position(row, column));
                    }
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
    public Map<Piece, Set<Move>> generateAllMovesForSide(Color color) {



        Map<Piece, Set<Move>> allMoves = new HashMap<>();
        Map<Piece, Position> piecePosition;

        if (color == Color.White){
            piecePosition = whiteLocation;
        }
        else{
            piecePosition = blackLocation;
        }
        //generate all possible moves
        for(Piece piece: piecePosition.keySet()){
            Set<Move> moves = piece.generateMoves(piecePosition.get(piece));

            Set<Move> filtered = moves.stream().filter(m->pieceCanMove(m, color)).collect(Collectors.toCollection(HashSet<Move>::new));

            allMoves.put(piece, filtered);
        }
        return allMoves;
    }

    @Override
    public Map<Piece, Position> getAllActivePiecesPositions() {
        Map<Piece, Position> all = new HashMap<>();
        all.putAll(whiteLocation);
        all.putAll(blackLocation);
        return all;
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
        Position start = move.getStart();
        Position destination = move.getDestination();

        Piece movedPiece = getPieceAt(start);
        Piece pieceAtDest = getPieceAt(destination);

        Color colorMovedPiece = movedPiece.getColor();
        Color colorPieceAtDest = pieceAtDest.getColor();

        int destX = destination.getX();
        int destY = destination.getY();

        int startX = start.getX();
        int startY = start.getY();

        int deltaX = destX - startX;
        int deltaY = destY - startY;

        boolean inBounds = outOfBounds(destX, deltaY);
        boolean sameColor = colorMovedPiece.equals(colorPieceAtDest);

        //out of board
        if(Math.abs(deltaX) != Math.abs(deltaY) || deltaX == 0 || !inBounds){
            return false;
        }
        //if two pieces from the same team near each other
        else if(sameColor){
            return false;
        }

        //possible capture move
        else if(mustCapture(color)){
            return false;
        }
        else{
            return true;
        }

    }
    public boolean mustCapture(Color side){

        Map<Piece,Position> piecePositions;
        //set depends on color
        if (side.equals(Color.Black)){
            piecePositions = blackLocation;
        }
        else{
            piecePositions = whiteLocation;
        }

        //TODO add logic for quene

        Iterator<Map.Entry<Piece, Position>> pieceIterator = piecePositions.entrySet().iterator();
        //iterate throw all pieces in set
        while (pieceIterator.hasNext()){
            Map.Entry<Piece, Position> pieceMap = pieceIterator.next();

            Position piecePosition = pieceMap.getValue();

            int x = piecePosition.getX();
            int y = piecePosition.getY();

            //check cells around
            for(int deltaX = -1; deltaX <= 1; deltaX++){
                for (int deltaY = -1; deltaY <= 1; deltaY++){
                    int underAttackX = x + deltaX;
                    int underAttackY = y + deltaY;

                    boolean inBounds = outOfBounds(underAttackX, underAttackY);

                    if((deltaX != 0 && deltaY != 0) && inBounds){

                        Piece underAttackPiece = getPieceAt(underAttackX, underAttackY);
                        //check piece next to our piece
                        if(underAttackPiece != null && !underAttackPiece.getColor().equals(side))
                        {

                            int cellBehindX = underAttackX + deltaX;
                            int cellBehindY = underAttackY + deltaY;
                            boolean onBoard = outOfBounds(cellBehindX, cellBehindY);

                            //cell must be empty
                            if (onBoard && getPieceAt(new Position(cellBehindX, cellBehindY)) == null){
                                return true;
                            }
                            else{
                                return false;
                            }
                        }
                    }
                }
            }
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

    private boolean outOfBounds(int x, int y){
        if (x>=0 && x < 8 && y >= 0 && y < 8){
            return true;
        }
        else{
            return false;
        }
    }
}
