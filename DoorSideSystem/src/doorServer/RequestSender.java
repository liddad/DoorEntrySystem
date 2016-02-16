package doorServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class RequestSender {

	public static void main(String args[]) {
		
		try{
			Socket sock = new Socket("127.0.0.1",6100);
			
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			
			ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
			DoorRequest dr = new DoorRequest();
			dr.doorCode = "R543";
			dr.userCode = 1111111111;
			out.writeObject(dr);
			ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
			
			Boolean m = (Boolean) in.readObject();
			System.out.println(m);
		} catch(Exception e){
			
		}
	}
}
