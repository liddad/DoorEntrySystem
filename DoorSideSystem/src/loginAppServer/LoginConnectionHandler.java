package loginAppServer;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class LoginConnectionHandler implements Runnable {

	@Override
	public void run() {
		try {
			SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket socket = (SSLServerSocket) socketFactory.createServerSocket(7070);
			SSLSocket sock;
			while (true) {
				sock = (SSLSocket) socket.accept();
				(new Thread(new LoginHandler(sock))).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
