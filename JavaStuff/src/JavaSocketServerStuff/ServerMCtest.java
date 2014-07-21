package JavaSocketServerStuff;

import javax.swing.*;

public class ServerMCtest extends JFrame
{
	
	private static ServerMC serverMC;
	
	public static void main(String[]args)
	{
	
		serverMC = new ServerMC();
		serverMC.startTheServer();
		//new ServerMCtest();
		
	}
	public ServerMCtest()
	{
		
	}
	
}