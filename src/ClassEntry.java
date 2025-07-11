/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */

public class ClassEntry {
    private final String semester;
    private final String courseCode;
    private final int seats;

    public ClassEntry(String semester, String courseCode, int seats) {
        this.semester   = semester;
        this.courseCode = courseCode;
        this.seats      = seats;
    }
    public String getSemester()   { 
        return semester; 
    }
    public String getCourseCode() {
        return courseCode; 
    }
    public int    getSeats()  {
        return seats; 
    }
}