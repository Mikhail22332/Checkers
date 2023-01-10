package com.Data.Polish;

import com.Data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//tests must return the longest possible chain of hitting\\

//tests for pawn recursion
class recursionTestsForPawn{

    //test recursion with only one possible way
    @Test
    void pawnTest1(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 2);
        board.printBoard();
        Assertions.assertEquals(2, validator.isRecursionPossible(board, 5, 1));
    }
    //test recursion with two options: 1) kill one enemy
    //                                 2) kill two enemies
    @Test
    void pawnTest2(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 4);
        Assertions.assertEquals(3, validator.isRecursionPossible(board, 5, 1));
    }
    //test recursion with a long chain
    @Test
    void pawnTest3(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 2); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 2); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 6); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 1, 6); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 1, 4); //
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4); //
        board.printBoard();
        Assertions.assertEquals(7, validator.isRecursionPossible(board, 4, 5));
    }
    //test recursion which is interrupted by your allies pieces
    @Test
    void pawnTest4(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 9, 0);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 1);
        board.setField(new Piece(PieceType.Pawn, Color.White), 8, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 1, 6);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);
        board.printBoard();
        Assertions.assertEquals(2, validator.isRecursionPossible(board, 9, 0));
    }

    //test recursion which has super long chain
    @Test
    void pawnTest5(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 9);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 9);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 9);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 9);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 7);
        board.printBoard();
        Assertions.assertEquals(10, validator.isRecursionPossible(board, 5, 4));
    }
    //empty board test
    @Test
    void pawnTest6(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 1);
        Assertions.assertEquals(0, validator.isRecursionPossible(board, 5, 1));
    }
}

//
class recursionTestsForQueen{
    //simple chain
    @Test
    void queenTest1(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 5, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 2);
        board.printBoard();
        Assertions.assertEquals(2, validator.isRecursionPossible(board, 5, 1));
    }
    //a bit longer chain
    @Test
    void queenTest2(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 8, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 5);
        board.printBoard();
        Assertions.assertEquals(8, validator.isRecursionPossible(board, 8, 1));
    }
    //
    @Test
    void queenTest3(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 3, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 5);
        board.setField(new Piece(PieceType.Pawn, Color.White), 6, 6);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 7, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 7);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 9);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 7, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 8);
        board.setField(new Piece(PieceType.Pawn, Color.White), 2, 8);
        board.printBoard();
        Assertions.assertEquals(4, validator.isRecursionPossible(board, 3, 3));
    }
    @Test
    void queenTest4(){
        Board board = new Board(10);
        ValidatorPolish validator = new ValidatorPolish();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Queen, Color.White), 9, 0);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 7, 0);
        board.setField(new Piece(PieceType.Pawn, Color.White), 9, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 8, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 1);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 7);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4);
        board.printBoard();
        Assertions.assertEquals(8, validator.isRecursionPossible(board, 9, 0));
    }
}


//Tests to check pawn moves
class isValidMovePawn{

    //move white pawn behind
    @Test
    void Test1(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.White), 2, 2);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(2,2,3,3), null, Color.White));
    }
    //move white pawn to empty cell
    @Test
    void Test2(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 0);

        Assertions.assertEquals(1, validator.isValidMove(board, new Move(5,0,4,1), null, Color.White));
    }
    //move black pawn behind
    @Test
    void Test3(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn,Color.Black), 2, 3);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(2,3,1,2), null, Color.Black));
    }
    //move black pawn to empty cell
    @Test
    void Test4(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);

        Assertions.assertEquals(1, validator.isValidMove(board, new Move(2,3,3,2), null, Color.Black));
    }
    //move white pawn to occupied cell
    @Test
    void Test5(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 1);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 0);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(5, 0,4, 1), null, Color.White));
    }
    // move to distance > 1 like quene
    @Test
    void Test6(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 4);
        board.printBoard();
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(5, 4,3, 6), null, Color.White));
    }
    // kill piece of your team
    @Test
    void Test7(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(5, 4,3, 6), null, Color.White));
    }
    // kill piece of enemy team
    @Test
    void Test8(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(4, 5,6, 3), null, Color.White));
    }
    // try to kill enemy piece like queen
    @Test
    void Test9(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(4, 5,7, 2), null, Color.White));
    }
    // try to kill enemy piece and place pawn to occupied cell
    @Test
    void Test10(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(5, 2,3, 4), null, Color.White));
    }
    // chain of kills
    @Test
    void Test11(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 7);
        board.setField(new Piece(PieceType.Pawn, Color.White), 1, 6);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 4);
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(0, 7,2, 5), null, Color.Black));
        validator.makeMove(board, new Move(0, 7,2, 5));
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(2,5,4,3), new Move(0, 7,2, 5), Color.Black));
    }
    // chain of kills and move
    @Test
    void Test12(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 7);
        board.setField(new Piece(PieceType.Pawn, Color.White), 1, 6);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 4);
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(0, 7,2, 5), null, Color.Black));
        validator.makeMove(board, new Move(0, 7,2, 5));
        Assertions.assertEquals(2, validator.isValidMove(board, new Move(2,5,4,3), new Move(0, 7,2, 5), Color.Black));
        validator.makeMove(board, new Move(2,5,4,3));
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(4,3,5,2), new Move(2,5,4,3), Color.Black));
    }
    // move enemy's piece
    @Test
    void Test13(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorPolish();
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 3);
        Assertions.assertEquals(0, validator.isValidMove(board, new Move(4,3,3,4), null, Color.Black));
    }
}

