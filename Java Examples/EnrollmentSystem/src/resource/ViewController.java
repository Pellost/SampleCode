package resource;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/*
 * This class encapsulates a GUI, a component which is a view-controller
 * delegate. This object is an observer of the Model.
 * 
 * This GUI shows views which is fixed to the current state of the Model.
 * Changes to the view will be based on by the  state changes to the Model.
 * The view will start by showing the overview to the user with degree requirements,
 * Student information, and term courses as well as couple of tabs in order to transition to the next options.
 */

public class ViewController extends JFrame implements ActionListener{

	private static final long serialVersionUID = 2L;
	private static final boolean shouldFill = true;
	private static final boolean shouldWeightX = true;

	/**************************FIELD****************************/

	private Model theModel;

	private JTabbedPane tabInfo;

	private JScrollPane progNameScroll;

	private String[][] schedule = new String[27][2];

	private String[][] dateSchedule = new String[27][3];

	private int m = 0;
	private int s = 0;
	private int v = 0;

	// Tab Panels
	private JPanel overviewPanel;
	private JPanel enrollPanel;
	private JPanel timetablePanel;

	// Overview Content panels
	private JTextArea programNamePanel;
	private JPanel termCoursesPanel;
	private JLabel termCourseLabel;
	private JTextArea fallListPanel;
	private JTextArea winterListPanel;
	private JTextArea personalInfoPanel;

	// Enroll Content panel
	private JTextField searchBar;
	private JTextArea courseTakePanel;
	private JTextArea courseNamePanel;
	private JTextArea courseDescripPanel;
	private JList<String> courseTimePanel;
	private JList<String> enrollCartPanel;
	private Button searchButton;
	private Button addButton;
	private Button submitButton;

	// Timetable Content panel
	private JTabbedPane termScheduleTab;
	private JPanel courseSchedulePanel;
	private JTextArea fallListPanel2;
	private JTextArea winterListPanel2;
	private JPanel fallSchedulePanel;
	private JPanel winterSchedulePanel;
	private JLabel courseScheduleTermsLabel;
	private JTable winterTable;
	private JTable fallTable;
	private DefaultTableModel fallDefaultTable;

	private String[] columnNames = {"Hours", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

	private String[][] fallData = {
			{"Hours", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"},
			{"08:00-08:30"," "," "," "," "," "},
			{"08:30-09:00"," "," "," "," "," "},
			{"09:00-09:30"," "," "," "," "," "},
			{"09:30-10:00"," "," "," "," "," "},
			{"10:00-10:30"," "," "," "," "," "},
			{"10:30-11:00"," "," "," "," "," "},
			{"11:00-11:30"," "," "," "," "," "},
			{"11:30-12:00"," "," "," "," "," "},
			{"12:00-12:30"," "," "," "," "," "},
			{"12:30-13:00"," "," "," "," "," "},
			{"13:00-13:30"," "," "," "," "," "},
			{"13:30-14:00"," "," "," "," "," "},
			{"14:00-14:30"," "," "," "," "," "},
			{"14:30-15:00"," "," "," "," "," "},
			{"15:00-15:30"," "," "," "," "," "},
			{"15:30-16:00"," "," "," "," "," "},
			{"16:00-16:30"," "," "," "," "," "},
			{"16:30-17:00"," "," "," "," "," "},
			{"17:00-17:30"," "," "," "," "," "},
			{"17:30-18:00"," "," "," "," "," "},
			{"18:00-18:30"," "," "," "," "," "},
			{"18:30-19:00"," "," "," "," "," "},
			{"19:00-19:30"," "," "," "," "," "},
			{"19:30-20:00"," "," "," "," "," "},
			{"20:00-20:30"," "," "," "," "," "}
	};
	private Object[][] winterData = {
			{"Hours", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"},
			{"08:00-08:30"," "," "," "," "," "},
			{"08:30-09:00"," "," "," "," "," "},
			{"09:00-09:30"," "," "," "," "," "},
			{"09:30-10:00"," "," "," "," "," "},
			{"10:00-10:30"," "," "," "," "," "},
			{"10:30-11:00"," "," "," "," "," "},
			{"11:00-11:30"," "," "," "," "," "},
			{"11:30-12:00"," "," "," "," "," "},
			{"12:00-12:30"," "," "," "," "," "},
			{"12:30-13:00"," "," "," "," "," "},
			{"13:00-13:30"," "," "," "," "," "},
			{"13:30-14:00"," "," "," "," "," "},
			{"14:00-14:30"," "," "," "," "," "},
			{"14:30-15:00"," "," "," "," "," "},
			{"15:00-15:30"," "," "," "," "," "},
			{"15:30-16:00"," "," "," "," "," "},
			{"16:00-16:30"," "," "," "," "," "},
			{"16:30-17:00"," "," "," "," "," "},
			{"17:00-17:30"," "," "," "," "," "},
			{"17:30-18:00"," "," "," "," "," "},
			{"18:00-18:30"," "," "," "," "," "},
			{"18:30-19:00"," "," "," "," "," "},
			{"19:00-19:30"," "," "," "," "," "},
			{"19:30-20:00"," "," "," "," "," "},
			{"20:00-20:30"," "," "," "," "," "}
	};

	private JLabel titleLabel;
	private GridBagConstraints c;

	/*************************METHOD****************************/

	public ViewController(Model model){

		theModel = model;

		/*
		 * Setting basic aspects of the frame
		 */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension thisScreen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int) thisScreen.getWidth() / 2, (int) thisScreen.getHeight() / 2);
		this.setTitle(this.getClass().getName());
		this.setLocationByPlatform(true);
		//this method ask the window manager to position the frame in the
		//center of the screen
		this.setLocationRelativeTo(null);

		/*
		 * Setting up the GUI.
		 * Initialize every variable from the field.
		 */

		tabInfo = new JTabbedPane();

		// Overview Panel
		overviewPanel = new JPanel();
		programNamePanel = new JTextArea();
		termCoursesPanel = new JPanel();
		fallListPanel = new JTextArea();
		winterListPanel = new JTextArea();
		termCourseLabel = new JLabel(model.getTermCourse());
		personalInfoPanel = new JTextArea();

		// Enroll Panel
		enrollPanel = new JPanel();
		courseTakePanel = new JTextArea();
		courseNamePanel = new JTextArea();
		courseDescripPanel = new JTextArea();
		courseTimePanel = new JList<String>();
		enrollCartPanel = new JList<String>();
		searchBar = new JTextField();
		searchButton = new Button("Search");
		addButton = new Button("Add");
		submitButton = new Button("Submit");

		// Timetable Panel
		timetablePanel = new JPanel();
		courseSchedulePanel = new JPanel();
		termScheduleTab = new JTabbedPane();
		fallListPanel2 = new JTextArea();
		winterListPanel2 = new JTextArea();
		fallSchedulePanel = new JPanel();
		winterSchedulePanel = new JPanel();
		fallDefaultTable = new DefaultTableModel(fallData, columnNames);
		fallTable = new JTable(fallDefaultTable);
		winterTable = new JTable(winterData, columnNames);
		courseScheduleTermsLabel = new JLabel(theModel.getCourseSchedTitle());

		schedule = new String[27][2];

		dateSchedule = new String[27][3];

		titleLabel = new JLabel();
		c = new GridBagConstraints();

		// Setting scroll panes
		progNameScroll = new JScrollPane(programNamePanel);

		/******** modifications to the different panels *********/
		titleLabel.setText("<html><font face = Verdana size = 100> Course Enrollment </font></html>");

		programNamePanel.setEditable(false);
		programNamePanel.setWrapStyleWord(true);
		programNamePanel.setLineWrap(true);
		personalInfoPanel.setEditable(false);
		personalInfoPanel.setWrapStyleWord(true);
		personalInfoPanel.setLineWrap(true);
		termCourseLabel.setFont(new Font ("Verdana", 1, 20));

		courseTakePanel.setEditable(false);
		courseTakePanel.setWrapStyleWord(true);
		courseTakePanel.setLineWrap(true);
		courseNamePanel.setEditable(false);
		courseNamePanel.setWrapStyleWord(true);
		courseNamePanel.setLineWrap(true);
		courseDescripPanel.setEditable(false);
		courseDescripPanel.setWrapStyleWord(true);
		courseDescripPanel.setLineWrap(true);

		fallListPanel.setEditable(false);
		fallListPanel.setWrapStyleWord(true);
		fallListPanel.setLineWrap(true);
		winterListPanel.setEditable(false);
		winterListPanel.setWrapStyleWord(true);
		winterListPanel.setLineWrap(true);
		fallListPanel2.setEditable(false);
		fallListPanel2.setWrapStyleWord(true);
		fallListPanel2.setLineWrap(true);
		winterListPanel2.setEditable(false);
		winterListPanel2.setWrapStyleWord(true);
		winterListPanel2.setLineWrap(true);

		fallTable.setEnabled(false);
		winterTable.setEnabled(false);

		if(shouldFill){	
			c.fill = GridBagConstraints.HORIZONTAL;
		}
		if(shouldWeightX){
			c.weightx = 0.5;
		}


		/************ Setting the color of the Panels ************/
		overviewPanel.setBackground(Color.WHITE);
		enrollPanel.setBackground(Color.WHITE);
		timetablePanel.setBackground(Color.WHITE);

		fallListPanel.setBackground(Color.CYAN);
		winterListPanel.setBackground(Color.CYAN);

		programNamePanel.setBackground(Color.PINK);
		termCoursesPanel.setBackground(Color.PINK);
		personalInfoPanel.setBackground(Color.PINK);

		courseTakePanel.setBackground(Color.ORANGE);
		courseNamePanel.setBackground(Color.ORANGE);
		courseDescripPanel.setBackground(Color.ORANGE);
		courseTimePanel.setBackground(Color.ORANGE);
		enrollCartPanel.setBackground(Color.ORANGE);

		courseSchedulePanel.setBackground(Color.LIGHT_GRAY);
		fallListPanel2.setBackground(Color.YELLOW);
		winterListPanel2.setBackground(Color.YELLOW);
		fallSchedulePanel.setBackground(Color.LIGHT_GRAY);
		winterSchedulePanel.setBackground(Color.LIGHT_GRAY);

		/************ Setting the GridLayout of each panel ************/
		overviewPanel.setLayout(new GridBagLayout());
		enrollPanel.setLayout(new GridBagLayout());
		timetablePanel.setLayout(new GridBagLayout());
		termCoursesPanel.setLayout(new GridBagLayout());
		fallSchedulePanel.setLayout(new GridBagLayout());
		winterSchedulePanel.setLayout(new GridBagLayout());
		courseSchedulePanel.setLayout(new GridBagLayout());

		// Adding the panels to the frame

		/*
		 * Creating panels for each tab. 
		 * The first three panels are for the overview tab.
		 * The second five panels are for the enrollment tab.
		 * The third four panels are  for the timetable tab.
		 */

		/****************************FIRST TAB*********************************/

		/*
		 * There is a program panel where it describes the program as well as it shows the courses
		 * that the student will have to take for his degree.
		 * There is also a term course panel where it shows the courses that the student is currently taking.
		 * As well as the student information in another panel.
		 */

		// The panel showing the program's description as well as courses to be taken.
		programNamePanel.setText(theModel.getProgName());
		programNamePanel.setFont(programNamePanel.getFont().deriveFont(15f));
		programNamePanel.append(theModel.getCourseList());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 150;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 399;
		c.insets = new Insets(10,10,10,10);
		overviewPanel.add(progNameScroll, c);

		// Panel showing the courses for each term
		termCoursesPanel.add(termCourseLabel);
		termCoursesPanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 0;
		c.insets = new Insets(10,10,205,10);
		overviewPanel.add(termCoursesPanel, c);

		// Course taken in the fall term
		fallListPanel.setText(theModel.getFallTitle());
		fallListPanel.setFont(fallListPanel.getFont().deriveFont(15f));
		fallListPanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.18;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(50,10,10,10);
		termCoursesPanel.add(fallListPanel, c);

		// Course taken in the winter term
		winterListPanel.setText(theModel.getWinterTitle());
		winterListPanel.setFont(winterListPanel.getFont().deriveFont(15f));
		winterListPanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.18;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(50,10,10,10);
		termCoursesPanel.add(winterListPanel, c);

		// Information about the student
		personalInfoPanel.setText(theModel.getPersonInfo());
		personalInfoPanel.setFont(personalInfoPanel.getFont().deriveFont(15f));
		personalInfoPanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 40;
		c.insets = new Insets(265,10,10,10);
		overviewPanel.add(personalInfoPanel, c);

		/*****************************SECOND TAB*********************************/

		/*
		 * The enrollment tab has a panel to the left which shows the user what courses he can take for the academic year.
		 * On the right there is a search bar which allows the user to search the course and then 
		 * get a display of the course name as well as the description and the times available for that course. The user
		 * has the option of adding the course to the cart and if he is satisfied with all the courses he has in the cart then he will
		 * submit those courses and be enrolled.
		 */

		// Course to be taken panel
		courseTakePanel.setText(theModel.courseTakeList());
		courseTakePanel.setFont(courseTakePanel.getFont().deriveFont(15f));
		courseTakePanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 400;
		c.insets = new Insets(10,10,10,10);
		enrollPanel.add(courseTakePanel, c);

		// The search bar
		searchBar.setText("Search for your course");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 10;
		c.insets = new Insets(10,10,390,10);
		enrollPanel.add(searchBar, c);

		// The button for the search bar
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0.2;
		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(10,10,390,10);
		enrollPanel.add(searchButton, c);

		// The panel name of the course
		courseNamePanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(10,10,310,10);
		enrollPanel.add(courseNamePanel, c);

		// The panel description of the course
		courseDescripPanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(10,10,200,10);
		enrollPanel.add(courseDescripPanel, c);

		// The panel showing different times for the course
		courseTimePanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(40,10,10,10);
		enrollPanel.add(courseTimePanel, c);

		// The button to add course to the enrollment cart
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0.2;
		c.gridx = 3;
		c.gridy = 1;
		c.ipady = 30;
		c.insets = new Insets(40,10,10,10);
		enrollPanel.add(addButton, c);

		// The list panel showing the courses added so far
		enrollCartPanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(300,10,10,10);
		enrollPanel.add(enrollCartPanel, c);

		// The button to submit the courses added
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0.2;
		c.gridx = 3;
		c.gridy = 1;
		c.ipady = 30;
		c.insets = new Insets(300,10,10,10);
		enrollPanel.add(submitButton, c);

		/****************************THIRD TAB**********************************/
		/*
		 * In the third tab, there is a courseSchedulePanel which will list all the courses that t he user is taking in the term.
		 * The termScheduleTab is the panel where the schedules for both terms will be displayed.
		 * The last two which is the fallSchedulePanel and winterSchedulePanel will contain a table which is the schedule.
		 * Finally adding both schedule panels into the termScheduleTab.
		 */

		// The panel showing the courses for each term
		courseSchedulePanel.add(courseScheduleTermsLabel);
		courseSchedulePanel.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 450;
		c.insets = new Insets(10,10,10,10);
		timetablePanel.add(courseSchedulePanel, c);

		// The fall courses
		fallListPanel2.setText(theModel.getFallTitle());
		fallListPanel2.setFont(fallListPanel2.getFont().deriveFont(15f));
		fallListPanel2.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 1;
		c.insets = new Insets(10,10,10,10);
		courseSchedulePanel.add(fallListPanel2, c);

		// The winter courses
		winterListPanel2.setText(theModel.getWinterTitle());
		winterListPanel2.setFont(winterListPanel2.getFont().deriveFont(15f));
		winterListPanel2.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 1;
		c.insets = new Insets(10,10,10,10);
		courseSchedulePanel.add(winterListPanel2, c);

		// The tab panel for each term schedule
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 200;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 400;
		c.insets = new Insets(10,10,10,10);
		timetablePanel.add(termScheduleTab, c);

		// The fall schedule
		fallTable.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
		fallSchedulePanel.add(fallTable, c);

		// The winter schedule
		winterTable.setBorder(new LineBorder(Color.BLACK));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
		winterSchedulePanel.add(winterTable, c);

		termScheduleTab.addTab("Fall", null, (JComponent)fallSchedulePanel, "Fall");
		termScheduleTab.addTab("Winter", null, winterSchedulePanel, "Winter");

		/***********************************************************************************/
		//Tabs for the overview, enroll, and timetable tab

		tabInfo.addTab("Overview", null, (JComponent)overviewPanel, "Overview");
		tabInfo.setMnemonicAt(0, KeyEvent.VK_1);

		tabInfo.addTab("Enroll",  null, (JComponent)enrollPanel, "Enroll");
		tabInfo.setMnemonicAt(1, KeyEvent.VK_2);

		tabInfo.addTab("Timetable", null, (JComponent)timetablePanel, "Timetable");
		tabInfo.setMnemonicAt(2, KeyEvent.VK_3);

		this.getContentPane().add(titleLabel, BorderLayout.NORTH);
		this.getContentPane().add(tabInfo, BorderLayout.CENTER);

		// Action listener for each button
		searchButton.addActionListener(this);
		addButton.addActionListener(this);
		submitButton.addActionListener(this);

		this.setVisible(true);

		// this method asks the frame layout manager to size the frame so that
		// all its contents are at or above their preferred sizes
		this.pack();
		// make this component visible (do not assume that it will be visible by
		// default)
		this.setVisible(true);
	}

	@Override
	public Dimension getPreferredSize(){
		// find the dimension of the screen and a dimension that is derive one
		//half of the size
		Dimension thisScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension targetSize = new Dimension((int) (thisScreen.getWidth() / 2) + 100, (int) (thisScreen.getHeight() / 2) + 60);
		return targetSize;
	}

	// Search the name of the course
	public String searchName(){
		String name = searchBar.getText();
		return theModel.getCourseName(name);
	}

	// Search the description of the course
	public String searchDescrip(){
		String descrip = searchBar.getText();
		return theModel.getCourseDescrip(descrip);
	}

	public String[] searchTime(){

		String[] timeArr = new String[3];

		for(int i = 0; i < 3; i++){
			timeArr[i] = theModel.getDay() + " at " + theModel.getTime() + " in " + theModel.getLocation();
		}

		return timeArr;
	}

	public void settingSchedule(){
		String[] split1 = new String[2];
		String[] split;
		String split2;
		String split3;


		split1 = schedule[v][0].split(" ");
		split = schedule[v][1].split("at ");
		split2 = split[0];
		split3 = split[1].substring(0, 5);
		dateSchedule[v][0] = split1[0]; // Name of course
		dateSchedule[v][1] = split2; // Day
		dateSchedule[v][2] = split3; // Time
		System.out.println(dateSchedule[v][0]);
		System.out.println(dateSchedule[v][1]);
		System.out.println(dateSchedule[v][2]);
		v++;

	}


	/*
	 * This method checks the enrollment cart and based on what the student submitted, it will build the schedule.
	 * This method will split the input and place the course code according to the given day and time into the correct cell.
	 */
	public void scheduleDate(){

		if(dateSchedule[s][1].equals("Monday ")){
			if(dateSchedule[s][2].equals("08:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 1, 1);
			}else if(dateSchedule[s][2].equals("08:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 2, 1);
			}else if(dateSchedule[s][2].equals("09:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 3, 1);
			}else if(dateSchedule[s][2].equals("09:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 4, 1);
			}else if(dateSchedule[s][2].equals("10:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 5, 1);
			}else if(dateSchedule[s][2].equals("10:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 6, 1);
			}else if(dateSchedule[s][2].equals("11:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 7, 1);
			}else if(dateSchedule[s][2].equals("11:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 8, 1);
			}else if(dateSchedule[s][2].equals("12:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 9, 1);
			}else if(dateSchedule[s][2].equals("12:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 10, 1);
			}else if(dateSchedule[s][2].equals("13:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 11, 1);
			}else if(dateSchedule[s][2].equals("13:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 12, 1);
			}else if(dateSchedule[s][2].equals("14:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 13, 1);
			}else if(dateSchedule[s][2].equals("14:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 14, 1);
			}else if(dateSchedule[s][2].equals("15:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 15, 1);
			}else if(dateSchedule[s][2].equals("15:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 16, 1);
			}else if(dateSchedule[s][2].equals("16:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 17, 1);
			}else if(dateSchedule[s][2].equals("16:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 18, 1);
			}else if(dateSchedule[s][2].equals("17:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 19, 1);
			}else if(dateSchedule[s][2].equals("17:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 20, 1);
			}else if(dateSchedule[s][2].equals("18:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 21, 1);
			}else if(dateSchedule[s][2].equals("18:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 22, 1);
			}else if(dateSchedule[s][2].equals("19:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 23, 1);
			}else if(dateSchedule[s][2].equals("19:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 24, 1);
			}else if(dateSchedule[s][2].equals("20:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 25, 1);
			}
		}else if(dateSchedule[s][1].equals("Tuesday ")){
			if(dateSchedule[s][2].equals("08:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 1, 2);
			}else if(dateSchedule[s][2].equals("08:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 2, 2);
			}else if(dateSchedule[s][2].equals("09:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 3, 2);
			}else if(dateSchedule[s][2].equals("09:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 4, 2);
			}else if(dateSchedule[s][2].equals("10:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 5, 2);
			}else if(dateSchedule[s][2].equals("10:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 6, 2);
			}else if(dateSchedule[s][2].equals("11:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 7, 2);
			}else if(dateSchedule[s][2].equals("11:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 8, 2);
			}else if(dateSchedule[s][2].equals("12:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 9, 2);
			}else if(dateSchedule[s][2].equals("12:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 10, 2);
			}else if(dateSchedule[s][2].equals("13:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 11, 2);
			}else if(dateSchedule[s][2].equals("13:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 12, 2);
			}else if(dateSchedule[s][2].equals("14:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 13, 2);
			}else if(dateSchedule[s][2].equals("14:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 14, 2);
			}else if(dateSchedule[s][2].equals("15:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 15, 2);
			}else if(dateSchedule[s][2].equals("15:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 16, 2);
			}else if(dateSchedule[s][2].equals("16:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 17, 2);
			}else if(dateSchedule[s][2].equals("16:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 18, 2);
			}else if(dateSchedule[s][2].equals("17:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 19, 2);
			}else if(dateSchedule[s][2].equals("17:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 20, 2);
			}else if(dateSchedule[s][2].equals("18:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 21, 2);
			}else if(dateSchedule[s][2].equals("18:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 22, 2);
			}else if(dateSchedule[s][2].equals("19:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 23, 2);
			}else if(dateSchedule[s][2].equals("19:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 24, 2);
			}else if(dateSchedule[s][2].equals("20:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 25, 2);
			}
		}else if(dateSchedule[s][1].equals("Wednesday ")){
			if(dateSchedule[s][2].equals("08:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 1, 3);
			}else if(dateSchedule[s][2].equals("08:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 2, 3);
			}else if(dateSchedule[s][2].equals("09:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 3, 3);
			}else if(dateSchedule[s][2].equals("09:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 4, 3);
			}else if(dateSchedule[s][2].equals("10:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 5, 3);
			}else if(dateSchedule[s][2].equals("10:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 6, 3);
			}else if(dateSchedule[s][2].equals("11:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 7, 3);
			}else if(dateSchedule[s][2].equals("11:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 8, 3);
			}else if(dateSchedule[s][2].equals("12:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 9, 3);
			}else if(dateSchedule[s][2].equals("12:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 10, 3);
			}else if(dateSchedule[s][2].equals("13:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 11, 3);
			}else if(dateSchedule[s][2].equals("13:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 12, 3);
			}else if(dateSchedule[s][2].equals("14:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 13, 3);
			}else if(dateSchedule[s][2].equals("14:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 14, 3);
			}else if(dateSchedule[s][2].equals("15:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 15, 3);
			}else if(dateSchedule[s][2].equals("15:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 16, 3);
			}else if(dateSchedule[s][2].equals("16:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 17, 3);
			}else if(dateSchedule[s][2].equals("16:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 18, 3);
			}else if(dateSchedule[s][2].equals("17:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 19, 3);
			}else if(dateSchedule[s][2].equals("17:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 20, 3);
			}else if(dateSchedule[s][2].equals("18:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 21, 3);
			}else if(dateSchedule[s][2].equals("18:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 22, 3);
			}else if(dateSchedule[s][2].equals("19:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 23, 3);
			}else if(dateSchedule[s][2].equals("19:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 24, 3);
			}else if(dateSchedule[s][2].equals("20:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 25, 3);
			}
		}else if(dateSchedule[s][1].equals("Thursday ")){
			if(dateSchedule[s][2].equals("08:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 1, 4);
			}else if(dateSchedule[s][2].equals("08:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 2, 4);
			}else if(dateSchedule[s][2].equals("09:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 3, 4);
			}else if(dateSchedule[s][2].equals("09:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 4, 4);
			}else if(dateSchedule[s][2].equals("10:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 5, 4);
			}else if(dateSchedule[s][2].equals("10:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 6, 4);
			}else if(dateSchedule[s][2].equals("11:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 7, 4);
			}else if(dateSchedule[s][2].equals("11:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 8, 4);
			}else if(dateSchedule[s][2].equals("12:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 9, 4);
			}else if(dateSchedule[s][2].equals("12:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 10, 4);
			}else if(dateSchedule[s][2].equals("13:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 11, 4);
			}else if(dateSchedule[s][2].equals("13:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 12, 4);
			}else if(dateSchedule[s][2].equals("14:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 13, 4);
			}else if(dateSchedule[s][2].equals("14:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 14, 4);
			}else if(dateSchedule[s][2].equals("15:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 15, 4);
			}else if(dateSchedule[s][2].equals("15:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 16, 4);
			}else if(dateSchedule[s][2].equals("16:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 17, 4);
			}else if(dateSchedule[s][2].equals("16:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 18, 4);
			}else if(dateSchedule[s][2].equals("17:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 19, 4);
			}else if(dateSchedule[s][2].equals("17:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 20, 4);
			}else if(dateSchedule[s][2].equals("18:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 21, 4);
			}else if(dateSchedule[s][2].equals("18:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 22, 4);
			}else if(dateSchedule[s][2].equals("19:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 23, 4);
			}else if(dateSchedule[s][2].equals("19:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 24, 4);
			}else if(dateSchedule[s][2].equals("20:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 25, 4);
			}
		}else if(dateSchedule[s][1].equals("Friday ")){
			if(dateSchedule[s][2].equals("08:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 1, 5);
			}else if(dateSchedule[s][2].equals("08:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 2, 5);
			}else if(dateSchedule[s][2].equals("09:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 3, 5);
			}else if(dateSchedule[s][2].equals("09:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 4, 5);
			}else if(dateSchedule[s][2].equals("10:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 5, 5);
			}else if(dateSchedule[s][2].equals("10:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 6, 5);
			}else if(dateSchedule[s][2].equals("11:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 7, 5);
			}else if(dateSchedule[s][2].equals("11:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 8, 5);
			}else if(dateSchedule[s][2].equals("12:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 9, 5);
			}else if(dateSchedule[s][2].equals("12:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 10, 5);
			}else if(dateSchedule[s][2].equals("13:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 11, 5);
			}else if(dateSchedule[s][2].equals("13:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 12, 5);
			}else if(dateSchedule[s][2].equals("14:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 13, 5);
			}else if(dateSchedule[s][2].equals("14:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 14, 5);
			}else if(dateSchedule[s][2].equals("15:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 15, 5);
			}else if(dateSchedule[s][2].equals("15:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 16, 5);
			}else if(dateSchedule[s][2].equals("16:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 17, 5);
			}else if(dateSchedule[s][2].equals("16:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 18, 5);
			}else if(dateSchedule[s][2].equals("17:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 19, 5);
			}else if(dateSchedule[s][2].equals("17:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 20, 5);
			}else if(dateSchedule[s][2].equals("18:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 21, 5);
			}else if(dateSchedule[s][2].equals("18:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 22, 5);
			}else if(dateSchedule[s][2].equals("19:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 23, 5);
			}else if(dateSchedule[s][2].equals("19:30")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 24, 5);
			}else if(dateSchedule[s][2].equals("20:00")){
				fallDefaultTable.setValueAt(dateSchedule[s][0], 25, 5);
			}

		}
		s++;
	}
	
/*	public void colorSetup(int row, int col){
		Component comp = fallTable.prepareRenderer(fallTable.getCellRenderer(row, col), row, col);
		if(fallTable.getValueAt(row, col).equals("LE/EECS1001")){
			comp.setBackground(Color.YELLOW);
			fallTable.getcell
		}else{
			comp.setBackground(Color.WHITE);
		}
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(searchButton)){

			courseNamePanel.setText(searchName());
			courseDescripPanel.setText(searchDescrip());
			if(!courseNamePanel.getText().equals("")){
			courseTimePanel.setListData(searchTime());
			}else{
				courseTimePanel.setListData(new String[3]);
			}
		}else if(e.getSource().equals(addButton)){

			for(int i = 0; i < courseTimePanel.getSelectedValuesList().size(); i++){
				String element = searchName();
				if(!theModel.getSelected().contains(element))
					theModel.addSelected(element);

				while(true){
					schedule[m][0] = searchName();
					schedule[m][1] = courseTimePanel.getSelectedValuesList().get(i);
					System.out.println(schedule[m][0]);
					m++;
					break;
				}
			}

			String[] data = theModel.getSelected().toArray(new String[0]);
			enrollCartPanel.setListData(data);

		}else if(e.getSource().equals(submitButton)){

			if(!fallListPanel.getText().toString().contains(theModel.getSelectedString())){
				fallListPanel.append(theModel.getSelectedString());
				fallListPanel2.append(theModel.getSelectedString());
				v = 0;
				for(int n = 0; n < m; n++){
					settingSchedule();
				}
				for(int d = 0; d < m; d++){
					scheduleDate();
				}
				/*for(int p = 0; p < fallData.length; p++){
					for(int u = 0; u < fallData[p].length; u++){
						colorSetup(p, u);
					}
				}*/
			}
		}
	}
}
