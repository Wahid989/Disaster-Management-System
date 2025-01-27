package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import java.util.Arrays;

public class DisasterVictimInformationEntry extends JFrame {
    private static final List<DisasterVictim> disasterVictims = new ArrayList<>();
    private JTextField firstNameField, lastNameField, birthDateOrAgeField, medicalDetailsField;
    private JComboBox<String> genderComboBox;
    private JTextArea commentsArea;
    private JComboBox<DisasterVictim.DietaryRestriction> dietaryRestrictionComboBox;
    private JTable victimsTable;
    private DefaultTableModel tableModel;
    private JButton submitButton;
    private JComboBox<DisasterVictim> relatedVictimComboBox;
    private String[] relationshipTypes = {"", "Partner", "Parent", "Child", "Sibling", "Relative", "Guardian", "Friend", "Roommate", "Neighbor"};
    private JComboBox<String> relationshipTypeComboBox;
    private JTextField searchField;
    private JButton searchButton;

    public DisasterVictimInformationEntry() {
        super("Disaster Victim Entry & View");
        try {
            initializeUI();
            initializeVictimsView();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());
    
        // Creating a main panel with ScrollPane for the entire form
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
        // Input Panel for user entries
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);

        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
    
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        birthDateOrAgeField = new JTextField(20);
        genderComboBox = new JComboBox<>();
        HashSet<String> genderOptions = loadGenderOptions();
        for (String option : genderOptions) {
            genderComboBox.addItem(option);
        }
        if (!genderOptions.isEmpty()) {
            genderComboBox.setSelectedIndex(0); 
        }
        commentsArea = new JTextArea(5, 20);
        dietaryRestrictionComboBox = new JComboBox<>(DisasterVictim.DietaryRestriction.values());
        medicalDetailsField = new JTextField(20);
        relationshipTypeComboBox = new JComboBox<>(relationshipTypes);
        relatedVictimComboBox = new JComboBox<>();
        relatedVictimComboBox.addItem(null); 
    
        // Adding components to the inputPanel
        addComponentToPanel(inputPanel, gbc, new JLabel("First Name:"), firstNameField);
        addComponentToPanel(inputPanel, gbc, new JLabel("Last Name:"), lastNameField);
        addComponentToPanel(inputPanel, gbc, new JLabel("Birthdate (YYYY-MM-DD) or Age:"), birthDateOrAgeField);
        addComponentToPanel(inputPanel, gbc, new JLabel("Gender:"), genderComboBox);
        addComponentToPanel(inputPanel, gbc, new JLabel("Dietary Restriction:"), dietaryRestrictionComboBox);
        addComponentToPanel(inputPanel, gbc, new JLabel("Comments:"), new JScrollPane(commentsArea));
        addComponentToPanel(inputPanel, gbc, new JLabel("Medical Details:"), medicalDetailsField);
        addComponentToPanel(inputPanel, gbc, new JLabel("Related Victim:"), relatedVictimComboBox);
        addComponentToPanel(inputPanel, gbc, new JLabel("Relationship Type:"), relationshipTypeComboBox);
        populateRelatedVictimComboBox();
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 0));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        searchPanel.add(searchButton);

        mainPanel.add(inputPanel);
    
        JPanel buttonPanel = new JPanel(new FlowLayout());
        submitButton = new JButton("Submit All Info");
        submitButton.addActionListener(this::handleSubmitAction);
        buttonPanel.add(submitButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); 
        buttonPanel.add(exitButton);
        
        mainPanel.add(buttonPanel); 
        mainPanel.add(searchPanel);
        relatedVictimComboBox.addActionListener(e -> {
            DisasterVictim selectedVictim = (DisasterVictim) relatedVictimComboBox.getSelectedItem();
            if (selectedVictim != null) {
                relationshipTypeComboBox.setSelectedItem(selectedVictim.getRelationshipType());
            }
            commentsArea.setText(selectedVictim != null ? selectedVictim.getComments() : "");
        });

        JScrollPane scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        add(scrollPane, BorderLayout.CENTER);
    
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private void performSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        updateTable(searchText); 
    }

    private void populateRelatedVictimComboBox() {
        relatedVictimComboBox.removeAllItems();
        relatedVictimComboBox.addItem(null); 
        for (DisasterVictim victim : disasterVictims) {
            relatedVictimComboBox.addItem(victim);
        }
    }

    private void addComponentToPanel(JPanel panel, GridBagConstraints gbc, JComponent label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);
        gbc.gridx++;
        panel.add(component, gbc);
    }

    private void initializeVictimsView() {
        String[] columnNames = {"First Name", "Last Name", "Birthdate/Age", "Gender", "Dietary Restrictions", "Comments", "Relationship Type", "Related Victim", "Medical Records"};
        tableModel = new DefaultTableModel(columnNames, 0);
        victimsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(victimsTable);
        scrollPane.setPreferredSize(new Dimension(800, 200)); 
        add(scrollPane, BorderLayout.SOUTH);
        relatedVictimComboBox.addActionListener(e -> {
            DisasterVictim selectedVictim = (DisasterVictim) relatedVictimComboBox.getSelectedItem();
            if (selectedVictim != null) {
                relationshipTypeComboBox.setSelectedItem(selectedVictim.getRelationshipType());
            } else {
                relationshipTypeComboBox.setSelectedIndex(0); 
            }
            commentsArea.setText(selectedVictim != null ? selectedVictim.getComments() : "");
        });
    }

    private void handleSubmitAction(ActionEvent e) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String ageOrBirthdate = birthDateOrAgeField.getText().trim();
        String gender = (String) genderComboBox.getSelectedItem();
        String relationshipType = (String) relationshipTypeComboBox.getSelectedItem();

        DisasterVictim victim = null;
        try {
            LocalDate birthDate = LocalDate.parse(ageOrBirthdate, DateTimeFormatter.ISO_LOCAL_DATE);
            victim = new DisasterVictim(firstName, lastName, birthDate, null, gender);
        } catch (DateTimeParseException ex) {
            try {
                int approximateAge = Integer.parseInt(ageOrBirthdate);
                victim = new DisasterVictim(firstName, lastName, null, approximateAge, gender);
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "Birthdate or age is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (victim != null) {
            DisasterVictim.DietaryRestriction dietaryRestriction = (DisasterVictim.DietaryRestriction) dietaryRestrictionComboBox.getSelectedItem();
            DisasterVictim relatedVictim = (DisasterVictim) relatedVictimComboBox.getSelectedItem();
            if (relatedVictim != null) {
                victim.setRelationshipDetails(relatedVictim.getFirstName() + " " + relatedVictim.getLastName());
            } else {
                victim.setRelationshipDetails(""); 
            }

            String comments = commentsArea.getText().trim();
            String medicalDetails = medicalDetailsField.getText().trim();
            victim.setRelationshipType(relationshipType);
            victim.addDietaryRestriction(dietaryRestriction);
            victim.setComments(comments);
            victim.setRelationshipType(relationshipType);
            if (!medicalDetails.isEmpty()) {
                MedicalRecord medicalRecord = new MedicalRecord(null, medicalDetails, LocalDate.now().toString());
                victim.addMedicalRecord(medicalRecord);
            }

            disasterVictims.add(victim);
            upTable();
            populateRelatedVictimComboBox();
            JOptionPane.showMessageDialog(this, "All information submitted successfully.");
            clearForm();
        }
    }

    private String formatMedicalRecords(MedicalRecord[] medicalRecords) {
        return Arrays.stream(medicalRecords)
                     .map(MedicalRecord::getTreatmentDetails)
                     .collect(Collectors.joining(", "));
    }

    private void updateTable(String searchFilter) {
        tableModel.setRowCount(0);
        for (DisasterVictim victim : disasterVictims) {
            // Convert to lower case for case-insensitive search
            String fullName = victim.getFirstName() + " " + victim.getLastName();
            if (fullName.toLowerCase().contains(searchFilter)) {
                String relationshipDetails = victim.getRelationshipDetails();
    
                Object[] row = {
                    victim.getFirstName(),
                    victim.getLastName(),
                    victim.getFormattedBirthDate(),
                    victim.getGender(),
                    victim.getDietaryRestrictions().stream().map(Enum::toString).collect(Collectors.joining(", ")),
                    victim.getComments(),
                    victim.getRelationshipType(),
                    relationshipDetails,
                    formatMedicalRecords(victim.getMedicalRecords())
                };
                tableModel.addRow(row);
            }
        }
    }

    private void upTable() {
        updateTable("");
    }
    

    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        birthDateOrAgeField.setText("");
        if (genderComboBox.getItemCount() > 0) {
            genderComboBox.setSelectedIndex(0);
        }
        dietaryRestrictionComboBox.setSelectedIndex(0);
        commentsArea.setText("");
        medicalDetailsField.setText("");
        if (relationshipTypeComboBox.getItemCount() > 0) {
            relationshipTypeComboBox.setSelectedIndex(0);
        }
        if (relatedVictimComboBox.getItemCount() > 0) {
            relatedVictimComboBox.setSelectedIndex(0);
        }
    }
    


    private HashSet<String> loadGenderOptions() {
        HashSet<String> genderOptions = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("GenderOptions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                genderOptions.add(line.trim());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading gender options from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return genderOptions;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DisasterVictimInformationEntry::new);
    }
}
