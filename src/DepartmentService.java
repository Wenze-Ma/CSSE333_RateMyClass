import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentService {
	public ArrayList<String> getDepartments(){
		ArrayList<String> result = new ArrayList<>();
		CallableStatement cs = null;
		try {
			cs = Main.connS.getConnection().prepareCall("{call getalldept(?)}");
			cs.setString(1, "Nothing");
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
}
