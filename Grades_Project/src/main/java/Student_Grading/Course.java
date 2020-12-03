package Student_Grading;

public class Course {
    private String Course_name;
    private String Course_sem;
    private String Course_ID;
    
    // DEFAULT Constructor
    public Course(){
        Course_name = "";
        Course_sem = "";
        Course_ID = "";
    }
    // BASE Constructor
    public Course(String name, String sem, String ID){
        Course_name = name;
        Course_sem = sem;
        Course_ID = ID;
    }
    
    // GETTERS & SETTERS
    public void set_name(String n){
        Course_name = n;
    }
    public String get_name(){
        return Course_name;
    }
    
    
    public void set_sem(String s){
        Course_sem = s;
    }
    public String get_sem(){
        return Course_sem;
    }
    
    
    public void set_ID(String ID){
        Course_ID = ID;
    }
    public String get_ID(){
        return Course_ID;
    }
    
    
    // RETURN ALL INFO
    public String toString(){
        return "Course: " + this.get_name() + "\nTeaching Semester: " + this.get_sem() + "\nCourse ID: " + this.get_ID();
    }
}
