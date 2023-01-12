package com.Data.Standart.Validator;

import com.Data.*;
import com.Data.Standart.ValidatorStandart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
// Tests to check pawn
class isAnyPawnCapture {

    // Test to kill enemy pawn
    @Test
    void Test1() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 5);
        Assertions.assertEquals(true, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test to kill allies pawn
    @Test
    void Test2() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 5);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test to kill enemy when behind it allies
    @Test
    void Test3() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 2, 2);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 4));
    }
    // Test to kill enemy when behind it enemy
    @Test
    void Test4() {
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        validator.setPlayerMark(Color.White);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 4);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 5);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 6, 6);
        Assertions.assertEquals(false, validator.isAnyCaptureForThatField(board, 4, 4));
    }
}
// Tests to check Queen
class isAnyQueenCapture {

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
//Tests to check pawn moves
class isValidMovePawn{

    //move white pawn behind
    @Test
    void Test1(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 2, 2);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(2,2,3,3), null, Color.White));
    }
    //move white pawn to empty cell
    @Test
    void Test2(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 0);

        Assertions.assertEquals(1, validator.isValidMoveTest(board, new Move(5,0,4,1), null, Color.White));
    }
    //move black pawn behind
    @Test
    void Test3(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn,Color.Black), 2, 3);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(2,3,1,2), null, Color.Black));
    }
    //move black pawn to empty cell
    @Test
    void Test4(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 2, 3);

        Assertions.assertEquals(1, validator.isValidMoveTest(board, new Move(2,3,3,2), null, Color.Black));
    }
    //move white pawn to occupied cell
    @Test
    void Test5(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 1);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 0);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5, 0,4, 1), null, Color.White));
    }
    // move to distance > 1 like quene
    @Test
    void Test6(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 4);
        board.printBoard();
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5, 4,3, 6), null, Color.White));
    }
    // kill piece of your team
    @Test
    void Test7(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5, 4,3, 6), null, Color.White));
    }
    // kill piece of enemy team
    @Test
    void Test8(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(4, 5,6, 3), null, Color.White));
    }
    // try to kill enemy piece like queen
    @Test
    void Test9(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 5, 4);
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 5);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(4, 5,7, 2), null, Color.White));
    }
    // try to kill enemy piece and place pawn to occupied cell
    @Test
    void Test10(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 4, 3);
        board.setField(new Piece(PieceType.Pawn, Color.White), 5, 2);
        board.setField(new Piece(PieceType.Pawn, Color.Black), 3, 4);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(5, 2,3, 4), null, Color.White));
    }
    // chain of kills
    @Test
    void Test11(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 7);
        board.setField(new Piece(PieceType.Pawn, Color.White), 1, 6);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 4);
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(0, 7,2, 5), null, Color.Black));
        validator.makeMove(board, new Move(0, 7,2, 5));
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(2,5,4,3), new Move(0, 7,2, 5), Color.Black));
    }
    // chain of kills and move
    @Test
    void Test12(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.Black), 0, 7);
        board.setField(new Piece(PieceType.Pawn, Color.White), 1, 6);
        board.setField(new Piece(PieceType.Pawn, Color.White), 3, 4);
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(0, 7,2, 5), null, Color.Black));
        validator.makeMove(board, new Move(0, 7,2, 5));
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(2,5,4,3), new Move(0, 7,2, 5), Color.Black));
        validator.makeMove(board, new Move(2,5,4,3));
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(4,3,5,2), new Move(2,5,4,3), Color.Black));
    }
    // move enemy's piece
    @Test
    void Test13(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Pawn, Color.White), 4, 3);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(4,3,3,4), null, Color.Black));
    }
}
//Tests to check queen moves
class isValidMoveQueen{
    //move queen to empty cell on an empty diagonal
    @Test
    void Test1(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.White), 0, 7);
        Assertions.assertEquals(1, validator.isValidMoveTest(board, new Move(0,7,4,3), null, Color.White));
    }
    //move enemy's queen to empty cell on an empty diagonal
    @Test
    void Test2(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.White), 0, 7);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(0,7,4,3), null, Color.Black));
    }
    //move queen with kill option available
    @Test
    void Test3(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.White), 0, 7);
        board.setField(new Piece(PieceType.Queen, Color.Black), 3, 4);
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(0,7,2,5), null, Color.White));
    }
    //kill enemy's piece
    @Test
    void Test4(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.White), 0, 7);
        board.setField(new Piece(PieceType.Queen, Color.Black), 3, 4);
        board.printBoard();
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(0,7,4,3), null, Color.White));
    }
    //try to kill my piece
    @Test
    void Test8(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.White), 0, 7);
        board.setField(new Piece(PieceType.Queen, Color.White), 3, 4);
        board.printBoard();
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(0,7,4,3), null, Color.White));
    }
    //try to kill 2 enemy's pieces in a row
    @Test
    void Test5(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.White), 0, 7);
        board.setField(new Piece(PieceType.Queen, Color.Black), 3, 4);
        board.setField(new Piece(PieceType.Queen, Color.Black), 4, 3);
        board.printBoard();
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(0,7,5,2), null, Color.White));
    }
    //chain of enemy's kill
    @Test
    void Test6(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.Black), 4, 3);
        board.setField(new Piece(PieceType.Queen, Color.White), 3, 4);
        board.setField(new Piece(PieceType.Queen, Color.White), 1, 4);
        board.setField(new Piece(PieceType.Queen, Color.White), 1, 2);
        board.printBoard();
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(4,3,2,5), null, Color.Black));
        validator.makeMove(board, new Move(4, 3,2, 5));
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(2,5,0,3), new Move(4, 3,2, 5), Color.Black));
        validator.makeMove(board, new Move(2,5,0,3));
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(0,3,2,1), new Move(2,5,0,3), Color.Black));
    }
    //chain of enemy's kill with one teammate piece
    @Test
    void Test7(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.Black), 4, 3);
        board.setField(new Piece(PieceType.Queen, Color.White), 3, 4);
        board.setField(new Piece(PieceType.Queen, Color.Black), 1, 4);
        board.setField(new Piece(PieceType.Queen, Color.White), 1, 2);
        board.printBoard();
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(4,3,2,5), null, Color.Black));
        validator.makeMove(board, new Move(4, 3,2, 5));
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(2,5,0,3), new Move(4, 3,2, 5), Color.Black));
        validator.makeMove(board, new Move(2,5,0,3));
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(0,3,2,1), new Move(2,5,0,3), Color.Black));
    }
    //kill piece and move after
    @Test
    void Test9(){
        Board board = new Board(8);
        AbstractValidator validator = new ValidatorStandart();
        board.setField(new Piece(PieceType.Queen, Color.Black), 0, 7);
        board.setField(new Piece(PieceType.Pawn, Color.White), 1, 6);
        board.printBoard();
        Assertions.assertEquals(2, validator.isValidMoveTest(board, new Move(0,7,2,5), null, Color.Black));
        validator.makeMove(board, new Move(0, 7,2, 5));
        Assertions.assertEquals(0, validator.isValidMoveTest(board, new Move(2,5,1,4), new Move(0, 7,2, 5), Color.Black));
    }
}