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
				cs = Main.connS.getConnection().prepareCall("{? = call take_Course(?, ?)}");
				
				if(username == null || username.isEmpty()) {
					//print somethin to JFrame
					JOptionPane.showMessageDialog(null,"Empty username not allow");
					return false;
				} 
				if(course < 0) {
					JOptionPane.showMessageDialog(null,"Illegal course id not allow");
					return false;
				} 
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, username);
				cs.setInt(3, course);
				cs.execute();
				int result = cs.getInt(1);
				System.out.println(result);
				
				if(result == 0) {
					JOptionPane.showMessageDialog(null, "Add succeeded");
					return true;
				} else if(result == 10){
					JOptionPane.showMessageDialog(null, "Empty input");
				}else if(result == 20){
					JOptionPane.showMessageDialog(null, "invalid Student Username");
				}else if(result == 30){
					JOptionPane.showMessageDialog(null, "invalid CourseID");
				}else if(result == 40){
					JOptionPane.showMessageDialog(null, "You have already selected this course!");
				}
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		public int getCourseIDByNumber(String CNumber) {
			int result = 0;
			PreparedStatement ps = null;
			String statement = "Select c.ID\n"
					+ "From Course c\n"
					+ "where c.Number = '" + CNumber + "'";
			try {
				ps = Main.connS.getConnection().prepareStatement(statement);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
		            result = rs.getInt("ID");
		        }
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	
		public ArrayList<String> getCoursesByDepartment(String deptName) {
			ArrayList<String> result = new ArrayList<>();
			result.add("----");
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
