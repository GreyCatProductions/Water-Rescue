package tests;

import scenario_creation_package.Sonar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SonarTester 
{
    private Sonar sonar;

    @BeforeEach
    public void setUp() 
    {
        sonar = new Sonar("Test Sonar", 2, 6, 0.5f, "Test Description", new javax.swing.ImageIcon());
    }

    @Test
    public void testConstructor() 
    {
        assertNotNull(sonar, "Sonar should be properly instantiated.");
        assertEquals("Test Sonar", sonar.getName(), "Sonar name should match.");
        assertEquals(2, sonar.getAmount(), "Sonar amount should match.");
        assertEquals(6, sonar.radius, "Sonar radius should match.");
        assertEquals(0.5f, sonar.maxNoise, "Sonar float value should match.");
    }
}
