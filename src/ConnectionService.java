
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConnectionService {
	private Connection conn;
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";


	public ConnectionService() {
		conn = null;
	}

	public boolean connect() {

		try {
			Properties pro = new Properties();
			FileInputStream in = new FileInputStream("src/ratemyclass.properties");
			pro.load(in);
		
			in.close();
			String server = pro.getProperty("serverName");
			String out = replace(SampleURL, "${dbServer}", server);
			String name = pro.getProperty("databaseName");
			out =  replace(out, "${dbName}", name);
			String user = pro.getProperty("serverUsername");
			out =  replace(out, "${user}", user);
			String pass = pro.getProperty("serverPassword");
			out =  replace(out, "${pass}", pass);
			this.conn = DriverManager.getConnection(out);
			
			
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
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
	
	public String replace(String input, String target, String re) {
		int length = target.length();
		int index = input.indexOf(target);
		return input.substring(0, index) + re + input.substring(index+length);
	}
	
}
