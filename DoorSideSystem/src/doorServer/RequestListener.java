package doorServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

/**
 * This class deals with listening and replying to the thread.
 * 
 * @author Adam Liddell
 *
 */
public class RequestListener implements Runnable {

	private InputStream is;
	private OutputStream os;
	private SQLParser sql;
	private Socket sock;

	/**
	 * @param s
	 *            - The connected socket
	 */
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
			// Set up the reader & writer for server
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			PrintWriter out = new PrintWriter(os, true);
			/*
			 * The first string received is the room name, and the pi closes the
			 * socket itself if false is recieved
			 */
			s = in.readLine();
			if (sql.roomExists(s)) {
				out.println("true");
			} else {
				out.println("false");
			}

			// Door request loop
			while (true) {
				Boolean accept = false;

				
				while((s = in.readLine())==null);
				System.out.println("Code: " + s); 
				
				try {
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
				} catch (Exception e) {
					// For unexpected input here don't close the connection
					out.println("false");
				}
			}
			// System.out.println("Ending connection...");
			// sock.close();

		} catch (SQLException | IOException e) {
			//Either the SQL server connection has been lost or an IO error has occurred
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
