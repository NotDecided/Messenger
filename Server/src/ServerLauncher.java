import ServerAndConnectionHandler.ServerSide;


public class ServerLauncher {

	public static void main(String[]args)
	{
		ServerSide server = new ServerSide();
		server.start();
	}
	
}
