import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentService {
	public ArrayList<String> getDepartments(){
		ArrayList<String> result = new ArrayList<>();
		PreparedStatement ps = null;
		String statement = "select * from fn_getalldept()";
		try {
			ps = Main.connS.getConnection().prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
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
