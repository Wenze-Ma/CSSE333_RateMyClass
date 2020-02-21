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

import net.miginfocom.swing.MigLayout;

public class MajorInfo {
	private JFrame myFrame;
	private ArrayList<String> majors = new ArrayList<>();
	private ArrayList<String> requiredCourses = new ArrayList<>();
	
	public MajorInfo() {
		myFrame = new JFrame();
		myFrame.setSize(700, 720);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Major Info");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        majors = MajorService.getMyMajors();
        requiredCourses = getRequiredCourses();
        setup();
	}

	private void setup() {
		JPanel panelContent = new JPanel();
		panelContent.setLayout(new MigLayout());
		
		JLabel majorsText = majors.size() > 1 ? new JLabel("Your majors are: ") : new JLabel("Your major is: ");
		JLabel majorDetail = new JLabel(MajorService.formattedMajors(majors));
		
		panelContent.add(majorsText);
		panelContent.add(majorDetail, "wrap");
		panelContent.add(new JLabel("You are required to take: "));
		
		for (int i = 0; i < requiredCourses.size(); i++) {
			String text = requiredCourses.get(i);
			if (isTaken(requiredCourses.get(i))) {
				text += " (taken)";
			}
			JLabel currentCourse = new JLabel(text);
			JButton currentButton = new JButton("View Comments");
			currentButton.addActionListener(new LinkActionListener(requiredCourses.get(i), myFrame, "major"));
			
			
			if (i == 0) {
				panelContent.add(currentCourse);
				panelContent.add(currentButton, "wrap");
			} else {
				panelContent.add(currentCourse, "skip, split");
				panelContent.add(currentButton, "wrap, skip, split2");
			}
		}
		
		myFrame.add(panelContent, BorderLayout.CENTER);
		
		JButton back = new JButton("Go Back To The Home Page");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new RateMyClassMain();				
			}
		});
		JPanel button = new JPanel();
		button.add(back);
		myFrame.add(button, BorderLayout.SOUTH);
		myFrame.setVisible(true);
		
	}
	
	private ArrayList<String> getRequiredCourses() {
		ArrayList<String> arr = new ArrayList<>();
		CallableStatement cs = null;
		try {
			cs = Main.connS.getConnection().prepareCall("{call getRequiredCourses(?)}");
			cs.setString(1, majors.get(0));
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(rs.findColumn("Name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;		
	}
	
	private boolean isTaken(String course) {
		CallableStatement cs = null;
		
		try {
			cs = Main.connS.getConnection().prepareCall("{call isTaken(?,?)}");
			cs.setString(1, UserLogIn.user);
			cs.setString(2, course);
			ResultSet rs = cs.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
