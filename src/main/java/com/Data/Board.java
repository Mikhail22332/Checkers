package com.Data;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<PieceType>> field = new ArrayList<>();
    private int size;
    public Board(int size){
        this.size = size;
        for(int i = 0; i < size; i++){
            ArrayList<PieceType> row = new ArrayList<>();
            for(int j = 0; j < size; j++){
                row.add(PieceType.Blank);
            }
            field.add(row);
        }
    }
    public int GetSize() {
        return size;
    }
    public PieceType GetField(int i, int j){
        return field.get(i).get(j);
    }
    public void SetField(PieceType piece, int i, int j){
        field.get(i).set(j, piece);
    }
    // To Test Client-Server Console version

}
