package com.Data.Standart;

import com.Data.*;

public class FactoryBoardStandart extends AbstractFactory {
    public FactoryBoardStandart(){
        super();
    }
    @Override
    public Board CreateBoard(int size){
        if(size == 8)
            return CreateBoard8x8();
        return null;
    }
    public Board CreateBoard8x8(){
        Board board = new Board(8);
        FillBoard8x8(board);
        return board;
    }
    private void FillBoard8x8(Board board){
        for(int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {

                if (i < 3 && (i + j) % 2 == 0) {
                    board.setField(new Piece(PieceType.Pawn, Color.Black), i, j);
                }
                if (i > 4 && (i + j) % 2 == 0) {
                    board.setField(new Piece(PieceType.Pawn, Color.White), i, j);
                }
            }
        }
    }

}
