package com.Data.English;

import com.Data.*;
import javafx.util.Pair;

/**
 * ValidatorEnglish class inherits from abstract class AbstractValidator.
 * It is used to check the validity of pieces' moves.
 */
public class ValidatorEnglish extends AbstractValidator {
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
        // Check is correct direction
        if(deltaX / Math.abs(deltaX) != direction) {
            return new Pair<>(0,"Not correct direction of move");
        }
        // Check move with capture
        if(Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2){
            // Check piece which will be captured
            if(board.getField(midX,midY).getPieceColor() != playerMark && board.getField(midX,midY).getPieceType() != (PieceType.Blank)){
                moveCounter = 0;
                //board.setField(new Piece(PieceType.Blank, Color.NoColor), midX, midY);
                return new Pair<>(2,"Valid move with capture");
            }
            return new Pair<>(0,"Not correct checker try to be captured");
        }
        // You must capture
        if(isAnyCapture || isLastMoveCapture) {
            return new Pair<>(0,"You must capture enemy piece");
        }
        // Check move without capture
        if(Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1){
            return new Pair<>(1,"Good simple move");
        }
        return new Pair<>(0,"Not correct move / Error");
    }

    /**
     * Checks whether queen's move is legal or not
     * @param board
     * @param move
     * @return int value 0,1,2 (illegal move, simple move, capture move) correspondingly
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
            if (board.getField(i, j).getPieceColor() == Color.NoColor) {
                continue;
            }
            // Check if there is at least one enemy piece between start and end
            if (board.getField(i, j).getPieceColor() != playerMark && !isPassingEnemy) {
                isPassingEnemy = true;
                enemyX = i;
                enemyY = j;
                if(i + directionX != endX || j + directionY != endY) {
                    return new Pair<>(0,"You must finish your move at field next to beat enemy cheker");
                }
            }
            // Check if there is more than one enemy piece between start and end
            else if (board.getField(i, j).getPieceColor() != playerMark && isPassingEnemy) {
                return new Pair<>(0,"You try to beat more than one enemy");
            }
            // Check if there is at least one allies piece between start and end
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

    /**
     * Checks whether pawn capture is possible or not.
     * This method is essential because rules in Russian(standart)
     * checkers and English checkers are a bit different.
     * @param board
     * @param startX
     * @param startY
     * @return true if capture is possible forward, false in all other cases
     */
    @Override
    protected boolean isAnyPawnCapturePossible(Board board, int startX, int startY){
        Piece pieceAtStart = board.getField(startX, startY);
        int directionX = 0;
        if(pieceAtStart.getPieceColor() == Color.White){
            directionX = -1;
        }
        if(pieceAtStart.getPieceColor() == Color.Black){
            directionX = 1;
        }
        for(int j = -1; j <= 1; j += 2) {
            int endX = startX + directionX * 2;
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
            Piece pieceAtMiddle = board.getField(midX, midY);
            if(pieceAtMiddle.getPieceType() == PieceType.Blank) {
                continue;
            }
            if(pieceAtMiddle.getPieceColor() != playerMark) {
                return true;
            }
        }
        return false;
    }
}
