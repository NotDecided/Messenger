package AdvancedSocketServerStuff;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import java.net.*;
import java.util.ArrayList;

public class Server 
{
	
	private static final int PORT = 8888;
	
	private ServerSocket serverSocket = null;
	private Socket clientConnection = null;
	
	
	public static JTextArea display;
	
	public static JTextField newMessage;
	
	public static ArrayList<NewClientThread> connectionsList;
	
	public static void main(String[]args)
	{
		new Server();
	}
	
	public Server()
	{
		boolean active = true;
		connectionsList = new ArrayList<NewClientThread>();
		setupGUI();
		
		try
		{
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server was successfully created on port : "+PORT);
			
			while(active)
			{
				try
				{

					//streamsSetup();
					connectionEstablishment();
					DataBase.startDataBase();
					//commandsRecognizing();
				}
				catch(IOException ex1)
				{
					ex1.printStackTrace();
				}
			}
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void setupGUI()
	{
		JFrame frame = new JFrame();
		frame.setSize(400, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Server");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		display = new JTextArea();
		display.setEditable(false);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setText("");
		frame.add(display,BorderLayout.NORTH);
		
		newMessage = new JTextField();
		newMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				NewClientThread.sendMessage(e.getActionCommand());
				newMessage.setText("");
			}
		});
		frame.add(newMessage,BorderLayout.SOUTH);
		
		JScrollPane scroller = new JScrollPane(display,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroller);
		
	}
	
	private void connectionEstablishment() throws IOException
	{
		
		clientConnection = serverSocket.accept();
		NewClientThread thread = new NewClientThread(clientConnection);
		connectionsList.add(thread);
		thread.start();
		System.out.println("Client with IP : "+clientConnection.getLocalSocketAddress() +" connected");
		
	}
	

	
	/*private void commandsRecognizing() throws IOException
	{
		
		String message = "";
		do
		{
			
		message = ois.readUTF();
		displayTheMessage(message);
		
		if(message.equals("/CREATE A NEW USER"))
		{
			displayTheMessage("Message received!");
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
		        createUserFolders(userName);
		        sendMessage("/NEW USER WAS SUCCESSFULLY CREATED");
		        //sendMessage("/LOGGING IN");
		    }
		    else 
		    {
		    	sendMessage("/CREATING OF NEW USER FAILED");
		    }
		    
		    //complete this registration stuff and all that database operating stuff
			
		}
		
		if(message.equals("/RESET STREAMS"))
		{
			resetStreams();
		}
		
		if(message.equals("/LOG IN"))
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
		    	startTheConversation();
		    }
		    else
		    {
		    	sendMessage("/LOGGING IN FAILED");
		    	sendMessage(String.valueOf(DataBase.loginCheck(userName, userPassword)));
		    }
		}
		}while(!message.equals("/CLOSE"));
		
		
	}*/ //END OF CommandsRecognizing function
	
	private void displayTheMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				display.append("\n"+message+"\n");
			}
		}
		);
	}
	
	/*private void sendMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					oos.writeUTF(message);
					displayTheMessage(message);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}*/
	
	/*private void sendUserInfo(String username ,String password)
	{
		String[] userInfo = new String[2];
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
			  
		
	}*/
	
	public static void createUserFolders(String username)
	{
		try
		{
			String userID = DataBase.getUserID(username);
			
			File mainFolder = new File("./Server/",userID);
			
			mainFolder.mkdir();
			
			File messageHistoryFolder = new File("./Server/"+userID,"MessageHistory");
			
			messageHistoryFolder.mkdir();
			
			File mediaFolder = new File("./Server/"+userID,"Media");
			
			mediaFolder.mkdir();
			
			File imageFolder = new File("./Server/"+userID+"/Media","Images");
			
			imageFolder.mkdir();
			
			File videoFolder = new File("./Server/"+userID+"/Media","Videos");
			
			videoFolder.mkdir();
			
			System.out.println("All User folders were successfully created!");
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	

	
	
}