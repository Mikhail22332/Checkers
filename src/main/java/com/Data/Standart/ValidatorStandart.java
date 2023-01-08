package com.Data.Standart;

import com.Data.*;

public class ValidatorStandart extends AbstractValidator {
    //0 -impossible move, 1 - simple move, 2- capture, 3 - promotion
    @Override
    public int isValidMove(Board board, Move move){
        //TODO some logic to check validation of moves
        int startX = move.getX1();
        int startY = move.getY1();
        int endX;
        int endY;
        Piece movingPiece = board.getField(startX, startY);
        for(int i = -1; i <= i; i+=2) {

            if(movingPiece.getPieceType() == PieceType.Pawn && movingPiece.getPieceColor() == Color.White){

                endX = startX + i;
                endY = startY - 1;

                boolean outOfBounds = outOfBounds(endX, endY);


                if (outOfBounds || Math.abs(endX-startX) == 0 || Math.abs(endY-startY)==0) {
                    return 0;
                }


                Piece pieceAtEnd = board.getField(endX, endY);

                if(mustCapture(startX, startY, endX, endY, board))
                    return 2;

                else if(!mustCapture(startX, startY, endX, endY, board)) {
                    int deltaX = endX-startX;
                    int deltaY = endY-startY;
                    if(Math.abs(deltaX) == Math.abs(deltaY) && pieceAtEnd.getPieceType().equals(PieceType.Blank)){
                        if(promotionPossible(startX,startY,endX,endY,board))
                            return 3;

                        else
                            return 1;

                    }
                else
                    return 0;

                }
            }
        }



        return 0;
    }
    @Override
    public void makeMove(Board board, Move move){
        //TODO problably need to rework this
        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getY2();
        board.setField(board.getField(x1,y1),x2,y2);
        board.setField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
    }

    @Override
    public void makeCaptureMove(Board board, Move move){
        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getY2();
        if (board.getField(x1, y1).getPieceType() == PieceType.Pawn){
            int dx = x2 - x1;
            int dy = y2 - y1;
            int midX = 0;
            int midY = 0;
            if(dx> 0 && dy> 0){
                midX = x1 + dx/2;
                midY = x1 + dy/2;
            }
            if(dx<0 && dy< 0){
                midX = x2 + dx/2;
                midY = y2 + dy/2;
            }
            if(dx<0 && dy > 0){
                midX = x2+dx/2;
                midY = x1+dy/2;
            }
            if(dx>0 && dy < 0){
                midX = x1 + dx/2;
                midY = x2 + dy/2;
            }
            board.setField(board.getField(x1,y1),x2,y2);
            board.setField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
            board.setField(new Piece(PieceType.Blank, Color.NoColor), midX, midY);
        } else{
            //TODO queen
        }
    }
    @Override
    public void makePromotion(Board board, Move move){
        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getY2();

        if (board.getField(x1,y1).getPieceColor().equals(Color.Black) && board.getField(x1, x2).getPieceType() == PieceType.Pawn){
            board.setField(board.getField(x1,y1),x2,y2);
            board.setField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
            board.setField(new Piece(PieceType.Queen, Color.Black),x2,y2);
        }else{
            board.setField(board.getField(x1,y1),x2,y2);
            board.setField(new Piece(PieceType.Blank, Color.NoColor),x1,y1);
            board.setField(new Piece(PieceType.Queen, Color.White),x2,y2);
        }
    }
    private boolean outOfBounds(int x, int y){
        if (!(x>=0 && x < 8 && y >= 0 && y < 8)){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean mustCapture(int x1, int y1, int x2, int y2, Board board){
        Piece start = board.getField(x1, y1);

        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        if(deltaX!=0 && deltaY != 0 && !outOfBounds(x2, y2) && Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2){

            Piece end = board.getField(x2, y2);

            if(!end.getPieceType().equals(PieceType.Blank) && start.getPieceColor() != end.getPieceColor()) {
                int cellBehindX = x2 + deltaX;
                int cellBehindY = y2 + deltaY;

                Piece behind = board.getField(cellBehindX, cellBehindY);

                if(!outOfBounds(cellBehindX, cellBehindY) && behind.getPieceType() == PieceType.Blank){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean promotionPossible(int x1, int y1, int x2, int y2, Board board){
        Piece start = board.getField(x1,y1);
        if(start.getPieceColor() == Color.Black && y2 == 7 || start.getPieceColor() == Color.Black && y2 == 0){
            return true;
        }
        else{
            return false;
        }
    }

}
