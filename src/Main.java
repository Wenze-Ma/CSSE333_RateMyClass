import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.Connection;

public class Main {
	
	
	public Main() {
		try (InputStream input = new FileInputStream("path/to/ratemyclassapp.properties")) {

	        Properties prop = new Properties();

	        // load a properties file
	        prop.load(input);

	        // get the property value and print it out
	        System.out.println(prop.getProperty("serverUsername"));

	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}

	public static ConnectionService connS = new ConnectionService("RateMyClass","golem.csse.rose-hulman.edu:1433");
	public static void main(String[] args) {
		
	if (connS.connect("RMC20","Password123")) {
			CommentService cs = new CommentService();
			ArrayList<ArrayList<String>> re = cs.getComment("course1", "");
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


