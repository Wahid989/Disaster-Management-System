package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Main Menu");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10)); 

        JButton enterDisasterVictimButton = new JButton("Enter Disaster Victim");
        JButton logInquiryButton = new JButton("Log Inquiry");
        JButton exitButton = new JButton("Exit");

        enterDisasterVictimButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                DisasterVictimInformationEntry frame = new DisasterVictimInformationEntry();
                frame.setVisible(true);
            });
        });

        logInquiryButton.addActionListener(e -> {
            DBHandler dbManager = new DBHandler(); 
            SwingUtilities.invokeLater(() -> {
                InquiryLoggerInterface frame = new InquiryLoggerInterface(dbManager);
                frame.setVisible(true);
            });
        });

        exitButton.addActionListener(e -> System.exit(0));

        panel.add(enterDisasterVictimButton);
        panel.add(logInquiryButton);
        panel.add(exitButton);

        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
