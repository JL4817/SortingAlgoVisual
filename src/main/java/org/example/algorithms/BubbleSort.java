package org.example.algorithms;

import org.example.Panel;

public class BubbleSort {

    /*
    The method takes an array to sort, a panel for visualization, and a delay between steps
     */
    public static void sort(int[] array, Panel panel, int delay) throws InterruptedException {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements
                panel.updateColors(j, j + 1, Panel.COMPARING);
                panel.repaint();
                Thread.sleep(delay);

                if (array[j] > array[j + 1]) {
                    // Change colors to swapping
                    panel.updateColors(j, j + 1, Panel.SWAPPING);
                    panel.repaint();
                    Thread.sleep(delay);

                    // Switch
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                // Change to default color
                panel.updateColors(j, j + 1, Panel.DEFAULT);
            }
            // Mark last element as sorted
            panel.updateColors(n - i - 1, -1, Panel.SORTED);
            panel.repaint();
        }
        // Mark first element as sorted
        panel.updateColors(0, -1, Panel.SORTED);
        panel.repaint();
    }
}