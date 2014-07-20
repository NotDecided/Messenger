import java.util.Scanner;
public class JavaLessonFive
{
	
	static double PI = 3.14159;//class variable
	static int randomNumber;
	static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[]args)
	{
		
		
		addThem(3,5);
		
		System.out.println("Global ="+PI);
		System.out.println("return function : "+ addThem(3,10));//printing of the value calculated in returnable function
		
		int d = 10;
		System.out.println("d original (without applying any methods) = "+d);
		
		voidFunc(d);
		
		
		int  guessResult = 1 ;
		int randomResult = 0;
		
		System.out.println("Obvious hint for simplicity of experiment : " +getRandomNumber());
		
		while(guessResult != -1)
		{
			System.out.println("Guess the number between 0 and 50 ");
			randomResult = userInput.nextInt();//obtaining user's desired integer , in other words user's guess
			
			guessResult = checkNumberGuess(randomResult);
			
		}
		
		System.out.println("Yes the random number is " + randomResult);
		
		String name = "arthur".toUpperCase();
		System.out.println(name);
		
		
		
		
		
	}
	
	public static int getRandomNumber()
	{
		
		randomNumber = (int)(Math.random()*51);
		
		return randomNumber;
	}

	public static int checkNumberGuess(int guess)
	{
		
		if(guess == randomNumber)
		{
			return -1;
		}
		else
		{
		return guess;
		}
		
	}

	public static void voidFunc(int d) {
		
		d = d+5;
		System.out.println("d int in the void function (after applying its methods) = " + d);
		
	}
	public static int addThem(int a , int b)
	{
		
		double smallPI = 3.40;//local varialbe
		
		PI = 30; //changing the value of the global variable
		
		double  PI = 20.0;
		System.out.println("local  =" + PI );
		
		int sum = a +b;
		
		return sum;
	}
	
	
	
}