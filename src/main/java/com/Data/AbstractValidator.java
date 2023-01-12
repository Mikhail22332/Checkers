package com.Data;

import javafx.util.Pair;

public abstract class AbstractValidator {
    protected Color playerMark = Color.NoColor;
    public void setPlayerMark(Color playerMark) {
        this.playerMark = playerMark;
    }
    protected boolean isAnyCapture = false;
    protected boolean isLastMoveCapture = false;
    protected int moveCounter = 0;
    public boolean checkForNextMove(Board board, int startX, int startY) {return isAnyCaptureForThatField(board, startX, startY);}
    // Return 0 - impossible move, 1 - simple move, 2 - move with capture
    public Pair<Integer, String> isValidMove(Board board, Move move, Move lastMove, Color playerMark) {
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
        if(movingPiece.getPieceType() == PieceType.Pawn){
            return validPawnMove(board, move);
        }

        if(movingPiece.getPieceType() == PieceType.Queen){
            return validQueenMove(board, move);
        }
        return new Pair<>(0, "Error");
    }
    // Check is valid move, if that piece was a pawn
    protected Pair<Integer, String> validPawnMove(Board board, Move move) {return new Pair<>(0,"");}
    // Check is valid move, if that piece was a queen
    protected Pair<Integer, String> validQueenMove(Board board, Move move) {return new Pair<>(0,"");}
    // Release a move
    public void makeMove(Board board, Move move) {
        moveCounter++;
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
    // Used at makeMove
    // Check if the piece, which made a move, need a promotion
    protected boolean checkPromotion(Piece current, int size, int x2) {
        if(current.getPieceType() != PieceType.Pawn) {return false;}
        if(x2 == 0 && current.getPieceColor() == Color.White){return true;}
        return x2 == size - 1 && current.getPieceColor() == Color.Black;
    }
    // Check is position (x,y) is out of bounds of board
    protected boolean outOfBounds(Board board, int x, int y){
        return !(x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize());
    }
    // Return true if there is any possible capture
    protected boolean findAllPossibleCaptures(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++){
                if(isAnyCaptureForThatField(board, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    // Return true if there is any possible capture from some field
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
    // Return true if there is any possible capture from some field with pawn
    protected boolean isAnyPawnCapturePossible(Board board, int startX, int startY){
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
    // Return true if there is any possible capture from some field with queen
    protected boolean isAnyQueenCapturePossible(Board board, int startX, int startY) {
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
    public boolean isPlayerHasAtLeastOneMove(Board board, Color playerColor) {
        return isPlayerHasAnyMoves(board, playerColor);
    }
    /**
     * Check Is the player a winner
     * @param board
     * @param playerColor
     * @return true if player is a winner, else false
     */
    public boolean isWinner(Board board, Color playerColor){
        return !isAnyOpponentPiecesOnBoard(board, playerColor);
    }
    public boolean isTie() {
        return moveCounter > 50 ? true : false;
    }
    /**
     * Check is there is any opponent pieces on board
     * @param board
     * @param playerColor
     * @return true if there is at least one enemy piece, false if there is no enemy pieces
     */
    private boolean isAnyOpponentPiecesOnBoard(Board board, Color playerColor){
        boolean flag = false;
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Piece pieceAtField = board.getField(i,j);
                if(pieceAtField.getPieceColor() == Color.NoColor)
                    continue;
                if(pieceAtField.getPieceColor() != playerColor){
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
        }
        return flag;
    }
    /**
     * Check is there at least one possible move for opponent
     * @param board
     * @param playerColor
     * @return true if there is at least one possible move, false if there is no possible moves
     */
    private boolean isPlayerHasAnyMoves(Board board, Color playerColor) {
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                int startX = i;
                int startY = j;
                for(int directionX = -1; directionX <= 1; directionX += 2) {
                    for(int directionY = -1; directionY <= 1; directionY += 2) {
                        int endX = startX;
                        int endY = startY;
                        while(!outOfBounds(board, endX + directionX, endY + directionY)) {
                            endX += directionX;
                            endY += directionY;
                            if(isValidMove(board, new Move(startX, startY, endX, endY), null, playerColor).getKey() != 0) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
