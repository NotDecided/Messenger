import java.util.ArrayList;
public class AnExtensionClassFor14lesson
{
	private int ten = 10;
	private static String string = "I'm from that class ";
	public static void main(String[]args)
	{
		
		
	}
	public static String getClassTourist(String location)
	{
		
		return string + location;
		
	}
	public static String setNewPerson(String name , int age , String city,boolean married)
	{
		ArrayList container = new ArrayList();
		container.add(name);
		container.add(age);
		container.add(city);
		container.add(married);
		return  container.toString();
	}
	
}