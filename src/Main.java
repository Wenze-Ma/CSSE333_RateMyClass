import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		ConnectionService connS = new ConnectionService();
		
		if (connS.connect("username","password")) {
			Connection conn = connS.getConnection();
			CommentService cs = new CommentService(connS);
			ArrayList<ArrayList<String>> re = cs.getComment(2);
			for(int i = 0; i < re.size(); i++) {
				for(int j = 0; j < re.get(i).size(); j++) {
					System.out.print(re.get(i).get(j) + " ");
				}
				System.out.print("\n");
			}
		}	
		else {
			System.out.println("Error");
		}
		new UserLogIn(connS);
	}

}
