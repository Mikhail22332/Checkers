package com.Data;

public class Move {
    private int x1 = 0;
    private int y1 = 0;
    private int x2 = 0;
    private int y2 = 0;
    private int stepCounter = 0;
    public Move(){}
    public Move(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public Move(int x1, int y1, int x2, int y2, int step) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.stepCounter = step;
    }

    public void setStepCounter(int stepCounter) { this.stepCounter = stepCounter; }

    public int getStepCounter() { return stepCounter; }

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
    public int getX1(){return x1;}
    public int getY1(){return y1;}
    public int getX2(){return x2;}
    public int getY2(){return y2;}

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public static String MoveToString(Move move){
        String s = "";
        s += move.getX1() + ",";
        s += move.getY1() + ",";
        s += move.getX2() + ",";
        s += move.getY2();
        return s    ;
    }
}
