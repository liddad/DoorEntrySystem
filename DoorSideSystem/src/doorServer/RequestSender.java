package doorServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class RequestSender {

	public static void main(String args[]) {
		
		try{
			Socket sock = new Socket("192.168.1.94",6100);
			
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			out.println("UID: 1 Room: LT1101");
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			String m = in.readLine();
			System.out.println(m);
			sock.close();
		} catch(Exception e){
		}
	}
}
