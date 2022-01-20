package maya2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Maya2 {

    //MAIN FUNCTION
    public static void main(String args[]) throws Exception {
        Connection con = getConnection();
        PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS member (name VARCHAR(255),id VARCHAR(255),password VARCHAR(255),type INT, Mav VARCHAR(255), MUET VARCHAR(255), programme VARCHAR(255), email VARCHAR(255), PRIMARY KEY (id,type));");
        create.executeUpdate();
        int choice = 0;
        while(choice == 0){
            while(choice == 0){
                System.out.println("\n\n-----MAYA 2.0-----");
                System.out.println("Welcome to MAYA2.0");
                System.out.print("(1) Login\n(2) Create a new account\n(3) Exit\n>");
                choice = testChoice();
            }
            switch(choice){
                case 1:
                    login();
                    choice = 0;
                    break;
                case 2:
                    createAcc();
                    choice = 0;
                    break;
                case 3:
                    choice = -1;
                    break;
                default:
                    System.out.println("Wrong command! Please enter the correct command!");
                    choice = 0;
                    break;
            }
        }
        
    }
    
    @SuppressWarnings("resource")
    public static int testChoice(){
        Scanner s = new Scanner(System.in);
        int choice;
        try{
            choice = s.nextInt();
        }catch(Exception e){
            choice = 0;
            System.out.println("Please enter the correct command! Try again!");
        }
        return choice;
    }
    
    //host connection with mysql server
    public static Connection getConnection() throws Exception{
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/maya";
            String username = "root";
            String password = "1118";
            Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(url,username,password);
            //System.out.println("Connected");
            return conn;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    //user account authorization
    @SuppressWarnings({ "resource", "unused" })
	public static void createAcc() throws Exception{
        try{
            Connection con = getConnection();
            Scanner s = new Scanner(System.in);
            int choice = 0, got = 0;
            String name = null, id = null, password = null, mav=null, muet=null, cpassword=null, programme = null, email = null;
                        
            System.out.println("\nHello! Welcome to UM!");
            
            //INSERT IDENTITY
            int type = 0;
            while(type == 0){
                System.out.print("Please enter (1) for student or (2) for staff: ");
                type = testChoice();
                if((type != 1)&&(type != 2)){
                    type = 0;
                    System.out.println("Please just enter (1) and (2)!");
                }
            }
            
            //INSERT ID
            System.out.print("Please enter your ID: ");
            id = s.nextLine();
            
            //CHECK EXISTENCE OF ID
            PreparedStatement statement = con.prepareStatement("SELECT COUNT(id) AS got FROM member WHERE id = '"+id+"' AND type = "+type);
            ResultSet exist = statement.executeQuery();
            while(exist.next()){
                got = exist.getInt("got");
            }
            if(got == 1){
                System.out.println("\nSorry, your ID is already occupied in Maya!");
                return;
            }
            
            //ENTER PASSWORD NAME MAV AND MUET
            while(true){
                System.out.print("Please enter your password: ");
                password = s.nextLine();
                System.out.print("Please comfirm your password: ");
                cpassword = s.nextLine();
                if((password.equals(cpassword))==false){
                    System.out.println("Those passwords didn’t match. Try again.");
                }else{
                    break;
                }
            }
            if (type==1) {
                System.out.print("Please enter your siswamail: ");
            }
            else if(type==2){
                System.out.print("Please enter your UMmail: ");
            }
            email = s.nextLine();
            System.out.print("Please enter your name: ");
            name = s.nextLine();
            System.out.print("Please enter your faculty: ");
            mav= s.nextLine();
            if (type == 1) {
                if ((mav.equalsIgnoreCase("fsktm"))||(mav.equalsIgnoreCase("fcsit"))||(mav.equalsIgnoreCase("fakulti sains komputer dan teknologi maklumat"))||(mav.equalsIgnoreCase("faculty computer science and information technology"))) {
                    System.out.print("Please enter your department(major): \n(SE)for Software Engineering\n(AI)for Artificial Intelligence\n(IS)for Information Systems\n(CSN)for Computer System And Network\n(DS)for Data Science\n(MMC)for Multimedia Computing\n>");
                    programme = s.nextLine();
                }
                else{
                	 System.out.print("Please enter your department(major):");
                    programme = s.nextLine();
                }
                System.out.print("Please enter your Muet Band: ");
                muet= s.nextLine();
            }
            else{
                muet = null;
                programme = null;
            }
            
            
            //Confirmation of creating account
            while(true){
                System.out.print("\nAre you sure to create account with:\nID: "+id+"\nEmail: "+email+"\nName: "+name+"\nFaculty: "+mav+"\n");
                if(type==1){
                    System.out.print("Muet level: "+muet+"\nProgramme: "+programme+"\n");
                }
                System.out.print("(Y/N) >");
                String confirm = s.nextLine();
                if(confirm.equalsIgnoreCase("y")){
                    break;
                }
                else if(confirm.equalsIgnoreCase("n")){
                    System.out.println("Creating account unsuccessfully!");
                    return;
                }
                else{
                    System.out.println("Invalid command! Please enter the correct command!");
                }
            }
                        
            //INSERT INTO member TABLE
            statement = con.prepareStatement("INSERT INTO member VALUES ('"+name+"', '"+id+"', '"+password+"', '"+type+"', '"+mav+"', '"+muet+"', '"+programme+"', '"+email+"')");
            statement.executeUpdate();
            System.out.println("\nHello "+name+"! Your account is created successfully!");
            
            //CREATE student TABLE for each student
            if(type == 1){
                PreparedStatement create = con.prepareStatement("CREATE TABLE student"+id+"(number INT NOT NULL AUTO_INCREMENT, ModuleCode VARCHAR(255), ModuleName VARCHAR(255), Activity VARCHAR(255), Tutor VARCHAR(255), Occurrence INT, credithour INT, Week VARCHAR(255), TIME1 INT, TIME2 INT, TIME3 INT, PRIMARY KEY (number));");
                create.executeUpdate();
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @SuppressWarnings("resource")
	public static void login() throws Exception{
        Connection con = getConnection();
        Scanner s = new Scanner(System.in);
        int choice = 0;
        String id = null, password = null, name=null;
                    
        //ENTER IDENTITY AND ID        
        choice = 0;
        while(choice == 0){
            System.out.println("\n\n-----LOGIN PAGE-----");
            System.out.print("(1)Student\n(2)Staff\n(3)Exit\n>");
            choice = testChoice();
            if(choice == 3){
                return;
            }
            if((choice != 1)&&(choice != 2)){
                choice = 0;
                System.out.print("Wrong command, please enter the correct command!");
            }            
        }
        int type = choice, got=0;
        System.out.print("Please enter your ID: ");
        id = s.nextLine();
        
        //CHECK EXISTENCE OF ID ACCORDING TO ITS 
        PreparedStatement statement = con.prepareStatement("SELECT COUNT(id) AS got FROM member WHERE id = '"+id+"' AND type = "+type);
        ResultSet exist = statement.executeQuery();
        while(exist.next()){
            got = exist.getInt("got");
        }
        if(got == 0){
            System.out.println("\nSorry, your account is not exists!");
            return;
        }
           
        //ENTER PASSWORD
        System.out.print("Please enter password: ");
        password = s.nextLine();
         
        //CHECK PASSWORD IS COLLECT ACCORDING TO ID AND IDENTITY
        statement = con.prepareStatement("SELECT COUNT(id) AS got FROM member WHERE id = '"+id+"' AND type = "+type+" AND password = '"+password+"';");
        exist = statement.executeQuery();
        while(exist.next()){
            got = exist.getInt("got");
        }
        if(got == 0){
            System.out.println("\nWrong password!");
            return;
        }
            
        //OPTION EITHER STUDENT OR STAFF
        if(type == 1){
            PreparedStatement getname = con.prepareStatement("SELECT name FROM member WHERE id = '"+id+"' AND type = "+type);
            ResultSet thename = getname.executeQuery();
            while(thename.next()){
                name = thename.getString("name");
            }
            Student.Shome(id,name);
        }
        if(type == 2){
            PreparedStatement getname = con.prepareStatement("SELECT name FROM member WHERE id = '"+id+"' AND type = "+type);
            ResultSet thename = getname.executeQuery();
            while(thename.next()){
                name = thename.getString("name");
            }
            Staff.Stfhome(name);
        }
    }
 }
