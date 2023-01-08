package com.Data.Standart;

import com.Data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorIsAnyQueenCapture {

    // Tests to check Queen
    // Test to kill close enemy
    @Test
    void Test1() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 5);
        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test to kill not close enemy
    @Test
    void Test2() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Queen, Color.Black), 6, 6);
        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test to kill 2 in a row
    @Test
    void Test3() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 1, 1);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test try to kill enemy at corner
    @Test
    void Test4() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 0);
        board.printBoard();
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test to check diagonals
    @Test
    void Test5() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 6);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 0);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 4));
    }
}