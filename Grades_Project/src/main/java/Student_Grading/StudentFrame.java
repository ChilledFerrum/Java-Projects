package Student_Grading;

import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.*;

import javafx.application.Application;
import javafx.scene.Scene;	
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.text.DecimalFormat;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class StudentFrame extends JFrame {
    private static ArrayList<Student> mStudents = new ArrayList<Student>();
    private static ArrayList<Course> mCourses = new ArrayList<Course>();
    private static ArrayList<Enroll> mEnrolls = new ArrayList<Enroll>();
    
    
    private JMenu     fileMenu,     StudentMenu,            CourseMenu,        		 DatabaseMenu;
//  =====================================================================================================
    private JMenuItem SaveItem,     New_Student,            New_Course,        		 Create_Database,
                      LoadItem,     Course_Enroll,          Show_performance,		 Load_Database,
                      ExitItem,		Show_Student,			Show_Performance_Graph,	 Show_All_Console,
                           			Remove_Student,			Select_Semester,   		 Insert_Student,
                      										Remove_Course,     		 Insert_Enroll,
                                                            						 Insert_Course,
                                                                               		 Clear_Data;
                                                                               
    private JMenuBar MenuBar;
    private static String ID_G = null;
    public StudentFrame(String title){
        // Title
        super(title);
        setup();
    }
    
    public void setup(){
        this.setSize(400,260);
        this.setResizable(false);
        this.setIconImage(GV.Logo);
        this.setLocation(WIDTH + 100 , HEIGHT + 200);
        this.makeMenu();
        
        this.pack();
        this.ActionListeners();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public boolean isDouble(String d){
        try{
            Double num = Double.parseDouble(d);
        }catch(NumberFormatException e){
            return false;
        }
        int count_dot = 0;
        for(int i = 0 ;  i < d.length(); i++){
            char ch = d.charAt(i);
            if(ch == '.')
                count_dot++;
            if(!(ch >= '0' && ch <= '9'))	
                return false;
        }
        return true;
    }
    
    public void makeMenu(){
        MenuBar = new JMenuBar();
        setJMenuBar(MenuBar);
        
        // FILE COLUMN
        fileMenu = new JMenu("Files");
        LoadItem = new JMenuItem("Load File", 
        						 new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Load_File.png"));
        SaveItem = new JMenuItem("Save File", 
        						 new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Save_File.png"));
        ExitItem = new JMenuItem("Exit", 
        						 new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Close.png"));
        
        // STUDENT COLUMN
        StudentMenu = new JMenu("Students");
        New_Student = new JMenuItem("New Student",
        								new ImageIcon(GV.file_path + "Styling/MenuItem Icons/New_Student.png"));
        Course_Enroll = new JMenuItem("Enroll to Course",
        							  	new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Enroll.png"));
        Show_Student = new JMenuItem("Show Student",
        							 	new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Show_Student.png"));
        Remove_Student = new JMenuItem("Remove Student",
        							   	new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Remove_Student.png"));
        
        // COURSE COLUMN
        CourseMenu = new JMenu("Courses");
        New_Course = new JMenuItem("New Course",
				   				   	new ImageIcon(GV.file_path + "Styling/MenuItem Icons/New_Course.png"));
        Show_performance = new JMenuItem("Show Performance",
        						    new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Show_Performance.png"));
        Show_Performance_Graph = new JMenuItem("Show Performance Graph",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Show_Performance_Graph.png"));
        Select_Semester = new JMenuItem("Select Semester",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Select_Semester.png"));
        Remove_Course = new JMenuItem("Remove Course",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Remove_Course.png"));

        // DATABASE COLUMN
        DatabaseMenu = new JMenu("Database");
        Create_Database = new JMenuItem("Create New Database",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/New_Database.png"));
        Load_Database = new JMenuItem("Load Database",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Load_Database.png"));
        Show_All_Console = new JMenuItem("Show All",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Show_All.png"));
        Insert_Student = new JMenuItem("Insert New Student",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Insert.png"));
        Insert_Enroll = new JMenuItem("Insert New Enroll",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Insert.png")); 
        Insert_Course = new JMenuItem("Insert New Course",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Insert.png"));
        Clear_Data = new JMenuItem("Clear Data",
        							new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Clear_Data.png"));
        // FILE OBJECT ADDING
        fileMenu.add(LoadItem); // Done
        fileMenu.add(SaveItem); // Done
        fileMenu.add(ExitItem); // Done
        
        // STUDENT OBJECT ADDING
        StudentMenu.add(New_Student); // Done
        StudentMenu.add(Course_Enroll); // Done
        StudentMenu.add(Show_Performance_Graph); // Done [Problematic, Unable to run more than once]
        StudentMenu.add(Show_Student);  // Done
        StudentMenu.add(Remove_Student); // Done
        
        //COURSE OBJECT ADDING
        CourseMenu.add(New_Course); // Done
        CourseMenu.add(Show_performance); // Done
        CourseMenu.add(Select_Semester); // Done
        CourseMenu.add(Remove_Course); // Done
        
        // DATABASE OBJECT ADDING
        DatabaseMenu.add(Create_Database); // Done
        DatabaseMenu.add(Load_Database); // Done
        DatabaseMenu.add(Show_All_Console); // Done
        DatabaseMenu.add(Insert_Student); // Done
        DatabaseMenu.add(Insert_Enroll); // Done
        DatabaseMenu.add(Insert_Course); // Done
        DatabaseMenu.add(Clear_Data); // Done
        
        MenuBar.add(fileMenu);
        MenuBar.add(StudentMenu);
        MenuBar.add(CourseMenu);
        MenuBar.add(DatabaseMenu);
        
        ImageIcon background = new ImageIcon(GV.file_path + "Styling/Background_Marbles.png");
        JLabel imageLabel = new JLabel(background);
        
        add(imageLabel);
    }
    
    public static boolean isNotEmpty() {
    	if(!mStudents.isEmpty() && !mEnrolls.isEmpty() && !mCourses.isEmpty()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public void ActionListeners(){
        
        // FILE MENU ITEM ACTION LISTENERS
        SaveItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String File_name = JOptionPane.showInputDialog(null, "Enter File Name: ");
                if(File_name != null) {
                    try{
                        File File_stud = new File(GV.file_path + "Load Directory/" + File_name + "_Stud" + ".rtf");
                        File File_enrolls = new File(GV.file_path + "Load Directory/" + File_name + "_Enrolls" + ".rtf");
                        File File_Courses = new File(GV.file_path + "Load Directory/" + File_name + "_Courses" + ".rtf");
                        
                        if(File_stud.createNewFile() && File_enrolls.createNewFile() && File_Courses.createNewFile()){
                            System.out.println("New File Created named: " + File_name + ".rtf");
                            try{
                                BufferedWriter out = new BufferedWriter(new FileWriter(File_stud, true));
                                out.write("[STUDENTS]\n" + 
                                          "#STUDENT FULLNAME; STUDENT SEMESTER;STUDENT ID;\n");
                                out.close();
                            }catch(IOException err){
                                err.printStackTrace();
                            }
                            
                            try{
                                BufferedWriter out = new BufferedWriter(new FileWriter(File_enrolls, true));
                                out.write("[ENROLLS]\n" + 
                                          "#STUDENT ID; COURSE ID; STUDENT GRADE;\n");
                                out.close();
                            }catch(IOException err){
                                System.out.println("Something went wrong" + err);
                                err.printStackTrace();
                            }
                            
                            try{
                                BufferedWriter out = new BufferedWriter(new FileWriter(File_Courses, true));
                                out.write("[COURSES]\n" + 
                                          "#COURSE NAME; COURSE SEMESTER; COURSE ID;\n");
                                out.close();
                            }catch(IOException err){
                                err.printStackTrace();
                            }
                            
                        }else{
                            JOptionPane.showMessageDialog(null, "Warning Files Already Exists!" + File_name, "Warning!", 0, GV.warn_icon);
                        }
                    }catch(IOException err){
                        System.out.println("An Error Occured while creating the file.");
                    }
                }else {
                	// Cancel Option
                }
            }
        });
        
        LoadItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String File_name = JOptionPane.showInputDialog(null, "Enter file name: ");
                Load_Data(File_name);
            }
        });
        
        ExitItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        
        // MENU Item Students ACTION LISTENERS
        New_Student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String fn;
                if(GV.fn_global == null)
                    fn = JOptionPane.showInputDialog(null, "Enter File Name: ");
                else
                    fn = GV.fn_global;
                File f = new File(GV.file_path + "Load Directory/" + fn + "_Stud" + ".rtf");
                if(f.exists()){
                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                        String fullname = JOptionPane.showInputDialog(null, "Enter Full Name: ");
                        if(fullname == null){
                            // Cancel Option
                        }else{
                            String stud_Semester = JOptionPane.showInputDialog(null, "Enter Student Semester: ");
                            if(stud_Semester == null){
                                // Cancel Option
                            }else{
                                String stud_ID = JOptionPane.showInputDialog(null, "Enter Student's ID: ");
                                if(stud_ID == null){
                                    // Cancel Option
                                }else{
                                    bw.write(fullname + ";" + stud_Semester + ";" + stud_ID + ";\n");
                                    System.out.println("Data written in file [" + f.getName() + "]:\n" + 
                                           fullname + " | " + stud_Semester + " | " + stud_ID);
                                }
                            }
                        }
                        bw.close();
                    }catch(IOException err){
                        JOptionPane.showMessageDialog(null, "Error, Something went wrong!" , "Error!", 0, GV.err_icon);
                    }
                }else if(fn == null){
                    // Cancel Option
                }
                else
                    JOptionPane.showMessageDialog(null, "Warning, file doesn't exist! ", "Warning!", 0, GV.warn_icon);
                    
            }
        });
        
        Course_Enroll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String stud_ID, Course_ID, stud_G;
                Enroll obj = new Enroll();
                String fn;
                if(GV.fn_global == null)
                    fn = JOptionPane.showInputDialog(null, "Enter file name: ");
                else
                    fn = GV.fn_global;
                
                File f = new File(GV.file_path + "Load Directory/" + fn + "_Enrolls" + ".rtf");
                if(f.exists()){
                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                        stud_ID = JOptionPane.showInputDialog(null, "Enter Student's ID: ");
                        if(stud_ID == null){
                            // Cancel Option
                        }else{
                            obj.set_StudID(stud_ID);
                            Course_ID = JOptionPane.showInputDialog(null, "Enter Course's ID: ");
                            if(Course_ID == null){
                                // Cancel Option
                            }else{
                                stud_G = JOptionPane.showInputDialog(null, "Enter Student's Grade: ");
                                if(!isDouble(stud_G) && Double.parseDouble(stud_G) < 0){
                                    JOptionPane.showMessageDialog(null, "Warning, Please enter a valid Grade!", "Warning!", 0, GV.warn_icon);
                                }else if(stud_G == null){
                                    // Cancel Option
                                }else{
                                    bw.write(stud_ID + ";" + Course_ID + ";" + stud_G + ";\n");
                                    System.out.println("Data written in file [" + f.getName() + "]:\n" + 
                                               stud_ID + " | " + Course_ID + " | " + stud_G);
                                }
                            }

                        }
                        bw.close();
                    }catch(IOException err){
                        JOptionPane.showMessageDialog(null, "Error, Something went wrong!", "Error!", 0, GV.err_icon);
                    }
                }else if(fn == null){
                    // Cancel Option
                }else
                    JOptionPane.showMessageDialog(null, "Warning, file doesn't exist!", "Warning!", 0, GV.warn_icon);
            }
        });
        
        
        // COURSE MENU ITEM ACTION LISTENERS
        New_Course.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String fn;
                if(GV.fn_global == null)
                    fn = JOptionPane.showInputDialog(null, "Enter File name: ");
                else
                    fn = GV.fn_global;
                
                File f = new File(GV.file_path + "Load Directory/" + fn + "_Courses" + ".rtf");
                if(f.exists()){
                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                        String Course_name = JOptionPane.showInputDialog(null, "Enter Course's name: ");
                        if(Course_name == null){
                            // Cancel Option
                        }else{
                            String Course_sem = JOptionPane.showInputDialog(null, "Enter Course's Semester: ");
                            if(Course_sem == null){
                                // Cancel Option
                            }else{
                                String Course_ID = JOptionPane.showInputDialog(null, "Enter Course's ID: ");
                                if(Course_ID == null){
                                    // Cancel Option
                                }else{
                                    bw.write(Course_name + ";" + Course_sem + ";" + Course_ID + ";\n");
                                    System.out.println("Data written in file [" + f.getName() + "]:\n" + 
                                                        Course_name + " | " + Course_sem + " | " + Course_ID);
                                }
                            }
                        }
                        bw.close();
                    }catch(IOException err){
                        JOptionPane.showMessageDialog(null, "Error, Something went wrong!", "Error!", 0, GV.err_icon);
                    }
                }else if(fn == null){
                    // Cancel Option
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, file doesn't exist!", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        Show_Student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!mStudents.isEmpty() && !mEnrolls.isEmpty()){
                    String ID = JOptionPane.showInputDialog(null, "Enter Student ID: ");
                    if(!(ID == null)){
                        String total = "<html><table border=5>" + GV.html_style1 + "List of information of the student [";
                        Double grade = 0.0;
                        boolean pass = false;
                        int c = 0;
                        
                        // First iteration to get the full name of the student.
                        // Inefficient, but prettier setup of the window!
                        for(int i = 0 ; i < mStudents.size(); i++){
                            if(mStudents.get(i).get_ID().equals(ID)){ pass = true;
                                total += mStudents.get(i).get_fullname() + " ID: " + ID + "]</h1>" + GV.htmltail;
                            }
                        }
                        
                        total += GV.htmlhead;
                        
                        if(pass){
                            // Second iteration to get the average grade.
                            
                            for(int i = 0 ; i < mStudents.size(); i++){
                                if(mStudents.get(i).get_ID().equals(ID)){
                                    total += "<tr>";
                                    total += "<td>Student Fullname: " + mStudents.get(i).get_fullname() + "</td>" +
                                             "<td>Student Semester: " + mStudents.get(i).get_sem() + "</td></tr>";
                                    for(int j = 0 ; j < mEnrolls.size(); j++)
                                    {
                                        if(mEnrolls.get(j).get_StudID().equals(ID)){
                                            grade += mEnrolls.get(j).get_grade();
                                            c++;
                                            for(int k = 0; k < mCourses.size(); k++){
                                                if(mEnrolls.get(j).get_CourseID().equals(mCourses.get(k).get_ID())){
                                                    total += "<tr><td>Course name: " + mCourses.get(k).get_name() + "</td>" +
                                                             "<td>Course grade: " + mEnrolls.get(j).get_grade() + "</td></tr>";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            grade /= c;
                            DecimalFormat decimal_reduction = new DecimalFormat("#.000");
                            total += "<tr>Total Average Grade: " + decimal_reduction.format(grade) + "</tr>";
                            total += GV.htmltail;
                            JOptionPane.showMessageDialog(null, total);
                        }else{
                            JOptionPane.showMessageDialog(null, "No Such Student with the ID [" + ID + "] was found!", "Warning!", 0, GV.warn_icon);
                        }
                    }else{
                        // Cancel Option
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, no data loaded into the Data Structure!", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        
        
        Show_Performance_Graph.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(isNotEmpty()) {
            		String ID = JOptionPane.showInputDialog(null, "Enter Student's ID");
            		if(ID != null) {
            			ID_G = ID;
            			Application.launch(Graph_Grades.class, "str");
            		}else {
            			// Cancel Option
            		}
            	}else {
            		JOptionPane.showMessageDialog(null, "Warning, no data loaded into the Data Structure!", "Warning!", 0, GV.warn_icon);
            	}
            	
            }
        });
        
        Remove_Student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!mStudents.isEmpty()){
                    String ID = JOptionPane.showInputDialog(null, "Enter Student's ID: ");
                    boolean passE = false, passS = false;
                    String total = "";
                    // Iteration Reversed to avoid different elements being deleted, or delete only one element, 
                    // while elements get shifted during deletion of multiple elements.
                    
                    for(int i = mEnrolls.size() - 1; i >= 0 ; i--){
                        if(mEnrolls.get(i).get_StudID().equals(ID)){
                            passE = true;
                        }
                            mEnrolls.remove(i);
                    }
                    
                    for(int i = mStudents.size() - 1 ; i >= 0 ; i--){
                        if(mStudents.get(i).get_ID().equals(ID)){
                            passS = true;
                            total += mStudents.get(i).get_fullname();
                            mStudents.remove(i);
                        }
                    }
                    
                    if(passS || passE){
                        JOptionPane.showMessageDialog(null, "Freed student's data from Data Structure. \nStudent name:[" + total + "]");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR inputed", "Warning!", 0, GV.warn_icon);
                }
                
            }
        });
        
        Show_performance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(GV.fn_global == null && !(mStudents.isEmpty() && mEnrolls.isEmpty() && mCourses.isEmpty())){
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR inputed", "Warning!", 0, GV.warn_icon);
                }else{
                    String ID = JOptionPane.showInputDialog(null, "Enter Course ID: ");
                    String total = "<html><table border=5>" + GV.html_style1 + "Students in Course [";
                    boolean pass = false;
                    for(int i = 0; i < mEnrolls.size(); i++){
                        for(int z = 0 ; z < mCourses.size(); z++){
                            if(mCourses.get(z).get_ID().equals(ID) && !pass){ pass = true;
                                total += mCourses.get(z).get_name() + "]:</h1></tr>" + GV.htmltail + GV.htmlhead;
                            }
                        }

                        for(int j = 0 ; j < mStudents.size(); j++){
                            if(mEnrolls.get(i).get_CourseID().equals(ID)){
                                if(mStudents.get(j).get_ID().equals(mEnrolls.get(i).get_StudID())){
                                    total += "<tr>";
                                    total += "<td>Student ID: " + mStudents.get(j).get_ID() + "</td>" +
                                             "<td>Fullname: " + mStudents.get(j).get_fullname() + "</td>" +
                                             "<td>Grade: " + mEnrolls.get(i).get_grade() + "</td></tr>";
                                }
                            }
                        }
                    }
                    
                    if(pass){ // Hardcoded if statement in case if there's no Enrolls in the Course
                        if(total.equals("<html><table border=5>" + "<tr><h1 style=\"font-family:verdana;font-size:110%;color:black;\">Students in Course [")){
                            for(int i = 0; i < mCourses.size(); i++){
                                if(mCourses.get(i).get_ID().equals(ID))
                                    total += mCourses.get(i).get_name() + "]:</h1></tr>" + GV.htmltail + GV.htmlhead;
                            }
                            JOptionPane.showMessageDialog(null, total);
                        }else{
                            total += GV.htmltail;
                            JOptionPane.showMessageDialog(null, total);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Warning Course Doesn't Exist!", "Warning!", 0, GV.warn_icon);
                    }
                }
            }
        });
        
        
        Select_Semester.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String SEM = JOptionPane.showInputDialog(null, "Enter Semester:");
                if(!(SEM == null)){
                    Boolean pass = false;
                    String total = "<html><table border=5>" + "Semester: " + SEM + GV.htmltail + GV.htmlhead;
                    for(int i = 0 ; i < mCourses.size(); i++){
                        if(mCourses.get(i).get_sem().equals(SEM)){ pass = true;
                            total += "<tr><td>" + "Course ID: " + mCourses.get(i).get_ID() + "</td>" +
                                     "<td>" + mCourses.get(i).get_name() + "</td></tr>";
                        }
                    }
                    
                    if(pass){
                        total += GV.htmltail;
                        JOptionPane.showMessageDialog(null, total);
                    }else
                        JOptionPane.showMessageDialog(null, "Warning Semester doesn't exist!", "Warning!", 0, GV.warn_icon);
                }else{
                    // Cancel Option
                }
            }
        });
        
        Remove_Course.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(mCourses.isEmpty())){
                    String ID = JOptionPane.showInputDialog(null, "Enter Course ID: ");
                    String total = "";
                    if(!(ID == null)){
                        boolean passC = false, passE = false;

                        // Reverse iteration to avoid deleting only one element after one iteration.
                        for(int i = mCourses.size() - 1; i >= 0 ; i--){

                            if(mCourses.get(i).get_ID().equals(ID)){
                                passC = true;
                                total += mCourses.get(i).get_name();
                                mCourses.remove(i);
                            }
                        }

                        for(int j = mEnrolls.size() - 1; j >= 0; j--){
                                if(mEnrolls.get(j).get_CourseID().equals(ID)){
                                    passE = true;
                                    mEnrolls.remove(j);
                                }
                        }

                        if(passC || passE){
                            JOptionPane.showMessageDialog(null, "Freed Course's data from Data structure.\nCourse:[" + total + "]");
                        }else{
                            JOptionPane.showMessageDialog(null, "Warning Course doesn't exist!\nIf values were entered please reLoad the file!", "Warning!", 0, GV.warn_icon);
                        }
                    }else{
                        // Cancel Option
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR inputed into the Data Structure.", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        Create_Database.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String db_n = JOptionPane.showInputDialog(null, "Enter Database Name: ");
                if(!(db_n == null)){
                    Create_DataBase(db_n);
                }else{
                    // Cancel Option
                }
            }
        });
        
        Load_Database.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.fn_global != null){
                    JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    int returnValue = choose.showOpenDialog(null);
                    if(returnValue == JFileChooser.APPROVE_OPTION){
                        String Database_name = choose.getSelectedFile().getName();
                        Database_name = Database_name.substring(0, Database_name.length() - 3);
                        if(Database_name != null){  
                        Load_Data(GV.fn_global);
                        GV.DBn_global = Database_name;
                        if(Insert_All(Database_name)){
                           System.out.println("Database [" + Database_name + "] Loaded successfully");
                        }else{
                            // Cancel Option
                        }
                    }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR inputed into the Data Structure.", "Warning!", 0, GV.warn_icon);
                }
            }
        });

        Show_All_Console.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.DBn_global != null){
                    try(Connection conn = connect(GV.DBn_global);
                        Statement stm = conn.createStatement()){
                        String SQL_Students = "SELECT ST_ID, Firstname, Lastname, Semester\n" +
                                              "FROM STUDENTS";
                        
                        String SQL_Courses = "SELECT C_ID, Name, Semester\n" +
                                             "FROM COURSES";
                        
                        String SQL_Enrolls = "SELECT Student_ID, Course_ID, Student_Grade\n" +
                                             "FROM ENROLLS";
                        
                        ResultSet rs = stm.executeQuery(SQL_Students);
                        int line = 1;
                        System.out.println("\n==========================================================================");
                        System.out.println(  "                               Students                                   ");
                        System.out.println(  "==========================================================================");
                        while(rs.next()){
                            String ID = rs.getString("ST_ID");
                            String FN = rs.getString("Firstname");
                            String LN = rs.getString("Lastname");
                            String Sem = rs.getString("Semester");
                            
                            System.out.print("Line:" + line + "|Student ID: " + ID);
                            System.out.print(", Student Firstname: " + FN);
                            System.out.print(", Student Lastname: " + LN);
                            System.out.println(", Semester: " + Sem);
                            line++;
                        }
                        
                        
                        System.out.println("\n==========================================================================");
                        System.out.println(  "                                Courses                                   ");
                        System.out.println(  "==========================================================================");
                        rs = stm.executeQuery(SQL_Courses);
                        line = 1;
                        while(rs.next()){
                            String ID = rs.getString("C_ID");
                            String Name = rs.getString("Name");
                            String Sem = rs.getString("Semester");
                            
                            System.out.print("Line: " + line + "|Course ID: " + ID);
                            System.out.print(", Course Name: " + Name);
                            System.out.println(", Semester: " + Sem);
                            line++;
                        }
                        
                        line = 1;
                        rs = stm.executeQuery(SQL_Enrolls);
                        System.out.println("\n==========================================================================");
                        System.out.println(  "                                 Enrolls                                  ");
                        System.out.println(  "==========================================================================");
                        while(rs.next()){
                            String id_S = rs.getString("Student_ID");
                            String id_C = rs.getString("Course_ID");
                            Float Grade = rs.getFloat("Student_Grade");
                            System.out.print("Line:" + line + "|Student ID: " + id_S);
                            System.out.print(", Course ID: " + id_C);
                            System.out.println(", Student Grade: " + Grade);
                            line++;
                        }
                    }catch(SQLException err){
                        System.out.println(err.getMessage());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning no Database selected", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        Insert_Student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.DBn_global != null){
                    try(Connection conn = connect(GV.DBn_global);
                        PreparedStatement pstmt = conn.prepareStatement(GV.SQL_in_Students)){
                        String ID = JOptionPane.showInputDialog(null, "Enter Student's ID: ");
                        if(ID == null){
                            // Cancel Option
                        }else{
                            String FULLNAME = JOptionPane.showInputDialog(null, "Enter Student's Full Name: ");
                            if(FULLNAME == null){
                                // Cancel Option
                            }else{
                                String SEM = JOptionPane.showInputDialog(null, "Enter Student's Semester: ");
                                if(SEM == null){
                                    // Cancel Option
                                }else{
                                    String FN = "", LN = "";
                                    String[] Str_arr = FULLNAME.split(" ", 2);
                                    int c = 0;
                                    for(String a : Str_arr){
                                        switch(c){
                                                case 0:
                                                    FN = a;
                                                    break;
                                                default:
                                                    LN = a;
                                        }
                                        c++;
                                    }
                                    pstmt.setString(1, ID);
                                    pstmt.setString(2, FN);
                                    pstmt.setString(3, LN);
                                    pstmt.setString(4, SEM);
                                    pstmt.executeUpdate();
                                    System.out.println("Data written in Database [" + GV.DBn_global + "]:\n" + 
                                                        ID + " | " + FN + " | " + LN + " | " + SEM);
                                }
                            }
                        }
                    }catch(SQLException err){
                        System.out.println(err.getMessage());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning no Database selected", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        Insert_Course.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.DBn_global != null){
                    try(Connection conn = connect(GV.DBn_global);
                        PreparedStatement pstmt = conn.prepareStatement(GV.SQL_in_Courses)){
                        String ID = JOptionPane.showInputDialog("Enter Course's ID: ");
                        if(ID == null){
                            // Cancel Option
                        }else{
                            String Name = JOptionPane.showInputDialog("Enter Course's Name: ");
                            if(Name == null){
                                // Cancel Option
                            }else{
                                String SEM = JOptionPane.showInputDialog("Enter Course's Semester: ");
                                if(SEM == null){
                                    // Cancel Option
                                }else{
                                    pstmt.setString(1, ID);
                                    pstmt.setString(2, Name);
                                    pstmt.setString(3, SEM);
                                    pstmt.executeUpdate();
                                    System.out.println("Data written in Database [" + GV.DBn_global + "]:\n" + 
                                                        ID + " | " + Name + " | " + SEM);
                                }
                            }
                        }
                    }catch(SQLException err){
                        JOptionPane.showMessageDialog(null, "Error while trying to get a connection\n" + err.getMessage(), "Error", 0, GV.warn_icon);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning no Database selected", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        Insert_Enroll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.DBn_global != null){
                    try(Connection conn = connect(GV.DBn_global);
                        PreparedStatement pstmt = conn.prepareStatement(GV.SQL_in_Enrolls)){
                        String ID_S = JOptionPane.showInputDialog("Enter Student's ID: ");
                        if(ID_S == null){
                            // Cancel Option
                        }else{
                            String ID_C = JOptionPane.showInputDialog("Enter Course's ID: ");
                            if(ID_C == null){
                                // Cancel Option
                            }else{
                                Double Grade = Double.valueOf(JOptionPane.showInputDialog("Enter Student's Grade: "));
                                if(Grade == null){
                                    // Cancel Option
                                }else{
                                    float f = Grade.floatValue();
                                    pstmt.setString(1, ID_S);
                                    pstmt.setString(2, ID_C);
                                    pstmt.setFloat(3, f);
                                    pstmt.executeUpdate();
                                    
                                    System.out.println("Data written in Database [" + GV.DBn_global + "]:\n" + 
                                                        ID_S + " | " + ID_C + " | " + f);
                                }
                            }
                        }
                    }catch(SQLException err){
                        System.out.println(err.getMessage());
                    }
                }
            }
        });
        
        Clear_Data.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.DBn_global == null){
                    JOptionPane.showMessageDialog(null, "Warning no Database selected", "Warning!", 0, GV.warn_icon);
                }else{
                    if(JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        // YES Option
                        clear_all(GV.DBn_global);
                    }else{
                        // NO Option
                    }
                }
            }
        });
    }
    
    public Connection connect(String file_name){
        String url = "jdbc:sqlite:" + GV.file_path + "Databases\\" + file_name + ".db";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        }catch(SQLException err){
            System.out.println(err.getMessage());
        }
        
        if(conn != null){
            return conn;
        }else{
            JOptionPane.showMessageDialog(null, "Something went wrong when connecting with the Driver", "Error!", 0, GV.err_icon);
            return conn; // If not returned here missing return statement error will show up
        }
    }
    
    public void Load_Data(String File_name){
        mEnrolls.clear();
        mStudents.clear();
        mCourses.clear();
        GV.fn_global = File_name;
        File file_Studs = new File(GV.file_path + "Load Directory/" + File_name + "_Stud" + ".rtf");
        File file_Enrolls = new File(GV.file_path + "Load Directory/" + File_name + "_Enrolls" + ".rtf");
        File file_Courses = new File(GV.file_path + "Load Directory/" + File_name + "_Courses" + ".rtf");
        if(file_Studs.exists() && file_Enrolls.exists() && file_Courses.exists()){
            try{
                System.out.println("Loaded a file: " + File_name);
                String comments[] = new String[3];

                FileReader rs = new FileReader(file_Studs);
                FileReader re = new FileReader(file_Enrolls);
                FileReader rc = new FileReader(file_Courses);

                BufferedReader read_Studs = new BufferedReader(rs);
                BufferedReader read_Enrolls = new BufferedReader(re);
                BufferedReader read_Courses = new BufferedReader(rc);

                String str;
                String[] split_str = new String[3];

                // READ STUDENTS
                read_Studs.readLine();
                comments[0] = read_Studs.readLine();
                while((str = read_Studs.readLine()) != null){
                    Student obj_stud = new Student();
                    split_str = str.split(";");
                    for(int i = 0 ; i < split_str.length; i++){
                        switch(i){
                            case 0:
                                obj_stud.set_fullname(split_str[i]);
                                break;
                            case 1:
                                obj_stud.set_sem(split_str[i]);
                                break;
                            case 2:
                                obj_stud.set_ID(split_str[i]);
                                break;
                        }
                    }
                    mStudents.add(obj_stud);
                }

                // READ ENROLLS
                read_Enrolls.readLine();
                comments[1] = read_Enrolls.readLine();
                while((str = read_Enrolls.readLine()) != null){
                    Enroll obj_Enroll = new Enroll();
                    split_str = str.split(";");
                    for(int i = 0 ; i < split_str.length; i++){
                        switch(i){
                            case 0:
                                obj_Enroll.set_StudID(split_str[i]);
                                break;
                            case 1:
                                obj_Enroll.set_CourseID(split_str[i]);
                                break;
                            case 2:
                                obj_Enroll.set_grade(Double.valueOf(split_str[i]));
                                break;
                        }
                    }
                    mEnrolls.add(obj_Enroll);
                }

                // READ COURSES
                read_Courses.readLine();
                comments[2] = read_Courses.readLine();
                while((str = read_Courses.readLine()) != null){
                    Course obj_Course = new Course();
                    split_str = str.split(";");
                    for(int i = 0 ; i < split_str.length; i++){
                        switch(i){
                            case 0:
                                obj_Course.set_name(split_str[i]);
                                break;
                            case 1:
                                obj_Course.set_sem(split_str[i]);
                                break;
                            case 2:
                                obj_Course.set_ID(split_str[i]);
                                break;
                        }
                    }
                    mCourses.add(obj_Course);
                }
                read_Studs.close();
                read_Enrolls.close();
                read_Courses.close();
                rs.close();
                re.close();
                rc.close();
            }catch(IOException err){
                JOptionPane.showMessageDialog(null, "Warning Something went wrong while opening the file", "Error!", 0, GV.err_icon);
            }
        }else if(File_name != null){
            JOptionPane.showMessageDialog(null, "Warning File Doesn't exist!!" , "Warning!", 0, GV.warn_icon);
        }else{
           // Do Absolutely nothing, just a pass!!
        }
    }
    
    public void Create_DataBase(String db_n){
        try(Connection conn = connect(db_n);
            Statement stmt = conn.createStatement()){
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The Driver's name is " + meta.getDriverName());
                System.out.println("A new database has been created with the name: [" + db_n + "]");
                
                String SQL_Students = "CREATE TABLE IF NOT EXISTS STUDENTS (\n" +
                                      "ST_ID varchar(10) PRIMARY KEY,\n" +
                                      "Firstname varchar(40) NOT NULL,\n" +
                                      "Lastname varchar(40) NOT NULL,\n" +
                                      "Semester varchar(7) NOT NULL\n" +
                                      ");";
                
                String SQL_Courses= "CREATE TABLE IF NOT EXISTS COURSES (\n" +
                                    "C_ID varchar(10) PRIMARY KEY,\n" +
                                    "Name varchar(60) NOT NULL,\n" +
                                    "Semester varchar(7) NOT NULL\n" +
                                    ");";
                
                String SQL_Enrolls = "CREATE TABLE IF NOT EXISTS ENROLLS (\n" +
                                     "Student_ID varchar(10) NOT NULL,\n" +
                                     "Course_ID varchar(10) NOT NULL,\n" +
                                     "Student_Grade real\n" +
                                     ");";
                
                stmt.execute(SQL_Students);
                stmt.execute(SQL_Courses);
                stmt.execute(SQL_Enrolls);
            
        }catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
    
    public void clear_all(String db_n){
        String DELETE = "DELETE FROM STUDENTS;\n" +
                    "DELETE FROM COURSES;\n" +
                    "DELETE FROM ENROLLS;";

        try(Connection conn = connect(db_n);
                Statement stm = conn.createStatement()){
            stm.executeUpdate(DELETE);
        }catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
    
    public boolean Insert_All(String db_n){
        clear_all(db_n);
        boolean pass1 = false, pass2 = false, pass3 = false;
        try(Connection conn = connect(db_n);
            PreparedStatement pstmt = conn.prepareStatement(GV.SQL_in_Students)){
            for(int i = 0 ; i < mStudents.size(); i++){
                pstmt.setString(1, mStudents.get(i).get_ID());
                pstmt.setString(2, mStudents.get(i).get_Firstname());
                pstmt.setString(3, mStudents.get(i).get_Lastname());
                pstmt.setString(4, mStudents.get(i).get_sem());

                pstmt.executeUpdate();
            }
            System.out.println("Table 1 was successfuly populated with data!");
            pass1 = true;
        }catch(SQLException err1){
            System.out.println(err1.getMessage());
        }
        
        // Data Insertion in Table Enrolls
        try(Connection conn = connect(db_n);
                PreparedStatement pstmt = conn.prepareStatement(GV.SQL_in_Enrolls)){
            for(int i = 0 ; i< mEnrolls.size(); i++){
                pstmt.setString(1, mEnrolls.get(i).get_StudID());
                pstmt.setString(2, mEnrolls.get(i).get_CourseID());
                float f = mEnrolls.get(i).get_grade().floatValue();
                pstmt.setFloat(3, f);

                pstmt.executeUpdate();
            }
            System.out.println("Table 2 was successfuly populated with data!");
            pass2 = true;
        }catch(SQLException err2){
            System.out.println(err2.getMessage());
        }
        
        // Data Insertion in Table Courses
        try(Connection conn = connect(db_n);
                PreparedStatement pstmt = conn.prepareStatement(GV.SQL_in_Courses)){
            for(int i = 0;  i < mCourses.size(); i++){
                pstmt.setString(1, mCourses.get(i).get_ID());
                pstmt.setString(2, mCourses.get(i).get_name());
                pstmt.setString(3, mCourses.get(i).get_sem());

                pstmt.executeUpdate();
            }
            System.out.println("Table 3 was successfuly populated with data!");
            pass3 = true;
        }catch(SQLException err3){
            System.out.println(err3.getMessage());
        }
        
        if(pass1 && pass2 && pass3){
            return true;
        }else
            return false;
    }
    
    public static class Graph_Grades extends Application{
    	@Override
    	public void start(Stage stage) {
    		stage.setTitle("Student Performance Graph");
        	final NumberAxis X = new NumberAxis();
        	final CategoryAxis Y = new CategoryAxis();
        	Y.setLabel("Courses");
        	X.setLabel("Grades");
        	final LineChart<Number, String> lineChart = new LineChart<Number,String>(X,Y);
        	lineChart.setTitle("Student Grade Performance");
        	int course_size = 0;
        	for(int i = 0 ; i < mCourses.size(); i++) {
        		for(int j = 0 ; j < mEnrolls.size(); j++) {
        			if(mEnrolls.get(j).get_StudID().equals(ID_G)) {
        				if(mCourses.get(i).get_ID().equals(mEnrolls.get(j).get_CourseID()))
        					course_size++;
        			}
        		}
        	}
        	XYChart.Series<Number, String>[] series = Stream.<XYChart.Series<Number, String>>generate(XYChart.Series::new).limit(course_size).toArray(XYChart.Series[]::new);
        	
        	int c = 0;
        	for(int i = 0 ; i < mEnrolls.size(); i++) {
        		if(mEnrolls.get(i).get_StudID().equals(ID_G)) {
        			for(int j = 0; j <mCourses.size(); j++) {
        				if(mEnrolls.get(i).get_CourseID().equals(mCourses.get(j).get_ID())) {
        					series[c].setName(mCourses.get(j).get_name());
        					series[c].getData().add(new XYChart.Data(mEnrolls.get(i).get_grade(), mCourses.get(j).get_name()));
        				    c++;
        				}
        			}
        		}
        	}
        	
        	Scene scene = new Scene(lineChart, 800, 600);
        	for(int i = 0 ; i < course_size; i++)
        		lineChart.getData().add(series[i]);
        	stage.setScene(scene);
        	stage.show();
        	stage.onCloseRequestProperty();
    	}
    }
}
