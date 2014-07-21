public interface Driveable
{
	//interfaces are storing data which can't be change which means they are storing constant data by default
	
	//lets declare some function instances
	
	//and inside interfaces you can't create classes(with definition)
	//you have to implement this methods in that class which will be implementing this interface
	//it doesn't mean that you can't define any new methods in your class but all you gotta do is just declare all 
	//defined here methods , variables etc.
	void setSpeed(int Speed);
	int getSpeed();
	void setWheels(int NumberOfWheels);
	int getWheels();
	
	
}