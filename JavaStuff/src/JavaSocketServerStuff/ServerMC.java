package JavaSocketServerStuff;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class ServerMC  extends JFrame
{
	
	private final int PORT = 8888;
	private final String IP = "127.0.0.1";
	private ServerSocket server = null;
	private Socket[] connection = null;
	
	
	public static JTextField newMessage;
	public static JTextArea chatHistory;
	public static JScrollPane scroller;
	
	
	public static DataOutputStream output;
	public static OutputStream fromClient;
	public static DataInputStream input;
	public static InputStream inFromClient;

	
	public void startTheServer()
	{
	   
		try
		{
			setupUI();
			server = new ServerSocket(PORT);
			
			while(true)
			{
	
			 
			  

			}
			
			
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void setupUI() 
	{
		
		this.setSize(300, 450);
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		newMessage = new JTextField();
		newMessage.setEditable(true);
		newMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				sendMessage(e.getActionCommand());
				newMessage.setText("");
			}
		});
		this.add(newMessage,BorderLayout.SOUTH);
		
		chatHistory = new JTextArea();
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		chatHistory.setEditable(false);
		this.add(chatHistory,BorderLayout.NORTH);
		
		scroller = new JScrollPane(chatHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroller);
		this.setVisible(true);
		
		
	}
	
	public  void showMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				chatHistory.append("\nSERVER : "+message+"\n");	
			}
		});
			
		
	}
	
	public  void sendMessage(final String message)
	{
		
		try
		{
			
		output.writeUTF("\nSERVER"+message+"\n");
		output.flush();
		showMessage("\nSERVER"+message+"\n");
		
		}catch(IOException ex)
		{
		  ex.printStackTrace();
		}
	}
	
	
	
}
class ConnectionHandlerThread extends Thread
{
	
	
	private Socket connection;
	private ServerMC server = new ServerMC();

	
	public ConnectionHandlerThread(Socket newConnection)
	{
		this.connection = newConnection;
		
	}
	
	public void run()
	{
		
		try
		{
		
			
			//setupStreams();
			whileChatting();
			
			System.out.println("Everything works perfectly");
			
			
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			closeConversation();
		}
		
		
	}
	

	
	private void setupStreams() throws IOException
	{
		
		
		
		
	}
	
	private void whileChatting() throws IOException
	{
		
		typeAllowed(true);
		String message = "";
		
		do
		{
		   try{
		   message = (String)ServerMC.input.readUTF();
		   server.showMessage("\nSERVER : "+message+"\n");
		   }catch(IOException ex)
		   {
			   ex.printStackTrace();
		   }
			
		}
		while(!message.contains("END"));
		
		
	}
	
	private void closeConversation()
	{
		typeAllowed(false);
		try
		{
			ServerMC.input.close();
			ServerMC.output.close();
			connection.close();
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void typeAllowed(final boolean ToF)
	{
		ServerMC.newMessage.setEditable(ToF);
	}
	

	

	
	
	
}