package com.Another.Pieces;

import com.Another.Color;
import com.Another.Move;
import com.Another.PieceType;
import com.Another.Position;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends AbstractPiece {

    public Pawn(Color color) {
        super(PieceType.Pawn, color);
    }

    @Override
    public Set<Move> generateMoves(Position position) {
        Set<Move> moves = new HashSet<Move>();
        int startX = position.getX();
        int startY = position.getY();

        for (int i = -1 ; i <= 1; i++) {
            int endX = startX + i;
            int endY = startY + i;
            boolean bounds = outOfBounds(endX, endY);

            //change if for promotion and capture
            if (bounds) {
                Move move = null;

                //capture needed!!

                //promotion
                if((endX == 0 && endY < 7 && endY > 0) || (endX == 7  && endY < 7 && endY > 0)){
                    move = new Promotion(position, new Position(endX, endY), PieceType.Quene);
                }
                //move
                if (Math.abs(endX-startX) == 1 && Math.abs(endY-startY) == 1 ) {
                    Position end = new Position(endX, endY);
                    move = new Move(position, end, false);
                }

                moves.add(move);
            }
        }
        return moves;
    }

    private boolean outOfBounds(int x, int y){
        if (x>=0 && x < 8 && y >= 0 && y < 8){
            return true;
        }
        else{
            return false;
        }
    }
}
