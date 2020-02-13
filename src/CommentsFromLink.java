import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
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

public class CommentsFromLink {
	
	private String courseName;
	private JFrame myFrame;
	private JPanel panelForDisplay = new JPanel();
	private JPanel esc = new JPanel();
	JButton Filter = new JButton("Filter");
	JButton close = new JButton("Back to Major info");
	ArrayList<ArrayList<String>> re = new ArrayList<ArrayList<String>>();
	
	private JTextField score = new JTextField("Score", 4);
	private JTextField comment = new JTextField("Comment", 8);
	private JTextField author = new JTextField("Author", 8);
	private JTextField date = new JTextField("Date", 8);
	private JTextField deptFilter = new JTextField(8);
	private JTextField scoreFilter = new JTextField(8);
	
	
	
	public CommentsFromLink(String courseName) {
		this.courseName = courseName;
		myFrame = new JFrame();
		myFrame.setSize(1000, 720);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Course Comments");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayComment();
	}

	public void initPanelDisplay() {
		panelForDisplay.setLayout(new MigLayout());
		JLabel deptLabel = new JLabel("Department     ");
		JLabel scoreLabel = new JLabel("Score Greater than     ");
		panelForDisplay.add(new JLabel("Course Name:"));
		panelForDisplay.add(new JLabel(courseName),"wrap, split");
		panelForDisplay.add(deptLabel);
		panelForDisplay.add(deptFilter);
		panelForDisplay.add(scoreLabel);
		panelForDisplay.add(scoreFilter);
		panelForDisplay.add(Filter,"wrap");
		panelForDisplay.add(score, "skip, split4");
		panelForDisplay.add(comment);
		panelForDisplay.add(author);
		panelForDisplay.add(date, "wrap");
		esc.add(close);
		
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
				esc.setVisible(false);
				esc = new JPanel();
				esc.setVisible(true);
				initPanelDisplay();
				re = filterComments();
				printRe();
			}

		});
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new MajorInfo();
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
		myFrame.add(esc, BorderLayout.SOUTH);
		myFrame.setVisible(true);

	}
	
	public ArrayList<ArrayList<String>> getComments() {
		CommentService cs = new CommentService();
		return cs.getComment(courseName, null);
	}
	
	protected ArrayList<ArrayList<String>> filterComments() {
		CommentService cs = new CommentService();
		return cs.getCommentByScoreOrDept(scoreFilter.getText(), deptFilter.getText(), courseName,
				null);
	}

}
