import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

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
	
//			[Date]	
//		      ,[content]
//		      ,[rate]
//		      ,[CourseName]
//		      ,[ProfessorName]
	
	public ArrayList<ArrayList<String>> getComment(int course){
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		PreparedStatement ps = null;
		String statement = "Select c.content, c.rate, c.Date, c2.Name as [Course Name], u.Name as [Professor Name]\r\n" + 
				"from Comment c join [User] u on c.ProfessorUsername = u.Username join Course c2 on c.CourseID = c2.ID "
				+ "where c.CourseID = ?";
		try {
			ps = this.dbService.getConnection().prepareStatement(statement);
			ps.setInt(1, course);
			
			ResultSet rs = ps.executeQuery();
			return parseResults(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private ArrayList<ArrayList<String>> parseResults(ResultSet rs) {
		try {
			ArrayList<ArrayList<String>> comment = new ArrayList<ArrayList<String>>();
			int ContentIndex = rs.findColumn("Content");
			int rateIndex = rs.findColumn("rate");
			int dateIndex = rs.findColumn("Date");
			int courseIndex = rs.findColumn("Course Name");
			int ProfIndex = rs.findColumn("Professor Name");
			while (rs.next()) {
				ArrayList<String> re = new ArrayList<String>();
				re.add(rs.getString(ContentIndex));
				re.add(rs.getString(rateIndex));
				re.add(rs.getString(dateIndex));
				re.add(rs.getString(courseIndex));
				re.add(rs.getString(ProfIndex));
				
				comment.add(re);
			}
			
			return comment;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving comments. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<ArrayList<String>>();
		}

	}
}
