import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MajorService {
	public ArrayList<String> getMajors(){
		ArrayList<String> result = new ArrayList<>();
		CallableStatement cs = null;
		try {
			cs = Main.connS.getConnection().prepareCall("{call getMajor}");
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
	            result.add(rs.getString("Name"));
	        }
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void addMajor(String username, String major) {
		CallableStatement cs = null;
		
		try {
			cs = Main.connS.getConnection().prepareCall("{call majorin(?,?)}");
			cs.setString(1, username);
			cs.setString(2, major);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String formattedMajors(ArrayList<String> arr) {
		String s = "";
		for (int i = 0; i < arr.size(); i++) {
			s += arr.get(i);
			if (i < arr.size() - 1) {
				s += ", ";
			}
		}
		return s;
	}
	
	public static ArrayList<String> getMyMajors() {
		ArrayList<String> arr = new ArrayList<>();
		CallableStatement cs = null;
		
		try {
			cs = Main.connS.getConnection().prepareCall("{call getMyMajor(?)}");
			cs.setString(1, UserLogIn.user);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(rs.findColumn("Name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
}
