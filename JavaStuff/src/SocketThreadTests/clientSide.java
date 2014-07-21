package SocketThreadTests;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class clientSide extends JFrame
{
	
	private final int PORT = 234;
	private final String IP = "127.0.0.1";
	private Socket connectionSocket = null;
	
	private JTextField newMessage;
	private JTextArea chatHistory;
	
	private DataOutputStream outputToStream;
	private OutputStream output;
	private DataInputStream inputFromStream;
	private InputStream input;
	
	
	public clientSide()
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
	
	private void setupUI()
	{
		JFrame frame = new JFrame();
	
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
		
		String fileName = "";
		if(message.contains("PREPARE FOR DATA RECEIVING , INCOMING FILE : "))
		{
			String unEditedMessage = message.replace(" ! - PREPARE FOR DATA RECEIVING , INCOMING FILE : ","");
			fileName = unEditedMessage.trim();
			
			receiveTheData(fileName);
			showMessage("\nFile Received !\n");
			reestablishConnection();
			
		}
		else if(message.contains("PREPARING SERVER FOR DATA RECEIVING , INCOMING DATA : "))
		{
			String fileToSendName = message.replace(" ! - PREPARING SERVER FOR DATA REVEIVING , INCOMING DATA : ","").trim();
			
			sendTheDataToTheServer(fileToSendName);
			showMessage("\nFile was sent successfully!\n");
			reestablishConnection();
		}
		
		
		else if(message.contains("\nBye...\n"))
		{
		   System.exit(0);	
		}
		
		
		
		}while(!message.contains("- END"));
		
		
	}
	private void sendTheDataToTheServer(String fileToSendName)
	{
		showMessage("Sending the file ...");
		
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		FileInputStream fileInputStream = null;
		
		
		try
		{
			
			bufferedOutputStream = new BufferedOutputStream(connectionSocket.getOutputStream());
			
			if(bufferedOutputStream != null)
			{
				
				
				File myFileToSend = new File("./SERVER_FILES/"+fileToSendName);
				
				
				byte[] byteArray = new byte[(int)myFileToSend.length()];
				
				
				try
				{
					fileInputStream = new FileInputStream(myFileToSend);
				
				}
				catch(FileNotFoundException FNFex)
				{
					showMessage(FNFex.getMessage());
				}
				
				bufferedInputStream = new BufferedInputStream(fileInputStream);//
				
				try
				{
					
					bufferedInputStream.read(byteArray, 0, byteArray.length);
					
					bufferedOutputStream.write(byteArray, 0, byteArray.length);
					bufferedOutputStream.flush();
					bufferedOutputStream.close();
					
				}
				catch(IOException IOex)
				{
					showMessage(IOex.getMessage());
				}
				
				
			}
			
			
			
		}
		catch(IOException ex)
		{
			showMessage(ex.getMessage());
		}
		
	}
	
	private void reestablishConnection()
	{
		try
		{
		connectionSocket.close();
		input.close();
		inputFromStream.close();
		output.close();
		outputToStream.close();
		
		startTheClient();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
    private void receiveTheData(String fileName)
    {
    	InputStream inputStream = null;
    	FileOutputStream fileOutputStream = null;
    	BufferedOutputStream bufferedOutputStream = null;
    	ByteArrayOutputStream byteArrayOutputStream = null;
    	byte[] byteArray = new byte[1];
    	
    	try
    	{
    		inputStream = connectionSocket.getInputStream();
    	}
    	catch(IOException ex)
    	{
    		showMessage(ex.getMessage());
    	}
    	
    	byteArrayOutputStream = new ByteArrayOutputStream();
    	
    	if(inputStream != null)
    	{
    		
    		
    		try
    		{
    		
    			File fileFromTheServer = new File(fileName);
    			System.err.println(fileFromTheServer.toString());
    			fileOutputStream = new FileOutputStream(fileFromTheServer);
    		
    			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    			
    			int bytesRead = inputStream.read(byteArray, 0, byteArray.length);
    			
    			do{
    				
    				byteArrayOutputStream.write(byteArray);
    				
    				bytesRead = inputStream.read(byteArray);
    				
    			}while(bytesRead != -1);
    			
    			bufferedOutputStream.write(byteArrayOutputStream.toByteArray());
    			
    			bufferedOutputStream.flush();
    			
    			bufferedOutputStream.close();
    			
    			
    		
    		}
    		catch(IOException  IOex)
    		{
    			showMessage(IOex.getMessage());
    		}
    		
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
