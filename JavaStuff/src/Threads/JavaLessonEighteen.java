package Threads;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit.*;
import java.util.concurrent.*;


public class JavaLessonEighteen 
{
	
	public static void main(String[]args)
	{
	
		addThreadsToPool();
		
	}
	public static void addThreadsToPool()
	{
		ScheduledThreadPoolExecutor eventPool = new ScheduledThreadPoolExecutor(5);  
		
		eventPool.scheduleAtFixedRate(new CheckSystemTime(), 0, 2, TimeUnit.SECONDS);
		eventPool.scheduleAtFixedRate(new PerformSystemCheck("Mail"), 5, 5, TimeUnit.SECONDS);
		eventPool.scheduleAtFixedRate(new PerformSystemCheck("Calendar"), 10, 10, TimeUnit.SECONDS);
		
		System.out.println("Number of threadth : " + Thread.activeCount());
		
		Thread[] listOfThreads = new Thread[Thread.activeCount()];
		Thread.enumerate(listOfThreads);//it's gonna take all threads and throw them into the Thread array which we've just defined
		
		for(Thread i : listOfThreads)
		{
			System.out.println(i.getName());
		}
		
		//in case you want to shut down all this eventPools(ScheduledThreadpoolExecutors) you can simply use .shutdown() method
		//example ; eventPool.shutdown();
		
		try
		{
			Thread.sleep(20000);
			
		}
		catch(InterruptedException e)
		{
			
		}
		
		
	}
	
}