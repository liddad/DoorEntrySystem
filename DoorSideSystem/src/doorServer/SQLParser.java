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
	private Connection doorLogConn;
	
	public SQLParser(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Connection c = null;
		Connection c2 = null;
		String serverName= "localhost";
		int port = 3306;
		Properties cp = new Properties();
		cp.put("user", "piServer");
		cp.put("password", "piServer1234");
		try {
			c = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/doorsystem", cp);
			c2 = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/doorlog", cp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = c;
		doorLogConn = c2;
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
		
		if(result.next()){
		UUID = result.getInt("UUID");
		firstNames = result.getString("FNames");
		surname = result.getString("Surname");
		startYear = result.getInt("StartYear");
		endYear = result.getInt("EndYear");
		faculty =result.getString("Faculty");
		faculty2 = result.getString("Faculty2");
		course = result.getString("Course");
		department = result.getString("Department");
		department2 = result.getString("Department2");
		type = result.getString("PType");
		
		return new Person(UUID, firstNames, surname, startYear, endYear, faculty, faculty2, 
				course, department, department2, type);
		}
		return null;
	}
	
	public List<Criteria> getCriteria(String room) throws SQLException{
		String roomName;
		String firstNames;
		String surname;
		int year;
		int maxYear, minYear;
		String faculty;
		String course;
		String department;
		String type;
		ArrayList<Criteria> list = new ArrayList<Criteria>();
		
		String statement = "SELECT * FROM criteria WHERE RoomName=\"" + room + "\"";
		Statement s = conn.createStatement();
		ResultSet result = s.executeQuery(statement);
		
		while(result.next()){
			roomName = result.getString("RoomName");
			firstNames = result.getString("FName");
			surname = result.getString("Surname");
			year = result.getInt("StudyYear");
			maxYear = result.getInt("MaxYear");
			minYear = result.getInt("MinYear");
			faculty =result.getString("Faculty");
			course = result.getString("Course");
			department = result.getString("Department");
			type = result.getString("PType");
		
			list.add(new Criteria(roomName, firstNames, surname, year, maxYear, minYear, faculty,
				course, department, type));
		}
		return list;
	}

	public boolean roomExists(String s)  throws SQLException{
		String statement = "SELECT * FROM rooms WHERE RoomName=\"" + s + "\"";
		Statement st = conn.createStatement();
		ResultSet result = st.executeQuery(statement);
		
		return result.isBeforeFirst();
	}
	
	public boolean logEntry(int UID, String room) throws SQLException{
		String statement = "INSERT INTO doorlog (UUID, RoomNo) VALUES (\"" + UID + "\",\"" + room + "\")";
		
		Statement s = doorLogConn.createStatement();
		int i = s.executeUpdate(statement);
		return (i>0);
	}
}
