package tests;

import java.awt.Color;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.GameManager;
import ui_package.GameColors;
import ui_package.UiManager;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateFieldColorTester 
{
	GameManager gameManager;
	UiManager uiManager;
	int x = 1;
	int y = 2;
	@BeforeEach
	public void prepare()
	{
		int size = 10;
		gameManager = new GameManager(true);
		uiManager = new UiManager();
		JPanel dummy = new JPanel();
		gameManager.initializeMainGameVariables(size);
		uiManager.createFields(dummy, size);
	}
	
	@Test  
	public void testSelectedOnly()
	{
		gameManager.selectedX = x;
		gameManager.selectedY = y;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.selectedColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testFoundNotRescued()
	{
		gameManager.foundFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.foundColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testMarkedOnly()
	{
		gameManager.markedFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.markedColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testRescuedOnly()
	{
		gameManager.rescuedFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.rescuedColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testFoundOnly()
	{
		gameManager.foundFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.foundColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testSearchedOnly()
	{
		gameManager.searchedFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.searchedColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testEmpty()
	{
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.seaColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testSearchedAndRescued()
	{
		gameManager.searchedFields[x][y] = true;
		gameManager.rescuedFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.rescuedColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testSearchedandMarkedAndRescued()
	{
		gameManager.markedFields[x][y] = true;
		gameManager.searchedFields[x][y] = true;
		gameManager.rescuedFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.markedColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
	
	@Test  
	public void testSearchedandMarkedAndFound()
	{
		gameManager.markedFields[x][y] = true;
		gameManager.searchedFields[x][y] = true;
		gameManager.foundFields[x][y] = true;
		gameManager.updateFieldColor(x, y);
		Color fieldColor = gameManager.gameFields[x][y].getBackground();
		Color expected = GameColors.foundColor;
		assertEquals(fieldColor, expected, "Expected: " + expected + " got: " + fieldColor);
	}
}
