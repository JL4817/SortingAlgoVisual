package org.example.algorithms;

import org.example.Panel;

public class InsertionSort {

    /*
    The method takes an array to sort, a panel for visualization, and a delay between steps
     */
    public static void sort(int[] array, Panel panel, int delay) throws InterruptedException {
        int n = array.length;

        // Start from index 1 (index 0 is considered sorted)
        for (int i = 1; i < n; i++) {
            panel.getSortingController().checkPauseAndStop();

            int key = array[i];  // Store the current element to insert
            int j = i - 1; // Start from the last element of the sorted portion [2, 5, 8 | 1], i value = 1, j value = 8
            // 1 shifts down leftwards.

            // Highlight the element being inserted
            panel.updateColors(i, -1, Panel.COMPARING);
            panel.repaint();
            Thread.sleep(delay);

            // Check if we need to shift
            while (j >= 0 && array[j] > key) {
                panel.getSortingController().checkPauseAndStop();

                // Two conditions must be true:
                // 1. j >= 0: We haven't gone past the beginning of the array
                // 2. array[j] > key: The element at j is bigger than what we're inserting
                // j is the backtracking area, left side.

                // Show comparison
                panel.updateColors(j, i, Panel.COMPARING);
                panel.repaint();
                Thread.sleep(delay);

                // Show swapping/shifting
                panel.updateColors(j, j + 1, Panel.SWAPPING);
                panel.repaint();
                Thread.sleep(delay);

                // Shift element to the right
                array[j + 1] = array[j];    // Copy element at j to position j+1 (shift right)
                j--;    // Move j one position to the left
            }

            // Insert the key at its correct position (in the gap)
            array[j + 1] = key;
            // Reset colors to default
            panel.updateColors(-1, -1, Panel.DEFAULT);
            panel.repaint();
        }

        // Mark all elements as sorted
        for (int i = 0; i < n; i++) {
            panel.updateColors(i, -1, Panel.SORTED);
        }
        panel.repaint();
    }
}