/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024
Licensed under GPL v3
See LICENSE.txt for more information.
*/
package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class ReliefServiceTest {
    private ReliefService reliefService;
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private Location lastKnownLocation;
    private String validDate = "2024-02-10";
    private String invalidDate = "2024/02/10";
    private String expectedInfoProvided = "Looking for family member";
    private String expectedLogDetails = "Inquirer: John, Missing Person: Jane Alex, Date of Inquiry: 2024-02-10, Info Provided: Looking for family member, Last Known Location: University of Calgary"; 

    @Before
    public void setUp() {
        inquirer = new Inquirer("John", "Alex", "1234567890");
        inquirer.addInteraction("Looking for family member");
        LocalDate birthdate = LocalDate.parse("1980-01-01"); 
        missingPerson = new DisasterVictim("Jane Alex", "Alex", birthdate, null, "male"); 
        lastKnownLocation = new Location("University of Calgary", "2500 University Dr NW");
        
        reliefService = new ReliefService(inquirer, missingPerson, validDate, expectedInfoProvided, lastKnownLocation);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull("ReliefService object should not be null", reliefService);
    }

    @Test
    public void testGetInquirer() {
        assertEquals("Inquirer should match the one set in setup", inquirer, reliefService.getInquirer());
    }

    @Test
    public void testGetMissingPerson() {
        assertEquals("Missing person should match the one set in setup", missingPerson, reliefService.getMissingPerson());
    }

    @Test
    public void testGetDateOfInquiry() {
        assertEquals("Date of inquiry should match the one set in setup", validDate, reliefService.getDateOfInquiry());
    }

    @Test
    public void testGetInfoProvided() {
        assertEquals("Info provided should match the one set in setup", expectedInfoProvided, reliefService.getInfoProvided());
    }

    @Test
    public void testGetLastKnownLocation() {
        assertEquals("Last known location should match the one set in setup", lastKnownLocation, reliefService.getLastKnownLocation());
    }

    @Test
    public void testSetDateOfInquiryWithValidDate() {
        reliefService.setDateOfInquiry(validDate);
        assertEquals("Setting a valid date should update the date of inquiry", validDate, reliefService.getDateOfInquiry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithInvalidDate() {
        reliefService.setDateOfInquiry(invalidDate); 
    }

    @Test
    public void testGetLogDetails() {
        assertEquals("Log details should match the expected format", expectedLogDetails, reliefService.getLogDetails());
    }

    @Test
    public void testSetInquirer() {
        Inquirer newInquirer = new Inquirer("Alice", "Smith", "9876543210");
        reliefService.setInquirer(newInquirer);
        assertEquals("Setting a new inquirer should update the inquirer", newInquirer, reliefService.getInquirer());
    }

    @Test
    public void testSetMissingPerson() {
        DisasterVictim newMissingPerson = new DisasterVictim("Jack", "Smith", LocalDate.now(), null, "male");
        reliefService.setMissingPerson(newMissingPerson);
        assertEquals("Setting a new missing person should update the missing person", newMissingPerson, reliefService.getMissingPerson());
    }

    @Test
    public void testSetDateOfInquiry() {
        String newDate = "2024-03-15";
        reliefService.setDateOfInquiry(newDate);
        assertEquals("Setting a new date of inquiry should update the date", newDate, reliefService.getDateOfInquiry());
    }

    @Test
    public void testSetInfoProvided() {
        String newInfoProvided = "Seeking medical assistance";
        reliefService.setInfoProvided(newInfoProvided);
        assertEquals("Setting new info provided should update the info", newInfoProvided, reliefService.getInfoProvided());
    }

    @Test
    public void testSetLastKnownLocation() {
        Location newLocation = new Location("Hospital", "123 Medical St");
        reliefService.setLastKnownLocation(newLocation);
        assertEquals("Setting a new last known location should update the location", newLocation, reliefService.getLastKnownLocation());
    }
}
