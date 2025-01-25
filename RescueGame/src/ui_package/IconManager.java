package ui_package;

import javax.swing.JLabel;

public class IconManager 
{
	public static final IconManager INSTANCE = new IconManager();
	
    /**
     *draws given icon on iconPanel. Refreshes iconPanel
     * @param icon icon to draw
     * @see UiObjectFactory#iconPanel
     */
    public void createIcon(JLabel icon)
    {
        UiManager.instance.iconPanel.add(icon);
        UiManager.instance.iconPanel.revalidate();
        UiManager.instance.iconPanel.repaint();
    }
    
    /**
     *removes given icon on iconPanel. Refreshes iconPanel
     * @param icon icon to remove
     * @see UiObjectFactory#iconPanel
     */
    public void removeIcon(JLabel icon)
    {
    	UiManager.instance.iconPanel.remove(icon);
    	UiManager.instance.iconPanel.revalidate();
    	UiManager.instance.iconPanel.repaint();
    }
}
