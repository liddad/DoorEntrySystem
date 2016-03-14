package doorServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Adam Liddell
 *
 * Listens for and accepts connections from Raspberry Pis and handles them in a separate thread
 */
public class ConnectionListener implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket sock = new ServerSocket(6100);
			Socket s;
			while(true){
				s = sock.accept();
				Thread t = new Thread(new RequestListener(s),"RequestListenerThread");
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
