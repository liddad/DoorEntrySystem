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
			while (true) {
				Boolean accept = false;
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				String s = in.readLine();
				System.out.println(s);
				DoorRequest r = new DoorRequest(s);
				PrintWriter out = new PrintWriter(os, true);
				Person person = sql.getPerson(r.userCode);
				List<Criteria> criteria = sql.getCriteria(r.doorCode);

				for (Criteria c : criteria) {
					if (person == null) {
						break;
					}
					if (person.fitsCriteria(c)) {
						accept = true;
						break;
					}
				}

				out.println(accept.toString());
			}
			// System.out.println("Ending connection...");
			// sock.close();

		} catch (Exception e) {
			PrintWriter out = new PrintWriter(os, true);
			out.println("false");
			System.out.println("Closing...");
			try {
				sock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
