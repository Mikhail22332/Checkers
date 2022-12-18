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
    public void SetX1(String s){this.x1 = Integer.parseInt(s);}
    public void SetY1(String s){this.y1 = Integer.parseInt(s);}
    public void SetX2(String s){this.x2 = Integer.parseInt(s);}
    public void SetY2(String s){this.y2 = Integer.parseInt(s);}
    public static Move StringToMove(String s){
        String[] a = s.split(",");
        Move move = new Move();
        move.SetX1(a[0]);
        move.SetY1(a[1]);
        move.SetX2(a[2]);
        move.SetY2(a[3]);
        return move;
    }
    public static String MoveToString(Move move){
        String s = "";
        s += move.GetX1() + ",";
        s += move.GetY1() + ",";
        s += move.GetX2() + ",";
        s += move.GetY2();
        return s;
    }
}
