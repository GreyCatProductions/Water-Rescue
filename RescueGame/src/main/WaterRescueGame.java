package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WaterRescueGame extends JFrame 
{
	private Dimension standardButtonSize = new Dimension(175, 25);
    private JFrame frame;
    private Scenario chosenScenario;
    
    private JButton[][] buttons;
    private int[][] survivors;
    private int survivorsSaved = 0;
    private int usesLeft;
    private Boolean[][] finalButtons; //Buttons that shall not be changed by reset anymore
    
    private int selectedX = -1;
    private int selectedY = -1;
    
    JPanel assetPanel;
    
    Color seaColor = new Color(0, 0, 51);
    Color selectedColor = new Color(0, 0, 200);
    Color searchedColor = new Color(50, 50, 150);
    Color foundColor = new Color(58, 18, 18);
    Color rescuedColor = new Color(18, 58, 22);
    
    private ImageIcon gameIcon;
    
    public WaterRescueGame()
    {
    	frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Water Rescue Operator");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new BorderLayout());
        
        try 
        {
        	gameIcon = new ImageIcon(getClass().getResource("WaterRescueOperator.png"));
	        frame.setIconImage(gameIcon.getImage());
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.darkGray);

        JButton button = new JButton("Start Game");
        button.setPreferredSize(standardButtonSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField textField = new JTextField(15);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = textField.getText(); 
                JOptionPane.showMessageDialog(frame, "Playing as: " + userInput); 
                CreateLevelSelection();
            }
        });

        panel.add(textField);
        panel.add(button);
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
        assetTextArea.setPreferredSize(new Dimension(225, 300)); 

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
        
        LinkedList<Scenario> scenarios = createScenarios();

        for (Scenario scenario : scenarios) {
            JButton scenarioButton = new JButton(scenario.name);

            scenarioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StringBuilder assetsInfo = new StringBuilder();
                    for (Asset asset : scenario.assets) {
                        assetsInfo.append("\n").append(asset.name)
                                  .append("\n(");
                        if (asset instanceof Vehicle) 
                        {
                            Vehicle vehicle = (Vehicle) asset;
                            assetsInfo.append("Range: ").append(vehicle.xRange).append(" x ").append(vehicle.yRange);
                        } else if (asset instanceof Sonar) 
                        {
                            Sonar sonar = (Sonar) asset;
                            assetsInfo.append("Radius: ").append(sonar.radius);
                        }
                        assetsInfo.append(", Amount: ").append(asset.amount).append(")\n");
                    }

                    scenarioTextArea.setText("\n" + scenario.name + "\n\nSize: " + scenario.size + " x " + scenario.size + " NM\n\nSurvivors: " + scenario.survivors + "\n\n" + scenario.description);
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
    
    private LinkedList<Scenario> createScenarios()
    {
    	LinkedList<Scenario> scenarios = new LinkedList<Scenario>();
        
        scenarios.add(new Scenario("Lake", 10, 4, 30,"A harsh storm summoned fast. The fishermen did not have time to leave it. 4 boats and 30 people are missing."
        		+ " The local fire department gave you all the ressources it has. Its not much but its all they have.",      		
        		new Asset[]{
        		new Vehicle ("Small Airtanker", 3, 3, 2, false),
        		new Vehicle ("Large Aitranker", 1, 5, 2, false), 
        		new Vehicle ("Dinghie", 4, 1, 1, true),
        		new Sonar ("Cheap Short Range Sonar Buoy", 2, 5, 0.3f)
        }));
        
        scenarios.add(new Scenario("Bay", 15, 3, 50,"", new Asset[]{}));
        scenarios.add(new Scenario("Sea", 25, 3, 50,"", new Asset[]{}));
        scenarios.add(new Scenario("Shore", 33, 3, 50,"", new Asset[]{}));
        scenarios.add(new Scenario("Ocean", 40, 3, 50,"", new Asset[]{}));
        
    	
    	return scenarios;
    }

    public void CreateMainGame(Scenario scenario)
    {
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

        assetPanel = createAssetPanel(assetPanelWidth);
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
                buttons[column][row] = gameField;
                
                gameField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectButton(c, r);
                    }
                });
            }
        }

        mainGamePanel.add(grid, BorderLayout.CENTER);
        return mainGamePanel;
    }

    private void drawAssets(JPanel panelToDrawOn, Asset[] assets) 
    {
        for (Asset asset : assets) 
        {
        	usesLeft += asset.amount;
        	
            JPanel assetWindow = new JPanel();
            assetWindow.setBackground(Color.gray);
            assetWindow.setLayout(new BoxLayout(assetWindow, BoxLayout.Y_AXIS));
            
            JTextArea textArea = new JTextArea();
            asset.myTextArea = textArea;
            SetText(asset);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            textArea.setBorder(BorderFactory.createEmptyBorder());
            textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, textArea.getPreferredSize().height));
            assetWindow.add(textArea);
	
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
            
            assetWindow.add(buttonsPanel);

            int extraWidth = 100; 
            int width = assetWindow.getPreferredSize().width + extraWidth;
            int height = assetWindow.getPreferredSize().height;

            assetWindow.setMaximumSize(new Dimension(width, height));
            assetWindow.setPreferredSize(new Dimension(width, height));
            assetWindow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
            
            panelToDrawOn.add(assetWindow);
        }
    }

    private void SetText(Asset asset)
    {
        String textAdd = "";
        if (asset instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) asset;
            if (vehicle.canRescue) {
                textAdd = " X";
            }
            asset.myTextArea.setText(vehicle.name + textAdd + "\n\nPattern: (" + vehicle.xRange + ", " + vehicle.yRange + ")\nAmount: " + vehicle.amount);
        } 
        else if (asset instanceof Sonar) {
            Sonar sonar = (Sonar) asset;
            asset.myTextArea.setText(sonar.name + "\n\nRadius: " + sonar.radius + "\nAmount: " + sonar.amount);
        }
    }
    
    private void UseAsset(Asset assetToUse) 
    {
    	if(assetToUse.amount <= 0)
    	{
    		JOptionPane.showMessageDialog(null, "no more uses left for " + assetToUse.name);
    		return;
    	}
    	
    	usesLeft --;
    	assetToUse.amount --;
    	SetText(assetToUse);
    	
    	assetPanel.revalidate();
    	assetPanel.repaint();
    	
        if (assetToUse instanceof Sonar) 
        {
        	Sonar sonar = (Sonar)assetToUse;
            int radius = sonar.radius;
            int[][] distanceMatrix = new int[chosenScenario.size][chosenScenario.size];
            
            for (int i = 0; i < chosenScenario.size; i++) 
            {
                Arrays.fill(distanceMatrix[i], Integer.MAX_VALUE);
            }

            Queue<Point> queue = new LinkedList<>();

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    int x = selectedX + dx;
                    int y = selectedY + dy;

                    if (x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
                        Math.abs(dx) + Math.abs(dy) <= radius && survivors[x][y] > 0) 
                    {                 
                        queue.add(new Point(x, y));
                        distanceMatrix[x][y] = 0;
                    }
                }
            }
            
            if(queue.isEmpty())
            {
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dy = -radius; dy <= radius; dy++) {
                        int x = selectedX + dx;
                        int y = selectedY + dy;

                        if(x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
                        Math.abs(x - selectedX) + Math.abs(y - selectedY) <= radius)
                        {
	                    	Random random = new Random();
	
	                        float noisedValue = radius + radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
	                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 
	                        buttons[x][y].setBackground(new Color(colorValue, 255 - colorValue, 0));
                        }
                    }
                }
            }

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            while (!queue.isEmpty()) 
            {
                Point current = queue.poll();
                int currentDistance = distanceMatrix[current.x][current.y];

                for (int[] dir : directions) 
                {
                    int nx = current.x + dir[0];
                    int ny = current.y + dir[1];

                    if (nx >= 0 && nx < chosenScenario.size && ny >= 0 && ny < chosenScenario.size &&
                        Math.abs(nx - selectedX) + Math.abs(ny - selectedY) <= radius &&
                        currentDistance + 1 < distanceMatrix[nx][ny]) 
                    {
                    	Random random = new Random();
                        distanceMatrix[nx][ny] = currentDistance + 1;
                        
                        float noisedValue = distanceMatrix[nx][ny] + radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
                        queue.add(new Point(nx, ny));

                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 
                        buttons[nx][ny].setBackground(new Color(colorValue, 255 - colorValue, 0));
                    }
                }
            }
            
            for (int i = 0; i < chosenScenario.size; i++) {
                for (int j = 0; j < chosenScenario.size; j++) {
                    if (distanceMatrix[i][j] == 0) 
                    {
                    	Random random = new Random();
                        float noisedValue =  radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 
                        buttons[i][j].setBackground(new Color(colorValue, 255 - colorValue, 0));
                    }
                }
            }
        }
    	else
		{
    		Vehicle vehicle = (Vehicle)assetToUse;
			for(int x = selectedX; x < selectedX + vehicle.xRange; x++)   
	    	{
	    		for(int y = selectedY; y < selectedY + vehicle.yRange; y++)
	    		{
	    			if(x >= chosenScenario.size || y >= chosenScenario.size)
	    				continue;
	    			if(survivors[x][y] > 0)
	    			{
	                    if(vehicle.canRescue)
	                    {
	                    	buttons[x][y].setBackground(rescuedColor);
	                        JOptionPane.showMessageDialog(frame, survivors[x][y] + " people saved."); 
	                        survivorsSaved += survivors[x][y];
	                        survivors[x][y] = 0;
	                        
	                        if(survivorsSaved == chosenScenario.survivors)
	                        {
	                        	endGame();
	                        }
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
    	
        if(usesLeft <= 0)
        {
        	endGame();
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
            do 
            {
                x = rand.nextInt(scenario.size);
                y = rand.nextInt(scenario.size);
            } while (survivors[x][y] != 0); 

            survivors[x][y] = peopleToPlace;
        }
    }
  
    private void selectButton(int x, int y)
    {
    	resetGridColors();
    	
    	selectedX = x;
    	selectedY = y;
    	
		buttons[x][y].setBackground(selectedColor);
    }
    
    private void previewAssetRange(Asset asset)
    {
    	if(selectedX == -1 || selectedY == -1)
    		return;
    	
    	resetGridColors();

    	if(asset instanceof Vehicle)
    	{
    		Vehicle vehicle = (Vehicle) asset;
	    	for(int i = selectedX; i < selectedX + vehicle.xRange; i++)
	    	{
	    		for(int j = selectedY; j < selectedY + vehicle.yRange; j++)
	    		{
	    			if(i < chosenScenario.size && j < chosenScenario.size)
	    			{
	        			buttons[i][j].setBackground(selectedColor);
	    			}
	    		}
	    	}
    	}
    	else
    	{
    		Sonar sonar = (Sonar) asset;
            for (int dx = -sonar.radius; dx <= sonar.radius; dx++) {
                for (int dy = -sonar.radius; dy <= sonar.radius; dy++) {
                    int x = selectedX + dx;
                    int y = selectedY + dy;

                    if (x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
                        Math.abs(dx) + Math.abs(dy) <= sonar.radius) 
                    {                 
                    	buttons[x][y].setBackground(selectedColor);
                    }
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
        		else
        			buttons[i][j].setBackground(searchedColor);
        	}
    	}
    }
    
    private void endGame()
    {
    	String title;
    	float ratio = (float) survivorsSaved / chosenScenario.survivors;

    	if (ratio == 1.0f) 
    	    title = "PERFECT JOB";
    	else if (ratio > 0.75f)
    		title = "GOOD JOB";
    	else if (ratio > 0.5f)
    		title = "MEDIOCRE JOB";
    	else if (ratio > 0.25f)
    		title = "BAD JOB";
    	else 
    	    title = "TERRIBLE JOB";

    	
        JDialog dialog = new JDialog(frame, title, true);
        dialog.setLayout(new BorderLayout());

        String message = survivorsSaved + " / " + chosenScenario.survivors + " people saved!\n\n";
        JLabel messageLabel = new JLabel("<html>" + message + "</html>", SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateLevelSelection();
                dialog.dispose();
            }
        });
        buttonPanel.add(okButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(new Dimension(300, 200));
        dialog.setLocationRelativeTo(frame); 
        dialog.setVisible(true); 
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
        new SaveSystem("Water Rescue Operator");
    	new WaterRescueGame();
    }
}