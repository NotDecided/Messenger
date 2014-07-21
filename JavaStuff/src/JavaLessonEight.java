import java.util.Arrays;

public class JavaLessonEight
{
	
	public static void main(String[]args)
	{
		
		MonsterLessonClassTwo.buildBattleBoard();
		
		char[][] temporaryBattleBoard = new char[10][10]; 
		
		//ObjectName[] ArrayName = new ObjectName[5] // creating and object array which will be containing a certain amount of its initializations ,explanation is,probably, not so perfect and not so correct ;
		//but anyway it's something like that
		
		//we are creating an MonsterClassArray which will contain certain amount(in our case 5) of monster object initializations
		MonsterLessonClassTwo[] Monsters = new MonsterLessonClassTwo[5];
		
		//
		Monsters[0] = new MonsterLessonClassTwo(1000,20,1,"Frank");
		Monsters[1] = new MonsterLessonClassTwo(1000,20,1,"John");
		Monsters[2] = new MonsterLessonClassTwo(1000,20,1,"Paul");
		Monsters[3] = new MonsterLessonClassTwo(1200,30,1,"Laina");
		Monsters[4] = new MonsterLessonClassTwo(1000,20,1,"Justine");
		
		MonsterLessonClassTwo.redrawTheBoard();
	}
	
	
}