package org.example.algorithms;

import org.example.Panel;
import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] array, Panel panel, int delay, String pivotStrategy) throws InterruptedException {
        quickSortRecursive(array, 0, array.length - 1, panel, delay, pivotStrategy);

        // Mark all as sorted
        for (int i = 0; i < array.length; i++) {
            panel.getSortingController().checkPauseAndStop();
            panel.updateColors(i, -1, Panel.SORTED);
        }
        panel.repaint();
    }

    private static void quickSortRecursive(int[] array, int low, int high, Panel panel, int delay, String pivotStrategy) throws InterruptedException {
        panel.getSortingController().checkPauseAndStop();

        if (low < high) {
            int pivotIndex = partition(array, low, high, panel, delay, pivotStrategy);
            quickSortRecursive(array, low, pivotIndex - 1, panel, delay, pivotStrategy);
            quickSortRecursive(array, pivotIndex + 1, high, panel, delay, pivotStrategy);
        }
    }

    private static int partition(int[] array, int low, int high, Panel panel, int delay, String pivotStrategy) throws InterruptedException {
        // Select and move pivot to end
        int pivotIndex = selectPivot(low, high, pivotStrategy);
        swap(array, pivotIndex, high);
        panel.updateColors(pivotIndex, high, Panel.SWAPPING);
        panel.repaint();
        Thread.sleep(delay);

        int i = low - 1;

        // Partition process
        for (int j = low; j < high; j++) {
            panel.getSortingController().checkPauseAndStop();

            panel.updateColors(j, high, Panel.COMPARING);
            panel.repaint();
            Thread.sleep(delay);

            if (array[j] <= array[high]) {
                i++;
                swap(array, i, j);
                panel.updateColors(i, j, Panel.SWAPPING);
                panel.repaint();
                Thread.sleep(delay);
            }

            panel.updateColors(j, high, Panel.DEFAULT);
        }

        // Place pivot in correct position
        swap(array, i + 1, high);
        panel.updateColors(i + 1, high, Panel.SORTED);
        panel.repaint();
        Thread.sleep(delay);

        return i + 1;
    }

    private static int selectPivot(int low, int high, String pivotStrategy) {
        if (pivotStrategy.equals("random")) {
            return low + random.nextInt(high - low + 1);
        }

        // Try to parse as specific index
        try {
            int userIndex = Integer.parseInt(pivotStrategy);
            if (userIndex >= low && userIndex <= high) {
                return userIndex;
            } else if (userIndex < low) {
                return low;
            } else {
                return high;
            }
        } catch (NumberFormatException e) {
            return high;
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}