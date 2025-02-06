package ui_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.GameManager;
import main.LostPeopleManager;
import main.SaveLoadManager;
import scenario_creation_package.Asset;
import scenario_creation_package.Scenario;
import scenario_creation_package.ScenarioManager;
import scenario_creation_package.Sonar;
import scenario_creation_package.Vehicle;

public class UiManager extends UiObjectFactory
{    
    public static UiManager instance;
    
    public String chosenUserName;
    
    public UiManager()
    {
    	instance = this;
    }
    
    /**
     * Clears the frame. Creates the login menu
     */
    public void createLoginMenu()
    {
        GameManager.frame.getContentPane().removeAll(); 
        GameManager.instance.stopAllThreads();

	    JPanel verticalPanel = new JPanel();
	    verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

	    verticalPanel.setOpaque(false);

	    JButton leaveGameButton = createLeaveGameButton();
	    verticalPanel.add(leaveGameButton);

	    verticalPanel.setSize(verticalPanel.getPreferredSize());

	    GameManager.frame.add(verticalPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = createCenterPanel();
        
        JLabel enterNameInstruction = createEnterNameInstruction();
        
        JPanel objectContainer = createObjectContainer();
         
        JTextField enterNameField = createEnterNameField();
        
        JButton loginButton = loginButton();
        loginButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	chosenUserName = enterNameField.getText(); 
                if (NameValidator.IsValidName(chosenUserName)) 
                {
                    JOptionPane.showMessageDialog(GameManager.frame, "Playing as: " + chosenUserName);
	                createMainMenu();
                }
                else
                {
                	JOptionPane.showMessageDialog(GameManager.frame, "Enter a normal name. \n-3-20 characters\n-contain no numbers\n-no leading/ending spaces.");
                	enterNameField.setText("");
                }
            }
        });

        JPanel titleMenuSplitter = createTitleMenuSpliter();
        
        JPanel logoContainer = createLogo();
        
        objectContainer.add(enterNameInstruction);
        objectContainer.add(Box.createVerticalStrut(10)); 
        objectContainer.add(enterNameField);
        objectContainer.add(Box.createVerticalStrut(10)); 
        objectContainer.add(loginButton);
       
        titleMenuSplitter.add(logoContainer);
        titleMenuSplitter.add(objectContainer);
        
        centerPanel.add(titleMenuSplitter);

        GameManager.frame.add(centerPanel, BorderLayout.CENTER);
        GameManager.frame.revalidate(); 
        GameManager.frame.repaint();   
    }
    
    /**
     * Clears the main frame. Draws the main menu
     */
    public void createMainMenu()
    {
        GameManager.frame.getContentPane().removeAll(); 
        GameManager.instance.stopAllThreads();

	    JPanel verticalPanel = new JPanel();
	    verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

	    verticalPanel.setOpaque(false);

	    JButton leaveGameButton = createLeaveGameButton();
	    verticalPanel.add(leaveGameButton);

	    verticalPanel.setSize(verticalPanel.getPreferredSize());

	    GameManager.frame.add(verticalPanel, BorderLayout.NORTH);
	    
	    JButton creditsButton = createCreditsButton();
        
        JPanel centerPanel = createCenterPanel();
        
        JPanel titlePanel = createTitlePanel();
        
        JPanel objectContainer = createObjectContainer();

        JButton startGameButton = createStartGameButton();
        startGameButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                createLevelSelection();
            }
        });
        
        JButton changeUserButton = createChangeUserButton();
        changeUserButton.addActionListener((ActionEvent e) -> 
        {
            createLoginMenu();
        });
        
        JPanel fieldManualPanel = createTutorialPanel();

        JPanel titleMenuSplitter = createTitleMenuSpliter();
        
        JPanel logoContainer = createLogo();
        
        objectContainer.add(startGameButton);
        objectContainer.add(Box.createVerticalStrut(5));
        objectContainer.add(creditsButton);
        objectContainer.add(Box.createVerticalStrut(5));
        objectContainer.add(changeUserButton);
        objectContainer.add(Box.createVerticalStrut(5));
        objectContainer.add(fieldManualPanel);
       
        titleMenuSplitter.add(titlePanel); 
        titleMenuSplitter.add(logoContainer);
        titleMenuSplitter.add(objectContainer);
        
        centerPanel.add(titleMenuSplitter);

        GameManager.frame.add(centerPanel, BorderLayout.CENTER);
        GameManager.frame.revalidate(); 
        GameManager.frame.repaint();   
    }
    
    /**
     * Clears the main frame. Draws the level selection menu
     */
	public void createLevelSelection()
	{
	    GameManager.frame.getContentPane().removeAll();
        GameManager.instance.stopAllThreads();
        
	    JPanel verticalPanel = new JPanel();
	    verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

	    verticalPanel.setOpaque(false);

	    JButton leaveGameButton = createLeaveGameButton();
	    verticalPanel.add(leaveGameButton);

	    verticalPanel.add(Box.createVerticalStrut(5));
	    
	    JButton mainMenuButton = createMainMenuButton();
	    verticalPanel.add(mainMenuButton);

	    verticalPanel.setSize(verticalPanel.getPreferredSize());

	    GameManager.frame.add(verticalPanel, BorderLayout.NORTH);

        
	    JPanel centerPanel = createCenterPanel();
	
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.insets = new Insets(10, 10, 10, 10);
	
	    JTextArea topInstruction = createTopInstruction();
	    
	    JPanel bottomTextContainer = createBottomTextContainer();
	
	    JTextArea scenarioTextArea = createScenarioTextArea();
	
	    JTextArea assetTextArea = createAssetTextArea();
	
	    bottomTextContainer.add(scenarioTextArea, BorderLayout.WEST);
	    bottomTextContainer.add(assetTextArea, BorderLayout.EAST);
	
	    JPanel verticalContainer = createVerticalContainer();
	   
	    verticalContainer.add(topInstruction);
	    verticalContainer.add(Box.createVerticalStrut(10));
	    verticalContainer.add(bottomTextContainer);
	
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    centerPanel.add(verticalContainer, gbc);
	
	    JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    selectionPanel.setBackground(Color.darkGray);
	    selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    JButton startScenarioButton = createStartScenarioButton();
	
	    ScenarioManager.createScenarios();
	    
    	SaveLoadManager.createAxelMuster();
    	SaveLoadManager.createKimBeispiel();
	    
	    int[] highscores = SaveLoadManager.getUserHighscores(chosenUserName);
	    
	    for(int i = 0; i < ScenarioManager.getAmountOfScenarios(); i++) 
	    {
	    	Scenario scenario = ScenarioManager.getScenario(i);
	    	final int current_counter = i;
	        JButton scenarioButton = new JButton(scenario.name);
	        scenarioButton.addActionListener(e -> {
	            StringBuilder assetsInfo = new StringBuilder();
	            for (Asset asset : scenario.assets) {
	                assetsInfo.append("\n").append(asset.getName())
	                          .append("\n(");
	                if (asset instanceof Vehicle) 
	                {
	                    Vehicle vehicle = (Vehicle) asset;
	                    String pattern = "complex";
	                    if(vehicle.steps.length == 1)
	                    {
	                    	pattern = "pattern: (" + vehicle.steps[0].x + " x " + vehicle.steps[0].y + ")";
	                    }
	                    assetsInfo.append(pattern);
	                } else if (asset instanceof Sonar) {
	                    Sonar sonar = (Sonar) asset;
	                    assetsInfo.append("Radius: ").append(sonar.radius);
	                }
	                assetsInfo.append(", Amount: ").append(asset.getAmount()).append(")\n");
	            }
	
	            scenarioTextArea.setText("\n" + scenario.name + "\n\nSize: " + scenario.size + " x " + scenario.size + " NM\n\nSurvivors: " + scenario.survivors + "\n\n" + scenario.description + "\n\nPersonal highscore: " + highscores[current_counter] + "/" + scenario.survivors);
	            assetTextArea.setText("\nASSETS:\n" + assetsInfo.toString());
	            GameManager.chosenScenario = scenario;
	            startScenarioButton.setEnabled(true);
	        });
	        selectionPanel.add(scenarioButton);
	    }
	
	    gbc.gridy = 1;
	    centerPanel.add(selectionPanel, gbc);
	
	    JPanel bottomContainer = createBottomContainer();
	    bottomContainer.add(startScenarioButton);
	
	    gbc.gridy = 2;
	    centerPanel.add(bottomContainer, gbc);
	
	    GameManager.frame.add(centerPanel, BorderLayout.CENTER);
	    GameManager.frame.revalidate();
	    GameManager.frame.repaint();
	}
   
    /**
     * Draws game canvas with given scenario
     * @param scenario scenario to create canvas for
     * @throws IllegalArgumentException scenario parameter must not be null
     */
    public void createGameCanvas(Scenario scenario)
    {
    	if(scenario == null)
    	{
    		throw new IllegalArgumentException("parameter 'scenario' must not be null!");
    	}
    	
	    JPanel verticalPanel = new JPanel();
	    verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

	    verticalPanel.setOpaque(false);

	    JButton leaveGameButton = createLeaveGameButton();
	    verticalPanel.add(leaveGameButton);

	    verticalPanel.add(Box.createVerticalStrut(5));
	    
	    JButton mainMenuButton = createMainMenuButton();
	    verticalPanel.add(mainMenuButton);

	    verticalPanel.setSize(verticalPanel.getPreferredSize());

	    GameManager.frame.add(verticalPanel, BorderLayout.NORTH);

        
        GameManager.frame.add(verticalPanel);
    	
        int eastPanelWidth = (int)(GameManager.frame.getWidth() * 0.2f);
        JPanel eastPanel = createEastPanel(eastPanelWidth);
        JPanel mainGamePanel = createMainGamePanel(scenario.size);
        assetPanel = createAssetPanel();
        
        JPanel bottomPanelContainer = new JPanel();
        bottomPanelContainer.setLayout(new BoxLayout(bottomPanelContainer, BoxLayout.Y_AXIS));
        bottomPanelContainer.setBackground(Color.GRAY);
        
        JPanel coordinatesPanel = createCoordinatesPanel();
        
        bottomPanelContainer.add(coordinatesPanel);
        
        eastPanel.add(assetPanel, BorderLayout.CENTER);
        eastPanel.add(bottomPanelContainer, BorderLayout.SOUTH);
        
        LostPeopleManager.INSTANCE.placePeople(scenario);
        drawAssets(assetPanel, scenario.assets);

        GameManager.frame.add(mainGamePanel, BorderLayout.CENTER);
        GameManager.frame.add(eastPanel, BorderLayout.EAST);
    }

    /**
     * sets visual coordinates
     * @param x x coordinate value to set
     * @param y y coordinate value to set
     */
    public void setVisualCoordinates(String x, String y)
    {
    	xCorVisual.setText(x);
    	yCorVisual.setText(y);
    }
    
    /**
     * creates and pop ups end game dialog
     * <p>
     * This method creates and pop ups end game dialog. The dialog is created depending 
     * on the chosen scenario from {@link GameManager} and the amount of survivorsSaved.
     * Adds a listener to the okay button of the dialog to go call {@link UiManager} to draw the 
     * level selection menu.
     * @param survivorsSaved amount of survivors saved
     * @see UiManager#createLevelSelection()
     */
    public void createEndGameDialog(int survivorsSaved) 
    {
    	int[] stats = SaveLoadManager.getUserHighscores(chosenUserName);
    	int scenario = ScenarioManager.getIndexOfScenario(GameManager.chosenScenario);
    	stats[scenario] = Math.max(stats[scenario], survivorsSaved);
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
    	String message = survivorsSaved + " people saved!\n\n"
    	        + (GameManager.chosenScenario.survivors - survivorsSaved) + " people lost to the sea.\n\n"
    	        + "Summary: " + survivorsSaved + " / " + GameManager.chosenScenario.survivors;

    	JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);


    	JPanel messagePanel = new JPanel();
    	messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
    	messagePanel.add(Box.createVerticalGlue());
    	messagePanel.add(messageLabel);
    	messagePanel.add(Box.createVerticalGlue());

    	dialog.add(messagePanel, BorderLayout.CENTER);

    	JPanel buttonPanel = new JPanel();
    	JButton okButton = new JButton("OK");
    	okButton.addActionListener(new ActionListener() 
    	{
    	    @Override
    	    public void actionPerformed(ActionEvent e) 
    	    {
    	        createLevelSelection();
    	        dialog.dispose();
    	    }
    	});
    	buttonPanel.add(okButton);

    	dialog.add(buttonPanel, BorderLayout.SOUTH);

    	dialog.setSize(new Dimension(300, 200));
    	dialog.setLocationRelativeTo(GameManager.frame); 
    	dialog.setVisible(true);
    }

    /**
     * Draws all given assets on given panel
     * <p>
     * This method uses {@link UiManager} to draw an assetWindow on the given JPanel for each {@link Asset} in the
     * asset array. 
     * @param panelToDrawOn panel to add the created assets to
     * @param assets all assets to draw
     * @see Asset#getAmount()
     */
    public void drawAssets(JPanel panelToDrawOn, Asset[] assets)
    {
        for (Asset asset : assets) 
        {
        	GameManager.instance.usesLeft += asset.getAmount();
        	
            JPanel assetWindow = createAssetWindow(asset);
            
            panelToDrawOn.add(assetWindow);
            panelToDrawOn.add(Box.createVerticalStrut(10));
        }
    }
}