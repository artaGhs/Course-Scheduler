
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.derby.iapi.sql.PreparedStatement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */
public class MultiTableQueries {
    private static final java.sql.Connection conn = DBConnection.getConnection();

    public static java.util.ArrayList<ClassDescription> getAllClassDescriptions(String semester) {
        java.util.ArrayList<ClassDescription> list = new java.util.ArrayList<>();
        String sql = "select c.courseCode, co.description, c.seats from class c join course co on c.courseCode = co.courseCode where c.semester=? order by c.courseCode";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setString(1, semester);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new ClassDescription(
                         rs.getString(1), rs.getString(2), rs.getInt(3)));
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return list;
    }
    
           
    public static java.util.ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
       
        java.util.ArrayList<StudentEntry> list = new java.util.ArrayList<>();
        String sql = "select s.studentID, st.firstName, st.lastName from schedule s join student st on s.studentID = st.studentID where s.semester = ? and s.courseCode = ? and s.status = 'S' order by s.timestamp";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new StudentEntry( rs.getString(1),rs.getString(2),rs.getString(3)));
        } catch (java.sql.SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }
    public static java.util.ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {

        java.util.ArrayList<StudentEntry> list = new java.util.ArrayList<>();
        String sql = "select s.studentID, st.firstName, st.lastName from schedule s join student st on s.studentID = st.studentID where s.semester = ? and s.courseCode = ? and s.status = 'W' order by s.timestamp";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new StudentEntry(rs.getString(1),rs.getString(2),rs.getString(3)));
        } catch (java.sql.SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }
}