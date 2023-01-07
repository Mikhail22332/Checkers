package com.example.demo.swing;
import java.awt.Color;

public class CheckersData {
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;

    private Color[][] board;

    public CheckersData() {
        board = new Color[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 1) {
                    board[row][col] = Color.RED;
                }
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 1) {
                    board[row][col] = Color.BLACK;
                }
            }
        }
    }

    public Color getColor(int row, int col) {
        return board[row][col];
    }

    public void setColor(int row, int col, Color color) {
        board[row][col] = color;
    }
}