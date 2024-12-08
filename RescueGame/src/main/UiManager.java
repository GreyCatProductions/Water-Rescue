package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class UiManager
{
    JPanel assetPanel;
    JPanel iconPanel;
    JLabel xCorVisual;
    JLabel yCorVisual;
    
    public static UiManager instance;
    
    public UiManager()
    {
    	instance = this;
    }
    
    public void createIcon(JLabel icon)
    {
        iconPanel.add(icon);
        iconPanel.revalidate();
        iconPanel.repaint();
    }
    
    public void removeIcon(JLabel icon)
    {
        iconPanel.remove(icon);
        iconPanel.revalidate();
        iconPanel.repaint();
    }
    
    public void setVisualCoordinates(int x, int y)
    {
    	xCorVisual.setText(Integer.toString(x));
    	yCorVisual.setText(Integer.toString(y));
    }
    
    protected JPanel createEastPanel(int width) 
    {
        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new BorderLayout());
        assetPanel.setBackground(Color.darkGray);
        assetPanel.setPreferredSize(new Dimension(width, WaterRescueGame.instance.frame.getHeight()));
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
        layeredPane.setPreferredSize(new Dimension((int) (WaterRescueGame.instance.frame.getHeight() * 0.9f), (int) (WaterRescueGame.instance.frame.getHeight() * 0.9f)));

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
                WaterRescueGame.instance.buttons[column][row] = gameField;

                gameField.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        WaterRescueGame.instance.selectButton(c, r);
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

    protected void drawAssets(JPanel panelToDrawOn, Asset[] assets) 
    {
        for (Asset asset : assets) 
        {
        	WaterRescueGame.instance.usesLeft += asset.amount;
        	
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
            selectButton.setMaximumSize(WaterRescueGame.standardButtonSize);
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            selectButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    WaterRescueGame.instance.previewAssetRange(asset);
                }
            });
            
            JButton deployButton = new JButton("DEPLOY");
            deployButton.setMaximumSize(WaterRescueGame.standardButtonSize);
            deployButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            deployButton.addActionListener(new ActionListener()
            {
            	@Override
            	public void actionPerformed(ActionEvent e)
            	{
            		WaterRescueGame.instance.UseAsset(asset);
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
        int eastPanelWidth = (int)(WaterRescueGame.instance.frame.getWidth() * 0.2f);
        JPanel eastPanel = UiManager.instance.createEastPanel(eastPanelWidth);
        JPanel mainGamePanel = UiManager.instance.createMainGamePanel(scenario.size);
        assetPanel = UiManager.instance.createAssetPanel();
        JPanel coordinatesPanel = UiManager.instance.createCoordinatesPanel();
        eastPanel.add(assetPanel, BorderLayout.CENTER);
        eastPanel.add(coordinatesPanel, BorderLayout.SOUTH);
        
        WaterRescueGame.instance.placePeople(scenario);
        drawAssets(assetPanel, scenario.assets);

        WaterRescueGame.instance.frame.add(mainGamePanel, BorderLayout.CENTER);
        WaterRescueGame.instance.frame.add(eastPanel, BorderLayout.EAST);

    }
}