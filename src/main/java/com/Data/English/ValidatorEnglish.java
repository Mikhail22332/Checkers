package com.Data.English;

import com.Data.*;

public class ValidatorEnglish extends AbstractValidator {
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
        // Check is correct direction
        if(deltaX / Math.abs(deltaX) != direction) {
            System.out.println("Not correct direction");
            return 0;
        }
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
                if(i + directionX != endX || j + directionY != endY) {
                    return 0;
                }
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
