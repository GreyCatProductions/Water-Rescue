package ui_package;

public class NameValidator 
{
	/**
	 * verifies given name
	 * @param name name to check
	 * @return if name is correct
	 */
	public static boolean IsValidName(String name) 
	{
	    return name.length() >= 3 && name.length() <= 20  //Länge
	        && name.matches("[a-zA-Z ]+")  //Nur Buchstaben und Leerzeichen
	        && !name.startsWith(" ") //Führendes Leerzeichen
	        && !name.endsWith(" "); //Endendes Leerzeichen
	}
}
