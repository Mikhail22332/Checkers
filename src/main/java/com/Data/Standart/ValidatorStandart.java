package com.Data.Standart;

import com.Data.*;

public class ValidatorStandart extends AbstractValidator {
    @Override
    protected int validPawnMove(Board board, Move move){
        int startX = move.getX1();
        int startY = move.getY1();
        int endX = move.getX2();
        int endY = move.getY2();
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        int midX = startX + deltaX/2;
        int midY = startY + deltaY/2;
        int direction = playerMark == Color.White ? -1 : 1;
        // Check move with capture
        if(Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2){
            // Check piece which will be captured
            if(board.getField(midX,midY).getPieceColor() != playerMark && board.getField(midX,midY).getPieceType() != (PieceType.Blank)){
                board.setField(new Piece(PieceType.Blank, Color.NoColor), midX, midY);
                System.out.println("Move with capture");
                return 2;
            }
            System.out.println("Not correct checker try to be captured");
            return 0;
        }
        // You must capture
        if(isAnyCapture || isLastMoveCapture) {
            System.out.println("You must capture enemy piece");
            return 0;
        }
        // Check is correct direction
        if(deltaX / Math.abs(deltaX) != direction) {
            System.out.println("Not correct direction");
            return 0;
        }
        // Check move without capture
        if(Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1){
            System.out.println("Good simple move");
            return 1;
        }
        System.out.println("Not correct");
        return 0;
    }
    @Override
    protected int validQueenMove(Board board, Move move){
        int startX = move.getX1();
        int startY = move.getY1();
        int endX = move.getX2();
        int endY = move.getY2();
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
                return 0;
            }
            // Check is there at least one allies piece between start and end
            else if (board.getField(i, j).getPieceColor() == playerMark) {
                return 0;
            }
        }
        if (isPassingEnemy) {
            System.out.println("Enemy was killed at " + enemyX + " " + enemyY);
            board.setField(new Piece(PieceType.Blank, Color.NoColor), enemyX, enemyY);
            return 2;
        }
        if(isAnyCapture || isLastMoveCapture) {
            System.out.println("You must capture enemy piece");
            return 0;
        }
        System.out.println("Queen move is valid");
        return 1;
    }
}
