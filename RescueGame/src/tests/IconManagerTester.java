package tests;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import icons.IconManager;

class IconManagerTester 
{
    @Test
    public void testIconResources() 
    {
        String[] resourcePaths = 
    	{
            "/icons/satellite1.png",
            "/icons/fisher1.png",
            "/icons/smallShipv1.png",
            "/icons/smallShipv2.png",
            "/icons/big plane1.png",
            "/icons/big plane2.png",
            "/icons/big plane3.png",
            "/icons/BigShip1.png",
            "/icons/BigShip2.png",
            "/icons/BigShip3.png",
            "/icons/buoyv1.png",
            "/icons/buoyv2.png",
            "/icons/buoyv3.png",
            "/icons/dinghie.png",
            "/icons/Heli1.png",
            "/icons/Heli2.png",
            "/icons/heli3.png",
            "/icons/hobby_pilot.png",
            "/icons/MainGameLogo.png",
            "/icons/oiltanker.png",
            "/icons/Small airtanker.png",
            "/icons/Small airtanker2.png",
            "/icons/yacht.png"
        };

        for (String resourcePath : resourcePaths) 
        {
            try 
            {
                ImageIcon icon = new ImageIcon(IconManager.class.getResource(resourcePath));
                assertNotNull(icon.getImage(), "Icon not found for resource: " + resourcePath);
            } catch (Exception e) {
                System.err.println("Error loading resource: " + resourcePath);
                e.printStackTrace();
                fail("Exception thrown while loading resource: " + resourcePath);
            }
        }
    }
}
