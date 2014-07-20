package SocketFileExchanging;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientSide
{
	
	public static void main(String[]args) throws Exception
	{
		
		Socket connection = new Socket("127.0.0.1",8888);
		
		
		byte[] myByteArray = new byte[4293372];
		
		InputStream is = connection.getInputStream();
		
		FileOutputStream fos = new FileOutputStream(new File("./FinalDestination/","USA.jpg"));
		
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		int bytesRead = is.read(myByteArray, 0, myByteArray.length);
		while(bytesRead < 4293372)
		{
		bos.write(myByteArray, 0, bytesRead);
		}
		bos.close();
		
		connection.close();
		
		
		
	}
	
	
	
}