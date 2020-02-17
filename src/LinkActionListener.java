import java.awt.BorderLayout;
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

class LinkActionListener implements ActionListener{
			
			private String courseName;
			private JFrame myFrame;
			private String linkTo;
			
			public LinkActionListener(String courseName, JFrame myFrame, String link) {
				this.courseName = courseName;
				this.myFrame = myFrame;
				this.linkTo = link;
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				myFrame.setVisible(false);
				myFrame.dispose();
				new CommentsFromLink(this.courseName, this.linkTo);
			}
			
			
			
}