package doorServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class RequestListener implements Runnable {

	private InputStream is;
	private ObjectOutputStream oos;
	private SQLParser sql;
	private Socket sock;
	
	public RequestListener(Socket s) {
		InputStream is = null;
		ObjectOutputStream oos = null;
		try {
			is = s.getInputStream();
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sock = s;
		this.is = is;
		this.oos = oos;
		sql = new SQLParser();
	}

	@Override
	public void run() {
		try {
			Boolean accept = false;
			ObjectInputStream ois = new ObjectInputStream(is);
			DoorRequest r = (DoorRequest)ois.readObject();
			Person person = sql.getPerson(r.userCode);
			List<Criteria> criteria = sql.getCriteria(r.doorCode);
			
			for(Criteria c:criteria){
				if(person==null){
					break;
				}
				if(person.fitsCriteria(c)){
					accept = true;
					break;
				}
			}
			oos.writeObject(accept);
			sock.close();
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
