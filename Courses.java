/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Student;


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
import DbConnection.Myconnection;

public class Courses {
    
    private Connection con;
    private static final Logger logger = Logger.getLogger(Courses.class.getName());

    public Courses() {
        con = Myconnection.getConnection();
    }

    public int getMax() {
        int id = 0;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT MAX(ID) FROM course");

            if (rs.next()) {
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

    public void insertCourse(int studentId, int semester, String course1, String course2, String course3, String course4, String course5) {
        String sql = "INSERT INTO course (studentId, semester, course1, course2, course3, course4, course5) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, semester);
            pstmt.setString(3, course1);
            pstmt.setString(4, course2);
            pstmt.setString(5, course3);
            pstmt.setString(6, course4);
            pstmt.setString(7, course5);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.log(Level.INFO, "Course information was inserted successfully!");
                JOptionPane.showMessageDialog(null, "Course information was inserted successfully!");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while inserting course data", ex);
            JOptionPane.showMessageDialog(null, "Error inserting course data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ResultSet getAllCourses() {
        String query = "SELECT * FROM course";
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while retrieving course data", ex);
            JOptionPane.showMessageDialog(null, "Error retrieving course data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public ResultSet searchCourseByStudentId(int studentId) {
        String query = "SELECT * FROM course WHERE studentId = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, studentId);
            return pstmt.executeQuery();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while searching course data", ex);
            JOptionPane.showMessageDialog(null, "Error searching course data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void deleteCourse(int studentId) {
        String sql = "DELETE FROM course WHERE studentId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.log(Level.INFO, "Course information was deleted successfully!");
                JOptionPane.showMessageDialog(null, "Course information was deleted successfully!");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception occurred while deleting course data", ex);
            JOptionPane.showMessageDialog(null, "Error deleting course data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   public void updateCourse(int studentId, int semester, String course1, String course2, String course3, String course4, String course5) {
    String sql = "UPDATE course SET semester=?, course1=?, course2=?, course3=?, course4=?, course5=? WHERE studentId=?";

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, semester);
        pstmt.setString(2, course1);
        pstmt.setString(3, course2);
        pstmt.setString(4, course3);
        pstmt.setString(5, course4);
        pstmt.setString(6, course5);
        pstmt.setInt(7, studentId); 

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            logger.log(Level.INFO, "Course information updated successfully!");
            JOptionPane.showMessageDialog(null, "Course information updated successfully!");
        }
    } catch (SQLException ex) {
        logger.log(Level.SEVERE, "SQL Exception occurred while updating course data", ex);
        JOptionPane.showMessageDialog(null, "Error updating course data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
   }


    public void getAllCourseValues(JTable jTable, String searchValue) {
        String query = "SELECT * FROM course";
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int studentId = rs.getInt("studentId");
                int semester = rs.getInt("semester");
                String course1 = rs.getString("course1");
                String course2 = rs.getString("course2");
                String course3 = rs.getString("course3");
                String course4 = rs.getString("course4");
                String course5 = rs.getString("course5");

                model.addRow(new Object[]{studentId, semester, course1, course2, course3, course4, course5});
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while populating table", e);
            JOptionPane.showMessageDialog(null, "Error populating table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
