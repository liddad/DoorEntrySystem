package loginAppServer;

/**
 * A class used to add new users
 * @author Adam Liddell
 *
 */
public class UserAdder {

	public static void main(String[] args) {
		String FIRSTNAME = "John";		//notnull
		String SURNAME = "Smith";		//notnull
		int STARTYEAR = 2011;
		int ENDYEAR = 2016;
		String FACULTY = "Science";		//notnull
		String FACULTY2 = null;
		String COURSE = "CIS";
		String DEPARTMENT = "CIS";		//notnull
		String DEPARTMENT2 = null;
		String TYPE = "Student";		//notnull
		String USERNAME = "bbb22222";	//notnull
		String PASSWORD = "password123";//notnull
		
		String hashpassword = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
		
		User user = new User(FIRSTNAME, SURNAME, STARTYEAR, ENDYEAR, FACULTY, FACULTY2,
			COURSE, DEPARTMENT,DEPARTMENT2, TYPE, USERNAME, hashpassword);
		
		LoginSQLParser p = new LoginSQLParser();
		System.out.println(p.addPerson(user));
	}

}
