package maya2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Staff extends Maya2 {
	   public static void Stfhome(String name) throws Exception {
	        int choice = 0;
	        while(choice == 0){
	            while(choice == 0){
	                System.out.println("\n\n-----STAFF PAGE-----");
	                System.out.println("Welcome! "+name);
	                System.out.print("1.Modify module\n2.View modules\n3.View enrolled student\n4.Logout\n>");
	                choice = testChoice();
	            }
	            switch(choice){
	                case 1:
	                    modify(name);
	                    choice = 0;
	                    break;
	                    
	                case 2:
	                    staffViewModule(name);
	                    choice = 0;
	                    break;
	                    
	                case 3:
	                    list(name);
	                    choice = 0;
	                    break;
	                
	                case 4:
	                    choice = -1;
	                    break;
	                
	                default:
	                    System.out.println("Wrong command, please enter the correct command!");
	                    choice = 0;
	                    break;
	            }
	        }        	
	    }
	    
	    @SuppressWarnings("resource")
            public static void modify(String name) throws Exception{
	        Scanner s = new Scanner(System.in);
	        int choice = 0;
	        while(choice==0){
	            while(choice==0){
	                System.out.println("\n\n-----MODIFY COURSE-----");
	                System.out.print("1.Create module\n2.Delete module\n3.Edit module\n4.Return\n>");
			choice = s.nextInt();
	            }
	            switch(choice){
	                case 1:
	                    createModule();
	                    choice = 0;
	                    break;
	                
	                case 2:
	                    deleteModule(name);
	                    choice = 0;
	                    break;
	                 
	                case 3:
	                    editModule(name);
	                    choice = 0;
	                    break;
	                
                        case 4:
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
            public static void createModule()throws Exception{
	        Connection con = getConnection();
	        Scanner s = new Scanner(System.in);
	        System.out.print("Enter module ID: ");
	        String a=s.nextLine();
	        System.out.print("Enter module Code: ");
	        String b=s.nextLine();
	        System.out.print("Enter module Name: ");
	        String c=s.nextLine();        
	        System.out.print("Enter occurence: ");
	        String d=s.nextLine();
	        System.out.print("Enter Mavname: ");
	        String e=s.nextLine();
	        int f;
	        while(true){
	            try{
	                System.out.print("Enter Target: ");
	                f = s.nextInt();
	                break;
	            }catch(Exception error){
	                System.out.println("Please enter the correct Target!");
	                s.nextInt();
	            }
	        }
                s.nextLine();
	        System.out.print("Enter Activity: ");
		String g=s.nextLine();
		System.out.print("Enter Tutor: ");
		String h=s.nextLine();	
	        int i;
	        while(true){
	            try{
	                System.out.print("Enter credit hour: ");
	                i = s.nextInt();
	                break;
	            }catch(Exception error){
	                System.out.println("Please enter the correct credit hour!");
	                s.nextInt();
	            }
	        }
                s.nextLine();
		System.out.print("Enter week: ");
		String j=s.nextLine();
	        int hour;
	        while(true){
	            try{
	                System.out.print("How many hour(s) is this course?");
	                hour = s.nextInt();
	                if((hour>3)||(hour<0)){
	                    throw new Exception();
	                }
	                break;
	            }catch(Exception error){
	                System.out.println("Please enter the correct duration!");
	                s.nextLine();
	            }
	        }
	        PreparedStatement modify = con.prepareStatement("SELECT * FROM raw");
	        int k = 0,l = 0,m = 0;
	        if(hour>0){
	            while(true){
	                try{
	                    System.out.print("Enter time 1: ");
	                    k = s.nextInt();
	                    break;
	                }catch(Exception error){
	                    System.out.println("Please enter the correct time 1!");
	                    s.nextLine();
	                }
	            }
	        }
	        if(hour>1){
	            while(true){
	                try{
	                    System.out.print("Enter time 2: ");
	                    l = s.nextInt();
	                    break;
	                }catch(Exception error){
	                    System.out.println("Please enter the correct time 2!");
	                    s.nextLine();
	                }
	            }
	        }
	        if(hour>2){
	            while(true){
	                try{
	                    System.out.print("Enter time 3: ");
	                    m = s.nextInt();
	                    break;
	                }catch(Exception error){
	                    System.out.println("Please enter the correct time 3!");
	                    s.nextLine();
	                }
	            }
	        }
	        if(hour==0){
	            modify = con.prepareStatement("INSERT INTO raw VALUES('"+a+"', '"+b+"', '"+c+"', '"+d+"', '"+e+"', "+f+", '"+g+"', '"+h+"', "+i+", '"+j+"', NULL, NULL, NULL)");
	        }
	        if (hour==1) {
	            modify = con.prepareStatement("INSERT INTO raw VALUES('"+a+"', '"+b+"', '"+c+"', '"+d+"', '"+e+"', "+f+", '"+g+"', '"+h+"', "+i+", '"+j+"', "+k+", NULL, NULL)");
	        }
	        if (hour==2) {
	            modify = con.prepareStatement("INSERT INTO raw VALUES('"+a+"', '"+b+"', '"+c+"', '"+d+"', '"+e+"', "+f+", '"+g+"', '"+h+"', "+i+", '"+j+"', "+k+", "+l+", NULL)");
	        }
	        if (hour==3) {
	            modify = con.prepareStatement("INSERT INTO raw VALUES('"+a+"', '"+b+"', '"+c+"', '"+d+"', '"+e+"', "+f+", '"+g+"', '"+h+"', "+i+", '"+j+"', "+k+", "+l+", "+m+")");
	        }
		System.out.printf("%-10s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %n ", "ModuleID" , "ModuleCode" ,"ModuleName", "Occurence", "MavName","Target", "Activity","Tutor","credit hour", "week", "time1", "time2", "time3");
		System.out.printf("%-10s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %n ", a, b,c,d,e,f,g,h,i,j,k,l,m);
		System.out.print("the record above will be added, do you wish to continue?(Y/N):");
		String comf=s.next();
		if(comf.equalsIgnoreCase("Y")) {
	            modify.executeUpdate();
	            System.out.println("Module "+c+" was sucessfully created");
		}
	        else{
	            System.out.println("Module "+c+" was not created");
	        }
	    }
	    
	    @SuppressWarnings("resource")
            public static void deleteModule(String name)throws Exception{
	        Connection con = getConnection();
	        Scanner s = new Scanner(System.in);
	        
                //Show lecturer courses
                System.out.println("Your course(s): ");
                PreparedStatement get = con.prepareStatement("SELECT * FROM raw WHERE Tutor = '"+name+"'");
                ResultSet result = get.executeQuery();
                System.out.printf("%-20s %-55s %-20s %n","ModuleCode", "ModuleName", "occurrence");
                while (result.next()) {
	            String ModuleCode = result.getString("ModuleCode");
	            String ModuleName = result.getString("ModuleName");
	            int Occurrence = result.getInt("Occurrence");
	            System.out.printf("%-20s %-55s %-20s %n", ModuleCode,ModuleName, Occurrence);
		}
		System.out.println();
                
	        //Enter module code and occurrence for module to be delete
	        System.out.print("Please enter module code for module to be deleted:");
		String code=s.next();
		System.out.print("Please enter occurence: ");
		String occ=s.next();
	        
	        //Check if searched module exist or not
	        PreparedStatement check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Occurrence = '"+occ+"' AND Tutor = '"+name+"'");
	        ResultSet checkresult = check.executeQuery();
	        int got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module you search is not exist!");
	            return;
	        }
	        
	        //Show module which will be deleted
	        PreparedStatement dropping = con.prepareStatement("SELECT ModuleCode, ModuleName, Occurrence,Activity,Tutor,credithour FROM raw WHERE ModuleCode='"+code+"' AND Occurrence='"+occ+"' AND Tutor = '"+name+"'");
		ResultSet Dropping=dropping.executeQuery();
		System.out.printf("%-20s%-40s%-20s%-15s%-40s%-20s%n","ModuleCode", "ModuleName", "occurrence","Activity","tutor","credit hours");
		while (Dropping.next()) {
	            String ModuleCode = Dropping.getString("ModuleCode");
	            String ModuleName = Dropping.getString("ModuleName");
	            int Occurrence = Dropping.getInt("Occurrence");
	            String Activity = Dropping.getString("Activity");
	            String Tutor = Dropping.getString("Tutor");
	            int credithour = Dropping.getInt("credithour");
	            System.out.printf("%-20s%-40s%-20s%-15s%-40s%-20s%n", ModuleCode,ModuleName, Occurrence,Activity,Tutor, credithour);
		}
		System.out.println("\n");
	        
	        //Confirmation to delete selected module
		System.out.print("The courses above are selected, do you wish to drop these?(Y/N): ");
	        String choice = s.next();
		if(choice.equalsIgnoreCase("Y")){
	            PreparedStatement dropped = con.prepareStatement("DELETE FROM raw WHERE ModuleCode='"+code+"' AND Occurrence='"+occ+"' AND Tutor = '"+name+"'");
	            dropped.executeUpdate();
	            System.out.println("Module "+code+" with occurrence "+occ+" has been sucessfully deleted");
	        }
	        else{
	            System.out.println("Module "+code+" with occurrence "+occ+" was not deleted");
	        }
	    }
	    
	    @SuppressWarnings("resource")
            public static void editModule(String name) throws Exception{
	        Scanner s = new Scanner(System.in);
	        Connection con = getConnection();
                
	        //Show lecturer courses
                System.out.println("Your course(s): ");
                PreparedStatement get = con.prepareStatement("SELECT * FROM raw WHERE Tutor = '"+name+"'");
                ResultSet result = get.executeQuery();
                System.out.printf("%-20s %-55s %-20s %n","ModuleCode", "ModuleName", "occurrence");
                while (result.next()) {
	            String ModuleCode = result.getString("ModuleCode");
	            String ModuleName = result.getString("ModuleName");
	            int Occurrence = result.getInt("Occurrence");
	            System.out.printf("%-20s %-55s %-20s %n", ModuleCode,ModuleName, Occurrence);
		}
		System.out.println();
                
	        //Get module by module code
	        System.out.print("Please enter module code:");
		String code = s.nextLine();
	        
	        //Check existence of course by course code
	        PreparedStatement check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Tutor = '"+name+"'");
	        ResultSet checkresult = check.executeQuery();
	        int got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module with module code "+code+" is not exist!");
	            return;
	        }
	        
	        //Display module that you searched by using module code
		PreparedStatement enrolling = con.prepareStatement("SELECT ModuleCode, ModuleName, Occurrence, Activity, Tutor,credithour FROM raw WHERE ModuleCode='"+code+"' AND Tutor = '"+name+"'");
	        ResultSet Enrolling=enrolling.executeQuery();
		System.out.printf("%-20s%-55s%-20s%-10s%-55s%-20s%n","ModuleCode", "ModuleName", "occurrence","Activity", "tutor","credit hours");
		while (Enrolling.next()) {
	            String ModuleCode = Enrolling.getString("ModuleCode");
	            String ModuleName = Enrolling.getString("ModuleName");
	            int Occurrence = Enrolling.getInt("Occurrence");
	            String Activity=Enrolling.getString("Activity");
	            String Tutor = Enrolling.getString("Tutor");
	            int credithour = Enrolling.getInt("credithour");
	            System.out.printf("%-20s%-55s%-20s%-10s%-55s%-20s%n", ModuleCode,ModuleName, Occurrence,Activity, Tutor, credithour);
		}
		System.out.println("\n");
	        
	        //Get module by occurrence
	        System.out.print("Please enter occurence to be modified: ");
	        String occ=s.nextLine();
	        
	        //Check existence of module by module code and occurrence
	        check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Occurrence = '"+occ+"' AND Tutor = '"+name+"'");
	        checkresult = check.executeQuery();
	        got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module with module code "+code+" and occurrence "+occ+" is not exist!");
	            return;
	        }
	        
	        //Display module that you searched by using module code and occurrence
		PreparedStatement enrolling2 = con.prepareStatement("SELECT ModuleCode, ModuleName, Occurrence, Activity, Tutor,credithour FROM raw WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"' AND tutor = '"+name+"'");
		ResultSet Enrolling2=enrolling2.executeQuery();
		System.out.printf("%-20s%-55s%-20s%-10s%-55s%-20s%n","ModuleCode", "ModuleName", "occurrence","Activity", "tutor","credit hours");
		while (Enrolling2.next()) {
	            String ModuleCode = Enrolling2.getString("ModuleCode");
	            String ModuleName = Enrolling2.getString("ModuleName");
	            int Occurrence = Enrolling2.getInt("Occurrence");
	            String Activity=Enrolling2.getString("Activity");
	            String Tutor = Enrolling2.getString("Tutor");
	            int credithour = Enrolling2.getInt("credithour");
	            System.out.printf("%-20s%-55s%-20s%-10s%-55s%-20s%n", ModuleCode,ModuleName, Occurrence,Activity, Tutor, credithour);
		}
	        System.out.println("\n");
	        
	        //Get module by activity mode
	        System.out.print("Please enter activity mode: ");
		String acc=s.nextLine();
	        
	        //Check existence of module by module code, occurrence and activity type
	        check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Occurrence = '"+occ+"' AND Activity = '"+acc+"' AND Tutor = '"+name+"'");
	        checkresult = check.executeQuery();
	        got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module with module code "+code+", occurrence "+occ+" and activity "+acc+" is not exist!");
	            return;
	        }
	        
	        //Display module you studentViewModule by using module code, occurrence and activity
		PreparedStatement enrolling3 = con.prepareStatement("SELECT ModuleCode, ModuleName, Occurrence, Activity, Tutor,credithour FROM raw WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"' AND Activity='"+acc+"' AND Tutor = '"+name+"'");
		ResultSet fin=enrolling3.executeQuery();
		System.out.printf("%-20s %-55s %-20s %-10s %-55s %-20s %n","ModuleCode", "ModuleName", "occurrence","Activity", "tutor","credit hours");
		while (fin.next()) {
	            String ModuleCode = fin.getString("ModuleCode");
	            String ModuleName = fin.getString("ModuleName");
	            int Occurrence = fin.getInt("Occurrence");
	            String Activity=fin.getString("Activity");
	            String Tutor = fin.getString("Tutor");
	            int credithour = fin.getInt("credithour");
	            System.out.printf("%-20s %-55s %-20s %-10s %-55s %-20s %n", ModuleCode,ModuleName, Occurrence,Activity, Tutor, credithour);
		}
		System.out.println("\n");
	        
	        //Confirmation to modify a course
	        System.out.print("courses above are selected, do you wish to edit the course selected?(Y/N): ");
		String choice = s.nextLine();
		if(choice.equalsIgnoreCase("Y")) {
	            System.out.print("enter new target student: ");
	            int num=s.nextInt();
                    s.nextLine();
	            System.out.print("enter new activity mode: ");
	            String acti=s.nextLine();
	            System.out.print("enter new tutor name: ");
	            String tutor=s.nextLine();
	            System.out.print("enter new credit hour: ");
	            int cre=s.nextInt();
                    s.nextLine();
	            System.out.print("enter new day of week: ");
	            String day=s.nextLine();
	            System.out.print("enter new time1: ");
	            int t1=s.nextInt();
	            System.out.print("enter new time2: ");
	            int t2=s.nextInt();
	            System.out.print("enter new time3: ");
	            int t3=s.nextInt();
	            PreparedStatement editor= con.prepareStatement("UPDATE raw SET Target='"+num+"' ,Activity='"+acti+"', Tutor='"+tutor+"', credithour='"+cre+"', Week='"+day+"', TIME1='"+t1+"' , TIME2='"+t2+"', TIME3='"+t3+"'  WHERE ModuleCode='"+code+"'AND occurrence='"+occ+"' AND Activity='"+acc+"'");
	            editor.executeUpdate();
	            System.out.println("Module "+code+" with occurrence "+occ+" and activity "+acc+" has been sucessfully edited");
	        }
	        else{
	            System.out.println("Module "+code+" with occurrence "+occ+" and activity "+acc+" was not edited");
	        }
	    }
	    
	    public static void staffViewModule(String name) throws Exception{
	        @SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
	        Connection con = getConnection();
	        int choice = 0;
	        while (choice == 0) {
	            while(choice == 0){
	                System.out.println("\n\n-----VIEW MODULES-----");
	                System.out.print("(1)View all module\n(2)Search for available modules\n(3)View my module\n(4)return\n>");
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
	                    PreparedStatement statement=con.prepareStatement("SELECT * FROM raw WHERE Tutor='"+name+"' ");
	                    ResultSet Result=statement.executeQuery();
	                    showModules(Result);
	                    choice = 0;
	                    break;                    
	                case 4:
	                    choice = -1;
			    break;
	            }
	        }
	    }
	    
	    @SuppressWarnings("resource")
            public static void list(String name) throws Exception{
	        Scanner s = new Scanner(System.in);
	        Connection con = getConnection();
	        
                //Show lecturer courses
                System.out.println("Your course(s): ");
                PreparedStatement get = con.prepareStatement("SELECT * FROM raw WHERE Tutor = '"+name+"'");
                ResultSet result = get.executeQuery();
                System.out.printf("%-20s %-55s %-20s %n","ModuleCode", "ModuleName", "occurrence");
                while (result.next()) {
	            String ModuleCode = result.getString("ModuleCode");
	            String ModuleName = result.getString("ModuleName");
	            int Occurrence = result.getInt("Occurrence");
	            System.out.printf("%-20s %-55s %-20s %n", ModuleCode,ModuleName, Occurrence);
		}
		System.out.println();

	        //Enter module code for module that you wish to view
	        System.out.print("Please enter module code:");
		String code = s.nextLine();
	        
	        //Check the existence of module by its module code
	        PreparedStatement check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Tutor = '"+name+"'");
	        ResultSet checkresult = check.executeQuery();
	        int got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module with module code "+code+" is not exist!");
                    
	            return;
	        }
	        
	        //Display course(s) by module code you entered
		PreparedStatement enrolling = con.prepareStatement("SELECT ModuleCode, ModuleName, Occurrence FROM raw WHERE ModuleCode='"+code+"' AND Tutor = '"+name+"'");
		ResultSet Enrolling=enrolling.executeQuery();
		System.out.printf("%-20s %-55s %-20s %n","ModuleCode", "ModuleName", "occurrence");
		while (Enrolling.next()) {
	            String ModuleCode = Enrolling.getString("ModuleCode");
	            String ModuleName = Enrolling.getString("ModuleName");
	            int Occurrence = Enrolling.getInt("Occurrence");
	            System.out.printf("%-20s %-55s %-20s %n", ModuleCode,ModuleName, Occurrence);
		}
		System.out.println();
	        
	        //Enter occurrence 
	        System.out.print("Please enter occurence: ");
		String occ = s.nextLine();
		String field = code.concat(occ);
	        
	        //Check the existence of course according to its course code and occurrence
	        check = con.prepareStatement("SELECT count(ID) AS got FROM raw where ModuleCode = '"+code+"' AND Tutor = '"+name+"' AND Occurrence = '"+occ+"'");
	        checkresult = check.executeQuery();
	        got = 0;
	        while(checkresult.next()){
	            got = checkresult.getInt("got");
	        }
	        if (got == 0) {
	            System.out.println("The module with module code "+code+" and occurrence "+occ+" is not exist!");
	            return;
	        }
	        
	        //Display information about module
	        String modname=null;
		String modtar=null;
		
		PreparedStatement mname = con.prepareStatement("SELECT ModuleName, Target FROM raw WHERE ModuleCode='"+code+"' AND Tutor = '"+name+"' AND Occurrence='"+occ+"'");
		ResultSet Name = mname.executeQuery();
		while (Name.next()) {
	            modname = Name.getString("ModuleName");
	            modtar = Integer.toString(Name.getInt("Target"));
		}
	        System.out.println("Module Code: "+code);
		System.out.println("Module Name: "+modname);
		System.out.println("Occurrence: "+occ);
		System.out.println("target student: "+modtar);
	        
	        //Display register student
                PreparedStatement print;
                ResultSet fin;
                try{
                    print = con.prepareStatement("SELECT * FROM "+field+" ");
                    fin = print.executeQuery();
                }catch(Exception e){
                    System.out.println("Registered Students: ");
                    System.out.printf("%-40s %-20s %n","Student name","Student ID");
                    return;
                }
		System.out.println("Registered Students: ");
	        System.out.printf("%-40s %-20s %n","Student name","Student ID");
		while (fin.next()) {
	            String studentname = fin.getString("name");
	            String id = fin.getString("id");
	            System.out.printf("%-40s %-20s %n",studentname,id.substring(7));
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
