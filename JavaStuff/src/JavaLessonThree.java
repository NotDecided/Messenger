
public class JavaLessonThree
{
	
	public static void main(String[]args)
	{
		int randomNumber = (int)(Math.round(Math.random()*20));
		System.out.println("Random number is equal to : " + randomNumber);
		
		/*
		 * Java Relational Operators
		 * > - Bigger than
		 * < - less than
		 * >= - bigger than or equal to
		 * <= - less  than or equal to
		 * == - equal to
		 * != - not equal to
		 */
		
		if(randomNumber >18)
		{
			System.out.println("The number is bigger than 18");
		}
		else if(randomNumber <2)
		{
			System.out.println("The number is less than 2");
		}
		else if(randomNumber >= 10)
		{
			System.out.println("The number is bigger than or equal to 10");
		}
		else if(randomNumber <=12)
		{
			System.out.println("The number is less than or equal to 12");
		}
		else if(randomNumber == 20)
		{
			System.out.println("The number is equal to 20 ");
		}
		else if(randomNumber != 20)
		{
			System.out.println("The number is not equal to 20");
		}
		else
		{
			System.out.println("Nothing works");
		}
		
		
		
		
		/*
		 * Logical operators
		 * Java has 6 logical operators 
		 * ! - Converts the boolean value to its right to its opposite false
		 * & - returns true if 1  
		 * && -
		 * | -
		 * || -
		 * ^ - returns true if there is 1 true and 1 false value
		 * 
		 */
		
		
		
		int value1 = 5;
		int value2 = 8;
		
		int biggestValue = (value1 > value2) ? value1 : value2;
		System.out.println(biggestValue);
		
		
		
		//SWITCH STATEMENT
		char theGrade = 'B';
		switch(theGrade)
		{
		case 'a':   //adding different types of values to single statement
		case 'A':
			System.out.println("Great Job");
			break;//what it'll do , it'll stop checking (executing) switch statement and will do declared stuff
			//you can continue executing (checking) switch statement by deleting "break"
		case 'B':
			System.out.println("Great Job");
			break;
		case 'C':
			System.out.println("ok");
			break;
		case 'D':
			System.out.println("not so bad");
			break;
		default://using of "default" statement is not required , it just broads the diversity of possible "tools"
			System.out.println("Checking  failed , Desired was'n found");
			break;
		}
		//
		
	}
	
	
}