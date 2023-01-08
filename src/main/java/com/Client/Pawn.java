package com.Client;

import javafx.scene.paint.Color;

class Pawn extends javafx.scene.shape.Circle {

    private int row;
    private int col;
    private int direction;
    private boolean selected;
    private boolean isQueen;
    private Color color;

    public Pawn(int row, int col, int direction, Color color) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.color = color;
        this.isQueen = false;

        // Set the size and color of the pawn
        setRadius(20);
        setFill(color);
        setStroke(Color.BLACK);

        // Place the pawn at the center of the square
        setCenterX(25);
        setCenterY(25);
    }
    public Pawn(int row, int col) {
        this.row = row;
        this.col = col;
        this.direction = 0;
        this.color = null;
        this.isQueen = false;

        // Set the size and color of the pawn
        setRadius(20);
        setFill(color);
        setStroke(Color.BLACK);

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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void makeQueen() {
        this.isQueen = true;
        setStroke(Color.RED);
        setStrokeWidth(2);
    }

    public void setColor(Color color) { this.color = color; setFill(color); }
}