package com.Data.Polish;

import com.Data.*;

public class FactoryBoardPolish extends AbstractFactory {
    public FactoryBoardPolish(){
        super();
    }

    public Board сreateBoard(int size){
        if(size == 10)
            return createBoard10X10();
        return null;
    }
    public Board createBoard10X10(){
        Board board = new Board(10);
        fillBoard10X10(board);
        return board;
    }
    private void fillBoard10X10(Board board){
        for(int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {

                if (i < 4 && (i + j) % 2 == 1) {
                    board.setField(new Piece(PieceType.Pawn, Color.Black), i, j);
                }
                if (i > 5 && (i + j) % 2 == 1) {
                    board.setField(new Piece(PieceType.Pawn, Color.White), i, j);
                }
            }
        }
    }
}
