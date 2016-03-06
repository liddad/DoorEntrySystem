package loginAppServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LoginSQLParser {

	private Connection conn;

	public LoginSQLParser() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Connection c = null;
		String serverName = "localhost";
		int port = 3306;
		Properties cp = new Properties();
		cp.put("user", "loginServer");
		cp.put("password", "loginServer1234");
		try {
			c = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/doorsystem", cp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = c;
	}

	public PersonInfo getPersonInfo(String username) {
		try {
			String statement = "SELECT * FROM people WHERE Username=" + username;
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(statement);

			if (result.next()) {
				PersonInfo pi = new PersonInfo();
				pi.password = result.getString("Password");
				pi.UUID = result.getInt("UUID");
				return pi;
			}
			return null;
		} catch (SQLException e) {
			return null;
		}
	}
}
