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
				conn.close();
				return pi;
			}
			conn.close();
			return null;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean addPerson(User person){
		String fields = "(FNames, Surname, Faculty, Department, PType, Username, Password";
		String values = "('" + person.getFirstNames() + "', '" + person.getSurname() + "', '" +
				person.getFaculty() + "', '" + person.getDepartment() + "', '" +
				person.getType() + "', '" + person.getUsername() + "', '" + person.getPassword() + "'";
		
		if(person.getCourse()!=null){
			fields += ", Course";
			values += ", '";
			values += person.getCourse();
			values += "'";
		}
		if(person.getDepartment2()!=null){
			fields += ", Department2";
			values += ", '";
			values += person.getDepartment2();
			values += "'";
		}
		if(person.getFaculty2()!=null){
			fields += ", Faculty2";
			values += ", '";
			values += person.getFaculty2();
			values += "'";
		}
		if(person.getStartYear()!=0){
			fields += ", StartYear";
			values += ", '";
			values += person.getStartYear();
			values += "'";
		}
		if(person.getEndYear()!=0){
			fields += ", EndYear";
			values += ", '";
			values = values + "" + person.getEndYear();
			values += "'";
		}
		fields += ")";
		values += ")";
		System.out.println(values);
		
		String statement = "INSERT INTO people " + fields + " VALUES " + values + ";";
		
		try {
			Statement s = conn.createStatement();
			s.executeUpdate(statement);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
