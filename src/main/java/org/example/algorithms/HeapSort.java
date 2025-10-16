package org.example.algorithms;

import org.example.Panel;

public class HeapSort {

    public static void sort(int[] array, Panel panel, int delay) throws InterruptedException {
        panel.getSortingController().setCurrentDelay(delay);

        int n = array.length;

        // Converts the array into a max heap structure where the largest element is at the root (index 0)
        // e.g. Original array: [4, 10, 3, 5, 1, 2]
        // After building max heap: [10, 5, 3, 4, 1, 2] - largest at root
        for (int i = n / 2 - 1; i >= 0; i--) {
            panel.getSortingController().checkPauseAndStop();
            heapify(array, n, i, panel, delay);
        }

        // The sorting part
        for (int i = n - 1; i > 0; i--) {
            panel.getSortingController().checkPauseAndStop();

            int currentDelay = panel.getSortingController().getCurrentDelay();

            panel.updateColors(0, i, Panel.COMPARING);
            panel.repaint();
            Thread.sleep(currentDelay);

            // Swap root with last element - move largest element to the end
            swap(array, 0, i);

            panel.updateColors(0, i, Panel.SWAPPING);
            panel.repaint();
            Thread.sleep(currentDelay);

            panel.updateColors(i, -1, Panel.SORTED);
            panel.repaint();

            // Reduce heap size (Exclude the sorted element)
            // Heapify again (Restore max heap property for the remaining elements)
            // Repeat
            heapify(array, i, 0, panel, delay);
        }

        // Mark the last element (root) as sorted
        panel.updateColors(0, -1, Panel.SORTED);
        panel.repaint();
    }

    // Arranges into max heap (not sorting)
    // Ensures: Parent >= both children
    private static void heapify(int[] array, int heapSize, int i, Panel panel, int delay) throws InterruptedException {
        panel.getSortingController().checkPauseAndStop();

        int largest = i;           // Initialize largest as root
        int left = 2 * i + 1;      // Left child index
        int right = 2 * i + 2;     // Right child index

        int currentDelay = panel.getSortingController().getCurrentDelay();

        // Highlight the current node being heapified
        panel.updateColors(i, -1, Panel.COMPARING);
        panel.repaint();
        Thread.sleep(currentDelay / 2);

        // Check if left child exists and is greater than root
        if (left < heapSize) { // Make duplicate method later
            currentDelay = panel.getSortingController().getCurrentDelay();

            panel.updateColors(i, left, Panel.COMPARING);
            panel.repaint();
            Thread.sleep(currentDelay);

            if (array[left] > array[largest]) {
                largest = left;
            }
        }

        // Check if right child exists and is greater than largest so far
        if (right < heapSize) {
            currentDelay = panel.getSortingController().getCurrentDelay();

            panel.updateColors(largest, right, Panel.COMPARING);
            panel.repaint();
            Thread.sleep(currentDelay);

            if (array[right] > array[largest]) {
                largest = right;
            }
        }

        // If largest is not root, swap and continue heapifying
        if (largest != i) {
            // Show the swap
            panel.updateColors(i, largest, Panel.SWAPPING);
            panel.repaint();
            Thread.sleep(delay);

            swap(array, i, largest);

            panel.repaint();
            Thread.sleep(currentDelay / 2);

            // Reset colors before recursive call
            panel.updateColors(i, largest, Panel.DEFAULT);
            panel.repaint();

            // Recursively heapify the affected subtree
            heapify(array, heapSize, largest, panel, delay);
        } else {
            // No swap needed, reset colors
            panel.updateColors(i, left, Panel.DEFAULT);
            panel.updateColors(i, right, Panel.DEFAULT);
            panel.repaint();
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}