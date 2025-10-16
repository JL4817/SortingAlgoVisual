package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualizer extends JFrame {
    private Panel sortPanel;
    private JButton btnBubble, btnSelection, btnInsertion, btnMerge, btnQuick, btnHeap, btnRandomize, btnReset, btnExit, btnPause;
    private JSlider speedSlider;
    private JTextField txtArraySize, txtPivot;
    private JLabel labelSpeed, labelArraySize, labelStatus, labelPivot;
    private SortingController sortingController;

    // Modern color scheme
    private static final Color DARK_BG = new Color(26, 32, 44);
    private static final Color PANEL_BG = new Color(37, 47, 63);
    private static final Color ACCENT_BLUE = new Color(66, 153, 225);
    private static final Color TEXT_PRIMARY = new Color(237, 242, 247);
    private static final Color TEXT_SECONDARY = new Color(160, 174, 192);
    private static final Color BUTTON_HOVER = new Color(49, 130, 206);

    public Visualizer() {
        setTitle("Sorting Algorithm Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        sortingController = new SortingController();
        sortPanel = new Panel();
        sortPanel.setSortingController(sortingController);

        setupUI();
    }

    private void setupUI() {
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        ToolTipManager.sharedInstance().setInitialDelay(0);

        // Main layout
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(DARK_BG);

        // Control panel at the top
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(PANEL_BG);
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("SORTING VISUALIZER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        controlPanel.add(titleLabel, gbc);

        // Reset insets and gridwidth
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridwidth = 1;

        // Sorting algorithm buttons (First row)
        gbc.gridy = 1;

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        btnBubble = createModernButton("Bubble Sort");
        btnBubble.setToolTipText("<html><div style='font-size:14px;'>" +
                "<b>Bubble Sort</b><br/>" +
                "Repeatedly compares adjacent elements and swaps them if they're in the wrong order. Here: Sorting ascending <br/><br/>" +
                "<b>Time Complexity:</b> O(n²)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1] → [2, 5, 1, 8] → [2, 1, 5, 8] → [1, 2, 5, 8]" +
                "</div></html>");
        controlPanel.add(btnBubble, gbc);

        gbc.gridx = 1;
        btnSelection = createModernButton("Selection Sort");
        btnSelection.setToolTipText("<html><div style='font-size:14px;'>" +
                "<b>Selection Sort</b><br/>" +
                "Finds the minimum element from the unsorted portion and places it at the ith place.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Current position (i):</b> The position you're trying to fill with the minimum value<br/>" +
                "• <b>Search range (j from i+1 to n-1):</b> The unsorted portion where you search for the minimum<br/><br/>" +
                "<b>Time Complexity:</b> O(n²)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1] → [1, 2, 8, 5] → [1, 2, 8, 5] → [1, 2, 5, 8]" +
                "</div></html>");
        controlPanel.add(btnSelection, gbc);

        gbc.gridx = 2;
        btnInsertion = createModernButton("Insertion Sort");
        btnInsertion.setToolTipText("<html><div style='font-size:14px;'>" +
                "<b>Insertion Sort</b><br/>" +
                "Picks elements one by one from the unsorted portion and inserts them into their correct position in the sorted portion by shifting elements.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Pick element (i):</b> Select the next element from the unsorted portion (right side)<br/>" +
                "• <b>Store as key:</b> Save this element temporarily<br/>" +
                "• <b>Shift elements (j from i-1 to 0):</b> Move backward through the sorted portion, shifting each element one position to the right if it's larger than the key<br/>" +
                "• <b>Insert key:</b> Place the key in the gap created by shifting<br/><br/>" +
                "<b>Time Complexity:</b> O(n²) worst case, O(n) best case (already sorted)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1]<br/>" +
                "Pick 2: shift 5→right, insert 2 → [2, 5, 8, 1]<br/>" +
                "Pick 8: no shift needed → [2, 5, 8, 1]<br/>" +
                "Pick 1: shift 8,5,2→right, insert 1 → [1, 2, 5, 8]" +
                "</div></html>");
        controlPanel.add(btnInsertion, gbc);

        gbc.gridx = 3;
        btnMerge = createModernButton("Merge Sort");
        btnMerge.setToolTipText("<html><div style='font-size:14px;'>" +
                "<b>Merge Sort</b><br/>" +
                "Divides the array into halves recursively until single elements remain, then merges them back together in sorted order.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Divide:</b> Split the array into two halves recursively until each subarray has only one element<br/>" +
                "• <b>Conquer:</b> Single elements are considered sorted by definition<br/>" +
                "• <b>Merge:</b> Combine two sorted subarrays by comparing their elements and placing them in order<br/>" +
                "• <b>Repeat:</b> Continue merging until the entire array is reconstructed in sorted order<br/><br/>" +
                "<b>Time Complexity:</b> O(n log n) in all cases (best, average, worst)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1]<br/>" +
                "Divide: [5, 2] [8, 1] → [5] [2] [8] [1]<br/>" +
                "Merge: [2, 5] [1, 8]<br/>" +
                "Final merge: [1, 2, 5, 8]" +
                "</div></html>");
        controlPanel.add(btnMerge, gbc);

        gbc.gridx = 4;
        btnQuick = createModernButton("Quick Sort");
        btnQuick.setToolTipText("<html><div style='font-size:14px; width:450px;'>" +
                "<b>Quick Sort</b><br/>" +
                "Selects a 'pivot' element and partitions the array so that elements smaller than the pivot are on the left and larger elements<br/>" +
                " are on the right,<br/>" +
                "then recursively sorts the partitions.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Choose Pivot:</b> Select an element from the array as the pivot<br/>" +
                "&nbsp;&nbsp;(commonly the last element of the current subarray)<br/>" +
                "• <b>Partition:</b> Rearrange the array so elements less than the pivot<br/>" +
                "&nbsp;&nbsp;are on the left, greater elements on the right<br/>" +
                "• <b>Recurse:</b> Apply the same process to the left and right subarrays<br/>" +
                "&nbsp;&nbsp;<i>Each subarray selects its own pivot from its last element</i><br/>" +
                "• <b>Combine:</b> No explicit merge needed - the array is sorted in place<br/><br/>" +
                "<b> Time Complexity:</b> O(n log n) average case, O(n²) worst case<br/>" +
                "(rare with good pivot selection)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1]<br/>" +
                "• Pivot = 1 (last): Partition → [1] [5, 2, 8]<br/>" +
                "• Left subarray [5, 2, 8]: Pivot = 8 (last) → [1] [5, 2] [8]<br/>" +
                "• Left subarray [5, 2]: Pivot = 2 (last) → [1] [2] [5] [8]<br/>" +
                "Result: [1, 2, 5, 8]" +
                "</div></html>");
        controlPanel.add(btnQuick, gbc);

        gbc.gridx = 5;
        btnHeap = createModernButton("Heap Sort");
        btnHeap.setToolTipText("<html><div style='font-size:14px; width:450px;'>" +
                "<b>Heap Sort</b><br/>" +
                "Converts the array into a max heap structure, then repeatedly extracts the maximum element<br/>" +
                "and places it at the end of the sorted portion.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Build Max Heap:</b> Transform the array into a max heap where each parent<br/>" +
                "&nbsp;&nbsp;node is greater than its children<br/>" +
                "• <b>Extract Max:</b> Swap the root (largest element) with the last element<br/>" +
                "&nbsp;&nbsp;of the heap and reduce heap size<br/>" +
                "• <b>Heapify:</b> Restore the max heap property by moving the new root<br/>" +
                "&nbsp;&nbsp;down to its correct position<br/>" +
                "• <b>Repeat:</b> Continue extracting and heapifying until the heap is empty<br/><br/>" +
                "<b>Time Complexity:</b> O(n log n) in all cases<br/>" +
                "(consistent performance regardless of input)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1]<br/>" +
                "• Build heap: [8, 5, 2, 1] (max heap structure)<br/>" +
                "• Extract 8: [5, 1, 2] | [8]<br/>" +
                "• Heapify & Extract 5: [2, 1] | [5, 8]<br/>" +
                "• Extract 2: [1] | [2, 5, 8]<br/>" +
                "Result: [1, 2, 5, 8]" +
                "</div></html>");
        controlPanel.add(btnHeap, gbc);

        // Second row - Control buttons
        gbc.gridy = 2;

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnRandomize = createModernButton("Randomize");
        controlPanel.add(btnRandomize, gbc);

        gbc.gridx = 1;
        btnReset = createModernButton("Reset");
        controlPanel.add(btnReset, gbc);

        gbc.gridx = 2;
        btnPause = createModernButton("Pause");
        controlPanel.add(btnPause, gbc);

        gbc.gridx = 5;
        btnExit = createModernButton("Exit");
        controlPanel.add(btnExit, gbc);

        // Third row - Configuration controls
        gbc.gridy = 3;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        labelArraySize = createModernLabel("Array Size");
        controlPanel.add(labelArraySize, gbc);

        gbc.gridx = 1;
        txtArraySize = createModernTextField("50");
        controlPanel.add(txtArraySize, gbc);

        gbc.gridx = 2;
        labelPivot = createModernLabel("Pivot Strategy");
        controlPanel.add(labelPivot, gbc);

        gbc.gridx = 3;
        txtPivot = createModernTextField("");
        txtPivot.setToolTipText("<html><div style='font-size:12px;'>" +
                "Enter pivot selection strategy (Quick Sort):<br/>" +
                " enter a number (0 to array size-1)<br/>" +
                "&nbsp;&nbsp;Example: '0', '5', '25' to use that index" +
                "</div></html>");
        controlPanel.add(txtPivot, gbc);

        gbc.gridx = 4;
        labelSpeed = createModernLabel("Animation Speed");
        controlPanel.add(labelSpeed, gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 2;
        JPanel speedPanel = createSpeedPanel();
        controlPanel.add(speedPanel, gbc);

        speedSlider.addChangeListener(e -> { // Update the delay (speed)
            int delay = 505 - (speedSlider.getValue() * 5);
            sortingController.setCurrentDelay(delay);
        });

        // Status panel at bottom
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 15));
        statusPanel.setBackground(PANEL_BG);
        JLabel statusLabel = new JLabel("STATUS:");
        statusLabel.setForeground(TEXT_SECONDARY);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusPanel.add(statusLabel);

        labelStatus = new JLabel("Ready");
        labelStatus.setForeground(ACCENT_BLUE);
        labelStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusPanel.add(labelStatus);

        // Add panels to frame
        add(controlPanel, BorderLayout.NORTH);
        add(sortPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // Add action listeners
        btnBubble.addActionListener(e -> startSort("Bubble"));
        btnSelection.addActionListener(e -> startSort("Selection"));
        btnInsertion.addActionListener(e -> startSort("Insertion"));
        btnMerge.addActionListener(e -> startSort("Merge"));
        btnQuick.addActionListener(e -> startSort("Quick"));
        btnHeap.addActionListener(e -> startSort("Heap"));
        btnRandomize.addActionListener(e -> randomizeArray());
        btnReset.addActionListener(e -> resetArray());
        btnExit.addActionListener(e -> System.exit(0));
        btnPause.addActionListener(e -> pauseAlgo());
    }

    private void pauseAlgo() {
        sortingController.togglePause();
        if (sortingController.isPaused()) {
            btnPause.setText("Resume");
            labelStatus.setText("Paused");
        } else {
            btnPause.setText("Pause");
            labelStatus.setText("Resuming...");
        }
    }

    private void resetArray() {
        // Stop any running sort first
        if (sortingController.getCurrentThread() != null && sortingController.getCurrentThread().isAlive()) {
            sortingController.stop();
            try {
                sortingController.getCurrentThread().join(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        sortingController.reset();
        sortPanel.resetArray();
        labelStatus.setText("Array reset");
        btnPause.setText("Pause");
        enableButtons(true);
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(ACCENT_BLUE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_BLUE);
            }
        });

        return button;
    }

    private JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    private JTextField createModernTextField(String text) {
        JTextField field = new JTextField(text, 10);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(DARK_BG);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(74, 85, 104), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPanel createSpeedPanel() {
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BorderLayout());
        speedPanel.setBackground(PANEL_BG);

        JPanel speedLabelsPanel = new JPanel(new GridLayout(1, 3));
        speedLabelsPanel.setPreferredSize(new Dimension(200, 20));
        speedLabelsPanel.setBackground(PANEL_BG);

        JLabel slowLabel = new JLabel("Slow", SwingConstants.LEFT);
        slowLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        slowLabel.setForeground(TEXT_SECONDARY);

        JLabel normalLabel = new JLabel("Normal", SwingConstants.CENTER);
        normalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        normalLabel.setForeground(TEXT_SECONDARY);

        JLabel fastLabel = new JLabel("Fast", SwingConstants.RIGHT);
        fastLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        fastLabel.setForeground(TEXT_SECONDARY);

        speedLabelsPanel.add(slowLabel);
        speedLabelsPanel.add(normalLabel);
        speedLabelsPanel.add(fastLabel);

        speedSlider = new JSlider(1, 100, 50);
        speedSlider.setPreferredSize(new Dimension(200, 30));
        speedSlider.setBackground(PANEL_BG);
        speedSlider.setForeground(ACCENT_BLUE);

        speedPanel.add(speedLabelsPanel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        return speedPanel;
    }

    private void startSort(String algorithm) {
        // Stop any currently running sort
        if (sortingController.getCurrentThread() != null && sortingController.getCurrentThread().isAlive()) {
            sortingController.stop();
            try {
                sortingController.getCurrentThread().join(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        sortingController.reset();
        btnPause.setText("Pause");
        enableButtons(false);
        btnPause.setEnabled(true);
        labelStatus.setText("Sorting with " + algorithm + " Sort...");

        int delay = 505 - (speedSlider.getValue() * 5);

        Thread sortThread = new Thread(() -> {
            try {
                switch (algorithm) {
                    case "Bubble":
                        sortPanel.bubbleSort(delay);
                        break;
                    case "Selection":
                        sortPanel.selectionSort(delay);
                        break;
                    case "Insertion":
                        sortPanel.insertionSort(delay);
                        break;
                    case "Merge":
                        sortPanel.mergeSort(delay);
                        break;
                    case "Quick":
                        String pivotStrategy = txtPivot.getText().trim().toLowerCase();
                        sortPanel.quickSort(delay, pivotStrategy);
                        break;
                    case "Heap":
                        sortPanel.heapSort(delay);
                        break;
                }
                SwingUtilities.invokeLater(() -> {
                    labelStatus.setText("Sorting Complete!");
                    btnPause.setText("Pause");
                    enableButtons(true);
                });
            } catch (InterruptedException e) {
                SwingUtilities.invokeLater(() -> {
                    labelStatus.setText("Sorting stopped");
                    btnPause.setText("Pause");
                    enableButtons(true);
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    labelStatus.setText("Error: " + e.getMessage());
                    btnPause.setText("Pause");
                    enableButtons(true);
                });
            }
        });

        sortingController.setCurrentThread(sortThread);
        sortThread.start();
    }

    private void randomizeArray() {
        // Stop any running sort first
        if (sortingController.getCurrentThread() != null && sortingController.getCurrentThread().isAlive()) {
            sortingController.stop();
            try {
                sortingController.getCurrentThread().join(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        try {
            int size = Integer.parseInt(txtArraySize.getText());

            if (size < 2 || size > 500) {
                JOptionPane.showMessageDialog(this,
                        "Array size must be between 2 and 500",
                        "Invalid Size",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            sortingController.reset();
            sortPanel.randomizeArray(size);
            labelStatus.setText("Array randomized with " + size + " elements");
            btnPause.setText("Pause");
            enableButtons(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enableButtons(boolean enabled) {
        btnBubble.setEnabled(enabled);
        btnSelection.setEnabled(enabled);
        btnInsertion.setEnabled(enabled);
        btnMerge.setEnabled(enabled);
        btnQuick.setEnabled(enabled);
        btnHeap.setEnabled(enabled);
        btnRandomize.setEnabled(enabled);
        btnReset.setEnabled(enabled);
        btnPause.setEnabled(!enabled);
    }
}