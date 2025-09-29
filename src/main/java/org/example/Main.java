package org.example;

import javax.swing.SwingUtilities;

// Safely start the GUI on the correct thread
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater((new Runnable() {
            @Override
            public void run() {
                Visualizer visualizer = new Visualizer();
                visualizer.setVisible(true);
            }
        }));
    }
}