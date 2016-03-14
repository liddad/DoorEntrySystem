package doorServer;

import java.io.Serializable;
import java.util.Scanner;

public class DoorRequest implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = -5847993936875893839L;
	
	public int userCode;
	public String doorCode;
	
	public DoorRequest(String s) throws Exception{
		Scanner scan = new Scanner(s);
		//First section should be "UID:"
		if(!scan.next().equals("UID:")){
			throw new Exception();
		}
		userCode = scan.nextInt();
		if(!scan.next().equals("Room:")){
			throw new Exception();
		}
		doorCode = scan.next();
	}
		
}
