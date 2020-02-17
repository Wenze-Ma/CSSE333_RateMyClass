import java.awt.BorderLayout;
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

public class departmentPage {
	private JFrame myFrame;
	JPanel panelForDept = new JPanel();
	JPanel courseOfDept = new JPanel();
	String departmentSelected = null;
	JPanel profOfCourse = new JPanel();
	ArrayList<ArrayList<String>> courseInfo = new ArrayList<ArrayList<String>>();
	public departmentPage() {
		myFrame = new JFrame();
		myFrame.setSize(1000, 700);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Department Page");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();    
	}
	private void setup() {
		
	}

	
}