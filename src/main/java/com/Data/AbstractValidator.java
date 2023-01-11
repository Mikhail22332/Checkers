package com.Data;

public abstract class AbstractValidator {
    protected Color playerMark = Color.NoColor;
    public void setPlayerMark(Color playerMark) {
        this.playerMark = playerMark;
    }
    protected boolean isAnyCapture = false;
    protected boolean isLastMoveCapture = false;
    public boolean checkForNextMove(Board board, int startX, int startY) {return isAnyCaptureForThatField(board, startX, startY);}
    // Return 0 - impossible move, 1 - simple move, 2 - move with capture
    public int isValidMove(Board board, Move move, Move lastMove, Color playerMark) {
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
    // Check is valid move, if that piece was a pawn
    protected int validPawnMove(Board board, Move move) {return 0;}
    // Check is valid move, if that piece was a queen
    protected int validQueenMove(Board board, Move move) {return 0;}
    // Release a move
    public void makeMove(Board board, Move move) {
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


    // Check to win
    public boolean isWinner(Board board, Color playerColor){
        if(playerColor == Color.Black)
            return isBlackWin(board);
        else if(playerColor == Color.White)
            return isWhiteWin(board);
        else
            return false;
    }
    // Check white is winning
    private boolean isWhiteWin(Board board){
        boolean flag = true;
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Piece pieceAtField = board.getField(i,j);

                if(pieceAtField.getPieceColor() == Color.Black){
                    flag = false;
                    break;
                }
            }
            if(!flag)
                break;
        }
        return flag;
    }
    // Check black is winning
    private boolean isBlackWin(Board board){
        boolean flag = true;
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Piece pieceAtField = board.getField(i,j);

                if(pieceAtField.getPieceColor() == Color.White){
                    flag = false;
                    break;
                }
            }
            if(!flag)
                break;
        }
        return flag;
    }
}
