package com.Data.Polish;

import com.Data.*;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * ValidatorPolish class inherits from abstract class AbstractValidator.
 * It is used to check the validity of pieces' moves.
 */
public class ValidatorPolish extends AbstractValidator {
    /**
     * Checks whether piece's move is legal or not.
     * @param board
     * @param move
     * @param lastMove
     * @param playerMark
     * @return pair <int, string>. int value 0,1,2 (illegal move, simple move, capture move) correspondingly. String is responsible for warning message
     *
     */
    @Override
    public Pair<Integer, String> isValidMove(Board board, Move move, Move lastMove, Color playerMark){
        this.playerMark = playerMark;

        int startX = move.getStartX();
        int startY = move.getStartY();
        int endX = move.getEndX();
        int endY = move.getEndY();

        isLastMoveCapture = false;
        if(lastMove != null) {
            int lastX = lastMove.getEndX();
            int lastY = lastMove.getEndY();
            isLastMoveCapture = true;
            if(startX != lastX || startY != lastY) {
                return new Pair<>(0, "You can move only one piece, which made capture");
            }
        }

        Piece movingPiece = board.getField(startX, startY);

        Piece pieceAtEnd = board.getField(endX, endY);
        if(movingPiece.getPieceColor() != playerMark) {
            return new Pair<>(0, "Not your checker");
        }
        if(pieceAtEnd.getPieceType() != PieceType.Blank){
            return new Pair<>(0, "You can't move a piece to the occupied cell");
        }
        this.isAnyCapture = findAllPossibleCaptures(board);

        ChainFinder chainFinder = new ChainFinder();
        ArrayList <Move> listOfMoves = chainFinder.getFirstMovesInLongestChain(board, startX, startY);
        if(isAnyCapture) {
            boolean flag = false;
            System.out.println(listOfMoves.size());
            for(Move i : listOfMoves) {
                if(move.isEqual(i)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                return new Pair<>(0, "You must make a move to complete longest beating chain");
            }
        }
        if(movingPiece.getPieceType() == PieceType.Pawn){
            return validPawnMove(board, move);
        }

        if(movingPiece.getPieceType() == PieceType.Queen){
            return validQueenMove(board, move);
        }
        return new Pair<>(0, "Error");
    }

    /**
     * Method overrides parent's class method according to Polish checker's rules
     * @param board
     * @param move
     */
    @Override
    public void makeMove(Board board, Move move){
        moveCounter++;
        int x1 = move.getStartX();
        int y1 = move.getStartY();
        int x2 = move.getEndX();
        int y2 = move.getEndY();
        Piece current = board.getField(x1,y1);
        if(checkPromotion(current, board.getSize(), x2)){
            if(!isAnyPawnCapturePossible(board, x2, y2)) {
                current.setPieceType(PieceType.Queen);
            }
        }
        board.setField(current,x2,y2);
        board.setField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
        System.out.println("Board after move");
        board.printBoard();
    }

    /**
     * Checks whether pawn's move is legal or not
     * @param board
     * @param move
     * @return pair <int, string>. int value 0,1,2 (illegal move, simple move, capture move) correspondingly. String is responsible for warning message
     */
    @Override
    protected Pair<Integer, String> validPawnMove(Board board, Move move){
        int startX = move.getStartX();
        int startY = move.getStartY();
        int endX = move.getEndX();
        int endY = move.getEndY();
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        int midX = startX + deltaX/2;
        int midY = startY + deltaY/2;
        int direction = playerMark == Color.White ? -1 : 1;
        // Check move with capture
        if(Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2){
            // Check piece which will be captured
            if(board.getField(midX,midY).getPieceColor() != playerMark && !board.getField(midX,midY).getPieceType().equals(PieceType.Blank)){
                moveCounter = 0;
                board.setField(new Piece(PieceType.Blank, Color.NoColor), midX, midY);
                return new Pair<>(2, "Valid move with capture");
            }
            return new Pair<>(0, "Not correct field try to be captured");
        }
        // You must capture
        if(isAnyCapture || isLastMoveCapture) {
            return new Pair<>(0,"You must capture enemy piece");
        }
        // Check is correct direction
        if(deltaX / Math.abs(deltaX) != direction) {
            return new Pair<>(0,"Not correct direction");
        }
        // Check move without capture
        if(Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1){
            return new Pair<>(1,"Good simple move");
        }
        return new Pair<>(0,"Not valid move");
    }

    /**
     * Checks whether queen's move is legal or not
     * @param board
     * @param move
     * @return pair <int, string>. int value 0,1,2 (illegal move, simple move, capture move) correspondingly. String is responsible for warning message
     */
    @Override
    protected Pair<Integer, String> validQueenMove(Board board, Move move){
        int startX = move.getStartX();
        int startY = move.getStartY();
        int endX = move.getEndX();
        int endY = move.getEndY();
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        int directionX = deltaX / Math.abs(deltaX);
        int directionY = deltaY / Math.abs(deltaY);
        int enemyX = 0;
        int enemyY = 0;
        boolean isPassingEnemy = false;
        int i = startX;
        int j = startY;
        while(i != endX && j != endY ){
            i += directionX;
            j += directionY;
            if(board.getField(i,j).getPieceColor() == Color.NoColor) {
                continue;
            }
            // Check is there at least one enemy piece between start and end
            if(board.getField(i,j).getPieceColor() != playerMark && !isPassingEnemy){
                isPassingEnemy = true;
                enemyX = i;
                enemyY = j;
            }
            // Check is there more than one enemy piece between start and end
            else if(board.getField(i,j).getPieceColor() != playerMark && isPassingEnemy) {
                return new Pair<>(0,"You try to beat more than one enemy");
            }
            // Check is there at least one allies piece between start and end
            else if(board.getField(i,j).getPieceColor() == playerMark) {
                return new Pair<>(0,"You try to beat allied  checker");
            }
        }

        if (isPassingEnemy) {
            System.out.println("Enemy was killed at " + enemyX + " " + enemyY);
            moveCounter = 0;
            board.setField(new Piece(PieceType.Blank, Color.NoColor), enemyX, enemyY);
            return new Pair<>(2, "Valid move with capture");
        }
        if(isAnyCapture || isLastMoveCapture) {
            return new Pair<>(0,"You must capture enemy piece");
        }
        return new Pair<>(1,"Queen move is valid");
    }
}
