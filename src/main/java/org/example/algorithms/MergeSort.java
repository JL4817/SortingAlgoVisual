package org.example.algorithms;

import org.example.Panel;

public class MergeSort {

    public static void sort(int[] array, Panel panel, int delay) throws InterruptedException {
        panel.getSortingController().setCurrentDelay(delay);

        mergeSort(array, 0, array.length - 1, panel, delay);

        // Mark all elements as sorted at the end
        for (int i = 0; i < array.length; i++) {
            panel.getSortingController().checkPauseAndStop();
            panel.updateColors(i, -1, Panel.SORTED);
        }
        panel.repaint();
    }

    private static void mergeSort(int[] array, int left, int right, Panel panel, int delay) throws InterruptedException {
        panel.getSortingController().checkPauseAndStop();

        int currDelay = panel.getSortingController().getCurrentDelay();

        if (left < right) { // Stops infinite recursion - means multiple elements remain
            int mid = left + (right - left) / 2; // Safer way to find mid (avoids overflow)

            // Highlight the entire range being divided with a distinct color
            for (int i = left; i <= right; i++) {
                panel.updateColors(i, -1, Panel.DEFAULT); // Show division boundaries
            }
            panel.repaint();
            Thread.sleep(currDelay / 2);

            // Recursively sort the left half
            mergeSort(array, left, mid, panel, currDelay);

            // Recursively sort the right half
            mergeSort(array, mid + 1, right, panel, currDelay);

            // Merge the two sorted halves
            merge(array, left, mid, right, panel, currDelay);
        }
    }

    private static void merge(int[] array, int left, int mid, int right, Panel panel, int delay) throws InterruptedException {

        int currDelay = panel.getSortingController().getCurrentDelay();

        int n1 = mid - left + 1; // Left subarray size (includes middle)
        int n2 = right - mid;     // Right subarray size (excludes middle)

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // Highlight the left subarray being merged (grey/default)
        for (int i = left; i <= mid; i++) {
            panel.updateColors(i, -1, Panel.DEFAULT);
        }
        // Highlight the right subarray being merged (comparing color)
        for (int i = mid + 1; i <= right; i++) {
            panel.updateColors(i, -1, Panel.COMPARING);
        }
        panel.repaint();
        Thread.sleep(currDelay);

        // Merge the temp arrays back into the original array
        int i = 0, j = 0;  // Indices for leftArray and rightArray
        int k = left;       // Index for merged array

        // Compare elements from both arrays and pick smaller one
        while (i < n1 && j < n2) {
            panel.getSortingController().checkPauseAndStop();

            // Highlight the TWO elements being compared
            panel.updateColors(left + i, mid + 1 + j, Panel.COMPARING);
            panel.repaint();
            Thread.sleep(currDelay);

            // Pick the smaller element and reconstruct the array
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }

            // Show the element that was just placed (swapping color = green)
            panel.updateColors(k, -1, Panel.SWAPPING);
            panel.repaint();
            Thread.sleep(currDelay);

            k++;
        }

        // Copy any remaining elements from leftArray
        while (i < n1) {
            panel.getSortingController().checkPauseAndStop();

            array[k] = leftArray[i];
            panel.updateColors(k, -1, Panel.SWAPPING);
            panel.repaint();
            Thread.sleep(currDelay / 2);
            i++;
            k++;
        }

        // Copy any remaining elements from rightArray
        while (j < n2) {
            panel.getSortingController().checkPauseAndStop();

            array[k] = rightArray[j];
            panel.updateColors(k, -1, Panel.SWAPPING);
            panel.repaint();
            Thread.sleep(currDelay / 2);
            j++;
            k++;
        }

        // Show the completed merge by highlighting the merged section briefly
        for (int idx = left; idx <= right; idx++) {
            panel.updateColors(idx, -1, Panel.SORTED);
        }
        panel.repaint();
        Thread.sleep(currDelay);

        // Reset to default color before returning
        for (int idx = left; idx <= right; idx++) {
            panel.updateColors(idx, -1, Panel.DEFAULT);
        }
        panel.repaint();
    }
}