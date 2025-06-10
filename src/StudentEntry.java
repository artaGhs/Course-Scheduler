/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arta Ghasemi
 */
public class StudentEntry {
    private final String studentID, firstName, lastName;

    public StudentEntry(String studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName  = lastName;
    }
    public String getStudentID() { return studentID; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    
    @Override
    public String toString(){
        return lastName+", " + firstName + " " +studentID;
    }
}