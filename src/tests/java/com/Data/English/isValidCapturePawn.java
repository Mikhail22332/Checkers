package com.Data.English;

import com.Data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class isValidCapturePawn{
    //kill enemy forward
    @Test
    void Test1(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4);
        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 4, 3));
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(4,3,2,5), null, Color.White));
    }
    //kill enemy backwards
    @Test
    void Test2(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 2, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 2, 5));
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(2,5,4,3), null, Color.White));
    }
    //kill teammate
    @Test
    void Test3(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 4);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 3));
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(4,3,2,5), null, Color.White));
    }
    //move while capture is possible
    @Test
    void Test4(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4);
        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 4, 3));
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(4,3,3,2), null, Color.White));
    }
    //chain of captures to all sides
    @Test
    void Test5(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.Black);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 4);
        board.setField(new Piece(PieceType.Queen, Color.White), 3, 6);

        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 2, 3));
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(2,3,4,5), null, Color.Black));
        validator.makeMove(board, new Move(2,3,4,5));
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 5));
    }
    //kill, become queen and try kill once again
    @Test
    void Test6(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorEnglish();
        validator.setPlayerMark(Color.Black);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 6, 5);
        board.setField(new Piece(PieceType.Queen, Color.White), 3, 2);

        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 5, 4));
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(5,4,7,6), null, Color.Black));
        validator.makeMove(board, new Move(5,4,7,6));
        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 7, 6));
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(7,6,2,1), new Move(5,4,7,6), Color.Black));
    }
}