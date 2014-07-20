public class MonsterLessonClass
{
	
	public  final String TOMBSTONE = "Here lies a Dead monster";
	
	
	
	private int health = 500;
	private int attack = 20;
	private int movement = 2;
	private int Xposition = 0;
	private int Yposition = 0;
	private boolean alive = true;
	
	public String name = "Monster";
	
	public int getHealth()
	{
		return health;
	}
	public int getAttack()
	{
		return attack;
	}
	public int getMovement()
	{
		return movement;
	}
	
	public void setHealth(int decreaseHealth)
	{
		health -= decreaseHealth;
		if(health <0)
		{
			alive = false;
			
		}
	}
	public void setHealth(double decreaseHealth)
	{
		
		int intDecreaseHealth = (int)decreaseHealth;
		health -=intDecreaseHealth;
		if(health <0)
		{
			alive = false;
		}
	}
	
	public MonsterLessonClass(int newHealth , int newAttack , int newMovement)//constructor (runs only ones) 
	// the constructors can't  have the return value of any type;
	{
		
		health = newHealth;
		attack = newAttack;
		movement = newMovement;
		
		//but let's pretend that we've set 3 arguments with the same name as global objects in this class have 
		//then we need to use keyword "this. + desired variable name in this class"
		//
		
		
	}
	public MonsterLessonClass()//overloading of main constructor 
	{
		
	}
	
	
	
	public static void main(String[]args)
	{
	
		
		
	}
	
}