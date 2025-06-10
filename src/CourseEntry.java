/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */

public class CourseEntry {
    private final String courseCode;
    private final String description;

    public CourseEntry(String courseCode, String description) {
        this.courseCode  = courseCode;
        this.description = description;
    }
    public String getCourseCode()  { return courseCode; }
    public String getDescription() { return description; }
}