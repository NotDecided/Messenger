package AdvancedSocketServerStuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.StyledEditorKit;


public class test extends JFrame
{
	private JPanel thePanel = null,messagePanel;
	private JLabel imageLabel,imageLabel2,messageLabel;
	private JScrollPane scroller;
	
	
	public static void main(String[]args)
	{
		
	/*	ArrayList<Integer> testAL = new ArrayList<Integer>();
		testAL.add(44);
		testAL.add(55);
		testAL.add(42);
		
		for(int i = 0 ; i< testAL.size() ; i++)
		{
		
			System.out.println(testAL.get(i));
		}
		
		System.out.println();
		
		testAL.remove(testAL.indexOf(42));
		
		
		
		for(int i = 0 ; i< testAL.size() ; i++)
		{
		
			System.out.println(testAL.get(i));
		}*/
		
		/*
		Object[] array1 ={ 123,"I'm array #1",false};
		Object[] array2 = {456,"I'm array #2",true};
		Object[] array3 = {789,"I'm array #3",false};
		
		ArrayList<Object[]> test = new ArrayList<Object[]>();
		test.add(array1);
		test.add(array2);
		test.add(array3);
		
		
		for(int i = 0; i<test.size() ; i++)
		{
			Object[] testArray = (Object[]) test.get(i);
			System.out.println("array"+(i+1)+"[0] ="+testArray[0]);
		}
		
	    boolean toRemoveTest = (boolean) test.get(1)[2];
	    //test.remove(test.indexOf(toRemoveTest));
	    
	    System.out.println();
	    
	    for(int i = 0 ; i<test.size(); i++)
	    {
	    	Object[] toDeleteTest = test.get(i);
	    	if((int)toDeleteTest[0] == 456)
	    	{
	    		Object[] newObj = test.get(i);
	    		System.out.println(test.indexOf(newObj));
	    		//test.remove(i);
	    	}
	    }
	    
	    
	    for(int i = 0; i<test.size() ; i++)
		{
			Object[] testArray = (Object[]) test.get(i);
			//System.out.println("array"+(i+1)+"[0] ="+testArray[0]);
		}*/
	    
	    
		
	/*	DataBase.startDataBase();
		
		int mainID = 32;
		ArrayList<Object> IDal = new ArrayList<Object>();
		ArrayList<Object[]> Infoal = new ArrayList<Object[]>();
		try
		{
			String query1 = "select friend_two from friends where friend_one=\""+mainID+"\" ;";
			DataBase.query = DataBase.statement.executeQuery(query1);
			while(DataBase.query.next())
			{
				IDal.add(DataBase.query.getInt("friend_two"));
				
			}
			System.out.println("size of main array list ="+IDal.size());
			
			for(int i = 0 ; i < IDal.size() ; i++)
			{
			
				String query2 = "select user_id,firstName,lastName from users where user_id=\""+(int)IDal.get(i)+"\" limit 1 ;";
				DataBase.query = DataBase.statement.executeQuery(query2);
				while(DataBase.query.next())
				{
				Object[] toAdd = {DataBase.query.getInt("user_id"),DataBase.query.getString("firstName"),DataBase.query.getString("lastName")};
				Infoal.add(toAdd);
				}
			}
			
			System.out.println("Info array list size ="+Infoal.size()+" , Info arary list first elemnt's user_id + first name + last name = ID: "+Infoal.get(0)[0]+" " +Infoal.get(0)[1] 
					+" "+Infoal.get(0)[2]+". !!!");
		
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}*/
		
		
		
		
		
		//************************************************************************
		//THE BEST WAY OF ADDING OF THE  IMAGE TO THE PANEL OR TO THE FRAME ITSELF
		//************************************************************************
		//
		//  try
		//  {
		//     BufferedImage bufferedImage = ImageIO.read("straight path to your image");
		//     JLabel imageLabel = new JLabel(new ImageIcon(bufferedImage));
		//     panelName.add(imageLabel);
		//  }
		//  catch(IOException ex)
		//  {
		//     ex.printStackTrace();
		//  }
		//
		//
		//
		//
		//************************************************************************
		//                   END     OF      THE     METHOD
		//************************************************************************
		
		
		new test();
		
	}
	
	public test()
	{
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run()
			{
				setVisible(true);
				setSize(400,500);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   
				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new BorderLayout());
				mainPanel.setBackground(Color.decode("0xFFFFFF"));
				
				try
				{
					setIconImage(ImageIO.read(new File("./images/deleteIcon.png")));
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
				thePanel = new JPanel();
				thePanel.setBackground(Color.decode("0xFFFFFF"));
				thePanel.setLayout(new GridBagLayout());
				//thePanel.setLayout(null);
			    GridBagConstraints constraints = new GridBagConstraints();
			    constraints.weightx = 1;
			    constraints.gridx = 1;
			    constraints.fill = GridBagConstraints.BOTH;
			    constraints.anchor = GridBagConstraints.CENTER;
			    
			    
			    //constraints.
	
			 
			    
			    Calendar cal = Calendar.getInstance();
			    cal.getTime();
			    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			    System.out.println("Time + Date :"+sdf.format(System.currentTimeMillis()));
			    System.out.println("Time + Date :"+System.currentTimeMillis());
				
				BufferedImage image = null;
				
				ArrayList<JPanel> testAL = new ArrayList<JPanel>();
				
				JButton button = new JButton("");
				button.setBounds(160, 0, 80, 30);
				button.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						SwingUtilities.invokeLater(new Runnable(){
							public void run()
							{
								/*JPanel add = (JPanel)newMessagePrototype("test"+Math.round(Math.random()*10),"test test test");
								add.setBounds(10, 110*testAL.size(), 360, 110);
								testAL.add(add);
								thePanel.add(add);
								thePanel.updateUI();*/
								

								for(int i = 0 ; i < testAL.size(); i++)
								{
									
									
									testAL.remove(i);
									
									
								}
								thePanel.removeAll();
								
								
								try
								{
								DataBase.startDataBase();
								
								String testQuery = "select sent_from,message from privatemessages ;";
								DataBase.query = DataBase.statement.executeQuery(testQuery);
								boolean active = true;
								
									
									
								
									
									while(DataBase.query.next())
									{
										if(DataBase.query.getInt("sent_from")== 30)
										{
											thePanel.add((JPanel) newMessagePrototype("ID"+DataBase.query.getInt("sent_from"),DataBase.query.getString("message")),constraints);
											
										}
										else
										{
											thePanel.add(newMessagePrototype2("ID"+DataBase.query.getInt("sent_from"),DataBase.query.getString("message")),constraints);
										}
									}
									
									
									
								
									thePanel.updateUI();
								
								}
								catch(SQLException ex)
								{
									ex.printStackTrace();
								}
								thePanel.updateUI();
								
							}
						});
						
					}
				});
				button.setIcon(new ImageIcon("./images/addFriendIcon.png"));
				mainPanel.add(button);
				
				
				
				//thePanel.setBounds(0, 0, 400, 400);
				//add(thePanel);;
				//thePanel.setPreferredSize(new Dimension);
				
				
				
				mainPanel.add(thePanel,BorderLayout.SOUTH);
				
				scroller = new JScrollPane(thePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
				scroller.setPreferredSize(new Dimension(400,400));
				scroller.getVerticalScrollBar().setPreferredSize(new Dimension(8,0));
				scroller.getVerticalScrollBar().setUnitIncrement(20);
				mainPanel.add(scroller,BorderLayout.SOUTH);
				
				JTextField tf = new JTextField(20);
				tf.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
	
						testAL.add((JPanel)newMessagePrototype("Arthur",e.getActionCommand()));
						scroller.getVerticalScrollBar().setValue(scroller.getVerticalScrollBar().getMaximum());
						thePanel.add((JPanel)newMessagePrototype("Arthur",e.getActionCommand()),constraints);
						thePanel.updateUI();
						
						
					}
				});
				mainPanel.add(tf,BorderLayout.NORTH);
				
				add(mainPanel);
				
				
			}
			
		});
		
		
	}

	public JComponent newMessagePrototype(String sender , String message )
	{
		messagePanel = new JPanel();
	    messagePanel.setLayout(new GridBagLayout());
	
	    
		messagePanel.setBackground(Color.decode("0xFFFFFF"));
		
		
		BufferedImage inputtedImage = null;
		
		try
		{
			inputtedImage = ImageIO.read(new File("./images/newUser.png"));
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		//imageLabel = new JLabel(new ImageIcon(inputtedImage));
		//imageLabel.setBounds(5, 5, 100, 100);
		//messagePanel.add(imageLabel);
		//THE GOLDEN SOLUTION !!!
		//http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		// grid bag layout is the freakin golden solution !
		GridBagConstraints constraints = new GridBagConstraints();
		
	
		JLabel senderName = new JLabel(sender);
		senderName.setForeground(Color.decode("0x93989A"));
		//senderName.setBounds(150, 2, 60, 16);
		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.anchor = constraints.FIRST_LINE_START;
		constraints.insets = new Insets(10, 10, 10, 10);
		messagePanel.add(senderName,constraints);
		
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		String sendingTime = hours +":"+minutes+":"+seconds;
		
		JLabel time = new JLabel(sendingTime);
		time.setFont(new Font("Arial",Font.ITALIC,12));
		time.setForeground(Color.decode("0x93989A"));		
		
		constraints.gridwidth = 1;
		constraints.gridx = 6;
		constraints.anchor = constraints.FIRST_LINE_END;
		constraints.insets = new Insets(10, 10, 10, 10);
		
		messagePanel.add(time,constraints);
		
		
		
		String receivedMessage = message;
		int columns = 30;
		int rows = receivedMessage.length()/columns;
		JTextArea messageText = new JTextArea();
		messageText.setColumns(20);
		messageText.setLineWrap(true);
		messageText.setText(message);
		messageText.setEditable(false);
		messageText.setFont(new Font("Arial",Font.PLAIN,12));

		
		
		messagePanel.setSize(360,10);;
		
		constraints.gridwidth = 3;
		constraints.gridx = 2;
		constraints.weightx = 0;
		constraints.insets = new Insets(10, 20, 10, 20);
		constraints.anchor = constraints.PAGE_START;

		messagePanel.add(messageText,constraints);
		
		
		
		
	
		
		
		messagePanel.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				System.out.println("You clicked on panel "+sender);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() % 2 == 0)
				{
					e.consume();
					//messageLabel.setText("here comes the MATH "+Math.random());
					messagePanel.updateUI();
					
				}
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			
		});
		
		//messagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		messagePanel.setToolTipText(" "+senderName.getText());
		return messagePanel;
		
		
	}
	
	private JPanel newMessagePrototype2(String sender , String message)
	{
		messagePanel = new JPanel();
	    messagePanel.setLayout(new GridBagLayout());
	
	    
		messagePanel.setBackground(Color.decode("0xEAF0F2"));
		
		
		BufferedImage inputtedImage = null;
		
		try
		{
			inputtedImage = ImageIO.read(new File("./images/deleteIcon.png"));
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		ImageIcon icon = new ImageIcon(inputtedImage);
		imageLabel = new JLabel(new ImageIcon(inputtedImage));
		imageLabel.setBounds(5, 5, 100, 100);
		//messagePanel.add(imageLabel);
		//THE GOLDEN SOLUTION !!!
		//http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		// grid bag layout is the freakin golden solution !
		
		GridBagConstraints constraints = new GridBagConstraints();
	    
		
	
		JLabel senderName = new JLabel(sender);
		senderName.setForeground(Color.decode("0x93989A"));
		//senderName.setBounds(150, 2, 60, 16);
		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.anchor = constraints.FIRST_LINE_START;
		constraints.insets = new Insets(10, 10, 10, 10);
		messagePanel.add(senderName,constraints);
		
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		String sendingTime = hours +":"+minutes+":"+seconds;
		
		JLabel time = new JLabel(sendingTime);
		time.setFont(new Font("Arial",Font.ITALIC,12));
		time.setForeground(Color.decode("0x93989A"));		
		
		constraints.gridwidth = 1;
		constraints.gridx = 5;
		constraints.anchor = constraints.FIRST_LINE_END;
		constraints.insets = new Insets(10, 10, 10, 10);
		
		messagePanel.add(time,constraints);
		
		
		
		String receivedMessage = message;
		int columns = 30;
		int rows = receivedMessage.length()/columns;
		JEditorPane messageText = new JEditorPane();
		//messageText.setColumns(20);
		//messageText.setLineWrap(true);
		
		
		messageText.setEditorKit(new StyledEditorKit());
		messageText.setContentType("text/html");
		messageText.setText(message+"<img src=\"http://i269.photobucket.com/albums/jj72/myem0/01/cute-rabbit-2-005.gif\" />"+" <img src=\"http://i269.photobucket.com/albums/jj72/myem0/01/cute-rabbit-2-005.gif\" />");

		messageText.setEditable(false);
		messageText.setBackground(Color.decode("0xEAF0F2"));
		messageText.setFont(new Font("Arial",Font.PLAIN,12));

		
		
		messagePanel.setSize(360,10);;
		
		constraints.gridwidth = 3;
		constraints.gridx = 2;
		constraints.weightx = 0;
		constraints.insets = new Insets(10, 20, 10, 20);
		constraints.anchor = constraints.PAGE_START;

		messagePanel.add(messageText,constraints);
		
		
		
		
	
		
		
		messagePanel.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				System.out.println("You clicked on panel "+sender);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() % 2 == 0)
				{
					e.consume();
					messageLabel.setText("here comes the MATH "+Math.random());
					messagePanel.updateUI();
					
				}
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			
		});
		
		//messagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		messagePanel.setToolTipText(" "+senderName.getText());
		return messagePanel;
		
		
	}
	
}