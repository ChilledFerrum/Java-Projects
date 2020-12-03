package Student_Grading;

import javax.swing.JOptionPane;

public class Enroll {
    private String Stud_ID;
    private String Course_ID;
    private Double Stud_grade;
    
    // DEFAULT Constructor
    public Enroll(){
        Stud_ID = "";
        Course_ID = "";
        Stud_grade = 0.0;
    }
    
    // BASE Constructor
    public Enroll(String St_ID, String Cs_ID, Double grade){
        this.Stud_ID = St_ID;
        this.Course_ID = Cs_ID;
        if(grade < 0.0)
            JOptionPane.showMessageDialog(null, "ERROR: Please set a valid grade!");
        else
            Stud_grade = grade;
    }
    
    
    // GETTERS & SETTERS
    public void set_StudID(String ID){
        Stud_ID = ID;
    }
    public String get_StudID(){
        return Stud_ID;
    }
    
    public void set_CourseID(String ID){
        Course_ID = ID;
    }
    public String get_CourseID(){
        return Course_ID;
    }
    
    
    public void set_grade(Double grade){
        if(grade < 0)
            JOptionPane.showMessageDialog(null, "ERROR: Please set a valid grade!");
        else
            Stud_grade = grade;
    }
    public Double get_grade(){
        return Stud_grade;
    }
    
    
    public String toString(){
        return "Student ID: " + this.get_StudID() + "\nCourse ID: " + this.get_CourseID() + "\nStudent Grade: " + this.get_grade();
    }
}
