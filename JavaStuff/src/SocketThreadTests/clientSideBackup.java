package SocketThreadTests;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class clientSideBackup extends JFrame
{
	
	private final int PORT = 8888;
	private final String IP = "127.0.0.1";
	private Socket connectionSocket = null;
	
	private JTextField newMessage;
	private JTextArea chatHistory;
	
	private DataOutputStream outputToStream;
	private OutputStream output;
	private DataInputStream inputFromStream;
	private InputStream input;
	
	
	public clientSideBackup()
	{
		this.setSize(300, 450);
		this.setLocationRelativeTo(null);
		this.setTitle("CLIENT");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		newMessage = new JTextField();
		newMessage.setEditable(false);
		newMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
			   sendMessage(e.getActionCommand());
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
		
		
	}
	
	public void startTheClient()
	{
		
		try
		{
			connect();
			streamsSetup();
			chatGoesHere();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void connect() throws IOException
	{
		
		
		showMessage("\n Trying To Connect ... \n");
		connectionSocket = new Socket(IP,PORT);
		showMessage("\n Successfully Connected To The Server \n");
		showMessage("\n Your IP : "+connectionSocket.getInetAddress().getHostAddress()+"\n");
		typeAllowed(true);
		
	}
	
	private void streamsSetup() throws IOException
	{
		
		input = connectionSocket.getInputStream();
		inputFromStream = new DataInputStream(input);
		
		output = connectionSocket.getOutputStream();
		outputToStream = new DataOutputStream(output);
		
		showMessage("\n Data Exchanging Is Setup \n");
	}
	
	private void chatGoesHere() throws IOException
	{
		
		String message = "";
		
		do
		{
		message = inputFromStream.readUTF();
		showMessage(message);
		}while(!message.contains("- END"));
		
		
	}
	
	private void showMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				chatHistory.append(message);
			}
		});
	}
	
	private void sendMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					
			    if(message != null && message.trim().length()>0)
			    {
				outputToStream.writeUTF("\n "+connectionSocket.getInetAddress().getHostAddress() +" - " +message+"\n");
				showMessage("\n"+connectionSocket.getInetAddress().getHostAddress()+" - "+message+"\n");
			    }
			    else
			    {
			    	showMessage("\n ! - You Can't Send An Empty Message!"+"\n");
			    }
			    
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void typeAllowed(final boolean ToF)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				newMessage.setEditable(ToF);
			}
		});
	}
}
