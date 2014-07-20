import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class JavaLessonEleven
{
	
	
	public static void main(String[]args)
	{
		//defining and using array list method //ArrayList  it's a flexible array which resizes its size 
		//depends on how many elements are involved in it
		ArrayList myArrayList = new ArrayList();//it can contain any type of objects  if it's declared like this one
		
		//but you can also define a certain type of objects which array will be containing in itself
		//here is the way how you can do that
		//let's pretend that you want to store only String objects , in this case you should define a new ArrayList object in the following way
		//
		ArrayList<String> names = new ArrayList<String>();//<DataType> , which in our case is <String> 
		
		//so , we've done with the defining of an ArrayList
		//now we are gonna add some elements in it
		//and we'll do that by using simple method arrayName.add(dataWhichWillBeAddedToTheArray) 
		//so let's implement that
		names.add("Johny");//here is an example of adding
		names.add(0,"Jack");//here is an example of placing certain object on certain position in array .add(DesiredIndex,data);
		names.add("Jessey");
		names.add("Justine");
		names.add("Laina");
		
		names.set(3, "iJustine");
		
		names.remove(2);// .remove(indexOfTheElement) method which removes elements from the ArrayList by using its element id (index)
		//or you can remove the range of the array elements by using simple method .removeRange(firstIndex,secondIndex);
		//in order to list  all ArrayList objects(all objects from that Array) we'll use the following for loop
		for(int i = 0;i<names.size();i++)//here names.size() , .size() - is a similar method to .length , which returns the length of an ArrayList
			//like .length does in simple Array
		{
			System.out.println(names.get(i));//as it's an ArrayList we can't simply retrieve data from the array by using nameOfTheArray[i] 
			// in this case we should use an ArrayList .get() method which will retrieve all desired data from an array //
			//it should be implemented like this nameOfTheArrayList.get(i(for loop looping variable , in our case it's i))
		}
		
		//for replacing some of an ArrayList objects we should use .set() method , nameOfTheArray.set(desiredIndex,desiredData);
		//which will replace current data by new data all you have to do is just enter the index of the replaceable data and new data 
		//for example : names.add(3,"iJustine"); and after that you should rewrite for loop for this ArrayList in order to update ArrayList data nad list it
		
		// in order to print out all ArrayList we just put its name in System.out.println(nameOfAnArrayList); and it will be printed out
		int k = 0;
		while(k <=40)
		{
		System.out.print("-");
		k++;
		}
		System.out.println();
		
		for(String i : names)//you can also use this type of for loop in order to list arraylist elements
		{
			System.out.println(i);
		}
		
		k = 0;
		while(k <=40)
		{
		System.out.print("-");
		k++;
		}
		System.out.println();
		
		//
		
		//les't pretend that we want to copy all elements from one ArrayList to another
		//and we can that by using simple method .addAll() ; which takes name of the ArrayList which will be copied as the parameter , and should be used in the following way
		// nameOfTheNewArrayList.addAll(nameOfArrayListWhichElementsWeAreGonnaCopy);
		
		//if we want to check some array for containing certain element we can use simple method .contains(nameOfCertainElement);
		
		//if we want to empty the ArrayList we can simply do that by using .clear() method;
	    //in order to check if an ArrayList is empty we can simply use an .isEmpty() method;
		
		//if we want to convert the ArrayList into simple Array we can simply use .toArray() method;
		
		
		
		
	}
}