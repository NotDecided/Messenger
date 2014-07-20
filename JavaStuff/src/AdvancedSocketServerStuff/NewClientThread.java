package AdvancedSocketServerStuff;


import java.io.*;
import java.net.*;

import javax.swing.SwingUtilities;


public class NewClientThread extends Thread
{
	
	private Socket connection;
	
	private ObjectInputStream ois;
	private static ObjectOutputStream oos;
	
	
	public NewClientThread(Socket connection)
	{
		
		this.connection = connection;
		
		try
		{
			streamsSetup();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	
	
	private void streamsSetup() throws IOException
	{
	    ois = new ObjectInputStream(connection.getInputStream());
	    oos = new ObjectOutputStream(connection.getOutputStream());
	}
	
	
	public void run()
	{
		boolean active = true;
		
		
		while(active)
		{
			String inbox ="";
			try
			{
				inbox = ois.readUTF();
				
				do
				{
				
				displayMessage(inbox);
				
				if(inbox.equals("/CREATE A NEW USER"))
				{
					displayMessage("Message received!");
					System.err.println("Message received!");
					//sendMessage("/SEND NEW USER DATA");
					
					
					
					String[] userData = null;
					try
					{
					userData = (String[]) ois.readObject();
					}
					catch(ClassNotFoundException ex)
					{
						ex.printStackTrace();
					}
					
					
					
					String userName = (String) userData[0];
					String userPassword = (String)userData[1];
				    String userEmail = (String)userData[2];
				    String userFirstName = (String)userData[3];
				    String userLastName = (String)userData[4];
				    String userBirthDate = (String)userData[5];
				    
				    System.err.println("Password received from the client : "+userPassword +" userName : "+userName +" Email : "+ userEmail +" FirstName : "+userFirstName);
				    
				    if(DataBase.availabilityCheck(userName, userEmail) == true)
				    {
				    	
				        DataBase.createNewUser(userName, userPassword, userEmail, userFirstName, userLastName, userBirthDate);
				        Server.createUserFolders(userName);
				        sendMessage("/NEW USER WAS SUCCESSFULLY CREATED");
				        //sendMessage("/LOGGING IN");
				    }
				    else 
				    {
				    	sendMessage("/CREATING OF NEW USER FAILED");
				    }
				    
				    //complete this registration stuff and all that database operating stuff
					
				}
				
				if(inbox.equals("/RESET STREAMS"))
				{
					//resetStreams();
				}
				
				if(inbox.equals("/LOG IN"))
			    {
					//sendMessage("/SEND USER NAME AND PASSWORD");
					
					String[] userData = null;
					
					try
					{
					userData = (String[])ois.readObject();
					}
					catch(ClassNotFoundException ex)
					{
						ex.printStackTrace();
					}
					
					String userName = (String)userData[0];
					String userPassword = (String)userData[1];
					System.err.println("Received password : "+userPassword);
					
				    if(DataBase.loginCheck(userName, userPassword))
				    {
				    	//sendMessage(String.valueOf(DataBase.loginCheck(userName, userPassword)));//ensures if user really passed security confirmation
				    	sendMessage("/SUCCESSFULLY SIGNED IN");
				    	sendUserInfo(userName,userPassword);//send the user data back to user from the server to the client (from data base to server , and afterwards from server to client) 
				    	//startTheConversation();
				    }
				    else
				    {
				    	sendMessage("/LOGGING IN FAILED");
				    	sendMessage(String.valueOf(DataBase.loginCheck(userName, userPassword)));
				    }
				}
				}while(!inbox.equals("/CLOSE"));
				
				
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
			
			
		}
		
		
	}
	
	private void sendUserInfo(String username ,String password)
	{
	    Object[] userInfo ={};
		userInfo = DataBase.getUserInfo(username, password);//gets specific info about certain user and stores that info in our string object
		
		System.err.println("SERVER : " +"First Name : "+userInfo[0]+" , Last Name : "+userInfo[1]+" , Birth Date : "+userInfo[2]);
		
		   try
		   {
			   oos.writeObject(userInfo);
		   }
		   catch(IOException ex)
		   {
			   ex.printStackTrace();
		   }
			  
		
	}
	
	public static void sendMessage(String message)
	{
		try
		{
			oos.writeUTF(message);
			displayMessage(message);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static void displayMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				Server.display.append("\n"+message+"\n");
			}
		});
	}
	
	
	
}