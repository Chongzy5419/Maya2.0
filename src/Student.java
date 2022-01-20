package maya2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student extends Maya2 {
	 public static void Shome(String id, String name) throws Exception {
	        int choice = 0;
	        while(choice == 0){
	            while(choice == 0){
	                System.out.println("\n\n-----STUDENT MENU-----");
	                System.out.println("Welcome! "+name);
	                System.out.print("(1)View timetable\n(2)Search for modules\n(3)Enroll moduless\n(4)Logout\n>");
	                choice = testChoice();
	            }
	            switch (choice){
	                case 1:
	                    timetable(id);
	                    choice = 0;
	                    break;
	                case 2:
	                    studentViewModule(id);
	                    choice = 0;
	                    break;
	                case 3:
	                    enrolls(id,name);
	                    choice = 0;
	                    break;
	                case 4:
	                    choice = -1;
	                    break;
	                default:
	                    choice = 0;
	                    break;
	            }
	        }
	    }
	    
	    public static void timetable(String id) throws Exception {
	        Connection con = getConnection();
		try {
	            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\nMy timetable\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
	            System.out.printf("%-15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%n", " ", "8:00a.m.", "9:00a.m.","10:00a.m.","11:00a.m.","12:00a.m.","1:00p.m.", "2:00p.m.","3:00p.m.","4:00p.m.","5:00p.m.","6:00p.m.","7:00p.m.","8:00p.m.");
	            int counter;
	            int weekc=1;
	            String output=" ";
	            String week=null;
	            for(weekc=1;weekc<6;weekc++) {
			switch (weekc) {
	                    case 1:
	                        week="MONDAY";
	                	break;
	                    case 2:
	                        week="TUESDAY";
				break;
	                    case 3:
				week="WEDNESDAY";
				break;
	                    case 4:
				week="THURSDAY";
				break;
	                    case 5:
				week="FRIDAY";
				break;
	                }
			System.out.printf("%-15s",week);
			for(counter=8;counter<20;counter++) {
	                    PreparedStatement input;
	                    input = con.prepareStatement("SELECT ModuleCode, TIME1,TIME2,TIME3 FROM student"+id+" WHERE Week='"+week+"'");
	                    ResultSet Input=input.executeQuery();
	                    while (Input.next()) {
				String module=Input.getString("ModuleCode");
				int hour1=Input.getInt("TIME1");
				int hour2=Input.getInt("TIME2");
				int hour3=Input.getInt("TIME3");
				if(hour1==counter||hour2==counter||hour3==counter) 
				output=module;
	                    }
				System.out.printf("%15s",output);
				output=(" ");
			}
	                System.out.println("");
	            }
		}
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @SuppressWarnings("resource")
            public static void studentViewModule(String id) throws Exception {
	        Scanner s = new Scanner(System.in);
	        int choice = 0;
	        while (choice == 0) {
	            while(choice == 0){
	                System.out.println("\n\n-----VIEW MODULES-----");
	                System.out.print("(1)View all module\n(2)Search for available modules\n(3)Return\n>");
	                choice = testChoice();
	            }
		    switch(choice){
	                case 1:
	                    viewAllModules();
	                    choice = 0;
	                    break;
			case 2:
			    choice = 0;
	                    while(choice == 0){
	                        while(choice==0){
	                            System.out.println("\n\n-----SEARCH FOR AVAILABLE MODULES-----");
	                            System.out.print("(1)Search by module code\n(2)Search by module name\n(3)Back\n>");
	                            choice = testChoice();
	                        }
	                        if((choice==1)||(choice==2)){
	                            System.out.print("Enter keyword about what you want to search: ");
	                            String key = s.nextLine();
	                            searchModules(choice,key);
	                            break;
	                        }
	                        if(choice == 3){
	                            break;
	                        }
	                        else{
	                            choice = 0;
	                            System.out.println("Please enter the correct command!");
	                        }
	                    }
	                    choice = 0;
			    break;
	                case 3:
	                    choice = -1;
			    break;
	            }
	        }
	    }
	    
	    public static void viewAllModules() throws Exception{
	        Connection con = getConnection();
	        try{
	            PreparedStatement statement = con.prepareStatement("SELECT * FROM raw;");
	            ResultSet result = statement.executeQuery();
	            showModules(result);                                             
	        }catch(Exception e){
	            System.out.println(e);
	        }
	    }
	    
	    public static void searchModules(int choice, String pattern) throws Exception{
	        try{
	            Connection con = getConnection();
	            PreparedStatement statement;
	            if (choice == 1){
	                //SEARCH BY MODULE CODE
	                pattern = "SELECT * FROM raw WHERE ModuleCode LIKE '%"+pattern+"%';";
	                statement = con.prepareStatement(pattern);

	            }
	            else{
	                //SEARCH BY MODULE NAME
	                pattern = "SELECT * FROM raw WHERE ModuleName LIKE '%"+pattern+"%';";
	                statement = con.prepareStatement(pattern);
	            }
	            ResultSet result = statement.executeQuery();
	            showModules(result);
	        }catch(Exception e){
	            System.out.println(e);
	        }
	    }
	    
	    public static void showModules(ResultSet result) throws Exception{
	        try{
	            int startTime = 0, endTime = 0, g, j;
	            String a, b, c, d, e, f, h, i;
	            boolean noTime;
	            
	            System.out.printf("%-15s%-67s%-12s%-14s%-12s%-16s%-10s%-12s%-50s%-11s%n","Module Code", "Module Name" , "MAV Name", "Occurrence" ,"Week" ,"Time" ,"Target", "Activity", "Tutor","Credit Hour");
	            while(result.next()){
	                noTime = false;
	                if((result.getInt("TIME3")!=0)){
	                    startTime = result.getInt("TIME1");
	                    endTime = result.getInt("TIME3")+1;
	                }
	                else if((result.getInt("TIME2")!=0)){
	                    startTime = result.getInt("TIME1");
	                    endTime = result.getInt("TIME2")+1;
	                }
	                else if((result.getInt("TIME1")!=0)){
	                    startTime = result.getInt("TIME1");
	                    endTime = result.getInt("TIME1")+1;
	                }
	                else{
	                    noTime = true;
	                }
	                a = result.getString("ModuleCode");
	                b = result.getString("ModuleName");
	                c = result.getString("MAVName");
	                d = result.getString("Occurrence");
	                if((result.getString("Week"))==null){
	                    e = "-----";
	                }
	                else{
	                    e = result.getString("Week");
	                }
	                if(noTime){
	                    f = "-----";
	                }
	                else{
	                    f = ""+startTime+":00 - "+endTime+":00";
	                }
	                g = result.getInt("Target");
	                h = result.getString("Activity");
	                i = result.getString("Tutor");
	                j = result.getInt("credithour");
	                System.out.printf("%-15s%-67s%-12s%-14s%-12s%-16s%-10d%-12s%-50s%-11d\n",a,b,c,d,e,f,g,h,i,j);
	            }
	        }catch(Exception e){
	            System.out.println(e);
	        }
	    }
	    
	    public static void enrolls(String id, String name) throws Exception {
	        Connection con = getConnection();
	        
		//here is displaying panel
	        try {
	            boolean main=true;
	            //MAIN SWITCH, DO NOT TOUCH!!!!
	            while (main==true) {
	            //here is displaying panel
	                int hours=0;
	                PreparedStatement cal=con.prepareStatement("SELECT credithour FROM student"+id+"");
	                ResultSet cals=cal.executeQuery();
	                while (cals.next()) {
	                    int hour=cals.getInt("credithour");
	                    hours=hours+hour;
	                }
	                System.out.print("\n-----MODULE REGISTRATION-----\nCurrent Credit hours:"+hours+"\nMaximum credit hours:22\n\nCurrent registered modules:\n");
	                PreparedStatement view = con.prepareStatement("SELECT * FROM student"+id+"");
	                ResultSet views=view.executeQuery();
	                System.out.printf("%-10s%-20s%-55s%-20s%-12s%-55s%-13s%-17s%-15s%n", "Number","ModuleCode", "ModuleName", "Occurrence", "Activity", "Tutor", "Week", "Time", "Credit hours");
	                while (views.next()) {
	                    int number=views.getInt("number");
	                    String ModuleCode = views.getString("ModuleCode");
	                    String ModuleName = views.getString("ModuleName");
	                    int Occurrence = views.getInt("Occurrence");
	                    String Activity=views.getString("Activity");
	                    String Tutor = views.getString("Tutor");
	                    String time;
	                    String week;
	                    int startTime = 0, endTime = 0;
	                    boolean noTime = false;
	                    if((views.getInt("TIME3")!=0)){
	                        startTime = views.getInt("TIME1");
	                        endTime = views.getInt("TIME3")+1;
	                    }
	                    else if((views.getInt("TIME2")!=0)){
	                        startTime = views.getInt("TIME1");
	                        endTime = views.getInt("TIME2")+1;
	                    }
	                    else if((views.getInt("TIME1")!=0)){
	                        startTime = views.getInt("TIME1");
	                        endTime = views.getInt("TIME1")+1;
	                    }
	                    else{
	                        noTime = true;
	                    }
	                    if((views.getString("Week"))==null){
	                        week = "-----";
	                    }
	                    else{
	                        week = views.getString("Week");
	                    }
	                    if(noTime){
	                         time = "-----";
	                    }
	                    else{
	                        time = ""+startTime+":00 - "+endTime+":00";
	                    }
	                    int credithour=views.getInt("credithour");
	                    System.out.printf("%-10s%-20s%-55s%-20s%-12s%-55s%-13s%-17s%-15s%n",number, ModuleCode,ModuleName, Occurrence, Activity, Tutor, week, time, credithour);
	                }
						
	                //here is switch control
	                @SuppressWarnings("resource")
	                Scanner A=new Scanner(System.in);
	                int EnrolOpt = 0;
	                
	                while(EnrolOpt==0){
	                    while(EnrolOpt==0){
	                        System.out.print("\n\n1.Add module\n2.Drop module\n3.Return\n>");
	                        EnrolOpt = testChoice();
	                    }
	                }
	                
	                boolean add=false;
	                boolean drop=false;
	                boolean back=false;
						
	                //here is switch
	                switch (EnrolOpt) {
	                    case 1:
	                        add=true;
	                        break;
	                    case 2:
	                        drop=true;
	                        break;
	                    case 3:
	                        back=true;
	                        break;
	                    default:
	                        System.out.println("Please enter the correct command! Try again!");
	                }
					
	                //here is add module function
	                if (add==true) {
						
	                //HERE IS GET MODULE CODE
	                    System.out.print("Please enter module code:");
	                    String code=A.next();
	                    
	                    //Check if searched module exist or not
	                    PreparedStatement check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"';");
	                    boolean moduleExists = checkModule(check);
	                    if(moduleExists==false){
	                        enrolls(id,name);
	                        break;
	                    }          
	                    
                            //Check qualification for English courses
                            PreparedStatement Muet = con.prepareStatement("SELECT * FROM member WHERE name = '"+name+"' AND id = '"+id+"';");
                            ResultSet getMuet = Muet.executeQuery();
                            String muet = null;
                            while(getMuet.next()){
                                muet = getMuet.getString("MUET");
                            }
                            if (code.equalsIgnoreCase("GLT1018")) {
                                if (!(muet.equals("2"))) {
                                    System.out.println("WARNING: This module required MUET band 2 to get credit, you do not reach the requirement! ");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if (code.equalsIgnoreCase("GLT1021")) {
                                if (!(muet.equals("3"))) {
                                    System.out.println("WARNING: This module required MUET band 3 to get credit, you do not reach the requirement! ");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if (code.equalsIgnoreCase("GLT1024")) {
                                if (!(muet.equals("4"))) {
                                    System.out.println("WARNING: This module required MUET band 4 to get credit, you do not reach the requirement! ");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if ((code.equalsIgnoreCase("GLT1027"))||(code.equalsIgnoreCase("GLT1028"))) {
                                if (!(muet.equals("5"))) {
                                    System.out.println("WARNING: This module required MUET band 5 to get credit, you do not reach the requirement!");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            
                            
                            //Check qualification for specialization elective courses,
                            PreparedStatement getMajorStatement = con.prepareStatement("SELECT * FROM member WHERE name = '"+name+"' AND id = '"+id+"';");
                            ResultSet getMajor = getMajorStatement.executeQuery();
                            String programme = null;
                            while(getMajor.next()){
                                programme = getMajor.getString("programme");
                            }
                            if (code.equalsIgnoreCase("WIA2010")) {
                                if (!(programme.equalsIgnoreCase("SE"))) {
                                    System.out.println("WARNING: This specialization elective course does not belongs to your programme!");
                                    System.out.println("Enroll unsuccessfully");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if (code.equalsIgnoreCase("WIA1001")) {
                                if (!(programme.equalsIgnoreCase("DS"))&&!(programme.equalsIgnoreCase("IS"))) {
                                    System.out.println("WARNING: This specialization elective course does not belongs to your programme!");
                                    System.out.println("Enroll unsuccessfully");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if (code.equalsIgnoreCase("WIA1007")) {
                                if (!(programme.equalsIgnoreCase("AI"))) {
                                    System.out.println("WARNING: This specialization elective course does not belongs to your programme!");
                                    System.out.println("Enroll unsuccessfully");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if (code.equalsIgnoreCase("WIX2001")) {
                                if (!(programme.equalsIgnoreCase("CSN"))) {
                                    System.out.println("WARNING: This specialization elective course does not belongs to your programme!");
                                    System.out.println("Enroll unsuccessfully");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            if (code.equalsIgnoreCase("WIA1008")) {
                                if (!(programme.equalsIgnoreCase("MMC"))) {
                                    System.out.println("WARNING: This specialization elective course does not belongs to your department!");
                                    System.out.println("Enroll unsuccessfully");
                                    enrolls(id,name);
                                    break;
                                }
                            }
                            
	                    PreparedStatement enrolling = con.prepareStatement("SELECT * FROM raw WHERE ModuleCode='"+code+"'");
	                    ResultSet Enrolling=enrolling.executeQuery();
	                    System.out.printf("%-20s%-55s%-20s%-10s%-55s%-13s%-17s%-15s%n","ModuleCode", "ModuleName", "Occurrence","Activity", "Tutor", "Week", "Time", "Credit hours");
	                    while (Enrolling.next()) {
	                        String ModuleCode = Enrolling.getString("ModuleCode");
	                        String ModuleName = Enrolling.getString("ModuleName");
	                        int Occurrence = Enrolling.getInt("Occurrence");
	                        String Activity=Enrolling.getString("Activity");
	                        String Tutor = Enrolling.getString("Tutor");
	                        String time;
	                        String week;
	                        int startTime = 0, endTime = 0;
	                        boolean noTime = false;
	                        if((Enrolling.getInt("TIME3")!=0)){
	                            startTime = Enrolling.getInt("TIME1");
	                            endTime = Enrolling.getInt("TIME3")+1;
	                        }
	                        else if((Enrolling.getInt("TIME2")!=0)){
	                            startTime = Enrolling.getInt("TIME1");
	                            endTime = Enrolling.getInt("TIME2")+1;
	                        }
	                        else if((Enrolling.getInt("TIME1")!=0)){
	                            startTime = Enrolling.getInt("TIME1");
	                            endTime = Enrolling.getInt("TIME1")+1;
	                        }
	                        else{
	                            noTime = true;
	                        }
	                        if((Enrolling.getString("Week"))==null){
	                            week = "-----";
	                        }
	                        else{
	                            week = Enrolling.getString("Week");
	                        }
	                        if(noTime){
	                             time = "-----";
	                        }
	                        else{
	                            time = ""+startTime+":00 - "+endTime+":00";
	                        }
	                        int credithour = Enrolling.getInt("credithour");
	                       System.out.printf("%-20s%-55s%-20s%-10s%-55s%-13s%-17s%-15s%n", ModuleCode,ModuleName, Occurrence,Activity, Tutor, week, time, credithour);
	                    }
	                    System.out.println("\n");
							
	                    //HERE IS GET OCC CODE
	                    System.out.print("Please enter occurence to be added: ");
	                    String occ=A.next();
	                    
	                    //Check if searched module exist or not
	                    check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Occurrence = '"+occ+"'");
	                    moduleExists = checkModule(check);
	                    if(moduleExists==false){
	                        enrolls(id,name);
	                        break;
	                    }  
	                    
	                    PreparedStatement enrolling2 = con.prepareStatement("SELECT * FROM raw WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"'");
	                    ResultSet Enrolling2=enrolling2.executeQuery();
	                    System.out.printf("%-20s%-55s%-20s%-10s%-55s%-13s%-17s%-15s%n","ModuleCode", "ModuleName", "occurrence","Activity", "Tutor", "Week", "Time", "Credit hours");
	                    while (Enrolling2.next()) {
	                        String ModuleCode = Enrolling2.getString("ModuleCode");
	                 	String ModuleName = Enrolling2.getString("ModuleName");
	                        int Occurrence = Enrolling2.getInt("Occurrence");
	                        String Activity=Enrolling2.getString("Activity");
	                        String Tutor = Enrolling2.getString("Tutor");
	                        String time;
	                        String week;
	                        int startTime = 0, endTime = 0;
	                        boolean noTime = false;
	                        if((Enrolling2.getInt("TIME3")!=0)){
	                            startTime = Enrolling2.getInt("TIME1");
	                            endTime = Enrolling2.getInt("TIME3")+1;
	                        }
	                        else if((Enrolling2.getInt("TIME2")!=0)){
	                            startTime = Enrolling2.getInt("TIME1");
	                            endTime = Enrolling2.getInt("TIME2")+1;
	                        }
	                        else if((Enrolling2.getInt("TIME1")!=0)){
	                            startTime = Enrolling2.getInt("TIME1");
	                            endTime = Enrolling2.getInt("TIME1")+1;
	                        }
	                        else{
	                            noTime = true;
	                        }
	                        if((Enrolling2.getString("Week"))==null){
	                            week = "-----";
	                        }
	                        else{
	                            week = Enrolling2.getString("Week");
	                        }
	                        if(noTime){
	                             time = "-----";
	                        }
	                        else{
	                            time = ""+startTime+":00 - "+endTime+":00";
	                        }
	                        int credithour = Enrolling2.getInt("credithour");
	                        System.out.printf("%-20s%-55s%-20s%-10s%-55s%-13s%-17s%-15s%n", ModuleCode,ModuleName, Occurrence,Activity, Tutor, week, time, credithour);
	                    }
	                    System.out.println("\n");
							
	                    //HERE IS COMFIRM SELECTION
	                    boolean choice1=true;
	                    boolean SameTime=false;
	                    boolean choice2=true;
	                    boolean hourlimit=true;
                            boolean fullregistered = false;
	                    
	                    //Check selection for yes or no
	                    String choice;
	                    while(true){
	                        System.out.print("courses above are selected, do you wish to add these courses to selection?(Y/N): ");
	                        choice=A.next();
	                        if((choice.equalsIgnoreCase("Y"))||(choice.equalsIgnoreCase("N"))){
	                            break;
	                        }
	                        else{
	                            System.out.println("Please enter Y or N only! Try again!");
	                        }
	                    }
						
	                    //check input Y/N
	                    if(choice.equalsIgnoreCase("N"))
	                        choice1=false;				
	                        //check for same time
	                        for(int weekc=1;weekc<6;weekc++) {
	                            String week=null;
	                            switch (weekc) {
	                                case 1:
	                                    week="MONDAY";
	                                    break;
	                                case 2:
	                                    week="TUESDAY";
	                                    break;
	                                case 3:
	                                    week="WEDNESDAY";
	                                    break;
	                                case 4:
	                                    week="THURSDAY";
	                                    break;
	                                case 5:
	                                    week="FRIDAY";
	                                    break;
	                            }
	                            PreparedStatement check3= con.prepareStatement("SELECT TIME1,TIME2,TIME3 FROM student"+id+" WHERE week='"+week+"' ");
	                            ResultSet timeIN=check3.executeQuery();
	                            while (timeIN.next()) {
	                                int TIMEIN1 = timeIN.getInt("TIME1");
	                                int TIMEIN2 = timeIN.getInt("TIME2");
	                                int TIMEIN3 = timeIN.getInt("TIME3");
	                                //System.out.println("TIMEIN= "+TIMEIN1+" "+TIMEIN2+" "+TIMEIN3+" ");			
	                                PreparedStatement check4= con.prepareStatement("SELECT TIME1,TIME2,TIME3 FROM raw WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"' AND week='"+week+"' ");
	                                ResultSet timeOUT=check4.executeQuery();
	                                while (timeOUT.next()) {
	                                    int TIMEOUT1 = timeOUT.getInt("TIME1");
	                                    int TIMEOUT2 = timeOUT.getInt("TIME2");
	                                    int TIMEOUT3 = timeOUT.getInt("TIME3");
	                                    //System.out.println("TIMEOUT= "+TIMEOUT1+" "+TIMEOUT2+" "+TIMEOUT3+" ");
	                                    if (TIMEOUT1!=0)
	                                        if (TIMEOUT1==TIMEIN1||TIMEOUT1==TIMEIN2||TIMEOUT1==TIMEIN3) 
	                                            SameTime=true;
	                                    if (TIMEOUT2!=0)
	                                        if (TIMEOUT2==TIMEIN1||TIMEOUT2==TIMEIN2||TIMEOUT2==TIMEIN3) 
	                                            SameTime=true;
	                                    if (TIMEOUT3!=0)
	                                        if (TIMEOUT3==TIMEIN1||TIMEOUT3==TIMEIN2||TIMEOUT3==TIMEIN3) 
	                                            SameTime=true;
	                                }           
	                            }
	                            //System.out.print(SameTime+"\t");
	                            //System.out.println(week);
	                        }
	                        if (SameTime==true)
	                        	System.out.println("Time clash occured, please try again!\n");
	                        
	                        //check for qualification
	                        /*String Mav=null;
	                        int eng = 0;
	                        PreparedStatement quali= con.prepareStatement("SELECT DISTINCT MAVName FROM raw WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"' ");
	                        ResultSet Quali=quali.executeQuery();
	                        while(Quali.next()) {
                                    Mav=Quali.getString("MAVName");
	                        }
	                        if(Mav.equals("L2")){
	                        	PreparedStatement muet= con.prepareStatement("SELECT DISTINCT MUET FROM member WHERE id='"+id+"'AND name='"+name+"' ");
		                        ResultSet Muet=muet.executeQuery();
		                        while(Muet.next()) {
			                        eng=Muet.getInt("Muet");
			                        }
		                       
		                        if (eng==3 ||eng==4)
		                    	   choice2=true;
		                        else {
		                        	choice2=false;
		                        	System.out.print("WARNING: this course required MUET band 3&4 to get credit, you do not reach the requirement " );
		                        }   
	                        }
	                        if(Mav.equals("L3")){
	                        	PreparedStatement muet= con.prepareStatement("SELECT DISTINCT MUET FROM member WHERE id='"+id+"'AND name='"+name+"' ");
		                        ResultSet Muet=muet.executeQuery();
		                        while(Muet.next()) {
			                        eng=Muet.getInt("Muet");
			                        }
		                       
		                        if (eng==5 ||eng==6)
		                    	   choice2=true;
		                        else {
		                        	choice2=false;
		                        	System.out.print("WARNING: this course required MUET band 5&6 to get credit, you do not reach the requirement " );
		                        }   
	                        }*/
							
	                        //check for credit hour limit
	                        if(hours>22) {
	                            hourlimit=false;
	                            System.out.println("Sorry, you had reached the maximum credit hour\n");
	                        }
                                String field=code.concat(occ);
                                
                                //check for number of student target
                                int numOfStudent = 0;
                                try{
                                    PreparedStatement check5 = con.prepareStatement("SELECT count(id) FROM "+field);
                                    ResultSet numofstudent = check5.executeQuery();
                                    numofstudent.next();
                                    numOfStudent = numofstudent.getInt("count(id)");
                                }catch(Exception e){}
                                PreparedStatement check6 = con.prepareStatement("SELECT Target FROM raw WHERE ModuleCode = '"+code+"' AND Occurrence = '"+occ+"'");
                                ResultSet getTarget = check6.executeQuery();
                                getTarget.next();
                                int target = getTarget.getInt("Target");
                                if (numOfStudent == target) {
                                    fullregistered = true;
                                    System.out.println("The number of students enroll has already reach the maximum!");                                    
                                }
							
	                        //if pass all check, enroll
	                        if(SameTime==false && choice1==true && choice2==true && hourlimit==true && fullregistered == false) {
	                            PreparedStatement enrolled1 = con.prepareStatement("ALTER TABLE student"+id+" AUTO_INCREMENT = 1;");
	                            PreparedStatement enrolled2 = con.prepareStatement("INSERT INTO student"+id+" (ModuleCode, ModuleName, Occurrence,Activity, Tutor,credithour, Week, TIME1,TIME2,TIME3) SELECT ModuleCode, ModuleName, Occurrence,Activity, Tutor,credithour, Week, TIME1,TIME2,TIME3 FROM raw WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"'");
	                            PreparedStatement enrolled3 = con.prepareStatement("CREATE TABLE IF NOT EXISTS "+field+" (number int NOT NULL AUTO_INCREMENT PRIMARY KEY, name varchar(255), id varchar(255))  ");
	                            PreparedStatement enrolled4 = con.prepareStatement("INSERT INTO "+field+"(name, id) VALUES ('"+name+"', 'student"+id+"');");		
	                            enrolled1.executeUpdate();
	                            enrolled2.executeUpdate();
	                            enrolled3.executeUpdate();
	                            enrolled4.executeUpdate();
	                            System.out.println("Sucessfully enrolled.\n");
	                        }
	                        else{
	                            System.out.println("Enroll unsuccessfully.");
	                        }
						
	                //set switch to go back to selection
	                add=false;
	                }
	                else 
	                    add=false;
						
					
	                //here is drop module function
	                if(drop==true) {
	                //HERE IS GET MODULE CODE
                            String occ = null;
	                    System.out.print("Please enter module to be dropped:");
	                    String code=A.next();
	                    
	                    //Check if searched module exist or not
	                    PreparedStatement check = con.prepareStatement("SELECT count(ModuleCode) AS got FROM student"+id+" WHERE ModuleCode = '"+code+"';");
	                    boolean moduleExists = checkModule(check);
	                    if(moduleExists==false){
	                        enrolls(id,name);
	                        break;
	                    }   
	                    
	                    PreparedStatement dropping = con.prepareStatement("SELECT ModuleCode, ModuleName, Occurrence,Tutor,credithour FROM student"+id+" WHERE ModuleCode='"+code+"'");
	                    ResultSet Dropping=dropping.executeQuery();
	                    System.out.printf("%-20s%-40s%-20s%-40s%-20s%n","ModuleCode", "ModuleName", "occurrence","tutor","credit hours");
	                    while (Dropping.next()) {
	                        String ModuleCode = Dropping.getString("ModuleCode");
	                        String ModuleName = Dropping.getString("ModuleName");
	                        int Occurrence = Dropping.getInt("Occurrence");
                                occ = Integer.toString(Occurrence);
	                        String Tutor = Dropping.getString("Tutor");
	                        int credithour = Dropping.getInt("credithour");
	                        System.out.printf("%-20s%-40s%-20s%-40s%-20s%n", ModuleCode,ModuleName, Occurrence,Tutor, credithour);
	                    }
	                    System.out.println("\n");                    
	                    
	                    String choice;
	                    while(true){
	                        System.out.print("The courses above are selected, do you wish to drop these?(Y/N)> ");
	                        choice=A.next();
	                        if((choice.equalsIgnoreCase("Y"))||(choice.equalsIgnoreCase("N"))){
	                            break;
	                        }
	                        else{
	                            System.out.println("Please enter Y or N only! Try again!");
	                        }
	                    }

	                    //here is confirm selection
	                    if(choice.equalsIgnoreCase("Y")){
	                        PreparedStatement dropped = con.prepareStatement("DELETE FROM student"+id+"  WHERE ModuleCode='"+code+"'");
	                        dropped.executeUpdate();
                                PreparedStatement dropped2 = con.prepareStatement("DELETE FROM "+code+occ+" WHERE id = 'student"+id+"'");
                                dropped2.executeUpdate();
	                        System.out.println("Sucessfully dropped\n");
	                        drop=false;
	                    }		
                            else{
	                        drop=false;
	                        System.out.println("Drop course unsuccessfully.");
                            }
	                }//end DROP function	

	                //here is back function
	                if(back==true) {
	                    main=false;		
	                }//end BACK function
	            }//end MAIN SWITCH
	        }//end throw
			
		//here is to catch error
	        catch (SQLException e) {
	    	e.printStackTrace();
		}
	    }
	    
	    public static boolean checkModule (PreparedStatement statement) throws Exception{
	    	 //Check if searched module exist or not        
	        ResultSet checkresult = statement.executeQuery();
	        int got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module you search is not exist!");
	            return false;
	        }     
	        return true;
	    }
}
