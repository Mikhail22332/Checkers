package com.Another.Pieces;

import com.Another.Color;
import com.Another.Move;
import com.Another.PieceType;
import com.Another.Position;

import java.util.HashSet;
import java.util.Set;

public class Quene extends AbstractPiece{

    public Quene(Color color){
        super(PieceType.Quene, color);
    }

    @Override
    public Set<Move> generateMoves(Position position) {
        Set<Move> moves = new HashSet<Move>();
        int startX = position.getX();
        int startY = position.getY();
        //TODO add capture
        for (int x = -1; x <= 1; x +=2){
            for(int y = -1; y <= 1; y +=2){
                int endX = startX + x;
                int endY = startY + y;
                while(endX > -1 && endX < 8 && endY > -1 && endY < 8){
                    Position destinationPos = new Position(endX, endY);
                    moves.add(new Move(position, destinationPos));
                    endX += x;
                    endY += y;
                }
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
