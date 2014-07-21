package OperatingWithMySQL;


import java.sql.*;



//dealing with MySQL
public class JavaLessonThirtyOne
{
	
	
	public static void main(String[]args)
	{
		
		Connection conn = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/lahman","root","Arthur12061997");
			
			Statement sqlStatement = conn.createStatement();
			
			String selectStuff = "Select salary from salaries";
			
			ResultSet rows = sqlStatement.executeQuery(selectStuff);
		
			
			while(rows.next())
			{
				
				System.out.println("Salary : $ " + rows.getString(1) + " ");
				int k = 40;
				while(k != 0)
				{
				   System.out.print("-");
				   k--;
				}
				System.out.println();
				
			}
			
			
		}
		catch(SQLException ex)
		{
			System.out.println("SQL Exception "+ex.getMessage());
			System.out.println("Vendor Error  "+ex.getErrorCode());
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
			
		}
		
	}
	
	
	
}