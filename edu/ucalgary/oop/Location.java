package edu.ucalgary.oop;

import java.util.ArrayList;

public class Location {
    private String name;
    private String address;
    private ArrayList<DisasterVictim> occupants = new ArrayList<>();
    private ArrayList<Supply> supplies = new ArrayList<>();

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<DisasterVictim> getOccupants() {
        return new ArrayList<>(occupants);
    }

    public void setOccupants(ArrayList<DisasterVictim> occupants) {
        this.occupants = new ArrayList<>(occupants); 
    }

    public ArrayList<Supply> getSupplies() {
        return new ArrayList<>(supplies); 
    }

    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = new ArrayList<>(supplies); 
    }

    public void addOccupant(DisasterVictim occupant) {
        occupants.add(occupant);
    }

    public void removeOccupant(DisasterVictim occupant) {
        occupants.remove(occupant);
    }

    public void addSupply(Supply supply) {
        supplies.add(supply);
    }

    public void removeSupply(Supply supply) {
        supplies.remove(supply);
    }

    public void allocateSupplyToOccupant(DisasterVictim occupant, Supply supply) {
        if (supplies.contains(supply)) {
            occupant.addPersonalBelonging(supply);
            supplies.remove(supply);
        } else {
            throw new IllegalArgumentException("Supply not available");
        }
    }

    public void deallocateSupplyFromOccupant(DisasterVictim occupant, Supply supply) {
        if (occupant.hasSupply(supply)) {
            occupant.removePersonalBelonging(supply);
            supplies.add(supply);
        } else {
            throw new IllegalArgumentException("The occupant does not have this supply.");
        }
    }

    @Override
    public String toString() {
        return name + " - " + address;
    }

}
