package com.Another.Pieces;

import com.Another.Move;
import com.Another.PieceType;
import com.Another.Position;

public class Promotion extends Move {
    private PieceType promotionToQuene;

    public Promotion(Position start, Position destination, PieceType type) {
        super(start, destination);
        setPromotionToQuene(type);
    }

    public void setPromotionToQuene(PieceType t){
        this.promotionToQuene = t;
    }

    public PieceType getPromotionToQuene() {
        return promotionToQuene;
    }
}
