package Threads;



public class JavaLessonSeventeen
{
	
	public static void main(String[]args)
	{
		
		Thread getTime = new JavaLEssonSeventeenGetTime();
		
		Runnable getMail = new JavaLessonSeventeenGetMail(10);
		
		Runnable getMailAgain = new JavaLessonSeventeenGetMail(20);
		
		
		
		getTime.start();
		
		new Thread(getMail).start();
		new Thread(getMailAgain).start();
		
		
	}
	
	
}