package com.Data.Standart;

import com.Data.*;

public class FactoryBoardStandart extends AbstractFactory {
    public FactoryBoardStandart(){
        super();
    }

    @Override
    public Board сreateBoard(int size) {
        if(size == 8)
            return createBoard8x8();
        if(size == 10)
            return createBoard10x10();
        return null;
    }

    private Board createBoard8x8(){
        Board board = new Board(8);
        fillBoard8x8(board);
        return board;
    }
    private void fillBoard8x8(Board board){
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
    private Board createBoard10x10(){
        Board board = new Board(10);
        FillBoard10x10(board);
        return board;
    }
    private void FillBoard10x10(Board board){
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
