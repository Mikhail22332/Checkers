package com.example.demo.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Piece {
    private static final Color PIECE_COLOR = Color.RED;
    private static final Color KING_COLOR = Color.BLUE;
    private Color
    private boolean isKing;
    private int row;
    private int col;

    public Piece(int row, int col) {
        this.row = row;
        this.col = col;
        this.isKing = false;
    }

    public void makeKing() {
        isKing = true;
    }

    public boolean isKing() {
        return isKing;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void draw(Graphics2D g2d, int tileSize) {
        int x = col * tileSize + tileSize / 2;
        int y = row * tileSize + tileSize / 2;
        int radius = tileSize / 2 - 2;
        g2d.setColor(getColor());
        g2d.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    public Color getColor() {
        return isKing ? KING_COLOR : PIECE_COLOR;
    }
}