package SocketThreadTests;


import java.io.*;
import java.net.*;

import javax.swing.*;

public class ConnectionsHandlerBackup extends Thread
{
	
	private final int PORT = 8888;
	private final String IP = "127.0.0.1";
	private Socket connection = null;
	public static DataInputStream inputFromStream;
	public static InputStream input;
	public static DataOutputStream outputToStream;
	public static OutputStream output;
	
	
	public ConnectionsHandlerBackup(Socket connection)
	{
		this.connection = connection;
		
	}
	
	public void run()
	{
		
		connect();
		
	}
	
	private void connect()
	{
		
		try
		{
			streamsSetup();
			chatGoesHere();
			
			
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}
	
	private void streamsSetup() throws IOException
	{
		
		showMessage("\n ! - Everything is ready to go\n");
		output = connection.getOutputStream();
		input = connection.getInputStream();
		outputToStream = new DataOutputStream(output);
		inputFromStream = new DataInputStream(input);
		
		
		
	}
	private void chatGoesHere() throws IOException
	{
		String message = "\nChat started!\n";
		do{
		
		typeAllowed(true);
		message = inputFromStream.readUTF();
		showMessage(message);
		
		}while(!message.contains("- END"));
		
		
	}
	public static void showMessage(final String message) 
	{
		
		SwingUtilities.invokeLater(new Runnable()
		{
			
			public void run()
			{
				serverSide.chatHistory.append(message);
			}
			
		});
		
	}
	public static void sendMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try {
					
					if(message != null && message.trim().length() > 0)
					{
					   outputToStream.writeUTF("\n ! - "+message +"\n");
					   showMessage("\n ! - "+message+"\n");
					}
					else
					{
					   showMessage("\n ! - You Can't Send An Empty Message"+"\n");
					}
				
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	public static void typeAllowed(final boolean ToF)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				serverSide.newMessage.setEditable(ToF);
			}
		});
	}
	
	
	
	
}

