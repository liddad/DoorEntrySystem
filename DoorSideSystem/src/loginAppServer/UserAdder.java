package loginAppServer;

/**
 * A class used to add new users
 * @author Adam Liddell
 *
 */
public class UserAdder {

	public static void main(String[] args) {
		String FIRSTNAME = "Blaise";		//notnull
		String SURNAME = "Pascal";		//notnull
		int STARTYEAR = 2013;
		int ENDYEAR = 2017;
		String FACULTY = "Science";		//notnull
		String FACULTY2 = null;
		String COURSE = "Mathematics & Physics";
		String DEPARTMENT = "Mathematics";		//notnull
		String DEPARTMENT2 = "Physics";
		String TYPE = "Student";		//notnull
		String USERNAME = "ggg77777";	//notnull
		String PASSWORD = "Triangles";//notnull
		
		String hashpassword = BCrypt.hashpw(PASSWORD, BCrypt.gensalt());
		
		User user = new User(FIRSTNAME, SURNAME, STARTYEAR, ENDYEAR, FACULTY, FACULTY2,
			COURSE, DEPARTMENT,DEPARTMENT2, TYPE, USERNAME, hashpassword);
		
		LoginSQLParser p = new LoginSQLParser();
		System.out.println(p.addPerson(user));
	}

}
