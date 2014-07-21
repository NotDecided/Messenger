import java.util.Arrays;


public class MonsterLessonClassTwo
{
	
	static char [][] battleBoard = new char[10][10];//two dimensional array
	
	
	
	
	
	
	public  final String TOMBSTONE = "Here lies a Dead monster";
	
	
	
	private int health = 500;
	private int attack = 20;
	private int movement = 2;
	
	private boolean alive = true;
	
	public String name = "Big Monster";
	public char nameChar1 = 'B';
	public int xPosition = 0;
	public int yPosition = 0;
	public static int numberOfMonsters = 0;
	

	
	
	public static void buildBattleBoard()
	{
		
		for(char[] row : battleBoard)
		{
			Arrays.fill(row, '*');
			
			
		}
		
	}
	
	public static void redrawTheBoard()
	{
		
		int k = 1;
		while(k<=30)
		{
			System.out.print('-');
			k++;
		}
		System.out.println();
		
		for(int i = 0; i<battleBoard.length ; i++)
		{
			
			
			for(int j = 0;j<battleBoard[i].length;j++)
			{
				System.out.print("|"+battleBoard[i][j] + "|");
			}
			System.out.println();
		}
		
		k = 1;
		while(k<=30)
		{
			System.out.print('-');
			k++;
		}
		
	}
	
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
	public boolean getAlive()
	{
		return alive;
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
	
	public MonsterLessonClassTwo(int health , int attack , int movement , String name)//constructor (runs only ones) 
	// the constructors can't  have the return value of any type;
	{
		
		this.health = health;
		this.attack = attack;
		this.movement = movement;
		this.name = name;
		
		//but let's pretend that we've set 3 arguments with the same name as global objects in this class have 
		//then we need to use keyword "this. + desired variable name in this class"
		//
		int maxXBoardSpace = battleBoard.length -1;//it gives a maximum amount of spaces in the row
		int maxYBoardSpace = battleBoard[0].length -1;//it gives a maximum amout of spaces in the column
		
		int randomNumX , randomNumY;
		
		do{
			randomNumX = (int)(Math.random()*maxXBoardSpace);
			randomNumY = (int)(Math.random()*maxYBoardSpace);
		}while(battleBoard[randomNumX][randomNumY] != '*');
		
		this.xPosition = randomNumX;
		this.yPosition = randomNumY;
		
		this.nameChar1 = this.name.charAt(0);//Equivalencing nameChar1 to out name's first char by using simple method .charAt();
		
		battleBoard[this.xPosition][this.yPosition] = this.nameChar1;
		numberOfMonsters++;
		
		
	}
	public MonsterLessonClassTwo()//overloading of main constructor 
	{
		
	}
	
	
	
	public static void main(String[]args)
	{
	
		
		
	}
	
}