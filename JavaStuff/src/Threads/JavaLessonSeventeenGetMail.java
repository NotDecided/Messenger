package Threads;
public class JavaLessonSeventeenGetMail implements Runnable
{
	
   private int startTime;
	
	
	public  JavaLessonSeventeenGetMail(int startTime)
	{
		this.startTime = startTime;
	}
	
	public void run()
	{
		
		try
		{
		  Thread.sleep(startTime *1000);
		}
		catch(InterruptedException e)
		{
			
		}
		System.out.print("Checking Mail");
		
	}
	
}