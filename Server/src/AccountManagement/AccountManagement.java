package AccountManagement;

import java.util.Calendar;

import DataBase.DataBase;
import Login.LoginData;
import Registration.RegistrationData;
import ServerAndConnectionHandler.ServerSide;


public class AccountManagement {

	
	
	
	
	public void createNewUser(RegistrationData newUserRegistrationData)
	{
		int temporaryID = 80549612 + ServerSide.usersOnlineList.size();
		Object[] temporaryIdentity = {temporaryID,"-----","-----"};
		ServerSide.usersOnlineList.add(temporaryIdentity);
		
		
		  if(DataBase.availabilityCheck(newUserRegistrationData.getUsername() ,
				                               newUserRegistrationData.getEmail()) == true)
		    {
			   
		    	DataBase.createNewUser(newUserRegistrationData.getUsername() ,newUserRegistrationData.getPassword() ,
		    			              newUserRegistrationData.getEmail() ,newUserRegistrationData.getFirstName() ,
		    			              newUserRegistrationData.getLastName() ,newUserRegistrationData.getBirthDate());
		    	
		        ServerSide.createUserFolders(newUserRegistrationData.getUsername());
		        
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
	
	
	public void signIn(LoginData loginData)
	{
		
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
	    	
	    	
	    	
	    	for(int i = 0 ; i < ServerSide.usersOnlineList.size() ; i++)
	    	{
	    		Object[] infoContainer = ServerSide.usersOnlineList.get(i);
	    		if((int)infoContainer[0] == temporaryID)
	    		{
	    			ServerSide.usersOnlineList.remove(i);
	    		}
	    	}
	    	
	    	
	    }
	    
	}
	
	public void signOut(int user_ID)
	{
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
		
	}
	
	public void addFriend(int mainUserID ,int targetID)
	{
		DataBase.addTheFriend(mainUserID ,targetID);
	}
	
	public void removeFriend(int mainUserID ,int targetID)
	{
		DataBase.removeTheFriend(mainUserID, targetID);
	}
	
	public void getFriendsList(int mainUserID)
	{
		ServerSide.directMessage(mainUserID ,mainUserID ,"/FRIENDS LIST IS SENDING" ,
				                   "QUERY" ,"SERVER" ,getTime());
		ServerSide.sendTheFriendsList(mainUserID);
	}
	
	public void blockUser(int mainUserID ,int targetID)
	{
		DataBase.blockTheUser(mainUserID, targetID);
		ServerSide.directMessage(mainUserID, mainUserID, "/USER WAS SUCCESSFULLY BLOCKED","QUERY","SERVER",getTime());
	}
	
	public void unblockUser(int mainUserID ,int targetID)
	{
		DataBase.unblockTheUser(mainUserID,targetID);
	}
	
	public void getBlockedUsersList(int mainUserID)
	{
		ServerSide.directMessage(mainUserID ,mainUserID ,"/BLACKLIST IS SENDING" ,
				                     "QUERY" ,"SERVER" ,getTime());
		ServerSide.sendBlacklist(mainUserID);
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
	
	
	
}
