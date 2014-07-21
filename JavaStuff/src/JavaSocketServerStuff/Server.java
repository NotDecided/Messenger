package JavaSocketServerStuff;

//http://docs.oracle.com/javase/tutorial/essential/concurrency/procthread.html
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;


//this class is without main method , which means we gotta use  its instance inside of our new class
public class Server implements Runnable
{
	
	
	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
	
	
	private Socket connection = null;
	private ServerSocket server = null;
	
	private JTextField newMessage;
	
	private JTextArea chatHistory;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	
	public Server()
	{
		JFrame jFrame = new JFrame();
		jFrame.setTitle("Messenger (Server Side)");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setSize(200, 350);
		chatHistory = new JTextArea();
		chatHistory.setEditable(false);
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		jFrame.add(chatHistory,BorderLayout.NORTH);
		
		JScrollPane scroller = new JScrollPane(chatHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jFrame.add(scroller);
			
		newMessage = new JTextField();
		newMessage.setEditable(false);
		newMessage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent action) {
				sendMessage(action.getActionCommand());
				newMessage.setText("");

			}
			
		}
		);
		jFrame.add(newMessage,BorderLayout.SOUTH);
		

		jFrame.setVisible(true);
		
	}
	
	
	public void run()
	{
		
		try
		{
			server = new ServerSocket(PORT,100);
			
			while(true)
			{
				
		      try{
			    connect();
			    setupStreams();
			    whileChatting();
			  
		      }
		      catch(EOFException eofEx)
		      {
		    	eofEx.printStackTrace();
		    	
		    	
		      }
		      finally
			  {
				closeConversation();
			  }
			}
			

		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
			
		}
		
		
		
	}
	
	private void connect() throws IOException
	{
			
		showMessage("\n Waiting for connection ... \n");
			
		connection = server.accept();
		
			
		showMessage("\n User With IP : " + connection.getInetAddress().getHostName()+" Connected \n");
	
		
	}
	
	private void setupStreams() throws IOException
	{

		output = new ObjectOutputStream(connection.getOutputStream());//sends data from  server to  client
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());//retrieves data from client
		showMessage("\nStreams are now setup!\n");
	
	}
	
	private void whileChatting() throws IOException
	{
		
		String message = " You are now connected !";
		sendMessage(message);
		typeAllowed(true);
		
		do
		{
		try
		{
			
			message = (String)input.readObject();
			showMessage("\n" +message);
			
		}
		catch(ClassNotFoundException cnfEx)
		{
			showMessage("\n 123@32$@#@%#@%@%#%@@#%@^!@%! \n");
		}
		}while(!message.contains("\nCLIENT - END"));
		
		
		
		
	}
	
	
	private void closeConversation()
	{
		
		showMessage("\n Closing Connections \n");
		typeAllowed(false);
		try
		{
		
		
		output.close();
		input.close();
		connection.close();
		
		
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		
		
	}
	
	private void sendMessage(final String message)
	{
		
		try
		{
			
			
			output.writeObject("SERVER - "+message);
			output.flush();//flush any extra bytes out 
			showMessage("\nSERVER - "+message+"\n");
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		
		
	}
	
	private void showMessage(final String message)
	{
		
		SwingUtilities.invokeLater(new Runnable(){//THREAD which will update certain info which we will define inside of run() method 
				public void run()
				{
					chatHistory.append(message);
				}
				});
		
	}
	
	private void typeAllowed(final boolean ToF)
	{
		SwingUtilities.invokeLater(new Runnable(){//THREAD which will update certain info which we will define inside of run() method
			public void run()
			{
				newMessage.setEditable(ToF);
			}
		});
	}
}