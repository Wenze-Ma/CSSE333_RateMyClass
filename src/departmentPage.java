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

public class departmentPage {
	private JFrame myFrame;
	JPanel panelForDept = new JPanel();
	JPanel courseOfDept = new JPanel();
	String departmentSelected = null;
	JPanel profOfCourse = new JPanel();
	JButton back  = new JButton("Go back to main page");
	ArrayList<ArrayList<String>> courseInfo = new ArrayList<ArrayList<String>>();
	public departmentPage() {
		myFrame = new JFrame();
		myFrame.setSize(1000, 700);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Department Page");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();    
	}
	private void setup() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new RateMyClassMain();
			}
		});
		chooseDept();
		myFrame.add(back,BorderLayout.SOUTH);
		panelForDept.setVisible(true);
        myFrame.setVisible(true);
	}
	public void chooseDept() {
		// the selection process for course Service
		panelForDept.removeAll();
		panelForDept.setLayout(new MigLayout());
		DepartmentService ds = new DepartmentService();
		ArrayList<String> departments = ds.getDepartments();
		JComboBox departmentList = new JComboBox(parseArrayListToArray(departments));
		departmentList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelForDept.removeAll();
				panelForDept.add(new JLabel("Choose a department: "));
				panelForDept.add(departmentList, "wrap");
				departmentSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				displayCourseOfDepart();
				myFrame.add(panelForDept, BorderLayout.NORTH);
				myFrame.setVisible(true);	
			}
		});
			panelForDept.add(new JLabel("Choose a department: "));
			panelForDept.add(departmentList,"wrap");
			myFrame.add(panelForDept, BorderLayout.NORTH);
			myFrame.setVisible(true);
	}
	protected void displayCourseOfDepart() {
		// TODO Auto-generated method stub
		courseOfDept.setVisible(false);
		courseOfDept = new JPanel();
		courseOfDept.setLayout(new MigLayout());
		CourseService courseService = new CourseService();
		courseInfo = courseService.getCoursesInfoByDeptName(this.departmentSelected);
		JTextField titleID = new JTextField(courseInfo.get(0).get(0), 6);
		JTextField titleName = new JTextField(courseInfo.get(0).get(1), 20);
		JTextField titleNum = new JTextField(courseInfo.get(0).get(2), 9);
		JTextField titlescore = new JTextField(courseInfo.get(0).get(3), 8);
		titleID.setEditable(false);
		titleName.setEditable(false);
		titleNum.setEditable(false);
		titlescore.setEditable(false);
		JTextField titleHolder = new JTextField();
		titleHolder.setVisible(false);
		courseOfDept.add(titleID, "skip, split4");
		courseOfDept.add(titleName);
		courseOfDept.add(titleNum);
		courseOfDept.add(titlescore);
		courseOfDept.add(titleHolder, "wrap");
		for (int i = 1; i < courseInfo.size(); i++) {
			JTextField tempID = new JTextField(courseInfo.get(i).get(0), 6);
			JTextField tempName = new JTextField(courseInfo.get(i).get(1), 20);
			JTextField tempNum = new JTextField(courseInfo.get(i).get(2), 9);
			JTextField tempscore = new JTextField(courseInfo.get(i).get(3), 8);
			tempID.setEditable(false);
			tempName.setEditable(false);
			tempNum.setEditable(false);
			tempscore.setEditable(false);
			JTextField placeHolder = new JTextField();
			placeHolder.setVisible(false);
			courseOfDept.add(tempID, "skip, split4");
			courseOfDept.add(tempName);
			courseOfDept.add(tempNum);
			courseOfDept.add(tempscore);
			JButton comment = new JButton("COMMENT");
			comment.addActionListener(new LinkActionListener(tempName.getText(), myFrame, "dept"));
			courseOfDept.add(comment);
			courseOfDept.add(placeHolder, "wrap");
		}
		myFrame.add(courseOfDept,BorderLayout.CENTER);
		courseOfDept.setVisible(true);
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