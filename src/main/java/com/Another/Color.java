package com.Another;

public enum Color {
    Black(0, 1, 2, "Black"),
    White(5, 6, 7, "White");

    private int row1, row2, row3;
    private String sideColor;

    Color(int row1, int row2, int row3, String color){
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
    }
    public int getRow1(){
        return row1;
    }

    public int getRow2() {
        return row2;
    }

    public int getRow3() {
        return row3;
    }
}
