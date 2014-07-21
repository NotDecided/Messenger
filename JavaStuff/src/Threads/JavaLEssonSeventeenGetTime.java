package Threads;
import java.util.*;
import java.text.DateFormat;



//lesson about threads which are very very useful so you gotta learn all this useful stuff
public class JavaLEssonSeventeenGetTime extends Thread
{
	//all thread actions should be declared inside of run() function
	public  void run()
	{
		
		Date rightNow;
		DateFormat timeFormatter;
		DateFormat dateFormatter;
		Locale currentLocale;
		String timeOutput;
		String dateOutput;
		
		for(int i = 1 ; i<= 20;i++)
		{
			rightNow = new Date();
			currentLocale = new Locale("en");
			timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT,currentLocale);
			dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,currentLocale);
			
			timeOutput = timeFormatter.format(rightNow);
			dateOutput = dateFormatter.format(rightNow);	
			
			System.out.println("Current time : "+timeOutput);
			System.out.println("Current Date : " +dateOutput);
			System.out.println();
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{}
			
		}
		
	}
	
}