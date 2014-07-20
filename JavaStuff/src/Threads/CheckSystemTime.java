package Threads;

import java.util.*;
import java.util.Date;
import java.text.DateFormat;



public class CheckSystemTime implements Runnable
{
	
	
	public void run()
	{
		
		Date RightNow;
		Locale currentLocale;
		DateFormat timeFormatter;
		String timeOutput;
		
		RightNow = new Date();
		currentLocale = new Locale("en");
		timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLocale);
		timeOutput = timeFormatter.format(RightNow);//as i guess it converts the time data into string
		
		System.out.println("Time: " + timeOutput);
		
		
		
		
	}
	
}