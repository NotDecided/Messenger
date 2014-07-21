package AdvancedSocketServerStuff;


import java.sql.*;
import java.util.ArrayList;
import java.io.*;

import javax.swing.SwingUtilities;


public class DataBase 
{
	
	private static Connection connection;
	public static Statement statement;
	public static ResultSet query ;
	
	
	public static void startDataBase()
	{
		start();
	}
	
	private static void start()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/messengerdatabase", "root", "Arthur12061997");
			
			statement = connection.createStatement();
			
			query = statement.executeQuery("show tables;");
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex1)
		{
			ex1.printStackTrace();
		}
		
		
	}
	
	public static void createNewUser(String userName,String userPassword , String userEmail , String userFirstName , String userLastName, String userBirthDate)
	{
		
		try
		{
			//complete this step
			String creatingOfNewUser = "insert into users set username=\'"+userName+"\' ,password=\'"+userPassword+
					              "\' , email=\'"+userEmail+"\' ,firstName=\'"+userFirstName+"\' , lastname=\'"+userLastName+
					                   "\' , birthDate=\'"+userBirthDate+"\' ;";
			System.out.println(creatingOfNewUser);
			statement.executeUpdate(creatingOfNewUser);//inserts new line with new users information , in other words creates new user with given information
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	public static void newMessage(int sent_from,int sent_to,String message,long time,int ip)
	{
		try
		{
			
			String addIntoMessageHistory = "insert into privatemessages set sent_from=\""+sent_from+"\" ,"
					+ " message=\""+message+"\" , sent_to=\""+sent_to+"\" ,sender_deleted=\"NO\",receiver_deleted=\"NO\","
							+ " time=\""+time+"\" , ip=\""+ip+"\" ;";
			
			statement.execute(addIntoMessageHistory);
			System.out.println("everything went good ! , message successfully added into message history table!");
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void addTheFriend(int user_ID,int target_ID)
	{
		
		try
		{
			String friendCheckment = " select friend_one, friend_two from friends "
					         + "where friend_one=\""+user_ID+"\" and friend_two=\""+target_ID+"\" ; ";
			
			query = statement.executeQuery(friendCheckment);
			
			int userID_checkment = 0;
			int targetID_checkment = 0;
			
			while(query.next())
			{
				userID_checkment = query.getInt("friend_one");
				targetID_checkment = query.getInt("friend_two");
			}
			
			if(user_ID == userID_checkment && target_ID != targetID_checkment)
			{
				String addingOfTheFriend = "insert into friends set friend_one=\""+user_ID+"\" , friend_two=\""+target_ID+"\" ; " ; 
				System.out.println(addingOfTheFriend);
				
				statement.executeUpdate(addingOfTheFriend);
			}
			else if(user_ID != userID_checkment)
			{
				String addingOfTheFriend = "insert into friends set friend_one=\""+user_ID+"\" , friend_two=\""+target_ID+"\" ; " ; 
				System.out.println(addingOfTheFriend);
				
				statement.executeUpdate(addingOfTheFriend);
			}
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public static void removeTheFriend(int user_ID,int target_ID)
	{
		try
		{
			String removingOfTheFriend = "delete from friends where friend_one=\""+user_ID+"\" and friend_two=\""+target_ID+"\" ;";
			
			statement.execute(removingOfTheFriend);
			
			System.out.println("friend with id:"+target_ID+"was successfully removed from user "+user_ID+" friends list !");
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void blockTheUser(int user_ID,int target_ID)
	{
		try
		{
			
			String blacklistCheckment = "select main_user_id , blocked_user_id from blacklist "
					+ " where main_user_id=\""+user_ID+"\" and blocked_user_id=\""+target_ID+"\" ;";
			
			query = statement.executeQuery(blacklistCheckment);
			
			int user_ID_checkment = 0;
			int target_ID_checkment = 0;
			
			while(query.next())
			{
				user_ID_checkment = query.getInt("main_user_id");
				target_ID_checkment = query.getInt("blocked_user_id");
			}
			
			if(user_ID_checkment == 0 && target_ID_checkment == 0)
			{
				String blockingOfTheUser = "insert into blacklist set main_user_id=\""+user_ID+"\" , blocked_user_id=\""+target_ID+"\" ;";
				
				statement.execute(blockingOfTheUser);
				
				removeTheFriend(user_ID,target_ID);
				
			}
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void unblockTheUser(int user_ID,int target_ID)
	{
		try
		{
			String queryUserUnblocker = "delete from blacklist where main_user_id=\""+user_ID+"\" and blocked_user_id=\""+target_ID+"\" ;";
			
			statement.execute(queryUserUnblocker);
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static boolean loginCheck(String userName, String userPassword)
	{
		
		
		//here go all verifying processes
		try
		{
			String sendQuery ="select username , password from users "
					+ "where username=\""+userName+"\" and password=\""+userPassword+"\" ;"; 
			query = statement.executeQuery(sendQuery);
			
			String usernameToCheck ="";
			String passwordToCheck ="";
			
			while(query.next())
			{
				
				System.err.println("Checked : "+query.getString("username") +"   &&   "+query.getString("password"));
				usernameToCheck = query.getString("username");
				passwordToCheck = query.getString("password");
			
			}
			
			
			if(userName.equals(usernameToCheck)  && userPassword.equals(passwordToCheck))
			{
				
				String logInQuery = "update users set onlineStatus=\"online\" where username=\""+userName+"\" ;";
				statement.execute(logInQuery);

				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public static void logOut(int user_ID)
	{

		try
		{
			
			String logOutQuery = "update users set onlineStatus=\"offline\" where user_id=\""+user_ID+"\" ;";//gotta complete this method
		    statement.execute(logOutQuery);
		
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	
	}
	
	public static Object[] getFriendsList(int user_ID)
	{
		try
		{
			
			ArrayList<Integer> IDList = new ArrayList<Integer>();
			ArrayList<Object[]> friendsInfoList = new ArrayList<Object[]>();
			
			String queryFriendIDGetter = "select friend_two from friends where friend_one=\""+user_ID+"\" ;";
			
			query = statement.executeQuery(queryFriendIDGetter);
			
			while(query.next())
			{
				IDList.add(query.getInt("friend_two"));
			}
			
			for(int i = 0 ; i < IDList.size() ; i++)
			{
				
				String queryFriendInfoGetter = "select user_id,firstName,lastName,onlineStatus from users where user_id=\""+IDList.get(i)+"\" limit 1 ;";
				
				query = statement.executeQuery(queryFriendInfoGetter);
				
				while(query.next())
				{
					Object[] friendInfo = {query.getInt("user_id"),query.getString("firstName"),query.getString("lastName"),query.getString("onlineStatus")};
					
					friendsInfoList.add(friendInfo);
				}
				
			}
			
			
			
			Object[] friendsInfoToReturn = friendsInfoList.toArray();
			return friendsInfoToReturn;
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static Object[] getBlacklist(int user_ID)
	{
		try
		{
			
			String queryBlockedUsersGetter = "select blocked_user_id from blacklist where main_user_id=\""+user_ID+"\" ;";
			
			query = statement.executeQuery(queryBlockedUsersGetter);
			
			
			ArrayList<Integer> blockedUsersIDs = new ArrayList<Integer>();
			ArrayList<Object[]> blockedUsersInfo = new ArrayList<Object[]>();
			
			while(query.next())
			{
				int newID = query.getInt("blocked_user_id");
				blockedUsersIDs.add(newID);
			}
			
			for(int i = 0 ; i < blockedUsersIDs.size() ; i++)
			{
				try
				{
					
					int blockedUserID = blockedUsersIDs.get(i);
					
					String blockedUsersInfoGetter = "select firstName , lastName from users where user_id=\""+blockedUserID+"\" limit 1 ;";
					
					query = statement.executeQuery(blockedUsersInfoGetter);
					
					while(query.next())
					{
						String firstName = query.getString("firstName");
						String lastName = query.getString("lastName");
						
						Object[] infoToAdd = {blockedUserID,firstName,lastName};
						
						blockedUsersInfo.add(infoToAdd);
						
					}
					
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				
			}//end of for loop
			
			Object[] blacklistToReturn = blockedUsersInfo.toArray();
			
			return blacklistToReturn;
			
			
		}
		
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;//returns null if some exception happens
		
	}
	
	public static boolean sendingPermission(int sender_ID,int receiver_ID)
	{
		try
		{
			
			String queryBlacklistCheckment = "select blacklist_id from blacklist "
					+ "where main_user_id=\""+receiver_ID+"\" and blocked_user_id=\""+sender_ID+"\" ;";
			
			query = statement.executeQuery(queryBlacklistCheckment);
			
			int blacklistCheckment = 0;
			
			while(query.next())
			{
				blacklistCheckment = query.getInt("blacklist_id");//checks if sender exists in blacklist
			}
			
			if(blacklistCheckment == 0)//if blacklistCheckment remains equal to 0 , then it means that this sender doesn't exist in the blacklist of that receiver
		    //and allows the sender to send the message to that receiver
			{
				return true;
			}
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public static Object[] getUserInfo(String username , String password)
	{
		
		try
		{
			String queryToExecute = "select firstname,lastname,birthdate,user_id from users where username=\""+username+"\" and password=\""+password+"\"; ";
			
			query = statement.executeQuery(queryToExecute);
			
			int user_id = 0;
			String firstName ="";
			String lastName ="";
			String birthDate ="";
			
			while(query.next())
			{
				
				firstName = query.getString("firstname");
				lastName = query.getString("lastname");
				birthDate = query.getString("birthdate");
				user_id = query.getInt("user_id");
			}
			
			Object[] userInfo ={firstName,lastName,birthDate,user_id};
			
			return userInfo;
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static String getUserFullName(int user_ID)
	{
		try
		{
			
			String fullNameGetter= "select firstName,lastName from users where user_id=\""+user_ID+"\" ;";
			
			query = statement.executeQuery(fullNameGetter);
			
			String firstName = "";
			String lastName ="";
			
			while(query.next())
			{
				
				firstName = query.getString("firstName");
				lastName = query.getString("lastName");
				
			}
			
			return firstName + " " + lastName;
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static String getUserID(String username)
	{
		
		try
		{
			
			String queryToExecute ="";
			
			query = statement.executeQuery("select user_id from users where username=\""+username+"\"");
			
			String ID = "";
			
			while(query.next())
			{
				ID = String.valueOf(query.getInt("user_id"));
			}
			
			return ID;
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
		
	}
	
	public static Object[] getUserFullNameAndID(String username,String password)
	{
		try
		{
			String queryToExecute = "select firstname,lastname ,user_id from users "
					+ " where username=\""+username+"\" and password=\""+password+"\" ;";
			
			query = statement.executeQuery(queryToExecute);
			
			String firstName = "";
			String lastName = "";
			int user_ID = 0;
			
			while(query.next())
			{
				firstName = query.getString("firstname");
				lastName = query.getString("lastname");
				user_ID = query.getInt("user_id");
			}
			
			Object[] toReturn = {firstName,lastName,user_ID};
			
			return toReturn;
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
		
	}
	
	public static Object[] getMessageHistory(int senderID,int receiverID)
	{
		try
		{//complete this method and implement the functional on the client side
			System.err.println("received IDs ="+senderID + " ; "+receiverID);
			String queryDataRetriever = "select sent_from,message,sent_to,time from privatemessages where "
					+ "sent_from=\""+senderID+"\"  and sent_to=\""+receiverID+"\" "
							+ "or sent_from=\""+receiverID+"\" and sent_to=\""+senderID+"\" ;";
			
			ArrayList<Object[]>  chatHistory = new ArrayList<Object[]>();
			ArrayList<Object[]>  chatHistoryToReturn = new ArrayList<Object[]>();
			
			query = statement.executeQuery(queryDataRetriever);
			  
			
			int retrievedSenderID = 0;
			int retrievedReceiverID = 0;
			String message = "";
			long retrievedTime = 0;
			
			while(query.next())
			{
				
				retrievedSenderID = query.getInt("sent_from");
				message = query.getString("message");
				retrievedReceiverID = query.getInt("sent_to");
				retrievedTime = query.getLong("time");
				
				
				
				Object[] toAddToTheChatHistory = {retrievedSenderID,message,retrievedReceiverID,retrievedTime};
				
				chatHistory.add(toAddToTheChatHistory);
				System.err.println("+1 message to retrievedMessages array list"+" STRING MESSAGE = "+"From "+
				query.getInt("sent_from")+" to "+query.getInt("sent_to")+query.getString("message")+ " Time "+retrievedTime);
				
			}
			
			for(int i = 0 ; i < chatHistory.size() ; i++)
			{
				Object[] toAdd = {getUserFullName((int)chatHistory.get(i)[0]),chatHistory.get(i)[1],getUserFullName((int)chatHistory.get(i)[2]),(long)chatHistory.get(i)[3],retrievedSenderID,retrievedReceiverID};
				chatHistoryToReturn.add(toAdd);
				System.err.println("Size of chat history = "+chatHistoryToReturn.size());
			}
		
			System.err.println("Size of chat history = "+chatHistory.size());
			Object[] toReturn = chatHistoryToReturn.toArray();
			
			return toReturn;
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		System.err.println("SHIT!");
		return null;
		
	}
	
	public static void deleteTheMessage(int sent_from , String message , int sent_to , long time)
	{
		
		try
		{
			
			String query = "delete from privatemessages where sent_from=\""+sent_from+"\" and message=\""+message+"\" "
					+ "and sent_to=\""+sent_to+"\" and time=\""+time+"\" ;";
			
			statement.execute(query);
			
			System.out.println("Message was successfully deleted!");
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public static boolean availabilityCheck(String userName,String userEmail)
	{
		//here goes availability check process
		try
		{
			String queryToExecute = "select username , email from users "
					+ " where username=\""+userName+"\" ;";//need more advanced verifying system , gotta complete this stuff as soon as I'll come back
			
			query = statement.executeQuery(queryToExecute);
			
			String userNameToCheck ="";
			String userEmailToCheck ="";
			
			while(query.next())
			{
				
				userNameToCheck = query.getString("username");
				userEmailToCheck = query.getString("email");
				
				
			}
			System.out.println("DB side :"+userNameToCheck);
			
			String queryToExecute2 = "select  email from users where email=\""+userEmail+"\" ;";
			
			query = statement.executeQuery(queryToExecute2);
			
			String emailVerifying ="";
			
			while(query.next())
			{
				emailVerifying = query.getString("email");
			}
			
			if(userNameToCheck.trim().length() == 0 || userEmailToCheck.trim().length() ==0)
			{
				if(emailVerifying.trim().length() == 0)
				{
				 return true;
				}
				else
				{
				 return	false;
				}
			}
			else
			{
				return false;
			}
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
}