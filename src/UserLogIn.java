import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class UserLogIn {
	
	private JFrame myFrame;
	private JTextField usernameField;
	private JTextField passwordField;
	private ConnectionService dbService = null;

	
	public UserLogIn(ConnectionService dbService) {
		this.dbService = dbService;
		myFrame = new JFrame();
		myFrame.setSize(400, 150);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Please log in");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        usernameField = new JTextField(20);
    	passwordField = new JTextField(20);
    	JButton submit = new JButton("Submit");
    	JButton register = new JButton("I'm a new user");
    	
        panel.add(usernameLabel);
        panel.add(usernameField, "wrap");
        panel.add(passwordLabel);
        panel.add(passwordField, "wrap");
        panel.add(submit, "skip, split2");
        panel.add(register);
        
        submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
        
        register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new Register(dbService);
			}
		});
        
        myFrame.add(panel);
        myFrame.setVisible(true);
	}
	
	private void login() {
		UserService us = new UserService(this.dbService);
		if (us.login(usernameField.getText(), passwordField.getText())) {
			myFrame.setVisible(false);
			myFrame.dispose();
			new RateMyClassMain();
		}

	}
	
}
