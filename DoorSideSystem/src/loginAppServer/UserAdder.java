package loginAppServer;

public class UserAdder {

	public static void main(String[] args) {
		String FIRSTNAME = "John";
		String SURNAME = "Smith";
		int STARTYEAR = 2011;
		int ENDYEAR = 2016;
		String FACULTY = "Science";
		String FACULTY2 = null;
		String COURSE = "CIS";
		String DEPARTMENT = "CIS";
		String DEPARTMENT2 = null;
		String TYPE = "Student";
		String USERNAME = "bbb22222";
		String PASSWORD = "password123";
		
		String hashpassword = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
		
		User user = new User(FIRSTNAME, SURNAME, STARTYEAR, ENDYEAR, FACULTY, FACULTY2,
			COURSE, DEPARTMENT,DEPARTMENT2, TYPE, USERNAME, hashpassword);
		
		LoginSQLParser p = new LoginSQLParser();
		System.out.println(p.addPerson(user));
	}

}
