import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
	import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;
	
public class CourseService {

		
		public CourseService() {
		}
		
//		@Comment varchar(MAX), 
//		@Rate int, 
//		@RaterName varchar(15),
//		@CourseName varchar(30), 
//		@Professor varchar(15)
		
		public boolean addTakeCourse(String username, int course) {
			CallableStatement cs = null;
			
			try {
				cs = Main.connS.getConnection().prepareCall("{call take_Course(?, ?)}");
				
				if(username == null || username.isEmpty()) {
					//print somethin to JFrame
					JOptionPane.showMessageDialog(null,"Empty username not allow");
					return false;
				} 
				if(course < 0) {
					JOptionPane.showMessageDialog(null,"Illegal course id not allow");
					return false;
				} 
				
				cs.setString(1, username);
				cs.setInt(2, course);
				
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
	
		public ArrayList<String> getCoursesByDepartment(String deptName) {
			ArrayList<String> result = new ArrayList<>();
			PreparedStatement ps = null;
			String statement = "Select c.Number\n" + 
							   "From Department d join course c on d.id = c.Dept\n" + 
							   "where d.Name = '" + deptName + "'";
			try {
				ps = Main.connS.getConnection().prepareStatement(statement);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
		            result.add(rs.getString("Number"));
		        }
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

}
