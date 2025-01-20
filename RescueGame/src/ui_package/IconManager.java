package ui_package;

import javax.swing.JLabel;

public class IconManager 
{
	public static final IconManager INSTANCE = new IconManager();
	
    public void createIcon(JLabel icon)
    {
        UiManager.instance.iconPanel.add(icon);
        UiManager.instance.iconPanel.revalidate();
        UiManager.instance.iconPanel.repaint();
    }
    
    public void removeIcon(JLabel icon)
    {
    	UiManager.instance.iconPanel.remove(icon);
    	UiManager.instance.iconPanel.revalidate();
    	UiManager.instance.iconPanel.repaint();
    }
}
