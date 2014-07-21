public class HelloWorld
{
	
	static String randomString = "String to print";
	static final double PINUM = 3.141529;//constant value defines by using "final" instead of "const"
	//and here in Java in creating a constant value you should declare its name(all name should be in upper case)
	//by using upper case
	
	public static void main(String[]args)
	{
		
		//this is how variables are being declared in Java
		int firstInteger = 33;
		int secondInteger = 34;
		int sumOfBothFirstAndSecondIntegers = firstInteger + secondInteger;//declaring of the expressions
		
		
		//sizes of values which can be held by variables declared below 
		byte bigByte = 127;
		short bigShort = 32767;
		int bigInt = 2100000000;
		long bigLong = 9220000000000000000L;//longs should be always declared with "L" ending at the end of the value
		float bigFloat = 3.14F;//floats can contain a way smaller "double" value , and a way less numbers after the dot 
		//and the should be always declared with the "F" character at the end of the value declaring
		double bigDouble = 3.1415291224121242412443452324234D;//doubles can contain a big values with a way bigger amount of numbers after the dot
		//and you can use the "D" character at the end of the value declaring but it's not required
		
		
		boolean bool = true;//booleans in java are usual
		
		char randomChar = 65;
		char anotherChar = 'A';
		char escaperChar = '\\';
		
		
		String aRandomString = "I'm a random string";//strings are objects , and declaring similarly to other variables
		String anotherRandomString = "and I'm its friend";
		String andAnotherString = aRandomString + " " + anotherRandomString;//for spaces you can use either ' ' or " " it really doesn't matter
		
		//String converting operations
		String convertingByteToString = Byte.toString(bigByte);
		String convertingShortToString = Short.toString(bigShort);
		String convertingIntToString = Integer.toString(bigInt);
		String convertinglongToString = Long.toString(bigLong);
		String convertingFloatToString = Float.toString(bigFloat);
		String convertingDoubleToString = Double.toString(bigDouble);
		String convvertingBooleandToString = Boolean.toString(bool);
		//
		
		//other converting operations
		double doubleToConvert = 3.1456453D;
		int convertingIntoInt = (int)doubleToConvert;
		
		//it also works for (byte) (short) (long) (double)
		//and doesn't work for (char) (boolean) (float) 
		
		int StringToInt = Integer.parseInt(convertingIntToString);//opposite converting
		//parses also available for Bytes , Shorts , Longs , Booleans , Doubles , Floats
		//parseByte , parseShort , parseLong , parseBoolean , parseDouble , parseFloat
		
		//
		
		
		
		System.out.println(StringToInt);
	
	}
}