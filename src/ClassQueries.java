
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */
public class ClassQueries {
    private static final java.sql.Connection conn = DBConnection.getConnection();
    private static java.sql.PreparedStatement psAdd, psCodes, psSeats, psDrop;

    public static boolean addClass(ClassEntry ce) {
        try {
            psAdd = conn.prepareStatement("insert into class (semester,courseCode,seats) values (?,?,?)");
            psAdd.setString(1, ce.getSemester());
            psAdd.setString(2, ce.getCourseCode());
            psAdd.setInt   (3, ce.getSeats());
            psAdd.executeUpdate();
            return true; 
        } catch (java.sql.SQLException e) {
        if ("23505".equals(e.getSQLState())) 
            return false;   
        e.printStackTrace();             
        return false;
        }
    }
    public static java.util.ArrayList<String> getAllCourseCodes(String semester) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        try {
            psCodes = conn.prepareStatement("select courseCode from class where semester=? order by courseCode");
            psCodes.setString(1, semester);
            java.sql.ResultSet rs = psCodes.executeQuery();
            while (rs.next()) list.add(rs.getString(1));
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return list;
    }
    public static int getClassSeats(String semester, String courseCode) {
        try {
            psSeats = conn.prepareStatement(
              "select seats from class where semester=? and courseCode=?");
            psSeats.setString(1, semester);
            psSeats.setString(2, courseCode);
            java.sql.ResultSet rs = psSeats.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return 0;
    }
   
    public static void dropClass(String semester, String courseCode) {
       try {
           
            var ps1 = conn.prepareStatement("DELETE FROM schedule WHERE semester=? AND courseCode=?");
            ps1.setString(1, semester);
            ps1.setString(2, courseCode);
            ps1.executeUpdate();

            psDrop = conn.prepareStatement("DELETE FROM class WHERE semester=? AND courseCode=?");
            psDrop.setString(1, semester);
            psDrop.setString(2, courseCode);
            psDrop.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}