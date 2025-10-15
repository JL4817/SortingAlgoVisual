package org.example.algorithms;

import org.example.Panel;

public class SelectionSort {

    /*
    The method takes an array to sort, a panel for visualization, and a delay between steps
     */
    public static void sort(int[] array, Panel panel, int delay) throws InterruptedException {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            panel.getSortingController().checkPauseAndStop();

            // Assume the current position has the minimum
            int minIndex = i;

            // Keep i grey throughout the search
            panel.updateColors(i, -1, Panel.DEFAULT);
            panel.repaint();
            Thread.sleep(delay);

            // Find the minimum element in the unsorted portion
            for (int j = i + 1; j < n; j++) {
                panel.getSortingController().checkPauseAndStop();

                // Keep i grey, make i+1 onwards red when searching
                panel.updateColors(i, -1, Panel.DEFAULT);
                panel.updateColors(minIndex, j, Panel.COMPARING);
                panel.repaint();
                Thread.sleep(delay);

                if (array[j] < array[minIndex]) {
                    // Found a new minimum
                    minIndex = j;
                }
            }

            // Swap the found minimum with the first element of unsorted portion
            if (minIndex != i) {
                panel.updateColors(i, minIndex, Panel.SWAPPING);
                panel.repaint();
                Thread.sleep(delay);

                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }

            // Reset unsorted elements to default before marking sorted
            for (int k = i + 1; k < n; k++) {
                panel.updateColors(k, -1, Panel.DEFAULT);
            }

            // Mark the current position as sorted
            panel.updateColors(i, -1, Panel.SORTED);
            panel.repaint();
            Thread.sleep(delay);
        }

        // Mark last element as sorted
        panel.updateColors(n - 1, -1, Panel.SORTED);
        panel.repaint();
    }
}