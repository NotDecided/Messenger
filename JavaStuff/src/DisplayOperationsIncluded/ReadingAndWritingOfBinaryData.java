package DisplayOperationsIncluded;

import java.io.*;




//(1)writing and (2)reading of data by using DataOutputStream/DataInputStream | BufferedOutputStream/BufferedInputStream | FileOutputStream/FileInputStream 
public class ReadingAndWritingOfBinaryData
{

	public static void main (String[]args)
	{
		
	
		Customer[] customers = getCustomers();	
		
		DataOutputStream custOutput = createFile("customers.dat");
		
		for(Customer person : customers)//in hands for loop
		{
			createCustomers(person,custOutput);
		}
		
		
		try
		{
			custOutput.close();
		}
		catch(IOException e)
		{
			
		}
		
		getFileInfo();
		
	}
	
	
	private static class Customer
	{
		
		public String custName;
		public int custAge;
		public double custDept;
		public boolean oweMoney;
		public char custSex;
		
		public Customer(String custName,int custAge,double custDept,boolean oweMoney,char sex)
		{
			this.custName = custName;
			this.custDept = custDept;
			this.custAge = custAge;
			this.oweMoney = oweMoney;
			this.custSex = sex;
			
		}
	}
		
	
	
		private static Customer[] getCustomers()
		{
			
			Customer[] customers = new Customer[5];
			
			customers[0] = new Customer("John",19,200.32,true,'M');
			customers[1] = new Customer("Paul",19,200.32,true,'M');
			customers[2] = new Customer("Sally",19,200.32,true,'M');
			customers[3] = new Customer("Sue",19,200.32,true,'M');
			customers[4] = new Customer("Jessey",19,200.32,true,'M');
			
			return customers;
			
		}
		
		private static DataOutputStream createFile(String thePath)
		{
			
			try
			{
			File listOfNames = new File(thePath);

			
			DataOutputStream inputToWrite = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(listOfNames)));
			
			//FileOutputStream(new File(),true || false) if false then it will create  a new file each time the function is called  and if 
			//true it will append new data to current file
			
			return inputToWrite;
			}
			catch(IOException e)
			{
				System.out.println("an IOException error ocurred");
				System.exit(0);
			}
			return null;
			
		}
		
		private static void createCustomers(Customer customer , DataOutputStream custOutput)
		{
			
			try
			{
				custOutput.writeUTF(customer.custName);//it writes the string , very important to keep in mind that
				custOutput.writeInt(customer.custAge);//it writes the Integer , very important to keep in mind that
				custOutput.writeDouble(customer.custDept);//it writes the Double , very important to keep in mind that
				custOutput.writeBoolean(customer.oweMoney);//it writes the Boolean , very important to keep in mind that
				custOutput.writeChar(customer.custSex);//it writes the Character , very important to keep in mind that
				
			}
			catch(IOException e)
			{
				System.out.println("An IOException error ocurred");
				System.exit(0);
			}
			
			
		}
	
		
		private static void getFileInfo()
		{
			
			System.out.println("Info Written to a file : " );
			
			File fileInfo = new File("customers.dat");
			
			boolean endingOfThisFile = false;
			
			try
			{
				DataInputStream getInfo = new DataInputStream(new BufferedInputStream(new FileInputStream(fileInfo)));
				
				while(!endingOfThisFile)
				{
					String custName = getInfo.readUTF();//gets a Strings from the .dat file
					int custAge = getInfo.readInt();//gets a ints from the .dat file
					double custDept = getInfo.readDouble();//gets a doubles from the .dat file
					boolean haveMoney = getInfo.readBoolean();//gets a booleans from the .dat file
					char customerSex = getInfo.readChar();//gets a chars from the .dat file
					
					System.out.println(custName);
					System.out.println(custAge);
					System.out.println(custDept);
					System.out.println(haveMoney);
					System.out.println(customerSex);
					
					
					
				}
			}
			catch(EOFException e)
			{
				
				endingOfThisFile = true;
				System.out.println("No File");
				System.exit(0);
				
				
			}
			catch(IOException e)
			{
				System.out.println("I/O EXCEPTION ocurred");
				System.exit(0);;
			}
			
		}
	
	
	
}