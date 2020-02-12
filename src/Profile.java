import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class Profile {
	private JFrame myFrame;
	
	public Profile() {
		myFrame = new JFrame();
		myFrame.setSize(400, 180);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("My Profile");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
	}
	
	private void setup() {
		JPanel panelContent = new JPanel();
		panelContent.setLayout(new MigLayout());
		
		JLabel username = new JLabel("Username: ");
		JLabel nickName = new JLabel("Name: ");
		JLabel major = new JLabel("Major: ");
		JLabel email = new JLabel("Email");
		JLabel role = new JLabel("Role: ");
		
		JLabel usernameResult = new JLabel();
		JLabel nickNameResult = new JLabel();
		JLabel emailResult = new JLabel();
		JLabel roleResult = new JLabel();
		JLabel majorResult = new JLabel();
		
		ArrayList<String> result = getInfo();
		usernameResult.setText(result.get(0));
		nickNameResult.setText(result.get(1));
		emailResult.setText(result.get(2));
		roleResult.setText(result.get(3));
		majorResult.setText(MajorService.formattedMajors(MajorService.getMyMajors()));
		
		panelContent.add(username);
		panelContent.add(usernameResult, "wrap");
		panelContent.add(nickName);
		panelContent.add(nickNameResult, "wrap");
		panelContent.add(major);
		panelContent.add(majorResult, "wrap");
		panelContent.add(email);
		panelContent.add(emailResult, "wrap");
		panelContent.add(role);
		panelContent.add(roleResult, "wrap");
				
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
	
	private ArrayList<String> getInfo() {
		ArrayList<String> arr = new ArrayList<>();
		PreparedStatement ps = null;
		String statement = "Select Username, Name, Email, Role\n" + 
						   "from [user]\n" + 
						   "where Username = '" + 
						   UserLogIn.user + "'";
		try {
			ps = Main.connS.getConnection().prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(rs.findColumn("Username")));
				arr.add(rs.getString(rs.findColumn("Name")));
				if (rs.getString(rs.findColumn("Email")).equals("")) {
					arr.add("You don't have an email yet");
				} else {					
					arr.add(rs.getString(rs.findColumn("Email")));
				}
				if ("s".equals(rs.getString(rs.findColumn("Role")))) {
					arr.add("Student");
				} else {
					arr.add("Professor");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
}
