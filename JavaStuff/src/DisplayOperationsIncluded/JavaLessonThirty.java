package DisplayOperationsIncluded;

import javax.swing.*;
import java.io.*;




//reading from files and writing in files

public class JavaLessonThirty 
{
	
	public static void main(String[]args)
	{
		
		Customer[] customers = getCustomers();
		
		PrintWriter customerOutput = createFile("D:/EclipseWorkspace/Java Training/FileOperations/CustomersList2.txt");
		
		
		//throwing the customers into .txt file
		for(Customer person : customers)
		{
			createCustomer(person , customerOutput);
		}
		
		
		
		
		customerOutput.close();//to complete all writing to the file we should close PrentWriter by simple method .close();
		
		
		getFileInfo();
		
	}
	
	private static class Customer{
		
		public String firstName , lastName;
		public int customerAge;
		
		
		public Customer(String firstName, String lastName,int customerAge)
		{
			this.firstName = firstName;
			this.lastName = lastName;
			this.customerAge = customerAge;
		}
		
		
	}
	
	private static Customer[] getCustomers()
	{
		
		Customer[] customers = new Customer[5];
		
		customers[0] = new Customer("Laina","Morris",22);
		customers[1] = new Customer("Justine","I",23);
		customers[2] = new Customer("Derek","Banas",30);
		customers[3] = new Customer("Connor","Perkins",15);
		customers[4] = new Customer("Robin","S.",17);
		
		return customers;
	}
	private static PrintWriter createFile(String locationPlusName)
	{
		
		try
		{
			File listOfNames = new File(locationPlusName);
			System.out.println(listOfNames.getCanonicalPath());
			PrintWriter infoToWrite = new PrintWriter(//is used to write characters in console , files etc.
					new BufferedWriter(//it gathers all character into a bunch and writes them
							new FileWriter(listOfNames)));//FileWritter()  is used to write the stream of characters into a file
			
			return infoToWrite;
			
		}
		catch(IOException e)//catching potential error
		{
			System.out.println("AN IO error ocurred");
			System.exit(0);
		}
		
		
		return null;
		
	}
	private static void createCustomer(Customer customer , PrintWriter customerOutput)
	{
		
		String custInfo = customer.firstName + " " + customer.lastName + " ";
		
		customerOutput.println(custInfo);//writes desired info to the file
		
	}
	
	private static void getFileInfo()
	{
		
		System.out.println("Info written to a file : \n");
		
		File listOfNames = new File("D:/EclipseWorkspace/Java Training/FileOperations/CustomersList2.txt");// we are reopening the file
		
		try{
			
			BufferedReader getInfo = new BufferedReader(
					new FileReader(listOfNames));
			
			
			String customerInfo = getInfo.readLine();
			
			while(customerInfo != null)
			{
				//System.out.println(customerInfo);
				String[] individualCustomerData = customerInfo.split(" ");
				//int customerAge  = Integer.parseInt(individualCustomerData[2]);
				System.out.println("First Name : "+ individualCustomerData[0]);
				
				if(customerInfo.contains("Laina"))
				{
					
					System.out.println("My love");
				}
				
				customerInfo = getInfo.readLine();
			}
				
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println("File wasn't found :(");
			System.exit(0);
		}
		catch(IOException exception)
		{
			System.out.println("An IO/Exception ocurred");
			System.exit(0);
		}
		
		
		
	}

}
