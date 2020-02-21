import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.Connection;

public class Main {

	public static ConnectionService connS = new ConnectionService();

	public static void main(String[] args) {
		connS.connect();
		new UserLogIn();
	}

}
