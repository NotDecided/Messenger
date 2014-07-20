import java.util.Scanner;
public class JavaLessonFour
{
	
	static Scanner userinput = new Scanner(System.in);
	
	public static void main(String[]args)
	{
	
		
		//While loop
		int i = 1;
		while(i<=20)
		{
		 
		if(i == 3)
		{
			i+=2;
			continue;//what 
		}
			
		if((i%2) ==0)
		{
			i++;
		}
		if(i>10)
		{
			break;//it stops executing the loop
		}
		 System.out.println(i);
		 i++;
		}
		//
		
		
		
		
		//PI calculating by using while loop
		
		double myPI = 4.0D;
		double J = 3.0D;
		
		//4 - 4/3 + 4/5 - 4/7 - 4/9
		while(J <20)
		{
			myPI = myPI -(4/J) +(4 /(J+2));
			J+=4;
			System.out.println(myPI);
			
			
		}
		System.out.println("Value of PI : " + Math.PI);
		
		
		String continueYourNumber = "y";
		int h = 1;
		while(continueYourNumber.equalsIgnoreCase("y"))
		{
			
			System.out.println(h);
			System.out.println("Continue (y) or not (n)");
			continueYourNumber = userinput.nextLine();
			h++;
		}
		//
		
		
		//do - while loop
		int k = 0;
		do
		{
			k++;
			System.out.println(k);
		}while(k<20);
		//
		
		//for loop
		for(int looper = 0;looper <=30;looper++)//as a simple example
		{
			System.out.println("Looper is equal to : " + looper);
		}
		//
		
	}
	
	
}