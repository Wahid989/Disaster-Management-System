package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBHandler {

    public void addInquirer(String firstName, String lastName, String phone) {
        String SQL = "INSERT INTO INQUIRER(firstName, lastName, phoneNumber) VALUES(?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addInquiry(int inquirerId, String callDate, String details) {
        String SQL = "INSERT INTO INQUIRY_LOG(inquirer, callDate, details) VALUES(?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, inquirerId);
            pstmt.setDate(2, Date.valueOf(callDate));
            pstmt.setString(3, details);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getOrCreateInquirer(String firstName, String lastName, String phone) {
        String selectSQL = "SELECT id FROM INQUIRER WHERE firstName = ? AND lastName = ? AND phoneNumber = ?";
        String insertSQL = "INSERT INTO INQUIRER(firstName, lastName, phoneNumber) VALUES(?,?,?) RETURNING id";
        
        try (Connection conn = connect();
             PreparedStatement selectPstmt = conn.prepareStatement(selectSQL);
             PreparedStatement insertPstmt = conn.prepareStatement(insertSQL)) {
            
            selectPstmt.setString(1, firstName);
            selectPstmt.setString(2, lastName);
            selectPstmt.setString(3, phone);
            
            try (ResultSet rs = selectPstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); 
                }
            }
            
            insertPstmt.setString(1, firstName);
            insertPstmt.setString(2, lastName);
            insertPstmt.setString(3, phone);
            
            try (ResultSet rs = insertPstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1; 
    }
    
    private Connection connect() {
    String url = "jdbc:postgresql://localhost/ensf380project";
    Properties props = new Properties();
    props.setProperty("user", "oop");
    props.setProperty("password", "ucalgary");
    try {
        Connection conn = DriverManager.getConnection(url, props);
        System.out.println("Connected to the PostgreSQL server successfully.");
        return conn;
    } catch (SQLException e) {
        System.out.println("Connection to the PostgreSQL server failed.");
        e.printStackTrace();
        return null;
    }
    }

    public void testConnection() {
        try {
            Connection conn = connect();
            if (conn != null) {
                System.out.println("Connection to the PostgreSQL server was successful.");
                conn.close();
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("Connection test failed.");
            e.printStackTrace();
        }
    }

    public List<String[]> searchInquirersByName(String searchTerm) throws SQLException {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT firstname, lastname, phonenumber, calldate, details FROM inquiry_log "
                     + "WHERE LOWER(firstname) LIKE ? OR LOWER(lastname) LIKE ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTerm.toLowerCase() + "%");
            pstmt.setString(2, "%" + searchTerm.toLowerCase() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = {
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phonenumber"),
                        rs.getString("calldate"),
                        rs.getString("details")
                    };
                    results.add(row);
                }
            }
        }
        return results;
    }
    
    
    
        public static void main(String[] args) {
        DBHandler dbHandler = new DBHandler();
        dbHandler.testConnection();
    }
}
