import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

public class JavaLessonTwelve
{
	
	
	public static void main(String[]args)
	{
		
		LinkedList myLinkedList = new LinkedList();
		
		LinkedList<String> names = new LinkedList<String>();
		
		names.add("Laina");
		names.add("Justine");
		names.add("Alisa");
		
		names.addLast("John Scalley");//adding as a last element of the LinkedList(Array)
		names.addFirst("Laina Morris");//adding as a first element of the LinkedList(Array)
		names.add(2,"Jesica");//placing on certain index
		names.set(5, "Steve Jobs");//replacing certain element which was placed on certain index(entered index) by new data(entered data)
		
		names.remove(4);//here in LinkedLinks you have 2 types of removing elements from an array / the first one if removing by using the index of certain element
		names.remove("Alisa");//and the second one is just using the data of that certain element;
		//retrieving of data is the same as in the ArrayList , by using simple method .get(indexNumber);
		//retrieving of last/first element is absolutely the same way as in ArrayList .getLast() / .getFirst()
		//almost all operations with LinkedList arrays are very similar to ArrayList arrays operations
		//such as removing , setting on certain position , copying , clearing , converting etc. 
		
		for(String name : names)
		{
			System.out.println(name);
		}
	}
	
}