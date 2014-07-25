


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.SwingUtilities;

import Login.LoginData;
import Messaging.Message;
import Registration.RegistrationData;

public class NewConnectionThread extends Thread
{
	
	private Socket connection;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private int userID;
	
	
	public NewConnectionThread(Socket connection)
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
		System.out.println("Started");
		boolean active = true;
	
		
		while(active)
		{
			try
			{
				
				
				Object[] inbox;
				
				
				inbox = (Object[])ois.readObject();
		
				if(inbox[0].equals("/SEND MESSAGE"))
				{
					
					/*int senderID = (int) inbox[1];
					String messageText = (String) inbox[2];
					int receiverID = (int) inbox[3];
					long sendingTime = (long) inbox[4];
					String sender_firstName = (String) inbox[5];
					String sender_lastName = (String) inbox[6];
					String sender_fullName = (String) sender_firstName + " " + sender_lastName;*/
					
					Message message = (Message) inbox[1];
					String sender = message.getFrom();
					String inboxMessage = message.getTo();
					String receiver = message.getMessage();
					
					System.out.println("Message " + inboxMessage + " | FROM : "+sender + " | TO : "+receiver);
					
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					
					/*ServerSide.displayMessage("From : "+senderID);
					ServerSide.displayMessage("To : "+receiverID);
					
					if(senderID != receiverID)
					{
						ServerSide.directMessage(senderID,receiverID, messageText,"MESSAGE",sender_fullName,sdf.format(sendingTime));
						DataBase.newMessage(senderID,receiverID,messageText,sendingTime,127001);
					}*/
					
					message = null;
			    	
				}
				else if(inbox[0].equals("/GET USERS ONLINE LIST"))
				{
					String numberOfUsersOnline = "Online "+String.valueOf(ServerSide.usersOnlineList.size()) +" Users.";
					Object[] toSend = {numberOfUsersOnline};
					int sendTo = (int) inbox[1];
					ServerSide.directMessage(sendTo, sendTo, numberOfUsersOnline,"MESSAGE","SERVER",getTime());
				}
				else if(inbox[0].equals("/DISCONNECT THE USER"))
				{
					int userID = (int) inbox[1];
					DataBase.logOut(userID);
					System.out.println("USER_ID : " + userID);
					ServerSide.disconnectTheUser(userID);
				}
				else if(inbox[0].equals("/GET THE USERS ONLINE LIST"))
				{
					ServerSide.directMessage((int)inbox[1], (int)inbox[1], "/ONLINE USERS LIST IS SENDING","QUERY","SERVER",getTime());
					int sendTo = (int)inbox[1];//send information to certain ID
					ServerSide.sendOnlineUsersList(sendTo);	
				}
				else if(inbox[0].equals("/ADD THE FRIEND"))
				{
					int userID = (int)inbox[1];
					int targetID = (int)inbox[2];
					DataBase.addTheFriend(userID, targetID);
				}
				else if(inbox[0].equals("/REMOVE THE FRIEND"))
				{
					int userID = (int)inbox[1];
					int targetID = (int)inbox[2];
					DataBase.removeTheFriend(userID, targetID);
				}
				else if(inbox[0].equals("/BLOCK THE USER"))
				{
					int userID = (int)inbox[1];
					int targetID = (int)inbox[2];
					DataBase.blockTheUser(userID, targetID);
					ServerSide.directMessage(userID, userID, "/USER WAS SUCCESSFULLY BLOCKED","QUERY","SERVER",getTime());
					
				}
				else if(inbox[0].equals("/UNBLOCK THE USER"))
				{
					int userID = (int)inbox[1];
					int targetID = (int)inbox[2];
					DataBase.unblockTheUser(userID,targetID);
				}
				else if(inbox[0].equals("/GET THE FRIENDS LIST"))
				{
					int userID = (int)inbox[1];
					ServerSide.directMessage(userID,userID,"/FRIENDS LIST IS SENDING","QUERY","SERVER",getTime());
					ServerSide.sendTheFriendsList(userID);
				}
				else if(inbox[0].equals("/GET THE BLOCKED USERS LIST"))
				{
					
					int userID = (int)inbox[1];
					ServerSide.directMessage(userID, userID, "/BLACKLIST IS SENDING", "QUERY","SERVER",getTime());
					ServerSide.sendBlacklist(userID);
					
				}
				else if(inbox[0].equals("/GET CHAT HISTORY"))
				{
					
					int senderID = (int)inbox[1];
					int receiverID = (int)inbox[2];
					ServerSide.directMessage(senderID, senderID, "/CHAT HISTORY IS SENDING", "QUERY", "SERVER", getTime());
					
					
					ServerSide.sendChatHistory(senderID, DataBase.getMessageHistory(senderID, receiverID));
					System.out.println("SERVER / MESSAGE HISTORY SIDE ="+DataBase.getMessageHistory(senderID, receiverID).length);
					
				}
				else if(inbox[0].equals("/DELETE THE MESSAGE"))
				{
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					int sent_from = (int)inbox[1];
					String message = (String)inbox[2];
					int sent_to = (int)inbox[3];
					long time = (long)inbox[4];
					
					DataBase.deleteTheMessage(sent_from,message,sent_to,time);
					
				}
				else if(inbox[0].equals("/CREATE NEW USER"))
				{
					
					int temporaryID = 80549612 + ServerSide.usersOnlineList.size();
					Object[] temporaryIdentity = {temporaryID,"-----","-----"};
					ServerSide.usersOnlineList.add(temporaryIdentity);
					
					RegistrationData newUserData = (RegistrationData) inbox[1];
					
					  if(DataBase.availabilityCheck(newUserData.getUsername(), newUserData.getEmail()) == true)
					    {
						   
					    	DataBase.createNewUser(newUserData.getUsername() ,newUserData.getPassword() ,
					    			              newUserData.getEmail() ,newUserData.getFirstName() ,
					    			              newUserData.getLastName() ,newUserData.getBirthDate());
					    	
					        ServerSide.createUserFolders(newUserData.getUsername());
					        
					        ServerSide.directMessage(-1, temporaryID, "/NEW USER WAS SUCCESSFULLY CREATED","QUERY","SERVER",getTime());
					        
					        for(int i = 0 ; i < ServerSide.usersOnlineList.size(); i++)
					        {
					        	Object[] infoContainer = ServerSide.usersOnlineList.get(i);
					        	if((int)infoContainer[0] == temporaryID)
					        	{
					        		ServerSide.usersOnlineList.remove(i);
					        	}
					        }
					    }
					    else 
					    {
					    	
					    	ServerSide.directMessage(-1, temporaryID, "/CREATING OF NEW USER FAILED","QUERY","SERVER",getTime());
					    	
					    	  for(int i = 0 ; i < ServerSide.usersOnlineList.size(); i++)
						        {
						        	Object[] infoContainer = ServerSide.usersOnlineList.get(i);
						        	if((int)infoContainer[0] == temporaryID)
						        	{
						        		ServerSide.usersOnlineList.remove(i);
						        	}
						        }
					    	
					    	
					    	
					    }
					  
				}
				else if(inbox[0].equals("/LOG IN"))
				{
					
					LoginData loginData = (LoginData)inbox[1];
					
					int temporaryID = 80549612+ServerSide.usersOnlineList.size();
					Object[] temporaryIdentity = {temporaryID,"-----","-----"};
					ServerSide.usersOnlineList.add(temporaryIdentity);
					
					System.err.println("Received password (NewConnectionThread) : "+loginData.getPassword());
					
				    if(DataBase.loginCheck(loginData.getUsername(), loginData.getPassword()))
				    {
				    	
				    	for(int i = 0; i < ServerSide.usersOnlineList.size(); i++)
				    	{
				    		Object[] infoContainer = ServerSide.usersOnlineList.get(i);
				    		if((int)infoContainer[0] == temporaryID)
				    		{
				    			ServerSide.usersOnlineList.remove(i);
				    		}
				    	}
				    	
				    	Object[] userIdentityInfo = DataBase.getUserFullNameAndID(
				    			                      loginData.getUsername(), loginData.getPassword());
				    	
				    	String firstName = (String)userIdentityInfo[0];
				    	String lastName = (String)userIdentityInfo[1];
				    	int user_ID = (int)userIdentityInfo[2];
				    	String userFullName = firstName +" "+lastName;
				    	
				    	int uolistSize = ServerSide.usersOnlineList.size();
				    	for(int i = 0 ; i < uolistSize ; i++)
				    	{
				    		Object[] infoContainer = ServerSide.usersOnlineList.get(i);
				    		
				    		if((int)infoContainer[0] == user_ID)
				    		{
				    			Object[] retrievedObject = ServerSide.usersOnlineList.get(i);
				    			
				    			if(ServerSide.usersOnlineList.indexOf(retrievedObject) != -1)
				    			{
				    				ServerSide.dTableModel.removeRow(ServerSide.usersOnlineList.indexOf(retrievedObject));
					    			ServerSide.usersOnlineList.remove(retrievedObject);
				    			}
				    			
				    		}
				    	}
				    	
				    	Object[] AddingOfUserToArrayList = {user_ID,firstName,lastName};
				    	ServerSide.usersOnlineList.add(AddingOfUserToArrayList);
						
						//we are adding new user to the server table 
						Object[] toAdd = {user_ID,firstName,lastName};
						ServerSide.dTableModel.addRow((Object[])toAdd);
						//
						
				    	//user info sending block
				    	Object[] userInfo = DataBase.getUserInfo(loginData.getUsername(), loginData.getPassword());
				    	String userFirstName = (String)userInfo[0];
				    	String userLastName = (String)userInfo[1];
				    	String userBirthDate = (String)userInfo[2];
				    	int userID = (int)userInfo[3];
				    	Object[] userInfoToSend = {"/SUCCESSFULLY SIGNED IN",userFirstName,userLastName,userBirthDate,userID};
				    	//
				    	
				    	ServerSide.sendUserInfo(user_ID, userInfoToSend);
				    	System.out.println("Stucked here ! ");
				    	//sendUserInfo(userName,userPassword);//send the user data back to user from the server to the client (from data base to server , and afterwards from server to client) 
				    	//startTheConversation();
				    }
				    else
				    {
				    	
				    	ServerSide.directMessage(-1, temporaryID, "/LOGGING IN FAILED","QUERY","SERVER",getTime());
				    	
				    	//ServerSide.usersOnlineList.remove(ServerSide.usersOnlineList.indexOf(temporaryID));
				    	
				    	for(int i = 0 ; i < ServerSide.usersOnlineList.size() ; i++)
				    	{
				    		Object[] infoContainer = ServerSide.usersOnlineList.get(i);
				    		if((int)infoContainer[0] == temporaryID)
				    		{
				    			ServerSide.usersOnlineList.remove(i);
				    		}
				    	}
				    	
				    	//("/LOGGING IN FAILED");
				    	//sendMessage(String.valueOf(DataBase.loginCheck(userName, userPassword)));
				    }
				}
				else if(inbox[0].equals("/LOG OUT"))
				{
					
					int user_ID = (int) inbox[1];
					
					
					ServerSide.directMessage(-1, user_ID, "/LOGGING OUT","QUERY","SERVER",getTime());
					
					
					for(int i = 0 ; i < ServerSide.usersOnlineList.size() ; i++)
					{
						Object[] infoContainer = ServerSide.usersOnlineList.get(i);
						if((int)infoContainer[0] == user_ID)
						{
							DataBase.logOut(user_ID);
							Object[] retrievedObject = ServerSide.usersOnlineList.get(i);
							ServerSide.dTableModel.removeRow(ServerSide.usersOnlineList.indexOf(retrievedObject));
							ServerSide.usersOnlineList.remove(retrievedObject);
							
						}
					}
					//ServerSide.usersOnlineList.remove(ServerSide.usersOnlineList.indexOf(user_ID));
					
					
				}
				
				
			
			}
			catch( IOException | ClassNotFoundException  ex)
			{
				ex.printStackTrace();
			}
			
		}
		
		
		
	}
	
	public void sendDirectMessage(final String message,final String sender_fullName,final String time,final int sent_from,final int sent_to)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {"/DIRECT MESSAGE",message,sender_fullName,time,sent_from,sent_to};//complete this transferring process
					oos.writeObject(toSend);
					
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
		});
		
		
	}
	
	public void sendQuery(final String query)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {query};
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
	
	public void sendFriendsList(final Object[] friendsList)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = friendsList;
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
	}
	
	public void sendBlacklist(final Object[] blacklist)
	{
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
			{
				try
				{
					Object[] toSend = blacklist;
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
		});
	}
	
	public void sendChatHistory(final Object[] chatHistory)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				
				try
				{
					Object[] toSend = chatHistory;
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
			}
		});
	}
	
	public void sendUserInfo(final Object[] info)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {info[0],info[1],info[2],info[3],info[4]};
					oos.writeObject(toSend);
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
					Object[] toSend = {"/DIRECT MESSAGE",message};
					oos.writeObject(toSend);
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
		});
		
			
	}
	
	public static String getTime()
	{
		
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		
		String time = hours+":"+minutes+":"+seconds;
		
		return time;
		
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