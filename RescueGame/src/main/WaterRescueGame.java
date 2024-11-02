package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.LinkedList;

public class WaterRescueGame extends JFrame 
{
	private Dimension standardButtonSize = new Dimension(175, 25);
    private JFrame frame;
    private Scenario chosenScenario;
    
    private JButton[][] buttons;
    private int[][] survivors;
    private Boolean[][] finalButtons; //Buttons that shall not be changed by reset anymore
    
    private int currentX = -1;
    private int currentY = -1;
    
    Color seaColor = new Color(0, 0, 51);
    Color selectedColor = new Color(0, 0, 200);
    Color searchedColor = new Color(50, 50, 150);
    Color foundColor = new Color(58, 18, 18);
    Color rescuedColor = new Color(18, 58, 22);
    
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
    
    //Windows
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
        
        JTextArea textArea = new JTextArea("\nYou are in charge of water rescue operations.\nUse assets to save as many lives as possible.");
        textArea.setLineWrap(true); 
        textArea.setWrapStyleWord(true); 
        textArea.setEditable(false); 
        textArea.setBackground(Color.lightGray); 
        textArea.setPreferredSize(new Dimension(500, 100)); 

        JPanel bottomTextSplitPanel = new JPanel();
        bottomTextSplitPanel.setLayout(new BorderLayout());
        bottomTextSplitPanel.setBackground(Color.darkGray);
        
        JTextArea scenarioTextArea = new JTextArea("\nChoose a scenario.");
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
        
        JButton startGameButton = new JButton("START SCENARIO");
        startGameButton.setEnabled(false);
        startGameButton.setPreferredSize(standardButtonSize);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateMainGame(chosenScenario);
            }
        });
        
        Asset scoutPlane = new Asset("scout plane", 5, 2, 3, false);
        Asset rescueShip = new Asset("rescue ship", 2, 2, 3, true);
        
        LinkedList<Scenario> scenarios = new LinkedList<Scenario>();
        scenarios.add(new Scenario("Pond", 10, 3, 50, new Asset[]{scoutPlane, rescueShip}));
        scenarios.add(new Scenario("Lake", 15, 3, 50, new Asset[]{scoutPlane, rescueShip}));
        scenarios.add(new Scenario("Bay", 25, 3, 50, new Asset[]{scoutPlane, rescueShip}));
        scenarios.add(new Scenario("Sea", 33, 3, 50, new Asset[]{scoutPlane, rescueShip}));
        scenarios.add(new Scenario("Ocean", 40, 3, 50, new Asset[]{scoutPlane, rescueShip}));
        
        for(Scenario scenario : scenarios)
        {
        	JButton scenarioButton = new JButton(scenario.name);
        	
        	scenarioButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                StringBuilder assetsInfo = new StringBuilder();
	                for (Asset asset : scenario.assets) {
	                    assetsInfo.append("\n- ").append(asset.name)
	                              .append(" (Range: ").append(asset.xRange).append(" x ").append(asset.yRange)
	                              .append(", Amount: ").append(asset.amount).append(")");
	                }
	
	                scenarioTextArea.setText("\nLAKE\n\nSize: " + scenario.size + " x " + scenario.size + " NM\nSurvivors: " + scenario.survivors);
	                assetTextArea.setText("\nASSETS:\n" + assetsInfo.toString());
	                chosenScenario = scenario;
	                startGameButton.setEnabled(true);
	            }
	        });
        	selectionPanel.add(scenarioButton);
        }

        gbc.gridy = 1;
        centerPanel.add(selectionPanel, gbc);
        
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

        buttons = new JButton[scenario.size][scenario.size];
        survivors = new int[scenario.size][scenario.size];
        finalButtons = new Boolean[scenario.size][scenario.size];
        for (int i = 0; i < scenario.size; i++) {
            for (int j = 0; j < scenario.size; j++) {
                finalButtons[i][j] = false;
            }
        }
        
        int assetPanelWidth = (int)(frame.getWidth() * 0.2f);

        JPanel assetPanel = createAssetPanel(assetPanelWidth);
        JPanel mainGamePanel = createMainGamePanel(scenario);
        
        placePeople(scenario);
        drawAssets(assetPanel, scenario.assets);

        frame.add(mainGamePanel, BorderLayout.CENTER);
        frame.add(assetPanel, BorderLayout.EAST);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newAssetPanelWidth = (int)(frame.getWidth() * 0.2f);
                assetPanel.setPreferredSize(new Dimension(newAssetPanelWidth, frame.getHeight()));

                int gridSize = (int) (frame.getHeight() * 0.9f);
                mainGamePanel.getComponent(0).setPreferredSize(new Dimension(gridSize, gridSize));  

                frame.revalidate();
            }
        });

        frame.revalidate(); 
        frame.repaint();  
    }

    //Main Game UI management
    private JPanel createAssetPanel(int width) {
        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new BoxLayout(assetPanel, BoxLayout.Y_AXIS));
        assetPanel.setBackground(Color.darkGray);
        assetPanel.setPreferredSize(new Dimension(width, frame.getHeight()));
        assetPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        return assetPanel;
    }

    private JPanel createMainGamePanel(Scenario scenario) {
        JPanel mainGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        mainGamePanel.setBackground(Color.DARK_GRAY);

        JPanel grid = new JPanel();
        grid.setBackground(Color.black);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        grid.setLayout(new GridLayout(scenario.size, scenario.size));
        grid.setPreferredSize(new Dimension((int) (frame.getHeight() * 0.9f), (int) (frame.getHeight() * 0.9f)));
        
        for(int row = 0; row < scenario.size; row++) {  
            for(int column = 0; column < scenario.size; column++) {
            	final int r = row;
            	final int c = column;
            	
                JButton gameField = new JButton();
                gameField.setBackground(seaColor);
                grid.add(gameField);
                buttons[row][column] = gameField;
                
                gameField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectButton(r, c);
                    }
                });
            }
        }

        mainGamePanel.add(grid, BorderLayout.CENTER);
        return mainGamePanel;
    }

    private void drawAssets(JPanel panelToDrawOn, Asset[] assets) {
        for (Asset asset : assets) {
            JPanel assetWindow = new JPanel();
            assetWindow.setBackground(Color.gray);
            assetWindow.setLayout(new BoxLayout(assetWindow, BoxLayout.Y_AXIS));
            
            String textAdd = asset.canRescue ? " X" : "";
            JTextArea textArea = new JTextArea(asset.name + textAdd + "\n\nPattern: (" + asset.xRange + ", " + asset.yRange + ")\nAmount: " + asset.amount);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            textArea.setBorder(BorderFactory.createEmptyBorder());
            textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, textArea.getPreferredSize().height));
	
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonsPanel.setBackground(Color.gray);
            
            JButton selectButton = new JButton("PREVIEW");
            selectButton.setMaximumSize(standardButtonSize);
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            selectButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    previewAssetRange(asset);
                }
            });
            
            JButton deployButton = new JButton("DEPLOY");
            deployButton.setMaximumSize(standardButtonSize);
            deployButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            deployButton.addActionListener(new ActionListener()
            {
            	@Override
            	public void actionPerformed(ActionEvent e)
            	{
            		UseAsset(asset);
            	}
            });
            
            buttonsPanel.add(selectButton);
            buttonsPanel.add(deployButton);
            
            assetWindow.add(textArea);
            assetWindow.add(buttonsPanel);

            int extraWidth = 100; 
            int width = assetWindow.getPreferredSize().width + extraWidth;
            int height = assetWindow.getPreferredSize().height;

            assetWindow.setMaximumSize(new Dimension(width, height));
            assetWindow.setPreferredSize(new Dimension(width, height));
            
            panelToDrawOn.add(assetWindow);
        }
    }

    private void placePeople(Scenario scenario) 
    {
        Random rand = new Random();
        int[] clusters = new int[scenario.clusters];

        for (int i = 0; i < scenario.clusters; i++) {
            clusters[i] = 1; 
        }

        int remainingSurvivors = scenario.survivors - scenario.clusters;
        for (int i = 0; i < remainingSurvivors; i++) {
            int randomClusterIndex = rand.nextInt(scenario.clusters);
            clusters[randomClusterIndex]++;
        }
        for (int peopleToPlace : clusters) {
            int x, y;
            do {
                x = rand.nextInt(scenario.size);
                y = rand.nextInt(scenario.size);
            } while (survivors[x][y] != 0); 

            survivors[x][y] = peopleToPlace;
        }
    }
  
    private void selectButton(int x, int y)
    {
    	resetGridColors();
    	
    	currentX = x;
    	currentY = y;
    	
		buttons[x][y].setBackground(selectedColor);
    }
    
    private void previewAssetRange(Asset asset)
    {
    	if(currentX == -1 || currentY == -1)
    		return;
    	
    	resetGridColors();

    	for(int i = currentX; i < currentX + asset.xRange; i++)
    	{
    		for(int j = currentY; j < currentY + asset.yRange; j++)
    		{
    			if(i < chosenScenario.size || j < chosenScenario.size)
    			{
        			buttons[i][j].setBackground(selectedColor);
    			}
    		}
    	}
    }
    
    private void resetGridColors()
    {
    	for(int i = 0; i < chosenScenario.size; i++)
    	{
        	for(int j = 0; j < chosenScenario.size; j++)
        	{
        		if(!finalButtons[i][j])
        			buttons[i][j].setBackground(seaColor);
        	}
    	}
    }
    
    private void UseAsset(Asset assetToUse)
    {
    	for(int x = currentX; x < currentX + assetToUse.xRange; x++)
    	{
    		for(int y = currentY; y < currentY + assetToUse.yRange; y++)
    		{
    			if(x >= chosenScenario.size || y >= chosenScenario.size)
    				continue;
    			if(survivors[x][y] > 0)
    			{
                    if(assetToUse.canRescue)
                    {
                    	buttons[x][y].setBackground(rescuedColor);
                        JOptionPane.showMessageDialog(frame, survivors[x][y] + " people saved."); 
                        survivors[x][y] = 0;
                    }
                    else
                    {
                    	buttons[x][y].setBackground(foundColor);
                    }
    			}
    			else
    			{
    				buttons[x][y].setBackground(searchedColor);
    			}
    			finalButtons[x][y] = true;
    		}
    	}
    	
    }
    public static void main(String[] args)
    {
        Font mainFont = new Font("Arial", Font.BOLD, 12);
        UIManager.put("Label.font", mainFont);
        UIManager.put("Button.font", mainFont);
        UIManager.put("TextField.font", mainFont);
        UIManager.put("TextArea.font", mainFont);
        UIManager.put("TextPane.font", mainFont);
        UIManager.put("MenuItem.font", mainFont);
    	new WaterRescueGame();
    }
}