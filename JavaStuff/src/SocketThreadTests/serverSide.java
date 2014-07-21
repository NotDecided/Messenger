package SocketThreadTests;


import java.net.*;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;


public class serverSide extends JFrame
{
	
	private final int PORT = 234;
	private final String IP = "127.0.0.1";
	private ServerSocket serverSocket = null;
	private  Socket connectionSocket = null;
	
	public static JTextArea chatHistory;
	public static JTextField newMessage;
	private boolean shutdown = false;
   
	public static ArrayList<ConnectionsHandler> arrayList;
	
	public serverSide()
	{
	
		this.setSize(300, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SERVER");
		this.setLocationRelativeTo(null);
		
		
		newMessage = new JTextField();
		newMessage.setEditable(false);
		newMessage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ConnectionsHandler.sendMessage(e.getActionCommand());
				newMessage.setText("");
			}
		});
		this.add(newMessage,BorderLayout.SOUTH);
		
		chatHistory = new JTextArea();
		chatHistory.setEditable(false);
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		this.add(chatHistory,BorderLayout.NORTH);
		
		JScrollPane scroller = new JScrollPane(chatHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroller);
		this.setVisible(true);
		
		
	}
	public void startTheServer()
	{
		
		try
		{
			
		serverSocket = new ServerSocket(PORT);
		arrayList = new ArrayList<ConnectionsHandler>();
		ConnectionsHandler.showMessage("\n ! - Initialization Successfully Completed\n");
		ConnectionsHandler.showMessage("\n ! - PORT : "+serverSocket.getLocalPort()+"\n");
		ConnectionsHandler.showMessage("\n ! - IP : "+serverSocket.getInetAddress().getHostName()+"\n");
		
		
		newConnection();
		
		
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}	
		
		
	}
	
	private void newConnection() throws IOException
	{
		
		
		while(!shutdown)
		{
		connectionSocket = serverSocket.accept();
		
		ConnectionsHandler r = new ConnectionsHandler(connectionSocket);
		arrayList.add(r);
		r.start();
		
		ConnectionsHandler.showMessage("\n ! - New Connection IP : "+connectionSocket.getInetAddress().getHostAddress()+"\n");
		}
	
		
		
	}
	
	private void setupUI() 
	{
		
		setSize(300, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SERVER");
		setLocationRelativeTo(null);
		
		
		newMessage = new JTextField();
		newMessage.setEditable(false);
		newMessage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ConnectionsHandler.sendMessage(e.getActionCommand());
				newMessage.setText("");
			}
		});
		add(newMessage,BorderLayout.SOUTH);
		
		chatHistory = new JTextArea();
		chatHistory.setEditable(false);
		add(chatHistory,BorderLayout.NORTH);
		
		
		setVisible(true);
		
		broadcast();
	}
	
	private synchronized void broadcast()
	{
		
		for(int i=serverSide.arrayList.size();i>=0 ; i--)
		{
			ConnectionsHandler ch = serverSide.arrayList.get(i);
			ConnectionsHandler.sendMessage("Hi all");
			
		}
	}


	
}

