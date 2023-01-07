package com.Data.Standart;

import com.Data.*;

public class ValidatorStandart extends AbstractValidator {
    @Override
    public boolean isValidMove(Board board, Move move){
        //TODO some logic to check validation of moves
        int startX = move.GetX1();
        int startY = move.GetY1();

        for(int i = -1; i <= i; i++) {

            /*boolean outOfBounds = outOfBounds(endX, endY);

            Piece movingPiece = board.GetField(startX, startY);

            if (outOfBounds) {
                return false;
            }
            Piece pieceAtEnd = board.GetField(endX, endY);
            */
        }



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
        board.SetField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
    }
    private boolean outOfBounds(int x, int y){
        if (x>=0 && x < 8 && y >= 0 && y < 8){
            return true;
        }
        else{
            return false;
        }
    }
}
