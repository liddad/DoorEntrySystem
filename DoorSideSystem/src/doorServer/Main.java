package doorServer;

import loginAppServer.LoginConnectionHandler;

/**
 * The main class to be run
 * @author Adam Liddell
 *
 */
public class Main {

	public static void main(String[] args) {
		(new Thread(new LoginConnectionHandler(), "LoginConnectionThread")).start();
		(new Thread(new ConnectionListener(), "LoginConnectionThread")).start();
	}

}
