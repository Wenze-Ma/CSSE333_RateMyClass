import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Register {
	private JFrame myFrame;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField nameField;
	private JTextField roleField;
	private JTextField emailField;
	
	private String[] roleStrings = {"Student", "Professor"};
	private JComboBox cmbMessageList = new JComboBox(roleStrings);
	
	private String role = "s";


	
	public Register() {
		myFrame = new JFrame();
		myFrame.setSize(400, 250);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Register Your account");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel nameLabel = new JLabel("Prefered Name: ");
        JLabel roleLabel = new JLabel("Role: ");
        JLabel emailLabel = new JLabel("Email: ");
        usernameField = new JTextField(20);
    	passwordField = new JTextField(20);
    	nameField = new JTextField(20);
    	roleField = new JTextField(20);
    	emailField = new JTextField(20);

    	JButton submit = new JButton("Submit");
    	JButton back = new JButton("Go Back to Log in");
    	
        panel.add(usernameLabel);
        panel.add(usernameField, "wrap");
        panel.add(passwordLabel);
        panel.add(passwordField, "wrap");
        panel.add(nameLabel);
        panel.add(nameField, "wrap");
        panel.add(roleLabel);
        panel.add(cmbMessageList, "wrap");
        panel.add(emailLabel);
        panel.add(emailField, "wrap");
        panel.add(back, "skip, split2");
        panel.add(submit);
        
        cmbMessageList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((((JComboBox) e.getSource()).getSelectedItem().toString()).equals("Student")) {
					role = "s";
				} else {
					role = "t";
				}
			}
		});
        
        back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new UserLogIn();
			}
		});
        
        submit.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
        
        myFrame.add(panel);
        myFrame.setVisible(true);
	}
	
	private void register() {
		UserService us = new UserService(Main.connS);
		if (us.register(usernameField.getText(), passwordField.getText(), role, emailField.getText(), nameField.getText())) {
			UserLogIn.user = usernameField.getText();
			myFrame.setVisible(false);
			myFrame.dispose();
			new RateMyClassMain();
		}
	}
}
