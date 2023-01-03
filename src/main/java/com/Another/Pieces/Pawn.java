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

            //change if for promotion and capture
            if (endX > 0 && endY > 0 && endX < 7 && endY < 7) {


                Move move = null;
                //promotion needed
                //capture needed

                if (endX-startX == 1 && endY-startY == 1 || endX - startX == -1 && endY-startY == 1 ) {
                    Position end = new Position(endX, endY);
                    move = new Move(position, end, false);
                }
                moves.add(move);
            }
        }
        return moves;
    }


}
