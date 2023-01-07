package com.example.demo.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Checkerboard extends JPanel {
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    private int tileSize = 50;

    private static final Color LIGHT_COLOR = Color.WHITE;
    private static final Color DARK_COLOR = Color.BLACK;

    private List<Piece> pieces;

    public Checkerboard() {
        pieces = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 1) {
                    pieces.add(new Piece(row, col));
                }
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 1) {
                    pieces.add(new Piece(row, col));
                }
            }
        }
    }
    public Checkerboard(CheckersData data) {
        pieces = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 1) {
                    pieces.add(new Piece(row, col));
                }
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 1) {
                    pieces.add(new Piece(row, col));
                }
            }
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / tileSize;
                int row = e.getY() / tileSize;
                if (data.getColor(row, col) == Color.RED) {
                    if (movePiece(row, col, row + 1, col + 1)) {
                        data.setColor(row, col, null);
                        data.setColor(row + 1, col + 1, Color.RED);
                    }
                }
                repaint();
            }
        });
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Color color = (row + col) % 2 == 0 ? LIGHT_COLOR : DARK_COLOR;
                g2d.setColor(color);
                g2d.fill(new Rectangle2D.Double(col * tileSize, row * tileSize, tileSize, tileSize));
            }
        }

        for (Piece piece : pieces) {
            piece.draw(g2d, tileSize);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(NUM_COLS * tileSize, NUM_ROWS * tileSize);
    }
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = getPiece(fromRow, fromCol);
        if (piece == null) {
            return false;
        }
        if (isValidMove(piece, fromRow, fromCol, toRow, toCol)) {
            piece.setPosition(toRow, toCol);
            return true;
        }
        return false;
    }

    private boolean isValidMove(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        if (toRow < 0 || toRow >= NUM_ROWS || toCol < 0 || toCol >= NUM_COLS) {
            return false;
        }
        if (getPiece(toRow, toCol) != null) {
            return false;
        }
        if (piece.isKing()) {
            return true;
        }
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        if (rowDiff != 1 || colDiff != 1) {
            return false;
        }
        if (piece.isKing()) {
            return true;
        }
        if (piece.getColor() == Color.RED) {
            return toRow > fromRow;
        } else {
            return toRow < fromRow;
        }
    }

    private Piece getPiece(int row, int col) {
        for (Piece piece : pieces) {
            if (piece.getRow() == row && piece.getCol() == col) {
                return piece;
            }
        }
        return null;
    }
}