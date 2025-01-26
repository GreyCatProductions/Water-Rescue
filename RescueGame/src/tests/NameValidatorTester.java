package tests;
import org.junit.jupiter.api.Test;
import ui_package.NameValidator;

import static org.junit.jupiter.api.Assertions.*;

public class NameValidatorTester 
{
    @Test
    void testValidNames() 
    {
        assertTrue(NameValidator.IsValidName("Max"), "Name 'Max' should be valid.");
        assertTrue(NameValidator.IsValidName("Max Muster"), "Name 'Max Muster' should be valid.");
        assertTrue(NameValidator.IsValidName("Erstname Zweitname"), "Name 'Erstname Zweitname' should be valid.");
    }

    @Test
    void testInvalidNamesLength() 
    {
        assertFalse(NameValidator.IsValidName("Jo"), "Name 'Jo' should be invalid (too short).");
        assertFalse(NameValidator.IsValidName("Viel zu langer Name lalalallalalalalla"), 
                    "Name 'Viel zu langer Name lalalallalalalalla' should be invalid (too long).");
    }

    @Test
    void testInvalidNamesCharacterTypes() 
    {
        assertFalse(NameValidator.IsValidName("Max123"), "Name 'Max123' should be invalid (contains numbers).");
        assertFalse(NameValidator.IsValidName("Max@"), "Name 'Max@' should be invalid (contains special characters).");
        assertFalse(NameValidator.IsValidName("Max-"), "Name 'Max-' should be invalid (contains special characters).");
        assertFalse(NameValidator.IsValidName("1234"), "Name '1234' should be invalid (only numbers).");
    }

    @Test
    void testInvalidNamesEdgeCases() 
    {
        assertFalse(NameValidator.IsValidName(" John "), "Name ' John ' should be invalid (leading/trailing spaces).");
        assertFalse(NameValidator.IsValidName("          "), "Name with only spaces should be invalid.");
        assertFalse(NameValidator.IsValidName("John  "), "Name 'John  ' should be invalid (trailing spaces).");
	    }
}
