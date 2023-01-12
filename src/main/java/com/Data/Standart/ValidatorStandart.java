package com.Data.Standart;

import com.Data.*;
import javafx.util.Pair;

/**
 * ValidatorEnglish class inherits from abstract class AbstractValidator.
 * It is used to check the validity of pieces' moves.
 */
public class ValidatorStandart extends AbstractValidator {
    /**
     * Checks whether pawn's move is legal or not
     * @param board
     * @param move
     * @return pair <int, string>. int value 0,1,2 (illegal move, simple move, capture move) correspondingly. String is responsible for warning message
     */
    @Override
    protected Pair<Integer, String> validPawnMove(Board board, Move move) {
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
            if(board.getField(midX,midY).getPieceColor() != playerMark && board.getField(midX,midY).getPieceType() != (PieceType.Blank)){
                moveCounter = 0;
                //board.setField(new Piece(PieceType.Blank, Color.NoColor), midX, midY);
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
     *  Checks whether queen's move is legal or not
     * @param board
     * @param move
     * @return pair <int, string>. int value 0,1,2 (illegal move, simple move, capture move) correspondingly. String is responsible for warning message
     */
    @Override
    protected Pair<Integer, String> validQueenMove(Board board, Move move) {
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
            if (board.getField(i, j).getPieceColor() == Color.NoColor) {
                continue;
            }
            // Check is there at least one enemy piece between start and end
            if (board.getField(i, j).getPieceColor() != playerMark && !isPassingEnemy) {
                isPassingEnemy = true;
                enemyX = i;
                enemyY = j;
            }
            // Check is there more than one enemy piece between start and end
            else if (board.getField(i, j).getPieceColor() != playerMark && isPassingEnemy) {
                return new Pair<>(0,"You try to beat more than one enemy");
            }
            // Check is there at least one allies piece between start and end
            else if (board.getField(i, j).getPieceColor() == playerMark) {
                return new Pair<>(0,"You try to beat allied  checker");
            }
        }
        if (isPassingEnemy) {
            System.out.println("Enemy was killed at " + enemyX + " " + enemyY);
            moveCounter = 0;
            //board.setField(new Piece(PieceType.Blank, Color.NoColor), enemyX, enemyY);
            return new Pair<>(2, "Valid move with capture");
        }
        if(isAnyCapture || isLastMoveCapture) {
            return new Pair<>(0,"You must capture enemy piece");
        }
        return new Pair<>(1,"Queen move is valid");
    }
}
