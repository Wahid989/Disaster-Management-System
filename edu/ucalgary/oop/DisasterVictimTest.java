/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;

import edu.ucalgary.oop.DisasterVictim.DietaryRestriction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisasterVictimTest {
    private DisasterVictim victim;
    private List<Supply> suppliesToSet;
    private String expectedFirstName = "Freda";
    private String expectedLastName = "Smith";
    private LocalDate validBirthDate = LocalDate.of(1987, 5, 21);
    private Integer expectedApproximateAge = null; 
    private String expectedGender = "female";
    private String expectedComments = "Needs medical attention and speaks 2 languages";

    @Before
    public void setUp() {
        victim = new DisasterVictim(expectedFirstName, expectedLastName, validBirthDate, expectedApproximateAge, expectedGender);
        suppliesToSet = new ArrayList<>();
        suppliesToSet.add(new Supply("Water Bottle", 10, LocalDate.now(), LocalDate.now().plusDays(30)));
        suppliesToSet.add(new Supply("Blanket", 5, LocalDate.now(), LocalDate.now().plusDays(60)));
        victim.setPersonalBelongings(suppliesToSet);
        victim.setComments(expectedComments);
    }

    @Test
    public void testConstructor() {
        assertNotNull("Constructor should successfully create an instance", victim);
        assertEquals("Constructor should set the first name correctly", expectedFirstName, victim.getFirstName());
        assertEquals("Constructor should set the last name correctly", expectedLastName, victim.getLastName());
        assertEquals("Constructor should set the birthdate correctly", validBirthDate, victim.getBirthdate());
        assertEquals("Constructor should set the gender correctly", expectedGender, victim.getGender().toLowerCase());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetBothBirthdateAndApproximateAge() {
        victim.setApproximateAge(33); 
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGender() {
        victim.setGender("InvalidGender"); 
    }

  		  
    @Test(expected = DateTimeParseException.class)
    public void testParsingInvalidEntryDateFormat() {
        LocalDate.parse("18/01/2024"); 
    }

    @Test
    public void testSetBirthdate() {
        LocalDate newBirthdate = LocalDate.of(1987, 5, 21);
        victim.setBirthdate(newBirthdate);
        assertEquals("setBirthdate should correctly update the birthdate", newBirthdate, victim.getBirthdate());
    }    

    @Test(expected = DateTimeParseException.class)
    public void testSetDateOfBirthWithInvalidFormat() {
    String invalidDate = "invalid-date-format";
    LocalDate parsedDate = LocalDate.parse(invalidDate);
    victim.setBirthdate(parsedDate);
    }

	
	@Test
    public void testSetAndGetFirstName() {
        String newFirstName = "Alice";
        victim.setFirstName(newFirstName);
        assertEquals("setFirstName should update and getFirstName should return the new first name", newFirstName, victim.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String newLastName = "Smith";
        victim.setLastName(newLastName);
        assertEquals("setLastName should update and getLastName should return the new last name", newLastName, victim.getLastName());
    }

    @Test
    public void testGetComments() {
        victim.setComments(expectedComments);
        assertEquals("getComments should return the initial correct comments", expectedComments, victim.getComments());
    }

    @Test
    public void testSetComments() {
        victim.setComments(expectedComments);
        String newComments = "Has a minor injury on the left arm";
        victim.setComments(newComments);
        assertEquals("setComments should update the comments correctly", newComments, victim.getComments());
    }

    @Test
    public void testGetAssignedSocialID() {
    LocalDate entryDate = LocalDate.now();
    DisasterVictim previousVictim = new DisasterVictim("Kash", "Doe", entryDate, null, "male");
    int expectedSocialId = previousVictim.getAssignedSocialID() + 1;
    DisasterVictim actualVictim = new DisasterVictim("Adeleke", "Doe", entryDate, null, "male");
    assertEquals("getAssignedSocialID should return the expected social ID", expectedSocialId, actualVictim.getAssignedSocialID());
    }


    @Test
    public void testGetEntryDate() {
    LocalDate expectedEntryDate = LocalDate.now();  
    String expectedEntryDateString = expectedEntryDate.toString(); 
    assertEquals("getEntryDate should return the expected entry date", expectedEntryDateString, victim.getEntryDate());
}

    @Test
public void testAddFamilyConnection() {
    DisasterVictim victim1 = new DisasterVictim("Jane", "Doe", LocalDate.now(), null, "female");
    DisasterVictim victim2 = new DisasterVictim("John", "Doe", LocalDate.now(), null, "male");

    FamilyRelation relation1 = new FamilyRelation(victim1, "sibling", victim2);

    victim1.addFamilyConnection(relation1);

    List<FamilyRelation> familyConnections = Arrays.asList(victim1.getFamilyConnections());
    assertTrue("Family connection should be added", familyConnections.contains(relation1));
}

@Test
public void testRemoveFamilyConnection() {
    DisasterVictim victim1 = new DisasterVictim("Jane", "Doe", LocalDate.now(), null, "female");
    DisasterVictim victim2 = new DisasterVictim("John", "Doe", LocalDate.now(), null, "male");

    FamilyRelation relation1 = new FamilyRelation(victim1, "sibling", victim2);

    victim1.addFamilyConnection(relation1);
    victim1.removeFamilyConnection(relation1);

    List<FamilyRelation> currentFamilyConnections = Arrays.asList(victim1.getFamilyConnections());

    assertFalse("Family connection should be removed after calling removeFamilyConnection", currentFamilyConnections.contains(relation1));
}


    @Test
public void testAddPersonalBelonging() {
    LocalDate now = LocalDate.now();
    Supply newSupply = new Supply("Emergency Kit", 1, now, now.plusDays(365));
    victim.addPersonalBelonging(newSupply);
    Supply[] testSupplies = victim.getPersonalBelongings();
    boolean correct = false;

    for (Supply testSupply : testSupplies) {
        if (testSupply.equals(newSupply)) {
            correct = true;
            break;
        }
    }
    assertTrue("addPersonalBelonging should add the supply to personal belongings", correct);
}


    @Test
public void testAllocateSupplyToOccupant() {
    Location location = new Location("Shelter A", "123 Main St");
    DisasterVictim victim = new DisasterVictim("John", "Doe", LocalDate.now(), null, "male");
    Supply water = new Supply("Water Bottle", 1, LocalDate.now(), LocalDate.now().plusDays(30));

    location.addSupply(water); 
    location.addOccupant(victim); 
    location.allocateSupplyToOccupant(victim, water); 
    List<Supply> locationSupplies = new ArrayList<>(location.getSupplies());
    List<Supply> victimSupplies = Arrays.asList(victim.getPersonalBelongings());

    assertFalse("Supply should no longer be in the location", locationSupplies.contains(water));
    assertTrue("Supply should be in the victim's personal belongings", victimSupplies.contains(water));
}




@Test
public void testSetFamilyConnection() {
    DisasterVictim victim1 = new DisasterVictim("Jane", "Doe", LocalDate.now(), null, "female");
    DisasterVictim victim2 = new DisasterVictim("John", "Doe", LocalDate.now(), null, "male");
    
    FamilyRelation relation = new FamilyRelation(victim1, "sibling", victim2);
    List<FamilyRelation> expectedRelations = Arrays.asList(relation);
    
    victim1.setFamilyConnections(expectedRelations);
    
    List<FamilyRelation> actualRelations = Arrays.asList(victim1.getFamilyConnections());
    assertTrue("Family relations should be set", actualRelations.containsAll(expectedRelations));
}


@Test
public void testSetMedicalRecords() {
    Location testLocation = new Location("Shelter Z", "1234 Shelter Ave");
    MedicalRecord testRecord = new MedicalRecord(testLocation, "test for strep", "2024-02-09");

    List<MedicalRecord> newRecords = new ArrayList<>();
    newRecords.add(testRecord);
    victim.setMedicalRecords(newRecords);

    List<MedicalRecord> actualRecords = Arrays.asList(victim.getMedicalRecords());

    assertEquals("Number of medical records should match", 1, actualRecords.size());
    assertTrue("The list should contain the set medical record", actualRecords.contains(testRecord));
}

@Test
public void testSetPersonalBelongings() {
    Supply one = new Supply("Tent", 1, LocalDate.now(), LocalDate.now().plusDays(365));
    Supply two = new Supply("Jug", 3, LocalDate.now(), LocalDate.now().plusDays(365));
    List<Supply> newSupplies = new ArrayList<>(); 
    newSupplies.add(one);
    newSupplies.add(two);

    victim.setPersonalBelongings(newSupplies); 
    List<Supply> actualSupplies = Arrays.asList(victim.getPersonalBelongings()); 

    assertEquals("setPersonalBelongings should correctly update personal belongings", newSupplies.size(), actualSupplies.size());
    assertTrue("setPersonalBelongings should contain all added supplies", actualSupplies.containsAll(newSupplies));
}

@Test
    public void testAddDietaryRestriction() {
        DisasterVictim victim = new DisasterVictim("Mary", "Ann", LocalDate.now(), null, "female");
        victim.addDietaryRestriction(DietaryRestriction.GFML);

        assertTrue("Dietary restriction should be added", victim.getDietaryRestrictions().contains(DietaryRestriction.GFML));
    }

    @Test
    public void testRemoveDietaryRestriction() {
        DisasterVictim victim = new DisasterVictim("Mark", "Doglous", LocalDate.now(), null, "male");
        victim.addDietaryRestriction(DietaryRestriction.GFML);
        victim.removeDietaryRestriction(DietaryRestriction.GFML);

        assertFalse("Dietary restriction should be removed", victim.getDietaryRestrictions().contains(DietaryRestriction.GFML));
    }

}





