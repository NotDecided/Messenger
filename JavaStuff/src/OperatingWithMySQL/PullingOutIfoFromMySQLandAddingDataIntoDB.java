package OperatingWithMySQL;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;//swing interface
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PullingOutIfoFromMySQLandAddingDataIntoDB extends JFrame
{
	
	static JLabel lFirstName,lLastName,lState,lBirthDate;
	
	static JTextField tfFirstName,tfLastName,tfState,tfBirthDate;
	
	static java.util.Date birthDate,sqlBirthDate;

	
	
	static Object[][] dataResults;
	
	static Object[] columns = {"ID","First Name" ,"Last Name","State","Birth Date"};
	
	static JButton addPresident,removePresident;
	
	static ResultSet setQuery;
	
	static DefaultTableModel dTableModel = new DefaultTableModel(dataResults,columns)//overriding of DefaultTableModel
	{
	  
		public Class getColumnClass(int column)
		{
			Class returnValue;
			
			if((column >=0)&&(column<getColumnCount()))
			{
				returnValue = getValueAt(0,column).getClass();
				
				
			}
			else
			{
				returnValue = Object.class;
			}
			
			return returnValue;
		}
		
	};
	
	static JTable Table = new JTable(dTableModel);
	
	private static JPopupMenu popupMenu;
	
	public static void main(String[]args)
	{
		
		JFrame frame= new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Connection conn = null;
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/samp_db", "root","Arthur12061997");
			
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			String selectStuff ="SELECT president_ID,first_name , last_name,state ,birth FROM president";
			
		    setQuery = statement.executeQuery(selectStuff);
			
			Object[] tempRow;
			
			while(setQuery.next())
			{
				
				tempRow = new Object[]{setQuery.getInt(1),setQuery.getString(2),setQuery.getString(3),setQuery.getString(4),setQuery.getInt(5)};
				
				dTableModel.addRow(tempRow);
				
				
			}
			
			
		}
		catch(SQLException ex)
		{
			ex.getStackTrace();
			System.out.println(ex.getMessage());
			
		}
		catch(ClassNotFoundException e)
		{
			e.getStackTrace();
			System.out.println(e.getMessage());
		}
		
		Table.setFont(new Font("Serif",Font.PLAIN,30));
		
		Table.setRowHeight(Table.getRowHeight()+10);
		
		Table.setAutoCreateRowSorter(true);
		
		JScrollPane scrollPane = new JScrollPane(Table);
		frame.add(scrollPane,BorderLayout.CENTER);
		
		  popupMenu = new JPopupMenu();
			
			JMenuItem menuItem = new JMenuItem("Delete");
			popupMenu.add(menuItem);
			
			menuItem = new JMenuItem("Start a conversation");
			popupMenu.add(menuItem);//gotta complete this pop up menu stuff
			//and should figure out how it works.
			//*** http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html#hierarchy
			
			Table.addMouseListener(new MouseAdapter(){
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
						popupMenu.show(Table,e.getX(),e.getY());
					}
				}
			});
		
		addPresident = new JButton("Add PRESIDENT");
		addPresident.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
			String sFirstName ="",sLastName ="",sState="",sBirthDate ="";	
			int iPresidentID = 0;	
			
			sFirstName = tfFirstName.getText();
			sLastName = tfLastName.getText();
			sState = tfState.getText();
			sBirthDate = tfBirthDate.getText();
			
	
			sqlBirthDate = getADate(sBirthDate);//converts our time
			
			try
			{
				
				//updating every single property element of the desired table when the new element is created
				
				setQuery.moveToInsertRow();//scrolls list(table(JTable)) straight to recently created element
				setQuery.updateString("first_name", sFirstName);
				setQuery.updateString("last_name", sLastName);
				setQuery.updateString("state", sState);
				setQuery.updateDate("birth", (Date) sqlBirthDate);
				
				
				setQuery.insertRow();//Inserts the contents of the insert row into this ResultSet object and into the database.
				//The cursor must be on the insert row when this method is called.
				setQuery.updateRow();//Updates the underlying database with the new contents of the current row of this ResultSet object. 
				//This method cannot be called when the cursor is on the insert row.
				//directly updates the database , you are going to do this every single time you work with database
			
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
			}
			
			try
			{
				setQuery.last();//scrolls down to the last element of the list(database table)
				iPresidentID = setQuery.getInt(1);//gets first property element name value (in our case its presidentID)
			}
			catch(SQLException e1)
			{
			   e1.printStackTrace();
			}
			
			
			
			Object[] president ={iPresidentID,sFirstName,sLastName,sState,sBirthDate};
			
			dTableModel.addRow(president);
			
			}
		});
		
		
		removePresident = new JButton("Remove President");
		removePresident.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				dTableModel.removeRow(Table.getSelectedRow());
				
				try
				{
					setQuery.absolute(Table.getSelectedRow());//gets an id of clicked row
					setQuery.deleteRow();//deletes that row
					
				}
				catch(SQLException e1)
				{
					
					e1.printStackTrace();
					
				}
				
				
			}
			
		});
		
		
		
		
		lFirstName =new JLabel("First Name");
		lLastName =new JLabel("Last Name");
		lState =new JLabel("State");
		lBirthDate =new JLabel("Birth Date");
		
		tfFirstName = new JTextField(15);
		tfLastName = new JTextField(15);
		tfState = new JTextField(2);
		tfBirthDate = new JTextField("yyyy-MM-dd",15);
		
		JPanel thePanel = new JPanel();
		frame.add(thePanel,BorderLayout.SOUTH);
		
		thePanel.add(lFirstName);
		thePanel.add(tfFirstName);
		thePanel.add(lLastName);
		thePanel.add(tfLastName);
		thePanel.add(lState);
		thePanel.add(tfState);
		thePanel.add(lBirthDate);
		thePanel.add(tfBirthDate);
		
		
		thePanel.add(addPresident);
		thePanel.add(removePresident);
		
		/*
		Table.addMouseListener(new MouseAdapter()
		{
			public  void mouseReleased(MouseEvent me)
			{
				
				String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");
				
				if(value != null)
				{
					Table.setValueAt(value, Table.getSelectedRow(), Table.getSelectedColumn());
					
				}
				
				try
				{
					setQuery.absolute(Table.getSelectedRow()+1);
					
					String updateColumn = dTableModel.getColumnName(Table.getSelectedColumn());
					
					switch(updateColumn)
					{
					case "birth" :
						sqlBirthDate = getADate(value);
						setQuery.updateDate(updateColumn,(Date)sqlBirthDate);
						setQuery.updateRow();
						break;
					default:
						setQuery.updateString(updateColumn,value);
						setQuery.updateRow();
						break;
						
						
					}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				
			}
		});*/
		
		
		frame.setSize(1200,500);
		frame.setVisible(true);
		
	}
	
	
	private static java.util.Date getADate(String sBirthDate)
	{
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			birthDate = dateFormatter.parse(sBirthDate);
			
			sqlBirthDate = new java.sql.Date(birthDate.getTime());
			
			
			
			
		}
		catch(ParseException ex)
		{
			ex.printStackTrace();
			
		}
		
		return sqlBirthDate;
	}
}