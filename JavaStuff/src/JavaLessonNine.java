import java.util.Arrays;
public class JavaLessonNine
{
	
	
	public static void main(String[]args)
	{
		
		
		int[] randomArray = new int[10];
		
		randomArray = new int[10];
		randomArray[1] = 2;
		
		String[] stringArray = {"just","declaring","of","array","string","the"};
		
		
		int k = 1;
		while(k <= 50)
		{
			System.out.print("-");
			k++;
		}
		
		System.out.println();
		for(int i =0;i<randomArray.length;i++)
		{
			randomArray[i] = i;
			
			System.out.print("| "+ i +" ");
			
		}
		System.out.print("|");
		
		System.out.println();
		
		k = 1;
		while(k <= 50)
		{
			System.out.print("-");
			k++;
		}
		
		String[][] multiArray = new String[10][10];
		
		for(int i = 0 ; i<multiArray.length ;i++)//looping through the rows of array (x axis);
		{
			
			for(int j = 0;j<multiArray[i].length;j++)//looping through the columns of array(y axis);
			{
				multiArray[i][j] = i + " " + j;
				
			}
		}
		
		k = 1;
		while(k <= 50)
		{
			System.out.print("-");
			k++;
		}
		
		for(int i = 0 ; i<multiArray.length ;i++)//looping through the rows of array (x axis);
		{
			
			for(int j = 0;j<multiArray[i].length;j++)//looping through the columns of array(y axis);
			{
				System.out.print("|" + multiArray[i][j]+ " ");
				
			}
			System.out.println("|");
		}
		
		
		k = 1;
		while(k <= 50)
		{
			System.out.print("-");
			k++;
		}
		
		System.out.println();
		for(int row : randomArray)
		{
			System.out.print("| " +row +" ");
		}
		System.out.println("|" +"\n");
		
		
		//different for loop style and rule of its implementing ( in-hands for loop)
		for(String[] rows : multiArray)//the rule for declaring for (easier) loop 
			//for(dataType[] variableName(dataName1) : nameOfTheArray)//here is dataType which is an array 'cause we are dealing with 2 dimensional array
			//{
			//for(dataType variableName(dataName2) : variableName(dataName1))
		    //{
			////to do some stuff
			//}
			//}
		{
			for(String columns : rows)
			{
				System.out.print("| " + columns +" ");
			}
			System.out.println("|");
		}
		//
		
		//how to print whole entire array
		System.out.println(Arrays.toString(randomArray));//it prints out the entire randomeArray
		
		//how to fill an array with certain data(information)
		char[] moreNumbers = new char[100];
		Arrays.fill(moreNumbers, 'A');
		System.out.println(Arrays.toString(moreNumbers));
		//
		
		//sorting the arrays data
		int[] arrayFullOfRandomNumbers = new int[10];
		//storing the data in array(filling an array)
		for(int i =0;i<arrayFullOfRandomNumbers.length;i++)
		{
			arrayFullOfRandomNumbers[i] = (int)(Math.random()*100);
		}
		Arrays.sort(arrayFullOfRandomNumbers);//method which sorts the data in array
		System.out.println(Arrays.toString(arrayFullOfRandomNumbers));
		//
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}