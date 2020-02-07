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

import net.miginfocom.swing.MigLayout;

public class CoursePage {
	private JFrame myFrame;
	JButton confirmCourse = new JButton("Take");
	JPanel panelForCourse = new JPanel();
	JPanel courseToTake = new JPanel();
	String courseSelected = null;
	int courseIDSelected = 0;
	String departmentSelected = null;
	private int sizeForPanel = 0;
	
	public CoursePage() {
		myFrame = new JFrame();
		myFrame.setSize(600, 700);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("CoursePage");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();    
	}
	private void setup() {
		courseToTake.setLayout(new MigLayout());
		
    	JButton closeCourse = new JButton("Close");
    	JLabel courseLabel = new JLabel("Course Service     ");
    	JButton takeCourse = new JButton("Take Course");
    	
        panelForCourse.setVisible(false);
        panelForCourse.setLayout(new GridBagLayout());
        panelForCourse.setSize(200, 300);
        
        panelForCourse.add(courseLabel);
        panelForCourse.add(closeCourse);
        panelForCourse.add(takeCourse);
        
        courseToTake.setVisible(false);
        
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
				}
			}
		});
        
        takeCourse.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(courseToTake.isVisible()) {
					courseToTake.removeAll();
					courseToTake.setVisible(false);
				}else {
					courseToTake.setVisible(true);
					takeCourseDept();
				}
			}
		});
        
        myFrame.add(panelForCourse,BorderLayout.NORTH);
        panelForCourse.setVisible(true);
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
				sizeForPanel = 2;
				departmentSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				myFrame.add(courseToTake, BorderLayout.CENTER);
				myFrame.setVisible(true);
				chooseCourseToTake();
			}
		});
			courseToTake.add(new JLabel("Choose a department: "));
			courseToTake.add(departmentList,"wrap");
			sizeForPanel += 2;
			myFrame.add(courseToTake, BorderLayout.CENTER);
			myFrame.setVisible(true);
	}
	protected void chooseCourseToTake() {
		CourseService cs = new CourseService();
		ArrayList<String> courses = cs.getCoursesByDepartment(departmentSelected);
		JComboBox courseList = new JComboBox(parseArrayListToArray(courses));
		courseList.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {

				courseToTake.add(new JLabel("Choose a course: "));
				courseToTake.add(courseList, "wrap");
				sizeForPanel += 2;
				courseSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				courseIDSelected = cs.getCourseIDByNumber(courseSelected);
			}
		});
		
		courseToTake.add(new JLabel("Choose a course: "));
		courseToTake.add(courseList, "wrap");
//		JButton print = new JButton("print");
//		print.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println(courseIDSelected);
//				
//			}
//		});
//		courseToTake.add(print);
		courseToTake.add(confirmCourse);
		sizeForPanel += 2;
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
