package OperatingWithMySQL;


import java.util.Date;






import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import DisplayOperationsIncluded.JavaLessonTwentyfive.ListenerForButtons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


//it should be MySQL reader/writer(should read /write/create new databases,tables etc.  in MySQL) 
public class MyPracticingWithMySQL 
{
	
	
	
	static Connection conn = null;
	
	static Statement statement;
	
	static ResultSet rows;
	
	static JTextField inputReader;
	
	static JButton executeButton;
	
	public static void main(String[]args)
	{
		
		try{
		
		Class.forName("com.mysql.jdbc.Driver");
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost/lahman","root","Arthur12061997");
		
		statement = conn.createStatement();
		
		String inputQuery = "describe salaries;";
		
		rows = statement.executeQuery(inputQuery);		
		
		
		while(rows.next())
		{
			System.out.println(rows.getString(1));	
		}
		
		
		
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
		   System.out.println(e.getMessage());
		   e.getStackTrace();
		}
		
		
		
		JFrame theFrame = new JFrame();
		theFrame.setSize(400,200);
		
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel thePanel = new JPanel();
		theFrame.add(thePanel);
		
	    inputReader = new JTextField(10);
	    
		thePanel.add(inputReader);
		
		
		
		executeButton = new JButton("Execute");
		executeButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try
				{
				rows = statement.executeQuery(inputReader.getText());
				
				while(rows.next())
				{
					
					System.out.print(rows.getString(1)+" " +rows.getString(2));
					System.out.println();
				}
				
				}
				catch(SQLException e1)
				{
					
				}
				
			}
			
		});


		thePanel.add(executeButton);
		
		
		
		

		theFrame.setVisible(true);
		
		
		
		
	}
	

	
	
}

