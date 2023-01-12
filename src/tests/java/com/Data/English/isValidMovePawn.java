package com.Data.English;

import com.Data.*;
import com.Data.English.ValidatorEnglish;
import com.Data.Standart.ValidatorStandart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class isValidMovePawn {
    //pawn move to occupied cell
    @Test
    void Test1(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 3);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5,2,4,3), null, Color.White));
    }
    //pawn move to empty cell
    @Test
    void Test2(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.Black);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 1);
        Assertions.assertEquals(1, validator.isValidMoveTest(board, new Move(2,1,3,2), null, Color.Black));
    }
    //pawn move back
    @Test
    void Test3(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.Black);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 1);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(2,1,1,0), null, Color.Black));
    }
    //pawn move 2 cells
    @Test
    void Test4(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 5);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5,5,3,3), null, Color.Black));
    }
    //move enemy's pawn
    @Test
    void Test5(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 5);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5,5,4,4), null, Color.Black));
    }
}

