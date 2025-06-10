/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */
public class ScheduleEntry {
    private final String semester, courseCode, studentID, status; 
    private final java.sql.Timestamp timestamp;

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, java.sql.Timestamp timestamp) {
        this.semester   = semester;
        this.courseCode = courseCode;
        this.studentID  = studentID;
        this.status     = status;
        this.timestamp  = timestamp;
    }
    public String getSemester()   { return semester; }
    public String getCourseCode() { return courseCode; }
    public String getStudentID()  { return studentID; }
    public String getStatus()     { return status; }
    
    public java.sql.Timestamp getTimestamp() { 
        return timestamp; 
    }
}