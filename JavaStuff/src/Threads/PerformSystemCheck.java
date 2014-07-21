package Threads;//name of the package where this class is being stored

import java.util.concurrent.locks.ReentrantLock;

public class PerformSystemCheck implements Runnable
{
	
	private String checkWhat;
	ReentrantLock  lock = new ReentrantLock();
	
	
	public PerformSystemCheck(String checkWhat)
	{
		this.checkWhat = checkWhat;
	}
	 public void run()
	{
		
		lock.lock();//it locks this method from being accessible and from being used by other threads
		System.out.println("Checking " + this.checkWhat);
		lock.unlock();//it unlocks this method from being not accessible and from not being used by other threads
	}
	
	
	
}