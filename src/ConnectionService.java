import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionService {
	private Connection conn;
	
	public ConnectionService() {
		conn = null;
	}
	public boolean connect(String user, String password) {
		String connectionUrl = "jdbc:sqlserver://golem.csse.rose-hulman.edu:1433;"  
				  + "database=RateMyClass;"
                  + "user=" + user + ";"
                  + "password=" + password + ";"
                  + "encrypt=false;"
                  + "trustServerCertificate=true;"
                  + "loginTimeout=30;";

		try {
			this.conn = DriverManager.getConnection(connectionUrl);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
