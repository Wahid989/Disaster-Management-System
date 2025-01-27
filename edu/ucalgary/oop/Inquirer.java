package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;

public class Inquirer {
    private final String FIRST_NAME;
    private final String LAST_NAME;
    private final String SERVICES_PHONE;
    private List<String> interactions;

    public Inquirer(String firstName, String lastName, String phone) {
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.SERVICES_PHONE = phone;
        this.interactions = new ArrayList<>();
    }

    public void addInteraction(String info) {
        interactions.add(info);
    }

    public String getFirstName() {
        return FIRST_NAME;
    }

    public String getLastName() {
        return LAST_NAME;
    }

    public String getServicesPhoneNum() {
        return SERVICES_PHONE;
    }

    public List<String> getInteractions() {
        return interactions;
    }

}
