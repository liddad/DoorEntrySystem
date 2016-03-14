package doorServer;

import java.util.Scanner;

/**
 * A class which parses and holds the information on a single request
 * to open a door
 * @author Adam Liddell
 *
 */
public class DoorRequest{
	
	public int userCode;
	public String doorCode;
	
	public DoorRequest(String s) throws Exception{
		Scanner scan = new Scanner(s);
		//First section should be "UID:"
		if(!scan.next().equals("UID:")){
			scan.close();
			throw new Exception();
		}
		userCode = scan.nextInt();
		//Next section should contain room
		if(!scan.next().equals("Room:")){
			scan.close();
			throw new Exception();
		}
		doorCode = scan.next();
		scan.close();
	}
		
}
