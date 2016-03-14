package loginAppServer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

public class LoginTester {

	private PrintWriter output;
	private BufferedReader input;
	
	@Before
	public void setup(){
		try {
			Socket socket = new Socket("192.168.1.94", 7070);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(),true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginUser1() {
		String username = "aaa11111";
		String password = "password";
		output.println(username);
		output.println(password);
		String reply;
		try {
			reply = input.readLine();
			assertEquals(reply, "UID: 1");
		} catch (IOException e) {
			fail("Exception thrown");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testWrongUsername(){
		String username = "aaa11112";
		String password = "password";
		output.println(username);
		output.println(password);
		String reply;
		try {
			reply = input.readLine();
			assertEquals(reply, "Denied");
		} catch (IOException e) {
			fail("Exception thrown");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testWrongPassword(){
		String username = "aaa11111";
		String password = "passworf";
		output.println(username);
		output.println(password);
		String reply;
		try {
			reply = input.readLine();
			assertEquals(reply, "Denied");
		} catch (IOException e) {
			fail("Exception thrown");
			e.printStackTrace();
		}
		
	}

	@Test
	public void testLoginUser2(){
		String username = "bbb22222";
		String password = "password123";
		output.println(username);
		output.println(password);
		String reply;
		try {
			reply = input.readLine();
			assertEquals(reply, "UID: 2");
		} catch (IOException e) {
			fail("Exception thrown");
			e.printStackTrace();
		}
		
	}
}
