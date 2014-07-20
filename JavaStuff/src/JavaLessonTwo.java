import java.util.Scanner;
public class JavaLessonTwo
{
    static Scanner userInput = new Scanner(System.in);
	public static void main(String[]args)
	{
		
		System.out.print("Your favorite number : ");
		
		if(userInput.hasNextInt())
		{
			
			int numberEntered = userInput.nextInt();
			
			int numberEnteredTimes2 = numberEntered + numberEntered;
			
			System.out.println("You entered " + numberEntered);
			System.out.println(numberEntered +" + "+numberEntered +" = " + numberEnteredTimes2);
			
			int numberMinus2 = numberEntered - 2;
			System.out.println(numberEntered + " -  2   =  "+ numberMinus2);
			
			int enteredNumberMultipliedByItself = numberEntered * numberEntered;
			System.out.println(numberEntered+" * " + numberEntered + " = " + enteredNumberMultipliedByItself);
			
			int numberDividedBy2 = numberEntered/2;
			System.out.println(numberEntered + " / 2 "+ " = " + numberDividedBy2);
			
			int remainderOfThePreviousDivision = numberEntered %2 ;// "%" is a module sign in programming which divides certain value by certain value 
			//and returns the remainder
			System.out.println(numberEntered + " % 2 = " + remainderOfThePreviousDivision);
			
			numberEntered += 20;
			
			
			int numberABS = Math.abs(numberEntered);//absolute number
			
		
			int maxValue = Math.max(10,43);//it'll return the max value , in our case 43
			int mixValue = Math.min(324, 235);//it'll return the min value ,in our case 235
			
			double numberSqrt = Math.sqrt(numberEntered);//returns the sqrt of certain value
			
			int numberCeilingToBigger = (int)Math.ceil(numberEntered);//rounding the number to bigger value 
			//for example : if the value would be equal to 5.23 then it would be rounded to 6 
			//in this case you make java round the value by your desire
			int numberCeilingToSmaller = (int)Math.floor(numberEntered);//rounding the number to smaller value
			//for example : if the value would be equal to 5.23 ,as well as , in rounding to the bigger value ,then
			//number would be rounded to 5
			//in this case you make java round the value by your desire
			
			int numberRounding = (int)Math.round(numberEntered);//in this case you make java round the value as it logically be
			//for example if the value is equal to 5.5 then it'll be rounded to 6 || if the value is equal to 5.4 it'll be rounded to 5
			
			int generatingOfTheRandomNumber = (int)(Math.random());//basically generates number between 0.1 and 0.9999
			//and for more advanced using of this stuff you can experiment with it and use the desired rounding method in order to get an integer value
			int generatingOfTheRoundedRandomNumber = (int)(Math.round(Math.random()*10));//in this case we'll get all rounded integer values between 1 and 10
			System.out.println("Random number between 1 and 10 = " + generatingOfTheRoundedRandomNumber);
			
		}
		else
		{
			System.out.println("Enter an integer next time");
			
		}
		
	}
}