/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024
Licensed under GPL v3
See LICENSE.txt for more information.
*/
package edu.ucalgary.oop;


import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class FamilyRelationTest {

    private DisasterVictim personOne = new DisasterVictim("John", "Dalan", LocalDate.parse("2024-01-19"), null, "male");
    private DisasterVictim personTwo = new DisasterVictim("Jane", "Dalan", LocalDate.parse("2024-02-20"), null, "female");

    private String relationshipTo = "sibling";
    private FamilyRelation testFamilyRelationObject = new FamilyRelation(personOne, relationshipTo, personTwo);
    
    @Test
    public void testObjectCreation() {
        assertNotNull(testFamilyRelationObject);
    }

    @Test
    public void testConstructor() {
        LocalDate birthDate1 = LocalDate.parse("2000-01-01");
        LocalDate birthDate2 = LocalDate.parse("2005-05-05");

        DisasterVictim personOne = new DisasterVictim("John", "Doe", birthDate1, null, "male");
        DisasterVictim personTwo = new DisasterVictim("Jane", "Doe", birthDate2, null, "female");

        String relationship = "parent";

        FamilyRelation familyRelation = new FamilyRelation(personOne, relationship, personTwo);

        assertEquals("personOne should match the one passed to the constructor", personOne, familyRelation.getPersonOne());
        assertEquals("personTwo should match the one passed to the constructor", personTwo, familyRelation.getPersonTwo());
        assertEquals("relationshipTo should match the one passed to the constructor", relationship, familyRelation.getRelationshipTo());

        FamilyRelation[] personOneConnections = personOne.getFamilyConnections();
        FamilyRelation[] personTwoConnections = personTwo.getFamilyConnections();

        assertEquals("personOne should have one family connection", 1, personOneConnections.length);
        assertEquals("personTwo should have one family connection", 1, personTwoConnections.length);

        assertEquals("The family connection in personOne's list should match", familyRelation, personOneConnections[0]);
        assertEquals("The family connection in personTwo's list should match", familyRelation, personTwoConnections[0]);
    }
	
    @Test
public void testSetAndGetPersonOne() {
    LocalDate birthDate = LocalDate.parse("2024-03-21");
    DisasterVictim newPersonOne = new DisasterVictim("New", "Person", birthDate, null, "non-binary");
    testFamilyRelationObject.setPersonOne(newPersonOne);
    assertEquals("setPersonOne should update personOne", newPersonOne, testFamilyRelationObject.getPersonOne());
}

    @Test
public void testSetAndGetPersonTwo() {
    LocalDate birthDate = LocalDate.parse("2024-04-22");
    DisasterVictim newPersonTwo = new DisasterVictim("Another", "Person", birthDate, null, "non-binary");
    testFamilyRelationObject.setPersonTwo(newPersonTwo);
    assertEquals("setPersonTwo should update personTwo", newPersonTwo, testFamilyRelationObject.getPersonTwo());
}


    @Test
    public void testSetAndGetRelationshipTo() {
        String newRelationship = "parent";
        testFamilyRelationObject.setRelationshipTo(newRelationship);
        assertEquals("setRelationshipTo should update the relationship", newRelationship, testFamilyRelationObject.getRelationshipTo());
    }
}
