import java.sql.Connection;

import javax.swing.JFrame;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		new RateMyClassMain();
		ConnectionService connS = new ConnectionService();
		if (connS.connect("fengt1", "Eagelbaba8")) {
			Connection conn = connS.getConnection();
			CommentService cs = new CommentService(connS);
			cs.addComment("d", 5, "1", "2", "t6");
		}	
		else {
			System.out.println("Error");
		}
			
	}

}
