package com.Data.English;

import com.Data.*;

public class FactoryBoardEnglish extends AbstractFactory{
    public FactoryBoardEnglish(){
        super();
    }

    public Board сreateBoard(int size){
        if(size == 8)
            return createBoard8X8();
        return null;
    }
    public Board createBoard8X8(){
        Board board = new Board(8);
        fillBoard8X8(board);
        return board;
    }
    private void fillBoard8X8(Board board){
        for(int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {

                if (i < 3 && (i + j) % 2 == 1) {
                    board.setField(new Piece(PieceType.Pawn, Color.Black), i, j);
                }
                if (i > 4 && (i + j) % 2 == 1) {
                    board.setField(new Piece(PieceType.Pawn, Color.White), i, j);
                }
            }
        }
    }
}
