public class Vehicle implements Driveable
{
	
	int speed = 300;
	int wheelsNumber =4;
	int carStrength = 0;
	
	public void setSpeed(int Speed)
	{
	  this.speed = Speed;
	}
	public int getSpeed()
	{
		return this.speed;
	}
	public void setWheels(int NumOfWheels)
	{
		this.wheelsNumber = NumOfWheels;
	}
	public int getWheels()
	{
		return this.wheelsNumber;
	}
	
	public void setCarStrength(int strength)
	{
		this.carStrength = strength;
	}
	public int getStrength()
	{
		return this.carStrength;
	}
	public Vehicle(int Wheels , int Speed)//our constructor
	{
		this.speed = Speed;
		this.wheelsNumber = Wheels;
	}
	
	
}