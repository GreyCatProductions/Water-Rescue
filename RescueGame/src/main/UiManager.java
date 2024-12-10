package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class UiManager
{
	private Dimension standardButtonSize = new Dimension(175, 25);
	private JPanel assetPanel;
	private JPanel iconPanel;
	private JLabel xCorVisual;
	private JLabel yCorVisual;
    
    public static UiManager instance;
    
    private String chosenUserName;
    
    public UiManager()
    {
    	instance = this;
    }
    
    protected void CreateMainMenu() //Switches to main menu
    {
        GameManager.frame.getContentPane().removeAll(); 

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.black); 
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.black);

        JLabel title = new JLabel("WATER RESCUE OPERATOR");
        title.setForeground(GameColors.selectedColor);
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
            	chosenUserName = enterField.getText(); 
                if (chosenUserName.length() >= 3 && chosenUserName.length() <= 20 && chosenUserName.matches("[a-zA-Z]+")) 
                {
                    JOptionPane.showMessageDialog(GameManager.frame, "Playing as: " + chosenUserName);
	                CreateLevelSelection();
                }
                else
                {
                	JOptionPane.showMessageDialog(GameManager.frame, "Name must be 3-20 characters and contain only letters.");
                	enterField.setText("");
                }
            }
        });

        JPanel titleMenuSplitPanel = new JPanel();
        titleMenuSplitPanel.setLayout(new BoxLayout(titleMenuSplitPanel, BoxLayout.Y_AXIS));
        titleMenuSplitPanel.setBackground(Color.BLACK);
        
        JPanel logoContainer = new JPanel();
        logoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoContainer.setOpaque(false);
        
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/main/icons/MainGameLogo.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
       
        JLabel gameLogo = new JLabel(logoIcon);
        logoContainer.add(gameLogo);

        panel.add(instructionText);
        panel.add(Box.createVerticalStrut(10)); 
        panel.add(enterField);
        panel.add(Box.createVerticalStrut(10)); 
        panel.add(button);
        
        titleMenuSplitPanel.add(titlePanel); 
        titleMenuSplitPanel.add(logoContainer);
        titleMenuSplitPanel.add(panel);
        centerPanel.add(titleMenuSplitPanel);
        
        //Credits
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel credits = new JLabel("MADE BY OLEG SHAPOVALOV");
        credits.setForeground(Color.WHITE);
        creditsPanel.add(credits);
        creditsPanel.setBackground(Color.BLACK);

        GameManager.frame.add(creditsPanel, BorderLayout.SOUTH);
        
        GameManager.frame.add(centerPanel, BorderLayout.CENTER);
        
        GameManager.frame.revalidate(); 
        GameManager.frame.repaint();   
    }

	public void CreateLevelSelection() // Switches to level selection
	{
	    GameManager.frame.getContentPane().removeAll();
	
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
	    startGameButton.addActionListener(e -> GameManager.instance.CreateMainGame(GameManager.chosenScenario));
	
	    int[] highscores = SaveLoadManager.getStats(chosenUserName);
	    
	    for(int i = 0; i < ScenarioManager.scenarios.size(); i++) 
	    {
	    	Scenario scenario = ScenarioManager.scenarios.get(i);
	    	final int current_counter = i;
	        JButton scenarioButton = new JButton(scenario.name);
	        scenarioButton.addActionListener(e -> {
	            StringBuilder assetsInfo = new StringBuilder();
	            for (Asset asset : scenario.assets) {
	                assetsInfo.append("\n").append(asset.name)
	                          .append("\n(");
	                if (asset instanceof Vehicle) 
	                {
	                    Vehicle vehicle = (Vehicle) asset;
	                    String pattern = "complex";
	                    if(vehicle.movePattern.length == 1)
	                    {
	                    	pattern = "pattern: (" + vehicle.movePattern[0].x + " x " + vehicle.movePattern[0].y + ")";
	                    }
	                    assetsInfo.append(pattern);
	                } else if (asset instanceof Sonar) {
	                    Sonar sonar = (Sonar) asset;
	                    assetsInfo.append("Radius: ").append(sonar.radius);
	                }
	                assetsInfo.append(", Amount: ").append(asset.amount).append(")\n");
	            }
	
	            scenarioTextArea.setText("\n" + scenario.name + "\n\nSize: " + scenario.size + " x " + scenario.size + " NM\n\nSurvivors: " + scenario.survivors + "\n\n" + scenario.description + "\n\nPersonal highscore: " + highscores[current_counter] + "/" + scenario.survivors);
	            assetTextArea.setText("\nASSETS:\n" + assetsInfo.toString());
	            GameManager.chosenScenario = scenario;
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
	
	    GameManager.frame.add(centerPanel, BorderLayout.CENTER);
	    GameManager.frame.revalidate();
	    GameManager.frame.repaint();
	}

    
    protected void createIcon(JLabel icon)
    {
        iconPanel.add(icon);
        iconPanel.revalidate();
        iconPanel.repaint();
    }
    
    protected void removeIcon(JLabel icon)
    {
        iconPanel.remove(icon);
        iconPanel.revalidate();
        iconPanel.repaint();
    }
    
    protected void setVisualCoordinates(int x, int y)
    {
    	xCorVisual.setText(Integer.toString(x));
    	yCorVisual.setText(Integer.toString(y));
    }
    
    protected JPanel createEastPanel(int width) 
    {
        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new BorderLayout());
        assetPanel.setBackground(Color.darkGray);
        assetPanel.setPreferredSize(new Dimension(width, GameManager.frame.getHeight()));
        assetPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        return assetPanel;
    }
    
    protected JPanel createAssetPanel() 
    {
        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new BoxLayout(assetPanel, BoxLayout.Y_AXIS));
        assetPanel.setBackground(Color.darkGray);
        return assetPanel;
    }
    
    protected JPanel createCoordinatesPanel()
    {
    	JPanel coordinatesPanel = new JPanel();
    	coordinatesPanel.setLayout(new BoxLayout(coordinatesPanel, BoxLayout.Y_AXIS));
    	coordinatesPanel.setPreferredSize(new Dimension(coordinatesPanel.getPreferredSize().width, 100));
    	JLabel topText = new JLabel("SELECTED COORDINATES");
    	JPanel coordinatesHolder = new JPanel();
    	coordinatesHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
    	Font font = new Font("Arial", Font.BOLD, 14);
    	
        xCorVisual = new JLabel("X", SwingConstants.CENTER);
        xCorVisual.setFont(font);
        xCorVisual.setPreferredSize(new Dimension(50, 50)); 
        xCorVisual.setOpaque(true); 
        xCorVisual.setBackground(Color.WHITE);
        xCorVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        yCorVisual = new JLabel("Y", SwingConstants.CENTER); 
        yCorVisual.setFont(font);
        yCorVisual.setPreferredSize(new Dimension(50, 50)); 
        yCorVisual.setOpaque(true); 
        yCorVisual.setBackground(Color.WHITE);
        yCorVisual.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    	
    	topText.setHorizontalAlignment(SwingConstants.CENTER);
    	xCorVisual.setHorizontalAlignment(SwingConstants.CENTER);
    	yCorVisual.setHorizontalAlignment(SwingConstants.CENTER);
    	
    	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
    	xCorVisual.setBorder(border);
    	yCorVisual.setBorder(border);
    	
    	coordinatesPanel.setBackground(Color.GRAY);
    	coordinatesHolder.setBackground(Color.GRAY);
    	topText.setBackground(Color.GRAY);
    	
    	coordinatesHolder.add(xCorVisual);
    	coordinatesHolder.add(yCorVisual);
    	coordinatesPanel.add(topText);
    	coordinatesPanel.add(coordinatesHolder);
    	
    	return coordinatesPanel;
    }
    
    protected JPanel createMainGamePanel(int size) 
    {
        JPanel mainGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainGamePanel.setBackground(Color.DARK_GRAY);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.DARK_GRAY);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension((int) (GameManager.frame.getHeight() * 0.9f), (int) (GameManager.frame.getHeight() * 0.9f)));

        JPanel grid = new JPanel();
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        grid.setLayout(new GridLayout(size, size));
        grid.setBounds(0, 0, layeredPane.getPreferredSize().width, layeredPane.getPreferredSize().height); 

        JPanel leftPanel = new JPanel(new GridLayout(size, 1));
        leftPanel.setBackground(Color.DARK_GRAY);
        
        JPanel bottomPanel = new JPanel(new GridLayout(1, size)); 
        bottomPanel.setBackground(Color.DARK_GRAY);
        
        for (int row = 0; row < size; row++) 
        {
            for (int column = 0; column < size; column++) 
            {
                final int r = row;
                final int c = column;

                JButton gameField = new JButton();
                gameField.setBackground(GameColors.seaColor);
                gameField.setToolTipText("AREA: (" + r + ", " + c+ ")");
                
                grid.add(gameField);
                GameManager.instance.buttons[column][row] = gameField;

                gameField.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        GameManager.instance.selectButton(c, r);
                    }
                });
            }
        }
        iconPanel = new JPanel(null); 
        iconPanel.setOpaque(false); 
        iconPanel.setBounds(0, 0, layeredPane.getPreferredSize().width, layeredPane.getPreferredSize().height);

        layeredPane.add(grid, Integer.valueOf(0));
        layeredPane.add(iconPanel, Integer.valueOf(1)); 

        centerPanel.add(layeredPane, BorderLayout.CENTER);
        centerPanel.add(leftPanel, BorderLayout.WEST); 
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainGamePanel.add(centerPanel);

        return mainGamePanel;
    }

    
    protected void endGame(int survivorsSaved) //Enables end screen
    {
    	int[] stats = SaveLoadManager.getStats(chosenUserName);
    	stats[ScenarioManager.scenarios.indexOf(GameManager.chosenScenario)] = survivorsSaved;
    	SaveLoadManager.saveStats(chosenUserName, stats);
    	
    	String title;
    	float ratio = (float) survivorsSaved / GameManager.chosenScenario.survivors;

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

    	
        JDialog dialog = new JDialog(GameManager.frame, title, true);
        dialog.setLayout(new BorderLayout());

        String message = survivorsSaved + " / " + GameManager.chosenScenario.survivors + " people saved!\n\n";
        JLabel messageLabel = new JLabel("<html>" + message + "</html>", SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiManager.instance.CreateLevelSelection();
                dialog.dispose();
            }
        });
        buttonPanel.add(okButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(new Dimension(300, 200));
        dialog.setLocationRelativeTo(GameManager.frame); 
        dialog.setVisible(true); 
    }
    
    protected void drawAssets(JPanel panelToDrawOn, Asset[] assets) 
    {
        for (Asset asset : assets) 
        {
        	GameManager.instance.usesLeft += asset.amount;
        	
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
                    GameManager.instance.previewAssetRange(asset);
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
            		GameManager.instance.UseAsset(asset);
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

    protected void SetText(Asset asset)
    {
        String textAdd = "";
        if (asset instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) asset;
            if (vehicle.canRescue) {
                textAdd = " X";
            }
            String pattern = "complex";
            if(vehicle.movePattern.length == 1)
            {
            	pattern = "(" + vehicle.movePattern[0].x + " x " + vehicle.movePattern[0].y + ")";
            }
            
            asset.myTextArea.setText(vehicle.name + textAdd + "\n\nPattern: " + pattern + "\nAmount: " + vehicle.amount);
        } 
        else if (asset instanceof Sonar) {
            Sonar sonar = (Sonar) asset;
            asset.myTextArea.setText(sonar.name + "\n\nRadius: " + sonar.radius + "\nAmount: " + sonar.amount);
        }
	    assetPanel.revalidate();
	    assetPanel.repaint();
    }

    protected void createGameCanvas(Scenario scenario)
    {
        int eastPanelWidth = (int)(GameManager.frame.getWidth() * 0.2f);
        JPanel eastPanel = UiManager.instance.createEastPanel(eastPanelWidth);
        JPanel mainGamePanel = UiManager.instance.createMainGamePanel(scenario.size);
        assetPanel = UiManager.instance.createAssetPanel();
        JPanel coordinatesPanel = UiManager.instance.createCoordinatesPanel();
        eastPanel.add(assetPanel, BorderLayout.CENTER);
        eastPanel.add(coordinatesPanel, BorderLayout.SOUTH);
        
        GameManager.instance.placePeople(scenario);
        drawAssets(assetPanel, scenario.assets);

        GameManager.frame.add(mainGamePanel, BorderLayout.CENTER);
        GameManager.frame.add(eastPanel, BorderLayout.EAST);

    }
}