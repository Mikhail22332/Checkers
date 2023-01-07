package com.example.demo;

import javafx.scene.paint.Color;

class Pawn extends javafx.scene.shape.Circle {

    private int row;
    private int col;
    private int direction; // ToDo Delete later if no reason in that
    private boolean selected;
    private Color color; // ToDo Delete later if no reason in that

    public Pawn(int row, int col, int direction, Color color) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.color = color;

        // Set the size and color of the pawn
        setRadius(20);
        setFill(color);

        // Place the pawn at the center of the square
        setCenterX(25);
        setCenterY(25);
    }

    public boolean canMoveTo(int row, int col) {
        // Check is right diagonal was chosen to move
        if(Math.abs(col - this.col) != Math.abs(row - this.row) || Math.abs(row - this.row) == 0 || Math.abs(col - this.col) == 0) {
            return false;
        }
        // Check if the destination square is in the correct direction relative to the pawn
        if ((row - this.row) / Math.abs(row - this.row) != direction) {
            return false;
        }
        if (Math.abs(col - this.col) != 1 && Math.abs(col - this.col) != 2) {
            return false;
        }

        // Check if the destination square is in the same column as the pawn
        else if(Math.abs(col - this.col) == 2) {
            Pawn temp = ClientApplication.getSquare((row + this.row) / 2, (col + this.col) / 2).getPawn();
            if(temp == null || temp.color == color) {
                return false;
            }
            else {
                ClientApplication.getSquare((row + this.row) / 2, (col + this.col) / 2).setPawn(null);
            }
        }

        return true;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setStrokeWidth(selected ? 3 : 0); // Not working probably
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }
}