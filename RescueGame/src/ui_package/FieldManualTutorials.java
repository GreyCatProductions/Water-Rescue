package ui_package;

import javax.swing.JOptionPane;

import main.GameManager;

public class FieldManualTutorials 
{
	/**
	 * shows message dialog with game mechanic as content
	 */
	public static void showGameMechanicTutorial()
	{
		String text = "Operator, you are in charge of water rescue operations.\n"
				+ "People are lost in the sea and it is your job to use available assets to save them.\n"
				+ "On the right side of your monitor you can see all of your assets.\n"
				+ "Use them wisely.\n"
				+ "The day ends when you save everyone or have no more assets left.\n"
				+ "Good Luck.";
				
		showMessage(text);
	}
	
	/**
	 * shows message dialog with {@link Vehicle} explanation as content
	 */
	public static void showVehicleTutorial()
	{
		String text = "Vehicles are your only tool to save survivors.\n"
				+ " Some vehicles, like ships, are usually able to pick people up from the sea.\n"
				+ "Those that are able to do that, are marked with a X next to their name.\n"
				+ "Others, like planes, only scout and mark found survivors on your map.\n"
				+ "It is recommended to use sonar before sending vehicles somewhere.";
				
		showMessage(text);
	}
	
	/**
	 * shows message dialog with {@link Sonar} explanation as content
	 */
	public static void showSonarTutorial()
	{
		String text = "Sonar buoys use sonar to detect sound of survivors in a huge radius.\n"
				+ "Loud = green, silent = red.\n"
				+ "Use them at the beginning to get a general understanding where to send your vehicles.";
		
		showMessage(text);
	}
	
	/**
	 * shows message dialog with map explanation as content
	 */
	public static  void showMapTutorial()
	{
		String text = "Your map is the latest technology available. It offers you an interactive map.\n"
				+ "You can mark areas by double clicking a sector.\n"
				+ "When you click on a sector, it gets selected.\n"
				+ "After you select a sector, you can deploy an asset there.\n "
				+ "After deployment, your vehicles report their actions directly to your map.\n\n"
				+ "Legend: \n"
				+ "- dark blue: unknown sector\n"
				+ "- blue: selected sector\n"
				+ "- yellow: marked sector\n"
				+ "- red: found but not rescued survivors in sector\n"
				+ "- green: survivors saved in sector\n";
		
		showMessage(text);
	}
	
	private static void showMessage(String message)
	{
		JOptionPane.showMessageDialog(GameManager.frame, message);
	}
}
