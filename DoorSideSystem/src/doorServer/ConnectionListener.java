package doorServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener {
	
	public static void main(String args[]) {
		try {
			ServerSocket sock = new ServerSocket(6100);
			Socket s;
			while(true){
				s = sock.accept();
				System.out.println("Connected");
				Thread t = new Thread(new RequestListener(s));
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
