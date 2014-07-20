package OperatingWithMySQL;

import java.sql.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JTable;


public class UsingJTableWithMySQL extends JFrame
{
	
	
	static Object[][] databaseInfo;
	
	static Object[] columns = {"Year","PlayerID","Name","TTRC","Team","Salary","CPR","POS"};
	
	static ResultSet rows;//contains the table of data with all of your queries
	
	static ResultSetMetaData metaData;//it contains all information , the data returned from the query(for example number of columns etc.)
	
	static DefaultTableModel dTableModel = new DefaultTableModel(databaseInfo,columns)// creating of table model , DefaultTableModel(infoSource,titlesForEachElementFromInfoSource) 
	{
	@Override
	public Class getColumnClass(int column)//neat overriding of DefaultTableModel class
	{
		Class returnValue;
		
		if((column >= 0)&& (column < getColumnCount()))
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
	//End of DefaultTableModel overriding
	
	
	public static void main(String[]args)
	{
		
		JFrame theFrame = new JFrame();
		
		theFrame.setDefaultCloseOperation(theFrame.EXIT_ON_CLOSE);
		
		Connection conn = null;
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/lahman591", "root", "Arthur12061997");
			
			Statement statement = conn.createStatement();
			
			String selectStuff = "select b.yearID, b.playerID, " +
            		"CONCAT(m.nameFirst, ' ', m.nameLast) AS Name, " + 
            		"((b.H+b.BB)+(2.4*(b.AB+b.BB)))*(t.TB+(3*(b.AB+b.BB)))/(9*(b.AB+b.BB))-(.9*(b.AB+b.BB)) AS TTRC, " +
            		"b.teamID AS Team, s.salary AS Salary, " +
            		"CAST( s.salary/(((b.H+b.BB)+(2.4*(b.AB+b.BB)))*(t.TB+(3*(b.AB+b.BB)))/(9*(b.AB+b.BB))-(.9*(b.AB+b.BB))) as decimal(10,2)) AS CPR, " +
            		"f.POS AS POS FROM Batting b, Master m, Salaries s, TOTBYR t, Fielding f " +
            		"WHERE b.playerID = m.playerID AND t.playerID = m.playerID " +
            		"AND t.yearID = 2010 AND b.yearID = t.yearID AND s.playerID = b.playerID " + 
            		"AND s.yearID = b.yearID AND b.AB > 50 AND b.playerID = f.playerID " +
            		"AND b.playerID = t.playerID GROUP BY b.playerID ORDER BY TTRC DESC LIMIT 200;";
			
			
			ResultSet rows = statement.executeQuery(selectStuff);
			
			//metaData = rows.getMetaData();
			
			
			Object[] tempRow ;
			
			while(rows.next())
			{
				tempRow = new Object[]{rows.getInt(1),rows.getString(2),rows.getString(3),rows.getDouble(4),rows.getString(5),rows.getInt(6),
						rows.getDouble(7),rows.getString(8)};
				
				dTableModel.addRow(tempRow);
			}
			
			
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
		
		JTable table = new JTable(dTableModel);
		
		table.setRowHeight(table.getRowHeight()+10);
		
		table.setFont(new Font("Serif",Font.PLAIN,20));
		
		table.setAutoCreateRowSorter(true);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col1 = table.getColumnModel().getColumn(0);
		col1.setPreferredWidth(100);
		
		TableColumn col2 = table.getColumnModel().getColumn(1);
		col2.setPreferredWidth(190);
		
		TableColumn col3 = table.getColumnModel().getColumn(2);
		col3.setPreferredWidth(260);
		
		TableColumn col4 = table.getColumnModel().getColumn(5);
		col4.setPreferredWidth(200);
		
		TableColumn col5 = table.getColumnModel().getColumn(6);
		col5.setPreferredWidth(200);
		
		
		
		TableColumn tc  = table.getColumn("Team");//the way of getting info from the column 
		CenterTableCellRenderer centerRenderer = new CenterTableCellRenderer();
		tc.setCellRenderer(centerRenderer);
		
		tc = table.getColumn("POS");
		centerRenderer = new CenterTableCellRenderer();
		tc.setCellRenderer(centerRenderer);
		
		JScrollPane scrollBar = new JScrollPane(table);
		theFrame.add(scrollBar,BorderLayout.CENTER);
		theFrame.setSize(800,500);
		theFrame.setVisible(true);
		
		new UsingJTableWithMySQL();
	}
	
	public UsingJTableWithMySQL()
	{
		
	}
	
	
	
}
class CenterTableCellRenderer extends DefaultTableCellRenderer
{
	public CenterTableCellRenderer()
	{
		setHorizontalAlignment(JLabel.CENTER);
	}
	
}
