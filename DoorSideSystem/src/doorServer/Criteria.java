package doorServer;

public class Criteria {
	private String room;
	private String firstNames;
	private String surname;
	private int year;
	private int maxYear;
	private int minYear;
	private String faculty;
	private String course;
	private String department;
	private String type;
	
	public Criteria(String room, String firstNames, String surname, int year, int maxYear, int minYear,
			String faculty, String course, String department, String type){
		this.room = room;
		this.firstNames=firstNames;
		this.surname=surname;
		this.year=year;
		this.maxYear=maxYear;
		this.minYear=minYear;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMaxYear() {
		return maxYear;
	}

	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}

	public int getMinYear() {
		return minYear;
	}

	public void setMinYear(int minYear) {
		this.minYear = minYear;
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