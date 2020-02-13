import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.scene.layout.Border;
import javafx.scene.text.Font;
import net.miginfocom.swing.MigLayout;

public class RateMyClassMain {
	private JFrame myFrame;
	JPanel panelForDisplay = new JPanel();
	JButton closeSearching = new JButton("Close");
	JButton postComment = new JButton("Post a new comment");
	JPanel panelForPost = new JPanel();
	JButton confirmPost = new JButton("Post");
	JButton CourseService = new JButton("Course");
	JButton Filter = new JButton("Filter");

	private JTextField searchField;
	private JTextField courseName = new JTextField(20);
	private JTextField score = new JTextField("Score", 4);
	private JTextField comment = new JTextField("Comment", 8);
	private JTextField author = new JTextField("Author", 8);
	private JTextField date = new JTextField("Date", 8);
	private JTextField deptFilter = new JTextField(8);
	private JTextField scoreFilter = new JTextField(8);
	private JTextField professorSearch = new JTextField(20);

	JTextArea writtenComment = new JTextArea(5, 25);
	String departmentSelected = null;
	String courseSelected = null;
	String scoreSelecetd = "1";
	int courseIDSelected = 0;

	private int sizeForPanel = 0;
	ArrayList<ArrayList<String>> re = new ArrayList<ArrayList<String>>();

	public RateMyClassMain() {
		this.myFrame = new JFrame();
		myFrame.setSize(1500, 1000);
		myFrame.setTitle("Rate My Class");
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelForPost.setLayout(new MigLayout());


		// Panel for Search
		JPanel panelForSearch = new JPanel();
		panelForSearch.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		panelForSearch.setSize(200, 300);
		JLabel searchLabel = new JLabel("Class Search: ");
		panelForSearch.add(searchLabel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;

		searchField = new JTextField(40);
		panelForSearch.add(searchField,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;

		postComment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelForPost.setVisible(true);
				chooseDepartment();

			}
		});

		JButton searchButton = new JButton("Submit");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		panelForSearch.add(searchButton,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 0;
		panelForSearch.add(postComment,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 0;
		panelForSearch.add(CourseService,c);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelForDisplay.setVisible(false);
				panelForDisplay = new JPanel();
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
				panelForDisplay = new JPanel();
			}
		});
		CourseService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new CoursePage();
			}
		});

		confirmPost.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CommentService cs = new CommentService();
				if (cs.addComment(writtenComment.getText(), Integer.parseInt(scoreSelecetd), UserLogIn.user,
						courseSelected, "prof1")) {
					panelForPost.setVisible(false);
				}
			}
		});

		JButton logout = new JButton("Log Out");
		
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new UserLogIn();
			}
		});

		JButton profile = new JButton("My Account");
		profile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new Profile();
			}
		});
		
		JButton major = new JButton("Major Info");
		major.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new MajorInfo();
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 0;
		panelForSearch.add(logout,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 0;
		panelForSearch.add(profile,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 8;
		c.gridy = 0;
		
		panelForSearch.add(major,c);
		
		panelForSearch.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		panelForSearch.add(new JLabel("Professor Search:    "),c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		panelForSearch.add(professorSearch,c);

		myFrame.add(panelForSearch, BorderLayout.NORTH);
		myFrame.setVisible(true);

	}

	public void initPanelDisplay() {
		panelForDisplay.setLayout(new MigLayout());
		courseName.setEditable(false);
		score.setEditable(false);
		comment.setEditable(false);
		author.setEditable(false);
		date.setEditable(false);
		courseName.setText(searchField.getText());
		JLabel deptLabel = new JLabel("Department     ");
		JLabel scoreLabel = new JLabel("Score Greater than     ");
		panelForDisplay.add(new JLabel("Course Name:"));
		panelForDisplay.add(courseName);
		panelForDisplay.add(deptLabel);
		panelForDisplay.add(deptFilter);
		panelForDisplay.add(scoreLabel);
		panelForDisplay.add(scoreFilter);
		panelForDisplay.add(Filter);
		panelForDisplay.add(closeSearching, "wrap");
		panelForDisplay.add(score, "skip, split4");
		panelForDisplay.add(comment);
		panelForDisplay.add(author);
		panelForDisplay.add(date, "wrap");
	}

	public void displayComment() {
		initPanelDisplay();

		this.re = getComments();

		Filter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelForDisplay.setVisible(false);
				panelForDisplay = new JPanel();
				panelForDisplay.setVisible(true);
				initPanelDisplay();
				re = filterComments();
				printRe();
			}

		});

		printRe();

	}

	public void printRe() {

		for (int i = 0; i < re.size(); i++) {
			String[] a = new String[] { "1", "2", "3", "4", "5" };
			JComboBox tempScore = new JComboBox(a);
			tempScore.setSelectedItem(re.get(i).get(1));
			tempScore.setEnabled(false);
			JTextField tempComment = new JTextField(re.get(i).get(0), 8);
			JTextField tempAuthor = new JTextField(re.get(i).get(6), 8);
			JTextField tempDate = new JTextField(re.get(i).get(2), 8);

			int commentID = Integer.parseInt(re.get(i).get(5));
			tempComment.setEditable(false);
			tempAuthor.setEditable(false);
			tempDate.setEditable(false);

			JTextField placeHolder = new JTextField();
			placeHolder.setVisible(false);

			panelForDisplay.add(tempScore, "skip, split4");
			panelForDisplay.add(tempComment);
			panelForDisplay.add(tempAuthor);
			panelForDisplay.add(tempDate);
			
			JButton view = new JButton("View");
			panelForDisplay.add(view);
			view.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new CommentDetail(commentID + "", courseName.getText(), tempComment.getText(), tempAuthor.getText(), tempDate.getText(), tempScore.getSelectedItem().toString());
				}
			});

			if (UserLogIn.user.equals(tempAuthor.getText())) {
				CommentService cs = new CommentService();
				JButton edit = new JButton("Edit");
				JButton delete = new JButton("Delete");
				panelForDisplay.add(edit);
				panelForDisplay.add(delete);
				delete.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cs.deleteComment(UserLogIn.user, commentID);
						panelForDisplay.setVisible(false);
						postComment.setVisible(true);
						panelForDisplay = new JPanel();
						displayComment();
					}
				});
				edit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tempComment.setEditable(true);
						tempScore.setEnabled(true);
						edit.setText("Post");
						edit.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								cs.editComment(UserLogIn.user, commentID, tempComment.getText(),
										Integer.parseInt(tempScore.getSelectedItem().toString()));
								panelForDisplay.setVisible(false);
								postComment.setVisible(true);
								panelForDisplay = new JPanel();
								displayComment();
							}
						});
					}
				});
			}
			panelForDisplay.add(placeHolder, "wrap");

		}
		myFrame.add(panelForDisplay, BorderLayout.CENTER);
		myFrame.setVisible(true);

	}

	protected ArrayList<ArrayList<String>> filterComments() {
		CommentService cs = new CommentService();
		return cs.getCommentByScoreOrDept(scoreFilter.getText(), deptFilter.getText(), searchField.getText(),
				professorSearch.getText());
	}

	public ArrayList<ArrayList<String>> getComments() {
		CommentService cs = new CommentService();
		return cs.getComment(searchField.getText(), professorSearch.getText());
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
				for (int i = sizeForPanel - 1; i > 1; i--) {
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
		String[] scores = { "1", "2", "3", "4", "5" };
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

	public static String[] parseArrayListToArray(ArrayList<String> arr) {
		String[] temp = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			temp[i] = arr.get(i);
		}
		return temp;
	}
}
