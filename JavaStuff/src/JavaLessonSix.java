import java.util.*;
import java.io.*;
public class JavaLessonSix
{
	
	static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[]args)
	{
		
		
		//divideByZero(5);
		
		
		//System.out.print("How old are you?");
		//int age = checkValidAge();
		
		
		/*if(age != 0)
		{
			System.out.print("You are " +age + " years old");
		}*/
		
		
		getAFile("./someFileName.txt");
		
		
		//or you can use error catching method by using "throws" after function declaring but in front of curly brackets
		/*
		 * try
		 * {
		 *  methodName();
		 *  }
		 *  catch(exceptionName e)
		 *  {
		 *  }
		 *  
		 *  /method declaration
		 *  public static void methodName() throws SomeException , ...
		 *  {
		 *  
		 *    //some stuff
		 *  }
		 */
		
	}
	public static void getAFile(String fileName)
	{
		try{
		FileInputStream file = new FileInputStream(fileName); 
		
		}
		catch(FileNotFoundException e)//this catches very specific exception
		{
			System.out.print("There is no file with following name " + fileName);
		}
		catch(IOException e)//this catches less exact exception 
		{
			System.out.println("Unknown IO Error");
		}
		catch(Exception e)//prints all exceptions (errors that could've occurred)  /;/this catches all exceptions
		{
			System.out.println("Some Error occurred");
		}
		/*
		 * in order to skip an error(don't do anything with error) just don't fill exception field
		 * do it like shown here:
		 * catch(someExceptionName e)
		 * {}
		 */
		finally//it works like a cleaner // as i got it (for example : if some file was opened by (try-catch)  this "finally" will close that file and the same for other accidents
		{
			
		}
	}
	public static void divideByZero(int a)
	{
		/* An example of checking certain action for errors 
		try{
			System.out.println(a/0);
			
		}
		catch(ArithmeticException e)
		{
			
			System.out.println(e.getMessage());
			
		}*/
		
		
		
	}
	
	/*public static int checkValidAge()
	{
		
		 //an input checker (checks the input for the errors (which can be caused by not matching with the condition) 
		try
		{
			return userInput.nextInt();
			
		}
		catch(InputMismatchException e)
		{
			System.out.print("That's not a real age");
			return 0;
		}
		
		
	}*/
	
	
}