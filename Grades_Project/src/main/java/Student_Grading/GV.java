 package Student_Grading;

import javax.swing.*;
import java.awt.*;

// GLOBAL VARIABLES...
public class GV {
    // Global static Desktop User File path
    // FIXED PATH Used for most functions
    public static String file_path = System.getProperty("user.home") + "/Desktop/Grades_Project/";
    
    // Global Image Icons used for every Action Listener in the Frame.
    public static ImageIcon warn_icon = new ImageIcon(file_path + "Styling/Blinking_warning.gif");
    public static ImageIcon err_icon = new ImageIcon(file_path + "Styling/Error_Warning.gif");
    public static Image Logo = Toolkit.getDefaultToolkit().getImage(file_path + "Styling/LogoADS.png");
    
    // Global static File name
    public static String fn_global = null;
    public static String DBn_global = null;
    
    // Global SQL Insertion lines
    public static String SQL_in_Students = "INSERT INTO STUDENTS(ST_ID, Firstname, Lastname, Semester) VALUES(?,?,?,?)";
    public static String SQL_in_Enrolls =  "INSERT INTO ENROLLS(Student_ID, Course_ID, Student_Grade) VALUES(?,?,?)";
    public static String SQL_in_Courses =  "INSERT INTO COURSES(C_ID, Name, Semester) VALUES(?,?,?)";
    
    // Global HTML Static lines
    public static String htmlhead = "<html><table border=2>";
    public static String html_style1 = "<tr><h1 style=\"font-family:verdana;font-size:110%;color:black;\">";
    public static String htmltail = "</table></border>";
}