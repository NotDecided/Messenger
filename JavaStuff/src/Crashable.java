public abstract class Crashable
{
	boolean carCrashed = false;
	
	
	//here can be defined all types all values and the won't be forced to be constant
	public void youCrashed()
	{
		carCrashed = true;
	}
	
	public abstract void setCarStrength(int strength);
	
	public abstract int getCrashStrength();
	
	
}