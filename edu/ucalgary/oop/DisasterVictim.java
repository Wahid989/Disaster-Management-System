package edu.ucalgary.oop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.time.format.DateTimeFormatter;

    public class DisasterVictim {
        private static int counter = 0;
        private String firstName;
        private String lastName;
        private LocalDate birthdate; 
        private Integer approximateAge; 
        private final int ASSIGNED_SOCIAL_ID;
        private List<FamilyRelation> familyConnections = new ArrayList<>();
        private List<MedicalRecord> medicalRecords = new ArrayList<>();
        private List<Supply> personalBelongings = new ArrayList<>();
        private final LocalDate ENTRY_DATE;
        private String gender;
        private List<DietaryRestriction> dietaryRestrictions = new ArrayList<>(); 
        private String comments;
        private String relationshipDetails;
        private static final Set<String> genderOptions = new HashSet<>();
        private String relationshipType;

        static {
            loadGenderOptions("GenderOptions.txt");
        }

        public DisasterVictim(String firstName, String lastName, LocalDate birthdate, Integer approximateAge, String gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.approximateAge = approximateAge;
            this.gender = gender.toLowerCase();
            this.ENTRY_DATE = LocalDate.now();
            this.ASSIGNED_SOCIAL_ID = generateSocialID();
        }

        public boolean hasSupply(Supply supply) {
            return personalBelongings.contains(supply);
        }

        private static int generateSocialID() {
            counter++;
            return counter;
        }

    
        // Getters and setters
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public LocalDate getBirthdate() {
            return birthdate;
        }

        public String getFormattedBirthDate() {
            if (birthdate != null) {
                return birthdate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            } else {
                return String.valueOf(approximateAge);
            }
        }

        public void setBirthdate(LocalDate birthdate) {
            if (this.approximateAge != null) {
                throw new IllegalStateException("Cannot set both birthdate and approximate age.");
            }
            this.birthdate = birthdate;
        }
        
        public void setApproximateAge(Integer approximateAge) {
            if (this.birthdate != null) {
                throw new IllegalStateException("Cannot set both approximate age and birthdate.");
            }
            this.approximateAge = approximateAge;
        }

        public Integer getApproximateAge() {
            return approximateAge;
        }

        public int getAssignedSocialID() {
            return ASSIGNED_SOCIAL_ID;
        }

        // In the DisasterVictim class
        public String getRelationshipDetails() {
            return relationshipDetails;
        }

        public void setRelationshipDetails(String relationshipDetails) {
        this.relationshipDetails = relationshipDetails;
    }

        public FamilyRelation[] getFamilyConnections() {
            return familyConnections.toArray(new FamilyRelation[0]);
        }

        public MedicalRecord[] getMedicalRecords() {
            return medicalRecords.toArray(new MedicalRecord[0]);
        }

        public Supply[] getPersonalBelongings() {
            return personalBelongings.toArray(new Supply[0]); 
        }

        public void setFamilyConnections(List<FamilyRelation> connections) {
            this.familyConnections = new ArrayList<>(connections);
            for (FamilyRelation newRecord : connections) {
                addFamilyConnection(newRecord);
            }
        }

        public void setMedicalRecords(List<MedicalRecord> records) {
            this.medicalRecords.clear(); 
            this.medicalRecords.addAll(records);
        }

        public void setPersonalBelongings(List<Supply> belongings) {
            this.personalBelongings = new ArrayList<>(belongings);
        }

        public void addPersonalBelonging(Supply supply) {
            if (this.personalBelongings == null) {
                this.personalBelongings = new ArrayList<>();
            }
            this.personalBelongings.add(supply);
            }


        public void removePersonalBelonging(Supply unwantedSupply) {
        if (this.personalBelongings != null) {
            this.personalBelongings.remove(unwantedSupply);
        }   
        }


        public void addFamilyConnection(FamilyRelation relation) {
            if (!this.familyConnections.contains(relation)) {
                this.familyConnections.add(relation);
            }
        }
        
        public void removeFamilyConnection(FamilyRelation relation) {
            this.familyConnections.remove(relation);
        }

        public String getRelationshipType() {
            return relationshipType;
        }
    
        public void setRelationshipType(String relationshipType) {
            this.relationshipType = relationshipType;
        }
        


        public void addMedicalRecord(MedicalRecord record) {
            medicalRecords.add(record);
        }

        public String getEntryDate() {
            return ENTRY_DATE.toString(); 
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments =  comments;
        }

        public String getGender() {
            return gender;
        }

        private static void loadGenderOptions(String resourcePath) {
            try (InputStream inputStream = DisasterVictim.class.getClassLoader().getResourceAsStream(resourcePath);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    genderOptions.add(line.trim().toLowerCase());
                }
            } catch (IOException e) {
                System.err.println("Error loading gender options: " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("GenderOptions.txt file not found. Make sure the file is in src/main/resources.");
            }
        }

        public void setGender(String gender) {
            String genderLowerCase = gender.toLowerCase();
            if (!genderOptions.contains(genderLowerCase)) {
                throw new IllegalArgumentException("Invalid gender. Acceptable values are from GenderOptions.txt.");
            }
            this.gender = genderLowerCase;
        }
        
        public enum DietaryRestriction {
            AVML, DBML, GFML, KSML, LSML, MOML, PFML, VGML, VJML
        }

        public List<DietaryRestriction> getDietaryRestrictions() {
            return this.dietaryRestrictions;
        }

        public void addDietaryRestriction(DietaryRestriction restriction) {
            if (!dietaryRestrictions.contains(restriction)) {
                dietaryRestrictions.add(restriction);
            }
        }

        public void removeDietaryRestriction(DietaryRestriction restriction) {
            dietaryRestrictions.remove(restriction);
        }

        @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DisasterVictim)) return false;
    DisasterVictim victim = (DisasterVictim) o;
    return Objects.equals(firstName, victim.firstName) &&
           Objects.equals(lastName, victim.lastName) &&
           Objects.equals(birthdate, victim.birthdate) && // Compare birthdate or age depending on which is used
           Objects.equals(approximateAge, victim.approximateAge) &&
           Objects.equals(gender, victim.gender) &&
           Objects.equals(comments, victim.comments);
}

@Override
public int hashCode() {
    return Objects.hash(firstName, lastName, birthdate, approximateAge, gender, comments);
}

@Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

}

