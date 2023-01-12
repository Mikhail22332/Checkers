package com.Client;

import javafx.scene.paint.Color;

class Pawn extends javafx.scene.shape.Circle {

    private int row;
    private int col;
    private boolean selected;
    private boolean isQueen;
    private Color color;

    /**
     * @param row
     * @param col
     * @param color
     */
    public Pawn(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.isQueen = false;

        // set color of the pawn
        setFill(color);
        setStroke(Color.BLACK);
    }

    /**
     * @param row
     * @param col
     */
    public Pawn(int row, int col) {
        this.row = row;
        this.col = col;
        this.color = null;
        this.isQueen = false;

        // Set color of the pawn
        setFill(color);
        setStroke(Color.BLACK);
    }

    /**
     * Check field is same diagonal as that pawn
     * @param row
     * @param col
     * @return
     */
    public boolean canMoveTo(int row, int col) {
        // Check is same diagonal was chosen to move
        if(Math.abs(col - this.col) != Math.abs(row - this.row) || Math.abs(row - this.row) == 0 || Math.abs(col - this.col) == 0) {
            return false;
        }
        return true;
    }

    /**
     * return is selected that pawn
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * set this pawn as selected
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if(!isQueen)
            setStrokeWidth(selected ? 3 : 1);
        else
            setStrokeWidth(selected ? 4 : 2);
    }

    /**
     * setter for row
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * setter for column
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * getter for row
     * @return
     */
    public int getRow() { return row; }

    /**
     * getter for column
     * @return
     */
    public int getCol() { return col; }

    /**
     * Method to make promotion for pawn
     */
    public void makeQueen() {
        this.isQueen = true;
        setStroke(Color.RED);
        setStrokeWidth(2);
    }

    /**
     * Method to set color for piece
     * @param color
     */
    public void setColor(Color color) { this.color = color; setFill(color); }

    /**
     * Method to set size for piece
     * @param size
     */
    public void setSize(double size){
        setRadius(size / 2 - 5);
        setCenterX(size / 2);
        setCenterY(size / 2);
    }
}