package doorServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLParser {

	private Connection conn;
	
	public SQLParser() throws SQLException{
		String serverName= "localhost";
		int port = 3306;
		Properties cp = new Properties();
		cp.put("user", "piServer");
		cp.put("password", "piServer1234");
		conn = DriverManager.getConnection("jdbc: mysql://" + serverName + ":" + port + "/", cp);
	}
	
	public Person getPerson(int ID) throws SQLException{
		int UUID;
		String firstNames;
		String surname;
		int startYear;
		int endYear;
		String faculty;
		String faculty2;
		String course;
		String department;
		String department2;
		String type;
		
		String statement = "SELECT * FROM people WHERE UUID=" + ID;
		Statement s = conn.createStatement();
		ResultSet result = s.executeQuery(statement);
		
		UUID = Integer.parseInt(result.getString("UUID"));
		firstNames = result.getString("FNames");
		surname = result.getString("Surname");
		startYear = Integer.parseInt(result.getString("StartYear"));
		endYear = Integer.parseInt(result.getString("EndYear"));
		faculty =result.getString("Faculty");
		faculty2 = result.getString("Faculty2");
		course = result.getString("Course");
		department = result.getString("Department");
		department2 = result.getString("Department2");
		type = result.getString("PType");
		
		return new Person(UUID, firstNames, surname, startYear, endYear, faculty, faculty2, 
				course, department, department2, type);
	}
	
	public List<Criteria> getCriteria(String room) throws SQLException{
		String roomName;
		String firstNames;
		String surname;
		int startYear;
		int endYear;
		String faculty;
		String course;
		String department;
		String type;
		ArrayList<Criteria> list = new ArrayList<Criteria>();
		
		String statement = "SELECT * FROM criteria WHERE RoomName=" + room;
		Statement s = conn.createStatement();
		ResultSet result = s.executeQuery(statement);
		
		do{
			roomName = result.getString("RoomName");
			firstNames = result.getString("FNames");
			surname = result.getString("Surname");
			startYear = Integer.parseInt(result.getString("StartYear"));
			endYear = Integer.parseInt(result.getString("EndYear"));
			faculty =result.getString("Faculty");
			course = result.getString("Course");
			department = result.getString("Department");
			type = result.getString("PType");
		
			list.add(new Criteria(roomName, firstNames, surname, startYear, endYear, faculty,
				course, department, type));
		}while(result.next());
		return list;
	}
	
}
