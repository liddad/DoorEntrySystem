package doorServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class RequestListener implements Runnable {

	private InputStream is;
	private OutputStream os;
	private SQLParser sql;
	private Socket sock;

	public RequestListener(Socket s) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = s.getInputStream();
			os = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sock = s;
		this.is = is;
		this.os = os;
		sql = new SQLParser();
	}

	@Override
	public void run() {
		try {
			String s;
			DoorRequest r;
			Person person;
			List<Criteria> criteria;
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			PrintWriter out = new PrintWriter(os, true);
			s = in.readLine();
			System.out.println(s);
			if(sql.roomExists(s)){
				out.println("true");
			} else {
				out.println("false");
			}
			while (true) {
				Boolean accept = false;
				
				s = in.readLine();
				System.out.println("Code: " + s);
				try{
				r = new DoorRequest(s);
				person = sql.getPerson(r.userCode);
				criteria = sql.getCriteria(r.doorCode);

				for (Criteria c : criteria) {
					if (person == null) {
						break;
					}
					if (person.fitsCriteria(c)) {
						accept = true;
						System.out.println("Logged: " + sql.logEntry(r.userCode, r.doorCode));
						break;
					}
				}

				out.println(accept.toString());
				} catch (Exception e){
					//For unexpected input here don't close the connection
					out.println("false");
				}
			}
			// System.out.println("Ending connection...");
			// sock.close();

		} catch (SQLException | IOException e) {
			PrintWriter out = new PrintWriter(os, true);
			out.println("false");
			System.out.println("Closing...");
			try {
				sock.close();
			} catch (IOException e1) {
			}
		}
	}

}
