package ServerAndConnectionHandler;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.SwingUtilities;

import AccountManagement.AccountManagement;
import DataBase.DataBase;
import Login.LoginData;
import Messaging.Message;
import Messaging.MessageManagement;
import Registration.RegistrationData;

public class NewConnectionThread extends Thread
{
	
	//handlers
	private AccountManagement accountManager;
	private MessageManagement messageManager;
	//
	
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
		
		//
		accountManager = new AccountManagement();
		messageManager = new MessageManagement();
		//
		
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
					//
					messageManager.sendDirectMessage((Message) inbox[1]);
			    	//
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
					//
					accountManager.addFriend((int) inbox[1] ,(int) inbox[2]);
					//
				}
				else if(inbox[0].equals("/REMOVE THE FRIEND"))
				{
					//
					accountManager.removeFriend((int) inbox[1] ,(int) inbox[2]);
					//
				}
				else if(inbox[0].equals("/BLOCK THE USER"))
				{
					//
					accountManager.blockUser((int) inbox[1],(int) inbox[2]);
					//
				}
				else if(inbox[0].equals("/UNBLOCK THE USER"))
				{
					//
					accountManager.unblockUser((int) inbox[1],(int) inbox[2]);
				    //
				}
				else if(inbox[0].equals("/GET THE FRIENDS LIST"))
				{
					//
					accountManager.getFriendsList((int) inbox[1]);
					//
				}
				else if(inbox[0].equals("/GET THE BLOCKED USERS LIST"))
				{
					//
					accountManager.getBlockedUsersList((int) inbox[1]);
					//
				}
				else if(inbox[0].equals("/GET CHAT HISTORY"))
				{
					//
					messageManager.getChatHistory((int) inbox[1] ,(int) inbox[2]);
				    //
				}
				else if(inbox[0].equals("/DELETE THE MESSAGE"))
				{
					//
					messageManager.deleteMessageFromHistory((int) inbox[1] ,(int) inbox[3] ,
							                                  (String) inbox[2] ,(long) inbox[4]);
					//
				}
				else if(inbox[0].equals("/CREATE NEW USER"))
				{
					//
					accountManager.createNewUser((RegistrationData) inbox[1]);
					//
				}
				else if(inbox[0].equals("/LOG IN"))
				{
					//
					accountManager.signIn((LoginData) inbox[1]);
					//
				}
				else if(inbox[0].equals("/LOG OUT"))
				{
				    //
					accountManager.signOut((int) inbox[1]);
					//
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