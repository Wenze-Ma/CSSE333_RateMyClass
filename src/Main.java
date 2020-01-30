import java.sql.Connection;

import javax.swing.JFrame;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		new RateMyClassMain();
		ConnectionService connS = new ConnectionService();
		if (connS.connect("", "")) {
			Connection conn = connS.getConnection();
			
		}	
		else {
			System.out.println("Error");
		}
			
	}

}
