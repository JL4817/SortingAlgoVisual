package org.example;

import org.example.algorithms.*;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class Panel extends JPanel {

    // Color state constants
    public static final int DEFAULT = 0;
    public static final int COMPARING = 1;
    public static final int SWAPPING = 2;
    public static final int SORTED = 3;

    // Modern color palette
    private static final Color DARK_BG = new Color(26, 32, 44);
    private static final Color BAR_DEFAULT = new Color(100, 116, 139);
    private static final Color BAR_COMPARING = new Color(251, 146, 60); // Orange
    private static final Color BAR_SWAPPING = new Color(59, 130, 246); // Blue
    private static final Color BAR_SORTED = new Color(52, 211, 153); // Green
    private static final Color TEXT_COLOR = new Color(148, 163, 184);
    private static final Color GRID_COLOR = new Color(51, 65, 85);

    private int[] array;
    private int[] colors;
    private int arraySize = 50;

    public Panel() {
        setBackground(DARK_BG);
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

    public void resetArray() {
        for (int i = 0; i < arraySize; i++) {
            colors[i] = DEFAULT;
        }
        repaint();
    }

    public void updateColors(int index1, int index2, int state) {
        if (index1 >= 0 && index1 < arraySize) colors[index1] = state;
        if (index2 >= 0 && index2 < arraySize) colors[index2] = state;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smooth graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / arraySize;
        int maxValue = 500;

        // Draw subtle grid lines
        g2d.setColor(GRID_COLOR);
        g2d.setStroke(new BasicStroke(1));
        for (int i = 0; i < 5; i++) {
            int y = i * (height - 60) / 5 + 20;
            g2d.drawLine(0, y, width, y);
        }

        // Draw bars with modern styling
        for (int i = 0; i < arraySize; i++) {
            int barHeight = (int) ((double) array[i] / maxValue * (height - 80));
            int x = i * barWidth;
            int y = height - barHeight - 50;

            // Set color based on state with gradient effect
            Color barColor;
            switch (colors[i]) {
                case COMPARING:
                    barColor = BAR_COMPARING;
                    break;
                case SWAPPING:
                    barColor = BAR_SWAPPING;
                    break;
                case SORTED:
                    barColor = BAR_SORTED;
                    break;
                default:
                    barColor = BAR_DEFAULT;
            }

            // Draw bar with gradient
            GradientPaint gradient = new GradientPaint(
                    x, y, barColor,
                    x, y + barHeight, barColor.darker()
            );
            g2d.setPaint(gradient);

            // Draw rounded rectangle for modern look
            int barPadding = Math.max(1, barWidth / 10);
            g2d.fillRoundRect(x + barPadding, y, barWidth - 2 * barPadding, barHeight, 4, 4);

            // Draw subtle border
            g2d.setColor(barColor.brighter());
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(x + barPadding, y, barWidth - 2 * barPadding, barHeight, 4, 4);

            // Draw index number at bottom (only if space permits)
            if (arraySize <= 100) {
                g2d.setColor(TEXT_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                String index = String.valueOf(i);
                int indexX = x + (barWidth - g2d.getFontMetrics().stringWidth(index)) / 2;
                g2d.drawString(index, indexX, height - 25);
            }
        }

        // Draw legend
        drawLegend(g2d, width, height);
    }

    private void drawLegend(Graphics2D g2d, int width, int height) {
        int legendY = height - 15;
        int legendX = 10;
        int boxSize = 12;
        int spacing = 120;

        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        // Default
        g2d.setColor(BAR_DEFAULT);
        g2d.fillRoundRect(legendX, legendY, boxSize, boxSize, 3, 3);
        g2d.setColor(TEXT_COLOR);
        g2d.drawString("Unsorted", legendX + boxSize + 5, legendY + 10);

        // Comparing
        legendX += spacing;
        g2d.setColor(BAR_COMPARING);
        g2d.fillRoundRect(legendX, legendY, boxSize, boxSize, 3, 3);
        g2d.setColor(TEXT_COLOR);
        g2d.drawString("Comparing", legendX + boxSize + 5, legendY + 10);

        // Swapping
        legendX += spacing;
        g2d.setColor(BAR_SWAPPING);
        g2d.fillRoundRect(legendX, legendY, boxSize, boxSize, 3, 3);
        g2d.setColor(TEXT_COLOR);
        g2d.drawString("Swapping", legendX + boxSize + 5, legendY + 10);

        // Sorted
        legendX += spacing;
        g2d.setColor(BAR_SORTED);
        g2d.fillRoundRect(legendX, legendY, boxSize, boxSize, 3, 3);
        g2d.setColor(TEXT_COLOR);
        g2d.drawString("Sorted", legendX + boxSize + 5, legendY + 10);
    }

    // Sorting methods
    public void bubbleSort(int delay) throws InterruptedException {
        BubbleSort.sort(array, this, delay);
    }

    public void selectionSort(int delay) throws InterruptedException {
        SelectionSort.sort(array, this, delay);
    }

    public void insertionSort(int delay) throws InterruptedException {
        InsertionSort.sort(array, this, delay);
    }

    public void mergeSort(int delay) throws InterruptedException {
        MergeSort.sort(array, this, delay);
    }

    public void quickSort(int delay, String pivotStrategy) throws InterruptedException {
        QuickSort.sort(array, this, delay, pivotStrategy);
    }

    public void heapSort(int delay) throws InterruptedException {
        HeapSort.sort(array, this, delay);
    }
}