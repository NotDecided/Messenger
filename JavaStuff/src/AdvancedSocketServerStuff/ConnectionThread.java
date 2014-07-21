package AdvancedSocketServerStuff;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class ConnectionThread extends Thread
{
	
	private Socket connection;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private int userID;
	
	
	public ConnectionThread(Socket connection)
	{
		
	    
		this.connection = connection;
		
		try
		{
			streamsSetup();
			//addNewUser();
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
	
	private void addNewUser()
	{
		try 
		{
			String newUser = ois.readUTF();
			SmultipleConnectionsTest.usersOnlineList.add(newUser);
			System.out.println(SmultipleConnectionsTest.usersOnlineList.size());
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		System.out.println("Started");
		boolean active = true;
	
		
		while(active)
		{
			try
			{
				
				
				Object[] inbox;
				connection.setKeepAlive(true);
				
				inbox = (Object[])ois.readObject();
				
				if(inbox[0].equals("/NEW USER"))
				{
					
					String user = (String) inbox[1];
					
					if(SmultipleConnectionsTest.usersOnlineList.indexOf(user) != -1)
					{
						SmultipleConnectionsTest.disconnectTheUser(user);
					}
					SmultipleConnectionsTest.usersOnlineList.add(user);
					
					//we are adding new user to the server table 
					Object[] toAdd ={user};
					SmultipleConnectionsTest.dTableModel.addRow((Object[])toAdd);
					//
					
					System.out.println("Successfully added! |"+user);
					
					if(SmultipleConnectionsTest.usersOnlineList.size()>0)
					{
						SmultipleConnectionsTest.notifyClients("User "+user+" is online"+"\n"+inbox[2]+"\n");
					}
					
				}
				else if(inbox[0].equals("/SEND MESSAGE"))
				{
					
					String sender = (String) inbox[1];
					String messageText = (String) inbox[2];
					String receiver = (String) inbox[3];
					String sendingTime = (String) inbox[4];
					
					String message = "\n"+""+sender+":"+"\n" +"\n"+messageText +"\n"+ "\n"+sendingTime+"\n";
					
					SmultipleConnectionsTest.displayMessage("From : "+sender);
					SmultipleConnectionsTest.displayMessage("To : "+receiver);
					
					if(!sender.equals(receiver))
					{
						SmultipleConnectionsTest.directMessage(sender,receiver, message);
					}
			    	//SmultipleConnectionsTest.directMessage(sender,sender, message);
					
					
				
				    
				}
				else if(inbox[0].equals("/GET USERS ONLINE LIST"))
				{
					String numberOfUsersOnline = "Online "+String.valueOf(SmultipleConnectionsTest.usersOnlineList.size()) +" Users.";
					Object[] toSend = {numberOfUsersOnline};
					String sendTo = (String) inbox[1];
					//ArrayList<String> usersOnline = new ArrayList<String>();
					//String usersData = "~USERS DATA";
					//usersOnline.add(usersData);
					//usersOnline.add(numberOfUsersOnline);
					//for(int i=0; i< SmultipleConnectionsTest.usersOnlineList.size();i++)
					//{
					//	usersOnline.add(SmultipleConnectionsTest.usersOnlineList.get(i));
					//}
					//String[] toSend = (String[])usersOnline.toArray();
					SmultipleConnectionsTest.directMessage(sendTo, sendTo, numberOfUsersOnline);
				}
				else if(inbox[0].equals("/DISCONNECT THE USER"))
				{
					String userNickName = (String) inbox[1];
					System.out.println(userNickName);
					SmultipleConnectionsTest.disconnectTheUser(userNickName);
				}
				//SmultipleConnectionsTest.notifyClients(inbox);
				else if(inbox[0].equals("/SEND USERS ONLINE LIST"))
				{
					SmultipleConnectionsTest.directMessage((String)inbox[1], (String)inbox[1], "/ONLINE USERS LIST IS SENDING");
					String sendTo = (String)inbox[1];
					SmultipleConnectionsTest.sendOnlineUsersList(sendTo);	
				}
				
				
			
			}
			catch( IOException | ClassNotFoundException  ex)
			{
				ex.printStackTrace();
			}
			
		}
		
		
		
	}
	
	public void sendDirectMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {message};//complete this transferring process
					oos.writeObject(toSend);
					
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
		});
		
		
	}
	
	public void sendOnlineList(final Object[] onlineUsersList)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					oos.writeObject(onlineUsersList);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	public void messageToAllClients(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try {
					Object[] toSend = {message};
					oos.writeObject(toSend);
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
		});
		
			
	}
	
	public boolean connectionCheck()
	{
		try
		{
			Object[] toSend = {"/CONNECTION CHECK"};
			oos.writeObject(toSend);
			return true;
		}
		catch(IOException ex)
		{
			
			ex.printStackTrace();
			return false;
		}
	
	}
	
	
}