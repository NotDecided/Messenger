package AdvancedSocketServerStuff;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.swing.*;

public class Client 
{
	
	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
	
	private Socket connection = null;
	private InputStream is = null;
	private OutputStream os = null;
	private ObjectInputStream ois ;
	private ObjectOutputStream oos ;
	private DataOutputStream dos ;
	private DataInputStream dis ;
	
	private JTextField TFloginUsername,TFloginPassword,TFregistrationUsername,TFregistrationPassword,TFregistrationEmail,
	     TFregistrationFirstName,TFregistrationLastName,TFregistrationBirthDate,newMessage;
	
	private JPasswordField PFsignIn,PFsignUp;
	
	private JLabel LloginUsername,LloginPassword, LregistrationUsername, LregistrationPassword, LregistrationEmail,
	     LregistrationFirstName, LregistrationLastName, LregistrationBirthDate;
	
	private JButton BsignUp, BsignIn, BsingOut , Bsubmit , BregistrationSignIn;
	
	private JFrame frame;
	
	private JPanel PsignIn, PsignUp , PsignedIn;
	
	private JTextArea chatHistory;

	
	public static void main(String[]args)
	{
		new Client();
	}
	
	public Client()
	{
		setupGUI();
		try
		{
			
			connectionEstablishment();
			
			try{
			streamsSetup();
			queryRetreiver();
			}
			catch(IOException ex1)
			{
				ex1.printStackTrace();
			}
			
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	private void setupGUI()
	{
		
		frame = new JFrame();
		frame.setTitle("Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(400,550);
		
		
		signIn();
		
	}
	
	private void signIn()
	{
		
		
		
		PsignIn = new JPanel();
		PsignIn.setVisible(true);
		PsignIn.setLayout(null);
		
		
		LloginUsername = new JLabel("Username");
		LloginUsername.setFont(new Font("Arial",Font.BOLD,20));
		Dimension size = LloginUsername.getPreferredSize();
		LloginUsername.setBounds(150,10, 140, 35);
		PsignIn.add(LloginUsername);
		
		TFloginUsername = new JTextField();
		size = TFloginUsername.getPreferredSize();
		TFloginUsername.setBounds(125, 55, 150, 25);
		PsignIn.add(TFloginUsername);
		
		LloginPassword = new JLabel("Password");
		LloginPassword.setFont(new Font("Arial",Font.BOLD,20));
		size = LloginPassword.getPreferredSize();
		LloginPassword.setBounds(150, 90, 140, 35);
		PsignIn.add(LloginPassword);
		
		PFsignIn = new JPasswordField();
		size = PFsignIn.getPreferredSize();
		PFsignIn.setBounds(125, 135, 150, 25);
		PsignIn.add(PFsignIn);
		
		BsignIn = new JButton();
		BsignIn.setText("Sign In");
		BsignIn.setFont(new Font("Arial",Font.BOLD,8));
		BsignIn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
				String username = TFloginUsername.getText();
				String password = String.valueOf(PFsignIn.getPassword());
				
				if(username.trim().length() > 0 && password.length() > 0)//in the future I'll make password security more advanced
				{
					querySender("/LOG IN");
					logIn(username,password);//sending data to the server
					
					//Clearing of our text fields(username , password fields)
					TFloginUsername.setText("");
					PFsignIn.setText("");
					//
					
				}
				else
				{
				  
					JOptionPane.showMessageDialog(PsignIn, "Please Enter Your Login Data", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
		});
		size = BsignIn.getPreferredSize();
		BsignIn.setBounds(125,170,70,30);
		PsignIn.add(BsignIn);
		
		BsignUp = new JButton();
		BsignUp.setText("Sign Up");
		BsignUp.setFont(new Font("Arial",Font.BOLD,8));
		BsignUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				signUp();
				PsignIn.setVisible(false);
				
			}
		});
		size = BsignUp.getPreferredSize();
		BsignUp.setBounds(205,170,70,30);
		PsignIn.add(BsignUp);
		
		frame.add(PsignIn);
	}
	
	private void signUp()
	{
		
		
		PsignUp = new JPanel();
		PsignUp.setVisible(true);
		PsignUp.setLayout(null);
		
		LregistrationUsername = new JLabel("Username");
		LregistrationUsername.setFont(new Font("Aria",Font.BOLD,15));
		Dimension size = LregistrationUsername.getPreferredSize();
		LregistrationUsername.setBounds(160,10,80,25);
		PsignUp.add(LregistrationUsername);
		
		TFregistrationUsername = new JTextField();
		size = TFregistrationUsername.getPreferredSize();
		TFregistrationUsername.setBounds(125,35,150,30);
		PsignUp.add(TFregistrationUsername);
		
		LregistrationPassword = new JLabel("Password");
		LregistrationPassword.setFont(new Font("Arial",Font.BOLD,15));
		size = LregistrationPassword.getPreferredSize();
		LregistrationPassword.setBounds(160,75,80,25);
		PsignUp.add(LregistrationPassword);
		
		PFsignUp = new JPasswordField();
		size = PFsignUp.getPreferredSize();
		PFsignUp.setBounds(125, 110, 150, 30);
		PsignUp.add(PFsignUp);
		
		LregistrationEmail = new JLabel("Email");
		LregistrationEmail.setFont(new Font("Arial",Font.BOLD,15));
		size = LregistrationEmail.getPreferredSize();
		LregistrationEmail.setBounds(160,150,80,20);
		PsignUp.add(LregistrationEmail);
		
		TFregistrationEmail = new JTextField();
		size = TFregistrationEmail.getPreferredSize();
		TFregistrationEmail.setBounds(125, 180, 150, 30);
		PsignUp.add(TFregistrationEmail);
		
		LregistrationFirstName = new JLabel("First Name");
		LregistrationFirstName.setFont(new Font("Arial",Font.BOLD,15));
		size = LregistrationFirstName.getPreferredSize();
		LregistrationFirstName.setBounds(160, 220, 80, 20);
		PsignUp.add(LregistrationFirstName);
		
		TFregistrationFirstName = new JTextField();
		size = TFregistrationFirstName.getPreferredSize();
		TFregistrationFirstName.setBounds(125, 250, 150, 30);
		PsignUp.add(TFregistrationFirstName);
		
		LregistrationLastName = new JLabel("Last Name");
		LregistrationLastName.setFont(new Font("Arial",Font.BOLD,15));
		size = LregistrationLastName.getPreferredSize();
		LregistrationLastName.setBounds(160, 290, 80, 20);
		PsignUp.add(LregistrationLastName);
		
		TFregistrationLastName = new JTextField();
		size = TFregistrationLastName.getPreferredSize();
		TFregistrationLastName.setBounds(125,320,150,30);
		PsignUp.add(TFregistrationLastName);
		
		LregistrationBirthDate = new JLabel("Birth Date");
		LregistrationBirthDate.setFont(new Font("Arial",Font.BOLD,15));
		size = LregistrationBirthDate.getPreferredSize();
		LregistrationBirthDate.setBounds(160,360,80,20);
		PsignUp.add(LregistrationBirthDate);
		
		TFregistrationBirthDate = new JTextField();
		size = TFregistrationBirthDate.getPreferredSize();
		TFregistrationBirthDate.setBounds(125, 390, 150, 30);
		PsignUp.add(TFregistrationBirthDate);
		
		Bsubmit = new JButton("Submit");
		Bsubmit.setFont(new Font("Arial",Font.BOLD,10));
		Bsubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
			
				String username = TFregistrationUsername.getText();
				//password
				char[] CharPassword = PFsignUp.getPassword();
				String password = String.valueOf(CharPassword);
				//
				String email = TFregistrationEmail.getText();
				String firstName = TFregistrationFirstName.getText();
				String lastName = TFregistrationLastName.getText();
				String birthDate = TFregistrationBirthDate.getText();
				
				if(username.trim().length() > 0 && password.length() > 0 && email.trim().length() > 0 && firstName.trim().length() > 0 && lastName.trim().length() > 0 && birthDate.trim().length() > 0)
				{
					
				querySender("/CREATE A NEW USER");
				sendUserInfo(username,password,email,firstName,lastName,birthDate);
                

				
				}
				else
				{
					JOptionPane.showMessageDialog(PsignUp,"Please Fill in All Fields ","All Fields Should Be Filled With Data",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		size = Bsubmit.getPreferredSize();
		Bsubmit.setBounds(125, 430, 70, 35);
		PsignUp.add(Bsubmit);
		
		BregistrationSignIn = new JButton("Sign In");
		BregistrationSignIn.setFont(new Font("Arial",Font.BOLD,10));
		BregistrationSignIn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				signIn();
				PsignUp.setVisible(false);
			}
		});
		size = BregistrationSignIn.getPreferredSize();
		BregistrationSignIn.setBounds(205, 430, 70, 35);
		PsignUp.add(BregistrationSignIn);
		
		frame.add(PsignUp);
	}
	
	private void signedIn(String[] userInfo)
	{
		String[] userInformation = new String[2];
		
		userInformation = userInfo;
		
		PsignIn.setVisible(false);
		
		
		PsignedIn = new JPanel();
		PsignedIn.setLayout(null);
		PsignedIn.setVisible(true);
		
		JLabel firstName,lastName,birthDate;
		
		firstName = new JLabel(userInformation[0]+" "+userInformation[1]);
		firstName.setFont(new Font("Arial",Font.BOLD,12));
		Dimension size = firstName.getPreferredSize();
		firstName.setBounds(200,20,size.width,size.height);
		PsignedIn.add(firstName);
		
		birthDate = new JLabel("Birth Date : "+userInformation[2]);
		birthDate.setFont(new Font("Arial",Font.BOLD,12));
		size = birthDate.getPreferredSize();
		birthDate.setBounds(200, 50, size.width, size.height);
		PsignedIn.add(birthDate);		
		
		newMessage = new JTextField();
		newMessage.setFont(new Font("Times New Roman",Font.ITALIC,14));
		newMessage.setBounds(50,425,300,25);
		PsignedIn.add(newMessage);
		
		chatHistory = new JTextArea();
		chatHistory.setFont(new Font("Times New Roman",Font.ITALIC,16));
		chatHistory.setBounds(50, 125, 300, 300);
		chatHistory.setEditable(true);
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		PsignedIn.add(chatHistory);
		
		JScrollPane scroller = new JScrollPane(chatHistory,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setBounds(50, 125, 300, 300);
		PsignedIn.add(scroller);
		
		JButton signOut = new JButton("Sign Out");
		signOut.setFont(new Font("Arial",Font.BOLD,10));
		signOut.setBounds(160, 490, 80, 20);
		signOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				PsignedIn.setVisible(false);
				signIn();
				querySender("/RESET STREAMS");
				resetStreams();
				PsignIn.setVisible(true);
				
			}
		});
		PsignedIn.add(signOut);
		
		
		frame.add(PsignedIn);
		
	}
	
	private void connectionEstablishment() throws IOException
	{
		
		connection = new Socket(IP,PORT);
		System.out.println("Connection  successfully established");
		
	}
	
	private void streamsSetup() throws IOException
	{
		
	    oos = new ObjectOutputStream(connection.getOutputStream());
	    ois = new ObjectInputStream(connection.getInputStream());
		
		System.out.println("Streams are setup");
	}
	
	private void queryRetreiver()
	{
		String message ="";
		
		do
		{
		
		try
		{
		message = ois.readUTF();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		if(message.equals("/SUCCESSFULLY SIGNED IN"))
		{
			
			String[] userInfo = new String[2];
			try 
			{
				userInfo = (String[])ois.readObject();
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			
			System.out.println("Received First Name : "+userInfo[0]+" , Received Last Name : "+userInfo[1]+" , Received Birth Date : "+userInfo[2]);
			
			
			
			signedIn(userInfo);
			
		}
		else if(message.equals("/LOGGING IN FAILED"))
		{
			
		}
		else if(message.equals("/SEND NEW USER DATA"))
		{
			
		}
		if(message.equals("/NEW USER WAS SUCCESSFULLY CREATED"))
		{
			
			//JOptionPane.showMessageDialog(PsignUp,"New User Was Successfully Registered","Congratulations!",JOptionPane.INFORMATION_MESSAGE);
			//Cleaning of all fields after successful data sending
			TFregistrationUsername.setText("");
			PFsignUp.setText("");
			TFregistrationEmail.setText("");
			TFregistrationFirstName.setText("");
			TFregistrationLastName.setText("");
			TFregistrationBirthDate.setText("");
			//
		}
		else if(message.equals("/CREATING OF NEW USER FAILED"))
		{
			//reconfirmation of user's username,password,email
			TFregistrationUsername.setText("");
			PFsignUp.setText("");
			TFregistrationEmail.setText("");
			//
			//JOptionPane.showMessageDialog(PsignUp, "The User With this USERNAME already exists","Failed!",JOptionPane.WARNING_MESSAGE);
			
		}
		}while(!message.equals("/BYE "));
		
		
		
	}
	
	private void querySender(final String query)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					oos.writeUTF(query);
					
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void logIn(final String username, final String password)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					String[] logInInfo = {username,password};
					oos.writeObject(logInInfo);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void sendUserInfo(final String username,final String password,final String email,final String firstName,final String lastName,final String birthDate)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				String[] objectToSend = {username,password,email,firstName,lastName,birthDate};
				
				try 
				{
					oos.writeObject(objectToSend);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private void resetStreams()
	{
		try
		{
			os.close();
			oos.close();
			streamsSetup();
			
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}