package doorServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import loginAppServer.LoginConnectionHandler;

public class ConnectionListener {
	
	public static void main(String args[]) {
		(new Thread(new LoginConnectionHandler(), "LoginConnectionThread")).start();
		try {
			ServerSocket sock = new ServerSocket(6100);
			Socket s;
			while(true){
				s = sock.accept();
				System.out.println("Connected");
				Thread t = new Thread(new RequestListener(s),"RequestListenerThread");
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
