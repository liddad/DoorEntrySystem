package doorServer;

import java.util.Calendar;

/** 
 * A class to hold info on a single person read from the db
 * @author Adam Liddell
 *
 */
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
	
	public boolean fitsCriteria(Criteria c){
		
		if(c.getFirstNames()!=null&&!firstNames.equals(c.getFirstNames())){
			return false;
		}
		if(c.getSurname()!=null&&!surname.equals(c.getSurname())){
			return false;
		}
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR)-startYear;
		if (now.get(Calendar.MONTH)>7){
			currentYear++;
		}
		if(endYear>0&&(currentYear>(endYear-startYear))){
			return false;
		}
		System.out.println("Here");
		if(c.getYear()>0&&c.getYear()!=currentYear){
			
			return false;
		}

		System.out.println("Here");
		if((c.getMaxYear()>0&&currentYear>c.getMaxYear())||(c.getMinYear()>0&&currentYear<c.getMinYear())){
			return false;
		}

		System.out.println("Here");
		if(c.getFaculty()!=null&&(!faculty.equals(c.getFaculty())&&!faculty2.equals(c.getFaculty()))){
			return false;
		}

		System.out.println("Here");
		if(c.getCourse()!=null&&!course.equals(c.getCourse())){
			return false;
		}

		System.out.println("Here");
		if(c.getDepartment()!=null&&(!department.equals(c.getDepartment())&&!department2.equals(c.getDepartment()))){
			return false;
		}

		System.out.println("Here");
		if(c.getType()!=null&&!type.equals(c.getType())){
			return false;
		}

		System.out.println("Here");
		return true;
	}
	
	public int getUUID(){
		return UUID;
	}
	
}
