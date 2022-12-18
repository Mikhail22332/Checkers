package com.Data.Standart;

import com.Data.*;

public class CheckerStandart extends AbstractChecker {
    @Override
    public boolean isValidMove(Board board, Move move){
        //TODO some logic to check validation of moves
        //if(move.GetX1() + move.GetY1() == move.GetX2() + move.GetY2())
        return true;
    }
    @Override
    public void MakeMove(Board board, Move move){
        //TODO problably need to rework this
        int x1 = move.GetX1();
        int y1 = move.GetY1();
        int x2 = move.GetX2();
        int y2 = move.GetY2();
        board.SetField(board.GetField(x1,y1),x2,y2);
        board.SetField(PieceType.Blank,x1,y1);
    }
}
