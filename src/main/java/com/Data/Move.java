package com.Data;

public class Move {
    private int x1 = 0;
    private int y1 = 0;
    private int x2 = 0;
    private int y2 = 0;
    public Move(){}
    public int GetX1(){return x1;}
    public int GetY1(){return y1;}
    public int GetX2(){return x2;}
    public int GetY2(){return y2;}
    public void SetMove(String s[]){
        this.x1 = Integer.parseInt(s[0]);
        this.y1 = Integer.parseInt(s[1]);
        this.x2 = Integer.parseInt(s[2]);
        this.y2 = Integer.parseInt(s[3]);
    }
    public static Move StringToMove(String s){
        String[] a = s.split(",");
        Move move = new Move();
        move.SetMove(a);
        return move;
    }
    public static String MoveToString(Move move){
        String s = "";
        s += move.GetX1() + ",";
        s += move.GetY1() + ",";
        s += move.GetX2() + ",";
        s += move.GetY2();
        return s    ;
    }
}
