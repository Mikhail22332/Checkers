package com.Data;

public abstract class AbstractChecker {
    public boolean isValidMove(Board board, Move move){
        return false;
    }
    public void MakeMove(Board board, Move move){}
    private boolean isWhiteWin(Board board){
        boolean flag = true;
        for(int i = 0; i < board.GetSize(); i++){
            for(int j = 0; j < board.GetSize(); j++){
                PieceType currentField = board.GetField(i,j);
                if(currentField == PieceType.WhitePawn || currentField == PieceType.WhiteQueen){
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
                PieceType currentField = board.GetField(i,j);
                if(currentField == PieceType.BlackPawn || currentField == PieceType.BlackQueen){
                    flag = false;
                    break;
                }
            }
            if(!flag)
                break;
        }
        return flag;
    }
    public boolean isWinner(Board board, Color color){
        if(color == Color.Black)
            return isBlackWin(board);
        else if(color == Color.White)
            return isWhiteWin(board);
        else
            return false;
    }
}
