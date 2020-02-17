import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionService {
	private Connection conn;
	
	private String databaseName;
	private String serverName;
	
	public ConnectionService(String databaseName, String serverName) {
		conn = null;
		this.databaseName = databaseName;
		this.serverName = serverName;
	}
	public boolean connect(String user, String password) {
		String connectionUrl = "jdbc:sqlserver://" + this.serverName
				  + ";database=" + this.databaseName
                  + ";user=" + user + ";"
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
