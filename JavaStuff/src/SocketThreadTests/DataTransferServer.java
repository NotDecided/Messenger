package SocketThreadTests;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;



public class DataTransferServer
{
	
	private final int PORT = 8888;
	private final String IP = "192.168.1.2";
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	
	private JTextField newQuery;
	private JTextArea queryHistory;
	
	
	public static void main(String[]args)
	{
		new DataTransferServer();
	}
	
	public DataTransferServer()
	{
		
		
		
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,400);
		frame.setTitle("SERVER");
		
		newQuery = new JTextField();
		newQuery.setFont(new Font("Arial",Font.BOLD,14));
		newQuery.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				sendMessage(e.getActionCommand());
				newQuery.setText("");
			}
		});
		frame.add(newQuery,BorderLayout.SOUTH);
		
		queryHistory = new JTextArea();
		queryHistory.setLineWrap(true);
		queryHistory.setWrapStyleWord(true);
		queryHistory.setEditable(false);
		queryHistory.setFont(new Font("Arial",Font.BOLD,20));
		frame.add(queryHistory,BorderLayout.NORTH);
		
		JScrollPane scroller = new JScrollPane(queryHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroller);
		
		establishConnection();
		setupStreams();
		setupDataExchanging();
		
		
	}
	
	private void establishConnection()
	{
		try
		{
			serverSocket = new ServerSocket(PORT);
			System.err.println("Server setup completed!");
			clientSocket = serverSocket.accept();
			System.err.println("Client connected!");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void setupStreams()
	{
		try
		{
			inputStream = clientSocket.getInputStream();
			outputStream = clientSocket.getOutputStream();
			dataInputStream = new DataInputStream(inputStream);
			dataOutputStream = new DataOutputStream(outputStream);
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void setupDataExchanging()
	{
		String inbox = "";
		try 
		{
			
			inbox = dataInputStream.readUTF();
			
			if(inbox.contains("/GET THE FILE"))
			{
				System.err.println("Yeah!");
				String fileName = inbox.replace("/GET THE FILE", "").trim();
				sendMessage("Receiving of File : "+fileName);
				sendToTheClient(fileName);
				System.err.println("File was successfully delivered to client!");
			}
			else if(inbox.contains("/SEND THE FILE"))
			{
				String fileName = inbox.replace("/SEND THE FILE", "").trim();
				sendMessage("Sending of File : "+fileName);
				receiveTheData(fileName);
				System.err.println("File was successfully received and stored on the Server!");
			}
			
			
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	
	private void sendToTheClient(String fileName)
	{
		BufferedOutputStream bos = null;//will allow us to write out file bytes
		BufferedInputStream bis = null;//will allow us to read our file bytes
		FileInputStream fis = null;//will allow us to pull our file data out and in the following steps convert it to bytes
		
		try
		{
			bos = new BufferedOutputStream(clientSocket.getOutputStream());
			
			if(bos != null)
			{
				
				File fileToSend = new File("./SERVER_SIDE/",fileName);//retrieves the file with this specific name (fileName) from the server
				byte[] byteArray = new byte[(int)fileToSend.length()];//setting the size of our byte array , this size is the size of our file (in bytes)
				//this byte array will be sent
				
				try
				{
					fis = new FileInputStream(fileToSend);//preparing our file to being converted
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
				bis = new BufferedInputStream(fis);//converting our file into bytes
				
				try
				{
					bis.read(byteArray, 0, byteArray.length);//writing our file bytes into our byte array
					bos.write(byteArray, 0, byteArray.length);//sending our file byte array(file bytes ) to the client 
					bos.flush();//flushing all extra bytes(emptying trash , flushing the toilet)
					bos.close();//closing of output stream , turning our output stream off
					
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
	
	private void receiveTheData(String fileName)
	{
		
		String realFileName = fileName.trim();
		
		FileOutputStream fos = null;
		InputStream is = null;
		BufferedOutputStream bos = null;
	    ByteArrayOutputStream baos = null;
	    
	    
	    int bufferSize = 0;
	    
	    try
	    {
	    	is = clientSocket.getInputStream();
	    	
	    	bufferSize = clientSocket.getReceiveBufferSize();
	    	
	    	System.err.println("Buffer size : "+bufferSize);
	    	
	    	
	    	File fileReceiving = new File("./SERVER_SIDE/",fileName);
	    	
	    	fos = new FileOutputStream(fileReceiving);
	    	
	    	bos = new BufferedOutputStream(fos);
	    	
	    	byte[] byteArray = new byte[bufferSize];
	    	
	    	int count ;
	    	
	    	while((count= is.read(byteArray)) >0)
	    	{
	    		
	    		bos.write(byteArray,0,byteArray.length);
	    		
	    	}
	    	
	    	bos.flush();
	    	bos.close();
	    	is.close();
	    	
	    	
	    }
	    catch(IOException ex)
	    {
	    	ex.printStackTrace();
	    }
	 
	    
	 
	    
		
		
	}
	
	private void sendMessage(final String query)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try 
				{
					dataOutputStream.writeUTF("\n"+query+"\n");
					printInHistory(query);
				} 
				catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		
	}
	
	private void printInHistory(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				queryHistory.append("\n"+message+"\n");
			}
		});
	}
	
	
}