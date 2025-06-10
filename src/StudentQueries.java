
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */
public class StudentQueries {
    private static final java.sql.Connection conn = DBConnection.getConnection();
    private static java.sql.PreparedStatement psAdd, psAll, psGet, psDrop;

    public static void addStudent(StudentEntry s) {
        try {
            psAdd = conn.prepareStatement("insert into student (studentID,firstName,lastName) values (?,?,?)");
            psAdd.setString(1, s.getStudentID());
            psAdd.setString(2, s.getFirstName());
            psAdd.setString(3, s.getLastName());
            psAdd.executeUpdate();
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
    }
    public static java.util.ArrayList<StudentEntry> getAllStudents() {
        java.util.ArrayList<StudentEntry> list = new java.util.ArrayList<>();
        try {
            psAll = conn.prepareStatement(
              "select studentID,firstName,lastName from student order by studentID");
            java.sql.ResultSet rs = psAll.executeQuery();
            while (rs.next())
                list.add(new StudentEntry(rs.getString(1), rs.getString(2), rs.getString(3)));
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return list;
    }
    public static StudentEntry getStudent(String studentID) {
        try {
            psGet = conn.prepareStatement(
              "select studentID,firstName,lastName from student where studentID=?");
            psGet.setString(1, studentID);
            java.sql.ResultSet rs = psGet.executeQuery();
            return rs.next()
                 ? new StudentEntry(rs.getString(1), rs.getString(2), rs.getString(3))
                 : null;
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return null;
    }

    
    public static void dropStudent(String studentID) {
        try {
            
            ArrayList<ScheduleEntry> entries = ScheduleQueries.getScheduleByStudentAll(studentID);
            
            
            for (ScheduleEntry se : entries) {

                ScheduleQueries.dropStudentScheduleByCourse(se.getSemester(), se.getCourseCode(), studentID);

                if ("S".equals(se.getStatus())) {
                    ArrayList<ScheduleEntry> waitListed = ScheduleQueries.getWaitlistedStudentsByClass(se.getSemester(), se.getCourseCode());
                    if (!waitListed.isEmpty()) {
                        ScheduleEntry next = waitListed.get(0);
                       
                        ScheduleQueries.updateScheduleEntry(next);
                        

//                        ScheduleQueries.updateScheduleEntry(new ScheduleEntry(se.getSemester(),se.getCourseCode(),next.getStudentID(),"S",null ));
                    }
                }
            }

            try (var ps = conn.prepareStatement("DELETE FROM student WHERE studentID = ?")) {
                ps.setString(1, studentID);
                ps.executeUpdate();
            }
            try (var ps = conn.prepareStatement("DELETE FROM schedule WHERE studentID = ?")) {
                ps.setString(1, studentID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
