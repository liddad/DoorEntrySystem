package doorServer;

public class Criteria {
	private String room;
	private String firstNames;
	private String surname;
	private int startYear;
	private int endYear;
	private String faculty;
	private String course;
	private String department;
	private String type;
	
	public Criteria(String room, String firstNames, String surname, int startYear, int endYear,
			String faculty, String course, String department, String type){
		this.room = room;
		this.firstNames=firstNames;
		this.surname=surname;
		this.startYear=startYear;
		this.endYear=endYear;
		this.faculty=faculty;
		this.course = course;
		this.department = department;
		this.type = type;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
