/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Student;


import DbConnection.Myconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentClass extends DelectClass
{
    private Connection con;
    private static final Logger logger = Logger.getLogger(StudentClass.class.getName());
    public String searchValue;

    public StudentClass() {
        con = Myconnection.getConnection();
    }

    public int getMax() {
        int id = 0;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT MAX(studentID) FROM student"); 

            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "SQL Exception occurred while closing resources", ex);
            }
        }

        return id + 1;
    }

    public void insertStudent(int studentID, String studentName, String fatherName, String motherName, String addressLine1, String addressLine2, String gender, String birthofDate) {
        String sql = "INSERT INTO student (studentID, studentName, fatherName, motherName, addressLine1, addressLine2, gender, birthofDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            pstmt.setString(2, studentName);
            pstmt.setString(3, fatherName);
            pstmt.setString(4, motherName);
            pstmt.setString(5, addressLine1);
            pstmt.setString(6, addressLine2);
            pstmt.setString(7, gender);
            pstmt.setString(8, birthofDate);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.log(Level.INFO, "A new student was inserted successfully!");
                JOptionPane.showMessageDialog(null, "A new student was inserted successfully!");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while inserting data", ex);
            JOptionPane.showMessageDialog(null, "Error inserting data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

public void getAllStudentValue(JTable jtable1, String searchValue) {
    String query = "SELECT * FROM student";
    DefaultTableModel model = (DefaultTableModel) jtable1.getModel();
    model.setRowCount(0); 

    try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            int studentID = rs.getInt("studentID");
            String studentName = rs.getString("studentName");
            String fatherName = rs.getString("fatherName");
            String motherName = rs.getString("motherName");
            String addressLine1 = rs.getString("addressLine1");
            String addressLine2 = rs.getString("addressLine2");
            String gender = rs.getString("gender");
            String birthofDate = rs.getString("birthofDate");

            model.addRow(new Object[]{studentID, studentName, fatherName, motherName, addressLine1, addressLine2, gender, birthofDate});
        }
    } catch (SQLException e) {
        Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, "SQL Exception occurred while populating table", e);
        JOptionPane.showMessageDialog(null, "Error populating table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void updateStudent(int studentID, String studentName, String fatherName, String motherName, String addressLine1, String addressLine2, String gender, String birthofDate, JTable table) {
    String sql = "UPDATE student SET studentName=?, fatherName=?, motherName=?, addressLine1=?, addressLine2=?, gender=?, birthofDate=? WHERE studentID=?";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, studentName);
        pstmt.setString(2, fatherName);
        pstmt.setString(3, motherName);
        pstmt.setString(4, addressLine1);
        pstmt.setString(5, addressLine2);
        pstmt.setString(6, gender);
        pstmt.setString(7, birthofDate);
        pstmt.setInt(8, studentID);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getLogger(Homepage.class.getName()).log(Level.INFO, "Student record updated successfully!");
            JOptionPane.showMessageDialog(null, "Student record updated successfully!");
            getAllStudentValue(table, "");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, "SQL Exception occurred while updating data", ex);
        JOptionPane.showMessageDialog(null, "Error updating data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
  public void deleteStudent(int studentID) {
        String sql = "DELETE FROM student WHERE studentID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.log(Level.INFO, "Student was deleted successfully!");
                JOptionPane.showMessageDialog(null, "Student was deleted successfully!");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while deleting data", ex);
            JOptionPane.showMessageDialog(null, "Error deleting data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
  public ResultSet searchStudentByID(String studentID) {
        String query = "SELECT * FROM student WHERE studentID = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentID);
            return pstmt.executeQuery();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while searching data", ex);
            JOptionPane.showMessageDialog(null, "Error searching data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public ResultSet getAllStudents() {
        String query = "SELECT * FROM student";
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while retrieving all data", ex);
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


 
}

