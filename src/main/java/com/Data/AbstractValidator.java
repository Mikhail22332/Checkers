package com.Data;

public abstract class AbstractValidator {
    public boolean isAnyCaptureForThatField(Board board, int startX, int startY) {return false;}
    //0 -impossible move, 1 - simple move, 2- capture
    public abstract int isValidMove(Board board, Move move, Move lastMove, Color playerMark);
    public boolean checkForNextMove(Board board, int startX, int startY) {return isAnyCaptureForThatField(board, startX, startY);}

    public void makeMove(Board board, Move move){}
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
    public boolean isWinner(Board board, Color playerColor){
        if(playerColor == Color.Black)
            return isBlackWin(board);
        else if(playerColor == Color.White)
            return isWhiteWin(board);
        else
            return false;
    }

    public abstract void setPlayerMark(Color playerMark);

    public boolean checkPromotion(Piece current, int size, int x2) {
        if(current.getPieceType() != PieceType.Pawn) {return false;}
        if(x2 == 0 && current.getPieceColor() == Color.White){return true;}
        return x2 == size - 1 && current.getPieceColor() == Color.Black;
    }
}
