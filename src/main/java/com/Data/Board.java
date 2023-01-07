package com.Data;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Piece>> field = new ArrayList<>();
    private int size;
    public Board(int size){
        this.size = size;
        for(int i = 0; i < size; i++){
            ArrayList<Piece> row = new ArrayList<>();
            for(int j = 0; j < size; j++){
                row.add(new Piece(PieceType.Blank, Color.NoColor));
            }
            field.add(row);
        }
    }
    public int getSize() {
        return size;
    }
    public Piece getField(int i, int j){
        return field.get(i).get(j);
    }
    public void setField(Piece piece, int i, int j){
        field.get(i).set(j, piece);
    }

    // To Test Client-Server Console version

}
