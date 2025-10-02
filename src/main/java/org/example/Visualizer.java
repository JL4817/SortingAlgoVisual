package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Contains UI Components
public class Visualizer extends JFrame {
    private Panel sortPanel;
    private JButton btnBubble, btnSelection, btnRandomize, btnReset, btnExit;
    private JSlider speedSlider;
    private JTextField txtArraySize;
    private JLabel labelSpeed, labelArraySize, labelStatus;

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

        // Control panel at the top
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
                "Finds the minimum element from the unsorted portion and places it at the beginning.<br/><br/>" +
                "<b>How it works:</b><br/>" +
                "• <b>Current position (i):</b> The position you're trying to fill with the minimum value<br/>" +
                "• <b>Search range (j from i+1 to n-1):</b> The unsorted portion where you search for the minimum<br/><br/>" +
                "<b>Time Complexity:</b> O(n²)<br/>" +
                "<b>Example:</b> [5, 2, 8, 1] → [1, 2, 8, 5] → [1, 2, 8, 5] → [1, 2, 5, 8]" +
                "</div></html>");

        btnRandomize = new JButton("Randomize");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Quit Program");

        // Array size input
        labelArraySize = new JLabel("Array Size:");
        txtArraySize = new JTextField("50", 5);

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

        // Add components to control panel
        controlPanel.add(btnBubble);
        controlPanel.add(btnSelection);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(btnRandomize);
        controlPanel.add(btnReset);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(labelArraySize);
        controlPanel.add(txtArraySize);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(labelSpeed);
        controlPanel.add(speedPanel);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(btnExit);

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
        btnRandomize.setEnabled(enabled);
        btnReset.setEnabled(enabled);
    }
}