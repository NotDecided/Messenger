package JavaSocketServerStuff;

import java.io.*;
import java.net.*;
import javax.swing.*;


public class ServerProgram extends JFrame
{
	
	private static Server server;
	
	
	public static void main(String[]args)
	{
		server = new Server();
		server.run();
		
		
		
	}
	
	
}