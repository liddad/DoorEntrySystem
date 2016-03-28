package doorServer;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class SQLParserTest {

	public static SQLParser parser;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parser = new SQLParser();
	}

	@Test
	public void testPerson1() {
		try {
			Person p = parser.getPerson(1);
			assertEquals(p.getUUID(),1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testNoPerson(){
		try {
			Person p = parser.getPerson(100);
			assertNull(p);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCriteria(){
		try {
			List<Criteria> l = parser.getCriteria("LT1101");
			assertEquals(l.size(), 1);
			Criteria c = l.get(0);
			assertEquals(c.getYear(),4);
			assertNull(c.getCourse());
			assertNull(c.getDepartment());
			assertNull(c.getFaculty());
			assertNull(c.getFirstNames());
			assertEquals(c.getMaxYear(),0);
			assertEquals(c.getMinYear(),0);
			assertNull(c.getSurname());
			assertNull(c.getType());
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNoCriteria(){
		try {
			List<Criteria> l = parser.getCriteria("NOROOM");
			assertTrue(l.isEmpty());
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
