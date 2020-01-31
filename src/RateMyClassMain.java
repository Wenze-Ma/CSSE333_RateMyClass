import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RateMyClassMain {
	private JFrame myFrame;
	private JTextField searchField;
	
	
	public RateMyClassMain() {
		this.myFrame = new JFrame();
		
		myFrame.setSize(1500, 1000);
		myFrame.setTitle("Rate My Class");
	    myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JPanel panelForSearch = new JPanel();
		panelForSearch.setLayout(new GridBagLayout());
		panelForSearch.setSize(200, 300);
		
		JLabel searchLabel = new JLabel("Class Search: ");
		panelForSearch.add(searchLabel);
		
		searchField = new JTextField(40);
		panelForSearch.add(searchField);
		
		JButton searchButton = new JButton("Submit");
		panelForSearch.add(searchButton);
		
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = searchField.getText();
				
			}
		});
		
		
		myFrame.add(panelForSearch, BorderLayout.NORTH);
		
		myFrame.setVisible(true);
	}
}
