package org.example;

import org.example.algorithms.BubbleSort;
import org.example.algorithms.SelectionSort;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

// Handles display, animation and color etc.
public class Panel extends JPanel {

    // Color state constants (Easier for Algorithm classes)
    public static final int DEFAULT = 0;
    public static final int COMPARING = 1;
    public static final int SWAPPING = 2;
    public static final int SORTED = 3;

    private int[] array;    // Height values
    private int[] colors;   // Color state
    private int arraySize = 50; // Default bars to display when starting the program;

    // Initializes the panel
    public Panel() {
        setBackground(Color.WHITE);
        randomizeArray(arraySize);
    }

    public void randomizeArray(int size) {
        arraySize = size;
        array = new int[arraySize];
        colors = new int[arraySize];

        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(400) + 50;
            colors[i] = DEFAULT;
        }
        repaint();
    }

    // Reset array back to default
    public void resetArray() {
        for (int i = 0; i < arraySize; i++) {
            colors[i] = DEFAULT;
        }
        repaint();
    }

    // Public method to update colors (for algorithm classes)
    public void updateColors(int index1, int index2, int state) {
        if (index1 >= 0 && index1 < arraySize) colors[index1] = state;
        if (index2 >= 0 && index2 < arraySize) colors[index2] = state;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / arraySize;
        int maxValue = 500; // Max possible value

        // Draw bars
        for (int i = 0; i < arraySize; i++) {
            int barHeight = (int) ((double) array[i] / maxValue * (height - 60));
            int x = i * barWidth;
            int y = height - barHeight - 40;

            // Set color based on state
            switch (colors[i]) {
                case COMPARING:
                    g.setColor(Color.RED);
                    break;
                case SWAPPING:
                    g.setColor(Color.BLUE);
                    break;
                case SORTED:
                    g.setColor(Color.GREEN);
                    break;
                default:
                    g.setColor(Color.GRAY);
            }

            g.fillRect(x, y, barWidth - 2, barHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth - 2, barHeight);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            String index = String.valueOf(i);
            int indexX = x + (barWidth - g.getFontMetrics().stringWidth(index))/2;
            g.drawString(index, indexX, height - 20);
        }
    }

    // Sorting methods that delegate to algorithm class (Bubble)
    public void bubbleSort(int delay) throws InterruptedException {
        BubbleSort.sort(array, this, delay);
    }

    public void selectionSort(int delay) throws InterruptedException {
        SelectionSort.sort(array, this, delay);
    }


}

