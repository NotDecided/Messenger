package SocketFileExchanging;

import java.net.*;
import java.io.*;
import javax.swing.*;

public class ServerSide
{
	
	public static void main(String[]args) throws IOException
	{
		
		ServerSocket server = new ServerSocket(8888);
		File myFile = new File("USA.jpg");
		
		Socket client = server.accept();
		
			
			while(client.isConnected())
			{
			System.err.println("client is connected");	
			
			byte[] myByteArray = new byte[(int)myFile.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
			bis.read(myByteArray, 0, myByteArray.length);
			OutputStream os = client.getOutputStream();
			
			
			os.write(myByteArray, 0, myByteArray.length);
			os.flush();
			client.close();
			}
			
			
		
		
		
	}
	
	
	
}