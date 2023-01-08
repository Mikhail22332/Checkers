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
    public void printBoard(){
        String s;
        System.out.println("board:");
        for(int i = 0; i < this.getSize(); i++){
            s = "";
            for(int j = 0; j < this.getSize(); j++){
                Piece current = this.getField(i,j);
                if(current.getPieceType() == PieceType.Blank) {
                    s = s + "0";
                }
                else if(current.getPieceColor() == Color.White) {
                    if(current.getPieceType() == PieceType.Pawn){
                        s = s + "1";
                    }
                    else
                        s = s + "2";
                }
                else if(current.getPieceType() == PieceType.Pawn){
                    s = s + "3";
                }
                else {
                    s = s + "4";
                }
                s = s + ",";
            }
            System.out.println(s);
        }
        System.out.println("finish");
    }
    public String boardToString(){
        String s = "";
        for(int i = 0; i < this.getSize(); i++){
            for(int j = 0; j < this.getSize(); j++){
                Piece current = this.getField(i,j);
                if(current.getPieceType() == PieceType.Blank) {
                    s = s + "0";
                }
                else if(current.getPieceColor() == Color.White) {
                    if(current.getPieceType() == PieceType.Pawn){
                        s = s + "1";
                    }
                    else
                        s = s + "2";
                }
                else if(current.getPieceType() == PieceType.Pawn){
                    s = s + "3";
                }
                else {
                    s = s + "4";
                }
                if(j != this.getSize() - 1)
                    s = s + ",";
            }
            s = s + "newline";
        }
        return s;
    }
    // To Test Client-Server Console version

}
