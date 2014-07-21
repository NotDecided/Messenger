package SocketThreadTests;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class DataTransferClient 
{
	
	private final int PORT = 8888;
	private final String IP = "192.168.1.2";
	private Socket connection = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private InputStream is = null;
	private OutputStream os = null;
	
	private JTextField newQuery;
	private JTextArea queryHistory;
	
	public static void main(String[]args)
	{
		new DataTransferClient();
	}
	
	public DataTransferClient()
	{
		JFrame frame = new JFrame();
		frame.setSize(300,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
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
		queryHistory.setFont(new Font("Arial",Font.BOLD,18));
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
			connection = new Socket(IP,PORT);
			System.err.println("Connected successfully!");
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
			is = connection.getInputStream();
			os = connection.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
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
			inbox = dis.readUTF();
			
			if(inbox.contains("Receiving of File :"))
			{
				String fileName = inbox.replace("Receiving of File :","").trim();
				receiveTheData(fileName);
				System.err.println("File was successfully received!");
						
			}
			else if(inbox.contains("Sending of File :"))
			{
				String fileName = inbox.replace("Sending of File :", "");
				sendTheData(fileName);
				System.err.println("File was successfully sent!");
			}
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void receiveTheData(String fileName)
	{
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null;
		byte[] byteArray = new byte[1];
		
		try
		{
		is = connection.getInputStream();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		baos = new ByteArrayOutputStream();
		
		if(is != null)
		{
			
			try
			{
				
				File fileReceiving = new File("./CLIENT_SIDE/",fileName);
				
				fos = new FileOutputStream(fileReceiving);
				
				bos = new BufferedOutputStream(fos);
				
				int readBytes = is.read(byteArray,0,byteArray.length);
				
				do
				{
					baos.write(byteArray);
					readBytes = is.read(byteArray);
				}while(readBytes != -1);//do that do-while loop until readBytes is not equal to -1 (is not equal to end of file(end of bytes))
				
				bos.write(baos.toByteArray());
				bos.flush();
				bos.close();
				
				
			}
			catch(IOException ex1)
			{
				ex1.printStackTrace();
			}
		}
		
		
	}
	//http://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
	private void sendTheData(String fileName)
	{
		String RealFileName = fileName.trim();
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		
		File file = new File("./CLIENT_SIDE/"+RealFileName);
		
		try
		{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(connection.getOutputStream());
			
			byte[] byteArray = new byte[(int)file.length()];
			
			
			int count ;
			while((count= bis.read(byteArray)) >0)
			{
				bos.write(byteArray,0,count);
			}
			
			bos.flush();
			bos.close();
			bis.close();
			fis.close();
			
		}
		catch( IOException ex)
		{
			
		}
		
	
	}
	
	private void sendMessage(final String query)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					dos.writeUTF("\n"+query+"\n");
					showMessage(query);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void showMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				queryHistory.append("\n"+message+"\n");
			}
		});
	}
}

