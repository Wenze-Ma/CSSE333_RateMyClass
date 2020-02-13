import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class CommentDetail {
	private JFrame myFrame;
	private String commentID;
	private String courseName;
	private String comment;
	private String author;
	private String date;
	private String score;
	private String professor;
	
	public CommentDetail(String commentID, String courseName, String comment, String author, String date, String score, String professor) {
		myFrame = new JFrame();
		myFrame.setSize(700, 720);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Comment Detail");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.commentID = commentID;
        this.courseName = courseName;
        this.comment = comment;
        this.author = author;
        this.date = date;
        this.score = score;
        this.professor = professor;
        setup();
	}

	private void setup() {
		JPanel panelContent = new JPanel();
		panelContent.setLayout(new MigLayout());
		
		JLabel courseNameLabel = new JLabel("Course Name: ");
		JLabel courseName = new JLabel(this.courseName);
		JLabel scoreLabel = new JLabel("Score: ");
		JLabel score = new JLabel(this.score);
		JLabel commentLabel = new JLabel("Comment: ");
		JTextArea comment = new JTextArea(5, 25);
		JLabel authorLabel = new JLabel("Post by: ");
		JLabel author = new JLabel(this.author);
		JLabel dateLabel =new JLabel("Date: ");
		JLabel date = new JLabel(this.date);
		JLabel profLabel =new JLabel("Taken with: ");
		JLabel prof = new JLabel(this.professor);
		
		comment.setLineWrap( true );
		comment.setWrapStyleWord( true );
		comment.setText(this.comment);
		comment.setEditable(false);
		
		panelContent.add(courseNameLabel);
		panelContent.add(courseName, "wrap");
		panelContent.add(scoreLabel);
		panelContent.add(score, "wrap");
		panelContent.add(commentLabel);
		panelContent.add(comment, "wrap");
		panelContent.add(authorLabel);
		panelContent.add(author, "wrap");
		panelContent.add(profLabel);
		panelContent.add(prof, "wrap");
		panelContent.add(dateLabel);
		panelContent.add(date, "wrap");
		
		
		
		myFrame.add(panelContent, BorderLayout.CENTER);
		
		JButton close = new JButton("CLOSE");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();				
			}
		});
		JPanel button = new JPanel();
		button.add(close);
		myFrame.add(button, BorderLayout.SOUTH);
		myFrame.setVisible(true);
		addView();
		
	}
	
	private void addView() {
		CallableStatement cs = null;
		
		try {
			cs = Main.connS.getConnection().prepareCall("{call add_view(?,?)}");
			cs.setString(1, UserLogIn.user);
			cs.setInt(2, Integer.parseInt(commentID));
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
