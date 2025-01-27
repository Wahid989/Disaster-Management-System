package edu.ucalgary.oop;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private LocalDate dateOfTreatment;
    private String details;
    private String date;

    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatmentStr) {
        setLocation(location);
        this.treatmentDetails = treatmentDetails;
        setDateOfTreatment(dateOfTreatmentStr); 
    }

    public String getDetails() {
        return details;
    }

    public String getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    public void setTreatmentDetails(String treatmentDetails) throws IllegalArgumentException {
        this.treatmentDetails = treatmentDetails;
    }

    public void setDateOfTreatment(String dateOfTreatmentStr) {
        try {
            this.dateOfTreatment = LocalDate.parse(dateOfTreatmentStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD", e);
        }
    }

    public LocalDate getDateOfTreatment() {
        return this.dateOfTreatment;
    }
    
}
