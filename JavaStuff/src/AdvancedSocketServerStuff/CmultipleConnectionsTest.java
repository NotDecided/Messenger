package AdvancedSocketServerStuff;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.sql.Date;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class CmultipleConnectionsTest 
{
	
	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
	private Socket connection;
	
	
	private ObjectOutputStream oos;
	private static ObjectInputStream ois;
	
	private JTextField nameField,newMessage,sendTo;
	private JTextArea chatHistory;
	
	private JPanel thePanel,chatPanel;
	
	private String userNickName = "" , personReceiver = "";
	
	private JLabel LuserNickName ;
	
	private boolean started = false;
	
	private JFrame frame,onlineUsersFrame;
	
	private Object[][] databaseInfo;
	private Object[] column;
	

	
	private JPopupMenu popupMenu;
	
	private JTable onlineUsersTable,FriendsTable;
	private boolean tableCreated = false;
	
	private Object[] usersOnline;
	
	public void start()
	{
		
		try
		{
			frameProperties();
			connectToTheServer();
			nameConfirmation();
			streamsSetup();
			chatGoesHere();
			//serverDataReceiver();
			
		}
		catch(IOException | ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void frameProperties()
	{
		frame = new JFrame();
		frame.setSize(400,550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e)
			{
				frame.setExtendedState(JFrame.ICONIFIED);
			}

		});*/
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setTitle("Client");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void nameConfirmation() 
	{
		
		thePanel = new JPanel();
		
		nameField = new JTextField(20);
		nameField.setText("Enter your name here");
		nameField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				
				nameField.setText("");
			
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				nameField.setText("Enter your name here");
				
			}
			
		});
		nameField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
				userNickName = e.getActionCommand();
				sendUserNickName();
				nameField.setText("");
				started = true;
				thePanel.setVisible(false);
				setupGUI();
				
			}
		});
		thePanel.add(nameField);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				closeConnectionAndExit();
			}
		});
		thePanel.add(exitBtn);
		
		
		frame.add(thePanel);
		
		
		
	}
	
	private void setupGUI()
	{
	
		chatPanel = new JPanel();
		chatPanel.setVisible(true);
		chatPanel.setLayout(null);
		
		LuserNickName = new JLabel(userNickName);
		LuserNickName.setFont(new Font("Arial",Font.BOLD,20));
		LuserNickName.setBounds(200, 50, 100, 40);
		chatPanel.add(LuserNickName);
		
		sendTo = new JTextField();
		sendTo.setEditable(false);
		sendTo.setBounds(100,100,200,25);
		chatPanel.add(sendTo);
		
		newMessage = new JTextField();
		newMessage.setPreferredSize(new Dimension(200,25));
		newMessage.setBounds(100, 125, 200, 25);
		newMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				sendMessage(e.getActionCommand());
				newMessage.setText("");
			}
		});
		chatPanel.add(newMessage);
		
		chatHistory = new JTextArea();
		chatHistory.setBounds(150,100,200,300);
		chatHistory.setEditable(false);
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		chatPanel.add(chatHistory);
		
		JScrollPane scroller = new JScrollPane(chatHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setBounds(100,150,200,300);
		chatPanel.add(scroller);
		
		JButton clearChatHistory = new JButton("Clear");
		clearChatHistory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				chatHistory.setText("");
			}
		});
		clearChatHistory.setBounds(300, 150, 90, 30);
		chatPanel.add(clearChatHistory);
		
		JButton getOnlineList = new JButton("Users"+ "\nOnline");
		getOnlineList.setToolTipText("Click to update list of users who are currently online.");
		getOnlineList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//sendUsersDataRetreivingConfirmation();
				getUsersOnlineCount();
				
			}
		});
		getOnlineList.setBounds(300, 180, 90, 30);
		chatPanel.add(getOnlineList);
		
		JButton friendsBtn = new JButton("Friends");
		friendsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				showFriends();
			}
		});
		friendsBtn.setBounds(10,150,90,30);
		chatPanel.add(friendsBtn);
		
		JButton onlineUsersBtn = new JButton("Online Users");
		onlineUsersBtn.setFont(new Font("Arial",Font.PLAIN,8));
		onlineUsersBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				retreiveOnlineUsers();
				showOnlineUsers();
			}
		});
		onlineUsersBtn.setBounds(10, 100, 90, 25);
		chatPanel.add(onlineUsersBtn);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				closeConnectionAndExit();
			}
		});
		exitBtn.setBounds(150, 470, 100, 40);
		chatPanel.add(exitBtn);
		

		
		
		
		frame.add(chatPanel);
	}
	
	private void connectToTheServer() throws IOException ,ClassNotFoundException
	{
		
		connection = new Socket(IP,PORT);
		System.out.println("Client successfully connected to the Server!");
		
	}
	
	private void sendUserNickName() 
	{
		try
		{
		String[] toSend = {"/NEW USER",userNickName,getTime()};
		oos.writeObject(toSend);
		System.err.println("Sent Successfully!");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void streamsSetup() throws IOException
	{
		oos = new ObjectOutputStream(connection.getOutputStream());
		ois = new ObjectInputStream(connection.getInputStream());
	}
	
	private void chatGoesHere() throws IOException , ClassNotFoundException
	{
		boolean active = true;
		
		do
		{
			
			Object[] message = (Object[]) ois.readObject();
			
			if(message[0].equals("/USERS ONLINE"))
			{
				String toDisplay = "Online "+message[1]+" Users";
				displayMessage(toDisplay);
			}
			else if(message[0].equals("/CONNECTION CHECK"))
			{
				System.err.println("Connection successfully checked!");
			}
			else if(message[0].equals("/ONLINE USERS LIST IS SENDING"))
			{
				usersOnline = (Object[])ois.readObject();
			}
			
			
			if(!message[0].equals("/ONLINE USERS LIST IS SENDING"))
			{
				displayMessage((String)message[0]);
			}
		
		}while(active);
		
	}
	
	private void retreiveOnlineUsers()
	{
		try
		{
			Object[] toSend = {"/SEND USERS ONLINE LIST",userNickName};
			oos.writeObject(toSend);
	
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void showOnlineUsers()
	{
		
			
		tableCreated = true;
		onlineUsersFrame = new JFrame();
		onlineUsersFrame.setSize(250,500);
		onlineUsersFrame.setTitle("Online Users");
		onlineUsersFrame.setVisible(true);
		onlineUsersFrame.setLocationRelativeTo(this.frame);
		
		
		DefaultTableModel dTableModel = new DefaultTableModel(databaseInfo,column)
		{
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		
		onlineUsersTable = new JTable(dTableModel);
		onlineUsersTable.setAutoCreateRowSorter(true);
		
		if(dTableModel.getColumnCount() <1)
		{
			dTableModel.addColumn("Online Users");
		}
		
	    //how to create frequently updated JTabel that performs well
		//http://www.oracle.com/technetwork/java/christmastree-138396.html
		
		
		for(int i = 0; i<usersOnline.length ; i++)
		{
			
			Object[] toAdd = {usersOnline[i]};
			dTableModel.addRow(toAdd);
			
		}
		
		JScrollPane scroller = new JScrollPane(onlineUsersTable);
		onlineUsersFrame.add(scroller,BorderLayout.CENTER);
		
		
        popupMenu = new JPopupMenu();
		
		JMenuItem menuItem = new JMenuItem("Delete");
		ImageIcon deleteIcon = new ImageIcon("images/deleteIcon.png");
		menuItem.setIcon(deleteIcon);
		popupMenu.add(menuItem);
		
		popupMenu.addSeparator();
		
		menuItem = new JMenuItem("Start a conversation");
		ImageIcon newMessageIcon = new ImageIcon("images/newMail.png");
		menuItem.setIcon(newMessageIcon);
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int row = onlineUsersTable.getSelectedRow();
				int column = onlineUsersTable.getSelectedColumn();
				sendTo.setText((String)onlineUsersTable.getValueAt(row, column));
				onlineUsersFrame.setVisible(false);
			}
		});
		popupMenu.add(menuItem);//gotta complete this pop up menu stuff
		//and should figure out how it works.
		//*** http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#hierarchy
		
		onlineUsersTable.addMouseListener(new MouseAdapter(){
			
			@Override 
			public void mousePressed(MouseEvent e)
			{
			    checkPopup(e);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() % 2 == 0)
				{
					e.consume();
					int row = onlineUsersTable.getSelectedRow();
					int column = onlineUsersTable.getSelectedColumn();
					sendTo.setText((String)onlineUsersTable.getValueAt(row, column));
					onlineUsersFrame.setVisible(false);
					
				}
				checkPopup(e);
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				checkPopup(e);
				//int row = onlineUsersTable.getSelectedRow();
				//int column = onlineUsersTable.getSelectedColumn();
				//sendTo.setText((String)onlineUsersTable.getValueAt(row, column));
				//onlineUsersFrame.setVisible(false);
			}
			
			public void checkPopup(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					popupMenu.show(onlineUsersTable,e.getX(),e.getY());
				}
			}
		});
		
		
	
		/*popupMenu = new JPopupMenu();
		
		JMenuItem menuItem = new JMenuItem("Delete");
		popupMenu.add(menuItem);
		
		menuItem = new JMenuItem("Start a conversation");
		popupMenu.add(menuItem);//gotta complete this pop up menu stuff
		//and should figure out how it works.
		//*** http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#hierarchy
		
		
		
		frame.addMouseListener(new PopupListener());*/

	}
	
	private void showFriends()
	{
		JFrame frame = new JFrame();
		frame.setSize(250,500);
		frame.setTitle("Friend List");
		frame.setLocationRelativeTo(this.frame);
		frame.setVisible(true);
		
		//JTable table = new JTable(dTableModel);
		//table.setAutoCreateRowSorter(true);
		//dTableModel.addColumn("Friends");
		//JScrollPane scroller = new JScrollPane(table);
		//frame.add(scroller,BorderLayout.CENTER);
	}
	
    private void closeConnectionAndExit()
    {
    	
    	 try
         {
       	    Object[] toSend = {"/DISCONNECT THE USER",userNickName};
       	    if(userNickName.length() >0)
       	    oos.writeObject(toSend);
       	    
         }
    	 catch(IOException ex)
    	 {
    		 ex.printStackTrace();
    	 }
    	 System.exit(0);
    	 
    }
	
	private void sendUsersDataRetreivingConfirmation()
	{
		try
		{
			Object[] toSend = {"/GET USERS ONLINE LIST"};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void sendMessage(final String message)
	{
       SwingUtilities.invokeLater(new Runnable(){
    	   public void run(){
    		   try
    	   		{
    			    personReceiver = sendTo.getText();
    			    Object[] toSend = {"/SEND MESSAGE",userNickName,message,personReceiver,getTime()};
    			    String toDisplay = "\n"+""+toSend[1]+":"+"\n" +"\n"+toSend[2] +"\n"+ "\n"+toSend[4]+"\n";
    			    displayMessage(toDisplay);
    	   			oos.writeObject(toSend);
    	   		}
    	   		catch(IOException ex)
    	   		{
    	   			ex.printStackTrace();
    	   		}
    	   }
       });
		
	}
	
	
	
	private void displayMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				if(started)
				{
				chatHistory.append("\n"+message+"\n");
				chatHistory.setFont(new Font("Arial",Font.BOLD,14));
				}
			}
		});
	}
	
	private void getUsersOnlineCount()
	{
		try
		{
			Object[] toSend = {"/GET USERS ONLINE LIST",userNickName};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private String getTime()
	{
	    Calendar calendar = Calendar.getInstance();
	    
	    int hours = calendar.get(Calendar.HOUR_OF_DAY);
	    int minutes = calendar.get(Calendar.MINUTE);
	    int seconds = calendar.get(Calendar.SECOND);
	    
	    String time = hours+":"+minutes+":"+seconds;
	    
	    return time;
	}
	
	
	class PopupListener extends MouseAdapter
	{
		
		@Override 
		public void mousePressed(MouseEvent e)
		{
			checkPopup(e);
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			checkPopup(e);
		}
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			checkPopup(e);
		}
		
		public void checkPopup(MouseEvent e)
		{
			if(e.isPopupTrigger())
			{
				popupMenu.show(chatHistory,e.getX(),e.getY());
			}
		}
		
	}
	
	class DataReceiver extends Thread
	{
		
		
		public void run()
		{
			while(true)
			{
				try 
				{
					String inbox = (String)ois.readObject();
					displayMessage(inbox);
				} 
				catch (IOException | ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
}