package com.Client;

import javafx.scene.paint.Color;

class Pawn extends javafx.scene.shape.Circle {

    private int row;
    private int col;
    private boolean selected;
    private boolean isQueen;
    private Color color;

    public Pawn(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.isQueen = false;

        // set color of the pawn
        setFill(color);
        setStroke(Color.BLACK);
    }
    public Pawn(int row, int col) {
        this.row = row;
        this.col = col;
        this.color = null;
        this.isQueen = false;

        // Set color of the pawn
        setFill(color);
        setStroke(Color.BLACK);
    }

    public boolean canMoveTo(int row, int col) {
        // Check is same diagonal was chosen to move
        if(Math.abs(col - this.col) != Math.abs(row - this.row) || Math.abs(row - this.row) == 0 || Math.abs(col - this.col) == 0) {
            return false;
        }
        return true;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(!isQueen)
            setStrokeWidth(selected ? 3 : 1);
        else
            setStrokeWidth(selected ? 4 : 2);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public void makeQueen() {
        this.isQueen = true;
        setStroke(Color.RED);
        setStrokeWidth(2);
    }

    public void setColor(Color color) { this.color = color; setFill(color); }

    public void setSize(double size){
        setRadius(size / 2 - 5);
        setCenterX(size / 2);
        setCenterY(size / 2);
    }
}