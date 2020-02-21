import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private ConnectionService dbService = null;

	public UserService(ConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean login(String username, String password) {
		String query = "select * from fn_getUserInfo('" + username + "')";
		Statement stmt = null;
        ResultSet rs = null;
        byte[] salt = null;
		String hash = null;

		try {
			stmt = this.dbService.getConnection().createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
		        salt = rs.getBytes("Salt");
		        hash = rs.getString("PasswordHash");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
                
        if (salt == null || hash == null) {
			JOptionPane.showMessageDialog(null, "Login Failed");
			return false;
        }
        String inputHash = hashPassword(salt, password);
        if (!inputHash.equals(hash)) {
			JOptionPane.showMessageDialog(null, "Login Failed");
        	return false;
        } 
        if (inputHash.equals(hash)) {
			JOptionPane.showMessageDialog(null, "Login Successful");
        	return true;
        }
        return false;
	}
	
	public boolean register(String username, String password, String role, String email, String name) {
		byte[] salt = getNewSalt();
		String hash = hashPassword(salt, password);
		
		CallableStatement cs = null;
		try {
			cs = this.dbService.getConnection().prepareCall("{? = call Register(?, ?, ?, ?, ?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setBytes(3, salt);
			cs.setString(4, hash);
			cs.setString(5, role);
			cs.setString(6, email);
			cs.setString(7, name);
			cs.execute();

			if (cs.getInt(1) == 0) {
				JOptionPane.showMessageDialog(null, "Registration Succeed");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Registration Failed");
				return false;
			}
		} catch (SQLException e){
			return false;
		}
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}
	

}
