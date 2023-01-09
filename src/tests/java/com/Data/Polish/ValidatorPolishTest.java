package com.Data.Polish;

import com.Data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//tests must return the longest possible chain of hitting
class recursionTestsForPawn{

    //test recursion without tree
    @Test
    void pawnTest1(){
        Board board = new Board(10);
        AbstractValidator validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 2);
        Assertions.assertEquals(2, validator.isRecursionPossible(board, startX, startY));
    }
    //test recursion with two options: 1) kill one enemy
    //                                 2) kill two enemies
    @Test
    void pawnTest2(){
        Board board = new Board(10);
        AbstractValidator validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 4);
        Assertions.assertEquals(2, validator.isRecursionPossible(board, startX, startY));
    }
    //test recursion with a long chain
    @Test
    void pawnTest3(){
        Board board = new Board(10);
        AbstractValidator validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 7, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 6);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 1, 6);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 1, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 4);
        board.printBoard();
        Assertions.assertEquals(2, validator.isRecursionPossible(board, startX, startY));
    }
}