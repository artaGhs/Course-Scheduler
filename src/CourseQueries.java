/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */
public class CourseQueries {
    private static final java.sql.Connection conn = DBConnection.getConnection();
    private static java.sql.PreparedStatement psAdd, psCodes;

    public static void addCourse(CourseEntry c) {
        try {
            psAdd = conn.prepareStatement("insert into course (courseCode, description) values (?,?)");
            psAdd.setString(1, c.getCourseCode());
            psAdd.setString(2, c.getDescription());
            psAdd.executeUpdate();
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
    }
    public static java.util.ArrayList<String> getAllCourseCodes() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        try {
            psCodes = conn.prepareStatement("select courseCode from course order by courseCode");
            java.sql.ResultSet rs = psCodes.executeQuery();
            while (rs.next()) list.add(rs.getString(1));
        } catch (java.sql.SQLException e) { e.printStackTrace(); }
        return list;
    }
}