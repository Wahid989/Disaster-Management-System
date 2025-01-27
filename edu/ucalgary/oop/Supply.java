package edu.ucalgary.oop;

import java.time.LocalDate;

public class Supply {
    private static int counter = 0; 
    private final int id;
    private String type;
    private int quantity;
    private LocalDate dateAdded;
    private LocalDate expiryDate;
    private String status;
    
    public Supply(String type, int quantity, LocalDate dateAdded, LocalDate expiryDate) {
        this.id = ++counter; 
        this.type = type;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.expiryDate = expiryDate;
        this.status = "Available"; 
    }

    public LocalDate getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDate dateAdded) { this.dateAdded = dateAdded; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getId() { return id; }

    public void setType(String type) { this.type = type; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getType() { return this.type; }
    public int getQuantity() { return this.quantity; }

}
