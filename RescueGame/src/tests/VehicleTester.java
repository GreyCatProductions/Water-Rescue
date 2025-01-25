package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenario_creation_package.Step;
import scenario_creation_package.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTester 
{
    private Vehicle vehicle;
    private Step[] movePattern;

    @BeforeEach
    public void setUp() 
    {
        movePattern = new Step[]
		{
            new Step(2, 1, false),
            new Step(1, 2, true)
        };

        vehicle = new Vehicle("Test Vehicle", 5, 5, movePattern, true, "Test Description", new javax.swing.ImageIcon());
    }

    @Test
    public void testConstructor() 
    {
        System.out.println("Running testConstructor...");
        assertNotNull(vehicle, "Vehicle should be properly instantiated.");
        assertEquals("Test Vehicle", vehicle.getName(), "Vehicle name should match.");
        assertEquals(5, vehicle.getAmount(), "Vehicle amount should match.");
        assertEquals(5, vehicle.speed, "Vehicle speed should match.");
        assertTrue(vehicle.canRescue, "Vehicle should be able to rescue.");
        assertNotNull(vehicle.steps, "Move pattern should not be null.");
        System.out.println("testConstructor passed.");
    }

    @Test
    public void testMovementPattern() 
    {
        System.out.println("Running testMovementPattern...");
        Step step = vehicle.steps[0];
        assertEquals(2, step.x, "Step X value should be 2.");
        assertEquals(1, step.y, "Step Y value should be 1.");
        assertFalse(step.noUse, "Step should not be marked as noUse.");
        System.out.println("testMovementPattern passed.");
    }
}

