public class JavaLessonFifteen 
{
	//using of abstract classes and interfaces
	//and their implementing
	public static void main(String[]args)
	{
		
		Vehicle car = new Vehicle(4,34);
		System.out.println("Speed " + car.getSpeed());
		System.out.println("Wheels " + car.getWheels());
		
		System.out.println("Car crash strength before applying new value " + car.getStrength());
		car.setCarStrength(40);
		System.out.println("Car crassh strength after applying new value " + car.getStrength());
	}
	
}