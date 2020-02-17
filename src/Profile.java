import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class Profile {
	private JFrame myFrame;
	
	public Profile() {
		myFrame = new JFrame();
		myFrame.setSize(400, 250);
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
		JTextField nickNameResult = new JTextField(30);
		JTextField emailResult = new JTextField(30);
		JLabel roleResult = new JLabel();
		JLabel majorResult = new JLabel();
		
		ArrayList<String> result = getInfo();
		usernameResult.setText(result.get(0));
		nickNameResult.setText(result.get(1));
		emailResult.setText(result.get(2));
		roleResult.setText(result.get(3));
		majorResult.setText(MajorService.formattedMajors(MajorService.getMyMajors()));
		emailResult.setEditable(false);
		nickNameResult.setEditable(false);
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
		JButton confirm = new JButton("Confirm");
		JButton back = new JButton("Go Back To The Home Page");		
		myFrame.add(panelContent, BorderLayout.CENTER);
		JButton edit = new JButton("EDIT PROFILE");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailResult.setEditable(true);
				nickNameResult.setEditable(true);
				edit.setVisible(false);
				confirm.setVisible(true);
			}
		});
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean rs = editProfile(UserLogIn.user, nickNameResult.getText(), emailResult.getText());
				if(rs) {
					edit.setVisible(true);
					confirm.setVisible(false);
					emailResult.setEditable(false);
					nickNameResult.setEditable(false);
				}
				
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new RateMyClassMain();				
			}
		});
		JPanel buttons = new JPanel();
		buttons.add(edit);
		buttons.add(confirm);
		buttons.add(back);
		
		confirm.setVisible(false);
		myFrame.add(buttons, BorderLayout.SOUTH);
		
		
		myFrame.setVisible(true);
	}
	
	public boolean editProfile(String username, String nickname, String email) {
		CallableStatement cs = null;
		try {
			cs = Main.connS.getConnection().prepareCall("{? = call editProfile(?, ?, ?)}");
			
			if(username == null || username.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Empty username not allow");
				return false;
			} 
			if(nickname == null || nickname.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Empty Name not allow");
				return false;
			} 
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, nickname);
			cs.setString(4, email);
			cs.execute();
			int result = cs.getInt(1);
			System.out.println(result);
			
			if(result == 0) {
				JOptionPane.showMessageDialog(null, "EDIT succeeded");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "EDIT failed");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
				//if (rs.getString(rs.findColumn("Email")).equals("")) {				
					arr.add(rs.getString(rs.findColumn("Email")));
				//}
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
