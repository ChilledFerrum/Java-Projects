package Student_Grading;

/* 
Full Name: Dimitrios Mpouziotas
ID: 16188 new(1707)
Age: 20
Total Lines of Code: 1350
Last updated at: 12/3/2020
Project Completed at: 11/23/2020
Description:
    This program creates a frame Swing that has 4 different menus. Each menu has a variety of drop down functions that
    complete a certain task.

Menu Items:            fileMenu        |  StudentMenu     			| CourseMenu        	|  DatabaseMenu
=========================================================================================================
Dropdown functions:    SaveItem        |  New_Student     			|  New_Course        	|  Create_Database
                       LoadItem        |  Course_Enroll   			|  Show_performance		|  Load_Database
                       ExitItem  	   |  Show_Performance_Graph	|  Select_Semester		|  Show_All
                       				   |  Show_Student	  			|  Remove_Course		|  Insert_Student
                               		   |  Remove_Student  			     					|  Insert_Course
                                        				  			       					|  Insert_Enroll
                                                                               				|  Clear_Data
    
    Each Function is self explanatory by the function's variable's name.
    Extras
    I used my own Styling methods, as well as a self made Gif Located in Styling/Blinking_warning.gif.
    I visualized the data using Javafx Charts library that asks for a student's ID and shows his performance.
    X Axis = Courses, Y Axis = Grades
    I added Icons to each MenuItem to beautify the swing and make it look more professional.

Contact Info: thealphadub79@gmail.com
*/

public class Student_Grading_Project {
    public static void main(String args[]) {
        StudentFrame frame = new StudentFrame("Student Grades Project");
        
    }
}