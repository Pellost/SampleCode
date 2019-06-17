package resource;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/*
 * This class encapsulates the data model of an enrollment system.
 */

public class Model extends Observable{
	
/*********************FIELD***************************/
	
	private Random rng = new Random();
	private String personInfo = "Student Name: Steven Wong"
								+ "\n"
								+ "Student Number: 212565666"
								+ "\n"
								+ "GPA: 4.5"
								+ "\n"
								+ "Number of credits taken: 60"
								+ "\n"
								+ "Home Address: 7 Bishop Avenue, North York, Ontario, M2M 4J4"
								+ "\n"
								+ "Phone Number: +1 (416) 898-9319"
								+ "Status: Academic Probation";
	private String programTitle = "Program Name: Computer Science";
	private String programName = "Computer science blends theoretical knowledge and practical skills "
								+ "to explore intriguing topics –computer systems that mimic human vision "
								+ "or helping design pharmaceutical drugs. Learn the theoretical limitations "
								+ "of computing and methodologies that lead to efficient, accurate computing."
								+ "\n"
								+ "\n"
								+ "List of required courses:";
	private String[] courseList1 = {"EECS1001 -- ACQUIRED", "EECS1012 -- ACQUIRED", "EECS1019", "EECS1022 -- ACQUIRED", "EECS2001","EECS2011", "EECS2021 -- ACQUIRED", 
									"EECS2030", "EECS2031"};
	private String[] courseList = {"EECS1001", "EECS1012", "EECS1019", "EECS1022", "EECS2001","EECS2011", "EECS2021", 
									"EECS2030", "EECS2031"};
	private String termCourse = "Term courses";
	private String fallTitle = "Fall";
	private String winterTitle = "Winter";
	private String courseTakenTitle = "Courses that can be taken \n";
	private String courseScheduleTitle = "Courses";
	private ArrayList<String> selectedElement = new ArrayList<String>();
	private ArrayList<String> itemSelected = new ArrayList<String>();
	
/**************************************************COURSES*******************************************************************/
	private String cse1001 = "LE/EECS1001 Research Directions in Computing";
	private String cse1001Descrip = "An introduction to research directions "
									+ "within the department and more broadly within the field. "
									+ "Students will attend lectures and other events organised by the department. "
									+ "Note: This course is expected to be completed in the first year of study.";
	
	private String cse1012 = "LE/EECS1012 Net-centric Introduction of Computing";
	private String cse1012Descrip = "The objectives of 1012 are threefold: providing a first exposure to event-driven "
									+ "programming, teaching students a set of computing skills (including reasoning about "
									+ "algorithms, tracing programs, test-driven development, unit testing), and providing an "
									+ "introduction to computing within a mobile, net-centric context. It uses problem-based "
									+ "approach to expose the underlying concepts and an experiential laboratory to implement them.";
	
	private String cse1019 = "LE/EECS1019 Discrete Mathematics for Computer Science";
	private String cse1019Descrip = "Introduction to abstraction. Use and development of precise formulations of mathematical ideas. "
									+ "Informal introduction to logic; introduction to naïve set theory; induction; relations and "
									+ "functions; big O-notation; recursive definitions, recurrence relations and their solutions; graphs and trees.";
	
	private String cse1022 = "LE/EECS1022 Programming for Mobile Computing";
	private String cse1022Descrip = "This course provides a first exposure to object-oriented programming and enhances student understanding of key "
									+ "computing skills such as reasoning about algorithms, designing user interfaces, and working with software tools. "
									+ "It uses problem-based approach to expose the underlying concepts and an experiential laboratory to implement them.";
	
	private String cse2001 = "LE/EECS2001 Introduction to the Theory of Computation";
	private String cse2001Descrip = "Introduction to the theory of computing, including automata theory, formal languages and Turing machines; theoretical "
									+ "models and their applications in various fields of computer science.";
	
	private String cse2011 = "LE/EECS2011 Fundamentals of Data Structures";
	private String cse2011Descrip = "A study of fundamental data structures and their use in the efficient implementation of algorithms. Topics include "
									+ "abstract data types, lists, stacks, queues, trees and graphs.";
	
	private String cse2021 = "LE/EECS2021 Computer Organization";
	private String cse2021Descrip = "Introduction to computer organization and instruction set architecture, covering assembly language, "
									+ "machine language and encoding, addressing modes, single/multicycle datapaths (including functional "
									+ "units and controls), pipelining, memory segments and memory hierarchy. ";
	
	private String cse2030 = "LE/EECS2030 Advanced Object Oriented Programming";
	private String cse2030Descrip = "This course continues the separation of concern theme introduced in EECS1020 and EECS1021. While EECS1020 "
									+ "focuses on the client concern, this course focuses on the concern of the implementer. Hence, rather than "
									+ "using an API (Application Programming Interface) to build an application, the student is asked to implement a given API.";
	
	private String cse2031 = "LE/EECS2031 Software Tools";
	private String cse2031Descrip = "Tools commonly used in the software development process: the C language; shell programming; filters and pipes; "
									+ "version control systems and make; debugging and testing.";
/************************************************************************************************************************************/

/*********************************************************SCHEDULE*********************************************************************/
	private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	private String[] time = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
							"18:00", "18:30", "19:00", "19:30", "20:00"};
	private String[] location = {"LSB 112", "CLH A", "ACW 012", "ACE 110", "TEL 001", "LAS A"};
	private String[] term = {"F", "W"};
/************************************************************************************************************/	
	
/******************************METHOD**************************************/
	
	public Model(){
		
	}
	
	public String getProgName(){
		String progContent = programTitle + "\n" + "\n" + programName + "\n";
		return progContent;
	}
	
	public String getProgTitle(){
		return programTitle;
	}
	
	public String getCourseList(){
		String cList = "";
		for(int i = 0; i < courseList1.length; i++){
			cList = cList + "\n" + courseList1[i];
		}
		return cList + "\n";
	}
	
	public String getTermCourse(){
		return termCourse;
	}
	
	public String getFallTitle(){
		return fallTitle + "\n";
	}
	
	public String getWinterTitle(){
		return winterTitle + "\n";
	}
	
	public String getPersonInfo(){
		return personInfo;
	}
	
	public String courseTakeList(){
		String ctList = "";
		
		for(int i = 0; i < courseList.length; i++){
			ctList = ctList + "\n" + courseList[i];
		}
		return courseTakenTitle + "" + ctList;
	}
	
	public String getCourseName(String course){
		
		if(course.equals("EECS1001") || course.equals("eecs1001")){
			return cse1001;
		}else if(course.equals("EECS1012") || course.equals("eecs1012")){
			return cse1012;
		}else if(course.equals("EECS1019") || course.equals("eecs1019")){
			return cse1019;
		}else if(course.equals("EECS1022") || course.equals("eecs1022")){
			return cse1022;
		}else if(course.equals("EECS2001") || course.equals("eecs2001")){
			return cse2001;
		}else if(course.equals("EECS2011") || course.equals("eecs2011")){
			return cse2011;
		}else if(course.equals("EECS2021") || course.equals("eecs2021")){
			return cse2021;
		}else if(course.equals("EECS2030") || course.equals("eecs2030")){
			return cse2030;
		}else if(course.equals("EECS2031") || course.equals("eecs2031")){
			return cse2031;
		}
		
		return "";
	}
	
	public String getCourseDescrip(String courseName){

		if(courseName.equals("EECS1001") || courseName.equals("eecs1001")){
			return cse1001Descrip;
		}else if(courseName.equals("EECS1012") || courseName.equals("eecs1012")){
			return cse1012Descrip;
		}else if(courseName.equals("EECS1019") || courseName.equals("eecs1019")){
			return cse1019Descrip;
		}else if(courseName.equals("EECS1022") || courseName.equals("eecs1022")){
			return cse1022Descrip;
		}else if(courseName.equals("EECS2001") || courseName.equals("eecs2001")){
			return cse2001Descrip;
		}else if(courseName.equals("EECS2011") || courseName.equals("eecs2011")){
			return cse2011Descrip;
		}else if(courseName.equals("EECS2021") || courseName.equals("eecs2021")){
			return cse2021Descrip;
		}else if(courseName.equals("EECS2030") || courseName.equals("eecs2030")){
			return cse2030Descrip;
		}else if(courseName.equals("EECS2031") || courseName.equals("eecs2031")){
			return cse2031Descrip;
		}
		
		return "";
	}
	
	public String getTerm(){
		
		int rand = rng.nextInt(term.length);
		return term[rand];
	}
	
	public String getDay(){
		
		int rand = rng.nextInt(days.length);
		return days[rand];
	}
	
	public String getTime(){
		
		int rand = rng.nextInt(time.length);
		return time[rand];
	}
	
	public String getLocation(){
		
		int rand = rng.nextInt(location.length);
		return location[rand];
	}
	
	public void addSelected(String item){
		selectedElement.add(item);
	}
	
	public void addSelectedItem(String item){
		itemSelected.add(item);
	}
	
	public ArrayList<String> getSelected(){
		return selectedElement;
	}
	
	public String getSelectedString(){
		
		String[] data = selectedElement.toArray(new String[selectedElement.size()]);
		String cList = "";
		for(int i = 0; i < data.length; i++){
			cList = cList + "\n" + data[i];
		}
		return cList + "\n";
	}
	
	public String getCourseSchedTitle(){
		return courseScheduleTitle;
	}
}
