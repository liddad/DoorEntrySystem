package doorServer;

public class Person {

	private int UUID;
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
	
	
	public Person(int UUID, String firstNames, String surname, int startYear, int endYear,
			String faculty, String faculty2, String course, String department, String department2,
			String type){
		this.UUID=UUID;
		this.firstNames=firstNames;
		this.surname=surname;
		this.startYear=startYear;
		this.endYear=endYear;
		this.faculty=faculty;
		this.faculty2=faculty2;
		this.course = course;
		this.department = department;
		this.department2 = department2;
		this.type = type;
	}

	public int getUUID() {
		return UUID;
	}

	public void setUUID(int uUID) {
		UUID = uUID;
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

	public String getFaculty2() {
		return faculty2;
	}

	public void setFaculty2(String faculty2) {
		this.faculty2 = faculty2;
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

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
