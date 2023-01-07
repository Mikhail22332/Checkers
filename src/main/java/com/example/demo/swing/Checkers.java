package com.example.demo.swing;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Checkers {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Checkerboard checkerboard = new Checkerboard(new CheckersData());
        frame.add(checkerboard);

        frame.setResizable(true);
        checkerboard.setPreferredSize(new Dimension(640,640));

        frame.pack();
        frame.setVisible(true);

        // Set the size of the tiles to be the width of the window divided by 8
        int tileSize = frame.getWidth() / 8;
        checkerboard.setTileSize(tileSize);

        // Update the tile size when the window is resized
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int tileSize = (Math.min( frame.getHeight() - 40, frame.getWidth() - 20)) / 8;

                checkerboard.setTileSize(tileSize);
                checkerboard.repaint();
            }
        });
    }
}