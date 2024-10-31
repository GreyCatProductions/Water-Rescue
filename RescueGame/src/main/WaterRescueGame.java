package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WaterRescueGame extends JFrame 
{
    Dimension standardButtonSize = new Dimension(150, 25);
    JFrame frame;
    Scenario chosenScenario;
    
    public WaterRescueGame()
    {
    	frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("WaterRescueGame");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new BorderLayout());
        CreateMainMenu();
        setVisible(true);
    }
    
    public void CreateMainMenu() 
    {
        frame.getContentPane().removeAll(); 

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.black); 

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.darkGray);

        JButton button = new JButton("Start Game");
        button.setPreferredSize(standardButtonSize);
        JTextField textField = new JTextField(15);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = textField.getText(); 
                JOptionPane.showMessageDialog(frame, "Playing as: " + userInput); 
                CreateLevelSelection();
            }
        });

        panel.add(button);
        panel.add(textField);

        centerPanel.add(panel);

        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.revalidate(); 
        frame.repaint();   
    }

    
    public void CreateLevelSelection() 
    {
        frame.getContentPane().removeAll(); 

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout()); 
        centerPanel.setBackground(Color.black); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        panel.setBackground(Color.darkGray);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        JTextArea textArea = new JTextArea("You are in charge of water rescue operations.\nUse assets to save as many lives as possible.");
        textArea.setLineWrap(true); 
        textArea.setWrapStyleWord(true); 
        textArea.setEditable(false); 
        textArea.setBackground(Color.lightGray); 
        textArea.setPreferredSize(new Dimension(500, 100)); 

        JPanel bottomTextSplitPanel = new JPanel();
        bottomTextSplitPanel.setLayout(new BorderLayout());
        bottomTextSplitPanel.setBackground(Color.darkGray);
        
        JTextArea scenarioTextArea = new JTextArea("Choose a scenario.");
        scenarioTextArea.setLineWrap(true); 
        scenarioTextArea.setWrapStyleWord(true); 
        scenarioTextArea.setEditable(false); 
        scenarioTextArea.setBackground(Color.lightGray); 
        scenarioTextArea.setPreferredSize(new Dimension(225, 100)); 
        
        JTextArea assetTextArea = new JTextArea();
        assetTextArea.setLineWrap(true); 
        assetTextArea.setWrapStyleWord(true); 
        assetTextArea.setEditable(false); 
        assetTextArea.setBackground(Color.lightGray); 
        assetTextArea.setPreferredSize(new Dimension(225, 100)); 

        panel.add(textArea);
        panel.add(Box.createVerticalStrut(10));
        bottomTextSplitPanel.add(assetTextArea, BorderLayout.EAST);
        bottomTextSplitPanel.add(scenarioTextArea, BorderLayout.WEST);
        panel.add(bottomTextSplitPanel);
        
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        centerPanel.add(panel, gbc);

        // Selection Panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        selectionPanel.setBackground(Color.darkGray);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        JButton button = new JButton("LAKE");
        JButton button2 = new JButton("SEA");
        JButton button3 = new JButton("OCEAN");
        
        JButton startGameButton = new JButton("START SCENARIO");
        startGameButton.setEnabled(false);
        startGameButton.setPreferredSize(standardButtonSize);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateMainGame(chosenScenario);
            }
        });
        
        // Asset and Scenario creation
        Asset plane = new Asset();
        plane.name = "rescue plane";
        plane.xRange = 5;
        plane.yRange = 2;
        plane.amount = 3;
        
        Scenario lake = new Scenario();
        lake.size = 25;
        lake.survivors = 3;
        lake.assets = new Asset[]{plane};
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder assetsInfo = new StringBuilder();
                for (Asset asset : lake.assets) {
                    assetsInfo.append("\n- ").append(asset.name)
                              .append(" (Range: ").append(asset.xRange).append(" x ").append(asset.yRange)
                              .append(", Amount: ").append(asset.amount).append(")");
                }

                scenarioTextArea.setText("LAKE\n\nSize: " + lake.size + " x " + lake.size + " NM\nSurvivors: " + lake.survivors);
                assetTextArea.setText("ASSETS:\n" + assetsInfo.toString());
                chosenScenario = lake;
                startGameButton.setEnabled(true);
            }
        });

        
        selectionPanel.add(button);
        selectionPanel.add(button2);
        selectionPanel.add(button3);

        gbc.gridy = 1;
        centerPanel.add(selectionPanel, gbc);
        
        //Start button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.darkGray);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        bottomPanel.add(startGameButton);

        gbc.gridy = 2;
        centerPanel.add(bottomPanel, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.revalidate(); 
        frame.repaint();  
    }

    public void CreateMainGame(Scenario scenario) {
        frame.getContentPane().removeAll(); 

        int assetPanelWidth = 200;
        int bottomPanelHeight = 100;
        int gridSize = Math.min(frame.getHeight() - bottomPanelHeight, frame.getWidth() - assetPanelWidth);

        JPanel assetPanel = createAssetPanel(assetPanelWidth);
        JPanel bottomPanel = createBottomPanel(bottomPanelHeight);
        JPanel mainGamePanel = createMainGamePanel(scenario, gridSize);

        frame.add(mainGamePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(assetPanel, BorderLayout.EAST);
        frame.revalidate(); 
        frame.repaint();  
    }

    private JPanel createAssetPanel(int width) {
        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new BoxLayout(assetPanel, BoxLayout.Y_AXIS));
        assetPanel.setBackground(Color.darkGray);
        assetPanel.setPreferredSize(new Dimension(width, frame.getHeight()));
        assetPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        return assetPanel;
    }

    private JPanel createBottomPanel(int height) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.darkGray);
        bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), height));
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray)); 
        return bottomPanel;
    }

    private JPanel createMainGamePanel(Scenario scenario, int gridSize) {
        JPanel mainGamePanel = new JPanel(new BorderLayout()); 
        mainGamePanel.setBackground(Color.black);

        JPanel grid = new JPanel();
        grid.setBackground(Color.DARK_GRAY);
        grid.setLayout(new GridLayout(scenario.size, scenario.size));
        grid.setPreferredSize(new Dimension(gridSize, gridSize));

        for(int row = 0; row < scenario.size; row++) {  
            for(int column = 0; column < scenario.size; column++) {
                JButton gameField = new JButton();
                gameField.setBackground(Color.black);
                grid.add(gameField);
            }
        }

        mainGamePanel.add(grid, BorderLayout.CENTER);
        return mainGamePanel;
    }



 
    public static void main(String[] args)
    {
    	new WaterRescueGame();
    }
}