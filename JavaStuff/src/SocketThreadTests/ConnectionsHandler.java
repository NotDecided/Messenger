package SocketThreadTests;


import java.io.*;
import java.net.*;

import javax.swing.*;

public class ConnectionsHandler extends Thread
{
	
	private final int PORT = 8888;
	private final String IP = "127.0.0.1";
	private Socket connection = null;
	public static DataInputStream inputFromStream;
	public static InputStream input;
	public static DataOutputStream outputToStream;
	public static OutputStream output;
	
	
	public ConnectionsHandler(Socket connection)
	{
		this.connection = connection;
		
	}
	
	public void run()
	{
		
		connect();
		
	}
	
	private void connect()
	{
		
		try
		{
			streamsSetup();
			chatGoesHere();
			
			
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}
	
	private void streamsSetup() throws IOException
	{
		
		showMessage("\n ! - Everything is ready to go\n");
		output = connection.getOutputStream();
		input = connection.getInputStream();
		outputToStream = new DataOutputStream(output);
		inputFromStream = new DataInputStream(input);
		
	
		
	}
	private  void chatGoesHere() throws IOException
	{
		String message = "\nChat started!\n";
		do{
		
		while(!isInterrupted())
		{
		typeAllowed(true);
		
		message = inputFromStream.readUTF();
		showMessage(message);
	
		if(message.contains("/GET THE FILE"))
		{
			String replacingExtraData = "";
			String exactFile = "";
			replacingExtraData = message.replace("192.168.1.2 - /GET THE FILE", "");
			exactFile = replacingExtraData.trim();
			sendTheFileToClient(exactFile);
			sendMessage("File Recieved!");
			closeConnection();
			
		}
		else if(message.contains("/SEND THE FILE"))
		{
			
			
			String fileName = message.replace("192.168.1.2 - /SEND THE FILE", "").trim();
			sendMessage("PREPARING SERVER FOR DATA RECEIVING , INCOMING DATA : "+fileName);
			receiveTheData(fileName);
			sendMessage("File successfully received and stored on the server");
			closeConnection();
		}
		else if(message.contains("/QUIT"))
		{
			sendMessage("Bye...");
		}
		else if(message.contains("/EXIT AND SHUT DOWN THE SERVER"))
		{
			sendMessage("Shutting  Down The Server");
			System.exit(0);
		}
		
		
		}
		
		}while(!message.contains("- END"));
		
		
	}

	private  void receiveTheData(String fileName)
	{
    	InputStream inputStream = null;
    	FileOutputStream fileOutputStream = null;
    	BufferedOutputStream bufferedOutputStream = null;
    	ByteArrayOutputStream byteArrayOutputStream = null;
    	
    	
    	try
    	{
    		inputStream = connection.getInputStream();
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
    		
    			File fileFromTheServer = new File("./finalDestination/"+fileName);
    			System.err.println(fileFromTheServer.toString());
    			fileOutputStream = new FileOutputStream(fileFromTheServer);
    		
    			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    			
    			byte[] byteArray = new byte[4293372];
    			
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
	private void closeConnection()
	{
		try
		{
			connection.close();
			input.close();
			inputFromStream.close();
			output.close();
			outputToStream.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	private void sendTheFileToClient(String fileName)
	{
		sendMessage("PREPARE FOR DATA RECEIVING , INCOMING FILE : "+fileName);
		

		
		
		
		
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		FileInputStream fileInputStream = null;
		
		
		try
		{
			
			bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
			
			if(bufferedOutputStream != null)
			{
				
				
				File myFileToSend = new File("./SERVER_FILES/"+fileName);
				
				
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
	private void createAFile(String fileName)
	{
		File file = new File(fileName+".xml");
		
		try
		{
		file.createNewFile();
		System.err.println("File "+file.getName()+" was successfully created!");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public static void showMessage(final String message) 
	{
		
		SwingUtilities.invokeLater(new Runnable()
		{
			
			public void run()
			{
				serverSide.chatHistory.append(message);
			}
			
		});
		
	}
	public static  void sendMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try {
					
					if(message != null && message.trim().length() > 0)
					{
					   outputToStream.writeUTF("\n ! - "+message +"\n");
					   showMessage("\n ! - "+message+"\n");
					}
					else
					{
					   showMessage("\n ! - You Can't Send An Empty Message"+"\n");
					}
				
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	public static void typeAllowed(final boolean ToF)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				serverSide.newMessage.setEditable(ToF);
			}
		});
	}
	
	
	
	
}

