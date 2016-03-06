package loginAppServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;

public class LoginHandler implements Runnable {

	private final String DENIED = "Denied";
	private SSLSocket socket;
	private InputStream is;
	private OutputStream os;
	private LoginSQLParser parser;

	public LoginHandler(SSLSocket socket) {
		this.socket = socket;
		InputStream tempis = null;
		OutputStream tempos = null;
		try {
			tempis = socket.getInputStream();
			tempos = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		is = tempis;
		os = tempos;
		parser = new LoginSQLParser();
	}

	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(is));
			PrintWriter output = new PrintWriter(os);
			String username = input.readLine();
			String password = input.readLine();
			
			PersonInfo person = parser.getPersonInfo(username);
			if(person == null){
				output.println(DENIED);
			} else if(BCrypt.checkpw(password, person.password)){
				output.println("UUID: " + person.UUID);
			} else{
				output.println(DENIED);
			}
			is.close();
			os.close();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
