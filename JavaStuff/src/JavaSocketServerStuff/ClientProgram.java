package JavaSocketServerStuff;

import java.io.*;
import java.net.*;
import javax.swing.*;


public class ClientProgram
{
	
	
	
	private static Client client;
	public static void main(String[]args)
	{
		
		client = new Client();
		client.run();
		
	}
	
	
}