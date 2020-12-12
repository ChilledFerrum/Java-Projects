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


@SuppressWarnings("serial")
public class StudentFrame extends JFrame {
    private static ArrayList<Student> mStudents = new ArrayList<Student>();
    private static ArrayList<Course> mCourses = new ArrayList<Course>();
    private static ArrayList<Enroll> mEnrolls = new ArrayList<Enroll>();
    
    
    private JMenu     fileMenu,     StudentMenu,            CourseMenu,        		 	DatabaseMenu;
//  =====================================================================================================
    private JMenuItem NewItem,		New_Student,            New_Course,        		 	Create_Database,
                      LoadItem,     Course_Enroll,          Show_performance,		 	Load_Database,
                      SaveItem,	    Show_Student,			Show_Courses_Grades_Graph,	Show_All_Console,
                      ExitItem,		Show_Performance_Graph, Select_Semester,		 	Insert_Student,
                      				Remove_Student,			Remove_Course, 			 	Insert_Enroll,
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
    
    
    // VALIDITY CHECK functions
    public static boolean isDouble(String d){
        try{
            @SuppressWarnings("unused")
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
        
        if(count_dot > 1)
        	return false;
        
        return true;
    }
    
    public static boolean isNotEmpty() {
    	if(!mStudents.isEmpty() && !mEnrolls.isEmpty() && !mCourses.isEmpty()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public static class Pair{
    	private final Boolean pass;
    	private final String err_type;
    	
    	public Pair(Boolean p, String err) {
    		pass = p; err_type = err;
    	}
    	public Boolean get_key() {
    		return pass;
    	}
    	public String get_reason() {
    		return err_type;
    	}
    }
    public static Pair Enrolls_Inputs_Validity_check(String Stud_ID, String Course_ID, String Grade){
    	Boolean pass = true;
    	String err_type = "";
    	int counter = 0;
    	
    	for(int i = 0 ; i < Stud_ID.length(); i++)
    		counter++;
    	if(counter > 10) {
    		pass = false;
    		err_type += "Student ID is too long\n";
    	}
    	
    	counter = 0;
    	for(int i = 0 ; i < Course_ID.length(); i++)
    		counter++;
    	if(counter > 10) {
    		pass = false;
    		err_type += "Course ID is too long\n";
    	}
    	
    	counter = 0;
    	for(int i = 0 ; i < Grade.length(); i++)
    		counter++;
    	if(counter > 5) {
    		pass = false;
    		err_type += "Student Grade is too long\n";
    	}
    	if(isDouble(Grade)) {
    		Double num = Double.parseDouble(Grade);
    		if(num < 0.0) {
    			pass = false;
    			err_type += "Grade cannot be negative\n";
    		}
    	}
    	Pair RValue = new Pair(pass, err_type);
    	return RValue;
    }
    public static Pair Courses_Inputs_Validity_check(String CN, String SEM, String ID) {
    	Boolean pass = true;
    	String err_type = "";
    	int counter = 0;
    	for(int i = 0 ;i < CN.length(); i++)
    		counter++;
    	if(counter > 60) {
    		pass = false;
    		err_type += "Course Name is too long\n";
    	}
    	
    	counter = 0;
    	for(int i = 0 ; i < SEM.length(); i++)
    		counter++;
    	if(counter > 7) {
    		pass = false;
    		err_type += "Semester is too long\n";
    	}
    	
    	
    	counter = 0;
    	for(int i = 0 ; i < ID.length(); i++)
    		counter++;
    	if(counter > 10) {
    		pass = false;
    		err_type = "Course ID is too long\n";
    	}
    	
    	for(int i = 0; i < mCourses.size(); i++) {
    		if(mCourses.get(i).get_ID().equals(ID)) {
    			pass = false;
    			err_type += "Course Already exists in the Data Structure [COURSES]\n";
    		}
    	}
    	
    	Pair RValue = new Pair(pass, err_type);
    	return RValue;
    }
    
    
    
    public static  Pair Students_Inputs_Validity_check(String FN, String SEM, String ID) {
    	Boolean pass = true;
    	String err_type = "";
    	int counter = 0;
    	for(int i = 0; i < FN.length(); i++)
    		counter++;
    	if(counter > 80) {
    		pass = false;
    		err_type += "Student Fullname is too long\n";
    	}
    	
    	counter=0;
    	
    	for(int i = 0; i < SEM.length(); i++)
    		counter++;
    	
    	if(counter > 7) {
    		pass = false;
    		err_type += "Student Semester is too long\n";
    	}
    	
    	counter = 0;
    	
    	for(int i = 0; i < ID.length(); i++)
    		counter++;
    	if(counter > 10) {
    		pass = false;
    		err_type += "Student ID is too long\n";
    	}
    	
    	for(int i = 0; i < mStudents.size(); i++) {
    		if(mStudents.get(i).get_fullname().equals(FN) || mStudents.get(i).get_ID().equals(ID)) {
    			pass = false;
    			err_type += "Student Already Exists in the Data Structure [STUDENTS]\n";
    		}
    	}
    	Pair RValue = new Pair(pass, err_type);
    	return RValue;
    }
    
    public void makeMenu(){
        MenuBar = new JMenuBar();
        setJMenuBar(MenuBar);
        
        // FILE COLUMN
        fileMenu = new JMenu("Files");
        
        NewItem = new JMenuItem("Create New File",
				 new ImageIcon(GV.file_path + "Styling/MenuItem Icons/NewItem.png"));
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
        Show_Performance_Graph = new JMenuItem("Show Performance Graph",
				new ImageIcon(GV.file_path + "Styling/MenuItem Icons/Show_Performance_Graph.png"));
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
        Show_Courses_Grades_Graph = new JMenuItem("Show Course's Grades Graph",
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
        fileMenu.add(NewItem);
        fileMenu.add(SaveItem);
        fileMenu.add(LoadItem);
        fileMenu.add(ExitItem);
        
        // STUDENT OBJECT ADDING
        StudentMenu.add(New_Student);
        StudentMenu.add(Course_Enroll);
        StudentMenu.add(Show_Performance_Graph); // Done [Problematic, Unable to run more than once]
        StudentMenu.add(Show_Student);
        StudentMenu.add(Remove_Student); 
        
        //COURSE OBJECT ADDING
        CourseMenu.add(New_Course);
        CourseMenu.add(Show_performance);
        CourseMenu.add(Show_Courses_Grades_Graph);
        CourseMenu.add(Select_Semester);
        CourseMenu.add(Remove_Course);
        
        // DATABASE OBJECT ADDING
        DatabaseMenu.add(Create_Database);
        DatabaseMenu.add(Load_Database);
        DatabaseMenu.add(Show_All_Console);
        DatabaseMenu.add(Insert_Student);
        DatabaseMenu.add(Insert_Enroll);
        DatabaseMenu.add(Insert_Course);
        DatabaseMenu.add(Clear_Data);
        
        MenuBar.add(fileMenu);
        MenuBar.add(StudentMenu);
        MenuBar.add(CourseMenu);
        MenuBar.add(DatabaseMenu);
        
        ImageIcon background = new ImageIcon(GV.file_path + "Styling/Background_Marbles.png");
        JLabel imageLabel = new JLabel(background);
        
        add(imageLabel);
    }
    
    @SuppressWarnings("exports")
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
    
    public void clear_files() {
        int start = 1;
        int stud_numlines = countlines_students(GV.fn_global);
        int course_numlines = countlines_courses(GV.fn_global);
        int Enrolls_numlines = countlines_enrolls(GV.fn_global);
        String Students = GV.file_path + "Load Directory/" + GV.fn_global + "_Stud" + ".rtf";
        String Courses = GV.file_path + "Load Directory/" + GV.fn_global + "_Courses" + ".rtf";
        String Enrolls = GV.file_path + "Load Directory/" + GV.fn_global + "_Enrolls" + ".rtf";
        
        // Student Clear
        try {
        	BufferedReader br = new BufferedReader(new FileReader(Students));
        	StringBuffer sb = new StringBuffer("");
        	int linenumber = 1;
        	String line;
        	while((line = br.readLine()) != null) {
        		if(linenumber < start || linenumber >= start + stud_numlines)
        			sb.append(line + "\n");
        		linenumber++;
        	}
        	
        	br.close();
        	FileWriter fw = new FileWriter(new File(Students));
        	fw.write(sb.toString());
        	fw.close();
        }catch(IOException err) { 
        	JOptionPane.showMessageDialog(null, "WARNING, File STUDENTS is OPEN!", "Warning!", 0, GV.warn_icon);
        	return;
        }
        
        
        // Courses Clear
        try {
        	BufferedReader br = new BufferedReader(new FileReader(Courses));
        	StringBuffer sb = new StringBuffer("");
        	int linenumber = 1;
        	String line;
        	while((line = br.readLine()) != null) {
        		if(linenumber < start || linenumber >= start + course_numlines)
        			sb.append(line + "\n");
        		linenumber++;
        	}
        	br.close();
        	FileWriter fw = new FileWriter(new File(Courses));
        	fw.write(sb.toString());
        	fw.close();
        }catch(IOException err) {
        	JOptionPane.showMessageDialog(null, "WARNING, File COURSES is OPEN!", "Warning!", 0, GV.warn_icon);
        	return;
        }
        
        // Enrolls Clear
        try {
        	BufferedReader br = new BufferedReader(new FileReader(Enrolls));
        	StringBuffer sb = new StringBuffer("");
        	int linenumber = 1;
        	String line;
        	while((line = br.readLine()) != null) {
        		if(linenumber < start || linenumber >= start + Enrolls_numlines)
        			sb.append(line + "\n");
        		linenumber++;
        	}
        	br.close();
        	FileWriter fw = new FileWriter(new File(Enrolls));
        	fw.write(sb.toString());
        	fw.close();
        }catch(IOException err) {
        	JOptionPane.showMessageDialog(null, "WARNING, File ENROLLS is OPEN!", "Warning!", 0, GV.warn_icon);
        	return;
        }
    }
    
    public int countlines_enrolls(String file_name) {
    	int lines = 0;
    	try {
    		BufferedReader r = new BufferedReader(new FileReader(GV.file_path + "Load Directory/" + file_name + "_Enrolls" + ".rtf"));
    		while(r.readLine() != null) lines++;
    		
    		r.close();
    	}catch(IOException err){
    		err.printStackTrace();
    	}
    	return lines;
    }
    
    public int countlines_students(String file_name) {
    	int lines = 0;
    	try {
    		BufferedReader r = new BufferedReader(new FileReader(GV.file_path + "Load Directory/" + file_name + "_Stud" + ".rtf"));
    		while(r.readLine() != null) lines++;
    		
    		r.close();
    	}catch(IOException err){
    		err.printStackTrace();
    	}
    	return lines;
    }
    
    public int countlines_courses(String file_name) {
    	int lines = 0;
    	try {
    		BufferedReader r = new BufferedReader(new FileReader(GV.file_path + "Load Directory/" + file_name + "_Courses" + ".rtf"));
    		while(r.readLine() != null) lines++;
    		
    		r.close();
    	}catch(IOException err){
    		err.printStackTrace();
    	}
    	return lines;
    }
    
    public Boolean Load_Data(String File_name){
        mEnrolls.clear();
        mStudents.clear();
        mCourses.clear();
        GV.fn_global = File_name;
        File file_Studs = new File(GV.file_path + "Load Directory/" + File_name + "_Stud" + ".rtf");
        File file_Enrolls = new File(GV.file_path + "Load Directory/" + File_name + "_Enrolls" + ".rtf");
        File file_Courses = new File(GV.file_path + "Load Directory/" + File_name + "_Courses" + ".rtf");
        if(file_Studs.exists() && file_Enrolls.exists() && file_Courses.exists()){
            try{
                String comments[] = new String[3];

                FileReader rs = new FileReader(file_Studs);
                FileReader rc = new FileReader(file_Courses);
                FileReader re = new FileReader(file_Enrolls);
                
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
                return true;
            }catch(IOException err){
                JOptionPane.showMessageDialog(null, "Warning Something went wrong while opening the file", "Error!", 0, GV.err_icon);
                return false;
            }
        }else if(File_name != null){
            JOptionPane.showMessageDialog(null, "Warning File Doesn't exist!!" , "Warning!", 0, GV.warn_icon);
            return false;
        }else{
           // Do Absolutely nothing, just a pass!!
        	return false;
        }
    }
    
    
    // GRAPHS
    public static class Graph_Grades extends Application{
    	@SuppressWarnings({ "exports", "unchecked", "rawtypes" })
		@Override
    	public void start(Stage stage) {
    		String Student_name = null;
    		for(int i = 0 ; i < mStudents.size(); i++) {
    			for(int j = 0 ; j < mEnrolls.size(); j++) {
    				if(mStudents.get(i).get_ID().equals(ID_G)) {
    					Student_name = mStudents.get(i).get_fullname();
    				}
    			}
    		}
    		stage.setTitle("Student [" + Student_name + "'s] Performance Graph");
        	final NumberAxis X = new NumberAxis();
        	final CategoryAxis Y = new CategoryAxis();
        	Y.setLabel("Courses");
        	X.setLabel("Grades");
        	final LineChart<Number, String> lineChart = new LineChart<Number,String>(X,Y);
        	lineChart.setTitle("Student [" + Student_name + "'s] Performance Graph");
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
        	ID_G = null;
    	}
    }
    
    public static class Graph_Course_grades extends Application{
    	@SuppressWarnings({ "exports", "unchecked", "rawtypes" })
		@Override
    	public void start(Stage stage) {
    		String Course_name = null;
    		for(int i = 0 ; i < mCourses.size(); i++) {
    			for(int j = 0 ; j < mEnrolls.size(); j++) {
    				if(mCourses.get(i).get_ID().equals(mEnrolls.get(j).get_CourseID())) {
    					Course_name = mCourses.get(i).get_name();
    				}
    			}
    		}
    		stage.setTitle("Student Grades of the Course [" + Course_name + "]");
    		
    		final NumberAxis X = new NumberAxis();
    		final CategoryAxis Y = new CategoryAxis();
    		
    		X.setLabel("Grades");
    		Y.setLabel("Students");
    		
    		final LineChart<Number, String> lineChart = new LineChart<Number, String>(X,Y);
    		
    		lineChart.setTitle("Student Grades of the Course [" + Course_name + "]");
    		
    		int Student_size = 0;
    		for(int i = 0 ; i < mEnrolls.size(); i++) {
    			for(int j = 0 ; j < mCourses.size(); j++) {
    				if(mCourses.get(j).get_ID().equals(mEnrolls.get(i).get_CourseID())) {
    					for(int x = 0 ; x < mStudents.size(); x++) {
    						if(mEnrolls.get(i).get_StudID().equals(mStudents.get(x).get_ID()))
    	    					Student_size++;
    					}
    				}
    			}
    		}
    		
    		XYChart.Series<Number, String>[] series = Stream.<XYChart.Series<Number, String>>generate(XYChart.Series::new).limit(Student_size).toArray(XYChart.Series[]::new);
    		
    		
    		int c = 0;
    		for(int i = 0 ; i < mEnrolls.size(); i++) {
    			for(int j = 0 ; j < mCourses.size(); j++) {
    				if(mCourses.get(j).get_ID().equals(ID_G)) {
    					if(mCourses.get(j).get_ID().equals(mEnrolls.get(i).get_CourseID())) {
    						String stud_name = null;
    						for(int x = 0 ; x < mStudents.size(); x++) {
    							if(mEnrolls.get(i).get_StudID().equals(mStudents.get(x).get_ID())) {
    								stud_name = mStudents.get(x).get_fullname();
    							}
    						}
    						series[c].getData().add(new XYChart.Data(mEnrolls.get(i).get_grade(), stud_name));
    						c++;
    					}
    				}
    			}
    		}
    		
    		Scene scene = new Scene(lineChart, 800, 600);
    		for(int i = 0 ; i < Student_size; i++)
    			lineChart.getData().add(series[i]);
    		stage.setScene(scene);
    		stage.show();
    	}
    }
    
    
    public void ActionListeners(){
    	
        // FILE MENU ITEM ACTION LISTENERS
        NewItem.addActionListener(new ActionListener(){
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
                if(Load_Data(File_name))
                	System.out.println("Loaded a file: " + File_name);
                
            }
        });
        
        SaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(GV.fn_global != null) {
			        String Students = GV.file_path + "Load Directory/" + GV.fn_global + "_Stud" + ".rtf";
			        String Courses = GV.file_path + "Load Directory/" + GV.fn_global + "_Courses" + ".rtf";
			        String Enrolls = GV.file_path + "Load Directory/" + GV.fn_global + "_Enrolls" + ".rtf";
					clear_files();
					
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(Students));
			            out.write("[STUDENTS]\n" + 
			                      "#STUDENT FULLNAME; STUDENT SEMESTER;STUDENT ID;\n");
						for(int i = 0 ; i < mStudents.size(); i++) {
							if(!mStudents.get(i).get_fullname().equals("") && !mStudents.get(i).get_ID().equals("") && !mStudents.get(i).get_sem().equals(""))
								out.write(mStudents.get(i).get_fullname() + ";" + mStudents.get(i).get_sem() + ";" + mStudents.get(i).get_ID() + ";\n");
						}

						out.close();
					}catch(IOException err) {
						err.printStackTrace();
					}
					
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(Courses));
			            out.write("[COURSES]\n" + 
			                      "#COURSE NAME; COURSE SEMESTER; COURSE ID;\n");
						for(int i = 0 ; i < mCourses.size(); i++) {
							if(!mCourses.get(i).get_ID().equals("") && !mCourses.get(i).get_name().equals("") && !mCourses.get(i).get_sem().equals(""))
								out.write(mCourses.get(i).get_name() + ";" + mCourses.get(i).get_sem() + ";" + mCourses.get(i).get_ID() + ";\n");
						}
						
						out.close();
					}catch(IOException err) {
						err.printStackTrace();
					}
					
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(Enrolls));
			            out.write("[ENROLLS]\n" + 
			                      "#STUDENT ID; COURSE ID; STUDENT GRADE;\n");
						for(int i = 0 ; i < mEnrolls.size(); i++)
							if(!mEnrolls.get(i).get_CourseID().equals("") && !mEnrolls.get(i).get_StudID().equals("") && !mEnrolls.get(i).get_grade().equals(0.0))
								out.write(mEnrolls.get(i).get_StudID() + ";" + mEnrolls.get(i).get_CourseID() + ";" + mEnrolls.get(i).get_grade()+ ";\n");
						
						out.close();
					}catch(IOException err) {
						err.printStackTrace();
					}
					Load_Data(GV.fn_global);
				}else {
					JOptionPane.showMessageDialog(null, "Warning File hasn't been loaded yet", "Warning!", 0, GV.warn_icon);
				}
			}});
        
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
            	if(GV.fn_global != null) {
            		JTextField FN = new JTextField();
            		JTextField SEM = new JTextField();
            		JTextField ID = new JTextField();
            		Object[] inputs = {
            				"Enter Student Full Name: ", FN,
            				"Enter Student Semester: ", SEM,
            				"Enter Student ID: ", ID
            		};
            		int option = JOptionPane.showConfirmDialog(null, inputs, "Student Information", JOptionPane.OK_CANCEL_OPTION);
            		if(option == JOptionPane.OK_OPTION) {
            			// 3 Way Check method for robust data inputing
            			if(FN.getText().isEmpty() && SEM.getText().isEmpty() && ID.getText().isEmpty()) {
            				JOptionPane.showMessageDialog(null, "Warning, Please Enter VALID inputs!", "Warning!", 0, GV.warn_icon);
            			}else {
            				Pair Check = Students_Inputs_Validity_check(FN.getText(), SEM.getText(), ID.getText());
            				if(!Check.get_key()) {
            					JOptionPane.showMessageDialog(null, Check.get_reason(), "Warning!", 0, GV.warn_icon);
            				}else {
            					mStudents.add(new Student(FN.getText(), SEM.getText(), ID.getText()));
                                System.out.println("Data added in the Data Structure [STUDENTS]:\n" + 
          				   			   "Student Fullname: " + FN.getText() + "  |Student Semester: " + SEM.getText() + "  |S: " + ID.getText() + "\n\n");
            				}
            				
            			}
            		}
            	}else
            		JOptionPane.showMessageDialog(null, "Warning File hasn't been loaded yet!", "Warning!", 0, GV.warn_icon);
            }
        });
        
        Course_Enroll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(GV.fn_global != null) {
                    JTextField Stud_ID = new JTextField();
                    JTextField Course_ID = new JTextField();
                    JTextField Grade = new JTextField();
                    Object[] inputs = {
                    	"Enter Student ID: ", Stud_ID, 
                    	"Enter Course ID: ", Course_ID,
                    	"Enter Student Grade: ", Grade
                    };
                	int option = JOptionPane.showConfirmDialog(null, inputs, "Enroll Information", JOptionPane.OK_CANCEL_OPTION);
                	if(option == JOptionPane.OK_OPTION) {
                		if(Stud_ID.getText().isEmpty() && Course_ID.getText().isEmpty() && Grade.getText().isEmpty()) {
                			JOptionPane.showMessageDialog(null, "Warning, Please Enter VALID inputs!", "Warning!", 0, GV.warn_icon);
                		}else {
                			Pair Check = Enrolls_Inputs_Validity_check(Stud_ID.getText(), Course_ID.getText(), Grade.getText());
                			if(!Check.get_key()) {
                				JOptionPane.showMessageDialog(null, Check.get_reason(), "Warning!", 0, GV.warn_icon);
                			}else {
                    			mEnrolls.add(new Enroll(Stud_ID.getText(), Course_ID.getText(), Double.parseDouble(Grade.getText())));
                                System.out.println("Data added in the Data Structure [ENROLLS]:\n" + 
                     				   			   "Student ID: " + Stud_ID.getText() + "  |Course ID: " + Course_ID.getText() + "  |Grade: " + Grade.getText() + "\n\n");
                			}
                		}
                	}
                }else 
                	JOptionPane.showMessageDialog(null, "Warning File hasn't been loaded yet!", "Warning!", 0, GV.warn_icon);
            }
        });
        
        
        Show_Student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(GV.fn_global != null && isNotEmpty()){
                    String ID = JOptionPane.showInputDialog(null, "Enter Student ID: ");
                    if(ID != null){
                        String total = "<html><table border=5>" + GV.html_style1 + "List of information of the student [";
                        Double grade = 0.0;
                        boolean pass = false;
                        int c = 0;
                        
                        // First iteration to get the full name of the student and Check if it exists
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
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR added in the Data Structure!", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        Remove_Student.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(GV.fn_global != null && !mStudents.isEmpty()){
                    String ID = JOptionPane.showInputDialog(null, "Enter Student's ID: ");
                    if(ID != null) {
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
                    }else {
                    	// CANCEL Option
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR added in the Data Structure!", "Warning!", 0, GV.warn_icon);
                }
                
            }
        });
        
        
        
        // COURSE MENU ITEM ACTION LISTENERS
        New_Course.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	if(GV.fn_global != null) {
            		JTextField CN = new JTextField();
            		JTextField SEM = new JTextField();
            		JTextField ID = new JTextField();
            		Object[] inputs = {
            			"Enter Course Name: ", CN,
            			"Enter Course Semester: ", SEM,
            			"Enter Course ID: ", ID
            		};
            		int option = JOptionPane.showConfirmDialog(null, inputs, "Course Information", JOptionPane.OK_CANCEL_OPTION);
            		if(option == JOptionPane.OK_OPTION) {
            			if(CN.getText().isEmpty() && SEM.getText().isEmpty() && ID.getText().isEmpty()) {
            				JOptionPane.showMessageDialog(null, "Warning, Please Enter VALID inputs!", "Warning!", 0, GV.warn_icon);
            			}else {
            				Pair Check = Courses_Inputs_Validity_check(CN.getText(), SEM.getText(), ID.getText());
            				if(!Check.get_key()) {
            					JOptionPane.showMessageDialog(null, Check.get_reason(), "Warning!", 0, GV.warn_icon);
            				}else {
            					mCourses.add(new Course(CN.getText(), SEM.getText(), ID.getText()));
                                System.out.println("Data added in the Data Structure [COURSES]:\n" + 
          				   			   "Course Name: " + CN.getText() + "  |Course Semester: " + SEM.getText() + "  |Course ID: " + ID.getText() + "\n\n");
            				}
            			}
            		}
            	}else
            		JOptionPane.showMessageDialog(null, "Warning File hasn't been loaded yet!", "Warning!", 0, GV.warn_icon);
            }
        });
        
        Show_performance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	if(GV.fn_global != null && isNotEmpty()) {
                    String ID = JOptionPane.showInputDialog(null, "Enter Course ID: ");
                    if(ID != null) {
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
                    }else {
                    	// CANCEL Option
                    }
            	}else {
            		JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR added in the Data Structure!", "Warning!", 0, GV.warn_icon);
            	}
            }
        });
        
        Show_Performance_Graph.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(GV.fn_global != null && isNotEmpty()) {
            		String ID = JOptionPane.showInputDialog(null, "Enter Student's ID");
            		if(ID != null) {
            			ID_G = ID;
            			try {
            				Application.launch(Graph_Grades.class, "str");
            			}catch(Exception ex) {
            				JOptionPane.showMessageDialog(null, "Warning, Can only launch the Graph at least once. Please Restart the Application!", "Warning!", 0, GV.err_icon);
            			}
            		}else{
            			// CANCEL Option
            		}
            	}else {
            		JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR added in the Data Structure!", "Warning!", 0, GV.warn_icon);
            	}
            }
        });
        
        Show_Courses_Grades_Graph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if(GV.fn_global != null && isNotEmpty()) {
					String ID = JOptionPane.showInputDialog(null, "Enter Course's ID");
					if(ID != null) {
						ID_G = ID;
						if(isNotEmpty()) {
							Application.launch(Graph_Course_grades.class, "str");
						}else {
							JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR inputed", "Warning!", 0, GV.warn_icon);
						}
					}else {
						// Cancel Option
					}
				}else {
					JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR added in the Data Structure!", "Warning!", 0, GV.warn_icon);
				}
			}
        });
        
        Select_Semester.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(GV.fn_global != null) {
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
            	}else {
            		JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR added in the Data Structure!", "Warning!", 0, GV.warn_icon);
            	}
            }
        });
        
        Remove_Course.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GV.fn_global != null && !mCourses.isEmpty()){
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
                            JOptionPane.showMessageDialog(null, "Warning Course doesn't exist!\nIf values were entered please reload the file!", "Warning!", 0, GV.warn_icon);
                        }
                    }else{
                        // Cancel Option
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Warning, Data has yet to be loaded OR inputed into the Data Structure.", "Warning!", 0, GV.warn_icon);
                }
            }
        });
        
        
        
        // Database Menu Item ACTION LISTENERS
        Create_Database.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String db_n = JOptionPane.showInputDialog(null, "Enter Database Name: ");
                if(!(db_n == null)){
                	GV.DBn_global = db_n;
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
                        System.out.println(  "                               Courses                                   ");
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
                        System.out.println(  "                               Enrolls                                  ");
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
                }else {
                	JOptionPane.showMessageDialog(null, "Warning no Database selected", "Warning!", 0, GV.warn_icon);
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
}
