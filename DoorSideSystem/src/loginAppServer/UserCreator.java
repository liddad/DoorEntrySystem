package loginAppServer;

public class UserCreator {
	private String firstNames;
	private String surname;
	private int startYear;
	private int endYear;
	private String faculty;
	private String faculty2;
	private String course;
	private String department;
	private String department2;
	private String type;
	private String username;
	private String password;

	public UserCreator(String firstNames, String surname, int startYear, int endYear, String faculty, String faculty2,
			String course, String department, String department2, String type, String username, String password) {
		this.firstNames = firstNames;
		this.surname = surname;
		this.startYear = startYear;
		this.endYear = endYear;
		this.faculty = faculty;
		this.faculty2 = faculty2;
		this.course = course;
		this.department = department;
		this.department2 = department2;
		this.type = type;
		this.username = username;
		this.password = password;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public String getSurname() {
		return surname;
	}

	public int getStartYear() {
		return startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public String getFaculty() {
		return faculty;
	}

	public String getFaculty2() {
		return faculty2;
	}

	public String getCourse() {
		return course;
	}

	public String getDepartment() {
		return department;
	}

	public String getDepartment2() {
		return department2;
	}

	public String getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	
}
