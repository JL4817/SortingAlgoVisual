package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Contains UI Components
public class Visualizer extends JFrame {
    private Panel sortPanel;
    private JButton btnBubble, btnSelection, btnInsertion, btnMerge, btnQuick, btnRandomize, btnReset, btnExit;
    private JSlider speedSlider;
    private JTextField txtArraySize, txtPivot;
    private JLabel labelSpeed, labelArraySize, labelStatus, labelPivot;

    // Sets up the main window and makes it full screen
    public Visualizer() {
        setTitle("Sorting Algorithm Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setupUI();
    }

    // Creates and positions the UI Components
    private void setupUI() {
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE); // Never auto-dismiss
        ToolTipManager.sharedInstance().setInitialDelay(0); // Show immediately

        // Main layout
        setLayout(new BorderLayout(10, 10));

        // Control panel at the top - using GridBagLayout for better spacing
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        // Sorting algorithm buttons
        btnBubble = new JButton("Bubble");
        btnBubble.setToolTipText("<html><div style='font-size:14px;'>" +
                "<b>Bubble Sort</b><br/>" +
                "Repeatedly compares adjacent elements and swaps them if they're in the wrong order.<br/><br/>" +
                "<b>Time Complexity:</b> O(n²)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1] → [2, 5, 1, 8] → [2, 1, 5, 8] → [1, 2, 5, 8]" +
                "</div></html>");

        btnSelection = new JButton("Selection");
        btnSelection.setToolTipText("<html><div style='font-size:14px;'>" +
                "<b>Selection Sort</b><br/>" +
                "Finds the minimum element from the unsorted portion and places it at the ith place.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Current position (i):</b> The position you're trying to fill with the minimum value<br/>" +
                "• <b>Search range (j from i+1 to n-1):</b> The unsorted portion where you search for the minimum<br/><br/>" +
                "<b>Time Complexity:</b> O(n²)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1] → [1, 2, 8, 5] → [1, 2, 8, 5] → [1, 2, 5, 8]" +
                "</div></html>");

        btnInsertion = new JButton("Insertion");
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

        btnMerge = new JButton("Merge");
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

        btnQuick = new JButton("Quick");
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

        btnRandomize = new JButton("Randomize");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Quit Program");

        // Array size input
        labelArraySize = new JLabel("Array Size:");
        txtArraySize = new JTextField("50", 5);

        // Pivot index input for Quick Sort
        labelPivot = new JLabel("Pivot Strategy:");
        txtPivot = new JTextField("", 8);
        txtPivot.setToolTipText("<html><div style='font-size:12px;'>" +
                "Enter pivot selection strategy (Quick Sort):<br/>" +
                " enter a number (0 to array size-1)<br/>" +
                "&nbsp;&nbsp;Example: '0', '5', '25' to use that index" +
                "</div></html>");

        // Speed slider with labels
        labelSpeed = new JLabel("Speed:");
        speedSlider = new JSlider(1, 100, 50);
        speedSlider.setPreferredSize(new Dimension(150, 30));

        // Panel to hold the speed labels and slider
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BorderLayout());

        // Label panel with GridLayout for positioning
        JPanel speedLabelsPanel = new JPanel(new GridLayout(1, 3));
        speedLabelsPanel.setPreferredSize(new Dimension(150, 20));

        JLabel slowLabel = new JLabel("Slow", SwingConstants.LEFT);
        slowLabel.setFont(new Font(slowLabel.getFont().getName(), Font.BOLD, 10));

        JLabel normalLabel = new JLabel("Normal", SwingConstants.CENTER);
        normalLabel.setFont(new Font(normalLabel.getFont().getName(), Font.BOLD, 10));

        JLabel fastLabel = new JLabel("Fast", SwingConstants.RIGHT);
        fastLabel.setFont(new Font(fastLabel.getFont().getName(), Font.BOLD, 10));

        speedLabelsPanel.add(slowLabel);
        speedLabelsPanel.add(normalLabel);
        speedLabelsPanel.add(fastLabel);

        // Add labels above slider
        speedPanel.add(speedLabelsPanel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        // Status label
        labelStatus = new JLabel("Ready");

        // First row - Sorting buttons + etc.
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(btnBubble, gbc);

        gbc.gridx = 1;
        controlPanel.add(btnSelection, gbc);

        gbc.gridx = 2;
        controlPanel.add(btnInsertion, gbc);

        gbc.gridx = 3;
        controlPanel.add(btnMerge, gbc);

        gbc.gridx = 4;
        controlPanel.add(btnQuick, gbc);

        gbc.gridx = 5;
        controlPanel.add(btnRandomize, gbc);

        gbc.gridx = 6;
        controlPanel.add(btnReset, gbc);

        // Second row - Configuration controls
        gbc.gridy = 1;
        gbc.gridx = 0;
        controlPanel.add(labelArraySize, gbc);

        gbc.gridx = 1;
        controlPanel.add(txtArraySize, gbc);

        gbc.gridx = 2;
        controlPanel.add(labelPivot, gbc);

        gbc.gridx = 3;
        controlPanel.add(txtPivot, gbc);

        gbc.gridx = 4;
        controlPanel.add(labelSpeed, gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 2;
        controlPanel.add(speedPanel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 7;
        controlPanel.add(btnExit, gbc);

        // Status panel at bottom
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Status: "));
        statusPanel.add(labelStatus);

        // Sort panel (center)
        sortPanel = new Panel();

        // Add panels to frame
        add(controlPanel, BorderLayout.NORTH);
        add(sortPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // Add ActionListener buttons to do action when clicked
        btnBubble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSort("Bubble");
            }
        });

        btnSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSort("Selection");
            }
        });

        btnInsertion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSort("Insertion");
            }
        });

        btnMerge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSort("Merge");
            }
        });

        btnQuick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSort("Quick");
            }
        });

        btnRandomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeArray();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortPanel.resetArray();
                labelStatus.setText("Ready");
                enableButtons(true);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    /*
    Disables all buttons so user can't click during sorting
    Updates status label
    Gets delay value from speed slider
    Creates a new Thread (to sort in the background, no freezing)
    At the end re-enables buttons and updates status
     */
    private void startSort(String algorithm) {
        enableButtons(false);
        labelStatus.setText("Sorting with " + algorithm + " Sort...");

        // Get delay from slider (inverse - higher value = faster)
        // e.g. 101 (Max value) - 1 (Slider)  = 100 ms delay => Slow
        // Easier for users, higher speed values = animation is faster
        // Just made it slower
        int delay = 505 - (speedSlider.getValue() * 5);

        // Run sorting in separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            labelStatus.setText("Sorting Complete!");
                            enableButtons(true);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            labelStatus.setText("Error: " + e.getMessage());
                            enableButtons(true);
                        }
                    });
                }
            }
        }).start();
    }

    // Randomize the array but make it so it has more than 2 elements, since if it only has 1 element, it's already sorted.
    // Responds to button and delegates to Panel
    private void randomizeArray() {
        try {
            int size = Integer.parseInt(txtArraySize.getText());

            if (size < 2 || size > 500) {
                JOptionPane.showMessageDialog(this,
                        "Array size must be between 2 and 500",
                        "Invalid Size",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            sortPanel.randomizeArray(size);
            labelStatus.setText("Array randomized with " + size + " elements");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Enables or disables all action buttons at once (except for exit button)
    private void enableButtons(boolean enabled) {
        btnBubble.setEnabled(enabled);
        btnSelection.setEnabled(enabled);
        btnInsertion.setEnabled(enabled);
        btnMerge.setEnabled(enabled);
        btnQuick.setEnabled(enabled);
        btnRandomize.setEnabled(enabled);
        btnReset.setEnabled(enabled);
    }
}