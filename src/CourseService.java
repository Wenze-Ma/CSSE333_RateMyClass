
	import java.sql.CallableStatement;
	import java.sql.SQLException;
	import java.sql.Types;

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
	

}
