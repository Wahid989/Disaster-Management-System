package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InquiryLoggerInterface extends JFrame {
    private final DBHandler dbHandler;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField callDateField;
    private JTextArea detailsArea;

    public InquiryLoggerInterface(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Inquiry Logger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5)); 

        // Initialize form fields
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        phoneField = new JTextField();
        callDateField = new JTextField();
        detailsArea = new JTextArea();

        // Add components to the frame
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Phone Number:"));
        add(phoneField);
        add(new JLabel("Call Date (YYYY-MM-DD):"));
        add(callDateField);
        add(new JLabel("Details:"));
        add(new JScrollPane(detailsArea)); 

        JButton submitButton = new JButton("Submit Inquiry");
        submitButton.addActionListener(this::handleSubmitAction);
        add(submitButton); 

        pack(); 
        setLocationRelativeTo(null);
    }

    private void handleSubmitAction(ActionEvent event) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String callDate = callDateField.getText().trim();
        String details = detailsArea.getText().trim();

        if (!phone.matches("\\d{3}-\\d{3}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Invalid phone number format. Please use XXX-XXX-XXXX.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate parsedCallDate;
        try {
            parsedCallDate = LocalDate.parse(callDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid call date format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int inquirerId = dbHandler.getOrCreateInquirer(firstName, lastName, phone);
        dbHandler.addInquiry(inquirerId, parsedCallDate.toString(), details);

        JOptionPane.showMessageDialog(this, "Inquiry logged successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
        callDateField.setText("");
        detailsArea.setText("");
    }

    public static void main(String[] args) {
        DBHandler dbHandler = new DBHandler();
        SwingUtilities.invokeLater(() -> new InquiryLoggerInterface(dbHandler).setVisible(true));
    }
}
