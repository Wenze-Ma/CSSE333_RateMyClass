import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class CoursePage {
	private JFrame myFrame;
	JButton confirmCourse = new JButton("Take");
	JPanel panelForCourse = new JPanel();
	JPanel courseToTake = new JPanel();
	String courseSelected = null;
	int courseIDSelected = 0;
	String departmentSelected = null;
	JPanel panelForDisplay = new JPanel();
	ArrayList<ArrayList<String>> courseInfo = new ArrayList<ArrayList<String>>();
	public CoursePage() {
		myFrame = new JFrame();
		myFrame.setSize(1000, 700);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("CoursePage");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();    
	}
	private void setup() {
		courseToTake.setLayout(new MigLayout());
		panelForDisplay.setLayout(new MigLayout());
    	JButton closeCourse = new JButton("Close");
    	JLabel courseLabel = new JLabel("Course Service        ");
    	JButton takeCourse = new JButton("Take Course");
    	courseToTake.setVisible(false);
        panelForCourse.setLayout(new GridBagLayout());
        panelForCourse.setSize(200, 300);
        panelForCourse.add(courseLabel);
        panelForCourse.add(takeCourse);
        panelForCourse.add(closeCourse);
  
        closeCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new RateMyClassMain();	
			}
		});        
        confirmCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CourseService cs = new CourseService();
				boolean tf = cs.addTakeCourse(UserLogIn.user, courseIDSelected);
				if(tf) {
					courseToTake.setVisible(false);
					displayCourseTaken();
					panelForDisplay.setVisible(true);
				}
			}
		});
       
        takeCourse.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(courseToTake.isVisible()) {
					courseToTake.removeAll();
					courseToTake.setVisible(false);
					displayCourseTaken();
					panelForDisplay.setVisible(true);
				}else {
					panelForDisplay.removeAll();
					panelForDisplay.setVisible(false);
					courseToTake.setVisible(true);
					takeCourseDept();
				}
			}
		});
        displayCourseTaken();
        myFrame.add(panelForCourse, BorderLayout.NORTH);
        panelForCourse.setVisible(true);
        myFrame.setVisible(true);
	}

	public void displayCourseTaken() {
		panelForDisplay.setLayout(new MigLayout());
		CourseService courseService = new CourseService();
		courseInfo = courseService.getCoursesInfoByStudent(UserLogIn.user);
		JTextField titleID = new JTextField(courseInfo.get(0).get(0), 6);
		JTextField titleName = new JTextField(courseInfo.get(0).get(1), 20);
		JTextField titleNum = new JTextField(courseInfo.get(0).get(2), 9);
		JTextField titleDept = new JTextField(courseInfo.get(0).get(3), 24);
		JTextField titlescore = new JTextField(courseInfo.get(0).get(4), 8);
		titleID.setEditable(false);
		titleName.setEditable(false);
		titleNum.setEditable(false);
		titleDept.setEditable(false);
		titlescore.setEditable(false);
		JTextField titleHolder = new JTextField();
		titleHolder.setVisible(false);
		panelForDisplay.add(titleID, "skip, split5");
		panelForDisplay.add(titleName);
		panelForDisplay.add(titleNum);
		panelForDisplay.add(titleDept);
		panelForDisplay.add(titlescore);
		panelForDisplay.add(titleHolder, "wrap");
		for (int i = 1; i < courseInfo.size(); i++) {
			JTextField tempID = new JTextField(courseInfo.get(i).get(0), 6);
			JTextField tempName = new JTextField(courseInfo.get(i).get(1), 20);
			JTextField tempNum = new JTextField(courseInfo.get(i).get(2), 9);
			JTextField tempDept = new JTextField(courseInfo.get(i).get(3), 24);
			JTextField tempscore = new JTextField(courseInfo.get(i).get(4), 8);
			int intID = Integer.parseInt(tempID.getText());
			tempID.setEditable(false);
			tempName.setEditable(false);
			tempNum.setEditable(false);
			tempDept.setEditable(false);
			tempscore.setEditable(false);
			JTextField placeHolder = new JTextField();
			placeHolder.setVisible(false);
			panelForDisplay.add(tempID, "skip, split5");
			panelForDisplay.add(tempName);
			panelForDisplay.add(tempNum);
			panelForDisplay.add(tempDept);
			panelForDisplay.add(tempscore);
			JButton drop = new JButton("DROP");
			JButton comment = new JButton("COMMENT");
			drop.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					courseService.dropTakeCourse(UserLogIn.user, intID);
					panelForDisplay.setVisible(false);
					panelForDisplay = new JPanel();
					displayCourseTaken();
				}
			});
			comment.addActionListener(new LinkActionListener(tempName.getText(), myFrame, "course"));
			panelForDisplay.add(drop);
			panelForDisplay.add(comment);
			panelForDisplay.add(placeHolder, "wrap");
		}
		myFrame.add(panelForDisplay,BorderLayout.CENTER);
		panelForDisplay.setVisible(true);
		myFrame.setVisible(true);
	}
	
	public void takeCourseDept() {
		// the selection process for course Service
		courseToTake.removeAll();
		DepartmentService ds = new DepartmentService();
		ArrayList<String> departments = ds.getDepartments();
		JComboBox departmentList = new JComboBox(parseArrayListToArray(departments));
		departmentList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				courseToTake.removeAll();
				courseToTake.add(new JLabel("Choose a department: "));
				courseToTake.add(departmentList, "wrap");
				departmentSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				myFrame.add(courseToTake, BorderLayout.CENTER);
				myFrame.setVisible(true);
				chooseCourseToTake();
			}
		});
			courseToTake.add(new JLabel("Choose a department: "));
			courseToTake.add(departmentList,"wrap");
			myFrame.add(courseToTake, BorderLayout.CENTER);
			myFrame.setVisible(true);
	}
	public void chooseCourseToTake() {
		CourseService cs = new CourseService();
		ArrayList<String> courses = cs.getCoursesByDepartment(departmentSelected);
		JComboBox courseList = new JComboBox(parseArrayListToArray(courses));
		courseList.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {

				courseToTake.add(new JLabel("Choose a course: "));
				courseToTake.add(courseList, "wrap");
				courseSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				courseIDSelected = cs.getCourseIDByNumber(courseSelected);
			}
		});
		
		courseToTake.add(new JLabel("Choose a course: "));
		courseToTake.add(courseList, "wrap");
		courseToTake.add(confirmCourse);
		myFrame.add(courseToTake, BorderLayout.CENTER);
		myFrame.setVisible(true);
	}
	public String[] parseArrayListToArray(ArrayList<String> arr) {
		String[] temp = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			temp[i] = arr.get(i);
		}
		return temp;
	}
}
