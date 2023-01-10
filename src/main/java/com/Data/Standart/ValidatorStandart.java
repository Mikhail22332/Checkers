package com.Data.Standart;

import com.Data.*;

public class ValidatorStandart extends AbstractValidator {
    private Color playerMark = Color.NoColor;
    private boolean isAnyCapture = false;
    private boolean isLastMoveCapture = false;
    //0 -impossible move, 1 - simple move, 2- capture
    @Override
    public int isValidMove(Board board, Move move, Move lastMove, Color playerMark){

        this.playerMark = playerMark;

        int startX = move.getX1();
        int startY = move.getY1();
        int endX = move.getX2();
        int endY = move.getY2();

        isLastMoveCapture = false;
        if(lastMove != null) {
            int lastX = lastMove.getX2();
            int lastY = lastMove.getY2();
            isLastMoveCapture = true;
            if(startX != lastX || startY != lastY) {
                System.out.println("You can move only one piece, which made capture");
                return 0;
            }
        }

        Piece movingPiece = board.getField(startX, startY);

        Piece pieceAtEnd = board.getField(endX, endY);
        if(movingPiece.getPieceColor() != playerMark) {
            System.out.println("Not your checker");
            return 0;
        }
        if(pieceAtEnd.getPieceType() != PieceType.Blank){
            System.out.println("You can't move a piece to the occupied cell.");
            return 0;
        }
        this.isAnyCapture = findAllPossibleCaptures(board);
        if(movingPiece.getPieceType() == PieceType.Pawn){
            System.out.println("Check pawn move");
            return validPawnMove(board, move);
        }

        if(movingPiece.getPieceType() == PieceType.Queen){
            System.out.println("Check queen move");
            return validQueenMove(board, move);
        }
        return 0;
    }
    @Override
    public void makeMove(Board board, Move move){
        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getY2();
        Piece current = board.getField(x1,y1);
        if(checkPromotion(current, board.getSize(), x2)){
            current.setPieceType(PieceType.Queen);
        }
        board.setField(current,x2,y2);
        board.setField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
        System.out.println("Board after move");
        board.printBoard();
    }

    @Override
    public void setPlayerMark(Color playerMark) {
        this.playerMark = playerMark;
    }

    private boolean outOfBounds(Board board, int x, int y){
        return !(x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize());
    }
    private int validPawnMove(Board board, Move move){
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
            if(board.getField(midX,midY).getPieceColor() != playerMark && !board.getField(midX,midY).getPieceType().equals(PieceType.Blank)){
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
    private int validQueenMove(Board board, Move move){
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
                return 0;
            }
            // Check is there at least one allies piece between start and end
            else if(board.getField(i,j).getPieceColor() == playerMark) {
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

    private boolean isAnyPawnCapturePossible(Board board, int startX, int startY){
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
                Piece pieceAtMiddle = board.getField(midX, midY);
                if(pieceAtMiddle.getPieceType() == PieceType.Blank) {
                    continue;
                }
                if(pieceAtMiddle.getPieceColor() != playerMark) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isAnyQueenCapturePossible(Board board, int startX, int startY) {
        for(int i = -1; i <= 1; i += 2) {
            for(int j = -1; j <= 1; j += 2) {
                int currentX = startX;
                int currentY = startY;
                while(true) {
                    currentX += i;
                    currentY += j;
                    if(outOfBounds(board, currentX + i, currentY + j))
                        break;
                    Piece currentPiece = board.getField(currentX, currentY);
                    if(currentPiece.getPieceType() == PieceType.Blank) {
                        continue;
                    }
                    if(currentPiece.getPieceColor() != playerMark) {
                        int endX = currentX + i;
                        int endY = currentY + j;
                        Piece endPiece = board.getField(endX, endY);
                        if(endPiece.getPieceType() == PieceType.Blank) {
                            return true;
                        }
                        else{
                            break;
                        }
                    }
                    if(currentPiece.getPieceColor() == playerMark) {
                        break;
                    }
                }
            }
        }
        return false;
    }
    private boolean findAllPossibleCaptures(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++){
                if(isAnyCaptureForThatField(board, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isAnyCaptureForThatField(Board board, int startX, int startY) {
        Piece movingPiece = board.getField(startX, startY);
        if(movingPiece.getPieceColor() != playerMark) {
            return false;
        }
        if(movingPiece.getPieceType() == PieceType.Pawn) {
            if(isAnyPawnCapturePossible(board, startX, startY)) {
                return true;
            }
        }
        if(movingPiece.getPieceType() == PieceType.Queen) {
            if(isAnyQueenCapturePossible(board, startX, startY)) {
                return true;
            }
        }
        return false;
    }
}
