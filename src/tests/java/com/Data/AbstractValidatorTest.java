package com.Data;

import com.Data.Standart.ValidatorStandart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractValidatorTest {

    @Test
    void isWinner1() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 0,0);
        board.printBoard();
        Assertions.assertEquals(true, validator.isWinner(board, Color.White));
    }
    @Test
    void isWinner2() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 0,0);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2,0);
        board.printBoard();
        Assertions.assertEquals(false, validator.isWinner(board, Color.White));
    }
    @Test
    void isWinner3() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 6,0);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5,1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4,2);
        board.printBoard();
        Assertions.assertEquals(false, validator.isPlayerHasAtLeastOneMove(board, Color.White));
    }
}