package AdvancedSocketServerStuff;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.management.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ClientSide 
{
	
	private static final int PORT = 8888;
	private static final String IP = "127.0.0.1";
	private Socket connection;
	
	
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	
	private JTextField nameField,sendTo,TFloginUsername,TFloginPassword,TFregistrationUsername,TFregistrationPassword,
	         TFregistrationEmail,TFregistrationFirstName,TFregistrationLastName,TFregistrationBirthDate;
	
	private JPasswordField PFsignIn,PFsignUp;
	
	private JTextArea chatHistory,newMessage;
	private JPanel chatHistoryPanel,receivedMessagePanel,sentMessagePanel;
	
    private JLabel LloginUsername,LloginPassword, LregistrationUsername, LregistrationPassword, LregistrationEmail,
    LregistrationFirstName, LregistrationLastName, LregistrationBirthDate;
    
    private JButton BsignUp, BsignIn, BsingOut , Bsubmit , BregistrationSignIn , deleteMessageBtn;
	
	private JPanel thePanel,chatPanel,PsignIn, PsignUp , PsignedIn;
	
	private String userNickName = "" , personReceiver = "";
	
	private JLabel LuserNickName ;
	
	private boolean started = false;
	
	private JFrame frame,onlineUsersFrame,friendsFrame,blacklistFrame;
	
	private Object[][] databaseInfo,databaseInfoFriends,databaseInfoBlacklist;
	private Object[] column,columnFriends,columnBlacklist;


	private JPopupMenu popupMenu,popupMenuFriends,popupMenuBlacklist;
	
	private JTable onlineUsersTable,FriendsTable,blacklistTable;
	private DefaultTableModel dTableModelOnlineUsersList , dTableModelFriendsList , dTableModelBlockedUsersList;
	private boolean tableCreated = false;
	
	private Object[] usersOnline , friendsList,blacklist,chatHistoryArray = null;
	
	private String userFullName ="";
	private static int user_ID = 0;
	private static int receiver_ID = 0;
	private String username="",user_firstName ="" , user_lastName ="";

	private GridBagConstraints constraints;
	private JScrollPane chatHistoryPanelscroller;
	
	private ArrayList<JPanel> messageContainer;
	
	private Thread threadUpdater;
	private ArrayList<Thread> threadList;
	private int theExactSelectedRow = 0;
	private static boolean threadIsRunning = false;
	private boolean chatActive = false;

	
	
	public void start()
	{
		
		try
		{
			frameProperties();
			connectToTheServer();
			//nameConfirmation();
			streamsSetup();
			signIn();
			commandsRecognizing();
			//chatGoesHere();
			//serverDataReceiver();
			
		}
		catch(IOException | ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private void frameProperties()
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				frame = new JFrame();
				frame.setSize(400,600);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				/*frame.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosing(WindowEvent e)
					{
						frame.setExtendedState(JFrame.ICONIFIED);
					}

				});*/
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setTitle("Client");
				
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} 
				catch (ClassNotFoundException | InstantiationException 
						| IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	private void signIn()
	{
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
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
						
						if(username.trim().length() > 0 && password.length() > 0)//in the future I'll make password security more advanced by using of hashing (encryption)
						{
							
							login(username,password);
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
				
			}//end of run() function
			
		});//end of swing utilities
		

	}
	
	private void signUp()
	{
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
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
							
							createNewUser(username,password,email,firstName,lastName,birthDate);

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
				
			}//end of run() function
			
		});//end of swing utilities
		
		
	}
	
	private void signedIn(String firstName,String lastName,String birthDate,int user_id)
	{
	

				chatPanel = new JPanel();
				chatPanel.setVisible(true);
				chatPanel.setLayout(null);
				
				LuserNickName = new JLabel("ID"+user_id+" "+firstName +" "+lastName);
				LuserNickName.setFont(new Font("Arial",Font.PLAIN,16));
				LuserNickName.setBounds(200, 2, 100, 20);
				chatPanel.add(LuserNickName);
				
				userFullName = firstName + " " + lastName;
				user_ID = user_id;
				user_firstName = firstName;
				user_lastName = lastName;
				
				started = true;
				
				sendTo = new JTextField();
				sendTo.setEditable(false);
				sendTo.setBounds(100,25,200,25);
				chatPanel.add(sendTo);
				
				messageContainer = new ArrayList<JPanel>();
				threadList = new ArrayList<Thread>();
				
				chatHistory = new JTextArea();
				chatHistory.setBounds(150,100,200,300);
				chatHistory.setEditable(false);
				chatHistory.setLineWrap(true);
				chatHistory.setWrapStyleWord(true);
				//chatPanel.add(chatHistory);
				
				chatHistoryPanel = new JPanel();
				chatHistoryPanel.setBackground(Color.decode("0xFFFFFF"));
				chatHistoryPanel.setBounds(0, 150, 400, 400);
				chatHistoryPanel.setLayout(new GridBagLayout());
				
				constraints = new GridBagConstraints();
				constraints.gridx = 1;
				constraints.weightx = 1;
				constraints.fill = GridBagConstraints.BOTH;
				
				
				chatHistoryPanelscroller = new JScrollPane(chatHistoryPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				chatHistoryPanelscroller.setBounds(0,150,392,400);
				chatHistoryPanelscroller.getVerticalScrollBar().setUnitIncrement(20);
				chatHistoryPanelscroller.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
				chatPanel.add(chatHistoryPanelscroller);
				
				JButton clearChatHistory = new JButton("Clear");
				clearChatHistory.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						chatHistory.setText("");
						chatHistoryPanel.removeAll();
						chatHistoryPanel.updateUI();
					}
				});
				clearChatHistory.setBounds(300, 50, 90, 30);
				chatPanel.add(clearChatHistory);
				
				newMessage = new JTextArea();
				newMessage.setBounds(100, 50, 200, 100);
				newMessage.setLineWrap(true);
				newMessage.setWrapStyleWord(true);
				System.out.println("CUrsor position = "+newMessage.getCaretPosition());
				newMessage.setSelectionStart(newMessage.getSelectionStart());
			    newMessage.addKeyListener(new KeyListener(){

					@Override
					public void keyPressed(KeyEvent e) {
						if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)
						{
	
							e.consume();//the solution!
							sendMessage(newMessage.getText());
							newMessage.setText("");
							newMessage.setCaretPosition(0);
							//newMessage.getca
							chatHistoryPanelscroller.getVerticalScrollBar().setValue(chatHistoryPanelscroller.getVerticalScrollBar().getMaximum());
							
						}
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
					
						
					}
			    	
			    });
				chatPanel.add(newMessage);
				
				JScrollPane newMessageScroller = new JScrollPane(newMessage,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				newMessageScroller.setBounds(100, 50, 200, 100);
				chatPanel.add(newMessageScroller);
				
				
				JButton getOnlineList = new JButton("Users"+ "\nOnline");
				getOnlineList.setToolTipText("Click to update list of users who are currently online.");
				getOnlineList.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						
						getUsersOnlineCount();
						
					}
				});
				getOnlineList.setBounds(300, 80, 90, 30);
				chatPanel.add(getOnlineList);
				
				JButton signOutBtn = new JButton("Sign Out");
				signOutBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						
						signOut();
						
					}
				});
				signOutBtn.setBounds(300, 0, 60, 20);
				chatPanel.add(signOutBtn);
				
				JButton friendsBtn = new JButton("Friends");
				friendsBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						retrieveFriendsList();
						//showFriends();
					}
				});
				friendsBtn.setBounds(10,25,90,25);
				chatPanel.add(friendsBtn);
				
				JButton onlineUsersBtn = new JButton("Online Users");
				onlineUsersBtn.setFont(new Font("Arial",Font.PLAIN,8));
				onlineUsersBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						retrieveOnlineUsers();
						//showOnlineUsers();
					}
				});
				onlineUsersBtn.setBounds(10, 50, 90, 25);
				chatPanel.add(onlineUsersBtn);
				
				JButton blacklistBtn = new JButton("Blacklist");
				blacklistBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						retrieveBlockedUsers();
					}
				});
				blacklistBtn.setBounds(10, 75, 90, 25);
				chatPanel.add(blacklistBtn);
				
				JButton exitBtn = new JButton("Exit");
				exitBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						closeConnectionAndExit();
					}
				});
				exitBtn.setBounds(150, 550, 100, 40);
				chatPanel.add(exitBtn);
				
             
				frame.add(chatPanel);
	
	}
	
	private void connectToTheServer() throws IOException ,ClassNotFoundException
	{
		
		connection = new Socket(IP,PORT);
		System.out.println("Client successfully connected to the Server!");
		
	}

	
	private void streamsSetup() throws IOException
	{
		oos = new ObjectOutputStream(connection.getOutputStream());
		ois = new ObjectInputStream(connection.getInputStream());
	}
	
	private void commandsRecognizing()
	{
		
		Object[] query = {};
		String firstName = "";
		String lastName = "";
		String birthDate = "";
		int user_id = 0;
		
		do
		{
			try 
			{
				query = (Object[])ois.readObject();
				String queryCallBack = (String)query[0];
				
				if(queryCallBack.equals("/LOGGING IN FAILED"))
				{
					System.out.println("LOGGIND IN FAILED ! ! !");
					JOptionPane.showMessageDialog(PsignIn, "Your username or password is invalid , please try again with valid data","Logging Failed !",JOptionPane.ERROR_MESSAGE);
				}
				else if(queryCallBack.equals("/CREATING OF NEW USER FAILED"))
				{
					JOptionPane.showMessageDialog(PsignUp, "User with this username and email exists !", "Registration Failed!", JOptionPane.ERROR_MESSAGE);
					
					//resetting of username , password and email textfields after failure
					TFregistrationUsername.setText("");
					PFsignUp.setText("");
					TFregistrationEmail.setText("");
					//
				}
				else if(queryCallBack.equals("/NEW USER WAS SUCCESSFULLY CREATED"))
				{
					JOptionPane.showMessageDialog(PsignUp, "New User was successfully created!", "Succeed!", JOptionPane.INFORMATION_MESSAGE);
					
					String username = TFregistrationUsername.getText();
					
					//resetting of all text fields after successful creation of user
					TFregistrationUsername.setText("");
					PFsignUp.setText("");
					TFregistrationEmail.setText("");
					TFregistrationFirstName.setText("");
					TFregistrationLastName.setText("");
					TFregistrationBirthDate.setText("");
					//
					
					PsignUp.setVisible(false);
					signIn();
					TFloginUsername.setText(username);
					PFsignIn.requestFocus();
				}
				
			} 
			catch (ClassNotFoundException | IOException e) 
			{
				e.printStackTrace();
			}
			
			
		}while(!query[0].equals("/SUCCESSFULLY SIGNED IN"));
		
		firstName = (String)query[1];
		lastName = (String)query[2];
		birthDate = (String)query[3];
		user_id = (int) query[4];
		
		PsignIn.setVisible(false);
		signedIn(firstName,lastName,birthDate,user_id);//here should be invoke later in order to update GUI ,as well as , in previous functions
		System.out.println("SUCCESSFULLY SIGNED IN ! ! !");
		
		try 
		{
			chatGoesHere();
		} 
		catch (ClassNotFoundException | IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void chatGoesHere() throws IOException , ClassNotFoundException
	{
		System.out.println("CHAT STARTED ! ! !");
		
		chatActive = true;
		
		do
		{
			
			Object[] message = (Object[]) ois.readObject();
		    System.out.println("INBOX : "+message[0]);
			if(message[0].equals("/USERS ONLINE"))
			{
				
				String toDisplay = "Online "+message[1]+" Users";
				messageContainer.add(messageToDisplay((String)message[2],(String)message[1],System.currentTimeMillis(),"INBOX",(int)message[3],(int)message[4]));
				updateGUI();
				
			}
			else if(message[0].equals("/CONNECTION CHECK"))
			{
				System.err.println("Connection successfully checked!");
			}
			else if(message[0].equals("/ONLINE USERS LIST IS SENDING"))
			{
				usersOnline = (Object[])ois.readObject();
				showOnlineUsers();
			}
			else if(message[0].equals("/FRIENDS LIST IS SENDING"))
			{
				friendsList = (Object[])ois.readObject();
				showFriends();
			}
			else if(message[0].equals("/BLACKLIST IS SENDING"))
			{
				blacklist = (Object[])ois.readObject();
				showBlacklist();
			}
			else if(message[0].equals("/CHAT HISTORY IS SENDING"))
			{
				chatHistoryArray = (Object[])ois.readObject();
				displayTheChatHistory();
			}
			else if(message[0].equals("/USER WAS SUCCESSFULLY BLOCKED"))
			{
				//complete this handling
				JOptionPane pane = new JOptionPane();
				JOptionPane.showConfirmDialog(FriendsTable,"Yes","No",JOptionPane.YES_NO_OPTION);
				System.out.print(pane.getValue());
				
				
			}
			else if(message[0].equals("/LOGGING OUT"))
			{
				chatActive = false;
			}
			
			if(message[0].equals("/DIRECT MESSAGE"))
			{
				//displayMessage((String)message[1]);
				constraints.anchor = GridBagConstraints.FIRST_LINE_END;
				chatHistoryPanel.add(messageToDisplay((String)message[2],(String)message[1],System.currentTimeMillis(),"INBOX",(int)message[4],(int)message[5]),constraints);
				chatHistoryPanelscroller.getVerticalScrollBar().setValue(chatHistoryPanelscroller.getVerticalScrollBar().getMaximum());
				chatHistoryPanel.updateUI();
			}
		
		}while(chatActive);
		
		signIn();
		chatPanel.setVisible(false);
		commandsRecognizing();
		
	}
	
	private void retrieveOnlineUsers()
	{
		try
		{
			Object[] toSend = {"/GET THE USERS ONLINE LIST",user_ID};
			oos.writeObject(toSend);
	
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void showOnlineUsers()
	{
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
			{
				tableCreated = true;
				onlineUsersFrame = new JFrame();
				onlineUsersFrame.setSize(250,500);
				onlineUsersFrame.setTitle("Online Users");
				onlineUsersFrame.setVisible(true);
				onlineUsersFrame.setLocationRelativeTo(frame);
				
				
				DefaultTableModel dTableModel = new DefaultTableModel(databaseInfo,column)
				{
					@Override
					public boolean isCellEditable(int row,int column)
					{
						return false;
					}
				};
				
				onlineUsersTable = new JTable(dTableModel);
				onlineUsersTable.setAutoCreateRowSorter(true);
				onlineUsersTable.repaint();
				if(dTableModel.getColumnCount() <1)
				{
					dTableModel.addColumn("ID");
					dTableModel.addColumn("First Name");
					dTableModel.addColumn("Last Name");
				}
				
			    //how to create frequently updated JTabel that performs well
				//http://www.oracle.com/technetwork/java/christmastree-138396.html
				
				
				for(int i = 0; i<usersOnline.length ; i++)
				{
					
					Object[] toRetrieveDataFromArray = (Object[])usersOnline[i];
					Object[] toAddToTheTable = {toRetrieveDataFromArray[0] ,toRetrieveDataFromArray[1],toRetrieveDataFromArray[2]};
					dTableModel.addRow(toAddToTheTable);
					
				}
				
				JScrollPane scroller = new JScrollPane(onlineUsersTable);
				onlineUsersFrame.add(scroller,BorderLayout.CENTER);
				
				
		        popupMenu = new JPopupMenu();
				
				
				
				
				
		        JMenuItem menuItem = new JMenuItem("Start a conversation");
				menuItem.setIcon(new ImageIcon("images/newMail.png"));
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = onlineUsersTable.getSelectedRow();
						
						String receiverName = String.valueOf(onlineUsersTable.getValueAt(row, 1)) + " " + String.valueOf(onlineUsersTable.getValueAt(row, 2));
						sendTo.setText(receiverName);
						receiver_ID = (int)onlineUsersTable.getValueAt(row, 0);
						onlineUsersFrame.setVisible(false);
					}
				});
				popupMenu.add(menuItem);//gotta complete this pop up menu stuff
				//and should figure out how it works.
				//*** http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#hierarchy
				popupMenu.addSeparator();
				
				
				menuItem = new JMenuItem("Add to a Friends List");
				menuItem.setIcon(new ImageIcon("./images/addFriendIcon.png"));
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = onlineUsersTable.getSelectedRow();
						int targetID = (int)onlineUsersTable.getValueAt(row, 0);
						addTheFriend(targetID);
					}
				});
				popupMenu.add(menuItem);
				
				onlineUsersTable.addMouseListener(new MouseAdapter(){
					
					@Override 
					public void mousePressed(MouseEvent e)
					{
					    checkPopup(e);
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount() % 2 == 0)
						{
							e.consume();
							int row = onlineUsersTable.getSelectedRow();
							
							String receiverName = String.valueOf(onlineUsersTable.getValueAt(row, 1)) + " " + String.valueOf(onlineUsersTable.getValueAt(row, 2));
							sendTo.setText(receiverName);
							receiver_ID = (int)onlineUsersTable.getValueAt(row, 0);
							onlineUsersFrame.setVisible(false);
							
							
						}
						checkPopup(e);
						
					}
					
					@Override
					public void mouseReleased(MouseEvent e)
					{
						checkPopup(e);
					}
					
					public void checkPopup(MouseEvent e)
					{
						if(e.isPopupTrigger())
						{
							popupMenu.show(onlineUsersTable,e.getX(),e.getY());
						}
					}
				});
			}//end of run() function
			
		});//end of swing utilities
	
		
		//*** http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#hierarchy


	}
	
	private void retrieveFriendsList()
	{
		try
		{
			Object[] toSend = {"/GET THE FRIENDS LIST",user_ID};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void showFriends()
	{
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				friendsFrame = new JFrame();
				friendsFrame.setSize(350,500);
				friendsFrame.setTitle("Friend List");
				friendsFrame.setLocationRelativeTo(frame);
				friendsFrame.setVisible(true);
				
				dTableModelFriendsList = new DefaultTableModel(databaseInfoFriends,columnFriends)
				{
					@Override
					public boolean isCellEditable(int row , int column)
					{
						return false;
					}
				};
				FriendsTable = new JTable(dTableModelFriendsList);
				FriendsTable.setAutoCreateRowSorter(true);
				
				if(dTableModelFriendsList.getColumnCount() < 1)
				{
					dTableModelFriendsList.addColumn("ID");
					dTableModelFriendsList.addColumn("First Name");
					dTableModelFriendsList.addColumn("Last Name");
					dTableModelFriendsList.addColumn("Status");
					
				}
				
				for(int i = 0 ; i< friendsList.length ; i++)
				{
					Object[] infoRetrieving = (Object[])friendsList[i];
					Object[] toAdd = {infoRetrieving[0],infoRetrieving[1],infoRetrieving[2],infoRetrieving[3]};
					dTableModelFriendsList.addRow(toAdd);
				}
				
				
				
				JScrollPane scroller = new JScrollPane(FriendsTable);
				friendsFrame.add(scroller,BorderLayout.CENTER);
			
				
				popupMenuFriends = new JPopupMenu();
				
				JMenuItem menuItem = new JMenuItem("Start a Conversation");
				menuItem.setIcon(new ImageIcon("./images/newMail.png"));
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = FriendsTable.getSelectedRow();
						receiver_ID = (int)FriendsTable.getValueAt(row, 0);
						String receiverFullName = FriendsTable.getValueAt(row, 1) + " " + FriendsTable.getValueAt(row, 2);
						sendTo.setText(receiverFullName);
						getChatHistory(user_ID,(int)FriendsTable.getValueAt(row, 0));
						friendsFrame.setVisible(false);
					}
				});
				popupMenuFriends.add(menuItem);
				
				popupMenuFriends.addSeparator();
				
				menuItem = new JMenuItem("Delete from a Friends List");
				menuItem.setIcon(new ImageIcon("./images/deleteIcon.png"));
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = FriendsTable.getSelectedRow();
						int targetID = (int)FriendsTable.getValueAt(row, 0);
						deleteTheFriend(targetID);
						dTableModelFriendsList.removeRow(row);
						
					}
				});
				popupMenuFriends.add(menuItem);
				
				menuItem = new JMenuItem("Block this User");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = FriendsTable.getSelectedRow();
						int targetID = (int)FriendsTable.getValueAt(row, 0);
						blockTheUser(targetID);
						dTableModelFriendsList.removeRow(row);
						
					}
				});
				popupMenuFriends.add(menuItem);
				
				FriendsTable.addMouseListener(new MouseAdapter(){
					//here should go a pop up menu actions defining 
					@Override
					public void mousePressed(MouseEvent e)
					{
						popupMenuShow(e);
					}
					
					@Override
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount() % 2 == 0)
						{
							e.consume();
							theExactSelectedRow = FriendsTable.getSelectedRow();
							receiver_ID = (int)FriendsTable.getValueAt(theExactSelectedRow, 0);
							String reveiverFullName = FriendsTable.getValueAt(theExactSelectedRow, 1) + " " + FriendsTable.getValueAt(theExactSelectedRow,2);
							sendTo.setText(reveiverFullName);

						
						    
							if(threadList.size() > 0)
							{
								for(int i = 0 ; i < threadList.size(); i ++)
								{
									
									    
										threadList.remove(i);
										ThreadUpdater.stopTheThread();
										
									
								}
							}
						    threadIsRunning = false;
						    
						    
						    threadIsRunning = true;
						    threadUpdater = new ThreadUpdater();
						    
						    threadList.add(threadUpdater);
						    threadUpdater.start();
						    
//**************************************************************************************
//Gotta work more on this concept , when i will be dealing with Android development :)							
//							new Thread()
//							{
//								public void run()
//								{
//									while(true)
//									{
//										try
//										{
//											Thread.sleep(2000);
//										}
//										catch(InterruptedException ex)
//										{
//											ex.printStackTrace();
//										}
//										getChatHistory(user_ID,(int)FriendsTable.getValueAt(row, 0));
//										System.out.println("Thread is Working!");
//									}
//								}
//							}.start();
//**************************************************************************************
							
							friendsFrame.setVisible(false);
						}
						popupMenuShow(e);
						
					}
					
					@Override
					public void mouseReleased(MouseEvent e)
					{
						popupMenuShow(e);
					}
					
					public void popupMenuShow(MouseEvent e)
					{
						if(e.isPopupTrigger())
						{
							popupMenuFriends.show(FriendsTable,e.getX(),e.getY());
						}
					}
					
				});
			}//end of run() function
		});//end of swing utilities
		
	}
	
	private void retrieveBlockedUsers()
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {"/GET THE BLOCKED USERS LIST",user_ID};
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
	}
	
	private void showBlacklist()
	{
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
			{
				
				//here comes blacklist window defining
				blacklistFrame = new JFrame();
				blacklistFrame.setTitle("Blacklist");
				blacklistFrame.setSize(250,500);
				blacklistFrame.setLocationRelativeTo(frame);
				blacklistFrame.setVisible(true);
				
				dTableModelBlockedUsersList = new DefaultTableModel(databaseInfoBlacklist, columnBlacklist)
				{
					@Override
					public boolean isCellEditable(int row,int column)
					{
						return false;
					}
				};
				
				if(dTableModelBlockedUsersList.getColumnCount() < 1)
				{
					dTableModelBlockedUsersList.addColumn("ID");
					dTableModelBlockedUsersList.addColumn("First Name");
					dTableModelBlockedUsersList.addColumn("Last Name");
				}
				
				blacklistTable = new JTable(dTableModelBlockedUsersList);
				blacklistTable.setAutoCreateRowSorter(true);

				
				for(int i = 0 ; i < blacklist.length ; i++)
				{
					Object[] userInfoObject = (Object[])blacklist[i];
					
					int userID = (int)userInfoObject[0];
					String firstName = (String)userInfoObject[1];
					String lastName = (String)userInfoObject[2];
					Object[] toAddToTheTable = {userID,firstName,lastName};
					dTableModelBlockedUsersList.addRow(toAddToTheTable);
				}
				
				JScrollPane scroller = new JScrollPane(blacklistTable);
				blacklistFrame.add(scroller);
				
				
				popupMenuBlacklist = new JPopupMenu();
				//complete pop up menu with all required methods 
				JMenuItem menuItem = new JMenuItem("Unblock");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = blacklistTable.getSelectedRow();
						int targetID = (int)blacklistTable.getValueAt(row, 0);
						unblockTheUser(targetID);
						dTableModelBlockedUsersList.removeRow(row);
						
					}
				});
				popupMenuBlacklist.add(menuItem);
				
				menuItem = new JMenuItem("Unblock and add to a Friends List");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int row = blacklistTable.getSelectedRow();
						int targetID = (int)blacklistTable.getValueAt(row,0);
						unblockTheUser(targetID);
						addTheFriend(targetID);
						dTableModelBlockedUsersList.removeRow(row);
					}
				});
				popupMenuBlacklist.add(menuItem);
				
				blacklistTable.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mousePressed(MouseEvent e)
					{
						showPopupMenu(e);
					}
					
					@Override
					public void mouseClicked(MouseEvent e)
					{
						showPopupMenu(e);
					}
					
					@Override
					public void mouseReleased(MouseEvent e)
					{
						showPopupMenu(e);
					}
					
					public void showPopupMenu(MouseEvent e)
					{
						if(e.isPopupTrigger())
						{
							popupMenuBlacklist.show(blacklistTable,e.getX(),e.getY());
						}
					}
					
				});
				
				
			}//end of run() method
			
		});//end of swing utilities
	}
	
    private void closeConnectionAndExit()
    {
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run()
    		{
    			 try
    	         {
    	       	    Object[] toSend = {"/DISCONNECT THE USER",user_ID};
    	       	    if(user_ID >0)
    	       	    oos.writeObject(toSend);
    	       	    
    	         }
    	    	 catch(IOException ex)
    	    	 {
    	    		 ex.printStackTrace();
    	    	 }
    	    	 System.exit(0);
    		}
    	});
    	 
    }
    
	
	private void sendUsersDataRetrievingConfirmation()
	{
		try
		{
			Object[] toSend = {"/GET USERS ONLINE LIST"};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void login(String username,String password)
	{
		try
		{
			//get along with friend system and message system by using mysql 
			//http://www.9lessons.info/2010/04/database-design-create-tables-and.html

			Object[] toSend = {"/LOG IN",username,password};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void signOut()
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					
					 Object[] toSend = {"/LOG OUT",user_ID};
					 oos.writeObject(toSend);
	    	       	
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
	}
	
	private void createNewUser(String username,String password,String email,String firstName ,String lastName , String birthDate)
	{
		try
		{
			Object[] toSend = {"/CREATE NEW USER",username,password,email,firstName,lastName,birthDate};
			oos.writeObject(toSend);
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void addTheFriend(int target_ID)
	{
		try
		{
			Object[] toSend = {"/ADD THE FRIEND",user_ID,target_ID};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void deleteTheFriend(int target_ID)
	{
		try
		{
			Object[] toSend = {"/REMOVE THE FRIEND",user_ID,target_ID};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void blockTheUser(int targetID)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {"/BLOCK THE USER",user_ID,targetID};
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void unblockTheUser(int targetID)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {"/UNBLOCK THE USER",user_ID,targetID};
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
	}
	
	private void sendMessage(final String message)
	{
       SwingUtilities.invokeLater(new Runnable(){
    	   public void run(){
    		   try
    	   		{
    			   
    			    Object[] toSend = {"/SEND MESSAGE",user_ID,message,receiver_ID,System.currentTimeMillis(),user_firstName,user_lastName};
    			    String user_fullName = user_firstName + " "+user_lastName;
    			    displayMessage(message,user_fullName,user_ID,receiver_ID);
    	   			oos.writeObject(toSend);
    	   		}
    	   		catch(IOException ex)
    	   		{
    	   			ex.printStackTrace();
    	   		}
    	   }
       });
		
	}
	
	
	
	private void displayMessage(final String message,final String senderName,int sent_from,int sent_to)
	{
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				if(started)
				{
				chatHistory.setFont(new Font("Arial",Font.BOLD,14));

				messageContainer.add(messageToDisplay(senderName,message,System.currentTimeMillis(),"OUTBOX",sent_from,sent_to));
				updateGUI();
				
				}
			}
		});
		
	}
	
	private void updateGUI()
	{
		
		for(int i = 0 ; i < messageContainer.size() ; i++)
		{
			chatHistoryPanel.add(messageContainer.get(i),constraints);
		}
		
		chatHistoryPanel.updateUI();
		chatHistoryPanelscroller.getVerticalScrollBar().setValue(chatHistoryPanelscroller.getVerticalScrollBar().getMaximum());
		
		
	}
	
	public static void getChatHistory(final int senderID,final int receiverID)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				try
				{
					Object[] toSend = {"/GET CHAT HISTORY",senderID,receiverID};
					oos.writeObject(toSend);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void displayTheChatHistory()
	{
		//clearing of chatHistory panel
		chatHistoryPanel.removeAll();
		//
		
		//adding of message history to our chat history panel
		
		
		System.err.println("Size of chat history = "+chatHistoryArray.length);
		for(int i = 0 ; i < chatHistoryArray.length ; i++)
		{
			Object[] retrievedObject = (Object[])chatHistoryArray[i];
			String typeOfMessageInboxOrOutbox = "";
			
			if(retrievedObject[0].equals(userFullName))
			{
				typeOfMessageInboxOrOutbox = "OUTBOX";
			}
			else
			{
				typeOfMessageInboxOrOutbox = "INBOX";
			}
			
			chatHistoryPanel.add(messageToDisplay(String.valueOf(retrievedObject[0]),(String)retrievedObject[1],(long)retrievedObject[3],typeOfMessageInboxOrOutbox,(int)retrievedObject[4],(int)retrievedObject[5]),constraints);
		}
		chatHistoryPanel.updateUI();
		System.out.println("the message history was loaded successfully!");
		//
		
	}
	
	private JPanel messageToDisplay(String sender , String message , long time , String typeInboxOrOutbox,int sent_from,int sent_to)
	{
	
		if(started)
		{
			JPanel newMessagePanel = new JPanel();
			newMessagePanel.setLayout(new GridBagLayout());
			if(typeInboxOrOutbox.equals("INBOX"))
			{
				newMessagePanel.setBackground(Color.decode("0xEAF0F2"));
			}
			else
			{
				newMessagePanel.setBackground(Color.decode("0xFFFFFF"));
			}
			GridBagConstraints constraints = new GridBagConstraints();
			
			JLabel senderName = new JLabel(sender);
			senderName.setFont(new Font("Arial",Font.BOLD,12));
			senderName.setForeground(Color.decode("0x93989A"));
			constraints.gridwidth = 1;
			constraints.gridx = 1;
			constraints.insets = new Insets(5,10,5,10);
			constraints.anchor = GridBagConstraints.FIRST_LINE_START;
			constraints.weighty = 0;
			constraints.weightx = 1;
		
		
			
			newMessagePanel.add(senderName,constraints);
			
			
			JTextArea messageText = new JTextArea();
			messageText.setColumns(15);
			messageText.setEditable(false);
			messageText.setLineWrap(true);
			messageText.setFont(new Font("Arial",Font.PLAIN,14));
			messageText.setBackground(newMessagePanel.getBackground());
			messageText.setWrapStyleWord(true);
			messageText.setText(message);
			constraints.gridwidth = 3;
			constraints.gridx = 2;
			constraints.insets = new Insets(5,10,5,10);
			constraints.anchor = GridBagConstraints.PAGE_START;
			constraints.weighty = 0;
			
			newMessagePanel.add(messageText,constraints);
			
			
		
			
			
			
			JLabel timeLabel = new JLabel(getConvertedTime(time));
			timeLabel.setFont(new Font("Arial",Font.ITALIC,12));
			timeLabel.setForeground(Color.decode("0x93989A"));
			constraints.gridwidth = 1;
			constraints.gridx = 6;
			constraints.insets = new Insets(5,10,5,10);
			constraints.anchor = GridBagConstraints.FIRST_LINE_END;
			constraints.weighty = 0;
			
			
			
			newMessagePanel.add(timeLabel,constraints);
			
			
			JTextArea edit = new JTextArea(5,15);
			edit.setVisible(false);
			edit.setLineWrap(true);
			edit.setFont(new Font("Arial",Font.PLAIN,14));
			
			constraints.gridwidth = 3;
			constraints.gridx = 2;
			//constraints.gridy = 2;
			constraints.insets = new Insets(5,10,5,10);
			constraints.anchor = GridBagConstraints.PAGE_START;
			constraints.weighty = 0;
			
			newMessagePanel.add(edit,constraints);
			
		  
			JPopupMenu popupMenuMessagePanel = new JPopupMenu();
			
			JMenuItem menuItem = new JMenuItem("Edit");
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					
				}
			});
			popupMenuMessagePanel.add(menuItem);
			
			menuItem = new JMenuItem("Panel's time");
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Time without formatting : "+time + "\n Time with formatting :"+timeLabel.getText());
				}
			});
			
			
			popupMenuMessagePanel.add(menuItem);
			
			menuItem = new JMenuItem("Delete");
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					deleteTheMessageFromChatHistory(sent_from,message,sent_to,time);
					//implement this method!
				}
			});
			popupMenuMessagePanel.add(menuItem);
			
			newMessagePanel.addMouseListener(new MouseAdapter(){
				
				@Override
				public void mousePressed(MouseEvent e)
				{
					showPopupMenu(e);
				}
				
				@Override
				public void mouseClicked(MouseEvent e)
				{
					showPopupMenu(e);
				}
				
				@Override
				public void mouseReleased(MouseEvent e)
				{
					showPopupMenu(e);
				}
				
				public void showPopupMenu(MouseEvent e)
				{
					if(e.isPopupTrigger())
					{
						if(typeInboxOrOutbox.equals("OUTBOX"))
						{
							popupMenuMessagePanel.show(newMessagePanel,e.getX(),e.getY());
						}
					}
				}
				
			});
			
			
			System.out.println("newMessagePanel returning works! + number of columns = ");
			return newMessagePanel;
		}
		return null;

		
	}
	
	private void deleteTheMessageFromChatHistory(int sent_from,String message,int sent_to,long time)
	{
		try
		{
			Object[] toSend = {"/DELETE THE MESSAGE",sent_from,message,sent_to,time};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void getUsersOnlineCount()
	{
		try
		{
			Object[] toSend = {"/GET USERS ONLINE LIST",user_ID};
			oos.writeObject(toSend);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private String getTime()
	{
	    Calendar calendar = Calendar.getInstance();
	    
	    int hours = calendar.get(Calendar.HOUR_OF_DAY);
	    int minutes = calendar.get(Calendar.MINUTE);
	    int seconds = calendar.get(Calendar.SECOND);
	    
	    String time = hours+":"+minutes+":"+seconds;
	    
	    return time;
	}
	
	private String getConvertedTime(long time)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		String toReturn = simpleDateFormat.format(time);
		return toReturn;
	}
	
	
	class PopupListener extends MouseAdapter
	{
		
		@Override 
		public void mousePressed(MouseEvent e)
		{
			checkPopup(e);
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			checkPopup(e);
		}
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			checkPopup(e);
		}
		
		public void checkPopup(MouseEvent e)
		{
			if(e.isPopupTrigger())
			{
				popupMenu.show(chatHistory,e.getX(),e.getY());
			}
		}
		
	}
	
	public static class ThreadUpdater extends Thread
	{
	
        
		public static void stopTheThread()
		{
			threadIsRunning = false;
		}
		public void run()
		{
		
			
			while(threadIsRunning)
			{
				
				try 
				{
					ThreadUpdater.sleep(1000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				System.out.println("Updating...");
			    getChatHistory(user_ID, receiver_ID);
			    System.out.println("Updated successfully!");
				
			}
			
			
		}
		
	}
	
}