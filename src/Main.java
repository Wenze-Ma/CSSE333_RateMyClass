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
		
	if (connS.connect("RMC20","Password123")) {
			Connection conn = connS.getConnection();
			CommentService cs = new CommentService();
			ArrayList<ArrayList<String>> re = cs.getComment("course1");
			for(int i = 0; i < re.size(); i++) {
				for(int j = 0; j < re.get(i).size(); j++) {
					System.out.print(re.get(i).get(j) + " ");
				}
				System.out.print("\n");
			}
		}	
		else {
			System.out.println("Error");
		}
		new UserLogIn();
	}

}


