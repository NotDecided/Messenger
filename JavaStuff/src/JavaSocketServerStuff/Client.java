package JavaSocketServerStuff;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class Client extends JFrame
{
	

	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
    private Socket connection = null;
    private JTextField newMessage;
    private JTextArea chatHistory;
    private ObjectInputStream input;
    private ObjectOutputStream output;
	
    
    public Client()
    {
    	this.setTitle("Messenger (Client Side)");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(200,350);
    	this.setLocationRelativeTo(null);
    	
    	newMessage = new JTextField();
    	newMessage.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			sendMessage(e.getActionCommand());
    			newMessage.setText("");
    		}
    	});
    	newMessage.setEditable(false);
    	this.add(newMessage,BorderLayout.SOUTH);
    	
    	chatHistory = new JTextArea();
    	chatHistory.setLineWrap(true);
    	chatHistory.setWrapStyleWord(true);
    	chatHistory.setEditable(false);
    	this.add(chatHistory);
    	
    	JScrollPane scroller = new JScrollPane(chatHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	this.add(scroller);
    	
    	
    	
    	this.setVisible(true);
    }
    
	public void run()
	{
		
		try{
			
			connect();
			setupStreams();
			whileChatting();
			
			
		}
		catch(IOException eofException)
		{
			
		}
		finally
		{
			closeConversation();
		}
	
	}
	
	private void connect() throws IOException
	{
		
		showMessage("\n Connecting To The Server ... \n");
		connection = new Socket(IP,PORT);
		showMessage("\n Connected \n" + "\n Your IP : "+connection.getLocalAddress().getHostAddress()+"\n");
		
	}
	
	private void setupStreams() throws IOException
	{
		
		output = new ObjectOutputStream(connection.getOutputStream());
		input = new ObjectInputStream(connection.getInputStream());
		
	}
	
	private void whileChatting() throws IOException
	{
		
		String message = "";
		do
		{
		typeAllowed(true);
		try
		{
	    message = (String)input.readObject();
		showMessage("\n"+message+"\n");
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		}while(!message.equals("CLIENT - END"));
		
		
		
	}
	
	private void closeConversation() {
		
		
		typeAllowed(false);
		
		
		try
		{
		    
			input.close();
			output.close();
			connection.close();
			showMessage("\n Conversation Ended \n ");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
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
				output.writeObject("\nCLIENT - "+message+"\n");
				showMessage("\nCLIENT - "+message+"\n");
				}
				catch(IOException ex)
				{
					showMessage("\n An Error Ocurred \n");
				}
			}
		});
		
	}
	
	private void typeAllowed(final boolean ToF)
	{
		SwingUtilities.invokeLater(new Runnable()
	    {
			public void run()
			{
				newMessage.setEditable(ToF);
				
			}
	    });
			
	}
	
	
	
	
}