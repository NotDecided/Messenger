import java.util.regex.*;//import in regular expression library

//regular expressions
public class JavaLessonNineteen
{
	
	public static void main(String[]args)
	{
		
		String longString = "here will be placed a lot of my text which will be used for matcher  55645 CA PA certainEmail@gmail.com 2-(564)-545-656 5555-452-452";
		String shortString =" }}} {{{ <<< >>> ((( ))) ... ,,, AAA aaa BBB bbb";
		
		//word that's 2 to 20 character in length
		//[A-Za-z]{2,20}   \\w{2,20}
		
		//regularExpressionCheck("\\s[A-Za-z]{2,20}\\s",longString);\\
		
		//let's pretend that we are looking for digits : "\\d       
		//for making a search for very very specific values which are repeating certain amount of times then will use {certainAmoutOfTimer}
		//then will use the following code 
		
		//the following example shows how to make ZIP code search request
		
		//regularExpressionCheck("\\s\\d{5}\\s",longString);
		
		//now we are gonna make a state search request for state
		//considering that fact that state name takes 2 chars we do the following
		
		//regularExpressionCheck("C[AO]|P[AH]",longString);
		
		//if you find the word or expression with minimum character of certain amount of chars then you will do the following {certainMinimumAmount,} 
		// if you wanna use one of the following symbols in your request you gotta put a double backslash in front of them
		// symbol list : . * + ? ^ { } [ ] ( ) \ |
		// + sign continues searching if there are more than one result which matches with search request
		// . sing defines searching for any chars
		// w[] - uses in word searching
		// "\\s   ... \\s" defines spaces (and it's being used for not considering spaces as a characters
		// ? - defines possible availability of certain expression or character combination  
		
		//regularExpressionCheck("\\s.{3}\\s+",shortString);//searching for any chars combination from 3 chars
		
		
		//now we are gonna make a search request form email
		//regularExpressionCheck("[A-Za-z0-9._%-]+@[A-Za-z0-9._-]+\\.[A-Za-z0-9]{2,4}",longString);
		
		//now we are gonna make a search request for a phone number  
		//regularExpressionCheck("([0-9](-| )?)?(\\(?[0-9]{3}\\)?|[0-9]{3})( |-)?([0-9]{3}(-| ))?[0-9]{4}|[A-Za-z0-9]{7})",longString);
		
		//now we are gonna replace all spaces in first (long) string with a ", " coma with space
		//and for that we'll define and call a new function called regularExpressionReplace();
		
		
		//now we are gonna replace all spaces in our longString with a ", " coma and space , and we will do that by using our (defined earlier)  function regularExpressionReplace()
		//regularExpressionReplace(longString);
		
		
	}
	public static void regularExpressionCheck(String theRegularExpression, String stringToCheckForMatch)
	{
		Pattern checkRegularExpression = Pattern.compile(theRegularExpression);
		
		Matcher regularExpressionMatcher = checkRegularExpression.matcher(stringToCheckForMatch);
		
		while(regularExpressionMatcher.find())
		{
			if(regularExpressionMatcher.group().length() != 0)
			{
				System.out.println(regularExpressionMatcher.group().trim());
			}
			System.out.println("Strarts at :"+regularExpressionMatcher.start());
			System.out.println("Ends at :" +regularExpressionMatcher.end());
		}
		
	}
	public static void regularExpressionReplace(String desiredStringToReplace)
	{
		Pattern replace = Pattern.compile("\\s+");//it's looking for all data which i've defined here as a parameter
		Matcher regularExpressionMatcher = replace.matcher(desiredStringToReplace.trim());	//what does .trim() method do is it replaces all spaces from certain string	
		System.out.println(regularExpressionMatcher.replaceAll(", "));
	}
	
}