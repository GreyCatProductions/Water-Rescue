package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WaterRescueGame extends JFrame {
    static final int GRID_SIZE = 25;
    static final int PEOPLE_COUNT = 10;
    static final int TILE_SIZE = 30;

    private JButton[][] gridButtons = new JButton[GRID_SIZE][GRID_SIZE];
    private boolean[][] peoplePositions = new boolean[GRID_SIZE][GRID_SIZE];
    private int rescuedPeople = 0;
    private int planeUses = 1;
    private int shipUses = 1;
    private JLabel statusLabel;

    public WaterRescueGame() {
        setTitle("Water Rescue Game");
        setSize(GRID_SIZE * TILE_SIZE + 100, GRID_SIZE * TILE_SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        
        statusLabel = new JLabel("Rescued: 0 / " + PEOPLE_COUNT);
        
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                button.setBackground(Color.CYAN);
                
                final int x = i;
                final int y = j;
                
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        placeRescueObject(x, y);
                    }
                });

                gridButtons[i][j] = button;
                gridPanel.add(button);
            }
        }

        placePeopleRandomly();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        JButton resetButton = new JButton("Reset Game");

        controlPanel.add(statusLabel);
        controlPanel.add(new JLabel("Click on a tile to use your assets"));
        controlPanel.add(resetButton);

        resetButton.addActionListener(e -> resetGame(resetButton));
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        setVisible(true);
    }

    private void placePeopleRandomly() {
        Random rand = new Random();
        int placed = 0;
        while (placed < PEOPLE_COUNT) {
            int x = rand.nextInt(GRID_SIZE);
            int y = rand.nextInt(GRID_SIZE);
            if (!peoplePositions[x][y]) {
                peoplePositions[x][y] = true;
                placed++;
            }
        }
    }

    private void placeRescueObject(int x, int y) {
        if (planeUses > 0 || shipUses > 0) {
            String[] options = new String[]{"Place Plane (5x20)", "Place Ship (10x10)", "Cancel"};
            int choice = JOptionPane.showOptionDialog(this, 
                    "Choose an asset to uset at (" + x + ", " + y + "):",
                    "Place Rescue Asset",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, options, options[0]);
            
            int rescued = 0;
            if (choice == 0 && planeUses > 0) {
                planeUses--;
                rescued = placeObjectAndRescue(x, y, 5, 20);
            } else if (choice == 1 && shipUses > 0) {
                shipUses--;
                rescued = placeObjectAndRescue(x, y, 10, 10);
            } else if (choice != 2) {
                JOptionPane.showMessageDialog(this, "No uses left for the chosen asset.");
            }
            
            rescuedPeople += rescued;
            statusLabel.setText("Rescued: " + rescuedPeople + " / " + PEOPLE_COUNT);
            checkEndGame();
        }
    }

    private int placeObjectAndRescue(int x, int y, int width, int height) {
        int rescued = 0;
        for (int i = x; i < x + height && i < GRID_SIZE; i++) {
            for (int j = y; j < y + width && j < GRID_SIZE; j++) {
                if (peoplePositions[i][j]) {
                    peoplePositions[i][j] = false;
                    gridButtons[i][j].setBackground(Color.GREEN);
                    rescued++;
                }
                else
                {
                    gridButtons[i][j].setBackground(Color.BLACK);
                }
            }
        }
        return rescued;
    }

    private void checkEndGame() {
        if (planeUses == 0 && shipUses == 0) {
            JOptionPane.showMessageDialog(this, "Game Over! You rescued " + rescuedPeople + " people.");
        }
    }

    private void resetGame(JButton resetButton) {
        rescuedPeople = 0;
        planeUses = 1;
        shipUses = 1;
        peoplePositions = new boolean[GRID_SIZE][GRID_SIZE];
        statusLabel.setText("Rescued: 0 / " + PEOPLE_COUNT);
        
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridButtons[i][j].setBackground(Color.CYAN);
            }
        }
        
        placePeopleRandomly();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WaterRescueGame::new);
    }
}

