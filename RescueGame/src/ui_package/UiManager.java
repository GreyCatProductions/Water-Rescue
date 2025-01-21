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
    
    private String chosenUserName;
    
    public UiManager()
    {
    	instance = this;
    }
    
    public void createMainMenu()
    {
        GameManager.frame.getContentPane().removeAll(); 

        JPanel centerPanel = createCenterPanel();
        
        JPanel titlePanel = createTitlePanel();
        
        JLabel enterNameInstruction = createEnterNameInstruction();
        
        JPanel objectContainer = createObjectContainer();
         
        JTextField enterNameField = createEnterNameField();
        
        JButton startGameButton = createStartGameButton();
        startGameButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	chosenUserName = enterNameField.getText(); 
                if (NameValidator.IsValidName(chosenUserName)) 
                {
                    JOptionPane.showMessageDialog(GameManager.frame, "Playing as: " + chosenUserName);
	                createLevelSelection();
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
        objectContainer.add(startGameButton);
       
        titleMenuSplitter.add(titlePanel); 
        titleMenuSplitter.add(logoContainer);
        titleMenuSplitter.add(objectContainer);
        
        centerPanel.add(titleMenuSplitter);
        
        JPanel creditsPanel = createCredits();

        GameManager.frame.add(creditsPanel, BorderLayout.SOUTH);
        GameManager.frame.add(centerPanel, BorderLayout.CENTER);
        GameManager.frame.revalidate(); 
        GameManager.frame.repaint();   
    }

	public void createLevelSelection()
	{
	    GameManager.frame.getContentPane().removeAll();
	
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
	    
	    int[] highscores = SaveLoadManager.getStats(chosenUserName);
	    
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
	                    if(vehicle.movePattern.length == 1)
	                    {
	                    	pattern = "pattern: (" + vehicle.movePattern[0].x + " x " + vehicle.movePattern[0].y + ")";
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
   
    public void createGameCanvas(Scenario scenario)
    {
        int eastPanelWidth = (int)(GameManager.frame.getWidth() * 0.2f);
        JPanel eastPanel = createEastPanel(eastPanelWidth);
        JPanel mainGamePanel = createMainGamePanel(scenario.size);
        assetPanel = createAssetPanel();
        JPanel coordinatesPanel = createCoordinatesPanel();
        eastPanel.add(assetPanel, BorderLayout.CENTER);
        eastPanel.add(coordinatesPanel, BorderLayout.SOUTH);
        
        LostPeopleManager.INSTANCE.placePeople(scenario);
        drawAssets(assetPanel, scenario.assets);

        GameManager.frame.add(mainGamePanel, BorderLayout.CENTER);
        GameManager.frame.add(eastPanel, BorderLayout.EAST);
    }

    public void setVisualCoordinates(int x, int y)
    {
    	xCorVisual.setText(Integer.toString(x));
    	yCorVisual.setText(Integer.toString(y));
    }
    
    public void createEndGameDialog(int survivorsSaved) 
    {
    	int[] stats = SaveLoadManager.getStats(chosenUserName);
    	stats[ScenarioManager.getIndexOfScenario(GameManager.chosenScenario)] = survivorsSaved;
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