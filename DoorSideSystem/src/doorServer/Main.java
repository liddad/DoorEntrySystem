package doorServer;

import loginAppServer.LoginConnectionHandler;

public class Main {

	public static void main(String[] args) {
		(new Thread(new LoginConnectionHandler(), "LoginConnectionThread")).start();
		(new Thread(new ConnectionListener(), "LoginConnectionThread")).start();
	}

}
