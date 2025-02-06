package icons;

import javax.swing.ImageIcon;

public class IconManager {
    public final static ImageIcon satellite1 = loadIcon("/icons/satellite1.png");
    public final static ImageIcon fisher1 = loadIcon("/icons/fisher1.png");
    public final static ImageIcon smallShipv1 = loadIcon("/icons/smallShipv1.png");
    public final static ImageIcon smallShipv2 = loadIcon("/icons/smallShipv2.png");
    public final static ImageIcon bigPlane1 = loadIcon("/icons/big_plane1.png");
    public final static ImageIcon bigPlane2 = loadIcon("/icons/big_plane2.png");
    public final static ImageIcon bigPlane3 = loadIcon("/icons/big_plane3.png");
    public final static ImageIcon bigShip1 = loadIcon("/icons/BigShip1.png");
    public final static ImageIcon bigShip2 = loadIcon("/icons/BigShip2.png");
    public final static ImageIcon bigShip3 = loadIcon("/icons/BigShip3.png");
    public final static ImageIcon buoyv1 = loadIcon("/icons/buoyv1.png");
    public final static ImageIcon buoyv2 = loadIcon("/icons/buoyv2.png");
    public final static ImageIcon buoyv3 = loadIcon("/icons/buoyv3.png");
    public final static ImageIcon dinghie1 = loadIcon("/icons/dinghie.png");
    public final static ImageIcon heli1 = loadIcon("/icons/Heli1.png");
    public final static ImageIcon heli2 = loadIcon("/icons/heli2.png");
    public final static ImageIcon heli3 = loadIcon("/icons/heli3.png");
    public final static ImageIcon hobbyPilot = loadIcon("/icons/hobby_pilot.png");
    public final static ImageIcon mainGameLogo = loadIcon("/icons/MainGameLogo.png");
    public final static ImageIcon oiltanker = loadIcon("/icons/oiltanker.png");
    public final static ImageIcon smallAirtanker1 = loadIcon("/icons/small_airtanker.png");
    public final static ImageIcon smallAirtanker2 = loadIcon("/icons/small_airtanker2.png");
    public final static ImageIcon yacht = loadIcon("/icons/yacht.png");

    private static ImageIcon loadIcon(String path) {
        java.net.URL imgURL = IconManager.class.getResource(path);
        if (imgURL == null) {
            System.err.println("FEHLT: " + path);
            return new ImageIcon(); 
        }
        return new ImageIcon(imgURL);
    }
}

