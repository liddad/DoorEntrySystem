package loginAppServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class LoginConnectionHandler implements Runnable {

	@Override
	public void run() {
		try {
			Socket sock;
			ServerSocket ss = new ServerSocket(7070);
			while (true) {
				sock = ss.accept();
				(new Thread(new LoginHandler(sock),"LoginHandlerThread")).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
