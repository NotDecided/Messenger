package AdvancedSocketServerStuff;

import java.awt.BorderLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ServerSide
{
	
	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
	private ServerSocket server;
    private Socket connection;
    private static ArrayList<NewConnectionThread> connectionsList;
    public static ArrayList<Object[]> usersOnlineList; //gotta finish direct messages
	
    private JTextField newMessage;
    
    private static JTextArea eventHistory;
    
    public static JTable onlineUsersTable;
    private Object[][] databaseInfo;
    private Object[] column;
    Object[] allOnlineUsers;
    public static DefaultTableModel dTableModel;
    private JPopupMenu popupMenu;
    
    
	public void start()
	{
		connectionsList = new ArrayList<NewConnectionThread>();
		usersOnlineList = new ArrayList<Object[]>();
		
		boolean active = true;
		
		
		try
		{
			setupGUI();
			server = new ServerSocket(PORT);
			
			DataBase.startDataBase();//starting out database
		
			while(active)
			{
				try
				{
				
				   connectionEstablishment();
				   
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
				
			}
			//removeUserConnection();
			
		    
	
		}
		catch(IOException ex1)
		{
			ex1.printStackTrace();
		}
	}
	
	private void setupGUI()
	{
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
			{
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setSize(700,550);
				frame.setLocationRelativeTo(null);
				frame.setTitle("SERVER");
				
				
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JPanel thePanel = new JPanel();
				thePanel.setLayout(null);
				
				eventHistory = new JTextArea();
				eventHistory.setEditable(false);
				eventHistory.setLineWrap(true);
				eventHistory.setBounds(50, 0, 300, 450);
				thePanel.add(eventHistory);
				
				JScrollPane scroller = new JScrollPane(eventHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroller.setBounds(50, 0,300 , 450);
				thePanel.add(scroller);
				
				newMessage = new JTextField();
				newMessage.setBounds(50,450,300,25);
				newMessage.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						notifyClients(e.getActionCommand());
						newMessage.setText("");
						
					}
				});
				thePanel.add(newMessage,BorderLayout.CENTER);
				
				
				
				JPanel onlineUsersPanel = new JPanel();
				onlineUsersPanel.setLayout(null);
				onlineUsersPanel.setSize(250, 500);
				
				dTableModel = new DefaultTableModel(databaseInfo,column)
				{
					public boolean isCellEditable(int row,int column)
					{
						return false;
					}
				};
				
				onlineUsersTable = new JTable(dTableModel);
				onlineUsersTable.setAutoCreateRowSorter(true);
				dTableModel.addColumn("ID");
				dTableModel.addColumn("First Name");
				dTableModel.addColumn("Last Name");
				allOnlineUsers = usersOnlineList.toArray();
				
				JScrollPane scrollPane = new JScrollPane(onlineUsersTable);
				scrollPane.setBounds(0, 0, 250, 500);
				onlineUsersPanel.add(scrollPane,BorderLayout.CENTER);
				onlineUsersPanel.setBounds(400, 0, 250, 500);
				
				thePanel.add(onlineUsersPanel);
				
				popupMenu = new JPopupMenu();
				JMenuItem menuItem = new JMenuItem("Kick");
				menuItem.setIcon(new ImageIcon("./images/deleteIcon.png"));
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = onlineUsersTable.getSelectedRow();
						int userFromTheTable = (int)onlineUsersTable.getValueAt(row, 0);
						System.out.println("Selected ID is : "+userFromTheTable);
						directMessage(-1,userFromTheTable,"You were disconnected by administrator!","MESSAGE","SERVER","--:--:--");
						disconnectTheUser(userFromTheTable);
					}
				});
				popupMenu.add(menuItem);
				
				
				onlineUsersTable.addMouseListener(new MouseAdapter(){
				
					
					@Override
					public void mouseClicked(MouseEvent e)
					{
						showPopup(e);
					}
					
					@Override
					public void mousePressed(MouseEvent e)
					{
						showPopup(e);
					}
					
					@Override
					public void mouseReleased(MouseEvent e)
					{
						showPopup(e);
					}
					
					public void showPopup(MouseEvent e)
					{
						if(e.isPopupTrigger())
						{
							popupMenu.show(onlineUsersTable,e.getX(),e.getY());
						}
					}
					
				});
				
				
				frame.add(thePanel);
			}//end of run() method
			
		});//end of swing utilities method

		
	
	}
	
	private void connectionEstablishment() throws IOException
	{
		
		connection = server.accept();
		System.err.println("New connection!");
		displayMessage("New Connection! IP : "+connection.getInetAddress().getHostAddress());
		NewConnectionThread thread = new NewConnectionThread(connection);
		connectionsList.add(thread);
		thread.start();
		

		System.out.println("connections list size :"+connectionsList.size());
		System.out.println("users online :"+usersOnlineList.size());
		
		
	}
	
	private void connectionCheckment()
	{
		boolean active = true;
		
		if(connectionsList.size() > 0)
		{
			for(int i = connectionsList.size() ; i >= 0 ; i--)
			{
				
				NewConnectionThread thread = connectionsList.get(i);
				
				if(thread.isInterrupted())
				{
					connectionsList.remove(i);
					usersOnlineList.remove(i);
					System.out.println("DISCONNECTED !");
				}
				
			}
		}
		
			
		
		
	}
	
/*	private void removeUserConnection()
	{
		
		for(int i=0; i<connectionsList.size();i++)
		{
			
			NewConnectionThread thread = connectionsList.get(i);
			
			if(!thread.connectionCheck())
			{
				connectionsList.remove(i);
				int user_ID = usersOnlineList.get(i);
				displayMessage("User "+ user_ID +" disconnected!");
				
			}
			
		}
		
	}
	*/
	public static void directMessage(int sender_ID,int target_ID,String message,String typeOfMessage,String sender_fullName,String time)
	{
		if(getUserPositionInTheArray(target_ID) != -1)
		{
			
			int targetPositionInTheArray = getUserPositionInTheArray(target_ID);

			System.out.println("Target "+ target_ID + " ID "+targetPositionInTheArray);
			
			NewConnectionThread thread = connectionsList.get(targetPositionInTheArray);
			
			if(typeOfMessage.equals("MESSAGE"))
			{
				
				if(DataBase.sendingPermission(sender_ID, target_ID) && DataBase.sendingPermission(target_ID, sender_ID))
				{
					int sent_from = sender_ID;
					int sent_to = target_ID;
					thread.sendDirectMessage(message,sender_fullName,time,sent_from,sent_to);
				}
				else
				{
					int senderPositionInTheArray = getUserPositionInTheArray(sender_ID);
					
					NewConnectionThread senderThread = connectionsList.get(senderPositionInTheArray);
				    
				    senderThread.sendDirectMessage("Access denied. You are blocked by this user !","SERVER","--:--:--",sender_ID,target_ID);
						
					
				}
				
			}
			else if(typeOfMessage.equals("QUERY"))
			{
				thread.sendQuery(message);
			}
		
		}
		else
		{
			System.err.println("User "+ DataBase.getUserFullName(target_ID) +" is offline");
		
			int userPositionInTheArray = getUserPositionInTheArray(sender_ID);
			
			NewConnectionThread thread = connectionsList.get(userPositionInTheArray);
			thread.sendDirectMessage("User "+ DataBase.getUserFullName(target_ID) +" is offline!","INFO",NewConnectionThread.getTime(),sender_ID,target_ID);
		}
		
		
	}
	
	public static void sendOnlineUsersList(int target_ID)
	{
		
		if(getUserPositionInTheArray(target_ID) != -1)
		{
			int userPositionInTheArray = getUserPositionInTheArray(target_ID);

			NewConnectionThread thread = connectionsList.get(userPositionInTheArray);
			Object[] onlineUsers = usersOnlineList.toArray();
			thread.sendOnlineList(onlineUsers);
		}
		
	}
	
	public static void sendTheFriendsList(int user_ID)
	{
		if(getUserPositionInTheArray(user_ID) != -1)
		{
			int userPositionInTheArray = getUserPositionInTheArray(user_ID);
			
			NewConnectionThread thread = connectionsList.get(userPositionInTheArray);
			
			Object[] friendsListToSend = DataBase.getFriendsList(user_ID);
			
			thread.sendFriendsList(friendsListToSend);
			
		}
	}
	
	public static void sendBlacklist(int user_ID)
	{
		if(getUserPositionInTheArray(user_ID) != -1)
		{
			int userPositionInTheArray = getUserPositionInTheArray(user_ID);
			
			NewConnectionThread thread = connectionsList.get(userPositionInTheArray);
			
			Object[] blacklistToSend = DataBase.getBlacklist(user_ID);
			
			thread.sendBlacklist(blacklistToSend);
			
		}
	}
	
	public static void sendUserInfo(int target_ID,Object[] info)
	{
		if(getUserPositionInTheArray(target_ID) != -1)
		{
			int userPositionInTheArray = getUserPositionInTheArray(target_ID);
			
			NewConnectionThread thread = connectionsList.get(userPositionInTheArray);
			Object[] infoToSend = info;
			thread.sendUserInfo(infoToSend);
		}
	}
	
	public static void sendChatHistory(int target_ID,Object[] chatHistory)
	{
		if(getUserPositionInTheArray(target_ID) != -1)
		{
			int userPositionInTheArray = getUserPositionInTheArray(target_ID);
			NewConnectionThread thread = connectionsList.get(userPositionInTheArray);
			Object[] toSend = chatHistory;
			thread.sendChatHistory(chatHistory);
			
		}
	}
	
	public static void notifyClients(String message)
	{
		
		for(int i = 0; i < connectionsList.size(); i++)
		{
			NewConnectionThread thread = connectionsList.get(i);
			thread.messageToAllClients(message);
			
		}
		
	}
	

	public static  void displayMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				eventHistory.append("\n"+message+"\n");
			}
		});
	}

	public static void disconnectTheUser(int user_ID)
	{
		if(getUserPositionInTheArray(user_ID) != -1)
		{
			int userPositionInTheArray = getUserPositionInTheArray(user_ID);
			
			connectionsList.remove(userPositionInTheArray);
			usersOnlineList.remove(userPositionInTheArray);
			dTableModel.removeRow(userPositionInTheArray);
			displayMessage("User "+user_ID+" disconnected!");
			
		}
		
	}
	
	public static int getUserPositionInTheArray(int user_ID)
	{

		int userPositionInTheArray = 0;
		
		for(int i = 0; i < usersOnlineList.size() ; i++)
		{
			
			Object[] infoContainer = usersOnlineList.get(i);
			
			if((int)infoContainer[0] == user_ID)
			{
				
				Object[] retrievedObject = usersOnlineList.get(i);
				userPositionInTheArray = usersOnlineList.indexOf(retrievedObject);
				return userPositionInTheArray;
				
			}
			
		}
		
		return -1;
		
		
	}
	
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