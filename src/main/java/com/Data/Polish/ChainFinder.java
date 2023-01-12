package com.Data.Polish;

import com.Data.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChainFinder {
    private Set<Pair<Integer, Integer>> beatedFields = new HashSet<>();
    /**
     * This method was created to write unit tests
     * @param board
     * @param startX
     * @param startY
     * @return maximum possible chain's length
     */
    public int isRecursionPossible(Board board, int startX, int startY) {
        ArrayList<Move> allMoves = getFirstMovesInLongestChain(board, startX, startY);
        return allMoves.size() > 0 ? allMoves.get(0).getStepCounter() : 0;
    }

    // Return all possible first moves for longest beating chain
    /**
     * Method is used to get access to all possible starting positions
     * which can lead to the longest beating chain
     * @param board
     * @param startX
     * @param startY
     * @return ArrayList of moves which are included in the longest possible chains
     */
    public ArrayList<Move> getFirstMovesInLongestChain(Board board, int startX, int startY) {
        beatedFields.clear();
        Piece pieceAtStart = board.getField(startX, startY);
        board.setField(new Piece(PieceType.Blank, Color.NoColor), startX, startY);
        ArrayList<Move> possibleMoves = recursion(board, startX, startY, pieceAtStart, 0);
        board.setField(pieceAtStart, startX, startY);
        return possibleMoves;
    }
    /**
     *
     * @param board
     * @param startX
     * @param startY
     * @param type
     * @param step
     * @return
     */
    private ArrayList<Move>  recursion(Board board, int startX, int startY, Piece type, int step) {
        ArrayList <Move> movesFromStart = getAllPossibleMovesWithCapture(board, startX, startY, type);
        ArrayList <Move> movesThatCanBe = new ArrayList<>();
        System.out.println(startX + " " + startY);
        if(movesFromStart.size() == 0) {
            movesThatCanBe.add(new Move(startX, startY, -1, -1, step));
            return movesThatCanBe;
        }
        int maxChain = 0;
        for(Move currentMove : movesFromStart) {

            Pair<Integer, Integer> beatedField = getBeatField(board, currentMove, type.getPieceColor());
            beatedFields.add(beatedField);
            ArrayList <Move> newMoveList = recursion(board, currentMove.getX2(), currentMove.getY2(), type, step + 1);
            beatedFields.remove(beatedField);
            for(Move moveAfterCurrent : newMoveList) {
                int currentChain = moveAfterCurrent.getStepCounter();
                if(currentChain > currentMove.getStepCounter()) {
                    maxChain = Math.max(maxChain, currentChain);
                    currentMove.setStepCounter(currentChain);
                }
            }
        }
        for(Move currentMove : movesFromStart) {
            if(currentMove.getStepCounter() == maxChain) {
                movesThatCanBe.add(currentMove);
            }
        }
        return movesThatCanBe;
    }

    /**
     * Method to get a pair of coordinates of beaten field of any move
     * @param board
     * @param move
     * @param playerMark
     * @return a pair of int values (x, y) coordinates of beat field
     */
    private Pair<Integer, Integer> getBeatField(Board board, Move move, Color playerMark) {
        int startX = move.getX1(), startY = move.getY1();
        int endX = move.getX2(), endY = move.getY2();
        int deltaX = endX - startX, deltaY = endY - startY;
        int directionX = deltaX / Math.abs(deltaX);
        int directionY = deltaY / Math.abs(deltaY);
        int i = startX, j = startY;
        while(i != endX && j != endY) {
            i += directionX;
            j += directionY;
            if(beatedFields.contains(new Pair(i, j)))
                continue;
            if(board.getField(i,j).getPieceType() == PieceType.Blank)
                continue;
            if(board.getField(i,j).getPieceColor() != playerMark) {
                return new Pair(i,j);
            }
        }
        return null;
    }

    /**
     * @param board
     * @param startX
     * @param startY
     * @param type
     * @return arrayList of all possible capture Moves
     */
    private ArrayList<Move> getAllPossibleMovesWithCapture(Board board, int startX, int startY, Piece type) {
        ArrayList<Move> moveList = new ArrayList<Move>();
        if(type.getPieceType() == PieceType.Pawn) {
            moveList = getAllCapturesForPawn(board, startX, startY, type.getPieceColor());
        }
        if(type.getPieceType() == PieceType.Queen) {
            moveList = getAllCapturesForQueen(board, startX, startY, type.getPieceColor());
        }
        return moveList;
    }

    /**
     * @param board
     * @param startX
     * @param startY
     * @param playerMark
     * @return arrayList of all possible capture Moves for pawn type
     */
    private ArrayList<Move> getAllCapturesForPawn(Board board, int startX, int startY, Color playerMark) {
        ArrayList<Move> moveList = new ArrayList<>();
        for(int i = -1; i <= 1; i += 2) {
            for(int j = -1; j <= 1; j += 2) {
                int endX = startX + i * 2;
                int endY = startY + j * 2;
                boolean outOfBounds = outOfBounds(board, endX, endY);
                if (outOfBounds || Math.abs(endX-startX) != 2 || Math.abs(endY-startY) != 2) {
                    continue;
                }
                Piece pieceAtEnd = board.getField(endX, endY);
                if(pieceAtEnd.getPieceType() != PieceType.Blank){
                    continue;
                }
                int midX = (startX + endX) / 2;
                int midY = (startY + endY) / 2;
                if(beatedFields.contains(new Pair(midX, midY)))
                    continue;
                Piece pieceAtMiddle = board.getField(midX, midY);
                if(pieceAtMiddle.getPieceType() == PieceType.Blank) {
                    continue;
                }
                if(pieceAtMiddle.getPieceColor() != playerMark) {
                    moveList.add(new Move(startX, startY, endX, endY));
                }
            }
        }
        return moveList;
    }

    /**
     *
     * @param board
     * @param startX
     * @param startY
     * @param playerMark
     * @return arrayList of all possible capture Moves for queen type
     */
    private ArrayList<Move> getAllCapturesForQueen(Board board, int startX, int startY, Color playerMark) {
        ArrayList<Move> moveList = new ArrayList<>();
        for(int i = -1; i <= 1; i += 2) {
            for(int j = -1; j <= 1; j += 2) {
                int currentX = startX;
                int currentY = startY;
                while(true) {
                    currentX += i;
                    currentY += j;
                    if(outOfBounds(board, currentX + i, currentY + j))
                        break;
                    if(beatedFields.contains(new Pair(currentX, currentY)))
                        continue;
                    Piece currentPiece = board.getField(currentX, currentY);
                    if(currentPiece.getPieceType() == PieceType.Blank) {
                        continue;
                    }
                    if(currentPiece.getPieceColor() != playerMark) {
                        int endX = currentX;
                        int endY = currentY;
                        while(!outOfBounds(board, endX + i, endY + j)) {
                            endX += i;
                            endY += j;
                            Piece endPiece = board.getField(endX, endY);
                            if (endPiece.getPieceType() == PieceType.Blank) {
                                moveList.add(new Move(startX, startY, endX, endY));
                            } else {
                                break;
                            }
                        }
                        break;
                    }
                    if(currentPiece.getPieceColor() == playerMark) {
                        break;
                    }
                }
            }
        }
        return moveList;
    }

    /**
     * @param board
     * @param x
     * @param y
     * @return true if out of bounds, false if in bounds
     */
    private boolean outOfBounds(Board board, int x, int y){
        return !(x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize());
    }
}
