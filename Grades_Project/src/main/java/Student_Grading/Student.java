package Student_Grading;

public class Student{
    private String Firstname, Lastname;
    private String Stud_sem;
    private String Stud_ID;
    
    // DEFAULT Constructor
    public Student(){
        Firstname = "";
        Lastname = "";
        Stud_ID = "";
        Stud_sem = "";
    }
    
    // BASE Constructor
    public Student(String FN, String LN, String sem, String ID){
        Firstname = FN;
        Lastname = LN;
        Stud_ID = ID;
        Stud_sem = sem;
    }
    
    // SECONDARY Constructor
    public Student(String FullN, String sem, String ID){
        String[] Str_arr = FullN.split(" ", 2);
        int c =0;
        for(String a : Str_arr){
            switch(c){
                case 0:
                    Firstname = a;
                    break;
                default:
                    Lastname = a;
                    break;
            }
            c++;
        }
        Stud_ID = ID;
        Stud_sem = sem;
    }
   
    // FULLNAME GETTER & SETTER
    public void set_fullname(String FullN){
        String[] Str_arr = FullN.split(" ", 2);
        int c = 0;
        for(String a : Str_arr){
            switch(c){
                    case 0:
                        Firstname = a;
                        break;
                    default:
                        Lastname = a;
            }
            c++;
        }
    }
    public String get_fullname(){
        return Firstname + " " + Lastname;
    }
    
    
    //  GETTERS & SETTERS
    public String get_Firstname(){
        return Firstname;
    }
    public String get_Lastname(){
        return Lastname;
    }
    
    
    public void set_ID(String ID){
        Stud_ID = ID;
    }
    public String get_ID(){
        return Stud_ID;
    }
    
    
    public void set_sem(String sem){
        Stud_sem = sem;
    }
    public String get_sem() {
        return Stud_sem;
    }
    
    
    // RETURN ALL INFO
    public String toString(){
        return "FullName: " + this.get_fullname() + "\nID: " + this.get_ID() + "\nSemester: " + this.get_sem();
    }
}
