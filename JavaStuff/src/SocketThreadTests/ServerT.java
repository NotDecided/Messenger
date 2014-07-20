package SocketThreadTests;

import javax.swing.*;

public class ServerT 
{
	
	private static serverSide serverLauncher;
	
	public static void main(String[]args)
	{
		
		
		new ServerT();
		
		
	}

	public ServerT()
	{
		
		serverLauncher = new serverSide();
		serverLauncher.startTheServer();
		
		
	}

	
}