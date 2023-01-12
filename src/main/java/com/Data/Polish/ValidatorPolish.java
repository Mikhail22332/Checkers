package com.Data.Polish;

import com.Data.*;

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
     * @return int value 0,1,2 (illegal move, simple move, capture move) correspondingly
     */
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
                return 0;
            }
        }
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

    /**
     * Method overrides parent's class method according to Polish checker's rules
     * @param board
     * @param move
     */
    @Override
    public void makeMove(Board board, Move move){
        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getY2();
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
     * @return int value 0,1,2 (illegal move, simple move, capture move) correspondingly
     */
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

    /**
     * Checks whether queen's move is legal or not
     * @param board
     * @param move
     * @return int value 0,1,2 (illegal move, simple move, capture move) correspondingly
     */
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

}
