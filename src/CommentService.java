import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class CommentService {
	private ConnectionService dbService = null;
	
	public CommentService(ConnectionService dbService) {
		this.dbService = dbService;
	}
	
//	@Comment varchar(MAX), 
//	@Rate int, 
//	@RaterName varchar(15),
//	@CourseName varchar(30), 
//	@Professor varchar(15)
	
	public boolean addComment(String comment, int rate, String raterName, String courseName, String professor) {
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{call insert_Comment(?, ?, ?, ?, ?)}");
			
			if(comment == null || comment.isEmpty()) {
				//print somethin to JFrame
				JOptionPane.showMessageDialog(null,"Empty comment not allow");
				return false;
			} 
			if(rate > 5 || rate < 0) {
				JOptionPane.showMessageDialog(null,"Illegal rate not allow");
				return false;
			} 
			if(raterName == null || raterName.isEmpty()) {
				//print somethin to JFrame
				JOptionPane.showMessageDialog(null,"Empty rater Name not allow");
				return false;
			} 
			if(courseName == null || courseName.isEmpty()) {
				//print somethin to JFrame
				JOptionPane.showMessageDialog(null,"Empty course name not allow");
				return false;
			} 
			if(professor == null || professor.isEmpty()) {
				//print somethin to JFrame
				JOptionPane.showMessageDialog(null,"Empty professor not allow");
				return false;
			} 
			//cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(1, comment);
			cs.setInt(2, rate);
			cs.setString(3, raterName);
			cs.setString(4, courseName);
			cs.setString(5, professor);
			
			
			if(!cs.execute()) {
				JOptionPane.showMessageDialog(null, "Add succeeded");
				//System.out.print(cs.getInt(1));
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Add failed");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
