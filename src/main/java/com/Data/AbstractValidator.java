package com.Data;

public abstract class AbstractValidator {
    public boolean isValidMove(Board board, Move move){
        return false;
    }
    public void MakeMove(Board board, Move move){}
    private boolean isWhiteWin(Board board){
        boolean flag = true;
        for(int i = 0; i < board.GetSize(); i++){
            for(int j = 0; j < board.GetSize(); j++){
                Piece pieceAtField = board.GetField(i,j);

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
        for(int i = 0; i < board.GetSize(); i++){
            for(int j = 0; j < board.GetSize(); j++){
                Piece pieceAtField = board.GetField(i,j);

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
}
