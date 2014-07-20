
import java.io.*;
import java.net.*;

public class TestAndroidConnection {

	public static void main(String[]args)
	{
		new TestAndroidConnection();
		
	}
	
	public TestAndroidConnection()
	{
		try {
			ServerSocket server = new ServerSocket(8888);
			System.out.println("SERVER was successfully created!");
			while(true)
			{
				Socket connection = server.accept();
				System.out.println("Android device was successfully connected!!!");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
