import java.sql.Connection;

import javax.swing.JFrame;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		new RateMyClassMain();
		ConnectionService connS = new ConnectionService();
		
		if (connS.connect("username","password")) {
			Connection conn = connS.getConnection();
//			CourseService cs = new CourseService(connS);
//			cs.addTakeCourse("1", 3);
			
		}	
		else {
			System.out.println("Error");
		}
	}

}
