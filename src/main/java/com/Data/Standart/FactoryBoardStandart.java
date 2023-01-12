package com.Data.Standart;

import com.Data.*;

public class FactoryBoardStandart extends AbstractFactory {
    /**
     * Constructor which invokes abstract class constructor
     */
    public FactoryBoardStandart(){
        super();
    }
    /**
     * A method which is used to create a new board of a given size
     * @param size
     * @return
     */
    @Override
    public Board createBoard(int size) {
        if(size == 8)
            return createBoard8x8();
        if(size == 10)
            return createBoard10x10();
        return null;
    }
    /**
     * A method which is used to create a new 8x8 board
     * @return board
     */
    private Board createBoard8x8(){
        Board board = new Board(8);
        fillBoard8x8(board);
        return board;
    }

    /**
     * Fill board of given size (8) with pieces
     * @param board
     */
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

    /**
     * A method which is used to create a new 10x10 board
     * @return board
     */
    private Board createBoard10x10(){
        Board board = new Board(10);
        FillBoard10x10(board);
        return board;
    }

    /**
     * Fill board of given size (10) with pieces
     * @param board
     */
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
