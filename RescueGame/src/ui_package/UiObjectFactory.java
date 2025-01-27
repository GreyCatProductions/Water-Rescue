package ui_package;

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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import icons.IconManager;
import main.GameManager;
import scenario_creation_package.Asset;

public abstract class UiObjectFactory 
{
	protected Dimension standardButtonSize = new Dimension(175, 25);
	protected JLabel xCorVisual;
	protected JLabel yCorVisual;
	public JPanel assetPanel;
	public JPanel iconPanel;
	
	//Main Menu Objects
	protected JPanel createCenterPanel()
	{
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.black); 
        return centerPanel;
	}
	
	protected JPanel createTitlePanel()
	{
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
        return titlePanel;
	}
	
	protected JPanel createObjectContainer()
	{
        JPanel mainMenuObjectContainer = new JPanel();
        mainMenuObjectContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        mainMenuObjectContainer.setLayout(new BoxLayout(mainMenuObjectContainer, BoxLayout.Y_AXIS));
        mainMenuObjectContainer.setBackground(Color.darkGray);
        return mainMenuObjectContainer;
	}
	
	protected JLabel createEnterNameInstruction()
	{
		JLabel enterNameInstruction = new JLabel("ENTER YOUR NAME");
        enterNameInstruction.setHorizontalAlignment(JLabel.CENTER); 
        enterNameInstruction.setBorder(null);
        enterNameInstruction.setForeground(Color.WHITE);
        enterNameInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        return enterNameInstruction;
	}
	
	protected JTextField createEnterNameField()
	{
        JTextField enterField = new JTextField(15);
        enterField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterField.setHorizontalAlignment(JTextField.CENTER); 
        enterField.setMaximumSize(new Dimension(200, 20)); 
        return enterField;
	}
	
	protected JButton createStartGameButton()
	{
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(standardButtonSize);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return startGameButton;
	}
	
	protected JPanel createTitleMenuSpliter()
	{
        JPanel titleMenuSplitPanel = new JPanel();
        titleMenuSplitPanel.setLayout(new BoxLayout(titleMenuSplitPanel, BoxLayout.Y_AXIS));
        titleMenuSplitPanel.setBackground(Color.BLACK);
        return titleMenuSplitPanel;
	}
	
	protected JPanel createLogo()
	{
        JPanel logoContainer = new JPanel();
        logoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoContainer.setOpaque(false);
        
        ImageIcon logoIcon = IconManager.mainGameLogo;
        Image scaledImage = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
       
        JLabel gameLogo = new JLabel(logoIcon);
        logoContainer.add(gameLogo);
        return logoContainer;
	}
	
	protected JPanel createCredits()
	{
		JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel credits = new JLabel("MADE BY OLEG SHAPOVALOV");
        credits.setForeground(Color.WHITE);
        creditsPanel.add(credits);
        creditsPanel.setBackground(Color.BLACK);
        return creditsPanel;
	}
	
	//Level Selection Menu Objects
	protected JTextArea createTopInstruction()
	{
		JTextArea topInstruction = new JTextArea("\nYou are in charge of water rescue operations.\nUse assets to save as many lives as possible.");
	    topInstruction.setLineWrap(true);
	    topInstruction.setWrapStyleWord(true);
	    topInstruction.setEditable(false);
	    topInstruction.setBackground(Color.lightGray);
	    topInstruction.setPreferredSize(new Dimension(500, 100));
	    topInstruction.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    return topInstruction;
	}

	protected JPanel createBottomTextContainer()
	{
		JPanel bottomTextContainer = new JPanel(new BorderLayout());
	    bottomTextContainer.setBackground(Color.darkGray);
	    bottomTextContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    return bottomTextContainer;
	}
	
	protected JTextArea createScenarioTextArea()
	{
	    JTextArea scenarioTextArea = new JTextArea("\nChoose a scenario.");
	    scenarioTextArea.setLineWrap(true);
	    scenarioTextArea.setWrapStyleWord(true);
	    scenarioTextArea.setEditable(false);
	    scenarioTextArea.setBackground(Color.lightGray);
	    scenarioTextArea.setPreferredSize(new Dimension(225, 100));
	    scenarioTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    return scenarioTextArea;
	}

	protected JTextArea createAssetTextArea()
	{
	    JTextArea assetTextArea = new JTextArea();
	    assetTextArea.setLineWrap(true);
	    assetTextArea.setWrapStyleWord(true);
	    assetTextArea.setEditable(false);
	    assetTextArea.setBackground(Color.lightGray);
	    assetTextArea.setPreferredSize(new Dimension(225, 300));
	    assetTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    return assetTextArea;
	}
	
	protected JPanel createVerticalContainer()
	{
	    JPanel verticalContainer = new JPanel();
	    verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));
	    verticalContainer.setBackground(Color.darkGray);
	    verticalContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    return verticalContainer;
	}

	protected JButton createStartScenarioButton()
	{
	    JButton startScenarioButton = new JButton("START SCENARIO");
	    startScenarioButton.setEnabled(false);
	    startScenarioButton.setPreferredSize(standardButtonSize);
	    startScenarioButton.addActionListener(e -> GameManager.instance.initializeMainGame(GameManager.chosenScenario));
	    return startScenarioButton;
	}

	//Main game Objects
	protected JPanel createBottomContainer()
	{
	    JPanel bottomContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    bottomContainer.setBackground(Color.darkGray);
	    bottomContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    return bottomContainer;
	}

	//Main Game Objects
    protected JPanel createEastPanel(int width)
    {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setBackground(Color.darkGray);
        eastPanel.setPreferredSize(new Dimension(width, GameManager.frame.getHeight()));
        eastPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        return eastPanel;
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
    	coordinatesPanel.setLayout(new BorderLayout());
    	coordinatesPanel.setPreferredSize(new Dimension(coordinatesPanel.getMaximumSize().width, 100));
    	JLabel topText = new JLabel("SELECTED COORDINATES");
        topText.setFont(new Font("Arial", Font.BOLD, 16)); 

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
    	coordinatesPanel.add(topText, BorderLayout.NORTH);
    	coordinatesPanel.add(coordinatesHolder, BorderLayout.CENTER);
    	coordinatesPanel.setBorder(border);
    	
    	return coordinatesPanel;
    }
    
    protected JPanel createTutorialPanel() 
    {
        JPanel tutorialPanel = new JPanel();
        tutorialPanel.setLayout(new BorderLayout());
        tutorialPanel.setPreferredSize(new Dimension(tutorialPanel.getMaximumSize().width, 100));
        tutorialPanel.setBackground(Color.GRAY);
        tutorialPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel topText = new JLabel("FIELD MANUAL");
        topText.setHorizontalAlignment(SwingConstants.CENTER);
        topText.setFont(new Font("Arial", Font.BOLD, 16)); 
        tutorialPanel.add(topText, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(Color.GRAY); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.NONE; 

        JButton objectiveButton = createExplainObjectiveButton();
        gbc.gridx = (0); 
        gbc.gridy = (0); 
        buttonPanel.add(objectiveButton , gbc);
        
        JButton mapButton = createExplainMapButton();
        gbc.gridx = (1); 
        gbc.gridy = (0); 
        buttonPanel.add(mapButton , gbc);
        
        JButton vehicleButton = createVehicleExplainButton();
        gbc.gridx = (0); 
        gbc.gridy = (1); 
        buttonPanel.add(vehicleButton, gbc);
        
        JButton sonarButton = createSonarExplainButton();
        gbc.gridx = (1); 
        gbc.gridy = (1); 
        buttonPanel.add(sonarButton, gbc);

        tutorialPanel.add(buttonPanel, BorderLayout.CENTER);

        return tutorialPanel;
    }
    
    private JButton createExplainObjectiveButton()
    {
    	JButton button = new JButton("Objective");
        button.setPreferredSize(standardButtonSize); 

        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                FieldManualTutorials.showGameMechanicTutorial();
            }
        });
        
		return button;
    }
    
    private JButton createExplainMapButton()
    {
    	JButton button = new JButton("Map");
        button.setPreferredSize(standardButtonSize); 

        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                FieldManualTutorials.showMapTutorial();
            }
        });
        
		return button;
    }
    
    
    private JButton createVehicleExplainButton()
    {
    	JButton button = new JButton("Vehicles");
        button.setPreferredSize(standardButtonSize); 

        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                FieldManualTutorials.showVehicleTutorial();
            }
        });
        
		return button;
    }

    private JButton createSonarExplainButton()
    {
    	JButton button = new JButton("Sonars");
        button.setPreferredSize(standardButtonSize); 

        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                FieldManualTutorials.showSonarTutorial();;
            }
        });
        
		return button;
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
        
        createFields(grid, size);
        
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
    
    public void createFields(JPanel grid, int size) //Public for testing
    {
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
                GameManager.instance.gameFields[column][row] = gameField;

                gameField.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        GameManager.instance.selectField(c, r);
                    }
                });
            }
        }
    }

    protected JPanel createAssetWindow(Asset asset)
    {
        JPanel assetWindow = new JPanel();
        assetWindow.setBackground(Color.gray);
        assetWindow.setLayout(new BorderLayout());

        JTextArea statsTextArea = new JTextArea();
        asset.setTextAreaReference(statsTextArea);
        asset.updateTextAreaText();
        
        statsTextArea.setLineWrap(true);
        statsTextArea.setWrapStyleWord(true);
        statsTextArea.setOpaque(false);
        statsTextArea.setEditable(false);

        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);
        statsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        statsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        statsScrollPane.setOpaque(true);
        statsScrollPane.setBackground(Color.gray); 
        statsScrollPane.getViewport().setBackground(Color.gray);
        statsScrollPane.setBorder(new LineBorder(Color.darkGray, 1));

        assetWindow.add(statsScrollPane, BorderLayout.WEST);

        JTextArea descriptionTextArea = new JTextArea(asset.getDescription());
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setOpaque(false);
        descriptionTextArea.setEditable(false);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        descriptionScrollPane.setOpaque(true);
        descriptionScrollPane.getViewport().setBackground(Color.gray);
        descriptionScrollPane.setBorder(new LineBorder(Color.darkGray, 1));
        assetWindow.add(descriptionScrollPane, BorderLayout.EAST);


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
                asset.tryToPreview();
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
                asset.tryToUse();
            }
        });

        buttonsPanel.add(selectButton);
        buttonsPanel.add(deployButton);
        
        assetWindow.add(buttonsPanel, BorderLayout.SOUTH);

        JPanel logoContainer = new JPanel();
        logoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoContainer.setOpaque(false);
        ImageIcon logoIcon = asset.getIcon();
        Image scaledImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        JLabel gameLogo = new JLabel(logoIcon);
        logoContainer.add(gameLogo);
        
        assetWindow.add(logoContainer);
        
        int width = assetWindow.getMaximumSize().width;
        int height = assetWindow.getPreferredSize().height + 15;

        assetWindow.setMaximumSize(new Dimension(width, height));
        assetWindow.setPreferredSize(standardButtonSize);
        assetWindow.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        return assetWindow;
    }
    
    public JButton createLeaveGameButton() 
    {
        JButton quitButton = new JButton("Quit Game");
        quitButton.setSize(standardButtonSize);
        quitButton.setOpaque(true); 
        quitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
        quitButton.addActionListener((ActionEvent e) -> 
        {
            System.exit(0);
        });
        return quitButton;
    }

    public JButton createMainMenuButton()
    {
        JButton returnToMainMenuButton = new JButton("Main Menu");
        returnToMainMenuButton.setPreferredSize(standardButtonSize); 
        returnToMainMenuButton.setOpaque(true);
        returnToMainMenuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        returnToMainMenuButton.addActionListener((ActionEvent e) -> 
        {
            UiManager.instance.createMainMenu();
        });
        return returnToMainMenuButton;
    }

    
}
