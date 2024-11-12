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
    private Boolean[][] changedButtons;
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
    
    public WaterRescueGame() //Prepares frame and opens main menu
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
    public void CreateMainMenu() //Switches to main menu
    {
        frame.getContentPane().removeAll(); 

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.black); 
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.black);

        JLabel title = new JLabel("WATER RESCUE OPERATOR");
        title.setForeground(selectedColor);
        title.setBackground(Color.black);
        title.setHorizontalAlignment(JLabel.CENTER);
        Font currentFont = title.getFont();
        Font newFont = currentFont.deriveFont(64f);
        title.setFont(newFont);
        title.setBorder(null);

        titlePanel.add(title);
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.darkGray);
        
        JLabel instructionText = new JLabel("ENTER YOUR NAME");
        instructionText.setHorizontalAlignment(JLabel.CENTER); 
        instructionText.setBorder(null);
        instructionText.setForeground(Color.WHITE);
        instructionText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("Start Game");
        button.setPreferredSize(standardButtonSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField enterField = new JTextField(15);
        enterField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterField.setHorizontalAlignment(JTextField.CENTER); 
        enterField.setMaximumSize(new Dimension(200, 20)); 
        
        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String userInput = enterField.getText(); 
                if (userInput.length() >= 3 && userInput.length() <= 20 && userInput.matches("[a-zA-Z]+")) 
                {
                    JOptionPane.showMessageDialog(frame, "Playing as: " + userInput);
	                CreateLevelSelection();
                }
                else
                {
                	JOptionPane.showMessageDialog(frame, "Name must be 3-20 characters and contain only letters.");
                	enterField.setText("");
                }
            }
        });

        JPanel titleMenuSplitPanel = new JPanel();
        titleMenuSplitPanel.setLayout(new BoxLayout(titleMenuSplitPanel, BoxLayout.Y_AXIS));
        titleMenuSplitPanel.setBackground(Color.BLACK);
        
        panel.add(instructionText);
        panel.add(Box.createVerticalStrut(10)); 
        panel.add(enterField);
        panel.add(Box.createVerticalStrut(10)); 
        panel.add(button);
        
        titleMenuSplitPanel.add(titlePanel);
        titleMenuSplitPanel.add(Box.createVerticalStrut(50)); 
        titleMenuSplitPanel.add(panel);
        centerPanel.add(titleMenuSplitPanel);
        
        //Credits
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel credits = new JLabel("MADE BY OLEG SHAPOVALOV");
        credits.setForeground(Color.WHITE);
        creditsPanel.add(credits);
        creditsPanel.setBackground(Color.BLACK);

        frame.add(creditsPanel, BorderLayout.SOUTH);
        
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.revalidate(); 
        frame.repaint();   
    }

	public void CreateLevelSelection() // Switches to level selection
	{
	    frame.getContentPane().removeAll();
	
	    JPanel centerPanel = new JPanel(new GridBagLayout());
	    centerPanel.setBackground(Color.black);
	
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.insets = new Insets(10, 10, 10, 10);
	
	    // Main text area
	    JTextArea textArea = new JTextArea("\nYou are in charge of water rescue operations.\nUse assets to save as many lives as possible.");
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setEditable(false);
	    textArea.setBackground(Color.lightGray);
	    textArea.setPreferredSize(new Dimension(500, 100));
	    textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	
	    // Scenario and Asset Details Area
	    JPanel detailsPanel = new JPanel(new BorderLayout());
	    detailsPanel.setBackground(Color.darkGray);
	    detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    JTextArea scenarioTextArea = new JTextArea("\nChoose a scenario.");
	    scenarioTextArea.setLineWrap(true);
	    scenarioTextArea.setWrapStyleWord(true);
	    scenarioTextArea.setEditable(false);
	    scenarioTextArea.setBackground(Color.lightGray);
	    scenarioTextArea.setPreferredSize(new Dimension(225, 100));
	    scenarioTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	
	    JTextArea assetTextArea = new JTextArea();
	    assetTextArea.setLineWrap(true);
	    assetTextArea.setWrapStyleWord(true);
	    assetTextArea.setEditable(false);
	    assetTextArea.setBackground(Color.lightGray);
	    assetTextArea.setPreferredSize(new Dimension(225, 300));
	    assetTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	
	    detailsPanel.add(scenarioTextArea, BorderLayout.WEST);
	    detailsPanel.add(assetTextArea, BorderLayout.EAST);
	
	    // Panel for Main Information Text and Details
	    JPanel infoPanel = new JPanel();
	    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	    infoPanel.setBackground(Color.darkGray);
	    infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    infoPanel.add(textArea);
	    infoPanel.add(Box.createVerticalStrut(10));
	    infoPanel.add(detailsPanel);
	
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    centerPanel.add(infoPanel, gbc);
	
	    // Scenario Selection Panel
	    JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    selectionPanel.setBackground(Color.darkGray);
	    selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    JButton startGameButton = new JButton("START SCENARIO");
	    startGameButton.setEnabled(false);
	    startGameButton.setPreferredSize(standardButtonSize);
	    startGameButton.addActionListener(e -> CreateMainGame(chosenScenario));
	
	    LinkedList<Scenario> scenarios = createScenarios();
	
	    for (Scenario scenario : scenarios) {
	        JButton scenarioButton = new JButton(scenario.name);
	        scenarioButton.addActionListener(e -> {
	            StringBuilder assetsInfo = new StringBuilder();
	            for (Asset asset : scenario.assets) {
	                assetsInfo.append("\n").append(asset.name)
	                          .append("\n(");
	                if (asset instanceof Vehicle) {
	                    Vehicle vehicle = (Vehicle) asset;
	                    assetsInfo.append("Range: ").append(vehicle.xRange).append(" x ").append(vehicle.yRange);
	                } else if (asset instanceof Sonar) {
	                    Sonar sonar = (Sonar) asset;
	                    assetsInfo.append("Radius: ").append(sonar.radius);
	                }
	                assetsInfo.append(", Amount: ").append(asset.amount).append(")\n");
	            }
	
	            scenarioTextArea.setText("\n" + scenario.name + "\n\nSize: " + scenario.size + " x " + scenario.size + " NM\n\nSurvivors: " + scenario.survivors + "\n\n" + scenario.description);
	            assetTextArea.setText("\nASSETS:\n" + assetsInfo.toString());
	            chosenScenario = scenario;
	            startGameButton.setEnabled(true);
	        });
	        selectionPanel.add(scenarioButton);
	    }
	
	    gbc.gridy = 1;
	    centerPanel.add(selectionPanel, gbc);
	
	    // Bottom Panel with Start Button
	    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    bottomPanel.setBackground(Color.darkGray);
	    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    bottomPanel.add(startGameButton);
	
	    gbc.gridy = 2;
	    centerPanel.add(bottomPanel, gbc);
	
	    frame.add(centerPanel, BorderLayout.CENTER);
	    frame.revalidate();
	    frame.repaint();
	}

	private LinkedList<Scenario> createScenarios() //creates scenarios for level selection
	{
    	LinkedList<Scenario> scenarios = new LinkedList<Scenario>();
        
        scenarios.add(new Scenario("Lake", 10, 4, 30,"A harsh storm summoned fast. The fishermen did not have time to leave it. 4 boats and 30 people are missing."
        		+ " The local fire department gave you all the ressources it has. Its not much but its all they have.",      		
        		new Asset[]{
        		new Vehicle ("Small Airtanker", 4, 3, 2, false, "Searches a small area and marks found survivors"),
        		new Vehicle ("Large Aitranker", 1, 5, 2, false, "Searches a big area and marks found survivors"), 
        		new Vehicle ("Dinghie", 5, 1, 1, true, "Small but fast. Rescues survivors"),
        		new Sonar ("Short Range Sonar Buoy", 2, 5, 0.3f, "Uses sonar to listen for survivors. Green = loud, Red = silence")
        }));
        
        scenarios.add(new Scenario("Bay", 15, 5, 40,"The calm waters of the bay were quickly turned into a dangerous trap when an unexpected fog rolled in. "
        		+ "5 groups of recreational sailors are missing. The coast guard has mobilized what they can, but time is running out as the visibility decreases.",
        		new Asset[]{
        				new Vehicle ("Oiltanker", 1, 15, 1, false, "An Oiltanker passes the water. We can ask the crew to choose a certain route"), 
                		new Vehicle ("Scoutplane", 2, 2, 5, false, "Searches a big area and marks found survivors"), 
                		new Vehicle ("Dinghie", 4, 1, 1, true, "Small but fast. Rescues survivors"),
                		new Vehicle ("Rescue Ship", 3, 3, 3, true, "Ship specialized on rescue operations. Rescues survivors."),
                		new Sonar ("Sonar Buoy V1", 3, 5, 0.3f, "Uses sonar to listen for survivors. Green = loud, Red = silence"),
                		new Sonar ("Sonar Buoy V2", 1, 8, 0.25f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence")
        		}));
        scenarios.add(new Scenario("Sea", 25, 5, 140,"An unusually strong current has swept away several ships, leaving their crews stranded in the sea. 140 people are missing. We dont know how many ships.", 
        		new Asset[]{}));
        scenarios.add(new Scenario("Shore", 33, 3, 50,"", new Asset[]{}));
        scenarios.add(new Scenario("Ocean", 40, 3, 50,"", new Asset[]{}));
        
    	
    	return scenarios;
    }

    public void CreateMainGame(Scenario scenario) //Switches to main game with a scenario
    {
        frame.getContentPane().removeAll(); 

        buttons = new JButton[scenario.size][scenario.size];
        survivors = new int[scenario.size][scenario.size];
        finalButtons = new Boolean[scenario.size][scenario.size];
        changedButtons = new Boolean[scenario.size][scenario.size];
        
        for (int i = 0; i < scenario.size; i++) {
            for (int j = 0; j < scenario.size; j++) {
                finalButtons[i][j] = false;
                changedButtons[i][j] = false;
            }
        }
        
        usesLeft = 0;
        survivorsSaved = 0;
        
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
    private JPanel createAssetPanel(int width) 
{
        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new BoxLayout(assetPanel, BoxLayout.Y_AXIS));
        assetPanel.setBackground(Color.darkGray);
        assetPanel.setPreferredSize(new Dimension(width, frame.getHeight()));
        assetPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        return assetPanel;
    }

    private JPanel createMainGamePanel(Scenario scenario)
    {
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
            assetWindow.setLayout(new BorderLayout());
 
            JTextArea statsTextArea = new JTextArea();
            asset.myTextArea = statsTextArea;
            SetText(asset);
            statsTextArea.setLineWrap(true);
            statsTextArea.setWrapStyleWord(true);
            statsTextArea.setOpaque(false);
            statsTextArea.setEditable(false);
            statsTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            assetWindow.add(statsTextArea, BorderLayout.WEST);
            
            JTextArea descriptionTextArea = new JTextArea(asset.description);
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setWrapStyleWord(true);
            descriptionTextArea.setOpaque(false);
            descriptionTextArea.setEditable(false);
            descriptionTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            assetWindow.add(descriptionTextArea, BorderLayout.EAST);
	
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
            
            assetWindow.add(buttonsPanel, BorderLayout.SOUTH);

            int extraWidth = 100; 
            int width = assetWindow.getPreferredSize().width + extraWidth;
            int height = assetWindow.getPreferredSize().height + 15;

            assetWindow.setMaximumSize(new Dimension(width, height));
            assetWindow.setPreferredSize(new Dimension(width, height));
            assetWindow.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            
            panelToDrawOn.add(assetWindow);
            panelToDrawOn.add(Box.createVerticalStrut(10));
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
	                        changeFieldColor(x, y, new Color(colorValue, 255 - colorValue, 0));
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

                        changeFieldColor(nx, ny, new Color(colorValue, 255 - colorValue, 0));
                    }
                }
            }
            
            for (int x = 0; x < chosenScenario.size; x++) {
                for (int y = 0; y < chosenScenario.size; y++) {
                    if (distanceMatrix[x][y] == 0) 
                    {
                    	Random random = new Random();
                        float noisedValue =  radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 

                        changeFieldColor(x, y, new Color(colorValue, 255 - colorValue, 0));
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
	                    	changeFieldColor(x, y, foundColor);
	                    }
	    			}
	    			else
	    			{
	    				changeFieldColor(x, y, searchedColor);
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
    	
		changeFieldColor(x, y, selectedColor);
    }
    
    private void previewAssetRange(Asset asset)
    {
    	if(selectedX == -1 || selectedY == -1)
    		return;
    	
    	resetGridColors();

    	if(asset instanceof Vehicle)
    	{
    		Vehicle vehicle = (Vehicle) asset;
	    	for(int x = selectedX; x < selectedX + vehicle.xRange; x++)
	    	{
	    		for(int y = selectedY; y < selectedY + vehicle.yRange; y++)
	    		{
	    			if(x < chosenScenario.size && y < chosenScenario.size)
	    			{
	        			changeFieldColor(x, y, selectedColor);
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
                    	changeFieldColor(x, y, selectedColor);
                    }
                }
            }
    	}
    }
    
    private void resetGridColors() //Resets all grid cells that changed and are not in final state
    {
    	for(int i = 0; i < changedButtons.length; i++)
    	{
        	for(int j = 0; j < changedButtons.length; j++)
        	{
        		if(changedButtons[i][j])
        		{
	        		if(!finalButtons[i][j])
	        			buttons[i][j].setBackground(seaColor);
	        		else
	        			buttons[i][j].setBackground(searchedColor);
        		}
        	}
    	}
    }
    
    private void changeFieldColor(int x, int y, Color color) //Paints field to given color and marks it as changed
    {
        buttons[x][y].setBackground(color);
		changedButtons[x][y] = true;
    }
    
    private void endGame() //Enables end screen
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