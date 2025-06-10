
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
public class ScheduleQueries {
    private static final java.sql.Connection conn = DBConnection.getConnection();

    public static void addScheduleEntry(ScheduleEntry se) {
        try {
            
            var ps0 = conn.prepareStatement("SELECT COUNT(*) FROM schedule WHERE semester=? AND courseCode=? AND status='S'");
            ps0.setString(1, se.getSemester());
            ps0.setString(2, se.getCourseCode());
            var rs0 = ps0.executeQuery();
            rs0.next();
            int taken = rs0.getInt(1);

            int capacity = ClassQueries.getClassSeats(se.getSemester(), se.getCourseCode());

            String status = taken < capacity ? "S" : "W";
            
            var ps = conn.prepareStatement("INSERT INTO schedule (semester,courseCode,studentID,status,timestamp) VALUES (?,?,?,?,?)");
            ps.setString(1, se.getSemester());
            ps.setString(2, se.getCourseCode());
            ps.setString(3, se.getStudentID());
            ps.setString(4, status);
            ps.setTimestamp(5, se.getTimestamp());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static java.util.ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        java.util.ArrayList<ScheduleEntry> list = new java.util.ArrayList<>();
        try (var ps = conn.prepareStatement("select courseCode,status,timestamp from schedule where semester=? and studentID=? order by timestamp")) {
            ps.setString(1, semester);
            ps.setString(2, studentID);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new ScheduleEntry(semester, rs.getString(1), studentID,rs.getString(2), rs.getTimestamp(3)));
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return list;
    }
    public static int getScheduledStudentCount(String semester, String courseCode) {
        try (var ps = conn.prepareStatement("SELECT COUNT(*) FROM schedule WHERE semester=? AND courseCode=? AND status='S'")) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            var rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static java.util.ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        java.util.ArrayList<ScheduleEntry> list = new java.util.ArrayList<>();
        try (var ps = conn.prepareStatement( "select studentID,timestamp from schedule where semester=? and courseCode=? and status='W' order by timestamp")) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new ScheduleEntry(semester, courseCode, rs.getString(1), "W", rs.getTimestamp(2)));
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        
        return list;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        String currentStatus = null;
        
        try (var ps = conn.prepareStatement(
                "SELECT status FROM schedule WHERE semester=? AND courseCode=? AND studentID=?")) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            ps.setString(3, studentID);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    currentStatus = rs.getString(1);  
                    System.out.println(currentStatus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if("S".equals(currentStatus)){
            ArrayList<ScheduleEntry> waitListed = getWaitlistedStudentsByClass(semester,courseCode);
            if(!waitListed.isEmpty()){
                ScheduleEntry next = waitListed.get(0);
                
                System.out.println("FIRST WAITLISTED: "+ semester + "  |  " + next.getStudentID());

                ScheduleQueries.updateScheduleEntry(next);
                
                
                
            }
            
            
            
        } 
        try (var ps = conn.prepareStatement("DELETE FROM schedule WHERE semester=? AND courseCode=? AND studentID=?")) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            ps.setString(3, studentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
    }      
   
    public static void dropScheduleByCourse(String semester, String courseCode) {
        try (var ps = conn.prepareStatement("DELETE FROM schedule WHERE semester=? AND courseCode=?")) {
            ps.setString(1, semester);
            ps.setString(2, courseCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry) {
        try (var ps = conn.prepareStatement("UPDATE schedule SET status='S' WHERE semester=? AND courseCode=? AND studentID=?")) {
            ps.setString(1, entry.getSemester());
            ps.setString(2, entry.getCourseCode());
            ps.setString(3, entry.getStudentID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudentAll(String studentID) {
        var list = new ArrayList<ScheduleEntry>();
        String sql ="SELECT semester, courseCode, status, timestamp FROM schedule WHERE studentID=? ORDER BY semester, timestamp";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentID);
            var rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ScheduleEntry(rs.getString("semester"),rs.getString("courseCode"),studentID,rs.getString("status"),rs.getTimestamp("timestamp")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}