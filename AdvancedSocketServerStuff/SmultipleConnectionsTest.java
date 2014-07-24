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

public class SmultipleConnectionsTest
{
	
	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
	private ServerSocket server;
    private Socket connection;
    private static ArrayList<ConnectionThread> connectionsList;
    public static ArrayList<String> usersOnlineList; //gotta finish direct messages
	
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
		connectionsList = new ArrayList<ConnectionThread>();
		usersOnlineList = new ArrayList<String>();
		
		boolean active = true;
		
		
		try
		{
			setupGUI();
			server = new ServerSocket(PORT);
			
		
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
			removeUserConnection();
			
		    
	
		}
		catch(IOException ex1)
		{
			ex1.printStackTrace();
		}
	}
	
	private void setupGUI()
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
		dTableModel.addColumn("Online Users");
		allOnlineUsers = usersOnlineList.toArray();
		
		JScrollPane scrollPane = new JScrollPane(onlineUsersTable);
		scrollPane.setBounds(0, 0, 250, 500);
		onlineUsersPanel.add(scrollPane,BorderLayout.CENTER);
		onlineUsersPanel.setBounds(400, 0, 250, 500);
		
		thePanel.add(onlineUsersPanel);
		
		popupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Kick");
		ImageIcon deleteIcon = new ImageIcon("./image/deleteIcon.png");
		menuItem.setIcon(deleteIcon);
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int row = onlineUsersTable.getSelectedRow();
				int column = onlineUsersTable.getSelectedColumn();
				String userFromTheTable = (String)onlineUsersTable.getValueAt(row, column);
				directMessage("Server",userFromTheTable,"You were disconnected by administrator!");
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
		
	
	}
	
	private void connectionEstablishment() throws IOException
	{
		
		connection = server.accept();
		System.err.println("New connection!");
		displayMessage("New Connection! IP : "+connection.getInetAddress().getHostAddress());
		ConnectionThread thread = new ConnectionThread(connection);
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
				
				ConnectionThread thread = connectionsList.get(i);
				
				if(thread.isInterrupted())
				{
					connectionsList.remove(i);
					usersOnlineList.remove(i);
					System.out.println("DISCONNECTED !");
				}
				
			}
		}
		
			
		
		
	}
	
	private void removeUserConnection()
	{
		
		for(int i=0; i<connectionsList.size();i++)
		{
			
			ConnectionThread thread = connectionsList.get(i);
			
			if(!thread.connectionCheck())
			{
				connectionsList.remove(i);
				String nameOfUser = usersOnlineList.get(i);
				displayMessage("User "+nameOfUser+" disconnected!");
				
			}
			
		}
		
	}
	
	public static void directMessage(String sender,String target,String message)
	{
		if(usersOnlineList.indexOf(target) != -1)
		{
		int user_ID = usersOnlineList.indexOf(target);
		System.out.println("Target "+target + " ID "+user_ID);
		ConnectionThread thread = connectionsList.get(user_ID);
		
		thread.sendDirectMessage(message);
		}
		else
		{
			System.err.println("User "+target+" is offline");
			int user_ID = usersOnlineList.indexOf(sender);
			ConnectionThread thread = connectionsList.get(user_ID);
			thread.sendDirectMessage("User "+target+" is offline!");
		}
		
		
	}
	
	public static void sendOnlineUsersList(String target)
	{
		
		if(usersOnlineList.indexOf(target)!= -1)
		{
			int user_ID = usersOnlineList.indexOf(target);
			ConnectionThread thread = connectionsList.get(user_ID);
			Object[] onlineUsers = usersOnlineList.toArray();
			thread.sendOnlineList(onlineUsers);
		}
		
	}
	
	public static void notifyClients(String message)
	{
		
		for(int i = 0; i < connectionsList.size(); i++)
		{
			ConnectionThread thread = connectionsList.get(i);
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

	public static void disconnectTheUser(String userNickName)
	{
		if(usersOnlineList.indexOf(userNickName)!=-1)
		{
			int user_ID = usersOnlineList.indexOf(userNickName);
			connectionsList.remove(user_ID);
			usersOnlineList.remove(userNickName);
			dTableModel.removeRow(user_ID);
			displayMessage("User "+userNickName+" disconnected!");
			ConnectionThread thread = connectionsList.get(user_ID);
			//thread.
		}
		
	}
	
	
	
}