import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class CommentInsert {
	
	private JFrame myFrame;
	private JTextField commentField;
	private JTextField rateField;
	private JTextField courseField;
	private JTextField professorField;
	
	private String username;

	
	public CommentInsert(String username) {
		this.username = username;
		myFrame = new JFrame();
		myFrame.setSize(400, 150);
	    myFrame.setLocationRelativeTo(null);
		myFrame.setTitle("Please enter information");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        
        JLabel commentLabel = new JLabel("Comments: ");
        JLabel rateLabel = new JLabel("Rates: ");
        JLabel courseName = new JLabel("Course Name: ");
        JLabel professorName = new JLabel("Professor Name: ");
        
        commentField = new JTextField(20);
    	rateField = new JTextField(20);
    	courseField = new JTextField(20);
    	professorField = new JTextField(20);
    	
    	JButton insert = new JButton("insert");
    	JButton cancel = new JButton("cancel");
    	
        panel.add(commentLabel);
        panel.add(commentField, "wrap");
        panel.add(rateLabel);
        panel.add(rateField, "wrap");
        panel.add(courseName);
        panel.add(courseField, "wrap");
        panel.add(professorName);
        panel.add(professorField, "wrap");
        panel.add(insert);
        panel.add(cancel);
        
        insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
        
        cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new RateMyClassMain();
			}
		});
        
        myFrame.add(panel);
        myFrame.setVisible(true);
	}
	
private void insert() {
		CommentService cs = new CommentService();
		if(cs.addComment(commentField.getText(),
				Integer.getInteger(rateField.getText()),
				this.username, 
				courseField.getText(), 
				professorField.getText())) {
			myFrame.setVisible(false);
			myFrame.dispose();
			new RateMyClassMain();
		}
	}
	
}
