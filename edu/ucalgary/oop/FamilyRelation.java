package edu.ucalgary.oop;

public class FamilyRelation {
    private DisasterVictim personOne;
    private String relationshipTo;
    private DisasterVictim personTwo;
    private String relation;
    private DisasterVictim relatedVictim;

    // Constructor
    public FamilyRelation(DisasterVictim personOne, String relationshipToPersonTwo, DisasterVictim personTwo) {
        this.personOne = personOne;
        this.relationshipTo = relationshipToPersonTwo; // Corrected to match the parameter name
        this.personTwo = personTwo;
        personOne.addFamilyConnection(this); // Use addFamilyConnection as per your implementation
        personTwo.addFamilyConnection(this);
    }

    // Getter and setter for personOne
    public DisasterVictim getPersonOne() {
        return personOne;
    }

    public void setPersonOne(DisasterVictim personOne) {
        this.personOne = personOne;
    }

    // Getter and setter for relationshipTo
    public String getRelationshipTo() {
        return relationshipTo;
    }

    public String getRelation() {
        return relation;
    }

    public DisasterVictim getRelatedVictim() {
        return relatedVictim;
    }

    public void setRelationshipTo(String relationshipTo) {
        this.relationshipTo = relationshipTo;
    }

    // Getter and setter for personTwo
    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    public void setPersonTwo(DisasterVictim personTwo) {
        this.personTwo = personTwo;
    }
}
