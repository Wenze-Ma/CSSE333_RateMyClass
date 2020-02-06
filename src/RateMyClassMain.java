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
import javax.swing.JTextArea;
import javax.swing.JTextField;


import javafx.scene.layout.Border;
import net.miginfocom.swing.MigLayout;

public class RateMyClassMain {
	private JFrame myFrame;
	JPanel panelForDisplay = new JPanel();
	JButton closeSearching = new JButton("Close");
	JButton postComment = new JButton("Post a new comment");
	JPanel panelForPost = new JPanel();
	JPanel panelForCourse = new JPanel();
//	JPanel courseDisplay = new JPanel();
	JButton confirmPost = new JButton("Post");
	JButton CourseService = new JButton("Course");
	
	JButton displayAll = new JButton("Display");
	JButton Filter = new JButton("Filter");
	JButton closeDisplay = new JButton("Close");
	
	private JTextField searchField;
	private JTextField courseName = new JTextField(20);
	private JTextField score = new JTextField("Score", 8);
	private JTextField comment = new JTextField("Comment", 8);
	private JTextField author = new JTextField("Author", 8);
	private JTextField date = new JTextField("Date", 8);
	private JTextField ProfessorFilter = new JTextField("Professor", 8);
	private JTextField scoreFilter = new JTextField("Score",8);

	JTextArea writtenComment = new JTextArea(5,25);
	String departmentSelected = null;
	String courseSelected = null;
	String scoreSelecetd = "1";
	
	private int sizeForPanel = 0;
	
	
	public RateMyClassMain() {
		this.myFrame = new JFrame();
		myFrame.setSize(1500, 1000);
		myFrame.setTitle("Rate My Class");
	    myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelForPost.setLayout(new MigLayout());
        
        
        //panel for course
    	JButton closeCourse = new JButton("Close");
    	JLabel courseLabel = new JLabel("Course Service     ");
    	JButton takeCourse = new JButton("Take Course");
        panelForCourse.setVisible(false);
        panelForCourse.setLayout(new GridBagLayout());
        panelForCourse.setSize(200, 300);
        panelForCourse.add(courseLabel);
        panelForCourse.add(closeCourse);
        closeCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelForCourse.setVisible(false);
			}
		});
        panelForCourse.add(takeCourse, 2);
        takeCourse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
//      panelForCourse.add(courseDisplay);

		
        
        //Panel for Search
        JPanel panelForSearch = new JPanel();
		panelForSearch.setLayout(new GridBagLayout());
		panelForSearch.setSize(200, 300);		
		JLabel searchLabel = new JLabel("Class Search: ");
		panelForSearch.add(searchLabel);
		
		searchField = new JTextField(40);
		panelForSearch.add(searchField);
		
		postComment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelForPost.setVisible(true);

				chooseDepartment();

			}
		});
		
		JButton searchButton = new JButton("Submit");
		panelForSearch.add(searchButton);
		panelForSearch.add(postComment);
		panelForSearch.add(CourseService);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayComment();
				panelForDisplay.setVisible(true);
				postComment.setVisible(false);
			}
		});
		
		closeSearching.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				panelForDisplay.setVisible(false);
				postComment.setVisible(true);
			}
		});
		CourseService.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(panelForCourse.isVisible()) {
					panelForCourse.setVisible(false);
				}else {
					panelForCourse.setVisible(true);
				}				
			}
		});
		
		confirmPost.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				CommentService cs = new CommentService();
				if (cs.addComment(writtenComment.getText(), Integer.parseInt(scoreSelecetd), UserLogIn.user, courseSelected, "t7")) {
					panelForPost.setVisible(false);
				}
			}
		});
		
		myFrame.add(panelForSearch, BorderLayout.NORTH);
		myFrame.add(panelForCourse, BorderLayout.SOUTH);
		myFrame.setVisible(true);
		
		//
		//
		//Panel For Filter (Not Finished)
		//
		//
		//
		JPanel panelForFilter = new JPanel();
		panelForFilter.setLayout(new GridBagLayout());
		panelForSearch.setSize(200, 300);		
		JLabel scoreFilterLabel = new JLabel("Score Greater than: ");
		panelForFilter.add(scoreFilterLabel);
		JLabel professorFilterLabel = new JLabel("Professor: ");
		panelForFilter.add(professorFilterLabel);
		
		searchField = new JTextField(40);
		panelForSearch.add(searchField);
		
	}
	


	public void displayComment() {
		panelForDisplay.setLayout(new MigLayout());
		courseName.setEditable(false);
		score.setEditable(false);
		comment.setEditable(false);
		author.setEditable(false);
		date.setEditable(false);
		courseName.setText(searchField.getText());
		panelForDisplay.add(new JLabel("Course Name:"));
		panelForDisplay.add(courseName);
		panelForDisplay.add(closeSearching, "wrap");
		panelForDisplay.add(score, "skip, split4");
		panelForDisplay.add(comment);
		panelForDisplay.add(author);
		panelForDisplay.add(date, "wrap");
		
		ArrayList<ArrayList<String>> re = getComments();
		
		for(int i = 0; i < re.size(); i++) {
			JTextField tempScore = new JTextField(re.get(i).get(1), 8);
			JTextField tempComment = new JTextField(re.get(i).get(0), 8);
			JTextField tempAuthor = new JTextField(re.get(i).get(6), 8);
			JTextField tempDate = new JTextField(re.get(i).get(2), 8);
//			JTextArea tempScore = new JTextArea(re.get(i).get(1), 8, 3);
//			JTextArea tempComment = new JTextArea(re.get(i).get(0), 8, 3);
//			JTextArea tempAuthor = new JTextArea(re.get(i).get(6), 8, 3);
//			JTextArea tempDate = new JTextArea(re.get(i).get(2), 8, 3);
			tempScore.setEditable(false);
			tempComment.setEditable(false);
			tempAuthor.setEditable(false);
			tempDate.setEditable(false);
			
			panelForDisplay.add(tempScore, "skip, split4");
			panelForDisplay.add(tempComment);
			panelForDisplay.add(tempAuthor);
			panelForDisplay.add(tempDate, "wrap");

		}
		myFrame.add(panelForDisplay, BorderLayout.CENTER);
		myFrame.setVisible(true);
	}
	
	public ArrayList<ArrayList<String>> getComments() {
		CommentService cs = new CommentService();
		return cs.getComment(searchField.getText());
	}

	public void chooseDepartment() {

		panelForPost.removeAll();
		DepartmentService ds = new DepartmentService();
		ArrayList<String> departments = ds.getDepartments();
		JComboBox departmentList = new JComboBox(parseArrayListToArray(departments));
		
		departmentList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelForPost.removeAll();
				panelForPost.add(new JLabel("Choose a department: "));
				panelForPost.add(departmentList, "wrap");
				sizeForPanel = 2;
				departmentSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				myFrame.add(panelForPost, BorderLayout.CENTER);
				myFrame.setVisible(true);
				chooseCourse();

			}
		});
		panelForPost.add(new JLabel("Choose a department: "));
		panelForPost.add(departmentList, "wrap");
		sizeForPanel += 2;
		myFrame.add(panelForPost, BorderLayout.CENTER);
		myFrame.setVisible(true);
	}
	
	public void chooseCourse() {

		CourseService cs = new CourseService();
		ArrayList<String> courses = cs.getCoursesByDepartment(departmentSelected);
		JComboBox courseList = new JComboBox(parseArrayListToArray(courses));
		
		courseList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = sizeForPanel - 1; i > 1; i--){
					panelForPost.remove(i);
					sizeForPanel--;
				}
				panelForPost.add(new JLabel("Choose a course: "));
				panelForPost.add(courseList, "wrap");
				sizeForPanel += 2;
				courseSelected = ((JComboBox) e.getSource()).getSelectedItem().toString();
				addComment();

			}
		});
		
		panelForPost.add(new JLabel("Choose a course: "));
		panelForPost.add(courseList, "wrap");
		sizeForPanel += 2;
		myFrame.add(panelForPost, BorderLayout.CENTER);
		myFrame.setVisible(true);
	}
	
	public void addComment() {
		panelForPost.add(new JLabel("Choose a score: "));
		String[] scores = {"1", "2", "3", "4", "5"};
		JComboBox scoreList = new JComboBox(scores);
		panelForPost.add(scoreList, "wrap");
		
		scoreList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreSelecetd = ((JComboBox) e.getSource()).getSelectedItem().toString();
			}
		});
		
		panelForPost.add(new JLabel("Comment: "));
		panelForPost.add(writtenComment, "wrap");
		panelForPost.add(confirmPost, "skip2");
		sizeForPanel += 5;
		myFrame.add(panelForPost, BorderLayout.CENTER);
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
